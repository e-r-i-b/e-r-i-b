package com.rssl.phizic.operations.background;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.operations.background.BackgroundOperation;
import com.rssl.phizic.business.operations.background.BackgroundTask;
import com.rssl.phizic.business.operations.background.TaskResult;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author krenev
 * @ created 24.08.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class BackgroundOperationBase<TR extends TaskResult, T extends BackgroundTask<TR>, R extends Restriction> extends OperationBase<R> implements BackgroundOperation<TR, T, R>
{
	protected static final SimpleService simpleService = new SimpleService();

	/**
	 * ���������������� ������� ������
	 * @param backgroundTask ������� ������
	 * @return ������������������ ������� ������
	 * @throws BusinessException
	 */
	protected T registerBackgroundTask(T backgroundTask) throws BusinessException
	{
		return registerBackgroundTask(backgroundTask, null);
	}

	/**
	 * ���������������� ������� ������
	 * @param backgroundTask ������� ������
	 * @param instanceName ��� ��������� ��
	 * @return ������������������ ������� ������
	 * @throws BusinessException
	 */
	protected T registerBackgroundTask(T backgroundTask, String instanceName) throws BusinessException
	{
		return simpleService.addOrUpdate(backgroundTask, instanceName);
	}

	/**
	 * @return ��� ���������� ���������� ������� ������. ���� � ���������� ��� ���(��������� �����), �� �����.
	 */
	protected String getCurrentEmployeeFullName()
	{
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		String fullName = employee.getFullName();
		if(StringHelper.isEmpty(fullName))
			fullName = employee.getLogin().getUserId();
		return fullName;
	}
}
