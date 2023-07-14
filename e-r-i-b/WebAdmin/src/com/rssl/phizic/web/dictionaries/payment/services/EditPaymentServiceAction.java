package com.rssl.phizic.web.dictionaries.payment.services;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.payment.services.DublicatePaymentServiceException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.payment.services.EditPaymentServiceOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditActionBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 01.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentServiceAction extends ImageEditActionBase
{
	private static final String FORWARD_LIST  = "List";

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditPaymentServiceOperation operation = createOperation(EditPaymentServiceOperation.class);
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
		return ((EditPaymentServiceForm) frm).createForm();
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		PaymentService paymentService = (PaymentService) entity;
		paymentService.setName((String) data.get("name"));
		paymentService.setSynchKey((String) data.get("synchKey"));
		paymentService.setPopular((Boolean) data.get("popular"));
		paymentService.setDescription((String) data.get("description"));
		paymentService.setPriority((Long) data.get("priority"));
		paymentService.setVisibleInSystem(true);
		paymentService.setCategory((Boolean) data.get("isCategory"));
		paymentService.setShowInSystem((Boolean) data.get("showInSystem"));
		paymentService.setShowInMApi((Boolean) data.get("showInMApi"));
		paymentService.setShowInAtmApi((Boolean) data.get("showInAtmApi"));
		paymentService.setShowInSocialApi((Boolean) data.get("showInSocialApi"));
	}

	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{
		PaymentService paymentService = (PaymentService) entity;
		EditPaymentServiceForm frm = (EditPaymentServiceForm) form;
		frm.setField("name", paymentService.getName());
		frm.setField("description", paymentService.getDescription());
		frm.setField("synchKey", paymentService.getSynchKey());
		frm.setField("popular", paymentService.isPopular());
		frm.setField("system", paymentService.isSystem());
		frm.setField("priority", paymentService.getPriority());
		frm.setField("isCategory", paymentService.getCategory());
		frm.setField("showInSystem", paymentService.getShowInSystem());
		frm.setField("showInMApi", paymentService.getShowInMApi());
		frm.setField("showInAtmApi", paymentService.getShowInAtmApi());
		frm.setField("showInSocialApi", paymentService.getShowInSocialApi());

		List<PaymentService> parentServices = paymentService.getParentServices();
		if (parentServices != null)
		{
			Long[] ids = new Long[parentServices.size()];
			int i = 0;
			for (PaymentService service: parentServices)
			{
				Long id = service.getId();
				ids[i++] = id;
				form.setField("parentServiceName" + id, service.getName());
			}
			frm.setParentServiceIds(ids);
		}
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		EditPaymentServiceOperation op = (EditPaymentServiceOperation) operation;
		ActionForward actionForward = new ActionForward();
		try
		{
			//‘иксируем данные, введенные пользователем
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
		EditPaymentServiceForm form = (EditPaymentServiceForm) frm;
		EditPaymentServiceOperation op = (EditPaymentServiceOperation) operation;
		form.setChildrenServiceIds(op.getChildrenServiceIds());
		updateFormImageData(frm, operation);

		CardOperationCategory category = op.getCardOperationCategory();
		if (category != null)
		{
			frm.setField("cardOperationCategoryId", category.getId());
			frm.setField("cardOperationCategoryName", category.getName());
		}
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		updateOperationImageData(editOperation, editForm, validationResult);
	    EditPaymentServiceOperation op = (EditPaymentServiceOperation) editOperation;
		EditPaymentServiceForm frm = (EditPaymentServiceForm) editForm;
		op.setParentServices(frm.getParentServiceIds());
		op.setCardOperationCategory((Long)validationResult.get("cardOperationCategoryId"));
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		//если редактируем услугу верхнего уровн€, то родительские услуги провер€ть не нужно
		if (frm.getField("isCategory").equals("false"))
		{
			EditPaymentServiceOperation op = (EditPaymentServiceOperation) operation;
	        EditPaymentServiceForm form = (EditPaymentServiceForm) frm;
			List<Long> childrenServiceIds = op.getChildrenServiceIds();
			int levelOfHierarchy = op.getLevelOfHierarchyDown(form.getId());
			ActionMessages msgs              = new ActionMessages();
			for (Long id : form.getParentServiceIds())
			{
				if (CollectionUtils.isNotEmpty(childrenServiceIds) && childrenServiceIds.contains(id)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("”слуга с id="+id+" уже прив€зана к данной услуге!", false));
					return msgs;
				}
				if (op.hasProvider(id)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("”слуга '"+frm.getField("parentServiceName"+id)+"' не может €вл€тьс€ родительской, так как имеютс€ поставщики, использующие ее.", false));
					return msgs;
				}
				if (op.getLevelOfHierarchy(id) + levelOfHierarchy >= 2){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("”слуга '"+frm.getField("parentServiceName"+id)+"' не может €вл€тьс€ родительской, так как уровень вложенности будет больше 3х.", false));
					return msgs;
				}
			}
		}
		return validateImageFormData(frm, operation);
	}
}
