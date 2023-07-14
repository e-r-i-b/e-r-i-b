package com.rssl.phizic.web.common.socialApi.finances.targets;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.targets.CreateAccountTargetOperation;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;
import com.rssl.phizic.web.common.socialApi.payments.ConfirmSocialPaymentByFormForm;
import com.rssl.phizic.web.common.socialApi.payments.forms.ConfirmSocialPaymentAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Balovtsev
 * @version 03.10.13 9:49
 */
public class ViewTargetSocialAction extends ConfirmSocialPaymentAction
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("confirm", "confirm");
		return map;
	}

	@Override
	public ActionForward confirm(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmSocialPaymentByFormForm form      = (ConfirmSocialPaymentByFormForm) frm;
		ConfirmFormPaymentOperation    operation = getConfirmOperation(request, form);
		saveOperation(request, operation);
		forwardShow(mapping, operation, form, request, true);

		return super.confirm(mapping, form, request, response);
	}

	@Override
	protected ActionForward createNextStageDocumentForward(ConfirmFormPaymentOperation operation, boolean backToEdit) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
	}

	@Override
	protected ConfirmFormPaymentOperation getConfirmOperation(HttpServletRequest request, ConfirmPaymentByFormForm frm) throws BusinessException, BusinessLogicException
	{
		ViewTargetSocialForm form = (ViewTargetSocialForm) frm;

		CreateAccountTargetOperation operation = createOperation(CreateAccountTargetOperation.class);
		operation.initializeById(form.getTargetId());
		form.setId(operation.getEntity().getClaimId());

		return super.getConfirmOperation(request, frm);
	}
}
