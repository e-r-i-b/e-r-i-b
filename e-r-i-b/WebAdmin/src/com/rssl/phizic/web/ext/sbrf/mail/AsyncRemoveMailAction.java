package com.rssl.phizic.web.ext.sbrf.mail;

import com.rssl.phizic.operations.ext.sbrf.mail.EditMailOperation;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 30.05.2012
 * @ $Author$
 * @ $Revision$
 * Удаление временных писем (статус TEMPLATE)
 */
public class AsyncRemoveMailAction extends EditMailAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMailOperation operation = createOperation(EditMailOperation.class);
		operation.removeTemplate(((EditFormBase)form).getId());
		return mapping.findForward(FORWARD_START);
	}
}
