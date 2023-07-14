package com.rssl.phizic.web.common.client.moneyBox;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ChangePaymentStatusType;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.operations.ext.sbrf.payment.ChangeStatusMoneyBoxOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.actions.payments.forms.EditDocumentActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tisov
 * @ created 01.10.14
 * @ $Author$
 * @ $Revision$
 * Ёкшн смены статуса копилки
 */

public class ChangeStatusMoneyBoxAction extends EditDocumentActionBase
{

	private static final String SUCCESS_FORWARD_NAME = "Success";
	private static final String FAIL_FORWARD_NAME    = "Fail";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}
	@Override
	protected EditDocumentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm frm, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		ChangeStatusMoneyBoxForm form = (ChangeStatusMoneyBoxForm) frm;
		ChangePaymentStatusType type = ChangePaymentStatusType.valueOf(form.getChangePaymentStatusType());

		return createOperation(ChangeStatusMoneyBoxOperation.class, type.getClientServiceKey());
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChangeStatusMoneyBoxForm frm = (ChangeStatusMoneyBoxForm) form;
		try
		{
			ChangeStatusMoneyBoxOperation operation = (ChangeStatusMoneyBoxOperation) createEditOperation(request, frm, null);
			operation.initialize(frm.getId(), ChangePaymentStatusType.valueOf(frm.getChangePaymentStatusType()));
			operation.changeStatus();
			saveOperationMessages(request, operation);
		}
		catch (BusinessException e)
		{
			log.error(e);
			saveError(request, StrutsUtils.getMessage("moneyBox.change.status.fail", "moneyboxBundle"));
			return mapping.findForward(FAIL_FORWARD_NAME);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward(FAIL_FORWARD_NAME);
		}
		saveMessage(request, StrutsUtils.getMessage("moneyBox.change.status.success", "moneyboxBundle"));
		return mapping.findForward(SUCCESS_FORWARD_NAME);
	}

	private void saveOperationMessages(HttpServletRequest request, ChangeStatusMoneyBoxOperation operation)
	{
		for(String message: operation.getStateMachineEvent().getMessageCollector().getMessages())
		{
			saveMessage(request, message);
		}
	}
}
