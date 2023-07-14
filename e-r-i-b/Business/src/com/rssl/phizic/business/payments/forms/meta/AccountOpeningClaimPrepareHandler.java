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
 * Проверяет, есть ли нарушения по договору закрываемого вклада
 * @author Pankin
 * @ created 30.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountOpeningClaimPrepareHandler extends PrepareDocumentHandler
{
	private static final String WARNING_MESSAGE = "Обратите внимание! Изменились значения выделенных полей.";
	private static final String ERROR_MESSAGE = "Изменился курс конверсии банка.";
	private static final String CHARGE_OFF_AMOUNT_MESSAGE = "Была пересчитана сумма списания средств со вклада.";
	private static final String DESTINATION_AMOUNT_MESSAGE = "Была пересчитана сумма зачисления средств на вклад.";
	
	private static final String DEPOSIT_PRODUCT_NOT_FOUND = "В справочнике депозитных продуктов нет записи для вклада с номером ";
	private static final String LESS_SUM_MESSAGE = "Рассчитанная сумма для зачисления - %s Сумма для " +
			"зачисления не может быть меньше минимального остатка для выбранного вклада. Внимательно " +
			"ознакомьтесь с условиями вклада, который хотите открыть.";

	private static final String NOT_FOUND_RATE = "На введенных Вами условиях невозможно открыть вклад. " +
			"Пожалуйста, внимательно ознакомьтесь с условиями вклада, который хотите открыть.";

	private static final String CALCULATE_AMOUNT_MESSAGE = "Рассчитана сумма остатка на вкладе при закрытии вклада с учетом процентов и комиссий.";

	private static final DepositProductService depositProductService = new DepositProductService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	protected boolean checkDatesChanged(StateObject document) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + document.getId()
					+ " (Ожидается AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		// Повторный запрос расчета суммы надо делать при изменении даты или при изменении курса конверсии
		// Пересчет курса конверсии должен идти первым
		return accountOpeningClaim.calcConvertionRates() || checkDatesChanged(accountOpeningClaim);
	}

	protected void recalculateDates(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + document.getId()
					+ " (Ожидается AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		if (accountOpeningClaim.isRateChanged())
		{
			stateMachineEvent.registerChangedField("confirmCourse");
			stateMachineEvent.addErrorMessage(ERROR_MESSAGE);
		}

		// Если дата не менялась, пересчитывать не нужно
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
	 * Проверка: достаточно ли средств на закрываемом вкладе для открытия нового
	 * @param document
	 * @param stateMachineEvent
	 * @return true, если достаточно
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 */
	private boolean isEnoughMoney(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
		// Для вклада без неснижаемого остатка
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
	 * Подсветка полей в случае невозможности открыть счет из-за недостатка средств
	 * @param accountOpeningClaim документ
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
	 * Сформировать строку, содержащую количество и валюту денежных средств в поле суммы зачисления
	 * @param accountOpeningClaim открываемый вклад
	 * @return строка с указанием количества и валюты суммы зачисления
	 * @throws DocumentException
	 */
	private String createSumString(AccountOpeningClaim accountOpeningClaim) throws DocumentException
	{
		String currencySign = CurrencyUtils.getCurrencySign(getDestinationCurrencyCode(accountOpeningClaim));
		String addPoint = (currencySign.equals("руб.")) ? "" : ".";
		return accountOpeningClaim.getDestinationAmount().getDecimal().toString() + " " + currencySign + addPoint;
	}

	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + document.getId() + " (Ожидается AccountClosingPayment)");
		}
		AccountClosingPayment payment = (AccountClosingPayment) document;
		String code = document.getState().getCode();

		if ("INITIAL".equals(code))
		{
			// проверяем, достаточно ли средств для открытия счета
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
		 * Если клиент пытается подтвердить заявку на закрытие вклада заполненную вчера, посылается запрос
		 * на предварительный расчет суммы списания и зачисления, и, в случае их несовпадения выдается сообщение
		 * об изменеиях.
		 *
		 */
		if ("SAVED".equals(code))
		{
			// проверяем, достаточно ли средств для открытия счета
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
			agreementViolation += " \"Подтвердить\".";
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
			throw new DocumentException("Неверный тип платежа id=" + document.getId()
					+ " (Ожидается AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		if (!accountOpeningClaim.isWithMinimumBalance())
		{
			// Если вклад без неснижаемого остатка, нужно рассчитать процентную ставку
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
				// Обновляем документ в базе, чтобы не потерять пересчитанные данные
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
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");

			String depositTariffPlanCode = accountOpeningClaim.getDepositTariffPlanCode();
			String clientTariffCode = accountOpeningClaim.getTarifPlanCodeType();
			String accountGroup = accountOpeningClaim.getAccountGroup();

			if (ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries())
			{
				Long depositProductGroup = StringHelper.isEmpty(accountGroup) ? 0L : Long.valueOf(accountGroup);
				DepositProductEntity depositEntity = depositProductService.findDepositEntity(depositProductType, depositProductGroup);
				if (depositEntity == null)
					throw new DocumentException("В справочнике депозитных продуктов нет записи для вклада с номером " +
							depositProductType);

				depInfo = DepositProductHelper.calculateEntityRate(accountOpeningClaim.getOpeningDate(),
						accountOpeningClaim.getPeriod(), accountOpeningClaim.getDestinationCurrency().getCode(),
						accountOpeningClaim.getDestinationAmount().getDecimal(), accountOpeningClaim.isPension(),
						depositProductType, depositTariffPlanCode, clientTariffCode, accountOpeningClaim.getAccountGroup(),
						accountOpeningClaim.getSegment());
			}
			else
			{
				// Ищем описание вклада
				DepositProduct depositProduct = depositProductService.findByProductId(depositProductType);
				if (depositProduct == null)
					throw new DocumentException(DEPOSIT_PRODUCT_NOT_FOUND + depositProductType);

				// Рассчитываем процентную ставку, номер вида и размер минимального доп. взноса
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
		// По переданным параметрам не найден подходящий подвид
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

		// Заполняем поля платежа найденными данными
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
