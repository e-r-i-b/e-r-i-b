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

		// ��������� ������ � ������� ����������� �� �� (��. ��_14, �. 5.5.2.2)
		Map<Long, List<Long>> tariffDependencies = depositProductService.getTariffDependence(entity.getDepositType());

		List<Long> tariffDependenceSubTypes = new ArrayList<Long>();
		// ���� � ������� ����������� ��� ������� ��� ����� ���� - ������, ��� ��������� "�������", � �� "��������" (�.�. ��� ������-���� ����������� ��)
		if (tariffDependencies.isEmpty())
			tariffDependenceSubTypes.addAll(subTypes);
		else
		{
			for (Long subType : subTypes)
			{
				// ���� ������� ��� ������� ���, ������, �� ��������� "�������", �.�. �������� �������� � ����� ��
				if (!tariffDependencies.containsKey(subType))
					tariffDependenceSubTypes.add(subType);
				// ���� ��� ������� ���� ������ � ������� �����������, �� �� �������� ������ �������� � ���������� � ������� ��
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
	 * ������� ������ � ������ �������� ������� (��������� - 1 / �� ��������� - 0), � ����� �������� �����-�����.
	 * ������ � ��������� � ������������ �����-����� ������ ������������ ��������� �������� ���������.
	 * ��������� ������������ �����-���� (���� ��� ������ ������ �������� ��������� ����� ������� � ����������� 0)
	 *
	 * @param entity - �������� �������
	 * @param rateList - ������ ��� ����������
	 * @param entities - ������ �������� ���������, ������������ ������������
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

		// ������� ������
		List<DepositProductRate> pensionRates = new ArrayList<DepositProductRate>();
		// �������� ������ ��� �����������
		List<DepositProductRate> notPensionRates = new ArrayList<DepositProductRate>();
		// ������ � ����������� � ����������� == 0. �������� ��������, ����� ������������ ���������, ���� �� � ������� ���������� ������ (�� �����)
		List<DepositProductRate> promoRates = new ArrayList<DepositProductRate>();
		// ������ � ����������� � ����������� == 1. ����� ������������ ��������� �������� ���������
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
				// ����������� �������� ���� ������ ���� ���� ������ � ������������ �����-����� (�� ������� ���������� ��������)
				// � ������ ������ ���� �� ������. �����-������ - ��� ��������� ������
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
			// ���� ������ �� ������� - �� ���������� ��������
			if (entity.getDepositRates().size() > 0)
				entities.add(entity);
		}
		else if (!notPensionRates.isEmpty())
		{
			if (!actualPromoCodeRates.isEmpty())
				entity.setPromoCodeDeposit(notPriorityCodeMap.get(actualPromoCodeRates.get(0).getSegment()));
			notPensionRates.addAll(actualPromoCodeRates);
			prepareEntityByTariffPlan(entity, notPensionRates, clientTariffPlan, dictionaryTariffPlanCodes, admin);
			// ���� ������ �� ������� - �� ���������� ��������
			if (entity.getDepositRates().size() > 0)
				entities.add(entity);
		}
		// ������������ ��������, ����� ���� ����� � �����-�������� � ����������� 0. � ��� �������/����������
		else if (!actualPromoCodeRates.isEmpty())
		{
			entity.setPromoCodeDeposit(notPriorityCodeMap.get(actualPromoCodeRates.get(0).getSegment()));
			prepareEntityByTariffPlan(entity, actualPromoCodeRates, clientTariffPlan, dictionaryTariffPlanCodes, admin);
			// ���� ������ �� ������� - �� ���������� ��������
			if (entity.getDepositRates().size() > 0)
				entities.add(entity);
		}

	}

	/**
	 * ������� ������ � ���������� �����-����� (�� ���� 16 � 5.11.9 (7.4.1)
	 *
	 * @param rates
	 * @param promoCodes
	 * @return
	 */
	// todo �������� �� ����. ����������� ������� �.�.
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
	 * ������� ������ � ������ ��������� ����� �������.
	 * ���� ��� �� �������, ������� ������ � "�� ������������" �� (�.�. � �����, �� ��������������� �� ������ �� �� �����������)
	 * @param entity - �������� �������
	 * @param rateList - ������ ��� ����������
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
	 * ������� ������ ��������� �������� � ������� �����
	 * @param entity - �������� �������
	 * @param rateList - ������ ��� ����������
	 */
	private void prepareRatesByCurrency(DepositProductEntity entity, List<DepositProductRate> rateList)
	{
		// �������� ���� ������-������
		Map<String, List<DepositProductRate>> currencyRates = new HashMap<String, List<DepositProductRate>>();
		for (DepositProductRate rate : rateList)
		{
			String currencyCode = rate.getCurrencyCode();
			if (!currencyRates.containsKey(currencyCode))
				currencyRates.put(currencyCode, new ArrayList<DepositProductRate>());
			currencyRates.get(currencyCode).add(rate);
		}
		// ���������� ������, ��������� � ������� �����
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
	 * ������� ������ ��������� �������� � ������� ����������� �������
	 * @param rates - ������ � ������� ������
	 */
	private Map<BigDecimal, Map<String, List<DepositProductRate>>> prepareRatesByMinBalance(List<DepositProductRate> rates)
	{
		// �������� ���� �����-������
		Map<BigDecimal, List<DepositProductRate>> minBalanceRates = new HashMap<BigDecimal, List<DepositProductRate>>();
		for (DepositProductRate rate : rates)
		{
			BigDecimal sumBegin = rate.getSumBegin();
			if (!minBalanceRates.containsKey(sumBegin))
				minBalanceRates.put(sumBegin, new ArrayList<DepositProductRate>());
			minBalanceRates.get(sumBegin).add(rate);
		}
		// ���������� ������, ��������� � ������� ���. �������
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
	 * ������� ������ � ������� ������� (� ������ ������� � ����)
	 * @param rateList - ������ � ������� ������ � ���������� ������
	 */
	private Map<String, List<DepositProductRate>> prepareRatesByPeriod(List<DepositProductRate> rateList)
	{
		// �������� ���� ������-������
		Map<String, List<DepositProductRate>> periodInDaysRates = new HashMap<String, List<DepositProductRate>>();
		for (DepositProductRate rate : rateList)
		{
			String period = StringHelper.getZeroIfEmptyOrNull(rate.getDictionaryPeriod());

			if (!periodInDaysRates.containsKey(period))
				periodInDaysRates.put(period, new ArrayList<DepositProductRate>());
			periodInDaysRates.get(period).add(rate);
		}

		// ���������� ������, ��������� � ������� ��������
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
	 * ������� ������ � ������� �������������� ������� � ������� ������������ ������
	 * @param rateList - - ������ � ������� ������, ���������� ������ � ������� � ����
	 * @return ������ ���������� ������
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
	 * ���������� ���������� ������ (�� ������� ��������)
	 * @param rates - ������ � ������� ������, ���������� ������ � �������
	 * @return ������ � �������� ������� ����� ������
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
	 * ������� ������ ���������, ���������� �������� ��������� �������� ��� ������ �������
	 * @param entity - �������� �������
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

		// ������������� �������� � ���������� ������� �����
		Collections.sort(descriptionRows, DepositDescriptionRowComparator.getInstance());

		entity.setDepositDescriptions(descriptionRows);
	}


}
