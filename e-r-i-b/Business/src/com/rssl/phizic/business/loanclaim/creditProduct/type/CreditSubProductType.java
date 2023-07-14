package com.rssl.phizic.business.loanclaim.creditProduct.type;

import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.annotation.JsonExclusion;
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
	@JsonExclusion
	private Long id;

	/**
	 * ��� ��� ��������
	 */
	private String code;

	/**
	 * ��������� �������
	 */
	@JsonExclusion
	private CreditProduct creditProduct;

	/**
	 * �����������(��������)
	 */
	private String terbank;

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

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
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

	public String getTerbank()
	{
		return terbank;
	}

	public void setTerbank(String terbank)
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
		String currCode = this.getCurrency().getCode();
		int comareTb = this.getTerbank().compareTo(that.getTerbank());
		boolean comareCurrency = StringHelper.equals(this.getCurrency().getCode(), that.getCurrency().getCode());
		//���� ��� ���� ��, � ���������� ������.
		if ( comareTb == 0 && comareCurrency)
			return 0;
			//���� ��� ���� ��, � ������ ������.
		else if (comareTb == 0 && ("RUB".equals(currCode) || "RUR".equals(currCode)))
			return 3;
		else if (comareTb == 0 && "USD".equals(currCode))
			return 2;
		else if (comareTb == 0 && "EUR".equals(currCode))
			return 1;
			//���� ������ ��.
		else
			return -1;

	}
}
