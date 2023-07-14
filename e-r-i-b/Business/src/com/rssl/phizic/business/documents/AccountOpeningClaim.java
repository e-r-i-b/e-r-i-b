package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.claims.AccountOpeningFromCardClaim;
import com.rssl.phizic.gate.claims.DemandAccountOpeningClaim;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;

/**
 * @ author: filimonova
 * @ created: 03.02.2011
 * @ $Author$
 * @ $Revision$
 *
 * Открытие вклада переводом со счета\карты
 */
public class AccountOpeningClaim extends ExchangeCurrencyTransferBase implements com.rssl.phizic.gate.claims.AccountOpeningClaim, AccountOpeningFromCardClaim, DemandAccountOpeningClaim
{
	public static final String DEPOSIT_OPENING_DATE = "open-date";
	public static final String DEPOSIT_CLOSING_DATE = "closing-date";
	public static final String DEPOSIT_PERIOD_DAYS = "period-days";
	public static final String DEPOSIT_PERIOD_MONTHS = "period-months";
	public static final String DEPOSIT_PERIOD_YEARS = "period-years";
	private static final String DEPOSIT_ID = "deposit-id";
	public static final String DEPOSIT_TYPE = "deposit-type";
	public static final String DEPOSIT_SUB_TYPE = "deposit-sub-type";
	public static final String DEPOSIT_GROUP = "deposit-group";
	public static final String DEPOSIT_NAME = "deposit-name";
	public static final String NEED_INITIAL_FEE = "need-initial-fee";
	public static final String TEMPLATE_ID = "template-id";
	public static final String TARIFF_TEMPLATE_ID = "tariff-template-id";
	public static final String INCOMING_TRANSACTIONS = "incoming-transactions";
	public static final String FREQUENCY_ADD = "frequency-add";
	public static final String DEBIT_TRANSACTIONS = "debit-transactions";
	public static final String FREQUENCY_PERCENT = "frequency-percent";
	public static final String PERCENT_ORDER = "percent-order";
	public static final String INCOME_ORDER = "income-order";
	public static final String RENEWALS = "renewals";
	public static final String PERCENT = "percent";
	public static final String INTEREST_RATE = "interest-rate";
	public static final String MIN_ADDITIONAL_FEE = "min-additional-fee";
	public static final String MIN_DEPOSIT_BALANCE = "min-deposit-balance";
	public static final String WITH_MINIMUM_BALANCE = "with-minimum-balance";
	public static final String IS_PENSION = "pension";
	public static final String AGREEMENT_VIOLATION = "agreement-violation";
	public static final String LONG_OFFERT = "long-offert";
	private static final String CODE_ACCOUNT = "code-account";
	public static final String LOGIN_CARD_HASH = "login-card-hash";
	public static final String ACCOUNT_TB = "region";
	public static final String ACCOUNT_OSB = "branch";
	public static final String ACCOUNT_VSP = "officeVSP";

	//Тип куратора: Промоутер, клиентский менеджер (КМ)
	public static final String CURATOR_TYPE = "curator-type";
	public static final String CURATOR_ID = "curator-id";
	public static final String CURATOR_TB = "curator-tb";
	public static final String CURATOR_OSB = "curator-osb";
	public static final String CURATOR_VSP = "curator-vsp";
	public static final String CLAIM_ERROR_MSG = "claim-error-msg";

    public static final String ATM_PLACE = "atm-place";
    public static final String ATM_TB = "atm-tb";
    public static final String ATM_OSB = "atm-osb";
    public static final String ATM_VSP = "atm-vsp";
	public static final String CREATE_MONEY_BOX_FIELD = "create-money-box";

	private static final String PFP_ID = "pfpId";
	private static final String PFP_PORTFOLIO_ID = "pfpPortfolioId";
	private static final String FROM_PERSONAL_FINANCE = "from-personal-finance";
	private static final String PERCENT_TRANSFER_SOURCE = "percentTransfer-source";
	private static final String PERCENT_CARD_SOURCE = "percentCard-source";
	private static final String PERCENT_CARD_NUMBER = "percentCard-number";
	//Данные для отчета
	//ИД промоутера
	private static final String PROMOTER_ID = "promoter-id";
	//Карта под которой вошел клиент
	private static final String LOGON_CARD_NUMBER = "logon-card-number";
	//Код тарифного плана вклада с учетом наличия тарифов по вкладу для тарифного плана клиента
	private static final String DEPOSIT_TARIFF_PLAN_CODE = "deposit-tariff-plan-code";
	private static final String CLIENT_SEGMENT = "segment";
	private static final String PROMO_CODE_NAME = "promo-code-name";
	private static final String USE_PROMO_RATE = "use-promo-rate";
	private static final String FROM_EXTENDED_LOAN_CLAIM = "from-extended-loan-claim";

