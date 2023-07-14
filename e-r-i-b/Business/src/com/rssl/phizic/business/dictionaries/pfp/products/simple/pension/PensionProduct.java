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
 * сущность пенсионного продукта
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
	 * @return пенсионный фонд
	 */
	public PensionFund getPensionFund()
	{
		return pensionFund;
	}

	/**
	 * задать пенсионный фонд
	 * @param pensionFund пенсионный фонд
	 */
	public void setPensionFund(PensionFund pensionFund)
	{
		this.pensionFund = pensionFund;
	}

	/**
	 * @return стартовый взнос
	 */
	public BigDecimal getEntryFee()
	{
		return entryFee;
	}

	/**
	 * задать стартовый взнос
	 * @param entryFee стартовый взнос
	 */
	public void setEntryFee(BigDecimal entryFee)
	{
		this.entryFee = entryFee;
	}

	/**
	 * @return ежеквартальный взнос
	 */
	public BigDecimal getQuarterlyFee()
	{
		return quarterlyFee;
	}

	/**
	 * задать ежеквартальный взнос
	 * @param quarterlyFee ежеквартальный взнос
	 */
	public void setQuarterlyFee(BigDecimal quarterlyFee)
	{
		this.quarterlyFee = quarterlyFee;
	}

	/**
	 * @return минимальный срок
	 */
	public Long getMinPeriod()
	{
		return minPeriod;
	}

	/**
	 * задать минимальный срок
	 * @param minPeriod минимальный срок
	 */
	public void setMinPeriod(Long minPeriod)
	{
		this.minPeriod = minPeriod;
	}

	/**
	 * @return максимальный срок
	 */
	public Long getMaxPeriod()
	{
		return maxPeriod;
	}

	/**
	 * задать максимальный срок
	 * @param maxPeriod максимальный срок
	 */
	public void setMaxPeriod(Long maxPeriod)
	{
		this.maxPeriod = maxPeriod;
	}

	/**
	 * @return срок по умолчанию
	 */
	public Long getDefaultPeriod()
	{
		return defaultPeriod;
	}

	/**
	 * задать срок по умолчанию
	 * @param defaultPeriod срок по умолчанию
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
