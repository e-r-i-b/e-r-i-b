package com.rssl.phizic.web.atm.service;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.UserServiceInfo;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.userprofile.EditUserServiceInfoOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Сохранение служебной информации в профиле клиента
 *
 * @author  Balovtsev
 * @created 10.09.14.
 */
public class UserServiceInfoAction extends EditActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>(1);
		keyMethodMap.put("save", "save");
		return keyMethodMap;
	}

	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();

		EditUserServiceInfoOperation operation = createOperation("EditUserServiceInfoOperation");
		operation.initialize(login);
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return UserServiceInfoForm.SERVICE_INFO_EDIT_FORM;
	}

	@Override
	protected FieldValuesSource getFormProcessorValueSource(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		form.setField(UserServiceInfoForm.FIELD_DATA, ((UserServiceInfoForm) form).getData());
		return super.getFormProcessorValueSource(form, operation);
	}

	@Override
	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{
		((UserServiceInfoForm) form).setData(((UserServiceInfo) entity).getData());
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		((UserServiceInfo) entity).setData((String) data.get(UserServiceInfoForm.FIELD_DATA));
	}
}
