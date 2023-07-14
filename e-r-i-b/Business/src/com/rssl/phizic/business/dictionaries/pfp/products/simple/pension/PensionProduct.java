package com.rssl.phizic.business.dictionaries.pfp.products.simple.pension;

import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFund;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;

import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� ����������� ��������
 */

public class PensionProduct extends ProductBase
{
	private PensionFund pensionFund;
	private BigDecimal entryFee;
	private BigDecimal quarterlyFee;
	private Long minPeriod;
	private Long maxPeriod;
	private Long defaultPeriod;

	/**
	 * @return ���������� ����
	 */
	public PensionFund getPensionFund()
	{
		return pensionFund;
	}

	/**
	 * ������ ���������� ����
	 * @param pensionFund ���������� ����
	 */
	public void setPensionFund(PensionFund pensionFund)
	{
		this.pensionFund = pensionFund;
	}

	/**
	 * @return ��������� �����
	 */
	public BigDecimal getEntryFee()
	{
		return entryFee;
	}

	/**
	 * ������ ��������� �����
	 * @param entryFee ��������� �����
	 */
	public void setEntryFee(BigDecimal entryFee)
	{
		this.entryFee = entryFee;
	}

	/**
	 * @return �������������� �����
	 */
	public BigDecimal getQuarterlyFee()
	{
		return quarterlyFee;
	}

	/**
	 * ������ �������������� �����
	 * @param quarterlyFee �������������� �����
	 */
	public void setQuarterlyFee(BigDecimal quarterlyFee)
	{
		this.quarterlyFee = quarterlyFee;
	}

	/**
	 * @return ����������� ����
	 */
	public Long getMinPeriod()
	{
		return minPeriod;
	}

	/**
	 * ������ ����������� ����
	 * @param minPeriod ����������� ����
	 */
	public void setMinPeriod(Long minPeriod)
	{
		this.minPeriod = minPeriod;
	}

	/**
	 * @return ������������ ����
	 */
	public Long getMaxPeriod()
	{
		return maxPeriod;
	}

	/**
	 * ������ ������������ ����
	 * @param maxPeriod ������������ ����
	 */
	public void setMaxPeriod(Long maxPeriod)
	{
		this.maxPeriod = maxPeriod;
	}

	/**
	 * @return ���� �� ���������
	 */
	public Long getDefaultPeriod()
	{
		return defaultPeriod;
	}

	/**
	 * ������ ���� �� ���������
	 * @param defaultPeriod ���� �� ���������
	 */
	public void setDefaultPeriod(Long defaultPeriod)
	{
		this.defaultPeriod = defaultPeriod;
	}

	public DictionaryProductType getProductType()
	{
		return DictionaryProductType.PENSION;
	}
}
