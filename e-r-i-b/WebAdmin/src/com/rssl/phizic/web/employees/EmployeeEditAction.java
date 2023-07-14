package com.rssl.phizic.web.employees;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.employee.Employee;
import com.rssl.phizic.gate.login.LoginBlock;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.logging.operations.LogParametersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.access.ChangeLockEmployeeOperation;
import com.rssl.phizic.operations.employees.EditEmployeeOperation;
import com.rssl.phizic.operations.employees.ForceChangeEmployeePasswordOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.LockForm;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.log.CollectionLogParametersReader;
import com.rssl.phizic.web.log.FormLogParametersReader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Roshka
 * @ created 29.09.2005
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeEditAction extends EditActionBase
{
    protected Map<String, String> getAdditionalKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
        map.put("button.lock",      "lock");
        map.put("button.unlock",    "unlock");
	    map.put("button.employee.changePassword", "changePassword");
        return map;
    }

	protected EditEntityOperation createViewOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EmployeeEditForm frm = (EmployeeEditForm) form;
		EditEmployeeOperation operation = createOperation("ViewEmployeeOperation");
		Long id = frm.getEmployeeId();
		if (id == null)
			operation.initialize();
		else
			operation.initialize(id);
		return operation;
	}

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EmployeeEditForm frm = (EmployeeEditForm) form;
		EditEmployeeOperation operation = createOperation("EditEmployeeOperation");
		Long id = frm.getEmployeeId();
		if (id == null)
			operation.initialize();
		else
			operation.initialize(id);
		return operation;
	}

	protected Form getEditForm(EditFormBase form)
	{
		EmployeeEditForm frm = (EmployeeEditForm) form;
		if (frm.getEmployeeId() == null)
			return EmployeeEditForm.CREATE_FORM;

		return EmployeeEditForm.EDIT_FORM;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		Employee employee = (Employee) entity;
		EmployeeEditForm form = (EmployeeEditForm) frm;
		frm.setField("firstName", employee.getFirstName());
	    frm.setField("patrName", employee.getPatrName());
	    frm.setField("surName", employee.getSurName());
	    frm.setField("email",employee.getEmail());
	    frm.setField("mobilePhone",employee.getMobilePhone());
	    frm.setField("info", employee.getInfo());
		frm.setField("sudirLogin", employee.getSUDIRLogin());
		Department department = (Department) employee.getDepartment();
		if (department != null)
		{
			frm.setField("departmentDescription", department.getFullName());
			frm.setField(EmployeeEditForm.DEPARTMENT_TB_FIELD_NAME,  department.getRegion());
			frm.setField(EmployeeEditForm.DEPARTMENT_OSB_FIELD_NAME, department.getOSB());
			frm.setField(EmployeeEditForm.DEPARTMENT_VSP_FIELD_NAME, department.getVSP());
		}
		frm.setField("login", employee.getLogin().getUserId());
		frm.setField("id", employee.getLogin().getId());

		frm.setField(EmployeeEditForm.CA_ADMIN, employee.isCAAdmin());
		frm.setField(EmployeeEditForm.VSP_EMPLOYEE, employee.isVSPEmployee());
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws BusinessException
	{
		EmployeeEditForm      frm       = (EmployeeEditForm) form;
		EditEmployeeOperation op = (EditEmployeeOperation) operation;
		Employee employee = op.getEntity();

		frm.setMaxLengthLogins(ConfigFactory.getConfig(BusinessSettingsConfig.class).getMaxLengthLogins());

		frm.setEmployee(employee);
		currentRequest().setAttribute("$$newId", employee.getId());

		frm.setAllowEditCA(op.isAllowEditCA());
		frm.setVspEmployee(op.isVSPEmployee());
	}

    protected void updateEntity(Object entity, Map<String, Object> data)
	{
		Employee employee = (Employee) entity;
		employee.setFirstName((String) data.get("firstName"));
        employee.setPatrName((String) data.get("patrName"));
        employee.setSurName((String) data.get("surName"));
        employee.setMobilePhone((String) data.get("mobilePhone"));
        employee.setEmail((String) data.get("email"));
        employee.setInfo((String) data.get("info"));
		String userId = (String) data.get("userId");
		employee.setSUDIRLogin((String)data.get("sudirLogin"));
		if (StringHelper.isNotEmpty(userId))
			employee.getLogin().setUserId(userId);
		employee.getLogin().setPassword((String) data.get("password"));
    }

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult) throws BusinessException
	{
		EditEmployeeOperation op = (EditEmployeeOperation) editOperation;
		String tb = (String) validationResult.get("departmentTB");
		String osb = (String) validationResult.get("departmentOSB");
		String vsp = (String) validationResult.get("departmentVSP");
		op.setDepartment(tb, osb, vsp);

		Employee employee = op.getEntity();
		// Изменять настройку может только администратор ЦА
		if (op.isAllowEditCA())
		{
			employee.setCAAdmin((Boolean) validationResult.get(EmployeeEditForm.CA_ADMIN));
		}
		//Изменять настройку может сотрудник без пометки Сотрудник ВСП
		if (!op.isVSPEmployee())
			employee.setVSPEmployee((Boolean) validationResult.get(EmployeeEditForm.VSP_EMPLOYEE));
	}

	public ActionForward changePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward actionForward;

		EmployeeEditForm frm = (EmployeeEditForm) form;
		Form editForm = EmployeeEditForm.CHANGE_PASSWORD_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), editForm);
		ForceChangeEmployeePasswordOperation operation = createOperation(ForceChangeEmployeePasswordOperation.class);

		if(processor.process())
		{
			operation.initialize(frm.getEmployeeId());
			Map<String, Object> result = processor.getResult();
			operation.setNewPassword((String) result.get("password"));
			operation.change();

			actionForward = sendRedirectToSelf(request);
		}
		else
		{
			saveErrors(request, processor.getErrors());
			actionForward = start(mapping, form, request, response);
		}

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

		return actionForward;
	}

	private ChangeLockEmployeeOperation getChangeLockEmployeeOperation(Long personId) throws BusinessException, BusinessLogicException
	{
		ChangeLockEmployeeOperation operation = createOperation(ChangeLockEmployeeOperation.class);
		operation.initialize(personId);
		return operation;
	}

	public ActionForward lock(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EmployeeEditForm form = (EmployeeEditForm) frm;
		Map<String, Object> valuesForSource = new HashMap<String, Object>();
		valuesForSource.put(LockForm.BLOCK_START_DATE_FIELD_NAME, form.getBlockStartDate());
		valuesForSource.put(LockForm.BLOCK_END_DATE_FIELD_NAME, form.getBlockEndDate());
		valuesForSource.put(LockForm.BLOCK_REASON_FIELD_NAME, form.getBlockReason());

		MapValuesSource valuesSource = new MapValuesSource(valuesForSource);

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, LockForm.LOCK_FORM);

		if(processor.process())
		{
			Map<String, Object> processorResult = processor.getResult();
			Date startDate = (Date) processorResult.get(LockForm.BLOCK_START_DATE_FIELD_NAME);
			Date endDate = (Date) processorResult.get(LockForm.BLOCK_END_DATE_FIELD_NAME);
			String reason = (String) processorResult.get(LockForm.BLOCK_REASON_FIELD_NAME);
			ChangeLockEmployeeOperation operation = getChangeLockEmployeeOperation(form.getEmployeeId());
			try
			{
				operation.lock(reason, startDate, endDate);
			}
			catch (BusinessLogicException e)
			{
				saveError(currentRequest(), e.getMessage());
			}
			finally
			{
				LogParametersReader beanLogReader = new BeanLogParemetersReader("Информация о блокируемом", operation.getEntity());
				FormLogParametersReader formLogParametersReader = new FormLogParametersReader("Параметры блокировки", LockForm.LOCK_FORM, valuesForSource);
				addLogParameters(new CompositeLogParametersReader(beanLogReader, formLogParametersReader));
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return start(mapping, form, request, response);
	}

	public ActionForward unlock(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EmployeeEditForm form = (EmployeeEditForm) frm;
		ChangeLockEmployeeOperation operation = getChangeLockEmployeeOperation(form.getEmployeeId());
		try
		{
			operation.unlock();
		}
		catch (BusinessLogicException e)
		{
			saveError(currentRequest(), e.getMessage());
		}
		finally
		{
			LogParametersReader beanLogReader = new BeanLogParemetersReader("Информация о разблокируемом", operation.getEntity());
			List<LoginBlock> blocks = operation.getBlocks();
			LogParametersReader blocksReader;
			if (CollectionUtils.isEmpty(blocks))
				blocksReader = new SimpleLogParametersReader("Снятые блокировки", "нет");
			else
				blocksReader = new CollectionLogParametersReader("Снятые блокировки", blocks);
			addLogParameters(new CompositeLogParametersReader(beanLogReader, blocksReader));
		}
		return start(mapping, form, request, response);
	}
}
