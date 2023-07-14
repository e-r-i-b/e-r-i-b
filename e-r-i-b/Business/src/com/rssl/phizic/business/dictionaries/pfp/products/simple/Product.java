package com.rssl.phizic.business.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductParameters;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author akrenev
 * @ created 21.02.2012
 * @ $Author$
 * @ $Revision$
 * ������� ����� ������� ���������
 */
public abstract class Product extends SimpleProductBase
{
	private ForComplexProductDiscriminator forComplex;
	private BigDecimal sumFactor;

	/**
	 * @return ����� ����������� ��������� �������� ������ �������
	 */
	public ForComplexProductDiscriminator getForComplex()
	{
		return forComplex;
	}

	/**
	 * ������ � ����� ����������� ��������� �������� ������ �������
	 * @param forComplex ����� ����������� ��������� �������� ������ �������
	 */
	public void setForComplex(ForComplexProductDiscriminator forComplex)
	{
		this.forComplex = forComplex;
	}

	/**
	 * @return ����������� ��� ������������ ����� ����������
	 */
	public BigDecimal getSumFactor()
	{
		return sumFactor;
	}

	/**
	 * ������ ����������� ��� ������������ ����� ����������
	 * @param sumFactor ����������� ��� ������������ ����� ����������
	 */
	public void setSumFactor(BigDecimal sumFactor)
	{
		this.sumFactor = sumFactor;
	}

	public Comparable getSynchKey()
	{
		return getForComplex().name() + super.getSynchKey() + getClass().getSimpleName();
	}

	public void updateFrom(DictionaryRecord that)
	{
		super.updateFrom(that);
		Product source = (Product) that;
		setForComplex(source.getForComplex());
		for (Map.Entry<PortfolioType, ProductParameters> entry : source.getParameters().entrySet())
		{
			ProductParameters productParameters = getParameters(entry.getKey());
			productParameters.updateFrom(entry.getValue());
		}

		setSumFactor(source.getSumFactor());
	}
}
