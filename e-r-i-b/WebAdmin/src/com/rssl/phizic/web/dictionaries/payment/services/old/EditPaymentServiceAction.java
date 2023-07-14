package com.rssl.phizic.web.dictionaries.payment.services.old;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.payment.services.CategoryServiceType;
import com.rssl.phizic.business.dictionaries.payment.services.DublicatePaymentServiceException;
import com.rssl.phizic.business.dictionaries.payment.services.api.PaymentServiceOld;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.payment.services.api.EditPaymentServiceOldOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditActionBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author lukina
 * @ created 29.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class EditPaymentServiceAction extends ImageEditActionBase
{
	private static final String FORWARD_LIST  = "List";

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditPaymentServiceOldOperation operation = createOperation(EditPaymentServiceOldOperation.class);
		Long id = frm.getId();
		if ((id != null) && (id != 0))
		{
			operation.initialize(id);
		}
		else
		{
			operation.initializeNew(((EditPaymentServiceForm)frm).getParentId());
		}
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditPaymentServiceForm.FILTER_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		PaymentServiceOld paymentService = (PaymentServiceOld) entity;
		paymentService.setName((String) data.get("name"));
		paymentService.setSynchKey((String) data.get("synchKey"));
		paymentService.setPopular((Boolean) data.get("popular"));
		paymentService.setDescription((String) data.get("description"));
		paymentService.setPriority((Long) data.get("priority"));
		paymentService.setVisibleInSystem(true);
		Set<CategoryServiceType> categories = new HashSet<CategoryServiceType>();
		for (CategoryServiceType category : CategoryServiceType.values())
		{
			if ((Boolean) data.get("category_"+category.toString()))
				categories.add(category);
		}
		paymentService.setCategories(categories);
	}

	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{
		PaymentServiceOld paymentService = (PaymentServiceOld) entity;
		EditPaymentServiceForm frm = (EditPaymentServiceForm) form;
		PaymentServiceOld parent = paymentService.getParent();
		frm.setField("name", paymentService.getName());
		frm.setField("description", paymentService.getDescription());
		frm.setField("synchKey", paymentService.getSynchKey());
		frm.setField("parent", parent != null?parent.getName():"");
		frm.setField("popular", paymentService.isPopular());
		frm.setField("system", paymentService.isSystem());
		frm.setField("priority", paymentService.getPriority());
		Set<CategoryServiceType> cat = paymentService.getCategories();
		if (cat != null)
		{
			for (CategoryServiceType category : cat)
			{
				frm.setField("category_"+category.toString(), true);
			}
		}
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		EditPaymentServiceOldOperation op = (EditPaymentServiceOldOperation) operation;
		ActionForward actionForward = new ActionForward();
		try
		{
			//Фиксируем данные, введенные пользователем
			addLogParameters(frm);
			op.save();
			actionForward = mapping.findForward(FORWARD_LIST);
		}
		catch(DublicatePaymentServiceException ex)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
			saveErrors(currentRequest(), msgs);
		    actionForward = mapping.findForward(FORWARD_START);
		}
		return actionForward;
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormImageData(frm, operation);
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		updateOperationImageData(editOperation, editForm, validationResult);
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return validateImageFormData(frm, operation);
	}
}

