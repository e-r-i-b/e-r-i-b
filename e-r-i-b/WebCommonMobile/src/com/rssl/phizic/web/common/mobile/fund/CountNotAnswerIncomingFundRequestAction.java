package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.phizic.operations.fund.response.CountNotAnswerIncomingFundRequestOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author saharnova
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 * Action для подсчета количества неотвеченных входящих запросов краудгифтинга у отправителя средств
 */

public class CountNotAnswerIncomingFundRequestAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CountNotAnswerIncomingFundRequestForm frm = (CountNotAnswerIncomingFundRequestForm) form;
		CountNotAnswerIncomingFundRequestOperation operation = createOperation(CountNotAnswerIncomingFundRequestOperation.class);
		operation.initialize();
		frm.setCount(operation.getCount());
		return mapping.findForward(FORWARD_START);
	}
}
