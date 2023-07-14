package com.rssl.phizic.business.ext.sbrf.receivers;

import com.rssl.phizic.business.dictionaries.PaymentReceiverPhiz;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.gate.dictionaries.officies.Code;

/**
 * @author Egorova
 * @ created 16.05.2008
 * @ $Author$
 * @ $Revision$
 */
//todo удалить, уже не нужен.
public class PaymentReceiverPhizSBRF extends PaymentReceiverPhiz
{
	private String fullName;
	private String personId;
	private Department officeKey;
	private SBRFOfficeCodeAdapter code;
	
	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public String getPersonId()
	{
		return personId;
	}

	public void setPersonId(String personId)
	{
		this.personId = personId;
	}

	public Department getOfficeKey()
	{
		return officeKey;
	}

	public void setOfficeKey(Department officeKey)
	{
		this.officeKey = officeKey;
	}

	public SBRFOfficeCodeAdapter getCode()
	{
		return code;
	}

	public void setCode(SBRFOfficeCodeAdapter code)
	{
		this.code = code;
	}
}
