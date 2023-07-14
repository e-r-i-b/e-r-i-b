package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.EntityList;
import com.rssl.phizic.business.xslt.lists.builder.Field;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 * @author hudyakov
 * @ created 22.02.2008
 * @ $Author$
 * @ $Revision$
 *
 * !!!!!!!!! использовать этот класс можно ТОЛЬКО В КЛИЕНТЕ!!!!!
 * (в силу того, что тут юзается PersonContext)
 * берутся лишь те валюты, что присутствуют у пользователя в счетах, картах и т.д.
 */
public abstract class CurrencyListSourceBase extends CachedEntityListSourceBase
{
	protected static final CurrencyService currencyService    = GateSingleton.getFactory().service(CurrencyService.class);
	private static final CurrencyRateService currencyRateService = GateSingleton.getFactory().service(CurrencyRateService.class);
	private static final DepartmentService departmentService = new DepartmentService();

	public static final String NATIONAL_CURRENCY = "nationCurrency";
    private Currency nationalCurrency;

	public CurrencyListSourceBase(EntityListDefinition definition, String nationCurrency) throws GateException, GateLogicException
	{
		super(definition);
		  nationalCurrency = currencyService.findByAlphabeticCode(nationCurrency);
	}
	public CurrencyListSourceBase(EntityListDefinition definition, Map parameters) throws GateException, GateLogicException
	{
		  this (definition, (String) parameters.get(NATIONAL_CURRENCY));
	}

	/**
	 * Список карт клиента
	 * @return List<CardLink> - список карт клиента
	 * @throws BusinessException
	 */
	protected List<CardLink> getCards() throws BusinessException, BusinessLogicException
	{
		return PersonContext.getPersonDataProvider().getPersonData().getCards(new CardFilterBase()
		{
			public boolean accept(Card card)
			{
				return !MockHelper.isMockObject(card) && skipStoredResource(card);
			}
		});
	}

	/**
	 * Список счетов клиента
	 * @return List<AccountLink> - список счетов клиента
	 * @throws BusinessException
	 */
	protected List<AccountLink> getAccounts() throws BusinessException, BusinessLogicException
	{
		return PersonContext.getPersonDataProvider().getPersonData().getAccounts(new AccountFilter()
		{
			public boolean accept(AccountLink accountLink)
			{
				Account account = accountLink.getAccount();
				return !MockHelper.isMockObject(account) && skipStoredResource(account);
			}
		});
	}

	/**
	 * Список ОМС клиента
	 * @return List<IMAccountLink> - список ОМС клиента
	 * @throws BusinessException
	 */
	protected List<IMAccountLink> getIMAccounts() throws BusinessException, BusinessLogicException
	{
		return PersonContext.getPersonDataProvider().getPersonData().getIMAccountLinks(new IMAccountFilter()
		{
			public boolean accept(IMAccountLink imAccountLink)
			{
				IMAccount imaccount = imAccountLink.getImAccount();
				return !MockHelper.isMockObject(imaccount) && skipStoredResource(imaccount);
			}
		});
	}

	/**
	 * Продукты клиента по которым будет строиться справочник валют. Реализуется в наследниках используя
	 * существующие методы в родителе.
	 * @param params - параметры справочника
	 * @return ClientCurrencyProductBundle - содержит списки продуктов, которые необходимы для построения справочника валют
	 * @throws BusinessException
	 */
	protected abstract ClientCurrencyProductBundle buildCurrencyProducts(Map<String, String> params) throws BusinessException, BusinessLogicException;

