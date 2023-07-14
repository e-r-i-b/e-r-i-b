package com.rssl.phizic.web.common.client.finances.targets;

import com.rssl.phizic.operations.finances.targets.CreateAccountTargetOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.ext.sbrf.payments.ViewDepositTermsForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lepihina
 * @ created 05.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class ViewDepositTermsAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewDepositTermsForm frm = (ViewDepositTermsForm) form;
		CreateAccountTargetOperation operation = createOperation(CreateAccountTargetOperation.class);

		//Получение html-кода условий договора или доп. соглашения к нему
		frm.setHtml(operation.getHtmlByTermsType(frm.getTermsType()));
		return mapping.findForward(FORWARD_START);
	}
}
