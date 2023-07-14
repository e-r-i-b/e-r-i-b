package com.rssl.phizic.business.pfp.portfolio.product;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 11.04.2012
 * @ $Author$
 * @ $Revision$
 */
//Базовый продукт для пфп: одно из: Страхование, Вклад, ОМС, ПИФ.
//с учетом суммы, которую клиент готов вложить в данный продукт
public class BaseProduct
{
	private static final String DESCRIPTION_FIELD_NAME = "description";
	private Long id;
	private DictionaryProductType productType;//тип продукта
	private String productName;     //название базового продукта
	private Money amount;           //сумма вложенний в данный продукт
	private BigDecimal income;      //доходность продукта, выбранная клиентом.
	private Long dictionaryProductId; //идентификатор продукта в справочнике, на основе которого был создан данный продукт
	private Map<String, PfpProductExtendedField> productExtendedFields = new HashMap<String, PfpProductExtendedField>();//дополнительные поля

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public DictionaryProductType getProductType()
	{
		return productType;
	}

	public void setProductType(DictionaryProductType productType)
	{
		this.productType = productType;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public Long getDictionaryProductId()
	{
		return dictionaryProductId;
	}

	public void setDictionaryProductId(Long dictionaryProductId)
	{
		this.dictionaryProductId = dictionaryProductId;
	}

	public BigDecimal getIncome()
	{
		return income;
	}

	public void setIncome(BigDecimal income)
	{
		this.income = income;
	}

	/**
	 * @return описание базового продукта
	 */
	public String getDescription()
	{
		return getExtendedFieldStringValue(DESCRIPTION_FIELD_NAME);
	}

	/**
	 * задать описание базового продукта
	 * @param description описание базового продукта
	 */
	public void setDescription(String description)
	{
		addExtendedField(DESCRIPTION_FIELD_NAME, description);
	}

	/**
	 * Получить значение дополнительного поля
	 * @param key - ключ
	 * @return
	 */
	public Long getExtendedFieldLongValue(String key)
	{
		PfpProductExtendedField field = productExtendedFields.get(key);
		if(field == null)
			return null;
		return Long.valueOf(field.getValue());
	}

	/**
	 * Получить значение дополнительного поля
	 * @param key - ключ
	 * @return значение
	 */
	public BigDecimal getExtendedFieldBigDecimalValue(String key)
	{
		PfpProductExtendedField field = productExtendedFields.get(key);
		if(field == null)
			return null;
		return new BigDecimal(field.getValue());
	}

	/**
	 * Получить значение дополнительного поля
	 * @param key - ключ
	 * @return
	 */
	public String getExtendedFieldStringValue(String key)
	{
		PfpProductExtendedField field = productExtendedFields.get(key);
		if(field == null)
			return null;
		return field.getValue();
	}

	public void addExtendedField(String key, Object value)
	{
		//Если пришло пустое значение, то не добавляем
		if(value == null)
			return;
		PfpProductExtendedField field = new PfpProductExtendedField();
		field.setKey(key);
		field.setValue(value.toString());
		productExtendedFields.put(key,field);
	}
}
