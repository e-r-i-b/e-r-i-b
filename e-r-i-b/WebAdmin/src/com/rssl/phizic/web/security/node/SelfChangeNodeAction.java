package com.rssl.phizic.web.security.node;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.operations.csaadmin.node.NodeNotAvailableException;
import com.rssl.phizic.operations.csaadmin.node.SelfChangeNodeOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.security.CSAAdminSessionListener;
import com.rssl.phizic.web.security.SessionIdFilter;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author mihaylov
 * @ created 08.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен самосто€тельной смены блока
 */
public class SelfChangeNodeAction extends OperationalActionBase
{
	private static final String FORWARD_GO_TO_AUTHORIZED_INDEX   = "GotoAuthorizedIndex";

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			SelfChangeNodeForm frm = (SelfChangeNodeForm) form;
			SelfChangeNodeOperation operation = createOperation(SelfChangeNodeOperation.class);
			operation.initialize(frm.getNodeId());
			operation.changeNode();
			closeSession(request);
			return new ActionRedirect(operation.getNodeUrl());
		}
		catch (NodeNotAvailableException e)
		{
			return getNodeNotAvailableForward(e.getNodeInfo());
		}
	}

	protected void closeSession(HttpServletRequest request)
	{
		//закрываем текущую сессию в блоке
		HttpSession session = request.getSession(false);
        if (session != null)
        {
	        session.setAttribute(CSAAdminSessionListener.DONT_CLOSE_CSA_ADMIN_SESSION_KEY,true);
	        session.setAttribute(SessionIdFilter.INVALIDATE_SESSION_KEY, true);
        }
	}

	protected ActionForward getNodeNotAvailableForward(NodeInfo nodeInfo)
	{
		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Ѕлок \"" + nodeInfo.getName() + "\" не доступен!", false), null);
		return getCurrentMapping().findForward(FORWARD_GO_TO_AUTHORIZED_INDEX);
	}
}
