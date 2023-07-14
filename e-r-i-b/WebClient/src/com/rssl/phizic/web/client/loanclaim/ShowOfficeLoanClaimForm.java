package com.rssl.phizic.web.client.loanclaim;

import com.rssl.phizgate.ext.sbrf.etsm.OfficeLoanClaim;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Nady
 * @ created 15.07.15
 * @ $Author$
 * @ $Revision$
 */
/**
 * Отображение зявки на кредит, созданной в каналах отличных от УКО
 */
public class ShowOfficeLoanClaimForm extends EditFormBase
{
	private String applicationNumber;
	private OfficeLoanClaim document;
	private Department department;

	public String getApplicationNumber()
	{
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber)
	{
		this.applicationNumber = applicationNumber;
	}

	public OfficeLoanClaim getDocument()
	{
		return document;
	}

	public void setDocument(OfficeLoanClaim document)
	{
		this.document = document;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}
}