	/**
	 * получаем лишь те курсы валют (и кросс-курсы), что есть у клиента в счетах.
	 * @param params строка с параметрами
	 * @return  строку с записанной в нее xml со списком валют вида:
	 *  entity key = буквенный_код_валюты
	 *      field name CB_ курс_цб
	 *      field name BYU курс_покупки
	 *      field name SALE курс_продажи
	 *  буквенный_код_валюты может быть как RUB, USD, EUR, так и
	 *  код_валюты1|код_валюты2 - в полях записи указан курс покупки-продажи валюты1 относительно валюты2
	 * @throws BusinessException
	 */
	public Pair<String, List<Object>> buildEntityList(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		//Сбор необходимых продуктов, по которым будет строиться справочник валют.
		ClientCurrencyProductBundle currencyProducts = buildCurrencyProducts(params);

		//Список неповторяющихся валют
		List<Currency> currenciesList = new ArrayList<Currency> (currencyProducts.getCurrenciesSet());

		EntityList entityList = new EntityList();
		Long departmentId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId();
		String tarifPlanCodeType = PersonHelper.getActivePersonTarifPlanCode();

		Office office = departmentService.findById(departmentId);
		for (int i = 0; i < currenciesList.size(); i++)
		{
			Currency currOne = currenciesList.get(i);
			entityList.addEntity(addCourseEntity(currOne, office, tarifPlanCodeType));

			for (int j = i + 1; j < currenciesList.size(); j++)
			{
				// нам нужны оба кросс-курса, например, EUR|USD, И  USD|EUR
				Currency currTwo = currenciesList.get(j);
				entityList.addEntity(getCrossCoursesEntity(currOne, currTwo, office, tarifPlanCodeType));
				entityList.addEntity(getCrossCoursesEntity(currTwo, currOne, office, tarifPlanCodeType));
			}
		}
		List<DepositProduct> depositProducts = currencyProducts.getDepositProducts();
		//depositProducts.toArray() - чтобы не создавался список с одним нуловым элементом в convertToReturnValue(), если depositProducts = null 
		return convertToReturnValue(entityList.toString(), CollectionUtils.isEmpty(depositProducts) ? null : depositProducts.toArray());
	}

	/**
	 * Добавляет в entity курс валют для указанного тарифного плана
	 * @param currOne - первая валюта
	 * @param currTwo - вторая валюта
	 * @param currencyRateType - тип курса валюты
	 * @param office - офис
	 * @param entity
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private void addStandartCurrencyRate(Currency currOne, Currency currTwo, CurrencyRateType currencyRateType, String fieldPrefix,
	                                     Office office, Entity entity, String tarifPlanCodeType) throws GateLogicException, GateException
	{
		CurrencyRate standartCurrencyRate = currencyRateService.getRate(currOne, currTwo, currencyRateType,
				office, tarifPlanCodeType);
		if (standartCurrencyRate != null)
		{
			String prefix = StringHelper.isNotEmpty(fieldPrefix) ? fieldPrefix.trim() : currencyRateType.name();
			BigDecimal rateFactor = standartCurrencyRate.getFactor();
			entity.addField(new Field(prefix+"_"+tarifPlanCodeType,
					getFormat().format(rateFactor)));
		}
	}

	/**
	 * возвращает тег с кросс-курсами
	 * entity key = буквенный_код_валюты
	 *      field name CB_ курс_цб
	 *      field name BYU курс_покупки
	 *      field name SALE курс_продажи
	 * entity
     * @param currOne первая валюта
     * @param currTwo вторая
	 * @param office - офис (для многофилиальности)
	 * @param tarifPlanCodeType - код тарифного плана
	 * @return тег с кросс-курсами
	 */
	private Entity getCrossCoursesEntity(Currency currOne, Currency currTwo, Office office, String tarifPlanCodeType)
	{
		Entity entity = new Entity(currOne.getCode() + "|" + currTwo.getCode(), currOne.getName() + "|" + currTwo.getName());
		try
		{
			CurrencyRate currencyRate = currencyRateService.getRate(currOne, currTwo, CurrencyRateType.CB, office, tarifPlanCodeType);
			if (currencyRate != null)
			{
				entity.addField(new Field(CurrencyRateType.CB.name(), getFormat().format(currencyRate.getFactor())));
			}

			//Курс валют для обычного тарифного плана если у клиента льготный
			if (!TariffPlanHelper.isUnknownTariffPlan(tarifPlanCodeType))
				addStandartCurrencyRate(currOne, currTwo, CurrencyRateType.CB, null, office, entity, TariffPlanHelper.getUnknownTariffPlanCode());

			// банк покупает currOne, rateBuy=числу currTwo, что нужно отдать
			fillEntity(entity, currOne, currTwo, CurrencyRateType.BUY_REMOTE, office, tarifPlanCodeType);

			// банк продает currOne.
			fillEntity(entity, currOne, currTwo, CurrencyRateType.SALE_REMOTE, office, tarifPlanCodeType);
		}
		catch (GateException ex)
		{
			log.info(ex.getMessage(), ex);
		}
		catch (GateLogicException e)
		{
			log.info(e.getMessage(), e);
		}
		return entity;
	}

