package com.rssl.phizic.web.persons;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.profile.images.AvatarInfo;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.person.EditBasketRequisitesOperation;
import com.rssl.phizic.operations.userprofile.EditPersonAvatarOperation;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.DateHelper;
import org.apache.struts.action.*;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Редактирование реквизитов документов клиента
 * @author miklyaev
 * @ created 04.06.14
 * @ $Author$
 * @ $Revision$
 */

public class EditBasketRequisitesAction extends EditPersonAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
        map.put("button.removeDocumentDL", "removeDL");
		map.put("button.removeDocumentRC", "removeRC");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditBasketRequisitesForm frm = (EditBasketRequisitesForm) form;
		EditBasketRequisitesOperation operation = createOperation("ViewBasketRequisitesOperation");

		try
		{
			operation.initialize(frm.getPerson());
		}
		catch (BusinessLogicException e)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, msgs);
		}

		updateForm(frm, operation);
		return mapping.findForward(FORWARD_START);
	}

	protected void updateForm(EditBasketRequisitesForm frm, Operation op) throws BusinessException, BusinessLogicException
	{
		EditBasketRequisitesOperation operation = (EditBasketRequisitesOperation) op;
		frm.setActivePerson(operation.getPerson());

		if (PermissionUtil.impliesOperation("ViewPersonAvatarOperation", "ViewPersonAvatar"))
		{
			EditPersonAvatarOperation editPersonAvatarOperation = createOperation("ViewPersonAvatarOperation", "ViewPersonAvatar");
			long loginId = operation.getPerson().getLogin().getId();
			AvatarInfo info = editPersonAvatarOperation.getPersonAvatarInfo(AvatarType.AVATAR, loginId);
			frm.setHasAvatar(info.getImageInfo() != null);
			if (info.getImageInfo() != null)
				frm.setAvatarInfo(info);
		}

		ProfileConfig profileConfig = ConfigFactory.getConfig(ProfileConfig.class);
		frm.setAccessDL(profileConfig.isAccessUserDocumentDL());
		frm.setAccessRC(profileConfig.isAccessUserDocumentRC());

		UserDocument userDocument = operation.getEntityDL();
		frm.setField(EditBasketRequisitesForm.NUMBER_FIELD_DL, userDocument.getNumber());
		frm.setField(EditBasketRequisitesForm.SERIES_FIELD_DL, userDocument.getSeries());
		frm.setField(EditBasketRequisitesForm.ISSUE_BY_FIELD_DL, userDocument.getIssueBy());
		frm.setField(EditBasketRequisitesForm.ISSUE_DATE_FIELD_DL, userDocument.getIssueDate() != null ? userDocument.getIssueDate().getTime() : null);
		frm.setField(EditBasketRequisitesForm.EXPIRE_DATE_FIELD_Dl, userDocument.getExpireDate() != null ? userDocument.getExpireDate().getTime() : null);

		userDocument = operation.getEntityRC();
		frm.setField(EditBasketRequisitesForm.NUMBER_FIELD_RC, userDocument.getNumber());
		frm.setField(EditBasketRequisitesForm.SERIES_FIELD_RC, userDocument.getSeries());

		super.updateForm(frm, operation.getPerson());
	}

	// Сохранение водительских прав клиента
	protected ActionForward doSave(ActionForm form, HttpServletRequest request, ActionMapping mapping) throws Exception
	{
		EditBasketRequisitesForm frm = (EditBasketRequisitesForm) form;
		EditBasketRequisitesOperation operation = createOperation("EditBasketRequisitesOperation", "PersonManagement");

		try
		{
			operation.initialize(frm.getPerson());
		}
		catch (BusinessLogicException e)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, msgs);
		}

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), EditBasketRequisitesForm.EDIT_BASKET_REQUISITES_FORM);
		if (!processor.process())
		{
			saveErrors(request, processor.getErrors());
			updateForm(frm, operation);
			return mapping.findForward(FORWARD_START);
		}
		Map<String, Object> result = processor.getResult();
		UserDocument document = operation.getEntityDL();
		document.setNumber((String) result.get(EditBasketRequisitesForm.NUMBER_FIELD_DL));
		document.setSeries((String) result.get(EditBasketRequisitesForm.SERIES_FIELD_DL));
		document.setIssueBy((String) result.get(EditBasketRequisitesForm.ISSUE_BY_FIELD_DL));
		if (result.get(EditBasketRequisitesForm.ISSUE_DATE_FIELD_DL) != null)
			document.setIssueDate(DateHelper.toCalendar((Date) result.get(EditBasketRequisitesForm.ISSUE_DATE_FIELD_DL)));
		if (result.get(EditBasketRequisitesForm.EXPIRE_DATE_FIELD_Dl) != null)
			document.setExpireDate(DateHelper.toCalendar((Date)result.get(EditBasketRequisitesForm.EXPIRE_DATE_FIELD_Dl)));
		operation.setEntityDL(document);
		operation.saveDL();
		document = operation.getEntityRC();
		document.setNumber((String) result.get(EditBasketRequisitesForm.NUMBER_FIELD_RC));
		document.setSeries((String) result.get(EditBasketRequisitesForm.SERIES_FIELD_RC));
		operation.setEntityRC(document);
		operation.saveRC();

		updateForm(frm, operation);
		return mapping.findForward(FORWARD_START);
	}

	// Удаление водительскиих прав клиента
	public ActionForward removeDL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    EditBasketRequisitesForm frm = (EditBasketRequisitesForm) form;
        EditBasketRequisitesOperation operation = createOperation("EditBasketRequisitesOperation", "PersonManagement");

	    try
        {
            operation.initialize(frm.getPerson());
        }
        catch (BusinessLogicException e)
        {
            ActionMessages msgs = new ActionMessages();
            msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
            saveErrors(request, msgs);
        }
        operation.removeDL();

	    updateForm(frm, operation);
	    return mapping.findForward(FORWARD_START);
    }

	// Удаление Свидетельства о регистрации транспортного средства
	public ActionForward removeRC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditBasketRequisitesForm frm = (EditBasketRequisitesForm) form;
		EditBasketRequisitesOperation operation = createOperation("EditBasketRequisitesOperation", "PersonManagement");

		try
		{
			operation.initialize(frm.getPerson());
		}
		catch (BusinessLogicException e)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, msgs);
		}
		operation.removeRC();

		updateForm(frm, operation);
		return mapping.findForward(FORWARD_START);
	}
}
