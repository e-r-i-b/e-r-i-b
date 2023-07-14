package com.rssl.phizic.web.actions.templates.offer;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.templates.offer.CreditOfferTemplateEditOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Balovtsev
 * @since 05.06.2015.
 */
public class CreditOfferTemplateEditAction extends EditActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> methodMap = super.getKeyMethodMap();
		methodMap.put("button.template.offer.add", "start");
		return methodMap;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			return super.start(mapping, form, request, response);
		}
		catch (BusinessLogicException e)
		{
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage()), null);
			return mapping.findForward(FORWARD_SUCCESS);
		}
	}

	@Override
	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		try
		{
			return super.doSave(operation, mapping, frm);
		}
		catch (BusinessLogicException e)
		{
			saveError(currentRequest(), new ActionMessage(e.getMessage()));
		}

		return start(mapping, frm, currentRequest(), currentResponse());
	}

	@Override
	protected CreditOfferTemplateEditOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		CreditOfferTemplateEditForm form = (CreditOfferTemplateEditForm) frm;

		CreditOfferTemplateEditOperation operation = createOperation(CreditOfferTemplateEditOperation.class);
		operation.init(form.getId());
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return CreditOfferTemplateEditForm.EDIT;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		CreditOfferTemplate template = (CreditOfferTemplate) entity;

		Calendar fromDate = DateHelper.toCalendar((Date) data.get("fromDate"));
		Calendar fromTime = DateHelper.toCalendar((Date) data.get("fromTime"));

		fromDate.set(Calendar.HOUR_OF_DAY, fromTime.get(Calendar.HOUR_OF_DAY));
		fromDate.set(Calendar.MINUTE,      fromTime.get(Calendar.MINUTE));

		template.setFrom(fromDate);
		template.setText((String) data.get("text"));
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		((CreditOfferTemplateEditForm) frm).setTemplate((CreditOfferTemplate) entity);
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		CreditOfferTemplateEditForm form = (CreditOfferTemplateEditForm) frm;
		CreditOfferTemplateEditOperation op = (CreditOfferTemplateEditOperation) operation;

		form.setTemplate(op.getEntity());
	}
}
