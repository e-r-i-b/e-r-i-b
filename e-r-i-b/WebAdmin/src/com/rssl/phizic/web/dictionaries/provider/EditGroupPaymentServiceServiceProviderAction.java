package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.DublicateServiceProviderException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.provider.EditServiceProvidersOperation;
import com.rssl.phizic.operations.dictionaries.provider.SetupBillingProviderServicesOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author khudyakov
 * @ created 18.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditGroupPaymentServiceServiceProviderAction extends EditServiceProvidersActionBase
{
	private static final String DUBLICATE_SERVICE_MESSAGE = "К этому получателю уже привязана данная услуга. Пожалуйста, выберите другую услугу или получателя.";

	protected Map<String, String>getAdditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAdditionalKeyMethodMap();
		map.put("button.remove", "remove");
		return map;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
	}

	protected SetupBillingProviderServicesOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		SetupBillingProviderServicesOperation operation = createOperation(SetupBillingProviderServicesOperation.class, "ServiceProvidersDictionaryManagement");
		operation.initialize(frm.getId());
		return operation;
	}
	protected EditEntityOperation createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditServiceProvidersOperation operation = createOperation("ViewBillingProviderServicesOperation");
		operation.initialize(frm.getId());
		return operation;
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(form, operation);
		EditGroupPaymentServiceServiceProviderForm frm = (EditGroupPaymentServiceServiceProviderForm) form;
		SetupBillingProviderServicesOperation op = (SetupBillingProviderServicesOperation) operation;
		frm.setBillingProviderServices(op.getProviderServices());
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult) throws Exception
	{
		EditGroupPaymentServiceServiceProviderForm frm = (EditGroupPaymentServiceServiceProviderForm) form;
		SetupBillingProviderServicesOperation op = (SetupBillingProviderServicesOperation) editOperation;
	    op.setAddingPaymentService(Long.valueOf(frm.getNewIds()));
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditGroupPaymentServiceServiceProviderForm frm = (EditGroupPaymentServiceServiceProviderForm) form;
		SetupBillingProviderServicesOperation operation = createEditOperation(frm);
		try
		{
			operation.setRemovingPaymentServices(StrutsUtils.parseIds(frm.getSelectedIds()));
			operation.remove();

			addLogParameters(new BeanLogParemetersReader("Данные редактируемой сущности", operation.getEntity()));
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return createStartActionForward(frm, mapping);
		}
		finally
		{
			updateFormAdditionalData(frm, operation);
		}
		return createSaveActionForward(operation, frm);
	}

	protected ActionForward doSave(EditEntityOperation op, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		SetupBillingProviderServicesOperation operation = (SetupBillingProviderServicesOperation) op;

		try
		{
			//Фиксируем данные, введенные пользователем
			addLogParameters(frm);
			operation.save();
			return createSaveActionForward(operation, frm);
		}
		catch(DublicateServiceProviderException ex)
		{
		    throw new BusinessLogicException(DUBLICATE_SERVICE_MESSAGE, ex);
		}
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		SetupBillingProviderServicesOperation op = (SetupBillingProviderServicesOperation) operation;
		ServiceProviderBase providerBase = op.getEntity();
		ActionForward forward = getCurrentMapping().findForward(FORWARD_SUCCESS);
		return new ActionRedirect(forward.getPath() + "?id=" + providerBase.getId());
	}
}
