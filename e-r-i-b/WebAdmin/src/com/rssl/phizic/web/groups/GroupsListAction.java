package com.rssl.phizic.web.groups;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.groups.EditGroupOperation;
import com.rssl.phizic.operations.groups.ListGroupsOperation;
import com.rssl.phizic.operations.groups.ListGroupsOperationA;
import com.rssl.phizic.operations.groups.ListGroupsOperationC;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

import java.util.Map;

/**
 * User: Omeliyanchuk
 * Date: 10.11.2006
 * Time: 12:40:15
 */
public class GroupsListAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		GroupsListForm frm = (GroupsListForm) form;
		String category = checkCategory(frm);

		ListGroupsOperation operation=null;
		if (AccessCategory.CATEGORY_CLIENT.equals(category))
	    {
			operation = createOperation(ListGroupsOperationC.class);
	    }
		else if (AccessCategory.CATEGORY_ADMIN.equals(category))
	    {
			operation = createOperation(ListGroupsOperationA.class);
	    }

		return operation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		GroupsListForm frm = (GroupsListForm) form;
		String category = checkCategory(frm);

		EditGroupOperation operation = null;
		if (AccessCategory.CATEGORY_CLIENT.equals(category))
		{
			operation = createOperation("EditGroupOperationC");
		}
		else if (AccessCategory.CATEGORY_ADMIN.equals(category))
		{
			operation = createOperation("EditGroupOperationA");
		}
		return operation;
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		try
		{
			super.doRemove(operation, id);
		}
		catch (BusinessLogicException ex)
		{
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage(ex.getMessage(),false));
			return messages;
		}
		return null;
	}

	protected Form getFilterForm(ListFormBase form, ListEntitiesOperation operation)
	{
		return GroupsListForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("name", filterParams.get("name"));
		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}

	protected ActionForward createActionForward(ListFormBase form) throws BusinessException
	{
		GroupsListForm frm = (GroupsListForm) form;

		return getCurrentMapping().findForward(FORWARD_START + checkCategory(frm));
	}

	private String checkCategory(GroupsListForm frm) throws BusinessException
	{
		String category = frm.getCategory();
		if (StringHelper.isEmpty(category))
			throw new BusinessException("Не установлен тип группы");

		return category;
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		GroupsListForm frm = (GroupsListForm) form;
		return  mapping.getPath() + "/" + frm.getCategory();
	}
}
