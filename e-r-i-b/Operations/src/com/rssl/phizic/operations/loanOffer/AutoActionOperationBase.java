package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.operations.background.TaskState;
import com.rssl.phizic.business.pereodicalTask.PereodicalTaskService;
import com.rssl.phizic.business.pereodicalTask.unload.DirInteractionPereodicalTask;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.CronExpressionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Moshenko
 * Date: 21.09.2012
 * Time: 11:09:10
 */
public abstract class AutoActionOperationBase extends OperationBase implements EditEntityOperation
{
	private static final String ERROR_TASK_STATE = "«адание находитс€ в процессе выполнени€. Ќеобходимо дождатьс€ его завершени€.";

	private static PereodicalTaskService pereodicalTaskService = new PereodicalTaskService();
	protected static final SimpleService simpleService = new SimpleService();
	protected Map<String, ScheduleData> scheduleMap;

	protected void putExist(String operationName) throws BusinessException
	{
		    ScheduleData scheduleData = new ScheduleData();
			DirInteractionPereodicalTask task = (DirInteractionPereodicalTask)pereodicalTaskService.getTaskByOperationName(operationName);
			if (task != null)
			{
				Pair<String,String> time = new Pair("","");
				String hour = "";
				String dayInMonth = "";
				String unloadDir = task.getDir();
				String cronExp = task.getCronExp();

				if (!StringHelper.isEmpty(cronExp))
				{
					//если выражение начинаетс€ с 1, тогда это выражение содержит
					//значение дл€ пол€ "ƒень в мес€це"
					if (StringUtils.indexOf(cronExp,'1')==0)
						dayInMonth = CronExpressionUtils.getDayFromExpression(cronExp) ;
					else
						time = DateHelper.getTimeFromExpression(cronExp);
				}
				else
					hour = String.valueOf(task.getTimeInterval());

				scheduleData.setDay(time.getFirst());
				scheduleData.setTime(time.getSecond());
				scheduleData.setHour(hour);
				scheduleData.setDayInMonth(dayInMonth);
				scheduleData.setDir(unloadDir);
				if (task.getState() == TaskState.DISABLED)
				scheduleData.setEnabled(false);
			}
			scheduleMap.put(operationName, scheduleData);
	}


	public void save() throws BusinessException, BusinessLogicException
	{
		Iterator<ScheduleData> scheduleIt = scheduleMap.values().iterator();
		Iterator<String> opertaionsName = scheduleMap.keySet().iterator();


		while (scheduleIt.hasNext())
		{
			ScheduleData schedule = scheduleIt.next();
			String operationName = opertaionsName.next();
			/*получаем текущий таск*/
			DirInteractionPereodicalTask task = (DirInteractionPereodicalTask)pereodicalTaskService.getTaskByOperationName(operationName);
			if (schedule.isEnabled())
			{
				if (task == null)
				{/*если задание создаетс€ в первые, то добовл€ем новый триггер*/
					task = new DirInteractionPereodicalTask();
					task.setOperationName(operationName);
					task = (DirInteractionPereodicalTask)pereodicalTaskService.addTask(updateTaskData(schedule,task));
					continue;
				}
				pereodicalTaskService.updateTask(updateTaskData(schedule,task));
			}
			else
			{/*иначе отключаем триггер от задани€(задние приостановлено)*/
				/*но если задание и не создовалось, то пропускаем*/
				if (task == null)
					continue;
				/*провер€ем что текущий момент таск не исполн€етс€*/
				if (task.getState()==TaskState.PROCESSING)
					throw new UnloadTaskStateException(ERROR_TASK_STATE);

					pereodicalTaskService.stopTask(task);
			}
		}
	}

	/**
	 * обновить таск данными из ScheduleData
	 * @param schedule
	 * @param task
	 * @return
	 */
	private DirInteractionPereodicalTask updateTaskData(ScheduleData schedule, DirInteractionPereodicalTask task)
	{
		if (schedule.isEnabled())
		{
			task.setState(TaskState.PERIODICAL);
		}
		if (schedule.isSimple())
		{
			task.setCronExp(null);
			task.setTimeInterval(Long.valueOf(schedule.getHour()));
		}
		else
		{
			task.setTimeInterval(null);
			task.setCronExp(schedule.getExpression());
		}
		task.setDir(schedule.getDir());
		task.setCreationDate(Calendar.getInstance());
		task.setOwner(EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getLogin());
		return  task;
	}


	protected boolean pathTester(String path)
	{
		if ((new File(path)).exists())
			return true;
		else
			return false;
	}
}
