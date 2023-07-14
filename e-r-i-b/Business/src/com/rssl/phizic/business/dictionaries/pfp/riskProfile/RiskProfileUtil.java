package com.rssl.phizic.business.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.common.types.SegmentCodeType;
import org.apache.commons.lang.math.NumberUtils;

import java.util.*;

/**
 * @author akrenev
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����� �� ������ � ����-��������
 */

public class RiskProfileUtil
{
	private static final List<ProductType> PRODUCT_TYPES;
	private static final int PRODUCT_TYPES_SUM = 100;
	private static final RiskProfileService riskProfileService = new RiskProfileService();
	private static final AgeCategoryService ageCategoryService = new AgeCategoryService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	static
	{
		List<ProductType> list = Arrays.asList(ProductType.values());
		PRODUCT_TYPES = Collections.unmodifiableList(list);
	}

	private static String getDBInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	/**
	 * @return ������ ��������� �� ������� ����� ����������� ��������
	 */
	public static List<ProductType> getProductTypes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return PRODUCT_TYPES;
	}

	/**
	 * @return ����� ���� � ��������� ��������� (�� ��������� == 0%) �� ���������
	 */
	public static Map<ProductType, Long> getNewProductWeightsMap()
	{
		Map<ProductType, Long> map = new HashMap<ProductType, Long>();
		for (ProductType type: PRODUCT_TYPES)
			map.put(type, NumberUtils.LONG_ZERO);
		return map;
	}

	/**
	 * @param map ���� � ��������� ��������� �� ���������
	 * @return ����� �������� == PRODUCT_TYPES_SUM
	 */
	public static boolean checkProductWeightsMap(Map<ProductType, Long> map)
	{
		long sum = 0;
		for (Map.Entry<ProductType, Long> entry: map.entrySet())
			sum += entry.getValue();

		return sum == PRODUCT_TYPES_SUM;
	}

	/**
	 * ���������� ��� ����-�������
	 * @return ������ ����-��������
	 */
	public static List<RiskProfile> getAllRiskProfiles()
	{
		try
		{
			return riskProfileService.getAllRiskProfiles();
		}
		catch (BusinessException be)
		{
			log.error("������ ��������� ������ ����-��������", be);
			return Collections.emptyList();
		}
	}

	/**
	 * @param segment ������� ��� �������� ������������ ��������
	 * @return ���� �� ���� � ���������� ����-��������
	 */
	public static final boolean isConcertedRiskProfiles(SegmentCodeType segment)
	{
		try
		{
			return riskProfileService.isConcertedRiskProfiles(segment, getDBInstanceName());
		}
		catch (Exception ignore)
		{
			return true;
		}
	}

	/**
	 * @return ���� �� ���� � ���������� ���������� ���������
	 */
	public static final boolean isConcertedAgeCategories()
	{
		try
		{
			return ageCategoryService.isConcertedAgeCategories(getDBInstanceName());
		}
		catch (Exception ignore)
		{
			return true;
		}
	}

}
