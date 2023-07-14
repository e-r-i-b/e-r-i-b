package com.rssl.phizic.web.fund;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.fund.response.ViewFundSenderResponseOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author usachev
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Action для получения детальной информации об ответе на сбор средств
 */

public class FundSenderResponseAction extends OperationalActionBase
{
	private static final String SHOW_FUND_REQUEST_INFORMATION = "ShowFundRequestInform";

	/**
	 * Просмотр информации об ответе на исходящий запрос на сбор средств
	 */
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FundSenderResponseForm frm = (FundSenderResponseForm)form;
		ViewFundSenderResponseOperation operation = createOperation();
		operation.init(frm.getId());
		frm.setResponse(operation.getResponse());
		frm.setAccumulatedSum(operation.getAccumulatedSum());
		return mapping.findForward(SHOW_FUND_REQUEST_INFORMATION);
	}

	/**
	 * Создание операции.
	 * @return Операция
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private ViewFundSenderResponseOperation createOperation() throws BusinessException, BusinessLogicException
	{
		    if (checkAccess(ViewFundSenderResponseOperation.class, "ViewPaymentList"))
		    {
			    return createOperation(ViewFundSenderResponseOperation.class, "ViewPaymentList");
		    }
			return createOperation(ViewFundSenderResponseOperation.class, "ViewPaymentListUseClientForm");
	}
}
