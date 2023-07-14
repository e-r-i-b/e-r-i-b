
package com.rssl.phizic.unloading;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.operations.background.BackgroundTaskOperationFactory;
import com.rssl.phizic.business.operations.background.TaskState;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.business.pereodicalTask.PereodicalTask;
import com.rssl.phizic.business.pereodicalTask.PereodicalTaskResult;
import com.rssl.phizic.business.pereodicalTask.PereodicalTaskService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeDataProvider;
import com.rssl.phizic.context.SimpleEmployeeDataProvider;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.pereodicalTask.PeriodicalTaskOperationBase;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * User: Moshenko
 * Date: 02.11.2011
 * Time: 12:04:16
 * джоб дл€ исполнени€ периодических задач
 */
public class PereodicalJob implements StatefulJob
{
	private static  PereodicalTaskService pereodicalTaskService = new PereodicalTaskService();
	private static final EmployeeService employeeService = new EmployeeService();
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	public PereodicalJob() throws JobExecutionException
	{
	}

	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		ApplicationInfo.setCurrentApplication(Application.Scheduler);
		PereodicalTask task = getTask(context);
		if(task == null)
			return;

		//инициализируем контекст EmployeContext
		initEmployeeContext(task.getOwner());

		try
		{
			// создаем операцию с помощью переопределенной фабрики(без проверки плав)
			OperationFactory operationFactory = new BackgroundTaskOperationFactory(task.getOwner(), new RestrictionProviderImpl());
			String[] token = task.getOperationClassName().split("\\.");
			String operationName =  token[token.length-1];
			PeriodicalTaskOperationBase operation = operationFactory.create(operationName);
			// инициализируем операцию фоновой задачи
			operation.initialize(task);

			// обновл€ем статус фоновой задачи на "задача в процессе выполнени€"
			pereodicalTaskService.updatePeriodicalTaskStatus(task, TaskState.PROCESSING);

			// запускаем процесс выполнени€
			PereodicalTaskResult result = operation.execute();

			// повещающий задачу о том, что ее выполение завершено успешно.
			task.executed(result);
			// обновл€ем задачу в базе
			pereodicalTaskService.addOrUpdate(task);
		}
		catch(Exception e)
		{
			try
			{
				pereodicalTaskService.updatePeriodicalTaskStatus(task, TaskState.ERROR);
			}
			catch (BusinessException be)
			{
				throw new JobExecutionException("ќшибка при обновлении статуса задачи " + TaskState.ERROR, be);
			}

			String message = "ќшибка при выполнении фоновой задачи taskId=";
			log.error(message + task.getId(), e);
			throw new JobExecutionException(message + task.getId(), e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	private void initEmployeeContext(CommonLogin login) throws JobExecutionException
	{
		try
		{
			Employee employee = employeeService.findByLogin((BankLogin) login);
			if(employee == null)
				throw new JobExecutionException("Ќеизвестный сотрудник loginId=" + login.getId());
			//инициализируем контекст EmployeContext, т.к он используетс€ дл€ всевозможных ограничений при выполнении операции
			EmployeeDataProvider employeeDataProvider = new SimpleEmployeeDataProvider(employee);
			EmployeeContext.setEmployeeDataProvider(employeeDataProvider);
		}
		catch (BusinessException e)
		{
			String message = "ќшибка при определении сотрудника";
			log.error(message);
			throw new JobExecutionException(message, e);
		}
	}

	/**
	 * получаем таск дл€ выполнени€, по имени триггера
	 */
	protected PereodicalTask getTask(JobExecutionContext context) throws JobExecutionException
	{
	   String triggerName = context.getTrigger().getName();
		PereodicalTask task = null;

		try
		{
			task = pereodicalTaskService.getTaskByTriggerName(triggerName);
		}
		catch (BusinessException e)
		{
			throw new JobExecutionException(e);
		}

		return task;
	}
}
