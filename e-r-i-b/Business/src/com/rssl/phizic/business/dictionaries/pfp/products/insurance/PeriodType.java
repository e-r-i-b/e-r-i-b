package com.rssl.phizic.business.dictionaries.pfp.products.insurance;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 13.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * “ип периода
 */
public class PeriodType extends PFPDictionaryRecord
{
	private String name;       // название тпа периода
	private Long months;       // число мес€цев периоде

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getMonths()
	{
		return months;
	}

	public void setMonths(Long months)
	{
		this.months = months;
	}

	public boolean equals(Object otherType)
	{
		if (this == otherType)
			return true;

		if (!(otherType instanceof PeriodType))
			return false;

		if (getId() == null)
			return false;

		return getId().equals(((PeriodType) otherType).getId());
	}

	public Comparable getSynchKey()
	{
		return name;
	}

	public void updateFrom(DictionaryRecord that)
	{
		PeriodType source = (PeriodType) that;
		setName(source.getName());
		setMonths(source.getMonths());
	}
}
