package com.rssl.phizic.web.employee;

import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.EmployeeDataProvider;
import com.rssl.phizic.web.WebContext;

import javax.servlet.http.HttpServletRequest;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 12:49:46 */
public class HttpEmployeeDataProvider implements EmployeeDataProvider
{
	public EmployeeData getEmployeeData()
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		return request == null ? null : EmployeeWebContext.get(request);
	}

	public void setEmployeeData(EmployeeData data)
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		if (request == null)
			throw new RuntimeException("Нет текущего запроса");
		EmployeeWebContext.set(request, data);
	}
}
