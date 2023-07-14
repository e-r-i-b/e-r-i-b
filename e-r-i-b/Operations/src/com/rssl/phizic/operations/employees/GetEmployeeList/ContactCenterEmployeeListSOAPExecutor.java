package com.rssl.phizic.operations.employees.GetEmployeeList;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.dataaccess.query.CustomListExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.employee.ContactCenterEmployee;
import com.rssl.phizic.gate.employee.ContactCenterEmployeeFilterParameters;
import com.rssl.phizic.gate.employee.EmployeeService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.employees.GetEmployeeListOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 28.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * «апрос списка сотрудников контактных центров через веб сервис
 */

public class ContactCenterEmployeeListSOAPExecutor implements CustomListExecutor<ContactCenterEmployee>
{
	public List<ContactCenterEmployee> getList(Map<String, Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws DataAccessException
	{
		try
		{
			Long soughtId = (Long) parameters.get(GetEmployeeListOperation.EMPLOYEE_ID_CONTACT_CENTER_EMPLOYEE_LIST_QUERY_PARAMETER);
			String soughtFIO = (String) parameters.get(GetEmployeeListOperation.FIO_CONTACT_CENTER_EMPLOYEE_LIST_QUERY_PARAMETER);
			String soughtArea = StringHelper.getEmptyIfNull(parameters.get(GetEmployeeListOperation.AREA_ID_CONTACT_CENTER_EMPLOYEE_LIST_QUERY_PARAMETER));
			Calendar soughtBlockedUntil = DateHelper.toCalendar((Date) parameters.get(GetEmployeeListOperation.BLOCKED_UNTIL_CONTACT_CENTER_EMPLOYEE_LIST_QUERY_PARAMETER));
			ContactCenterEmployeeFilterParameters filterParameters = new ContactCenterEmployeeFilterParametersImpl(soughtId, soughtFIO, soughtArea, soughtBlockedUntil, offset, size);
			EmployeeService service = GateSingleton.getFactory().service(EmployeeService.class);
			return service.getListMailManagers(filterParameters);
		}
		catch (GateException e)
		{
			throw new DataAccessException("ќшибка получени€ списка сотрудников контактных центров.", e);
		}
		catch (GateLogicException e)
		{
			throw new DataAccessException("ќшибка получени€ списка сотрудников контактных центров.", e);
		}
	}
}
