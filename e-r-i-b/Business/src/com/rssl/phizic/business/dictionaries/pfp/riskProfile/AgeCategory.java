package com.rssl.phizic.business.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 30.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * возрастная категория
 */
public class AgeCategory extends PFPDictionaryRecord
{
	private Long minAge; //от
	private Long maxAge; //до
	private Long weight; //балл

	public Long getMinAge()
	{
		return minAge;
	}

	public void setMinAge(Long minAge)
	{
		this.minAge = minAge;
	}

	public Long getMaxAge()
	{
		return maxAge;
	}

	public void setMaxAge(Long maxAge)
	{
		this.maxAge = maxAge;
	}

	public Long getWeight()
	{
		return weight;
	}

	public void setWeight(Long weight)
	{
		this.weight = weight;
	}

	public Comparable getSynchKey()
	{
		return String.valueOf(minAge) + String.valueOf(maxAge);
	}

	public void updateFrom(DictionaryRecord that)
	{
		AgeCategory source = (AgeCategory) that;
		setMinAge(source.getMinAge());
		setMaxAge(source.getMaxAge());
		setWeight(source.getWeight());
	}
}