	/**
	 * возвращает курс покупки-продажи валюты
	 * @param currency - валюта
	 * @param office - офис (для многофилиальности)
	 * @return тег с покупкой-продажей валюты
	 */
	private Entity addCourseEntity(Currency currency, Office office, String tarifPlanCodeType)
	{
		Entity entity = new Entity(currency.getCode(), currency.getName());
		try
		{
			entity.addField(new Field("number", currency.getNumber(), null));

			CurrencyRate currencyRate = currencyRateService.getRate(nationalCurrency, currency, CurrencyRateType.CB, office, tarifPlanCodeType);
			if (currencyRate != null)
			{
				entity.addField(new Field(CurrencyRateType.CB.name(), getFormat().format(currencyRate.getToValue())));
			}

			//Курс валют для обычного тарифного плана если у клиента льготный
			if (!TariffPlanHelper.isUnknownTariffPlan(tarifPlanCodeType))
				addStandartCurrencyRate(nationalCurrency, currency, CurrencyRateType.CB, null, office, entity, TariffPlanHelper.getUnknownTariffPlanCode());

			// покупка
			fillEntity(entity, nationalCurrency, currency, CurrencyRateType.BUY_REMOTE, office, tarifPlanCodeType);
			// продажа
			fillEntity(entity, nationalCurrency, currency, CurrencyRateType.SALE_REMOTE, office, tarifPlanCodeType);
		}
		catch (GateException ex)
		{
			log.info(ex.getMessage(), ex);
		}
		catch (GateLogicException e)
		{
			log.info(e.getMessage(), e);
		}
		return entity;
	}

	/**
	 * Заполнить тег с кросс-курсами значениями курса покупки и продажи
	 * @param entity - заполняемый тег
	 * @param currOne - первая валюта
	 * @param currTwo - вторая валюта
	 * @param rateType - тип операции
	 * @param office - офис
	 * @param tarifPlanCodeType - код тарифного плана
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private void fillEntity(Entity entity, Currency currOne, Currency currTwo, CurrencyRateType rateType, Office office, String tarifPlanCodeType) throws GateLogicException, GateException
	{
		//Курс валют для тарифного плана клиента
		CurrencyRate rate = currencyRateService.getRate(currOne, currTwo, rateType, office, tarifPlanCodeType);
		if (rate != null)
		{
			DecimalFormat formatter = getFormat();
			String fieldPrefix = rateType == CurrencyRateType.SALE_REMOTE ? CurrencyRateType.SALE.name() : CurrencyRateType.BUY.name();
			BigDecimal rateFactor = rate.getFactor();
			entity.addField(new Field(fieldPrefix, formatter.format(rateFactor)));

			//Только если льготный тарифный план иначе не лезем
			if (!TariffPlanHelper.isUnknownTariffPlan(tarifPlanCodeType))
			{
				//Курс валют для обычного тарифного плана
				addStandartCurrencyRate(currOne, currTwo, rateType, fieldPrefix, office, entity, TariffPlanHelper.getUnknownTariffPlanCode());

				Set<String> currencyShowMsgSet = CurrencyUtils.getCurrencyShowPremierMsgSet();
				//Если валюта операции из списка, то сообщение отображается
				if (currencyShowMsgSet.contains(rate.getFromCurrency().getCode().toUpperCase()) ||
						currencyShowMsgSet.contains(rate.getToCurrency().getCode().toUpperCase()))
				{
					entity.addField(new Field(fieldPrefix + "_SHOW_MSG", "true"));
				}
				else//иначе сравнивается курс валют текущего тарифа клиента с курсом обычного тарифа ("отсутствует" - UNKNOWN)
				{
					CurrencyRate rateUnknown = currencyRateService.getRate(currOne, currTwo, rateType,
							office, TariffPlanHelper.getUnknownTariffPlanCode());
					if (rateUnknown != null)
					{
						String tarifCompare = rateFactor.compareTo(rateUnknown.getFactor()) != 0 ? "true" : "false";
						entity.addField(new Field(fieldPrefix + "_SHOW_MSG", tarifCompare));
					}
				}
			}
		}
	}

	/**
	 *
	 * Разрешается ли использование оффлайн продуктов в качестве источника информации
	 *
	 * @return boolean
	 */
	protected boolean skipStoredResource(Object object)
	{
		return !(object instanceof AbstractStoredResource);
	}
	
	/**
	 * Получаем форматтер для корректного отображения курса конверсии
	 * @return форматтер
	 */
	private DecimalFormat getFormat()
	{
		return new DecimalFormat("#0.00##", new DecimalFormatSymbols(Locale.US));
	}
}
