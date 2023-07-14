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
//������� ������� ��� ���: ���� ��: �����������, �����, ���, ���.
//� ������ �����, ������� ������ ����� ������� � ������ �������
public class BaseProduct
{
	private static final String DESCRIPTION_FIELD_NAME = "description";
	private Long id;
	private DictionaryProductType productType;//��� ��������
	private String productName;     //�������� �������� ��������
	private Money amount;           //����� ��������� � ������ �������
	private BigDecimal income;      //���������� ��������, ��������� ��������.
	private Long dictionaryProductId; //������������� �������� � �����������, �� ������ �������� ��� ������ ������ �������
	private Map<String, PfpProductExtendedField> productExtendedFields = new HashMap<String, PfpProductExtendedField>();//�������������� ����

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
	 * @return �������� �������� ��������
	 */
	public String getDescription()
	{
		return getExtendedFieldStringValue(DESCRIPTION_FIELD_NAME);
	}

	/**
	 * ������ �������� �������� ��������
	 * @param description �������� �������� ��������
	 */
	public void setDescription(String description)
	{
		addExtendedField(DESCRIPTION_FIELD_NAME, description);
	}

	/**
	 * �������� �������� ��������������� ����
	 * @param key - ����
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
	 * �������� �������� ��������������� ����
	 * @param key - ����
	 * @return ��������
	 */
	public BigDecimal getExtendedFieldBigDecimalValue(String key)
	{
		PfpProductExtendedField field = productExtendedFields.get(key);
		if(field == null)
			return null;
		return new BigDecimal(field.getValue());
	}

	/**
	 * �������� �������� ��������������� ����
	 * @param key - ����
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
		//���� ������ ������ ��������, �� �� ���������
		if(value == null)
			return;
		PfpProductExtendedField field = new PfpProductExtendedField();
		field.setKey(key);
		field.setValue(value.toString());
		productExtendedFields.put(key,field);
	}
}
