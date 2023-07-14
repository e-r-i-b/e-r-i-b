package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.business.deposits.DepositEntityService;
import com.rssl.phizic.business.deposits.DepositEntityVisibilityService;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductHelper;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositsTDOG;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductRate;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * ���������, �� ���������� �� �������, �� ������� ����������� �����, � ������� �������� ������.
 * @author Pankin
 * @ created 25.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountOpeningChangeTermsHandler extends AccountOpeniningInitTermsHandler
{
	private static final String DEPOSIT_PRODUCT_NOT_FOUND = "� ����������� ���������� ��������� ��� ������ ��� ������ � ������� ";
	private static final String OPENING_TERMS_CHANGED = "������� ���������� ������� �� ���������� ������ ����������. ����������, �������������� ��� �������� ����� ������.";
	//������������� �� ������ ����� �� ��������
	private static final String DEPOSIT_CAPITALIZATION_CHANGED = "���� �������� ����� ������ ����������. ����������, ��������� � ���� ����� ������";
	//����� ��� ������������ ��������� �� ������ �������
	private static final String DEPOSIT_CARD_FOR_PERCENT_IS_CLOSED = "�������� �� ��������� ����� ��� ���������� ��������� ��������������. ����������, ������� ������ ����� ��� ���������� ���������.";
	private static final String INTEREST_DATE_BEGIN = "interestDateBegin";
	private static final String INTEREST_RATE = "interestRate";
	private static final String SEGMENT_CODE = "segmentCode";
	private static final String DATE_END_PATH = "/product/data/options/element[id='%d']/dateEnd";
	private static final ExternalResourceService resourceService = new ExternalResourceService();
	private static final DepositEntityVisibilityService DEPOSIT_ENTITY_VISIBILITY_SERVICE = new DepositEntityVisibilityService();
	private static final DepositEntityService depositEntityService = new DepositEntityService();
	private static final ClientPromoCodeService clientPromoCodeService = new ClientPromoCodeService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("�������� ��� ������� id=" + document.getId() + " (��������� AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
		Long depositProductType = accountOpeningClaim.getAccountType();

		if (accountOpeningClaim.isPension() && !PersonHelper.isPensioner())
			accountOpeningClaim.setPension(false);

		DepositProduct depositProduct = null;
		try
		{
			if (ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries())
			{
				String accountGroup = accountOpeningClaim.getAccountGroup();
				Long depositProductGroup = StringHelper.isEmpty(accountGroup) ? 0L : Long.valueOf(accountGroup);
				depositProductGroup = -22L == depositProductGroup ? 0L : depositProductGroup;
				DepositProductEntity depositEntity = depositProductService.findDepositEntity(depositProductType, depositProductGroup);
				if (depositEntity == null)
					throw new DocumentException(DEPOSIT_PRODUCT_NOT_FOUND + depositProductType);

				// ���������, �� ���������� �� ���������� ������. ���� ����� ���� ������ �������, �� ������ �� ����� ������� (���������� ������ ����������)
				if (checkInterestRateChanged(accountOpeningClaim, null))
					throw new DocumentLogicException(OPENING_TERMS_CHANGED);
			}
			else
			{
				depositProduct = depositProductService.findByProductId(depositProductType);

				if (depositProduct == null)
					throw new DocumentException(DEPOSIT_PRODUCT_NOT_FOUND + depositProductType);

				Element description = null;
				try
				{
					description = XmlHelper.parse(depositProduct.getDescription()).getDocumentElement();
				}
				catch (Exception e)
				{
					throw new DocumentException(e);
				}
				// ���� ���������� ���������� ������, ���� ���� ������ ������� ������� �����, ����������.
				if (checkInterestRateChanged(accountOpeningClaim, description) ||
						checkSubTypeExpired(accountOpeningClaim, description))
				{
					throw new DocumentLogicException(OPENING_TERMS_CHANGED);
				}
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

		// �������� ��������� �������
		if (checkTextTermsChanged(accountOpeningClaim) ||
				checkTemplateIdChanged(accountOpeningClaim) ||
				checkNameChanged(accountOpeningClaim))
		{
			throw new DocumentLogicException(OPENING_TERMS_CHANGED);
		}

		//���� ��� ������ ������� ������ ���������
		if (StringHelper.isNotEmpty(accountOpeningClaim.getPercentTransferSource()))
		{
			//�������� ����������� ������������� �� ������
			if (!checkCapitalization(depositProduct, depositProductType))
				throw new DocumentLogicException(DEPOSIT_CAPITALIZATION_CHANGED);

			//���� "����������� �������� �� ���� ���������� �����"
			if (accountOpeningClaim.getPercentTransferSource().equals("card"))
			{
				String cardCode = accountOpeningClaim.getPercentTransferCardSource();
				long cardLinkId = CardLink.getIdFromCode(cardCode);
				try
				{
					CardLink cardLink = resourceService.findLinkById(CardLink.class, cardLinkId);
					//�������� ��������� �����: ������ ���� ��������
					if (cardLink == null ||  !CardState.active.equals(cardLink.getCard().getCardState()))
						throw new DocumentLogicException(DEPOSIT_CARD_FOR_PERCENT_IS_CLOSED);
				}
				catch (BusinessException e)
				{
					throw new DocumentException(e);
				}
			}
		}

		//���������� ���������� �� ��, ��� � ��� ������������ ������ �������� �� �������� � ����������� ��������� � ����������
		if (PersonContext.isAvailable())
			updateCuratorInfo(accountOpeningClaim, getOfficeCode(accountOpeningClaim));
	}

	/**
	 * ���������, �������� �� ������ �������������.
	 * @param depositProduct - ��������� ������� (���������� � ���������� �������, �� 16.1). ���� null, ������, ������������ ���������� � ���������� �� ������������ ��� ���
	 * @param depositProductType - ��� ��������� ��������
	 * @return true, ���� ������ �������� �������������
	 * @throws DocumentException
	 */
	private boolean checkCapitalization(DepositProduct depositProduct, Long depositProductType) throws DocumentException
	{
		try
		{
			if (depositProduct == null)
				return DepositProductHelper.isAvailableCapitalizationByType(String.valueOf(depositProductType));
			return depositProduct.isCapitalization();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/*
	 �������� ��������� ����� ������� �������
	 */
	private boolean checkTextTermsChanged(AccountOpeningClaim accountOpeningClaim)
			throws DocumentException
	{
		try
		{
			Long accountGroup = StringHelper.isEmpty(accountOpeningClaim.getAccountGroup()) ? 0L : Long.valueOf(accountOpeningClaim.getAccountGroup());
			DepositsTDOG tdog = depositProductService.getDepositAdditionalInfo(accountOpeningClaim.getAccountType(), -22L == accountGroup ? 0L : accountGroup,
					accountOpeningClaim.isNeedInitialFee() ? null : accountOpeningClaim.getAccountSubType());
			if (tdog == null)
				return true;

			if (!StringHelper.equalsNullIgnore(tdog.getIncomingTransactions(), accountOpeningClaim.getTermsPosition(AccountOpeningClaim.INCOMING_TRANSACTIONS)))
				return true;
			if (!StringHelper.equalsNullIgnore(tdog.getAdditionalFeePeriod(), accountOpeningClaim.getTermsPosition(AccountOpeningClaim.FREQUENCY_ADD)))
				return true;
			if (!StringHelper.equalsNullIgnore(tdog.getDebitTransactions(), accountOpeningClaim.getTermsPosition(AccountOpeningClaim.DEBIT_TRANSACTIONS)))
				return true;
			if (!StringHelper.equalsNullIgnore(tdog.getFrequencyPercent(), accountOpeningClaim.getTermsPosition(AccountOpeningClaim.FREQUENCY_PERCENT)))
				return true;
			if (!StringHelper.equalsNullIgnore(tdog.getPercentOrder(), accountOpeningClaim.getTermsPosition(AccountOpeningClaim.PERCENT_ORDER)))
				return true;
			if (!StringHelper.equalsNullIgnore(tdog.getIncomeOrder(), accountOpeningClaim.getTermsPosition(AccountOpeningClaim.INCOME_ORDER)))
				return true;
			if (!StringHelper.equalsNullIgnore(tdog.getRenewals(), accountOpeningClaim.getTermsPosition(AccountOpeningClaim.RENEWALS)))
				return true;
			if (!StringHelper.equalsNullIgnore(tdog.getRate(), accountOpeningClaim.getTermsPosition(AccountOpeningClaim.PERCENT)))
				return true;

			return false;
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/*
	 �������� ������� ��������. ��� ���������� ������� ������� ������� �������������.
	 */
	private boolean checkTemplateIdChanged(AccountOpeningClaim accountOpeningClaim)
			throws DocumentException
	{
		try
		{
			Long savedTemplateId = accountOpeningClaim.getTermsTemplateId();
			Long actualTemplateId = depositProductService.findTemplateIdByType(accountOpeningClaim.getAccountType(), accountOpeningClaim.getAccountSubType(), 32L);
			return (savedTemplateId == null || !savedTemplateId.equals(actualTemplateId));
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/*
	 �������� �������� ������
	 */
	private boolean checkNameChanged(AccountOpeningClaim accountOpeningClaim)
			throws DocumentException
	{
		try
		{
			String claimName = accountOpeningClaim.getDepositName();
			String dictionaryName = null;
			Long accountGroup = StringHelper.isEmpty(accountOpeningClaim.getAccountGroup()) ? 0L : Long.valueOf(accountOpeningClaim.getAccountGroup());
			if (Long.valueOf(0).equals(accountGroup) || Long.valueOf(-22).equals(accountGroup))
			{
				dictionaryName = DEPOSIT_ENTITY_VISIBILITY_SERVICE.getDepositEntityName(accountOpeningClaim.getAccountType());
			}
			else
			{
				dictionaryName = depositProductService.findDepositNameByGroupCode(Long.valueOf(accountGroup));
			}

			return !StringHelper.equalsNullIgnore(claimName, dictionaryName);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private static class DepositRateDescription
	{
		private Calendar interestDateBegin;
		private String rate;
		private String segmentCode;
	}

	/*
     �������� ���������� ������
	 */
	private boolean checkInterestRateChanged(AccountOpeningClaim accountOpeningClaim, Element description)
			throws DocumentException
	{
		if (!accountOpeningClaim.isNeedInitialFee())
			return false;

		final List<DepositRateDescription> rateDescriptions = new ArrayList<DepositRateDescription>();
		try
		{
			Long type = accountOpeningClaim.getAccountType();
			Long subType = accountOpeningClaim.getAccountSubType();
			Calendar openingDate = accountOpeningClaim.getOpeningDate();
			String currencyCode = accountOpeningClaim.getCurrency().getCode();
			Boolean pension = accountOpeningClaim.isPension();
			String depositTariffPlanCode = accountOpeningClaim.getDepositTariffPlanCode();
			String clientTariffPlanCode = accountOpeningClaim.getTarifPlanCodeType();
			String depositGroup = accountOpeningClaim.getAccountGroup();
			String segmentCode = accountOpeningClaim.getSegment();
			Boolean isPromoDeposit = accountOpeningClaim.isPromoDepositProduct();
			String minBalance = accountOpeningClaim.getAttribute(AccountOpeningClaim.MIN_DEPOSIT_BALANCE).getStringValue();

			if (ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries())
			{
				List<PromoCodeDeposit> actualClientPromoCodes = new ArrayList<PromoCodeDeposit>();
				ClientPromoCode clientPromoCode = clientPromoCodeService.getPromoCodeBySegment(segmentCode);
				if (clientPromoCode != null)
					actualClientPromoCodes.add(clientPromoCode.getPromoCodeDeposit());

				DepositProductEntity depositProductEntity = depositEntityService.getDepositProductEntity(type, Long.valueOf(depositGroup), actualClientPromoCodes, TariffPlanHelper.getDictionaryTariffCodes(), pension, Long.valueOf(clientTariffPlanCode), false, null);

				Map<BigDecimal, Map<String, List<DepositProductRate>>> currencyRates = depositProductEntity.getDepositRates().get(currencyCode);
				for (BigDecimal balance : currencyRates.keySet())
				{
					Map<String, List<DepositProductRate>> periodRates = currencyRates.get(balance);
					for (List<DepositProductRate> rates : periodRates.values())
					{
						for (DepositProductRate rate : rates)
						{
							if (rate.getDepositSubType().equals(subType) && rate.getSumBegin().compareTo(new BigDecimal(minBalance)) == 0)
							{
								DepositRateDescription rateDescription = new DepositRateDescription();
								rateDescription.interestDateBegin = rate.getDateBegin();
								rateDescription.rate = String.valueOf(rate.getBaseRate());
								rateDescription.segmentCode = String.valueOf(rate.getSegment());
								rateDescriptions.add(rateDescription);
							}
						}
					}
				}
			}
			else
			{
				String path = DepositProductHelper.getElementPath(description, openingDate, currencyCode, pension, depositTariffPlanCode, clientTariffPlanCode, depositGroup, segmentCode, isPromoDeposit, minBalance, subType);

				// �������� ��� "���� ������ �������� - ���������� ������" ��� ���������� ������� ������
				XmlHelper.foreach(description, path,
						new ForeachElementAction()
						{

							public void execute(Element element) throws Exception
							{
								DepositRateDescription rateDescription = new DepositRateDescription();
								Calendar startDate = DateHelper.toCalendar(
										getDateFormat().parse(XmlHelper.getSimpleElementValue(element, INTEREST_DATE_BEGIN)));
								rateDescription.interestDateBegin = startDate;
								rateDescription.rate = XmlHelper.getSimpleElementValue(element, INTEREST_RATE);
								rateDescription.segmentCode = XmlHelper.getSimpleElementValue(element, SEGMENT_CODE);
								rateDescriptions.add(rateDescription);
							}
						});
			}
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}

		// ���� ������ ���, �������, ��� ���������� ������ ����������
		if (rateDescriptions.isEmpty())
			return true;

		Collections.sort(rateDescriptions, new Comparator<DepositRateDescription>()
		{
			public int compare(DepositRateDescription o1, DepositRateDescription o2)
			{
				int result = DepositProductHelper.isPriorSegment(o2.segmentCode).compareTo(DepositProductHelper.isPriorSegment(o1.segmentCode));
				if (result != 0)
					return result;
				return o2.interestDateBegin.compareTo(o1.interestDateBegin);
			}
		});

		String claimInterestRate = accountOpeningClaim.getAttribute(AccountOpeningClaim.INTEREST_RATE).getStringValue();
		String dictionaryInterestRate = rateDescriptions.get(0).rate;

		// ���� ���������� ������ ��� � ������ ��� �����������, �������, ��� ����������
		if (StringHelper.isEmpty(claimInterestRate) || StringHelper.isEmpty(dictionaryInterestRate))
			return true;

		try
		{
			return NumericUtil.parseBigDecimal(claimInterestRate).compareTo(NumericUtil.parseBigDecimal(dictionaryInterestRate)) != 0;
		}
		catch (ParseException ignored)
		{
			// ���� �������� ���������� ������ �����������, �������, ��� ����������.
			return true;
		}
	}

	/*
	 �������� ���� ��������� ������ ���������� ������� ������
	 */
	private boolean checkSubTypeExpired(AccountOpeningClaim accountOpeningClaim, Element description)
			throws DocumentException
	{
		Calendar currentDate = DateHelper.getCurrentDate();
		Calendar endDate = null;
		try
		{
			endDate = DateHelper.toCalendar(getDateFormat().parse(
					XmlHelper.getElementValueByPath(description,
							String.format(DATE_END_PATH, accountOpeningClaim.getAccountSubType()))));
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}

		if (currentDate.before(endDate))
		{
			return false;
		}
		return true;
	}
}
