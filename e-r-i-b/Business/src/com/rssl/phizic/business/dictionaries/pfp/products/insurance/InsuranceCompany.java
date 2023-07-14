package com.rssl.phizic.business.dictionaries.pfp.products.insurance;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 03.04.2012
 * @ $Author$
 * @ $Revision$
 * страховая компания
 */
public class InsuranceCompany extends PFPDictionaryRecord
{
	private String name;  // название
	private Long imageId; // иконка

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	public Comparable getSynchKey()
	{
		return name;
	}

	public void updateFrom(DictionaryRecord that)
	{
		InsuranceCompany company = (InsuranceCompany) that;
		name = company.getName();
		imageId = company.getImageId();
	}
}
