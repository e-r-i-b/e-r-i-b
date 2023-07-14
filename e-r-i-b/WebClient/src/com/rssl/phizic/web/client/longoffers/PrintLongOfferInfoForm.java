package com.rssl.phizic.web.client.longoffers;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.resources.external.LongOfferLink;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author osminin
 * @ created 03.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class PrintLongOfferInfoForm extends EditFormBase
{
	private LongOffer longOffer;
	private LongOfferLink longOfferLink;
	private String longOfferType;
	private Department topLevelDepartment;
	private Department currentDepartment;

	public LongOffer getLongOffer()
	{
		return longOffer;
	}

	public void setLongOffer(LongOffer longOffer)
	{
		this.longOffer = longOffer;
	}

	public LongOfferLink getLongOfferLink()
	{
		return longOfferLink;
	}

	public void setLongOfferLink(LongOfferLink longOfferLink)
	{
		this.longOfferLink = longOfferLink;
	}

	public String getLongOfferType()
	{
		return longOfferType;
	}

	public void setLongOfferType(String longOfferType)
	{
		this.longOfferType = longOfferType;
	}

	public Department getCurrentDepartment()
	{
		return currentDepartment;
	}

	public void setCurrentDepartment(Department currentDepartment)
	{
		this.currentDepartment = currentDepartment;
	}

	public Department getTopLevelDepartment()
	{
		return topLevelDepartment;
	}

	public void setTopLevelDepartment(Department topLevelDepartment)
	{
		this.topLevelDepartment = topLevelDepartment;
	}
}
