package com.rssl.phizic.business.etsm.offer;

import com.google.gson.Gson;
import com.rssl.phizgate.ext.sbrf.etsm.OfferOfficePrior;
import com.rssl.phizgate.ext.sbrf.etsm.OfferPrior;
import com.rssl.phizgate.ext.sbrf.etsm.OfficeLoanClaim;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.accounts.AccountsUtil;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.etsm.offer.service.OfferEribService;
import com.rssl.phizic.business.etsm.offer.service.OfferPriorWebService;
import com.rssl.phizic.business.loanclaim.officeClaim.OfficeLoanClaimCaheService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanOffer.LoanOfferConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.DateSpanFormat;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.json.BasicGsonSingleton;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author EgorovaA
 * @ created 01.07.15
 * @ $Author$
 * @ $Revision$
 */
public class ETSMOfferHelper
{
    public static final String DELETED_STATE = "DELETED";

	private static OfferEribService offerEribService = new OfferEribService();
	private static BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static OfficeLoanClaimCaheService  officeLoanClaimCaheService = new OfficeLoanClaimCaheService();
	private static final OfferPriorWebService offerPriorWebService = new OfferPriorWebService();
	private static final SimpleService service = new SimpleService();
    protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Получить информацию о неподтвержденной оферте для отображения ее клиенту
	 * @param incrementCounter - увеличивать счетчик отображения на главной странице
	 * @return
	 * @throws BusinessException
	 */
	public static Map<String, String> getOfferInfo(Boolean incrementCounter)
	{
		Map<String, String> offerInfo = new HashMap<String, String>();

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		OfferPrior offerPrior = null;
		//Так как используется только в tld-функции ловим исключение
		try
		{
			offerPrior = personData.getOfferPrior(incrementCounter);
		}
		catch (BusinessException e)
		{
			log.error(e);
		}

		if (offerPrior == null || offerPrior.isEmpty())
			return offerInfo;

		if (offerPrior instanceof OfferEribPrior)
		{
			OfferEribPrior offerEribPrior = (OfferEribPrior) offerPrior;
            if (DELETED_STATE.equals(offerEribPrior.getState()))
                return offerInfo;
			offerInfo.put("applicationNumber", offerEribPrior.getApplicationNumber());
			offerInfo.put("claimId", String.valueOf(offerEribPrior.getClaimId()));
			offerInfo.put("loanProductName", offerEribPrior.getLoanProductName());
		}
		else
		{
			offerInfo.put("applicationNumber", offerPrior.getApplicationNumber());
			offerInfo.put("loanProductName", offerPrior.getLoanProductName());
		}

		return offerInfo;
	}

	/**
	 * Получить оферты клиента.
	 * Сначала проверяем оферты в ЕРИБ.
	 * Если не найдено ни одной оферты для отображения - ищем среди оферт в централизованной БД (по завякам, созданным в каналах, отличных от УКО)
	 * @param person - персона клиента
	 * @param incrementCounter - увеличивать счетчик отображения на главной странице
	 * @return неподтвержденная оферта
	 * @throws BusinessException
	 */
	public static OfferPrior getActualOffer(ActivePerson person, boolean incrementCounter) throws BusinessException
	{
		OfferPrior offerPrior = getActualOfferEribPrior(person, incrementCounter);
		if (offerPrior == null)
			offerPrior = getActualOfferOfficePrior(person, incrementCounter);

		if (offerPrior == null)
			return new OfferPrior();

		return offerPrior;
	}

