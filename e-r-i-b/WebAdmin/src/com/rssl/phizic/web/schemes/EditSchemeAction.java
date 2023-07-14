package com.rssl.phizic.web.schemes;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.scheme.EditSchemeOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kidyaev
 * @ created 29.09.2005
 * @ $Author$
 * @ $Revision$
 */
public class EditSchemeAction extends EditActionBase
{
	protected EditEntityOperation createViewOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditSchemeForm frm = (EditSchemeForm) form;
		EditEntityOperation operation;
		if (frm.getId() == null)
		{
			operation = createAddOpertion(frm);
		}
		else
		{
			operation = createOperation("ViewSchemeOperation");
			((EditSchemeOperation)operation).initialize(frm.getId());
		}
		return operation;
	}

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditSchemeForm frm = (EditSchemeForm) form;
		EditEntityOperation operation;

		if (frm.getId() == null)
		{
			operation = createAddOpertion(frm);
		}
		else
		{
			operation = createOperation(EditSchemeOperation.class);
			((EditSchemeOperation)operation).initialize(frm.getId());
		}
		return operation;
	}

	private EditEntityOperation createAddOpertion(EditSchemeForm frm) throws BusinessLogicException, BusinessException
	{
		EditSchemeOperation operation = createOperation("AddSchemeOperation");
		String scope = frm.getScope();
		operation.initializeNew(scope);

		return operation;
	}

	protected void updateForm(EditFormBase frm, Object entity){}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws BusinessException
	{
		EditSchemeForm frm = (EditSchemeForm) form;

		EditSchemeOperation op = (EditSchemeOperation) operation;
		SharedAccessScheme scheme = op.getEntity();

		updateMainInformation(frm, op, scheme);

		frm.setAllServices(op.getServicesTuple());
	}

	protected void updateMainInformation(EditSchemeForm frm, EditSchemeOperation op, SharedAccessScheme scheme)
	{
		frm.setHelpers(op.getServiceHelpers());
		frm.setName(scheme.getName());
		frm.setCategory(scheme.getCategory());
		addSelectedServicesInformation(frm, scheme);
	}

	protected void addSelectedServicesInformation(EditSchemeForm frm, SharedAccessScheme scheme)
	{
		List<Service> services = scheme.getServices();
		String[] selectedServices = new String[services.size()];

		int i = 0;
		for (Service service : services)
		{
			selectedServices[i++] = service.getId().toString();
		}
		frm.setSelectedServices(selectedServices);
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditSchemeForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) {}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult) throws BusinessException
	{
		EditSchemeOperation op = (EditSchemeOperation) editOperation;
		EditSchemeForm frm = (EditSchemeForm) form;

		String[] serviceStringIds = frm.getSelectedServices();
		op.setNewCategory(frm.getCategory());
		op.setNewName(frm.getName());

		List<Long> serviceIds = new ArrayList<Long>();

		for (String stringId : serviceStringIds)
		{
			serviceIds.add(Long.valueOf(stringId));
		}

		op.setNewServices(serviceIds);
	}
}