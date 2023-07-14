package com.rssl.phizic.web.departments;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.extended.DepartmentAutoType;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.departments.EditDepartmentOperation;
import com.rssl.phizic.operations.dictionaries.billing.ListBillingsOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizicgate.manager.routing.Adapter;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Map;

/**
 * @author egorova
 * @ created 23.07.2009
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен редактировани€ подразделени€
 */

public class EditDepartmentAction extends EditActionBase
{
	@Override
	protected EditEntityOperation createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ViewDepartmentOperation", (EditDepartmentForm) frm);
	}

	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("EditDepartmentOperation", (EditDepartmentForm) frm);
	}

	private EditEntityOperation createOperation(String operationName, EditDepartmentForm form) throws BusinessException, BusinessLogicException
	{
		EditDepartmentOperation operation = createOperation(operationName);
		Long id = form.getId();

		if (id != null)
			operation.initialize(id);
		else
			operation.initializeNew(form.getParentId());

		return operation;
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		ExtendedDepartment department = (ExtendedDepartment) entity;
		EditDepartmentForm form = (EditDepartmentForm) frm;
		form.setField(EditDepartmentForm.OFFICE_ID_FIELD_NAME, department.getSynchKey());
		form.setField(EditDepartmentForm.NAME_FIELD_NAME, department.getName());
		form.setField(EditDepartmentForm.CITY_FIELD_NAME, department.getCity());
		form.setField(EditDepartmentForm.ADDRESS_FIELD_NAME, department.getAddress());
		form.setField(EditDepartmentForm.LOCATION_FIELD_NAME, department.getLocation());
		form.setField(EditDepartmentForm.TELEPHONE_FIELD_NAME, department.getTelephone());
		form.setField(EditDepartmentForm.WEEK_OPERATION_TIME_BEGIN_FIELD_NAME, EditDepartmentForm.formatTime(department.getWeekOperationTimeBegin()));
		form.setField(EditDepartmentForm.WEEK_OPERATION_TIME_END_FIELD_NAME, EditDepartmentForm.formatTime(department.getWeekOperationTimeEnd()));
		form.setField(EditDepartmentForm.TIME_SCALE_FIELD_NAME, department.getTimeScale());
		form.setField(EditDepartmentForm.NOTIFY_CONTRACT_CANCELLATION_FIELD_NAME, department.getNotifyContractCancelation());
		form.setField(EditDepartmentForm.CONNECTION_CHARGE_FIELD_NAME, department.getConnectionCharge());
		form.setField(EditDepartmentForm.MONTHLY_CHARGE_FIELD_NAME, department.getMonthlyCharge());
		form.setField(EditDepartmentForm.MAIN_DEPARTMENT_FIELD_NAME, department.getMain());
		form.setField(EditDepartmentForm.POSSIBLE_LOANS_OPERATION_FIELD_NAME, department.isPossibleLoansOperation());
		form.setField(EditDepartmentForm.REGION, department.getRegion());
		form.setField(EditDepartmentForm.BRANCH, department.getOSB());
		form.setField(EditDepartmentForm.OFFICE, department.getVSP());
		form.setField(EditDepartmentForm.TIME_ZONE, department.getTimeZone());
		form.setField(EditDepartmentForm.SERVICE_FIELD_NAME, department.isService());
		form.setField(EditDepartmentForm.ESB_SUPPORTED, department.isEsbSupported());
		form.setField(EditDepartmentForm.CREDIT_CARD_OFFICE, department.isCreditCardOffice());
		form.setField(EditDepartmentForm.OPEN_IMA_OFFICE, department.isOpenIMAOffice());
		form.setField(EditDepartmentForm.AUTOMATION, department.getAutomationType());
		form.setField(EditDepartmentForm.BILLING_ID_FIELD_NAME, department.getBillingId());
		form.setField(EditDepartmentForm.OFFICE_NAME_FIELD_NAME, department.getName());
		form.setField(EditDepartmentForm.NOT_ACTIVE, !department.isActive());
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		EditDepartmentOperation op = (EditDepartmentOperation) operation;
		EditDepartmentForm form = (EditDepartmentForm) frm;

		Adapter adapter = op.getAdapter();
		if (adapter != null)
		{
			form.setField(EditDepartmentForm.EXTERNAL_SYSTEM_ID_FIELD_NAME, adapter.getId());
			form.setField(EditDepartmentForm.EXTERNAL_SYSTEM_NAME_FIELD_NAME, adapter.getName());
			form.setField(EditDepartmentForm.EXTERNAL_SYSTEM_UUID_FIELD_NAME, adapter.getUUID());

			boolean isExternalSystemOffice = op.isExternalSystemOffice();
			form.setField(EditDepartmentForm.IS_EXTERNAL_SYSTEM_OFFICE_FIELD_NAME, isExternalSystemOffice);

			if (isExternalSystemOffice)
				form.setField(EditDepartmentForm.OFFICE_NAME_FIELD_NAME, op.getOfficeName());
		}

		Billing billing = op.getBilling();
		if (billing != null)
			form.setField(EditDepartmentForm.BILLING_NAME_FIELD_NAME, billing.getName());

		form.setParentDepartment(op.getParent());
		form.setDepartment((Department) operation.getEntity());
	}

	@Override
	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditDepartmentOperation op = (EditDepartmentOperation) operation;
		ActionMessages actionMessages = super.validateAdditionalFormData(frm, operation);

		if (op.getEntity().getMain() || !BooleanUtils.toBoolean((String) frm.getField(EditDepartmentForm.MAIN_DEPARTMENT_FIELD_NAME)))
			return actionMessages;

		String adapterUUID = (String) frm.getField(EditDepartmentForm.EXTERNAL_SYSTEM_UUID_FIELD_NAME);
		if (op.getMainDepartmentsCount(adapterUUID) >= 1)
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("¬о внешней системе уже существует головное подразделение! ќно должно быть единственным.", false));

		return actionMessages;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return EditDepartmentForm.EDIT_FORM;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> result) throws BusinessException
	{
		ExtendedDepartment department = (ExtendedDepartment) entity;
		department.setPossibleLoansOperation((Boolean) result.get(EditDepartmentForm.POSSIBLE_LOANS_OPERATION_FIELD_NAME));
		department.setName((String) result.get(EditDepartmentForm.NAME_FIELD_NAME));
		department.setCity((String) result.get(EditDepartmentForm.CITY_FIELD_NAME));
		department.setAddress((String) result.get(EditDepartmentForm.ADDRESS_FIELD_NAME));
		department.setLocation((String) result.get(EditDepartmentForm.LOCATION_FIELD_NAME));
		department.setTelephone((String) result.get(EditDepartmentForm.TELEPHONE_FIELD_NAME));
		department.setWeekOperationTimeBegin((Time) result.get(EditDepartmentForm.WEEK_OPERATION_TIME_BEGIN_FIELD_NAME));
		department.setWeekOperationTimeEnd((Time) result.get(EditDepartmentForm.WEEK_OPERATION_TIME_END_FIELD_NAME));
		department.setTimeScale((String) result.get(EditDepartmentForm.TIME_SCALE_FIELD_NAME));
		department.setNotifyContractCancelation((Long) result.get(EditDepartmentForm.NOTIFY_CONTRACT_CANCELLATION_FIELD_NAME));
		department.setConnectionCharge((BigDecimal) result.get(EditDepartmentForm.CONNECTION_CHARGE_FIELD_NAME));
		department.setMonthlyCharge((BigDecimal) result.get(EditDepartmentForm.MONTHLY_CHARGE_FIELD_NAME));
		department.setMain((Boolean) result.get(EditDepartmentForm.MAIN_DEPARTMENT_FIELD_NAME));

		department.buildCode(result);

		department.setAdapterUUID((String) result.get(EditDepartmentForm.EXTERNAL_SYSTEM_UUID_FIELD_NAME));

		if (checkAccess(ListBillingsOperation.class))
			department.setBillingId((Long) result.get(EditDepartmentForm.BILLING_ID_FIELD_NAME));

		Integer timeZone = (Integer) result.get(EditDepartmentForm.TIME_ZONE);
		if (timeZone != null)
			department.setTimeZone(timeZone);

		department.setService((Boolean) result.get(EditDepartmentForm.SERVICE_FIELD_NAME));
		department.setEsbSupported((Boolean) result.get(EditDepartmentForm.ESB_SUPPORTED));
		department.setCreditCardOffice((Boolean) result.get(EditDepartmentForm.CREDIT_CARD_OFFICE));
		department.setAutomationType((DepartmentAutoType) result.get(EditDepartmentForm.AUTOMATION));
		department.setSynchKey((String) result.get(EditDepartmentForm.OFFICE_ID_FIELD_NAME));
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		super.updateOperationAdditionalData(editOperation, editForm, validationResult);
		EditDepartmentOperation operation = (EditDepartmentOperation) editOperation;
		operation.updateOfficeId();
	}

	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		if (frm.getId() == null)
			return getCurrentMapping().findForward(FORWARD_SUCCESS);

		return getCurrentMapping().findForward(FORWARD_START);
	}
}
