package com.rssl.phizic.web.ext.sbrf.history;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.type.PersonOldIdentity;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ermb.person.identity.PersonIdentityHistoryEditOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.persons.PersonUtils;
import org.apache.struts.action.ActionForward;

import java.util.Date;
import java.util.Map;

/**
 * User: moshenko
 * Date: 22.03.2013
 * Time: 11:18:25
 * Редактирование истроии изменений идентификационных данных клиента
 */
public class PersonHistoryIdentityEditAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		PersonHistoryIdentityEditForm form = (PersonHistoryIdentityEditForm) frm;
		PersonIdentityHistoryEditOperation operation = createOperation(PersonIdentityHistoryEditOperation.class);
		operation.initialize(form.getIdentityId(), PersonUtils.getPersonId(currentRequest()),false);
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return PersonHistoryIdentityEditForm.FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		PersonOldIdentity identity = (PersonOldIdentity) entity;
		identity.setFirstName(data.get("firstName").toString());
		identity.setSurName(data.get("surName").toString());
		identity.setPatrName(data.get("patrName").toString());
		identity.setBirthDay(DateHelper.toCalendar((Date) data.get("birthDay")));
		identity.setDateChange(DateHelper.getCurrentDate());
		identity.setEmployee(EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getLogin());
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> res) throws Exception
	{
		PersonIdentityHistoryEditOperation op = (PersonIdentityHistoryEditOperation) editOperation;
		op.setDocument(res.get("documentType").toString(), res.get("documentNumber").toString(), res.get("documentSeries").toString());
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		PersonHistoryIdentityEditForm form = (PersonHistoryIdentityEditForm) frm;
		PersonIdentityHistoryEditOperation op = (PersonIdentityHistoryEditOperation) operation;
		form.setDocumentTypes(op.getDocumentTypes());
		form.setActivePerson(op.getPerson());
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		PersonHistoryIdentityEditForm form = (PersonHistoryIdentityEditForm) frm;
		PersonOldIdentity identity = (PersonOldIdentity) entity;
		form.setField("firstName", identity.getFirstName());
		form.setField("surName", identity.getSurName());
		form.setField("patrName", identity.getPatrName());
		form.setField("birthDay", DateHelper.toDate(identity.getBirthDay()));
		form.setField("documentType", identity.getDocType());
		form.setField("documentNumber", identity.getDocNumber());
		form.setField("documentSeries", identity.getDocSeries());
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		PersonIdentityHistoryEditOperation op = (PersonIdentityHistoryEditOperation) operation;
		return new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath() + "?person=" + op.getPerson().getId(), true);
	}
}
