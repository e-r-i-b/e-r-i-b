package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.phizic.operations.security.GetRegistrationOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ��������� ���������� ������ � ������
 * @author Jatsky
 * @ created 10.01.14
 * @ $Author$
 * @ $Revision$
 */

public class GenerateDisposableLoginAction extends OperationalActionBase
{
	private static final String notSendedErrorText = "�������� ������/������ ���������� - � ������� �� ���������� ������ ��������� ����.";
	private static final String errorText = "� ��������� ������ ��� ������� ������� �� �� ������ ������� ��������� ����� � ������. ����������, ��������� ������� �����.";

	@Override public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GenerateDisposableLoginForm frm = (GenerateDisposableLoginForm) form;

		try
		{
			GetRegistrationOperation gro = createOperation(GetRegistrationOperation.class);
			gro.initialization(frm.getPersonId());

			if (gro.isSended())
			{
				frm.setDisposableLogin(gro.getLogin());
				frm.setError(null);
			}
			else
			{
				frm.setError(notSendedErrorText);
			}
		}
		catch (Exception e)
		{
			frm.setError(errorText);
		}
		return mapping.findForward(FORWARD_START);
	}

	@Override protected boolean isAjax()
	{
		return true;
	}
}
