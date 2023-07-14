package com.rssl.phizic.web.ext.sbrf.deposits;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityVisibility;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.deposits.EditDepositEntityDetailsOperation;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.transform.TransformerConfigurationException;

/**
 * @author EgorovaA
 * @ created 20.04.15
 * @ $Author$
 * @ $Revision$
 */
public class EditDepositEntityAction extends EditDepositProductAction
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditDepositEntityDetailsOperation operation = createOperation(EditDepositEntityDetailsOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws TransformerConfigurationException, BusinessException
	{
		EditDepositEntityForm frm = (EditDepositEntityForm)form;
		EditDepositEntityDetailsOperation op  = (EditDepositEntityDetailsOperation) operation;
		DepositEntityVisibility visibility = op.getEntity();

		frm.setAvailable(visibility.isAvailableOnline());
		frm.setDepositName(visibility.getName());
		frm.setDepartments(visibility.getAllowedDepartments());
		frm.setDepositEntitySubTypes(op.getDepositEntitySubTypes(frm.getId()));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws BusinessException
	{
		EditDepositEntityDetailsOperation op  = (EditDepositEntityDetailsOperation) editOperation;
		EditDepositEntityForm form = (EditDepositEntityForm) editForm;

		DepositEntityVisibility visibility = op.getEntity();
		visibility.setAvailableOnline(form.isAvailable());
		visibility.setAllowedDepartments(op.getProductAllowedTBList(form.getTerbankIds()));

		List<Long> subTypes = new ArrayList<Long>();
		for (String subType : form.getDepositSubTypeIds())
		{
			subTypes.add(Long.valueOf(subType));
		}
		visibility.setDepositSubTypes(subTypes);
	}
}
