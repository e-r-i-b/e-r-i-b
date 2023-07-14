package com.rssl.phizic.web.promocodes;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.promocodes.PromoCodeSettings;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.promocodes.EditPromoCodeSettingsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Map;

import static com.rssl.phizic.web.promocodes.EditPromoCodeSettingsForm.*;

/**
 * @author gladishev
 * @ created 07.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditPromoCodeSettingsAction extends EditActionBase
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final String CONSTRAINT_EXCEPTION_MESSAGE = "Для данного тербанка уже задана промо-акция.";

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditPromoCodeSettingsOperation operation = createOperation(EditPromoCodeSettingsOperation.class);
		Long id = frm.getId();
		if (id != null && id != 0)
			operation.initialize(id);
		else
			operation.initializeNew();

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditPromoCodeSettingsForm.FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		PromoCodeSettings settings = (PromoCodeSettings) entity;
		Department department = departmentService.findBySynchKey((String)data.get(TB_SYNCH_KEY));
		settings.setTerbank(department);
		settings.setStartDate(DateHelper.createCalendar(data.get(START_DATE_FIELD), data.get(START_TIME_FIELD)));

		if (!((Boolean) data.get(TERMLESS_FIELD)))
			settings.setEndDate(DateHelper.createCalendar(data.get(END_DATE_FIELD), data.get(END_TIME_FIELD)));
		else
			settings.setEndDate(null);
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		EditPromoCodeSettingsForm form = (EditPromoCodeSettingsForm) frm;
		PromoCodeSettings settings = (PromoCodeSettings)entity;
		if (settings.getTerbank() != null)
		{
			form.setField(TB_SYNCH_KEY, settings.getTerbank().getSynchKey());
			form.setField(TB_NAME_FIELD,settings.getTerbank().getName());
		}
		form.setField(START_DATE_FIELD, settings.getStartDate().getTime());
		form.setField(START_TIME_FIELD, settings.getStartDate().getTime());
		if (settings.getEndDate() != null)
		{
			form.setField(END_DATE_FIELD, settings.getEndDate().getTime());
			form.setField(END_TIME_FIELD, settings.getEndDate().getTime());
		}
		else
		{
			form.setField(TERMLESS_FIELD, "true");
		}
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		try
		{
			return super.doSave(operation, mapping, frm);
		}
		catch (ConstraintViolationException e)
		{
			ActionMessages errors = new ActionMessages();

			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(CONSTRAINT_EXCEPTION_MESSAGE, false));
			saveErrors(currentRequest(), errors);
		}
		return mapping.findForward(FORWARD_START);
	}
}
