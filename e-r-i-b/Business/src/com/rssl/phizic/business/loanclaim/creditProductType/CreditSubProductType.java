package com.rssl.phizic.business.loanclaim.creditProductType;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Moshenko
 * @ created 05.01.2014
 * @ $Author$
 * @ $Revision$
 *  ��� ���������� ��� ��������
 */
public class CreditSubProductType implements Comparable
{
	private Long id;

	/**
	 * ��� ��� ��������
	 */
	private Long code;

	/**
	 * ��������� �������
	 */
	private CreditProduct creditProduct;

	/**
	 * �����������(��������)
	 */
	private Department terbank;

	/**
	 * ������
	 */
	private Currency currency;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getCode()
	{
		return code;
	}

	public void setCode(Long code)
	{
		this.code = code;
	}

	public CreditProduct getCreditProduct()
	{
		return creditProduct;
	}

	public void setCreditProduct(CreditProduct creditProduct)
	{
		this.creditProduct = creditProduct;
	}

	public Department getTerbank()
	{
		return terbank;
	}

	public void setTerbank(Department terbank)
	{
		this.terbank = terbank;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public int compareTo(Object o)
	{
		CreditSubProductType that = (CreditSubProductType) o;
		int comareRes = this.terbank.getRegion().compareTo(that.terbank.getRegion());
		//���� ��� ���� ��, � ���������� ������.
		if ( comareRes== 0 && StringHelper.equals(currency.getCode(),that.currency.getCode()))
		  return 0;
		//���� ��� ���� ��, � ������ ������.
		else if (comareRes == 0 && StringHelper.equals(currency.getCode(),that.currency.getCode()))
			return 1;
		//���� ������ ��.
		else
			return -1;
	}
}
