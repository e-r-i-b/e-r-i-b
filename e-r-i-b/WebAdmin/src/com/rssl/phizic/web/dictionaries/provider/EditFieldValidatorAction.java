package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.fields.FieldValidationRuleImpl;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.provider.EditFieldValidatorOperation;
import com.rssl.phizic.operations.dictionaries.provider.EditServiceProvidersOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRuleType;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import java.util.Map;
import java.security.AccessControlException;

/**
 * @author krenev
 * @ created 05.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditFieldValidatorAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditFieldValidatorForm frm = (EditFieldValidatorForm) form;
		EditFieldValidatorOperation operation = createOperation("EditFieldValidatorOperation");
		Long validatorId = frm.getValidatorId();
		if (validatorId != null && validatorId != 0)
		{
			operation.initialize(validatorId);
		}
		else
		{
			operation.initializeNew(frm.getId());
		}
		return operation;
	}

	protected EditEntityOperation createViewOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditFieldValidatorForm frm = (EditFieldValidatorForm) form;
		EditFieldValidatorOperation operation = createOperation("ViewFieldValidatorOperation");
		Long validatorId = frm.getValidatorId();
		if (validatorId != null && validatorId != 0)
		{
			operation.initialize(validatorId);
		}
		else
		{
			operation.initializeNew(frm.getId());
		}
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditFieldValidatorForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		FieldValidationRuleImpl fieldValidationRule = (FieldValidationRuleImpl) entity;
		String validatorType = (String) data.get("validatorType");
		fieldValidationRule.setFieldValidationRuleType(FieldValidationRuleType.fromValue(validatorType));
		fieldValidationRule.setErrorMessage((String) data.get("validatorErrorMessage"));
	}

	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{
		EditFieldValidatorForm frm = (EditFieldValidatorForm) form;
		FieldValidationRuleImpl rule = (FieldValidationRuleImpl) entity;
		frm.setFieldId(rule.getFieldId());
		frm.setField("validatorErrorMessage", rule.getErrorMessage());
		Map<String, Object> fieldValidators = rule.getFieldValidators();
		if (fieldValidators == null || fieldValidators.isEmpty())
		{
			//задаем значения по умолчанию
			frm.setField("validatorType", FieldValidationRuleType.REGEXP);
			frm.setField("validatorRuleType", "string");
			return;
		}
		frm.setField("validatorRuleType", fieldValidators.keySet().toArray()[0]);
		fieldValidators = rule.getParameters();
		String validatorType = (String) fieldValidators.keySet().toArray()[0];
		frm.setField("validatorExpression", fieldValidators.get(validatorType));
		frm.setField("validatorType", validatorType);
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		EditFieldValidatorForm frm = (EditFieldValidatorForm) form;
		EditFieldValidatorOperation op = (EditFieldValidatorOperation) operation;
		frm.setId(op.getProviderId());
		boolean hasAccess = false;
		//Необходимо задизаблить поля если нет прав редактировать поставщика.
		try
		{
			((EditServiceProvidersOperation) createOperation("EditServiceProvidersOperation")).initialize(frm.getId());
			hasAccess = true;
		}
		catch (AccessControlException e)
		{
			//Либо операция не создалась, либо сработал рестрикшен - дизаблим поля.
			hasAccess = false;
		}
		frm.setEditable(hasAccess);
		frm.setAllFields(op.getFieldDescriptions());
		frm.setField("providerName", op.getProviderName());
		frm.setField("billingName", op.getBillingName());
		frm.setField("serviceName", op.getServiceName());
		FieldDescription fieldDescription = op.getFieldDescription();
		if (fieldDescription == null)
		{
			return;
		}
		frm.setId(fieldDescription.getHolderId());
		frm.setFieldId(fieldDescription.getId());
		frm.setField("fieldCode", op.getFieldDescription().getExternalId());
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditFieldValidatorForm form = (EditFieldValidatorForm) editForm;
		EditFieldValidatorOperation op = (EditFieldValidatorOperation) editOperation;
		op.setFieldId(form.getFieldId());
		String validatorRuleType = (String) editForm.getField("validatorRuleType");
		op.setValidatorRule(validatorRuleType, editForm.getField("validatorExpression"));
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		EditFieldValidatorOperation op = (EditFieldValidatorOperation) operation;
		FieldValidationRuleImpl entity = op.getEntity();
		ActionForward forward = new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS));
		forward.setPath(forward.getPath() + "?validatorId=" + entity.getId());

		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.dictionaries.provider.save.success"), null);
		return forward;
	}
}