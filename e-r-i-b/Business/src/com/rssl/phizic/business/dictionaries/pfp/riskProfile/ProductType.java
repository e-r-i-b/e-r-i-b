package com.rssl.phizic.business.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;

import java.util.*;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 * ���� ��������� ��� ����-�������
 */
public enum ProductType
{
	account("�����", "������", Arrays.asList(DictionaryProductType.ACCOUNT, DictionaryProductType.COMPLEX_INVESTMENT)),
	IMA("���", "���", Arrays.asList(DictionaryProductType.IMA, DictionaryProductType.COMPLEX_INVESTMENT)),
	fund("���", "����", Arrays.asList(DictionaryProductType.FUND, DictionaryProductType.COMPLEX_INVESTMENT)),
	insurance("��������� �������", "�����������", Arrays.asList(DictionaryProductType.INSURANCE, DictionaryProductType.PENSION, DictionaryProductType.COMPLEX_INSURANCE)),
	trustManaging("������������� ����������", "������������� ����������", Arrays.asList(DictionaryProductType.TRUST_MANAGING));

	private final String name;
	private final String description;
	private final List<DictionaryProductType> dictionaryProductTypes;

	private static final Map<DictionaryProductType, List<ProductType>> dictionaryToProductTypeMapping = new HashMap<DictionaryProductType, List<ProductType>>(8);

	static
	{
		for (DictionaryProductType dictionaryProductType : DictionaryProductType.values())
		{
			dictionaryToProductTypeMapping.put(dictionaryProductType, getDictionaryToProductTypeMapping(dictionaryProductType));
		}
	}

	private static List<ProductType> getDictionaryToProductTypeMapping(DictionaryProductType type)
	{
		List<ProductType> result = new ArrayList<ProductType>();
		for (ProductType productType : values())
		{
			if (productType.dictionaryProductTypes.contains(type))
			{
				result.add(productType);
			}
		}
		return result;
	}

	/**
	 * @return ��������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return ��������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return ��� ��������
	 */
	public List<DictionaryProductType> getDictionaryProductTypes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return dictionaryProductTypes;
	}

	ProductType(String name, String description, List<DictionaryProductType> dictionaryProductTypes)
	{
		this.name = name;
		this.description = description;
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.dictionaryProductTypes = dictionaryProductTypes;
	}

	/**
	 * ���������� �������� ����� �� ����������� ���� �������� �� �����������
	 * @param dictionaryProductType - ��� �������� �� �����������
	 * @return ��� ��������
	 */
	public static ProductType getByDictionaryProductTypeSingle(DictionaryProductType dictionaryProductType)
	{
		List<ProductType> productTypeList = getByDictionaryProductType(dictionaryProductType);
		if(productTypeList == null || productTypeList.size() == 0)
			throw new IllegalArgumentException("��� ����������� ���� �������� ��� ���� �������� � ���� �������");
		if(productTypeList.size() > 1)
			throw new IllegalArgumentException("��� ����������� ���� �������� ������� ��������� ����� ��������� � ���� �������");
		return productTypeList.get(0);
	}

	/**
	 * ���������� �������� ����� �� ����������� ����
	 * @param type - ��� ��������
	 * @return ��� ��������
	 */
	public static List<ProductType> getByDictionaryProductType(DictionaryProductType type)
	{
		return dictionaryToProductTypeMapping.get(type);
	}
}
