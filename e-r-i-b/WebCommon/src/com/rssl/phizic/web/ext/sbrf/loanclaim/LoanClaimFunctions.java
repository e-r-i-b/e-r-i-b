package com.rssl.phizic.web.ext.sbrf.loanclaim;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.bki.CreditProfileService;
import com.rssl.phizic.business.bki.PersonCreditProfile;
import com.rssl.phizic.business.loanOffer.LoanOfferHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProductService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductType;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductTypeService;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditSubProductType;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 @author Pankin
 @ created 17.01.2014
 @ $Author$
 @ $Revision$
 */
public class LoanClaimFunctions
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final CreditProductTypeService typeServise = new CreditProductTypeService();
	private static final CreditProductService productServise = new CreditProductService();
	private static final CreditProfileService creditProfileService = new CreditProfileService();

	private static final CreditProductConditionService conditionServise  = new CreditProductConditionService();

	/**
	 * Получить список всех типов кредитных продуктов
	 * @return список всех типов кредитных продуктов
	 */
	public static List<CreditProductType> getAllCreditProductTypes()
	{
		try
		{
			return typeServise.getAll();
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения списка всех видов кредитов", e);
			return new ArrayList<CreditProductType>();
		}
	}
	/**
	 * Получить список всех кредитных продуктов
	 * @return список всех кредитных продуктов
	 */
	public static List<CreditProduct> getAllCreditProducts()
	{
		try
		{
			return productServise.getAll();
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения списка всех кредитных продуктов", e);
			return new ArrayList<CreditProduct>();
		}
	}
	/**
	 * @param condition Общие условие.
	 * @return Получаем список всех действующих условий(по 3м валютам), из общего условия.
	 */
	public static List<CurrencyCreditProductCondition> getAllActiveCurrCondition(CreditProductCondition condition)
	{
		List<CurrencyCreditProductCondition> currConditions = conditionServise.getAllActiveCurrCondition(condition);
		ListIterator<CurrencyCreditProductCondition> it = currConditions.listIterator();
		while (it.hasNext())
		{
			CurrencyCreditProductCondition currCondition = it.next();
			if (!currCondition.isClientAvaliable())
			{
				it.remove();
			}
		}

		return currConditions;
	}

	/**
	 * @return Признак того что используется новый алгоритм работы с кредитами.
	 */
	public static  Boolean isUseNewLoanClaimAlgorithm()
	{
		LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
		return  loanClaimConfig.isUseNewAlgorithm();
	}

	/**
	 * @return Признак того что используется новая xsd (16 релиза)
	 */
	public static boolean isXsd16ReleaseForEtsmUse()
	{
		LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
		return  loanClaimConfig.isUseXSDRelease16Version();
	}
	/**
	 * @return Признак того что используется новая xsd (19 релиза)
	 */
	public static boolean isXsd19ReleaseForEtsmUse()
	{
		LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
		return  loanClaimConfig.isUseXSDRelease19Version();
	}

	/**
	 * @return Признак того что кредитное условие имеет исторические записи по переданной валюте.
	 */
	public static Boolean hasCreditConditionHistory(CreditProductCondition condition,String currCode)
	{
		return !conditionServise.getArchCurrCondition(condition,currCode).isEmpty();
	}

	/**
	 * @param currConditon По валютное условие.
	 * @return Вернет дату завершения действия(Дату начала нового условия) по валютного условия в виде строки dd.MM.yyyy.
	 * Если нет то пустая строка.
	 */
	public static String getNextCurrConditionDate(CurrencyCreditProductCondition currConditon)
	{
		if (currConditon != null)
		{
			CreditProductCondition condition =  currConditon.getCreditProductCondition();
			Currency currency = currConditon.getCurrency();
			List<CurrencyCreditProductCondition>  allCurrCondition = conditionServise.getAllCurrencyCondition(condition,currency.getCode());
			ListIterator<CurrencyCreditProductCondition> it = allCurrCondition.listIterator();
			while (it.hasNext())
			{
				CurrencyCreditProductCondition cond = it.next();
				if (cond == currConditon)
				{
					if (it.hasNext())
						return DateHelper.formatDateToStringWithPoint(it.next().getStartDate());
					break;
				}
			}
		}
		return "";
	}

	/**
	 * @param tb TB
	 * @param corrency валюта (RUR,USD,EUR)
	 * @param subTypes набор типов кредитного суб продукта
	 * @return Код субкод продукта.
	 */
	public static String getSubCodeByTbAndCorrency(String tb,String corrency,Set<CreditSubProductType> subTypes)
	{
		for (CreditSubProductType subType:subTypes)
		{
			if (StringHelper.equals(tb,subType.getTerbank()) && StringHelper.equals(corrency,subType.getCurrency().getCode()))
				return subType.getCode();
		}
		log.warn("Для тербанка  c кодом " + tb + ", по валюте " + corrency + " не найден тип суб продукта.");
		return "\u00A0";
	}

	/**
	 * Проверяет допустимость отображения ссылки на открытие кредита
	 *
	 * @param isLoanPage для страницы "Кредиты" есть специфические условия
	 * @return отображать ссылку на открытие кредита или нет
	 */
	public static boolean takeCreditLinkAvailable(boolean isLoanPage)
	{
		boolean hasProductConditions = false;
		boolean hasLoanOffers        = false;
		boolean hasRussianPassport   = false;

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

		/*
		 * У клиента российский паспорт. Если у клиента нет паспорта - оформление кредита недоступно.
		 */
		try
		{
			List<? extends ClientDocument> clientDocuments = personData.getPerson().asClient().getDocuments();
			for (ClientDocument document : clientDocuments)
			{
				if (document.getDocumentType() == ClientDocumentType.REGULAR_PASSPORT_RF)
				{
					hasRussianPassport = true;
					break;
				}
			}
		}
		catch (BusinessException e)
		{
			log.error("Ошибка во время получения списка документов клиента.", e);
		}

		/*
		 * Есть ли в системе хотя бы одно кредитное условие
		 */
		try
		{
			List<CreditProductCondition> productConditions = conditionServise.getPublicityConditions();
			hasProductConditions = !productConditions.isEmpty();
		}
		catch (BusinessException e)
		{
			log.error("Ошибка во время получения списка кредитных условий.", e);
		}

		/*
		 * Есть ли для клиента предодобренные предложения
		 */
		try
		{
			Set<LoanOffer> loanOffers = LoanOfferHelper.filterLoanOffersByEndDate(personData.getLoanOfferByPersonData(null, null) );
			hasLoanOffers = !loanOffers.isEmpty();
		}
		catch (BusinessException e)
		{
			log.error("Ошибка во время получения предодобренных предложения клиента.", e);
		}

		//	Если в системе заведен хотя бы один кредитный продукт
		if (hasProductConditions)
		{
			// Если предодобренное предложение есть
			if (hasLoanOffers)
			{
				// Если пасспорт РФ то везде отображаем
				return hasRussianPassport;
			}
			// Если предодобренное предложение отсутствует
			else
			{
				// Отображается только ссылка "Взять кредит в Сбербанке" на странице кредиты если есть паспорт РФ
				return hasRussianPassport && isLoanPage;
			}
		}
		// Если в системе не заведено ни одного кредитного продукта
		else
		{
			// Есть предодобренные предложения
			if (hasLoanOffers)
			{
				// Ссылка должна отображаться только на странице "Кредиты" только в случае когда у клиента есть паспорт РФ
				return hasRussianPassport && isLoanPage;
			}
			// Нет ни предодобренных предложений, ни кредитных условий - ссылку не отображаем нигде
			else
			{
				return false;
			}
		}
	}

	/**
	 * Проверяет допустимость отображения ссылки получения кредитной истории
	 * @return отображать ссылку
	 */
	public static boolean getCreditHistoryLinkAvailable()
	{
		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		PersonCreditProfile profile = null;
		try
		{
			profile = creditProfileService.findByPerson(person);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения кредитного профиля клиента " + person.getId(), e);
			return false;
		}
		return profile != null && profile.isConnected();
	}
}