	private ClientDocumentImpl mainDocument;

	private boolean incrementedPromoCode = false;

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		setOpeningDate(getClientCreationDate());
	}

	public Calendar getClosingDate()
	{
		String closingDate = getNullSaveAttributeStringValue(DEPOSIT_CLOSING_DATE);
		try{
			Calendar calendar = Calendar.getInstance();
			if (StringHelper.isEmpty(closingDate))
				return null;
			calendar.setTime(getDateFormat().parse(closingDate));
			return calendar;
		}catch (ParseException ignored)
		{
			return null;
		}
	}

	public DateSpan getPeriod()
	{
		String days = getNullSaveAttributeStringValue(DEPOSIT_PERIOD_DAYS);
		String months = getNullSaveAttributeStringValue(DEPOSIT_PERIOD_MONTHS);
		String years = getNullSaveAttributeStringValue(DEPOSIT_PERIOD_YEARS);

		return new DateSpan(Integer.parseInt(years), Integer.parseInt(months), Integer.parseInt(days));
	}

	public Currency getCurrency()
	{
		try
		{
			return getDestinationCurrency();
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public Long getAccountId()
	{
		return Long.parseLong(getNullSaveAttributeStringValue(DEPOSIT_ID));
	}

	public Long getAccountType()
	{
		return Long.parseLong(getNullSaveAttributeStringValue(DEPOSIT_TYPE));
	}

	public Long getAccountSubType()
	{
		return getNullSaveAttributeLongValue(DEPOSIT_SUB_TYPE);
	}

	public String getAccountGroup()
	{
		return getNullSaveAttributeStringValue(DEPOSIT_GROUP);
	}

	public void setAccountGroup(String accountGroup)
	{
		setNullSaveAttributeStringValue(DEPOSIT_GROUP, accountGroup);
	}

	public boolean isWithClose()
	{
		return !getFormName().equals(FormConstants.ACCOUNT_OPENING_CLAIM_FORM);
	}

	public BigDecimal getIrreducibleAmmount()
	{
		// Если вклад без неснижаемого остатка, возвращаем null.
		if (!isWithMinimumBalance())
			return null;

		return (BigDecimal) getNullSaveAttributeValue(MIN_DEPOSIT_BALANCE);
	}

	public String getAgreementViolation()
	{
		return getNullSaveAttributeStringValue(AGREEMENT_VIOLATION);
	}

	public void setAgreementViolation(String violationText)
	{
		setNullSaveAttributeStringValue(AGREEMENT_VIOLATION, violationText);
	}

	public boolean getTariffClosingMsg()
	{
		return false;
	}

	public void setTariffClosingMsg(boolean isTariff)
	{
	}

	public String getLongOffertFormalized()
	{
		return getNullSaveAttributeStringValue(LONG_OFFERT);
	}

	public void setLongOffertFormalized(String longOffertFormalized)
	{
		setNullSaveAttributeStringValue(LONG_OFFERT, longOffertFormalized);
	}

	public Calendar getOpeningDate()
	{
		String openingDate = getNullSaveAttributeStringValue(DEPOSIT_OPENING_DATE);
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDateFormat().parse(openingDate));
			return calendar;
		}catch (ParseException e)
		{
			return null;
		}
	}

	public void setOpeningDate(Calendar openingDate)
	{
		setNullSaveAttributeStringValue(DEPOSIT_OPENING_DATE, getDateFormat().format(openingDate.getTime()));
	}

	public void setClosingDate(Calendar closingDate)
	{
		setNullSaveAttributeStringValue(DEPOSIT_CLOSING_DATE, getDateFormat().format(closingDate.getTime()));
	}

	public Class<? extends GateDocument> getType()
	{
		if (!isNeedInitialFee())
		{
			return DemandAccountOpeningClaim.class;
		}
		ResourceType chargeOffResourceType = getChargeOffResourceType();
		if (chargeOffResourceType == ResourceType.NULL)
		{
			return null;
		}

		if (chargeOffResourceType == ResourceType.CARD)
		{
			return AccountOpeningFromCardClaim.class;
		}
		if (chargeOffResourceType == ResourceType.ACCOUNT)
		{
			return com.rssl.phizic.gate.claims.AccountOpeningClaim.class;
		}

		throw new IllegalStateException("Невозмжно определить тип документа");
	}

	protected boolean needRates() throws DocumentException
	{
		return getChargeOffResourceType()==ResourceType.ACCOUNT && isConvertion();
	}

	protected boolean needSumUpdate()
	{
		return !isWithClose() || getState().getCode().equals("INITIAL");
	}

	public Document convertToDom() throws DocumentException
	{
		Document document = super.convertToDom();
		Element root = document.getDocumentElement();
		appendNullSaveString(root, CODE_ACCOUNT, getChargeOffResourceLink() == null ? null : getChargeOffResourceLink().getCode());
		appendNullSaveDate(root, DEPOSIT_OPENING_DATE, getDocumentDate());
		return document;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		Element root = document.getDocumentElement();
		setOperationDate(getDateFromDom(root, DEPOSIT_OPENING_DATE));
	}

	/**
	 * Обновить информацию о средствах списания/зачисления для вклада с закрытием.
	 * Требуется для проверки наличия достаточного количества средств для открытия вклада до отправки документа в шину
	 * @param document
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 */
	public void storeAmountInfo(Document document) throws DocumentLogicException, DocumentException
	{
		try
		{
			if (getChargeOffResourceLink() != null && isWithClose())
			{
				Money amount = ((Account) getChargeOffResourceLink().getValue()).getBalance();
				setChargeOffAmount(amount);
				if (!isConvertion())
					setDestinationAmount(amount);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	protected void storeCardsInfo(ResourceType chargeOffResourceType, ResourceType destinationResourceTyp, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		if (chargeOffResourceType == ResourceType.CARD)
		{
			try
			{
				//Сохраняем инфу о сроке действия
				setChargeOffCardExpireDate(getChargeOffCardExpireDate(chargeOffResourceType, messageCollector));
			}
			catch (DocumentLogicException e)
			{
				if(updateState != InnerUpdateState.INIT)
					throw e;
				// при инициализации идем дальше, обновим нормальными данными в свое время
				log.warn(e);
			}
		}
	}

	/**
	 * @return тип открытого вклада
	 */
	public String getDepositName()
	{
		return getNullSaveAttributeStringValue(DEPOSIT_NAME);
	}

	/**
	 * Установить признак, определяющий с начальным взносом или без него вклад
	 * @param needInitialFee true - вклад с первоначальным взносом
	 */
	public void setNeedInitialFee(boolean needInitialFee)
	{
		setNullSaveAttributeBooleanValue(NEED_INITIAL_FEE, needInitialFee);
	}

	/**
	 * @return признак, определяющий с начальным взносом или без него вклад
	 */
	public boolean isNeedInitialFee()
	{
		return (Boolean) getAttribute(NEED_INITIAL_FEE).getValue();
	}

	/**
	 * Установить признак, определяющий есть ли неснижаемый остаток у вклада
	 * @param withMinimumBalance true - вклад с неснижаемым остатком
	 */
	public void setWithMinimumBalance(boolean withMinimumBalance)
	{
		setNullSaveAttributeBooleanValue(WITH_MINIMUM_BALANCE, withMinimumBalance);
	}

	/**
	 * @return признак, определяющий есть ли неснижаемый остаток у вклада
	 */
	public boolean isWithMinimumBalance()
	{
		return (Boolean) getAttribute(WITH_MINIMUM_BALANCE).getValue();
	}

	/**
	 * Установить значение захешированного номера карты
	 * @param loginCardHash - захешированный функцией SHA-1 номер карты
	 */
	public void setLoginCardHash(String loginCardHash)
	{
		setNullSaveAttributeStringValue(LOGIN_CARD_HASH, loginCardHash);
	}

	/**
	 * @return захешированный функцией SHA-1 номер карты
	 */
	public String getLoginCardHash()
	{
		return (String) getAttribute(LOGIN_CARD_HASH).getValue();
	}

	/**
	 * Установить признак, определяющий, является ли пенсионером владелец заявки
	 * @param pension true - пенсионер
	 */
	public void setPension(boolean pension)
	{
		setNullSaveAttributeBooleanValue(IS_PENSION, pension);
	}

	/**
	 * @return признак, определяющий, является ли пенсионером владелец заявки
	 */
	public boolean isPension()
	{
		return (Boolean) getAttribute(IS_PENSION).getValue();
	}

	/**
	 * @return идентификатор шаблона договора
	 */
	public Long getTermsTemplateId()
	{
		String templateId = getNullSaveAttributeStringValue(TEMPLATE_ID);
		return StringHelper.isEmpty(templateId) ? null : Long.parseLong(templateId);
	}

	/**
	 * установить идентификатор шаблона договора
	 * @param templateId идентификатор шаблона договора
	 */
	public void setTermsTemplateId(String templateId)
	{
		setNullSaveAttributeStringValue(TEMPLATE_ID, templateId);
	}


	/**
	 * @return идентификатор шаблона договора для тарифного плана
	 */
	public Long getTariffTermsTemplateId()
	{
		String templateId = getNullSaveAttributeStringValue(TARIFF_TEMPLATE_ID);
		return StringHelper.isEmpty(templateId) ? null : Long.parseLong(templateId);
	}

	/**
	 * установить идентификатор шаблона договора для тарифного плана
	 * @param templateId идентификатор шаблона договора для тарифного плана
	 */
	public void setTariffTermsTemplateId(String templateId)
	{
		setNullSaveAttributeStringValue(TARIFF_TEMPLATE_ID, templateId);
	}

	/**
	 * Получить текст условия размещения средств во вклад по имени
	 * @param name имя соответствующего аттрибута
	 * @return текст условия либо null
	 */
	public String getTermsPosition(String name)
	{
		return getNullSaveAttributeStringValue(name);
	}

	/**
	 * Установить текст условия размещения средств во вклад по имени
	 * @param name имя соответствующего аттрибута
	 * @param value текст условия
	 */
	public void setTermsPosition(String name, String value)
	{
		setNullSaveAttributeStringValue(name, value);
	}

	/**
	 * Установить значение тербанка
	 * @param tb - тербанк
	 */
	public void setAccountTb(String tb)
	{
		setNullSaveAttributeStringValue(ACCOUNT_TB, tb);
	}

	/**
	 * @return Тербанк (ТБ) в котором нужно открыть счет
	 */
	public String getAccountTb()
	{
		return getNullSaveAttributeStringValue(ACCOUNT_TB);
	}

	/**
	 * Установить значение ОСБ
	 * @param osb - ОСБ
	 */
	public void setAccountOsb(String osb)
	{
		setNullSaveAttributeStringValue(ACCOUNT_OSB, osb);
	}

	/**
	 * @return Филиал банка (ОСБ), в котором нужно открыть вклад
	 */
	public String getAccountOsb()
	{
		return getNullSaveAttributeStringValue(ACCOUNT_OSB);
	}

	/**
	 * Установить значение ВСП
	 * @param vsp - ВСП
	 */
	public void setAccountVsp(String vsp)
	{
		setNullSaveAttributeStringValue(ACCOUNT_VSP, vsp);
	}

	/**
	 * @return Отделение филиала банка (ВСП), в котором нужно открыть вклад
	 */
	public String getAccountVsp()
	{
		return getNullSaveAttributeStringValue(ACCOUNT_VSP);
	}

	public String getOperUId()
	{
		return getNullSaveAttributeStringValue(OPER_UID);
	}

	/**
	 * Установка идентификатора операции
	 * @param operUid
	 */
	public void setOperUId(String operUid)
	{
		setNullSaveAttributeStringValue(OPER_UID, operUid);
	}

	public Calendar getOperTime()
	{
		return getNullSaveAttributeCalendarValue(OPER_TIME);
	}

	/**
	 * Установка даты и времени передачи сообщения
	 * @param operTime
	 */
	public void setOperTime(Calendar operTime)
	{
		setNullSaveAttributeCalendarValue(OPER_TIME, operTime);
	}

	/**
	 * @return идентификатор ПФП
	 */
	public Long getPfpId()
	{
		return (Long)getNullSaveAttributeValue(PFP_ID);
	}

	/**
	 * @return идентификатор портфеля из ПФП
	 */
	public Long getPfpPortfolioId()
	{
		return (Long)getNullSaveAttributeValue(PFP_PORTFOLIO_ID);
	}

	/**
	 * @return true - заявка на открытие вклада оформлена в АЛФ
	 */
	public Boolean getFromPersonalFinance()
	{
		return Boolean.valueOf(getNullSaveAttributeStringValue(FROM_PERSONAL_FINANCE));
	}

	/**
	 * @return - порядок уплаты процентов (на карту "card" на счет вклада "account")
	 */
	public String getPercentTransferSource()
	{
		return getNullSaveAttributeStringValue(PERCENT_TRANSFER_SOURCE);
	}

	/**
	 * @return - Код карты для перечисления процентов по вкладу
	 */
	public String getPercentTransferCardSource()
	{
		return getNullSaveAttributeStringValue(PERCENT_CARD_SOURCE);
	}

	/**
	 * @return - Номер карты для перечисления процентов по вкладу
	 */
	public String getPercentCardNumber()
	{
		return getNullSaveAttributeStringValue(PERCENT_CARD_NUMBER);
	}

	public void setPercentCardNumber(String cardNumber)
	{
		setNullSaveAttributeStringValue(PERCENT_CARD_NUMBER, cardNumber);
	}

	/**
	 * @return - тип: "Промоутер" или "КМ"
	 */
	public String getCuratorType()
	{
		return getNullSaveAttributeStringValue(CURATOR_TYPE);
	}

	public void setCuratorType(String curatorType)
	{
		setNullSaveAttributeStringValue(CURATOR_TYPE, curatorType);
	}

	/**
	 * @return - ИД промоутера, клиентского менеджера или УС
	 */
	public String getCuratorId()
	{
		return getNullSaveAttributeStringValue(CURATOR_ID);
	}

	public void setCuratorId(String curatorId)
	{
		setNullSaveAttributeStringValue(CURATOR_ID, curatorId);
	}

	/**
	 * @return - TБ менеджера или промоутера
	 */
	public String getCuratorTb()
	{
		return getNullSaveAttributeStringValue(CURATOR_TB);
	}

	public void setCuratorTb(String curatorTb)
	{
		setNullSaveAttributeStringValue(CURATOR_TB, curatorTb);
	}

	/**
	 * @return - ОСБ менеджера или промоутера
	 */
	public String getCuratorOsb()
	{
		return getNullSaveAttributeStringValue(CURATOR_OSB);
	}

	public void setCuratorOsb(String curatorOsb)
	{
		setNullSaveAttributeStringValue(CURATOR_OSB, curatorOsb);
	}

	/**
	 * @return - ВСП менеджера или промоутера
	 */
	public String getCuratorVsp()
	{
		return getNullSaveAttributeStringValue(CURATOR_VSP);
	}

	public void setCuratorVsp(String curatorVsp)
	{
		setNullSaveAttributeStringValue(CURATOR_VSP, curatorVsp);
	}

    /**
     * @return - Место установки устройства
     */
    public String getAtmPlace() {
        return getNullSaveAttributeStringValue(ATM_PLACE);
    }

    public void setAtmPlace(String atmPlace) {
        setNullSaveAttributeStringValue(ATM_PLACE, atmPlace);
    }

    /**
     * @return - Номер ТБ, где установлено устройство
     */
    public String getAtmTB() {
        return getNullSaveAttributeStringValue(ATM_TB);
    }

    public void setAtmTB(String atmTB) {
        setNullSaveAttributeStringValue(ATM_TB, atmTB);
    }

    /**
     * @return - Номер ОСБ, где установлено устройство
     */
    public String getAtmOSB() {
        return getNullSaveAttributeStringValue(ATM_OSB);
    }

    public void setAtmOSB(String atmOSB) {
        setNullSaveAttributeStringValue(ATM_OSB, atmOSB);
    }

    /**
     * @куегкт - Номер ВСП, где установлено устройство
     */
    public String getAtmVSP() {
        return getNullSaveAttributeStringValue(ATM_VSP);
    }

    public void setAtmVSP(String atmOSB) {
        setNullSaveAttributeStringValue(ATM_VSP, atmOSB);
    }

	/**
	 * @return код сегмента клиента
	 */
	public String getSegment() {
		return getNullSaveAttributeStringValue(CLIENT_SEGMENT);
	}

	public void setSegment(String segment) {
		setNullSaveAttributeStringValue(CLIENT_SEGMENT, segment);
	}

	/**
	 * @return код сегмента клиента
	 */
	public Boolean isPromoDepositProduct() {
		String promoCodeName = getNullSaveAttributeStringValue(PROMO_CODE_NAME);
		return StringHelper.isEmpty(promoCodeName) && StringHelper.isNotEmpty(getSegment()) && isUsePromoRate() &&
				!StringHelper.equalsNullIgnore(getSegment(), "0") && !StringHelper.equalsNullIgnore(getSegment(), "1");
	}

	/**
	 * @return - Сообщение об ошибке (при формировании заявки или ответ из шины) на открытие счета
	 */
	public String getClaimErrorMsg()
	{
		return getNullSaveAttributeStringValue(CLAIM_ERROR_MSG);
	}

	public void setClaimErrorMsg(String claimErrorMsg)
	{
		setNullSaveAttributeStringValue(CLAIM_ERROR_MSG, claimErrorMsg);
	}

	public String getChangePercentDestinationAccountNumber()
	{
		return getReceiverAccount();
	}

	/**
	 * Установка ИД промоутера
	 * @param promoterId
	 */
	public void setPromoterId(String promoterId)
	{
		setNullSaveAttributeStringValue(PROMOTER_ID, promoterId);
	}
	/**
	 * Установка номера карты под которой вощел клиент
	 * @param cardNumber
	 */
	public void setLogonCardNumber(String cardNumber)
	{
		setNullSaveAttributeStringValue(LOGON_CARD_NUMBER, cardNumber);
	}

	public String getDepositTariffPlanCode() {
		return getNullSaveAttributeStringValue(DEPOSIT_TARIFF_PLAN_CODE);
	}

	public void setDepositTariffPlanCode(String depositTariffPlanCode)
	{
		setNullSaveAttributeStringValue(DEPOSIT_TARIFF_PLAN_CODE, depositTariffPlanCode);
	}

	public Boolean getCreateMoneyBox()
	{
		return getNullSaveAttributeBooleanValue(CREATE_MONEY_BOX_FIELD);
	}

	public SumType getMoneyBoxSumType()
	{
		return getNullSaveAttributeEnumValue(SumType.class, "money-box-sum-type");
	}

	public String getMoneyBoxName()
	{
		return getNullSaveAttributeStringValue("money-box-name");
	}

	public Integer getMoneyBoxPercent()
	{
		return getNullSaveAttributeIntegerValue("long-offer-percent");
	}

	public String getMoneyBoxSellAmount()
	{
		return getNullSaveAttributeStringValue("money-box-sell-amount");
	}

	public String getMoneyBoxStartDate()
	{
		return getNullSaveAttributeStringValue("money-box-long-offer-start-date");
	}

	public ExecutionEventType getMoneyBoxEventType()
	{
		return getNullSaveAttributeEnumValue(ExecutionEventType.class, "long-offer-event-type");
	}

	public String getMoneyBoxFromResourceCode()
	{
		return getNullSaveAttributeStringValue("money-box-from-resource");
	}

	public boolean isUsePromoRate()
	{
		return (Boolean) getAttribute(USE_PROMO_RATE).getValue();
	}



	public ClientDocument getMainDocument()
	{
		return mainDocument;
	}

	/**
	 * Устанавливает главный документ
	 * @param mainDocument документ
	 * @throws BusinessException
	 */
	public void setMainDocumentInfo(ClientDocumentImpl mainDocument) throws BusinessException
	{
		this.mainDocument = mainDocument;
	}

	public boolean isIncrementedPromoCode()
	{
		return incrementedPromoCode;
	}

	public void setIncrementedPromoCode(boolean incrementedPromoCode)
	{
		this.incrementedPromoCode = incrementedPromoCode;
	}

	public void setFromExtendedLoanClaim(boolean fromExtendedLoanClaim)
	{
		setNullSaveAttributeBooleanValue(FROM_EXTENDED_LOAN_CLAIM, fromExtendedLoanClaim);
	}

	public Boolean getFromExtendedLoanClaim()
	{
		return getNullSaveAttributeBooleanValue(FROM_EXTENDED_LOAN_CLAIM);
	}

}
