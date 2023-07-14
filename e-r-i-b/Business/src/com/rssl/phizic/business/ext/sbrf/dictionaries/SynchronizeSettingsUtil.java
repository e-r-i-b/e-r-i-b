package com.rssl.phizic.business.ext.sbrf.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.MinMax;
import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.CardsConfig;
import com.rssl.phizic.utils.IMAConfig;
import com.rssl.phizic.utils.MapUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * ��������������� ����� ��� ������ � ����������� �������� ������������
 * @author Pankin
 * @ created 23.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class SynchronizeSettingsUtil
{
	public static final String EMPTY_PROPERTY = "empty";
	private static final char DEPOSIT_PRODUCT_SEPARATOR = ',';
	private static final char DEPOSIT_PRODUCT_SUBPRODUCT_SEPARATOR = ':';
	private static final char DEPOSIT_SUBPRODUCT_SEPARATOR = '.';
	private static final char CARD_PRODUCT_SEPARATOR = ';';
	private static final char CARD_PRODUCT_SUBPRODUCT_SEPARATOR = '.';
	private static final char CARD_SUBPRODUCT_SEPARATOR = ',';
	private static final char IMA_PRODUCT_SEPARATOR = ';';
	private static final char IMA_PRODUCT_SUBPRODUCT_SEPARATOR = '.';
	private static final char IMA_SUBPRODUCT_SEPARATOR = ',';

	/**
	 * ��������� ������ � �������� ����������� ����� �������
	 * @param depositProductKinds ������ � �������� ����� ������� ��� �������� ����� �������
	 * @return ���� �������
	 */
	public static Map<Long, List<Long>> readAllowedDepositProductMap(String depositProductKinds)
	{
		return readAllowedProducts(
				depositProductKinds,
				DEPOSIT_PRODUCT_SEPARATOR,
				DEPOSIT_PRODUCT_SUBPRODUCT_SEPARATOR,
				DEPOSIT_SUBPRODUCT_SEPARATOR
		);
	}

	/**
	 * �������� ��������� ������������� ������ ����������� ����� �������
	 * @param depositProductKinds ��������� ��������
	 * @return ������ � �������� ����� ������� ����� �������
	 */
	public static String getAllowedDepositProducts(Map<Long, List<Long>> depositProductKinds)
	{
		return getAllowedProducts(depositProductKinds,
				DEPOSIT_PRODUCT_SEPARATOR,
				DEPOSIT_PRODUCT_SUBPRODUCT_SEPARATOR,
				DEPOSIT_SUBPRODUCT_SEPARATOR
		);
	}

	/**
	 * ���������� ������ �� ������ � ������� ������� ���������� ��������
	 */
	public static MinMax<Long> getMinMaxCardProductKindValues() throws BusinessException
	{
		String[] allowedCardProductKinds = ConfigFactory.getConfig(CardsConfig.class).getCardTypes();// ������ �������� ���������� ����� ����, ���������� � ���� ���� ����� ����� "-"
		if (allowedCardProductKinds != null && allowedCardProductKinds.length == 2)
		{
			return new MinMax<Long>(Long.parseLong(allowedCardProductKinds[0].trim()), Long.parseLong(allowedCardProductKinds[1].trim()));
		}
		throw new BusinessException("������ ��������� ��������� ���������� �������� ����");
	}

	/**
	 * �������� ������ ����� ������, ��������������� ���
	 * @return ������ � ��������
	 */
	public static List<Long> getIMAProductTypeValues()
	{
		String[] allowedIMAProductTypes = ConfigFactory.getConfig(IMAConfig.class).getImaTypes();
		List<Long> result = new ArrayList<Long>();
		if (ArrayUtils.isNotEmpty(allowedIMAProductTypes))
		{
			for (String type : allowedIMAProductTypes)
			{
				result.add(Long.parseLong(type));
			}
		}
		return result;
	}

	/**
	 * ��������� ������ � �������� ����� � �������� �������, ���������� ���.
	 * @param imaProductKinds ������ ���� "���.������,������;���.������;���;���.������"
	 * @return ����� < ���, ������ �������� >; ���� ������� �� �������, �� ������ ������.
	 */
	public static Map<Long, List<Long>> readAllowedIMAProducts(String imaProductKinds)
	{
		return readAllowedProducts(
				imaProductKinds,
				IMA_PRODUCT_SEPARATOR,
				IMA_PRODUCT_SUBPRODUCT_SEPARATOR,
				IMA_SUBPRODUCT_SEPARATOR
		);
	}

	/**
	 * �������� ��������� ������������� ������ ����������� �����/�������� ���
	 * @param depositProductKinds ��������� ��������
	 * @return ������ � �������� ����� �������
	 */
	public static String getAllowedIMAProducts(Map<Long, List<Long>> depositProductKinds)
	{
		return getAllowedProducts(depositProductKinds,
				IMA_PRODUCT_SEPARATOR,
				IMA_PRODUCT_SUBPRODUCT_SEPARATOR,
				IMA_SUBPRODUCT_SEPARATOR
		);
	}

	/**
	 * ��������� ������ � �������� ����� � �������� �������, ���������� ����������.
	 * @param cardProductKinds ������ ���� "���.������,������;���.������;���;���.������"
	 * @return ����� < ���, ������ �������� >; ���� ������� �� �������, �� ������ ������.
	 */
	public static Map<Long, List<Long>> readAllowedCardProducts(String cardProductKinds)
	{
		return readAllowedProducts(
				cardProductKinds,
				CARD_PRODUCT_SEPARATOR,
				CARD_PRODUCT_SUBPRODUCT_SEPARATOR,
				CARD_SUBPRODUCT_SEPARATOR
		);
	}

	/**
	 * ������ ������ ���������/������������ � ����
	 * @param productKinds ������
	 * @param prodSep ����������� ���������
	 * @param prodSubprodSep ����������� ������� - ����������
	 * @param subprodSep ����������� ����� �������������
	 * @return ����������� ����
	 */
	private static Map<Long, List<Long>> readAllowedProducts(String productKinds,
	                                                         char prodSep, char prodSubprodSep, char subprodSep)
	{
		if (EMPTY_PROPERTY.equals(productKinds) || StringHelper.isEmpty(productKinds))
			return Collections.emptyMap();

		Map<Long, List<Long>> result = new TreeMap<Long, List<Long>>();

		Map<String, String> mapString = MapUtil.parse(productKinds,
				String.valueOf(prodSubprodSep),
				String.valueOf(prodSep)
		);
		for (Map.Entry<String, String> entry : mapString.entrySet())
		{
			Long kind = Long.parseLong(entry.getKey().trim());
			List<Long> subkinds = new LinkedList<Long>();
			if (!StringHelper.isEmpty(entry.getValue()))
			{
				String[] subkindArray = entry.getValue().split(escapeRegexSymbol(subprodSep));
				for (int i = 0; i < subkindArray.length; i++) {
					Long val = Long.parseLong(subkindArray[i].trim());
					if(!subkinds.contains(val))
						subkinds.add(val);
				}
			}

			result.put(kind, subkinds);
		}

		return result;
	}

	/**
	 * �������� ������ �� ��� ���, ����� �� ��������� ����������� regex-�������� �
	 * String#split(String regex)
	 * @param symbol ������
	 * @return ������ \\u<utf-��� �������>
	 */
	private static String escapeRegexSymbol(char symbol)
	{
		return String.format("\\u%04X", (int)symbol);	
	}

	/**
	 * ������������ ������ ����������� ���������/������������ �� ����
	 * @param map ����
	 * @param prodSep ����������� ���������
	 * @param prodSubprodSep ����������� ������� - ����������
	 * @param subprodSep ����������� ����� �������������
	 * @return �������������� ������ (��� <empty>, ���� ���� �����)
	 */
	private static String getAllowedProducts(Map<Long, List<Long>> map,
	                                         char prodSep, char prodSubprodSep, char subprodSep)
	{
		StringBuilder sb = new StringBuilder();
		Iterator<Map.Entry<Long, List<Long>>> iter = map.entrySet().iterator();

		if (!iter.hasNext())
			return EMPTY_PROPERTY; //null � properties ���� �� ������

		appendKindAndSubkinds(sb, iter.next(), prodSubprodSep, subprodSep);

		while (iter.hasNext())
		{
			sb.append(prodSep);
			appendKindAndSubkinds(sb, iter.next(), prodSubprodSep, subprodSep);
		}

		return sb.toString();
	}

	/**
	 * ��������� ������ ��� �������� �� ����
	 * @param sb ������
	 * @param entry ������ ����
	 * @param prodSubprodSep ����������� ������� - ����������
	 * @param subprodSep ����������� ������������
	 */
	private static void appendKindAndSubkinds(StringBuilder sb, Map.Entry<Long, List<Long>> entry,
	                                          char prodSubprodSep, char subprodSep)
	{
		sb.append(entry.getKey());
		if (!CollectionUtils.isEmpty(entry.getValue()))
		{
			sb.append(prodSubprodSep);
			sb.append(StringUtils.join(entry.getValue(), subprodSep));
		}
	}

	/**
	 * ����������� ������ ����� ���� ���:������ � ���� ��� - ������ ��������
	 * @param records ������ ����� ���� ���:������
	 * @return ���� ��� - ������ ��������, ��� ������ ����, ���� ������ ����
	 */
	public static Map<Long,List<Long>> convertDepositProductList(String[] records)
	{
		if(records == null || records.length == 0)
			return Collections.emptyMap();
		Map<Long,List<Long>> result = new HashMap<Long,List<Long>>();
		for(String record : records)
		{
			String[] pieces = record.split(escapeRegexSymbol(DEPOSIT_PRODUCT_SUBPRODUCT_SEPARATOR));
			Long key = Long.valueOf(pieces[0].trim());
			if(pieces.length<2)
			{
				result.put(key,null);
				continue;
			}
			Long value = Long.valueOf(pieces[1].trim());
			List<Long> currList = result.get(key);
			if(currList == null)
			{
				currList = new ArrayList<Long>();
				result.put(key,currList);
			}
			currList.add(value);
		}
		return result;
	}
}
