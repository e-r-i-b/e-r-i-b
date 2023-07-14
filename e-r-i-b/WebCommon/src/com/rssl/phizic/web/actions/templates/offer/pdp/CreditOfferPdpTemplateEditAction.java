package com.rssl.phizic.web.actions.templates.offer.pdp;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.templates.offer.pdp.CreditOfferPdpTemplateEditOperation;
import com.rssl.phizic.web.actions.templates.offer.CreditOfferTemplateEditForm;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.util.Map;

/**
 * @author Balovtsev
 * @since  25.07.2015.
 */
public class CreditOfferPdpTemplateEditAction extends EditActionBase
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		CreditOfferPdpTemplateEditOperation operation = createOperation(CreditOfferPdpTemplateEditOperation.class);
		operation.init();
		return operation;
	}

	@Override
	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		try
		{
			super.doSave(operation, mapping, frm);
		}
		catch (BusinessLogicException e)
		{
			saveError(currentRequest(), new ActionMessage(e.getMessage()));
		}

		return start(mapping, frm, currentRequest(), currentResponse());
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return CreditOfferTemplateEditForm.EDIT_PDP;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		CreditOfferTemplate template = (CreditOfferTemplate) entity;
		template.setText((String) data.get("text"));
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		((CreditOfferTemplateEditForm) frm).setTemplate((CreditOfferTemplate) entity);
	}
}
