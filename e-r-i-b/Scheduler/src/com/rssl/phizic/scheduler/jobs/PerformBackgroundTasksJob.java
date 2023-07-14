package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.operations.background.*;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.*;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.store.SimpleStore;
import com.rssl.phizic.utils.store.StoreManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ���� ��� ���������� ������� �����
 * @author niculichev
 * @ created 31.08.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class PerformBackgroundTasksJob<TR extends TaskResult, T extends BackgroundTaskBase<TR>> extends BaseJob implements Job
{
	private static final String ERROR_UPDATE_MESSAGE = "������ ���������� � ������ taskId = %s ������� %s";
	private static final String ERROR_PERFORM_MESSAGE = "������ ��� ���������� ������� ������ taskId=";

	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	private static final BackgroundTaskService backgroundTaskService = new BackgroundTaskService();
	private static final EmployeeService employeeService = new EmployeeService();
	private static final SecurityService securityService = new SecurityService();
	private static final DepartmentService departmentService = new DepartmentService();

	private void initPerformContext(T task) throws JobExecutionException
	{
		try
		{
			LogThreadContext.setIPAddress(ApplicationConfig.getIt().getApplicationHost().getHostAddress());
			if(ConfigFactory.getConfig(CSAAdminGateConfig.class).isMultiBlockMode())
			{
				StoreManager.setCurrentStore(new SimpleStore());
				Long loginId = task.getOwnerId();
				boolean isAllTbAccess = departmentService.isAllTBAccess(loginId);
				CSAAdminEmployeeContext.setData(new CSAAdminEmployeeDataImpl(loginId,isAllTbAccess));
			}
			else
			{
				BankLogin bankLogin = (BankLogin)securityService.findById(task.getOwnerId());
				Employee employee = employeeService.findByLogin(bankLogin);
				EmployeeDataProvider employeeDataProvider = new SimpleEmployeeDataProvider(employee);
				EmployeeContext.setEmployeeDataProvider(employeeDataProvider);
			}
		}
		catch (SecurityDbException e)
		{
			log.error("�� ������� ���������������� �������� ���������� ������� ������", e);
			throw new JobExecutionException(e);
		}
		catch (BusinessException e)
		{
			log.error("�� ������� ���������������� �������� ���������� ������� ������", e);
			throw new JobExecutionException(e);
		}
		catch (UnknownHostException e)
		{
			log.error("�� ������� ���������������� �������� ���������� ������� ������", e);
			throw new JobExecutionException(e);
		}
	}

	private BackgroundOperation<TR, T, Restriction> getOperation(BackgroundTask task) throws BusinessException
	{
		try
		{
			Class<BackgroundOperation<TR, T, Restriction>> clazz = ClassHelper.loadClass(task.getOperationClassName());
			return clazz.newInstance();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	protected abstract Class<T> getTaskClass();

	private T getNextTask() throws JobExecutionException
	{
		try
		{
			// �������� ������ ��������� ������� ������
			return backgroundTaskService.getNextTask(getTaskClass());
		}
		catch (BusinessException e)
		{
			String message = "������ ��� ��������� ��������� ������.";
			log.error(message, e);
			throw new JobExecutionException(message, e);
		}
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{

		T task = getNextTask();
		if (task == null)
			return;

		//�������������� �������� ���������
		initPerformContext(task);

		try
		{
			BackgroundOperation<TR, T, Restriction> operation = getOperation(task);

			// �������������� �������� ������� ������
			operation.initialize(task);

			// ��������� ������ ������� ������ �� "������ � �������� ����������"
			updateStateTask(task, TaskState.PROCESSING, null);

			// ��������� ������� ����������
			TR result = operation.execute();

			// ���������� ������ � ���, ��� �� ��������� ��������� �������.
			task.executed(result);

			// ��������� ������ � ����
			backgroundTaskService.addOrUpdate(task);
		}
		catch (Exception e)
		{
			updateStateTask(task, TaskState.ERROR, e.getMessage());

			log.error(ERROR_PERFORM_MESSAGE + task.getId(), e);
			throw new JobExecutionException(ERROR_PERFORM_MESSAGE + task.getId(), e);
		}
	}

	private void updateStateTask(BackgroundTask task, TaskState state, String report) throws JobExecutionException
	{
		try
		{
			backgroundTaskService.updateStatus((BackgroundTaskBase) task, state, report);
		}
		catch (BusinessException e)
		{
			String message = String.format(ERROR_UPDATE_MESSAGE, task.getId(), state);
			log.error(message, e);
			throw new JobExecutionException(message, e);
		}
	}
}
