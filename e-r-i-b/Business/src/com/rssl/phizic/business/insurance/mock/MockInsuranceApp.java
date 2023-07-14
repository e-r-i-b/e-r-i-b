package com.rssl.phizic.business.insurance.mock;

import com.rssl.phizic.common.types.MockObject;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.insurance.PolicyDetails;

import java.util.Calendar;

/**
 * Объект-заглушка для страховых/НПФ продуктов клиента
 * @author gladishev
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class MockInsuranceApp implements InsuranceApp, MockObject
{
	private String id; //идентификатор

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getReference()
	{
		return null;
	}

	public String getBusinessProcess()
	{
		return null;
	}

	public String getProductType()
	{
		return null;
	}

	public String getStatus()
	{
		return null;
	}

	public String getCompany()
	{
		return null;
	}

	public String getProgram()
	{
		return null;
	}

	public Calendar getStartDate()
	{
		return null;
	}

	public Calendar getEndDate()
	{
		return null;
	}

	public String getSNILS()
	{
		return null;
	}

	public Money getAmount()
	{
		return null;
	}

	public String getRisk()
	{
		return null;
	}

	public String getAdditionalInfo()
	{
		return null;
	}

	public PolicyDetails getPolicyDetails()
	{
		return null;
	}
}