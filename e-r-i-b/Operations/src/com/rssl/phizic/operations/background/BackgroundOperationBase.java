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
	 * Зарегистрировать фоновую задачу
	 * @param backgroundTask фоновая задача
	 * @return зарегистрированаая фоновая задача
	 * @throws BusinessException
	 */
	protected T registerBackgroundTask(T backgroundTask) throws BusinessException
	{
		return registerBackgroundTask(backgroundTask, null);
	}

	/**
	 * Зарегистрировать фоновую задачу
	 * @param backgroundTask фоновая задача
	 * @param instanceName имя экземпляр БД
	 * @return зарегистрированаая фоновая задача
	 * @throws BusinessException
	 */
	protected T registerBackgroundTask(T backgroundTask, String instanceName) throws BusinessException
	{
		return simpleService.addOrUpdate(backgroundTask, instanceName);
	}

	/**
	 * @return ФИО сотрудника создавшего фоновую задачу. Если у сотрудника нет ФИО(дефолтный админ), то логин.
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
