package com.rssl.phizic.business.dictionaries.pfp.products.loan;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * @author akrenev
 * @ created 29.03.2012
 * @ $Author$
 * @ $Revision$
 * Вид кредита для ПФП
 */
public class LoanKindProduct extends PFPDictionaryRecord implements Serializable
{
	private String name;        // название
	private BigDecimal fromAmount;    // сумма от
	private BigDecimal toAmount;      // сумма до
	private Long fromPeriod;          // срок от
	private Long toPeriod;            // срок до
	private Long defaultPeriod;       // срок по умолчанию
	private BigDecimal fromRate;      // ставка от
	private BigDecimal toRate;        // ставка до
	private BigDecimal defaultRate;   // ставка пр умрлчанию
	private Long imageId;             // логотип

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Comparable getSynchKey()
	{
		return name;
	}

	public void updateFrom(DictionaryRecord that)
	{
		setName(((LoanKindProduct) that).getName());
		setFromAmount(((LoanKindProduct) that).getFromAmount());
		setToAmount(((LoanKindProduct) that).getToAmount());
		setFromPeriod(((LoanKindProduct) that).getFromPeriod());
		setToPeriod(((LoanKindProduct) that).getToPeriod());
		setDefaultPeriod(((LoanKindProduct) that).getDefaultPeriod());
		setFromRate(((LoanKindProduct) that).getFromRate());
		setToRate(((LoanKindProduct) that).getToRate());
		setDefaultRate(((LoanKindProduct) that).getDefaultRate());
		setImageId(((LoanKindProduct) that).getImageId());
	}

	public BigDecimal getFromAmount()
	{
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount)
	{
		this.fromAmount = fromAmount;
	}

	public BigDecimal getToAmount()
	{
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount)
	{
		this.toAmount = toAmount;
	}

	public Long getFromPeriod()
	{
		return fromPeriod;
	}

	public void setFromPeriod(Long fromPeriod)
	{
		this.fromPeriod = fromPeriod;
	}

	public Long getToPeriod()
	{
		return toPeriod;
	}

	public void setToPeriod(Long toPeriod)
	{
		this.toPeriod = toPeriod;
	}

	public Long getDefaultPeriod()
	{
		return defaultPeriod;
	}

	public void setDefaultPeriod(Long defaultPeriod)
	{
		this.defaultPeriod = defaultPeriod;
	}

	public BigDecimal getFromRate()
	{
		return fromRate;
	}

	public void setFromRate(BigDecimal fromRate)
	{
		this.fromRate = fromRate;
	}

	public BigDecimal getToRate()
	{
		return toRate;
	}

	public void setToRate(BigDecimal toRate)
	{
		this.toRate = toRate;
	}

	public BigDecimal getDefaultRate()
	{
		return defaultRate;
	}

	public void setDefaultRate(BigDecimal defaultRate)
	{
		this.defaultRate = defaultRate;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}
}
