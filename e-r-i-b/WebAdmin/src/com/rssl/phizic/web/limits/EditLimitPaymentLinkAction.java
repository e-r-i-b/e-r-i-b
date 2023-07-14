package com.rssl.phizic.web.limits;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.limits.link.LimitPaymentsLink;
import com.rssl.phizic.business.limits.link.LimitPaymentsLinkBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.limits.EditLimitPaymentsLinkOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionRedirect;

import java.util.List;
import java.util.Map;

/**
 * @author vagin
 * @ created 30.05.2012
 * @ $Author$
 * @ $Revision$
 * Ёкшен формы задани€ настроек групп риска дл€ перевода внешнему получателю
 */
public class EditLimitPaymentLinkAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditLimitPaymentsLinkOperation operation = createOperation(EditLimitPaymentsLinkOperation.class, "EditLimitPaymentsLinkManagment");
		operation.initialize(frm.getId());
		EditLimitPaymentLinkForm form = (EditLimitPaymentLinkForm) frm;
		form.setGroupRisks(operation.getGroupRisks());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditLimitPaymentLinkForm.EDIT_LIMIT_PAYMENT_LINK_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		EditLimitPaymentLinkForm form = (EditLimitPaymentLinkForm) frm;

		List<LimitPaymentsLink> links = (List<LimitPaymentsLink>) entity;
		form.setLimitPaymentsLinks(links);

		for (int i = 0; i < links.size(); i++)
		{
			LimitPaymentsLink link = links.get(i);
			if (link.getGroupRisk() != null)
				form.setField(EditLimitPaymentLinkForm.PAYMENT_TO_NAME.get(link.getClass()), link.getGroupRisk().getId());
		}
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		ActionRedirect forward = new ActionRedirect(getCurrentMapping().findForward(FORWARD_SUCCESS));
		forward.addParameter("id", frm.getId());
		return forward;
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		List<LimitPaymentsLink> links = (List<LimitPaymentsLink>) editOperation.getEntity();
		EditLimitPaymentsLinkOperation op = (EditLimitPaymentsLinkOperation) editOperation;

		for (int i = 0; i < links.size(); i++)
		{
			Long id = (Long) validationResult.get(EditLimitPaymentLinkForm.PAYMENT_TO_NAME.get(links.get(i).getClass()));
			((LimitPaymentsLinkBase)links.get(i)).setGroupRisk(id != null ? op.getGroupRiskById(id) : null);
		}
	}
}

