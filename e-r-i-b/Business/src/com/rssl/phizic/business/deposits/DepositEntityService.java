package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.dictionaries.currencies.CurrencyUtils;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.*;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.BooleanUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author EgorovaA
 * @ created 10.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositEntityService extends MultiInstanceDepositProductService
{
	private static final DepositProductService depositProductService = new DepositProductService();
	private static final DepositEntityVisibilityService visibilityServiceService = new DepositEntityVisibilityService();

	public List<DepositProductEntity> getDepositEntities(List<Long> depositTypeList, List<PromoCodeDeposit> promoCodes, List<Long> dictionaryTariffPlanCodes, boolean pensioner, Long clientTariffPlan, boolean admin, String tb, boolean showWithInitialFeeOnly) throws BusinessException
	{
		List<DepositProductEntity> allTypesEntities = depositProductService.getDepositEntities(depositTypeList);
		List<DepositProductEntity> entities = new ArrayList<DepositProductEntity>();

		if (showWithInitialFeeOnly)
		{
			for (DepositProductEntity entity : allTypesEntities)
			{
				if (entity.getInitialFee())
					entities.add(entity);
			}
		}
		else
			entities.addAll(allTypesEntities);

		List<DepositProductEntity> preparedEntities = new ArrayList<DepositProductEntity>();

		for (DepositProductEntity entity : entities)
		{
			prepareEntity(entity, preparedEntities, promoCodes, dictionaryTariffPlanCodes, pensioner, clientTariffPlan, admin, tb);
		}

		for (DepositProductEntity entity : preparedEntities)
		{
			setDepositEntityName(entity);
		}
		for (DepositProductEntity entity : preparedEntities)
		{
			prepareDepositDescription(entity);
		}
		Collections.sort(preparedEntities, new DepositEntityComparator());

		return preparedEntities;
	}

	public DepositProductEntity getDepositProductEntity(Long depositType, Long depositGroup, List<PromoCodeDeposit> promoCodes, List<Long> dictionaryTariffPlanCodes, boolean pensioner, Long clientTariffPlan, boolean admin, String tb) throws BusinessException
	{
		DepositProductEntity entity = depositProductService.findDepositEntity(depositType, depositGroup);
		List<DepositProductEntity> preparedEntities = new ArrayList<DepositProductEntity>();
		prepareEntity(entity, preparedEntities, promoCodes, dictionaryTariffPlanCodes, pensioner, clientTariffPlan, admin, tb);

		if (preparedEntities.isEmpty())
			return null;
		DepositProductEntity productEntity = preparedEntities.get(0);
		setDepositEntityName(productEntity);
		prepareDepositDescription(productEntity);

		return productEntity;
	}

	private void setDepositEntityName(DepositProductEntity entity) throws BusinessException
	{
		if (entity.getGroupCode() != 0)
			return;

		String name = depositProductService.getDepositEntityName(entity.getDepositType());
		entity.setGroupName(name);
	}

	private void prepareEntity(DepositProductEntity entity, List<DepositProductEntity> preparedEntities, List<PromoCodeDeposit> promoCodes, List<Long> dictionaryTariffPlanCodes, boolean pensioner, Long clientTariffPlan, boolean admin, String tb) throws BusinessException
	{
		List<Long> subTypes = new ArrayList<Long>();
		if (admin || StringHelper.isEmpty(tb))
			subTypes = visibilityServiceService.getAllDepositSubTypes(entity.getDepositType(), entity.getGroupCode());
		else
			subTypes = visibilityServiceService.getDepositsSubTypesByTb(entity.getDepositType(), entity.getGroupCode(), tb);

		if (subTypes.isEmpty())
			return;

		// Проверяем записи в таблице зависимости от ТП (см. РО_14, п. 5.5.2.2)
		Map<Long, List<Long>> tariffDependencies = depositProductService.getTariffDependence(entity.getDepositType());

		List<Long> tariffDependenceSubTypes = new ArrayList<Long>();
		// если в таблице зависимости нет записей для этого вида - значит, вид считается "обычным", а не "тарифным" (т.е. для какого-либо конкретного ТП)
		if (tariffDependencies.isEmpty())
			tariffDependenceSubTypes.addAll(subTypes);
		else
		{
			for (Long subType : subTypes)
			{
				// Если записей для подвида нет, значит, он считается "обычным", т.е. доступен клиентам с любым ТП
				if (!tariffDependencies.containsKey(subType))
					tariffDependenceSubTypes.add(subType);
				// если для подвида есть запись в таблице зависимости, то он доступен только клиентам с указанными в таблице ТП
				else if (tariffDependencies.get(subType).contains(clientTariffPlan))
					tariffDependenceSubTypes.add(subType);
			}
		}

		if (tariffDependenceSubTypes.isEmpty())
			return;

		List<DepositProductRate> rateList = depositProductService.findDepositEntityRates(entity.getDepositType(), tariffDependenceSubTypes);
		entity.setDebitOperations(depositProductService.getDebitAllowed(entity.getDepositType(), tariffDependenceSubTypes));
		entity.setCreditOperations(depositProductService.getCreditAllowed(entity.getDepositType(), tariffDependenceSubTypes));
		entity.setInterestOperations(depositProductService.getInterestAllowed(entity.getDepositType(), tariffDependenceSubTypes));
		prepareDepositEntityBySegment(entity, rateList, preparedEntities, promoCodes, dictionaryTariffPlanCodes, pensioner, clientTariffPlan, admin);
	}

	/**
	 * Выбрать ставки с учетом сегмента клиента (пенсионер - 1 / не пенсионер - 0), а также сегметов промо-кодов.
	 * Ставки с сегментом с приоритетным промо-кодом должны отображаться отдельным вкладным продуктом.
	 * Проверяем актуальность промо-кода (если для одного вклада подходят несколько кодов клиента с приоритетом 0)
	 *
	 * @param entity - вкладной продукт
	 * @param rateList - ставки для фильтрации
	 * @param entities - список вкладных продуктов, отображаемых пользователю
	 * @throws BusinessException
	 */
	private void prepareDepositEntityBySegment(DepositProductEntity entity, List<DepositProductRate> rateList, List<DepositProductEntity> entities, List<PromoCodeDeposit> promoCodes, List<Long> dictionaryTariffPlanCodes, boolean pensioner, Long clientTariffPlan, boolean admin) throws BusinessException
	{
		boolean allowPromoRates = BooleanUtils.isTrue(entity.getPromoCodeSupported());

		Map<Long, PromoCodeDeposit> priorityCodeMap = new HashMap<Long, PromoCodeDeposit>();
		Map<Long, PromoCodeDeposit> notPriorityCodeMap = new HashMap<Long, PromoCodeDeposit>();

		for (PromoCodeDeposit promoCodeDeposit : promoCodes)
		{
			if (promoCodeDeposit.getPrior() == 1)
				priorityCodeMap.put(promoCodeDeposit.getCodeS(), promoCodeDeposit);
			else
				notPriorityCodeMap.put(promoCodeDeposit.getCodeS(), promoCodeDeposit);
		}

		// обычные ставки
		List<DepositProductRate> pensionRates = new ArrayList<DepositProductRate>();
		// льготные ставки для пенсионеров
		List<DepositProductRate> notPensionRates = new ArrayList<DepositProductRate>();
		// ставки с промокодами с приоритетом == 0. Собираем отдельно, чтобы впоследствии проверить, есть ли у клиента пенсионные ставки (не промо)
		List<DepositProductRate> promoRates = new ArrayList<DepositProductRate>();
		// ставки с промокодами с приоритетом == 1. Будут отображаться отдельным вкладным продуктом
		Map<Long, List<DepositProductRate>> promoDepositRates = new HashMap<Long, List<DepositProductRate>>();

		for (DepositProductRate rate : rateList)
		{
			Long rateSegment = rate.getSegment();

			if (allowPromoRates && priorityCodeMap.containsKey(rateSegment))
			{
				if (!promoDepositRates.containsKey(rateSegment))
					promoDepositRates.put(rateSegment, new ArrayList<DepositProductRate>());
				promoDepositRates.get(rateSegment).add(rate);
			}
			if (allowPromoRates && notPriorityCodeMap.containsKey(rateSegment))
				promoRates.add(rate);
			if (rate.getSegment() == 1)
				pensionRates.add(rate);
			if (rate.getSegment() == 0)
				notPensionRates.add(rate);
		}

		List<DepositProductRate> actualPromoCodeRates = getActualPromoCodeRates(promoRates, promoCodes);

		if (!promoDepositRates.isEmpty())
		{
			for (Long promoCode : promoDepositRates.keySet())
			{
				// клонировать сущность надо ТОЛЬКО если есть ставки с приоритетным промо-кодом (на радость резвящимся тестерам)
				// а вообще такого быть не должно. Промо-вклады - это отдельные группы
				if (!pensionRates.isEmpty() || !notPensionRates.isEmpty() || !actualPromoCodeRates.isEmpty())
				{
					DepositProductEntity promoEntity = entity.clone();
					promoEntity.setPromoCodeDeposit(priorityCodeMap.get(promoCode));
					prepareEntityByTariffPlan(promoEntity, promoDepositRates.get(promoCode), clientTariffPlan, dictionaryTariffPlanCodes, admin);
					entities.add(promoEntity);
				}
				else
				{
					entity.setPromoCodeDeposit(priorityCodeMap.get(promoCode));
					prepareEntityByTariffPlan(entity, promoDepositRates.get(promoCode), clientTariffPlan, dictionaryTariffPlanCodes, admin);
					entities.add(entity);
					return;
				}
			}
		}
		if (admin)
		{
			List<DepositProductRate> adminPensionRates = new ArrayList<DepositProductRate>();
			adminPensionRates.addAll(pensionRates);
			adminPensionRates.addAll(notPensionRates);
			prepareEntityByTariffPlan(entity, adminPensionRates, clientTariffPlan, dictionaryTariffPlanCodes, admin);
			if (entity.getDepositRates().size() > 0)
				entities.add(entity);
		}
		else if (!pensionRates.isEmpty() && pensioner && BooleanUtils.isNotFalse(entity.getAllowPensionRates()))
		{
			if (!actualPromoCodeRates.isEmpty())
				entity.setPromoCodeDeposit(notPriorityCodeMap.get(actualPromoCodeRates.get(0).getSegment()));
			pensionRates.addAll(actualPromoCodeRates);
			prepareEntityByTariffPlan(entity, pensionRates, clientTariffPlan, dictionaryTariffPlanCodes, admin);
			// Если ставки не найдены - не отображаем сущность
			if (entity.getDepositRates().size() > 0)
				entities.add(entity);
		}
		else if (!notPensionRates.isEmpty())
		{
			if (!actualPromoCodeRates.isEmpty())
				entity.setPromoCodeDeposit(notPriorityCodeMap.get(actualPromoCodeRates.get(0).getSegment()));
			notPensionRates.addAll(actualPromoCodeRates);
			prepareEntityByTariffPlan(entity, notPensionRates, clientTariffPlan, dictionaryTariffPlanCodes, admin);
			// Если ставки не найдены - не отображаем сущность
			if (entity.getDepositRates().size() > 0)
				entities.add(entity);
		}
		// предусмотрим ситуацию, когда есть вклад с промо-ставками с приоритетом 0. И без обычных/пенсионных
		else if (!actualPromoCodeRates.isEmpty())
		{
			entity.setPromoCodeDeposit(notPriorityCodeMap.get(actualPromoCodeRates.get(0).getSegment()));
			prepareEntityByTariffPlan(entity, actualPromoCodeRates, clientTariffPlan, dictionaryTariffPlanCodes, admin);
			// Если ставки не найдены - не отображаем сущность
			if (entity.getDepositRates().size() > 0)
				entities.add(entity);
		}

	}

	/**
	 * Выбрать ставки с актуальным промо-кодом (РО ЕРИБ 16 п 5.11.9 (7.4.1)
	 *
	 * @param rates
	 * @param promoCodes
	 * @return
	 */
	// todo заменить на мапу. Исполнитель Егорова А.В.
	private List<DepositProductRate> getActualPromoCodeRates(List<DepositProductRate> rates, List<PromoCodeDeposit> promoCodes)
	{
		Long actualPromoCode = null;
		for (PromoCodeDeposit promoCode : promoCodes)
		{
			Long code = promoCode.getCodeS();
			for (DepositProductRate rate : rates)
			{
				if (rate.getSegment().equals(code))
				{
					actualPromoCode = code;
					break;
				}
			}
		}

		List<DepositProductRate> actualPromoCodeRates = new ArrayList<DepositProductRate>();
		for (DepositProductRate rate : rates)
		{
			if (rate.getSegment().equals(actualPromoCode))
				actualPromoCodeRates.add(rate);
		}
		return actualPromoCodeRates;
	}

	/**
	 * Выбрать ставки с учетом тарифного плана клиента.
	 * Если они не найдены, выбрать ставки с "не определенным" ТП (т.е. с кодом, не соответствующим ни одному ТП из справочника)
	 * @param entity - вкладной продукт
	 * @param rateList - ставки для фильтрации
	 */
	private void prepareEntityByTariffPlan(DepositProductEntity entity, List<DepositProductRate> rateList, Long tariffPlanCode, List<Long> dictionaryTariffPlanCodes, boolean admin)
	{
		List<DepositProductRate> clientTariffPlanRates = new ArrayList<DepositProductRate>();
		List<DepositProductRate> notTariffPlanRates = new ArrayList<DepositProductRate>();

		if (!admin)
		{
			for (DepositProductRate rate : rateList)
			{
				if (rate.getClientCategory() != 0 && rate.getClientCategory().equals(tariffPlanCode))
					clientTariffPlanRates.add(rate);
				else if (!dictionaryTariffPlanCodes.contains(rate.getClientCategory()) || rate.getClientCategory() == 0)
					notTariffPlanRates.add(rate);
			}

			if (!clientTariffPlanRates.isEmpty())
				prepareRatesByCurrency(entity, clientTariffPlanRates);
			else
				prepareRatesByCurrency(entity, notTariffPlanRates);
		}
		else
		{
			if (tariffPlanCode == null)
			{
				notTariffPlanRates.addAll(rateList);
				prepareRatesByCurrency(entity, notTariffPlanRates);
			}
			else if (tariffPlanCode.equals(0L))
			{
				for (DepositProductRate rate : rateList)
				{
					if (!dictionaryTariffPlanCodes.contains(rate.getClientCategory()) || rate.getClientCategory() == 0)
						notTariffPlanRates.add(rate);
				}
				prepareRatesByCurrency(entity, notTariffPlanRates);
			}
			else
			{
				for (DepositProductRate rate : rateList)
				{
					if (rate.getClientCategory().equals(tariffPlanCode))
						clientTariffPlanRates.add(rate);
				}
				prepareRatesByCurrency(entity, clientTariffPlanRates);
			}
		}

	}

	/**
	 * Выбрать ставки вкладного продукта в разрезе валют
	 * @param entity - вкладной продукт
	 * @param rateList - ставки для фильтрации
	 */
	private void prepareRatesByCurrency(DepositProductEntity entity, List<DepositProductRate> rateList)
	{
		// Составим мапу валюта-ставки
		Map<String, List<DepositProductRate>> currencyRates = new HashMap<String, List<DepositProductRate>>();
		for (DepositProductRate rate : rateList)
		{
			String currencyCode = rate.getCurrencyCode();
			if (!currencyRates.containsKey(currencyCode))
				currencyRates.put(currencyCode, new ArrayList<DepositProductRate>());
			currencyRates.get(currencyCode).add(rate);
		}
		// Обработать ставки, собранные в разрезе валют
		SortedMap<String, Map<BigDecimal, Map<String, List<DepositProductRate>>>> preparedCurrencyRates = new TreeMap<String, Map<BigDecimal, Map<String, List<DepositProductRate>>>>(DepositEntityCurrencyComparator.getInstance());
		for (String key : currencyRates.keySet())
		{
			List<DepositProductRate> curRates = currencyRates.get(key);
			String currencyCode = CurrencyUtils.getCurrencyCodeByNumericCode(key);

			if(StringHelper.isEmpty(currencyCode))
				continue;

			if (!preparedCurrencyRates.containsKey(currencyCode))
				preparedCurrencyRates.put(currencyCode, new TreeMap<BigDecimal, Map<String, List<DepositProductRate>>>());
			preparedCurrencyRates.get(currencyCode).putAll(prepareRatesByMinBalance(curRates));
		}
		entity.setDepositRates(preparedCurrencyRates);
	}

	/**
	 * Выбрать ставки вкладного продукта в разрезе минимальных взносов
	 * @param rates - ставки в разрезе валюты
	 */
	private Map<BigDecimal, Map<String, List<DepositProductRate>>> prepareRatesByMinBalance(List<DepositProductRate> rates)
	{
		// Составим мапу взнос-ставки
		Map<BigDecimal, List<DepositProductRate>> minBalanceRates = new HashMap<BigDecimal, List<DepositProductRate>>();
		for (DepositProductRate rate : rates)
		{
			BigDecimal sumBegin = rate.getSumBegin();
			if (!minBalanceRates.containsKey(sumBegin))
				minBalanceRates.put(sumBegin, new ArrayList<DepositProductRate>());
			minBalanceRates.get(sumBegin).add(rate);
		}
		// Обработать ставки, собранные в разрезе мин. взносов
		SortedMap<BigDecimal, Map<String, List<DepositProductRate>>> preparedMinBalanceRates =
				new TreeMap<BigDecimal, Map<String, List<DepositProductRate>>>(new Comparator<BigDecimal>() {
					public int compare(BigDecimal o1, BigDecimal o2)
					{
						return o1.compareTo(o2);
					}});
		for (BigDecimal key : minBalanceRates.keySet())
		{
			List<DepositProductRate> sumBeginRates = minBalanceRates.get(key);

			if (!preparedMinBalanceRates.containsKey(key))
				preparedMinBalanceRates.put(key, new HashMap<String, List<DepositProductRate>>());
			preparedMinBalanceRates.get(key).putAll(prepareRatesByPeriod(sumBeginRates));
		}
		return preparedMinBalanceRates;
	}

	/**
	 * Собрать ставки в разрезе периода (с учетом периода в днях)
	 * @param rateList - ставки в разрезе валюты и начального взноса
	 */
	private Map<String, List<DepositProductRate>> prepareRatesByPeriod(List<DepositProductRate> rateList)
	{
		// Составим мапу период-ставки
		Map<String, List<DepositProductRate>> periodInDaysRates = new HashMap<String, List<DepositProductRate>>();
		for (DepositProductRate rate : rateList)
		{
			String period = StringHelper.getZeroIfEmptyOrNull(rate.getDictionaryPeriod());

			if (!periodInDaysRates.containsKey(period))
				periodInDaysRates.put(period, new ArrayList<DepositProductRate>());
			periodInDaysRates.get(period).add(rate);
		}

		// Обработать ставки, собранные в разрезе периодов
		Map<String, List<DepositProductRate>> preparedPeriodsRates = new HashMap<String, List<DepositProductRate>>();
		for (String key : periodInDaysRates.keySet())
		{
			List<DepositProductRate> sumBeginRates = periodInDaysRates.get(key);
			List<DepositProductRate> actualRate = prepareRatesByBuhPeriod(sumBeginRates);
			String periodKey = StringHelper.isEmpty(actualRate.get(0).getPeriod()) ? key : actualRate.get(0).getPeriod();
			preparedPeriodsRates.put(periodKey, actualRate);
		}
		return preparedPeriodsRates;
	}

	/**
	 * Собрать ставки в разрезе бухгалтерского периода и отсеять исторические ставки
	 * @param rateList - - ставки в разрезе валюты, начального взноса и периода в днях
	 * @return список актуальных ставок
	 */
	private List<DepositProductRate> prepareRatesByBuhPeriod(List<DepositProductRate> rateList)
	{
		List<DepositProductRate> actualRate = prepareActualRates(rateList);
		for (DepositProductRate rate : actualRate)
		{
			rate.setPeriod(DepositEntityHelper.parsePeriod(rate.getDictionaryPeriod()));
		}
		return actualRate;
	}

	/**
	 * Определить актуальную ставку (по времени действия)
	 * @param rates - ставки в разрезе валюты, начального взноса и периода
	 * @return ставка с наиболее поздней датой начала
	 */
	private List<DepositProductRate> prepareActualRates(List<DepositProductRate> rates)
	{
		Set<Long> subTypes = new HashSet<Long>();
		for (DepositProductRate rate : rates)
		{
			subTypes.add(rate.getDepositSubType());
		}
		Collections.sort(rates, new Comparator<DepositProductRate>()
		{
			public int compare(DepositProductRate o1, DepositProductRate o2)
			{
				int compareResult = o2.getSegment().compareTo(o1.getSegment());
				if(compareResult != 0)
					return compareResult;
				return o2.getDateBegin().compareTo(o1.getDateBegin());
			}
		});
		List<DepositProductRate> result = new ArrayList<DepositProductRate>();
		for (Long subType : subTypes)
		{
			for (DepositProductRate rate: rates)
			{
				if (subType.equals(rate.getDepositSubType()))
				{
					result.add(rate);
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Собрать список сущностей, содержащих описание вкладного продукта для списка вкладов
	 * @param entity - вкладной продукт
	 */
	private void prepareDepositDescription(DepositProductEntity entity)
	{
		List<DepositDescriptionRow> descriptionRows = new ArrayList<DepositDescriptionRow>();
		Set<String> periods = new HashSet<String>();
		for (String currency : entity.getDepositRates().keySet())
		{
			if (StringHelper.isEmpty(currency))
				continue;
			DepositDescriptionRow descriptionRow = new DepositDescriptionRow();
			descriptionRow.setCurrency(currency);

			Map<BigDecimal, Map<String, List<DepositProductRate>>> minBalances = entity.getDepositRates().get(currency);
			List<BigDecimal> sumBeginList = new ArrayList<BigDecimal>();
			sumBeginList.addAll(minBalances.keySet());
			Collections.sort(sumBeginList);
			descriptionRow.setSumBegin(sumBeginList.get(0));

			List<BigDecimal> actualRates = new ArrayList<BigDecimal>();

			for (BigDecimal sumBegin : sumBeginList)
			{
				Map<String, List<DepositProductRate>> map  = minBalances.get(sumBegin);
				for (List<DepositProductRate> rates : map.values())
				{
					for (DepositProductRate rate : rates)
					{
						if (StringHelper.isNotEmpty(rate.getPeriod()))
							periods.add(rate.getPeriod());
						actualRates.add(rate.getBaseRate());
						if (rate.getSegment() != 0 && rate.getSegment() != 1)
							descriptionRow.setPromo(true);
					}
				}
			}
			Collections.sort(actualRates);
			descriptionRow.setMinRate(actualRates.get(0));
			descriptionRow.setMaxRate(actualRates.get(actualRates.size() - 1));
			descriptionRows.add(descriptionRow);
		}
		List<String> periodList = new ArrayList<String>();
		periodList.addAll(periods);
		Collections.sort(periodList);
		entity.setPeriodList(periodList);

		// отсортировать описание в правильном порядке валют
		Collections.sort(descriptionRows, DepositDescriptionRowComparator.getInstance());

		entity.setDepositDescriptions(descriptionRows);
	}


}
