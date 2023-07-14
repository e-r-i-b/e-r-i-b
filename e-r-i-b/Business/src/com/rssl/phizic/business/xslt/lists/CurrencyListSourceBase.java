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
 * !!!!!!!!! ������������ ���� ����� ����� ������ � �������!!!!!
 * (� ���� ����, ��� ��� ������� PersonContext)
 * ������� ���� �� ������, ��� ������������ � ������������ � ������, ������ � �.�.
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
	 * ������ ���� �������
	 * @return List<CardLink> - ������ ���� �������
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
	 * ������ ������ �������
	 * @return List<AccountLink> - ������ ������ �������
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
	 * ������ ��� �������
	 * @return List<IMAccountLink> - ������ ��� �������
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
	 * �������� ������� �� ������� ����� ��������� ���������� �����. ����������� � ����������� ���������
	 * ������������ ������ � ��������.
	 * @param params - ��������� �����������
	 * @return ClientCurrencyProductBundle - �������� ������ ���������, ������� ���������� ��� ���������� ����������� �����
	 * @throws BusinessException
	 */
	protected abstract ClientCurrencyProductBundle buildCurrencyProducts(Map<String, String> params) throws BusinessException, BusinessLogicException;

	/**
	 * �������� ���� �� ����� ����� (� �����-�����), ��� ���� � ������� � ������.
	 * @param params ������ � �����������
	 * @return  ������ � ���������� � ��� xml �� ������� ����� ����:
	 *  entity key = ���������_���_������
	 *      field name CB_ ����_��
	 *      field name BYU ����_�������
	 *      field name SALE ����_�������
	 *  ���������_���_������ ����� ���� ��� RUB, USD, EUR, ��� �
	 *  ���_������1|���_������2 - � ����� ������ ������ ���� �������-������� ������1 ������������ ������2
	 * @throws BusinessException
	 */
	public Pair<String, List<Object>> buildEntityList(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		//���� ����������� ���������, �� ������� ����� ��������� ���������� �����.
		ClientCurrencyProductBundle currencyProducts = buildCurrencyProducts(params);

		//������ ��������������� �����
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
				// ��� ����� ��� �����-�����, ��������, EUR|USD, �  USD|EUR
				Currency currTwo = currenciesList.get(j);
				entityList.addEntity(getCrossCoursesEntity(currOne, currTwo, office, tarifPlanCodeType));
				entityList.addEntity(getCrossCoursesEntity(currTwo, currOne, office, tarifPlanCodeType));
			}
		}
		List<DepositProduct> depositProducts = currencyProducts.getDepositProducts();
		//depositProducts.toArray() - ����� �� ���������� ������ � ����� ������� ��������� � convertToReturnValue(), ���� depositProducts = null 
		return convertToReturnValue(entityList.toString(), CollectionUtils.isEmpty(depositProducts) ? null : depositProducts.toArray());
	}

	/**
	 * ��������� � entity ���� ����� ��� ���������� ��������� �����
	 * @param currOne - ������ ������
	 * @param currTwo - ������ ������
	 * @param currencyRateType - ��� ����� ������
	 * @param office - ����
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
	 * ���������� ��� � �����-�������
	 * entity key = ���������_���_������
	 *      field name CB_ ����_��
	 *      field name BYU ����_�������
	 *      field name SALE ����_�������
	 * entity
     * @param currOne ������ ������
     * @param currTwo ������
	 * @param office - ���� (��� �����������������)
	 * @param tarifPlanCodeType - ��� ��������� �����
	 * @return ��� � �����-�������
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

			//���� ����� ��� �������� ��������� ����� ���� � ������� ��������
			if (!TariffPlanHelper.isUnknownTariffPlan(tarifPlanCodeType))
				addStandartCurrencyRate(currOne, currTwo, CurrencyRateType.CB, null, office, entity, TariffPlanHelper.getUnknownTariffPlanCode());

			// ���� �������� currOne, rateBuy=����� currTwo, ��� ����� ������
			fillEntity(entity, currOne, currTwo, CurrencyRateType.BUY_REMOTE, office, tarifPlanCodeType);

			// ���� ������� currOne.
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
	 * ���������� ���� �������-������� ������
	 * @param currency - ������
	 * @param office - ���� (��� �����������������)
	 * @return ��� � ��������-�������� ������
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

			//���� ����� ��� �������� ��������� ����� ���� � ������� ��������
			if (!TariffPlanHelper.isUnknownTariffPlan(tarifPlanCodeType))
				addStandartCurrencyRate(nationalCurrency, currency, CurrencyRateType.CB, null, office, entity, TariffPlanHelper.getUnknownTariffPlanCode());

			// �������
			fillEntity(entity, nationalCurrency, currency, CurrencyRateType.BUY_REMOTE, office, tarifPlanCodeType);
			// �������
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
	 * ��������� ��� � �����-������� ���������� ����� ������� � �������
	 * @param entity - ����������� ���
	 * @param currOne - ������ ������
	 * @param currTwo - ������ ������
	 * @param rateType - ��� ��������
	 * @param office - ����
	 * @param tarifPlanCodeType - ��� ��������� �����
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private void fillEntity(Entity entity, Currency currOne, Currency currTwo, CurrencyRateType rateType, Office office, String tarifPlanCodeType) throws GateLogicException, GateException
	{
		//���� ����� ��� ��������� ����� �������
		CurrencyRate rate = currencyRateService.getRate(currOne, currTwo, rateType, office, tarifPlanCodeType);
		if (rate != null)
		{
			DecimalFormat formatter = getFormat();
			String fieldPrefix = rateType == CurrencyRateType.SALE_REMOTE ? CurrencyRateType.SALE.name() : CurrencyRateType.BUY.name();
			BigDecimal rateFactor = rate.getFactor();
			entity.addField(new Field(fieldPrefix, formatter.format(rateFactor)));

			//������ ���� �������� �������� ���� ����� �� �����
			if (!TariffPlanHelper.isUnknownTariffPlan(tarifPlanCodeType))
			{
				//���� ����� ��� �������� ��������� �����
				addStandartCurrencyRate(currOne, currTwo, rateType, fieldPrefix, office, entity, TariffPlanHelper.getUnknownTariffPlanCode());

				Set<String> currencyShowMsgSet = CurrencyUtils.getCurrencyShowPremierMsgSet();
				//���� ������ �������� �� ������, �� ��������� ������������
				if (currencyShowMsgSet.contains(rate.getFromCurrency().getCode().toUpperCase()) ||
						currencyShowMsgSet.contains(rate.getToCurrency().getCode().toUpperCase()))
				{
					entity.addField(new Field(fieldPrefix + "_SHOW_MSG", "true"));
				}
				else//����� ������������ ���� ����� �������� ������ ������� � ������ �������� ������ ("�����������" - UNKNOWN)
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
	 * ����������� �� ������������� ������� ��������� � �������� ��������� ����������
	 *
	 * @return boolean
	 */
	protected boolean skipStoredResource(Object object)
	{
		return !(object instanceof AbstractStoredResource);
	}
	
	/**
	 * �������� ��������� ��� ����������� ����������� ����� ���������
	 * @return ���������
	 */
	private DecimalFormat getFormat()
	{
		return new DecimalFormat("#0.00##", new DecimalFormatSymbols(Locale.US));
	}
}
