package com.rssl.phizic.web.mail.area;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.froms.validators.TBValidator;
import com.rssl.phizic.business.mail.area.ContactCenterArea;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.mail.area.EditAreaOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * @author komarov
 * @ created 04.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class EditAreaAction extends EditActionBase
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditAreaOperation editOperation = createOperation(EditAreaOperation.class, "ContactCenterAreaManagment");
		Long id = frm.getId();
		if(id != null)
		{
			editOperation.initialize(id);
			return editOperation;
		}
		editOperation.initialize();
		return editOperation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return EditAreaForm.CONTACT_CENTER_AREA_FORM;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		ContactCenterArea area = (ContactCenterArea) entity;
		area.setName((String)data.get("name"));
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditAreaOperation op = (EditAreaOperation) editOperation;
		EditAreaForm form = (EditAreaForm)editForm;
		op.setDepartments(form.getSelectedTBs());
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		EditAreaForm form =  (EditAreaForm) frm;
		ContactCenterArea area = (ContactCenterArea) entity;
		frm.setField("name", area.getName());
		form.setDepartments(area.getDepartments());
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditAreaForm form =  (EditAreaForm) frm;
		if(!frm.isFromStart())
		{
			form.setDepartments(new HashSet<String>(Arrays.asList(form.getSelectedTBs())));
		}
	}

	@Override
	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditAreaForm form = (EditAreaForm)frm;
		ActionMessages msgs = new ActionMessages();

		if(ArrayUtils.isEmpty(form.getSelectedTBs()))
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Не добавлен ни один ТБ!", false));
			return msgs;
		}

		if(!AllowedDepartmentsUtil.isAllowedTerbanksNumbers(Arrays.asList(form.getSelectedTBs())))
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("У Вас нет доступа ко всем добавленным подразделениям.", false));
			return msgs;
		}

		return msgs;
	}
}