	/**
	 * Получить оферты по заявкам, созданным в каналах, отличных от УКО
	 * @param person - персона клиента
	 * @param incrementCounter - увеличивать счетчик отображения на главной странице
	 * @return неподтвержденная оферта
	 * @throws BusinessException
	 */
	public static OfferOfficePrior getActualOfferOfficePrior(ActivePerson person, boolean incrementCounter) throws BusinessException
	{
		LoanOfferConfig config = ConfigFactory.getConfig(LoanOfferConfig.class);

		List<OfferOfficePrior> offerOfficePriors= offerPriorWebService.getOfferOfficePriorByFIODocBD(person);

		// Выберем по одной сущности на оферту
		Map<String, OfferOfficePrior> offerMap = new HashMap<String, OfferOfficePrior>();
		for (OfferOfficePrior offer : offerOfficePriors)
		{
			String applicationNumber = offer.getApplicationNumber();
			if (!offerMap.containsKey(applicationNumber))
				offerMap.put(applicationNumber, offer);
		}

		for (OfferOfficePrior offer : offerMap.values())
		{
			OfficeLoanClaim claim = officeLoanClaimCaheService.getOfficeLoanClaimByAppNum(offer.getApplicationNumber());
			if (claim == null)
				continue;

			offer.setLoanProductName(claim.getProductName());

			// 1. Определить принадлежность клиента к категории «Предпенсионер»
			Long claimPeriodInMonth = claim.getLoanPeriod();
			if (PersonHelper.isNearPensioner(person, claimPeriodInMonth.intValue()))
				continue;

			// 2. Проверить тип заявки
			Boolean isUseLoanOffer = claim.isPreapproved();
			// 3. Определить категорию клиента
			String clientCategory = offer.getClientCategory();

			if (!getOfferOfficeShowSetting(config, isUseLoanOffer, clientCategory))
				continue;

			// 4. Определить статус заявки (если заявка не в статусе "одобрено" - не отображаем ее)
			if (!(claim.getState() == 2))
				continue;

			// 5. Если оферта нужна для страницы кредитов - отображаем сразу
			if (!incrementCounter)
				return offer;

			// Если оферта нужна для главной - проверим значения счетчика отображения, при необходимости увеличим его
			Long offerVisibilityCounter = offer.getVisibilityCounter();
			int offerVisibilitySetting = config.getOfferDisplayCountOnMainPage();
			Boolean isCounterUpdated = offer.getCounterUpdated();
			if ((offerVisibilityCounter < offerVisibilitySetting && !isCounterUpdated) || (offerVisibilityCounter <= offerVisibilitySetting && isCounterUpdated))
			{
				if (!isCounterUpdated)
				{
					offerPriorWebService.updateOfferOfficePriorVisibleCounter(offer.getId());
				}
				return offer;
			}
		}
		return null;
	}

	/**
	 * Получить оферты по заявкам, созданным в ЕРИБ
	 * @param person - персона клиента
	 * @param incrementCounter - увеличивать счетчик отображения на главной странице
	 * @return неподтвержденная оферта
	 * @throws BusinessException
	 */
	public static OfferEribPrior getActualOfferEribPrior(ActivePerson person, boolean incrementCounter) throws BusinessException
	{
		LoanOfferConfig config = ConfigFactory.getConfig(LoanOfferConfig.class);

		Long clientLoginId = person.getLogin().getId();

		List<OfferEribPrior> offerEribPriorList = offerEribService.getOfferEribPrior(clientLoginId);

		// Выберем по одной сущности на оферту
		Map<String, OfferEribPrior> offerMap = new HashMap<String, OfferEribPrior>();
		for (OfferEribPrior offer : offerEribPriorList)
		{
			String applicationNumber = offer.getApplicationNumber();
			if (!offerMap.containsKey(applicationNumber))
				offerMap.put(applicationNumber, offer);
		}

		for (OfferEribPrior offer : offerMap.values())
		{
			ExtendedLoanClaim claim = (ExtendedLoanClaim) businessDocumentService.findById(offer.getClaimId());
			if (claim == null)
				continue;

			offer.setLoanProductName(claim.getProductName());

			// 1. Определить принадлежность клиента к категории «Предпенсионер»
			Long claimPeriodInMonth = claim.getLoanPeriod();
			if (PersonHelper.isNearPensioner(person, claimPeriodInMonth.intValue()))
				continue;

			// 2. Проверить тип заявки
			Boolean isUseLoanOffer = claim.isUseLoanOffer();
			// 3. Определить категорию клиента
			String clientCategory = offer.getClientCategory();

			if (!getOfferEribShowSetting(config, isUseLoanOffer, clientCategory))
				continue;

			// 4. Определить статус заявки (если заявка не в статусе "одобрено" - не отображаем ее)
			if (!claim.getState().getCode().equals("APPROVED_MUST_CONFIRM"))
				continue;

			// 5. Если оферта нужна для страницы кредитов - отображаем сразу
			if (!incrementCounter)
				return offer;

			// Если оферта нужна для главной - проверим значения счетчика отображения, при необходимости увеличим его
			Long offerVisibilityCounter = offer.getVisibilityCounter();
			int offerVisibilitySetting = config.getOfferDisplayCountOnMainPage();
			Boolean isCounterUpdated = offer.getCounterUpdated();
			if ((offerVisibilityCounter < offerVisibilitySetting && !isCounterUpdated) || (offerVisibilityCounter <= offerVisibilitySetting && isCounterUpdated))
			{
				if (!isCounterUpdated)
				{
					offer.setVisibilityCounter(offerVisibilityCounter + 1);
					offer.setCounterUpdated(true);
					service.update(offer);
				}
				return offer;
			}
		}
		return null;
	}

