package com.rssl.phizic.business.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;

import java.util.*;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 * Типы продуктов для риск-профиля
 */
public enum ProductType
{
	account("Вклад", "Вклады", Arrays.asList(DictionaryProductType.ACCOUNT, DictionaryProductType.COMPLEX_INVESTMENT)),
	IMA("ОМС", "ОМС", Arrays.asList(DictionaryProductType.IMA, DictionaryProductType.COMPLEX_INVESTMENT)),
	fund("ПИФ", "ПИФы", Arrays.asList(DictionaryProductType.FUND, DictionaryProductType.COMPLEX_INVESTMENT)),
	insurance("Страховой продукт", "Страхование", Arrays.asList(DictionaryProductType.INSURANCE, DictionaryProductType.PENSION, DictionaryProductType.COMPLEX_INSURANCE)),
	trustManaging("Доверительное управление", "Доверительное управление", Arrays.asList(DictionaryProductType.TRUST_MANAGING));

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
	 * @return название
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return тип продукта
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
	 * Возвращает значение енума по переданному типу продукта из справочника
	 * @param dictionaryProductType - тип продукта из справочника
	 * @return тип продукта
	 */
	public static ProductType getByDictionaryProductTypeSingle(DictionaryProductType dictionaryProductType)
	{
		List<ProductType> productTypeList = getByDictionaryProductType(dictionaryProductType);
		if(productTypeList == null || productTypeList.size() == 0)
			throw new IllegalArgumentException("Для переданного типа продукта нет типа продукта в риск профиле");
		if(productTypeList.size() > 1)
			throw new IllegalArgumentException("Для переданного типа продукта найдено несколько типов продуктов в риск прифиле");
		return productTypeList.get(0);
	}

	/**
	 * Возвращает значение енума по переданному типу
	 * @param type - тип продукта
	 * @return тип продукта
	 */
	public static List<ProductType> getByDictionaryProductType(DictionaryProductType type)
	{
		return dictionaryToProductTypeMapping.get(type);
	}
}
