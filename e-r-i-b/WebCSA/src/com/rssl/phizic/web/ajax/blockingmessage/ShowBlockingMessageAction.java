package com.rssl.phizic.web.ajax.blockingmessage;

import com.rssl.auth.csa.front.operations.blockingmessage.ShowBlockingMessageOperation;
import com.rssl.phizic.web.common.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшен подт€гивани€ сообщени€ об ограничении входа.
 * @author mihaylov
 * @ created 21.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowBlockingMessageAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String,String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{		
		ShowBlockingMessageForm frm = (ShowBlockingMessageForm) form;
		ShowBlockingMessageOperation operation = new ShowBlockingMessageOperation();
		operation.initialize();
		frm.setBlockingMessage(operation.getBlockingMessage());
		return mapping.findForward(FORWARD_START);
	}
}