	/**
	 * Определить, отображать ли оферту (для оферт по заявкам, созданным в УКО)
	 * @param config - конфиг с настройками
	 * @param isUseLoanOffer - заявка на общих условиях или с предодобренным предложением
	 * @param clientCategory - тип категории клиента
	 * @return true, если для данных условий оферта должна отображаться
	 */
	private static boolean getOfferEribShowSetting(LoanOfferConfig config, boolean isUseLoanOffer, String clientCategory)
	{
		if(StringHelper.isEmpty(clientCategory))
			return false;

		String clientType = clientCategory.substring(0, 1);
		if (isUseLoanOffer)
		{
			if (clientType.equalsIgnoreCase("A"))
				return config.isUkoPreapprovedEmployees();
			else if (clientType.equalsIgnoreCase("B"))
				return config.isUkoPreapprovedSalaries();
			else if (clientType.equalsIgnoreCase("C"))
				return config.isUkoPreapprovedAccredits();
			else if (clientType.equalsIgnoreCase("D"))
				return config.isUkoPreapprovedStreet();
			else if (clientType.equalsIgnoreCase("E"))
				return config.isUkoPreapprovedPensioners();
		}
		else
		{
			if (clientType.equalsIgnoreCase("A"))
				return config.isUkoCommonConditionsEmployees();
			else if (clientType.equalsIgnoreCase("B"))
				return config.isUkoCommonConditionsSalaries();
			else if (clientType.equalsIgnoreCase("C"))
				return config.isUkoCommonConditionsAccredits();
			else if (clientType.equalsIgnoreCase("D"))
				return config.isUkoCommonConditionsStreet();
			else if (clientType.equalsIgnoreCase("E"))
				return config.isUkoCommonConditionsPensioners();
		}
		return false;
	}

	/**
	 * Определить, отображать ли оферту (для оферт по заявкам, созданным НЕ в УКО)
	 * @param config - конфиг с настройками
	 * @param isUseLoanOffer - заявка на общих условиях или с предодобренным предложением
	 * @param clientCategory - тип категории клиента
	 * @return true, если для данных условий оферта должна отображаться
	 */
	private static boolean getOfferOfficeShowSetting(LoanOfferConfig config, boolean isUseLoanOffer, String clientCategory)
	{
		if(StringHelper.isEmpty(clientCategory))
			return false;

		String clientType = clientCategory.substring(0, 1);
		if (isUseLoanOffer)
		{
			if (clientType.equalsIgnoreCase("A"))
				return config.isVspPreapprovedEmployees();
			else if (clientType.equalsIgnoreCase("B"))
				return config.isVspPreapprovedSalaries();
			else if (clientType.equalsIgnoreCase("C"))
				return config.isVspPreapprovedAccredits();
			else if (clientType.equalsIgnoreCase("D"))
				return config.isVspPreapprovedStreet();
			else if (clientType.equalsIgnoreCase("E"))
				return config.isVspPreapprovedPensioners();
		}
		else
		{
			if (clientType.equalsIgnoreCase("A"))
				return config.isVspCommonConditionsEmployees();
			else if (clientType.equalsIgnoreCase("B"))
				return config.isVspCommonConditionsSalaries();
			else if (clientType.equalsIgnoreCase("C"))
				return config.isVspCommonConditionsAccredits();
			else if (clientType.equalsIgnoreCase("D"))
				return config.isVspCommonConditionsStreet();
			else if (clientType.equalsIgnoreCase("E"))
				return config.isVspCommonConditionsPensioners();
		}
		return false;
	}

