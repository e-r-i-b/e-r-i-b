package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductHelper;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AccountClosingPayment;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.*;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ���������, ���� �� ��������� �� �������� ������������ ������
 * @author Pankin
 * @ created 30.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountOpeningClaimPrepareHandler extends PrepareDocumentHandler
{
	private static final String WARNING_MESSAGE = "�������� ��������! ���������� �������� ���������� �����.";
	private static final String ERROR_MESSAGE = "��������� ���� ��������� �����.";
	private static final String CHARGE_OFF_AMOUNT_MESSAGE = "���� ����������� ����� �������� ������� �� ������.";
	private static final String DESTINATION_AMOUNT_MESSAGE = "���� ����������� ����� ���������� ������� �� �����.";
	
	private static final String DEPOSIT_PRODUCT_NOT_FOUND = "� ����������� ���������� ��������� ��� ������ ��� ������ � ������� ";
	private static final String LESS_SUM_MESSAGE = "������������ ����� ��� ���������� - %s ����� ��� " +
			"���������� �� ����� ���� ������ ������������ ������� ��� ���������� ������. ����������� " +
			"������������ � ��������� ������, ������� ������ �������.";

	private static final String NOT_FOUND_RATE = "�� ��������� ���� �������� ���������� ������� �����. " +
			"����������, ����������� ������������ � ��������� ������, ������� ������ �������.";

	private static final String CALCULATE_AMOUNT_MESSAGE = "���������� ����� ������� �� ������ ��� �������� ������ � ������ ��������� � ��������.";

	private static final DepositProductService depositProductService = new DepositProductService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	protected boolean checkDatesChanged(StateObject document) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("�������� ��� ������� id=" + document.getId()
					+ " (��������� AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		// ��������� ������ ������� ����� ���� ������ ��� ��������� ���� ��� ��� ��������� ����� ���������
		// �������� ����� ��������� ������ ���� ������
		return accountOpeningClaim.calcConvertionRates() || checkDatesChanged(accountOpeningClaim);
	}

	protected void recalculateDates(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("�������� ��� ������� id=" + document.getId()
					+ " (��������� AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		if (accountOpeningClaim.isRateChanged())
		{
			stateMachineEvent.registerChangedField("confirmCourse");
			stateMachineEvent.addErrorMessage(ERROR_MESSAGE);
		}

		// ���� ���� �� ��������, ������������� �� �����
		if (!checkDatesChanged(accountOpeningClaim))
			return;

		accountOpeningClaim.setOpeningDate(Calendar.getInstance());
		accountOpeningClaim.setClosingDate(DateHelper.add(accountOpeningClaim.getOpeningDate(), accountOpeningClaim.getPeriod()));
		stateMachineEvent.registerChangedField("openingDate");
		stateMachineEvent.registerChangedField("depoClosingDate");
		stateMachineEvent.registerChangedField("closingDate");
		stateMachineEvent.addMessage(WARNING_MESSAGE);
		
	}

	/**
	 * ��������: ���������� �� ������� �� ����������� ������ ��� �������� ������
	 * @param document
	 * @param stateMachineEvent
	 * @return true, ���� ����������
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 */
	private boolean isEnoughMoney(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
		// ��� ������ ��� ������������ �������
		if (!accountOpeningClaim.isWithMinimumBalance())
		{
			Map<String, String> depInfo = getDepInfo(accountOpeningClaim);
			if (depInfo.containsKey(DepositProductHelper.CALC_RATE_ERROR))
			{
				registerChangedFields(accountOpeningClaim, stateMachineEvent);
				stateMachineEvent.addMessage(NOT_FOUND_RATE);
				return false;
			}
		}
		else if (accountOpeningClaim.getDestinationAmount().getDecimal().compareTo(accountOpeningClaim.getIrreducibleAmmount()) < 0)
		{
			registerChangedFields(accountOpeningClaim, stateMachineEvent);
			throw new DocumentLogicException(String.format(LESS_SUM_MESSAGE, createSumString(accountOpeningClaim)));
		}
		return true;
	}

	/**
	 * ��������� ����� � ������ ������������� ������� ���� ��-�� ���������� �������
	 * @param accountOpeningClaim ��������
	 * @param stateMachineEvent stateMachineEvent
	 * @throws DocumentException
	 */
	private void registerChangedFields(AccountOpeningClaim accountOpeningClaim, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		String course = "";
		if (accountOpeningClaim.getState().getCode().equals("INITIAL"))
		{
			course = "course";
		}
		else if (accountOpeningClaim.getState().getCode().equals("SAVED"))
		{
			course = "confirmCourse";
		}

		stateMachineEvent.registerChangedField("destinationAmount");

		if (!getChargeOffCurrencyCode(accountOpeningClaim).equals(getDestinationCurrencyCode(accountOpeningClaim)))
		{
			stateMachineEvent.registerChangedField(course);
		}
	}

	private String getChargeOffCurrencyCode(AccountOpeningClaim accountOpeningClaim) throws DocumentException
	{
		try
		{
			return accountOpeningClaim.getChargeOffCurrency().getCode();
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	private String getDestinationCurrencyCode(AccountOpeningClaim accountOpeningClaim) throws DocumentException
	{
		try
		{
			return accountOpeningClaim.getDestinationCurrency().getCode();
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * ������������ ������, ���������� ���������� � ������ �������� ������� � ���� ����� ����������
	 * @param accountOpeningClaim ����������� �����
	 * @return ������ � ��������� ���������� � ������ ����� ����������
	 * @throws DocumentException
	 */
	private String createSumString(AccountOpeningClaim accountOpeningClaim) throws DocumentException
	{
		String currencySign = CurrencyUtils.getCurrencySign(getDestinationCurrencyCode(accountOpeningClaim));
		String addPoint = (currencySign.equals("���.")) ? "" : ".";
		return accountOpeningClaim.getDestinationAmount().getDecimal().toString() + " " + currencySign + addPoint;
	}

	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("�������� ��� ������� id=" + document.getId() + " (��������� AccountClosingPayment)");
		}
		AccountClosingPayment payment = (AccountClosingPayment) document;
		String code = document.getState().getCode();

		if ("INITIAL".equals(code))
		{
			// ���������, ���������� �� ������� ��� �������� �����
			if (!isEnoughMoney(document, stateMachineEvent))
				return;
			super.process(document, stateMachineEvent);
			saveCollectorMessages(payment, stateMachineEvent);
			updateDocumentAdditionalData(document, stateMachineEvent);

			AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
			if (accountOpeningClaim.isRateChanged())
			{
				stateMachineEvent.registerChangedField("confirmCourse");
			}
		}

		/*
		 *
		 * ���� ������ �������� ����������� ������ �� �������� ������ ����������� �����, ���������� ������
		 * �� ��������������� ������ ����� �������� � ����������, �, � ������ �� ������������ �������� ���������
		 * �� ���������.
		 *
		 */
		if ("SAVED".equals(code))
		{
			// ���������, ���������� �� ������� ��� �������� �����
			if (!isEnoughMoney(document, stateMachineEvent))
				return;
			if (checkDatesChanged(document))
			{
				Set<String> changedFields = new HashSet<String>();

				recalculateDates(document, stateMachineEvent);

				Money chargeOffAmount   = payment.getChargeOffAmount();
				Money destinationAmount = payment.getDestinationAmount();

				super.process(document, stateMachineEvent);

				AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
				boolean isRateChanged = accountOpeningClaim.isRateChanged();

				Money changedChargeOffAmount = payment.getChargeOffAmount();
				if (!MoneyUtil.equalsNullIgnore(chargeOffAmount, changedChargeOffAmount))
				{
					changedFields.add("chargeOffAmount");
					payment.setChargeOffAmount(changedChargeOffAmount);
					if (isRateChanged)
						stateMachineEvent.addErrorMessage(CHARGE_OFF_AMOUNT_MESSAGE);
				}

				Money changedDestinationAmount = payment.getDestinationAmount();
				if (!MoneyUtil.equalsNullIgnore(destinationAmount, changedDestinationAmount))
				{
					changedFields.add("destinationAmount");
					payment.setDestinationAmount(changedDestinationAmount);
					if (isRateChanged)
						stateMachineEvent.addErrorMessage(DESTINATION_AMOUNT_MESSAGE);
				}

                saveCollectorMessages(payment, stateMachineEvent);
				for (String changedField : changedFields)
				{
					stateMachineEvent.registerChangedField(changedField);
				}
			}
			updateDocumentAdditionalData(document, stateMachineEvent);
		}

		if (stateMachineEvent.hasChangedFields())
		{
			stateMachineEvent.addMessage(WARNING_MESSAGE);
		}
	}

	private void saveCollectorMessages(AccountClosingPayment payment, StateMachineEvent stateMachineEvent)
	{
		String agreementViolation = payment.getAgreementViolation();
		if (!StringHelper.isEmpty(agreementViolation))
		{
			agreementViolation += " \"�����������\".";
			stateMachineEvent.addMessage(agreementViolation);
		}

		String longOffertFormalized = payment.getLongOffertFormalized();
		if (!StringHelper.isEmpty(longOffertFormalized))
		{
			stateMachineEvent.addMessage(longOffertFormalized);
		}
	}

	private void updateDocumentAdditionalData(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		String stateCode = document.getState().getCode();
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("�������� ��� ������� id=" + document.getId()
					+ " (��������� AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		if (!accountOpeningClaim.isWithMinimumBalance())
		{
			// ���� ����� ��� ������������ �������, ����� ���������� ���������� ������
			calculateRate(accountOpeningClaim, stateMachineEvent);
		}
		else if (accountOpeningClaim.getDestinationAmount().getDecimal().compareTo(accountOpeningClaim.getIrreducibleAmmount()) < 0)
		{
			String calculatedSumm = createSumString(accountOpeningClaim);
			stateMachineEvent.registerChangedField("destinationAmount");
			if (stateCode.equals("INITIAL"))
				throw new DocumentLogicException(String.format(LESS_SUM_MESSAGE, calculatedSumm));
			else
				stateMachineEvent.addMessage(String.format(LESS_SUM_MESSAGE, calculatedSumm));
		}

		if (stateCode.equals("SAVED"))
		{
			try
			{
				// ��������� �������� � ����, ����� �� �������� ������������� ������
				businessDocumentService.addOrUpdate(accountOpeningClaim);
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
		}
		else if (stateCode.equals("INITIAL"))
		{
			stateMachineEvent.addMessage(CALCULATE_AMOUNT_MESSAGE);
		}
	}

	private Map<String, String> getDepInfo(AccountOpeningClaim accountOpeningClaim) throws DocumentException
	{
		Long depositProductType = accountOpeningClaim.getAccountType();

		Map<String, String> depInfo = null;
		try
		{
			BusinessDocumentOwner documentOwner = accountOpeningClaim.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");

			String depositTariffPlanCode = accountOpeningClaim.getDepositTariffPlanCode();
			String clientTariffCode = accountOpeningClaim.getTarifPlanCodeType();
			String accountGroup = accountOpeningClaim.getAccountGroup();

			if (ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries())
			{
				Long depositProductGroup = StringHelper.isEmpty(accountGroup) ? 0L : Long.valueOf(accountGroup);
				DepositProductEntity depositEntity = depositProductService.findDepositEntity(depositProductType, depositProductGroup);
				if (depositEntity == null)
					throw new DocumentException("� ����������� ���������� ��������� ��� ������ ��� ������ � ������� " +
							depositProductType);

				depInfo = DepositProductHelper.calculateEntityRate(accountOpeningClaim.getOpeningDate(),
						accountOpeningClaim.getPeriod(), accountOpeningClaim.getDestinationCurrency().getCode(),
						accountOpeningClaim.getDestinationAmount().getDecimal(), accountOpeningClaim.isPension(),
						depositProductType, depositTariffPlanCode, clientTariffCode, accountOpeningClaim.getAccountGroup(),
						accountOpeningClaim.getSegment());
			}
			else
			{
				// ���� �������� ������
				DepositProduct depositProduct = depositProductService.findByProductId(depositProductType);
				if (depositProduct == null)
					throw new DocumentException(DEPOSIT_PRODUCT_NOT_FOUND + depositProductType);

				// ������������ ���������� ������, ����� ���� � ������ ������������ ���. ������
				Element description = XmlHelper.parse(depositProduct.getDescription()).getDocumentElement();
				depInfo = DepositProductHelper.calculateRate(accountOpeningClaim.getOpeningDate(),
						accountOpeningClaim.getPeriod(), accountOpeningClaim.getDestinationCurrency().getCode(),
						accountOpeningClaim.getDestinationAmount().getDecimal(), accountOpeningClaim.isPension(),
						description, depositTariffPlanCode, clientTariffCode, accountGroup,
						accountOpeningClaim.getSegment(), accountOpeningClaim.isPromoDepositProduct());

			}
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
		return depInfo;
	}

	private void calculateRate(AccountOpeningClaim accountOpeningClaim, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		Map<String, String> depInfo = getDepInfo(accountOpeningClaim);
		// �� ���������� ���������� �� ������ ���������� ������
		if (depInfo.containsKey(DepositProductHelper.CALC_RATE_ERROR))
		{
			stateMachineEvent.registerChangedField("destinationAmount");
			stateMachineEvent.addMessage(NOT_FOUND_RATE);
			return;
		}
		if (accountOpeningClaim.getState().getCode().equals("SAVED"))
		{
			try
			{
				BigDecimal calculateRate = NumericUtil.parseBigDecimal(depInfo.get(AccountOpeningClaim.INTEREST_RATE));
				BigDecimal claimRate = NumericUtil.parseBigDecimal(accountOpeningClaim.getAttribute(AccountOpeningClaim.INTEREST_RATE).getStringValue());
				if (claimRate.compareTo(calculateRate)!=0)
					stateMachineEvent.registerChangedField("interestRate");
			}
			catch (ParseException e)
			{
				throw new DocumentException(e);
			}
		}

		// ��������� ���� ������� ���������� �������
		for (String key : depInfo.keySet())
		{
			accountOpeningClaim.setTermsPosition(key, depInfo.get(key));
		}
	}

	private boolean checkDatesChanged(AccountOpeningClaim accountOpeningClaim)
	{
		Calendar closingDate = DateHelper.clearTime((Calendar) accountOpeningClaim.getOpeningDate().clone());
		Calendar currentDate = DateHelper.getCurrentDate();

		return closingDate.compareTo(currentDate) != 0;
	}
}
