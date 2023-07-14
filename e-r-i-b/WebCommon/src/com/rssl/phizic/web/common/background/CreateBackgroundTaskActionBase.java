package com.rssl.phizic.web.common.background;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.background.BackgroundOperation;
import com.rssl.phizic.business.operations.background.TaskResult;
import com.rssl.phizic.business.operations.background.BackgroundTask;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author krenev
 * @ created 19.08.2011
 * @ $Author$
 * @ $Revision$
 * Базовый класс экшенов поддерживающих создание фоновых задач
 */
public abstract class CreateBackgroundTaskActionBase extends OperationalActionBase
{
	public static final String FORWARD_START = "Start";
	public static final String FORWARD_TASK_LIST = "TaskList";
	public static final String FORWARD_REPORT = "Report";

	/**
	 * Валидация данных формы, проверка которых невозможна через FormProcessor
	 * Данный метод переопределять по минимуму!!!
	 * @param form форма для обновления
	 * @return ActionMessages
	 */
	protected ActionMessages validateFormData(CreateBackgroundTaskFormBase form)
	{
		return new ActionMessages();
	}

	/**
	 * получить форвард на просмотр отчета об исполении нефонового действия
	 * по умолчанию отчет складывается в форму и происходит фозврщается форвард REPORT
	 * @param frm форма
	 * @param taskResult  результат исполнения
	 * @return форвард
	 */
	protected ActionForward createReportForward(CreateBackgroundTaskFormBase frm, TaskResult taskResult)
	{
		frm.setResult(taskResult);
		return getCurrentMapping().findForward(FORWARD_REPORT);
	}

	/**
	 * получить форвард на страницу после успешного задания фоновой задачи
	 * @param frm форма
	 * @param task созданная задача
	 * @return форвар. по умолчанию переход на TASK_LIST
	 */
	protected ActionForward createTaskCreatedForward(CreateBackgroundTaskFormBase frm, BackgroundTask task)
	{
		return getCurrentMapping().findForward(FORWARD_TASK_LIST);
	}

	/**
	 * создать и проинициализировать операцию
	 * @param form форма с данными пользователя
	 * @return операция
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected abstract BackgroundOperation createOperation(CreateBackgroundTaskFormBase form) throws BusinessException, BusinessLogicException;

	public ActionForward doIt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateBackgroundTaskFormBase frm = (CreateBackgroundTaskFormBase) form;
		// 1 валидируем форму
		ActionMessages actionMessages = validateFormData(frm);
		if (!actionMessages.isEmpty())
		{
			saveErrors(request, actionMessages);
			return mapping.findForward(FORWARD_START);
		}

		try
		{
			BackgroundOperation operation = createOperation(frm);
			if (!frm.isBackground())// если выплянть незамедлительно
			{
				//выполняем действие незамедлительно
				TaskResult taskResult = operation.execute();
				//переходим на форму просмотра отчета
				return createReportForward(frm, taskResult);
			}
			//пользователь хочет создать фоновую задачу создаем ее
			BackgroundTask task = operation.createBackroundTask();
			//перезодим на экшен, соотвествующий успешному окончанию задачи
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("background.replication.task.created.success"), null);
			return createTaskCreatedForward(frm, task);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward(FORWARD_START);
		}
	}
}
