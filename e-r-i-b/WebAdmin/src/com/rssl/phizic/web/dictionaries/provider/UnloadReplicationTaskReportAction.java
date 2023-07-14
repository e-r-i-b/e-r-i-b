package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.background.BackgroundTask;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.operations.background.ViewBackgroundTaskOperationBase;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.UnloadOperationalActionBase;
import com.rssl.phizic.web.common.background.ViewBackgroundTaskForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author krenev
 * @ created 27.08.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class UnloadReplicationTaskReportAction extends UnloadOperationalActionBase<String>
{
	private static final String ERROR_MESSAGE = "Отчет пуст!";

	public Pair<String, String> createData(ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewBackgroundTaskForm frm = (ViewBackgroundTaskForm) form;

		ViewBackgroundTaskOperationBase operation = createEntityOperation();
		operation.initialize(frm.getId());

		BackgroundTask task = operation.getEntity();
		String report = getReport(task);

		frm.setTask(task);

		if (StringHelper.isEmpty(report))
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ERROR_MESSAGE, false));
			saveErrors(request, errors);
			return null;
		}

		return new Pair<String, String>("replication-report-" + task.getId() + ".txt", report);
	}

	/**
	 * Получить детализированный отчет фоновой задачи
	 * @param task фоновая задача
	 * @return отчет
	 */
	abstract protected String getReport(BackgroundTask task);

	/**
	 * Создание сущности операции с проверкой прав
	 * @return сущность операции
	 * @throws BusinessException
	 */
	abstract protected ViewBackgroundTaskOperationBase createEntityOperation() throws BusinessException;
}
