package com.rssl.phizic.web.groups;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.groups.DublicateGroupNameException;
import com.rssl.phizic.business.groups.Group;
import static com.rssl.phizic.business.schemes.AccessCategory.CATEGORY_ADMIN;
import static com.rssl.phizic.business.schemes.AccessCategory.CATEGORY_CLIENT;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.groups.EditGroupOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;

import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 10.11.2006 Time: 15:04:30 To change this template use
 * File | Settings | File Templates.
 */
public class GroupsEditAction extends EditActionBase
{
	public static final String FORWARD_SAVE = "Save";
	protected Form getEditForm(EditFormBase frm)
	{
		return GroupsEditForm.GROUP_FORM;
	}

	protected EditEntityOperation createViewOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		GroupsEditForm frm = (GroupsEditForm) form;
		String category = frm.getCategory();
		if (StringHelper.isEmpty(category))
			throw new BusinessException("Не установлен тип группы");

		EditGroupOperation operation = null;
		if (category.equals(CATEGORY_CLIENT))
		{
			operation = createOperation("ViewGroupOperationC");
		}
		else if (category.equals(CATEGORY_ADMIN))
		{
			operation = createOperation("ViewGroupOperationA");
		}

		if (frm.getId() == null)
			operation.initializeNew(category);
		else
			operation.initialize(frm.getId());
		return operation;
	}

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		GroupsEditForm frm = (GroupsEditForm) form;
		String category = frm.getCategory();

		if (StringHelper.isEmpty(category))
			throw new BusinessException("Не установлен тип группы");

		EditGroupOperation operation = null;
		if (category.equals(CATEGORY_CLIENT))
		{
			operation = createOperation("EditGroupOperationC");
		}
		else if (category.equals(CATEGORY_ADMIN))
		{
			operation = createOperation("EditGroupOperationA");
		}

		if (frm.getId() == null)
			operation.initializeNew(category);
		else
			operation.initialize(frm.getId());
		return operation;
	}

	protected void updateForm(EditFormBase form, Object entity)
	{
		GroupsEditForm frm = (GroupsEditForm) form;
		Group group = (Group) entity;

		frm.setCategory(group.getCategory());
		frm.setField("name", group.getName());
		frm.setField("description", group.getDescription());
		frm.setField("priority", group.getPriority());
		frm.setField("useDefaultStyle", group.getSkin() != null);
		if (group.getSkin() != null) frm.setField("defaultStyle", group.getSkin().getId()); 
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation)
	{
		GroupsEditForm     frm = (GroupsEditForm) form;
		EditGroupOperation op  = (EditGroupOperation) operation;
		Group group = op.getEntity();

		if(op.isNew())
		{
			currentRequest().setAttribute("$$newId", group.getId());
			frm.setId( group.getId() );
		}

		frm.setGroup( group );
		frm.setSkins(op.getSkins());
	}

	public ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		try
		{
			return super.doSave(operation, mapping, frm);
		}
		catch(DublicateGroupNameException ex)
		{
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Группа с таким именем уже существует",false));
			saveErrors(currentRequest(), messages);
		}
		return mapping.findForward(FORWARD_START);
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws BusinessLogicException
	{
		Group group = (Group) entity;

		group.setName( (String) data.get("name") );
		group.setDescription( (String) data.get("description") );
		group.setPriority((Long) data.get("priority") );
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult)
	{
		EditGroupOperation op = (EditGroupOperation) editOperation;

		if (Boolean.valueOf ((Boolean)validationResult.get("useDefaultStyle")))
			op.setSkinId((Long) validationResult.get("defaultStyle"));
		else
			op.setSkinId(null);
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		EditGroupOperation op = (EditGroupOperation) operation;
		ActionMapping mapping = getCurrentMapping();
		Group group = op.getEntity();

		if(op.isNew())
		{
			return new ActionForward(
					mapping.findForward(FORWARD_SAVE).getPath() +
							"?id=" +
							group.getId() +
							"&category=" +
							group.getCategory(),
					true
			);
		}
		return mapping.findForward(FORWARD_START);
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		GroupsEditForm frm = (GroupsEditForm) form;
		return  mapping.getPath() + "/" + frm.getCategory();
	}
}