    public static String getConfirmedOfferText(ExtendedLoanClaim claim) {
        try
        {
            if (claim == null)
                return null;

            if (claim.getConfirmedOfferId() == null)
                return null;

            OfferConfirmed offerConfirmed = service.findById(OfferConfirmed.class, claim.getConfirmedOfferId() );

            if (offerConfirmed == null)
                return null;

            if (offerConfirmed.getApplicationNumber() == null)
                return null;

            CreditOfferTemplate offerTemplate = service.findById(CreditOfferTemplate.class, offerConfirmed.getTemplateId());
            if (offerTemplate == null) {
                log.error("Не удалось определить шаблон оферты " + offerConfirmed.getTemplateId());
                return null;
            }

            ExtendedLoanClaim cliam = service.findById(ExtendedLoanClaim.class, offerConfirmed.getClaimId());
            if (cliam == null)  {
                log.error("Не удалось найти кредитную заявку с id:" + offerConfirmed.getClaimId());
                return null;
            }

            // ФИО клиента
            String clientFIO = StringHelper.getEmptyIfNull(offerConfirmed.getLastName()) + " " +
                        StringHelper.getEmptyIfNull(offerConfirmed.getFirstName()) + " " +
                        StringHelper.getEmptyIfNull(offerConfirmed.getMiddleName());

            // Дата выдачи документа
            Calendar docIssueDate = offerConfirmed.getIssueDt();
            String docIssue = docIssueDate == null ? "" : DateHelper.formatDateWithMonthString(docIssueDate);

            // Процентная ставка
            BigDecimal interestRate = offerConfirmed.getAltInterestRate();
            String interestRateText = interestRate == null ? "" :
                    interestRate.toString() + "% (" + StringUtils.sumInWords(interestRate.toString(), "") + " процентов)";

            // Срок кредита в месяцах
            Long period = offerConfirmed.getAltPeriod();
            String durationText = period == null ? "" : DateSpanFormat.format(new DateSpan(0, period.intValue(), 0));

            // Полная стоимость кредита
            BigDecimal fullLoanCost = offerConfirmed.getAltFullLoanCost();
            String fullLoanCostTxt = fullLoanCost == null ? "" :
                    fullLoanCost.toString() + " руб. (" + StringUtils.sumInWords(fullLoanCost.toString(), "RUB") + "), российские рубли";

            // Альтернативная сумма кредита в рублях;
            BigDecimal altAmount = offerConfirmed.getAltAmount();
            String altAmountTxt = altAmount == null ? "" :
                    altAmount.toString() + " руб. (" + StringUtils.sumInWords(altAmount.toString(), "RUB") + "), российские рубли";

            // Счёт для выдачи кредита, выбранный клиентом при заполнении оферты
            String accountNumber = AccountsUtil.getFormattedAccountNumber(offerConfirmed.getAccountNumber());

            // Альтернативный аннуитетный платеж (порядок погашения кредита)
            BigDecimal altAnnuityPayment = offerConfirmed.getAltAnnuityPayment();
            String altAnnuityPaymentTxt = altAnnuityPayment == null ? "" :
                                         "Ежемесячно аннуитетными платежами в размере " + altAnnuityPayment.toString() + " руб.";

            Map<String, String> params = new HashMap<String, String>();
            params.put(CreditOfferTemplate.ATTRIBUTE_FIO, clientFIO);
            params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_SERIES, StringHelper.getEmptyIfNull(offerConfirmed.getIdSeries()));
            params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_NUMBER, StringHelper.getEmptyIfNull(offerConfirmed.getIdNum()));
            params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_ISSUED_BY, StringHelper.getEmptyIfNull(offerConfirmed.getIssuedBy()));
            params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_ISSUED_DATE, docIssue);
            params.put(CreditOfferTemplate.ATTRIBUTE_LOAN_COST, fullLoanCostTxt);
            params.put(CreditOfferTemplate.ATTRIBUTE_AMOUNT, altAmountTxt);
            params.put(CreditOfferTemplate.ATTRIBUTE_PERIOD, durationText);
            params.put(CreditOfferTemplate.ATTRIBUTE_INTEREST_RATE, interestRateText);
            params.put(CreditOfferTemplate.ATTRIBUTE_ANNUITY_PAYMENT, altAnnuityPaymentTxt);
            params.put(CreditOfferTemplate.ATTRIBUTE_ISSUE_ACCOUNT, accountNumber);
            params.put(CreditOfferTemplate.ATTRIBUTE_BORROWER, StringHelper.getEmptyIfNull(offerConfirmed.getBorrower()));
            params.put(CreditOfferTemplate.ATTRIBUTE_REGISTRATION, StringHelper.getEmptyIfNull(offerConfirmed.getRegistrationAddress()));

            return  XmlHelper.getWithHtmlTag(offerTemplate.getText(), params);

        }
        catch (BusinessException e)
        {
            log.error("Ошибка при получении подтверждённой оферты по заявке " + claim.getId());
            return null;
        }
	}

	/**
	 * @return список переменных, используемых в тексте оферты
	 */
	public static String getCreditOfferTemplateAttributesJson()
	{
		List<String> attributes = new ArrayList<String>(13);
		attributes.add(XmlHelper.escapeWithImitation(CreditOfferTemplate.ATTRIBUTE_FIO));
		attributes.add(XmlHelper.escapeWithImitation(CreditOfferTemplate.ATTRIBUTE_PASSPORT_SERIES));
		attributes.add(XmlHelper.escapeWithImitation(CreditOfferTemplate.ATTRIBUTE_PASSPORT_NUMBER));
		attributes.add(XmlHelper.escapeWithImitation(CreditOfferTemplate.ATTRIBUTE_PASSPORT_ISSUED_BY));
		attributes.add(XmlHelper.escapeWithImitation(CreditOfferTemplate.ATTRIBUTE_PASSPORT_ISSUED_DATE));
		attributes.add(XmlHelper.escapeWithImitation(CreditOfferTemplate.ATTRIBUTE_LOAN_COST));
		attributes.add(XmlHelper.escapeWithImitation(CreditOfferTemplate.ATTRIBUTE_AMOUNT));
		attributes.add(XmlHelper.escapeWithImitation(CreditOfferTemplate.ATTRIBUTE_PERIOD));
		attributes.add(XmlHelper.escapeWithImitation(CreditOfferTemplate.ATTRIBUTE_INTEREST_RATE));
		attributes.add(XmlHelper.escapeWithImitation(CreditOfferTemplate.ATTRIBUTE_ANNUITY_PAYMENT));
		attributes.add(XmlHelper.escapeWithImitation(CreditOfferTemplate.ATTRIBUTE_ISSUE_ACCOUNT));
		attributes.add(XmlHelper.escapeWithImitation(CreditOfferTemplate.ATTRIBUTE_BORROWER));
		attributes.add(XmlHelper.escapeWithImitation(CreditOfferTemplate.ATTRIBUTE_REGISTRATION));

		Gson gson = BasicGsonSingleton.getGson();
		return gson.toJson(attributes);
	}
}
