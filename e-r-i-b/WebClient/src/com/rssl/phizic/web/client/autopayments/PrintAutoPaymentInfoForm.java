package com.rssl.phizic.web.client.autopayments;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author osminin
 * @ created 14.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class PrintAutoPaymentInfoForm extends EditFormBase
{
	private AutoPaymentLink link;
	private Department currentDepartment;
	private Department topLevelDepartment;

	public AutoPaymentLink getLink()
	{
		return link;
	}

	public void setLink(AutoPaymentLink link)
	{
		this.link = link;
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
