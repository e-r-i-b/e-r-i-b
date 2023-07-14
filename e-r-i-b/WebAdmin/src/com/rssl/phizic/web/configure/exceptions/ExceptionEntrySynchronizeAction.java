package com.rssl.phizic.web.configure.exceptions;

import com.rssl.phizic.operations.config.exceptions.SynchronizeExceptionMappingOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.*;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 27.03.15
 * @ $Author$
 * @ $Revision$
 *
 * ����� ������������� ����������� �������� ������
 */
public class ExceptionEntrySynchronizeAction extends OperationalActionBase
{
	private static final String SYNCHRONIZATION_SUCCESS_MESSAGE = "���������� �������� ������ ������� ���������������.";
	private static final String SYNCHRONIZATION_FAIL_MESSAGE    = "�������� ����������� �������� ������ � ����� [%s] ����������� �������.";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.synchronize", "synchronize");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//������� �������� ������ ��� �������� ���� �������
		createOperation(SynchronizeExceptionMappingOperation.class);
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * ���������������� ���������� ����� �������
	 * @param mapping �������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward synchronize(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ExceptionEntrySynchronizeForm frm = (ExceptionEntrySynchronizeForm) form;
		SynchronizeExceptionMappingOperation operation = createOperation(SynchronizeExceptionMappingOperation.class);
		operation.initialize(frm.getSelectedNodes());

		List<Long> errorNodes = operation.synchronize();
		String message = getSynchronizationMessage(errorNodes);
        saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false), null);

		return mapping.findForward(FORWARD_START);
	}

	private String getSynchronizationMessage(List<Long> errorNodes)
	{
		if (CollectionUtils.isEmpty(errorNodes))
		{
			return SYNCHRONIZATION_SUCCESS_MESSAGE;
		}
		return String.format(SYNCHRONIZATION_FAIL_MESSAGE, StringUtils.join(errorNodes, ","));
	}
}
