package com.rssl.phizic.web.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ext.sbrf.mail.EditMailOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.ViewMailOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 27.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * экшен перехода к письмам
 */

public class MailManagerAction extends OperationalActionBase
{
	private static final String REPLY_FORWARD   = "Reply";
	private static final String VIEW_FORWARD    = "View";
	private static final String LIST_FORWARD    = "List";

	private static final String ID_PARAMETER_NAME  = "id";
	private static final String FROM_QUESTIONARY_PARAMETER_NAME = "fromQuestionary";
	private static final String MAIL_MANAGEMENT_SERVICE = "MailManagment";

	@Override
	public final ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MailManagerForm form = (MailManagerForm) frm;
		switch (Action.valueOf(form.getAction()))
		{
			case view: return doView(form.getMailId(), form.isFromQuestionary());
			case reply: return doReply(form.getMailId());
		}

		throw new UnsupportedOperationException("Неизвестный тип действия " + form.getAction());
	}

	protected Long getViewMailId(Long mailId) throws BusinessException
	{
		ViewMailOperation operation = createOperation(ViewMailOperation.class, MAIL_MANAGEMENT_SERVICE);
		return operation.createView(mailId);
	}

	private ActionForward doView(Long mailId, boolean fromQuestionary) throws BusinessException
	{
		ActionRedirect redirect = new ActionRedirect(getCurrentMapping().findForward(VIEW_FORWARD));
		redirect.addParameter(ID_PARAMETER_NAME,                getViewMailId(mailId));
		redirect.addParameter(FROM_QUESTIONARY_PARAMETER_NAME,  fromQuestionary);
		return redirect;
	}

	private ActionForward doReply(Long mailId) throws BusinessException
	{
		try
		{
			EditMailOperation operation = createOperation(EditMailOperation.class, MAIL_MANAGEMENT_SERVICE);
			ActionRedirect redirect = new ActionRedirect(getCurrentMapping().findForward(REPLY_FORWARD));
			redirect.addParameter(ID_PARAMETER_NAME, operation.createReply(mailId));
			return redirect;
		}
		catch (BusinessLogicException e)
		{
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false), null);
			return getCurrentMapping().findForward(LIST_FORWARD);
		}
	}

	static enum Action
	{
		view,
		reply
	}
}
