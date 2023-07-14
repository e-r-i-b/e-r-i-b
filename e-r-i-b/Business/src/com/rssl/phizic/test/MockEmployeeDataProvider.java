package com.rssl.phizic.test;

import com.rssl.phizic.business.employees.EmployeeImpl;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.EmployeeDataImpl;
import com.rssl.phizic.context.EmployeeDataProvider;

/**
 * @author Evgrafov
 * @ created 24.08.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3841 $
 */

public class MockEmployeeDataProvider implements EmployeeDataProvider
{
	private EmployeeData data;

	public MockEmployeeDataProvider()
	{
		this(new EmployeeDataImpl(new EmployeeImpl()));
	}

	public MockEmployeeDataProvider(EmployeeData data)
	{
		this.data = data;
	}

	public EmployeeData getEmployeeData()
	{
		return data;
	}


	public void setEmployeeData(EmployeeData data)
	{
		this.data = data;
	}
}