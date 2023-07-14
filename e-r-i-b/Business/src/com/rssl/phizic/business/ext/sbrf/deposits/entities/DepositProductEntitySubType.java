package com.rssl.phizic.business.ext.sbrf.deposits.entities;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * —ущность дл€ описани€ параметров подвида вкладного продукта (требуетс€ на форме открыти€)
 * @author EgorovaA
 * @ created 22.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductEntitySubType
{
	private Long id;
	private Calendar dateBegin;
	private Calendar dateEnd;
	private BigDecimal minAdditionalFee;
	private String name;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getDateBegin()
	{
		return dateBegin;
	}

	public void setDateBegin(Calendar dateBegin)
	{
		this.dateBegin = dateBegin;
	}

	public Calendar getDateEnd()
	{
		return dateEnd;
	}

	public void setDateEnd(Calendar dateEnd)
	{
		this.dateEnd = dateEnd;
	}

	public BigDecimal getMinAdditionalFee()
	{
		return minAdditionalFee;
	}

	public void setMinAdditionalFee(BigDecimal minAdditionalFee)
	{
		this.minAdditionalFee = minAdditionalFee;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
