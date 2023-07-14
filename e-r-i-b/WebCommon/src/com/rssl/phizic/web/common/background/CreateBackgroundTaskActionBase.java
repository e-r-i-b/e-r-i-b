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
 * ������� ����� ������� �������������� �������� ������� �����
 */
public abstract class CreateBackgroundTaskActionBase extends OperationalActionBase
{
	public static final String FORWARD_START = "Start";
	public static final String FORWARD_TASK_LIST = "TaskList";
	public static final String FORWARD_REPORT = "Report";

	/**
	 * ��������� ������ �����, �������� ������� ���������� ����� FormProcessor
	 * ������ ����� �������������� �� ��������!!!
	 * @param form ����� ��� ����������
	 * @return ActionMessages
	 */
	protected ActionMessages validateFormData(CreateBackgroundTaskFormBase form)
	{
		return new ActionMessages();
	}

	/**
	 * �������� ������� �� �������� ������ �� ��������� ���������� ��������
	 * �� ��������� ����� ������������ � ����� � ���������� ����������� ������� REPORT
	 * @param frm �����
	 * @param taskResult  ��������� ����������
	 * @return �������
	 */
	protected ActionForward createReportForward(CreateBackgroundTaskFormBase frm, TaskResult taskResult)
	{
		frm.setResult(taskResult);
		return getCurrentMapping().findForward(FORWARD_REPORT);
	}

	/**
	 * �������� ������� �� �������� ����� ��������� ������� ������� ������
	 * @param frm �����
	 * @param task ��������� ������
	 * @return ������. �� ��������� ������� �� TASK_LIST
	 */
	protected ActionForward createTaskCreatedForward(CreateBackgroundTaskFormBase frm, BackgroundTask task)
	{
		return getCurrentMapping().findForward(FORWARD_TASK_LIST);
	}

	/**
	 * ������� � ������������������� ��������
	 * @param form ����� � ������� ������������
	 * @return ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected abstract BackgroundOperation createOperation(CreateBackgroundTaskFormBase form) throws BusinessException, BusinessLogicException;

	public ActionForward doIt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateBackgroundTaskFormBase frm = (CreateBackgroundTaskFormBase) form;
		// 1 ���������� �����
		ActionMessages actionMessages = validateFormData(frm);
		if (!actionMessages.isEmpty())
		{
			saveErrors(request, actionMessages);
			return mapping.findForward(FORWARD_START);
		}

		try
		{
			BackgroundOperation operation = createOperation(frm);
			if (!frm.isBackground())// ���� �������� ���������������
			{
				//��������� �������� ���������������
				TaskResult taskResult = operation.execute();
				//��������� �� ����� ��������� ������
				return createReportForward(frm, taskResult);
			}
			//������������ ����� ������� ������� ������ ������� ��
			BackgroundTask task = operation.createBackroundTask();
			//��������� �� �����, �������������� ��������� ��������� ������
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
