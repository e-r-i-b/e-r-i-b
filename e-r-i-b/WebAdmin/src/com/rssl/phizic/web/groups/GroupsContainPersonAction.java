package com.rssl.phizic.web.groups;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.groups.ListGroupPersonsOperation;
import com.rssl.phizic.operations.groups.ModifyPersonGroupsOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.persons.ListPersonsAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 13.11.2006 Time: 15:18:59 To change this template use
 * File | Settings | File Templates.
 */
@SuppressWarnings({"JavaDoc"})
public class GroupsContainPersonAction extends ListPersonsAction
{
	private static final String DELIMETR   = ";";

	protected Map<String, String> getAditionalKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.add" , "add");
        return map;
    }

	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		GroupsContainPersonForm frm = (GroupsContainPersonForm)form;
		ListGroupPersonsOperation operation = createOperation(ListGroupPersonsOperation.class);
		operation.initialize(frm.getId(), frm.getMailId());
		return operation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		GroupsContainPersonForm frm = (GroupsContainPersonForm) form;
	    ModifyPersonGroupsOperation operation = createOperation(ModifyPersonGroupsOperation.class);
	    operation.initialize( frm.getId() );

		return operation;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		if (frm.isFromStart())
		{
			frm.setData( Collections.emptyList() );
			frm.setFilters( filterParams );
			updateFormAdditionalData(frm, operation);	
		}
		else
		{
			super.doFilter(filterParams, operation, frm);
		}
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return GroupsContainPersonForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		super.fillQuery(query, filterParams);
		query.setParameter("fio", filterParams.get("fio"));
		query.setParameter("mobile_phone", filterParams.get("mobile_phone"));
		query.setParameter("loginId", filterParams.get("loginId"));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        GroupsContainPersonForm frm = (GroupsContainPersonForm) form;

		ModifyPersonGroupsOperation operation = createOperation(ModifyPersonGroupsOperation.class);
	    operation.initialize( frm.getId() );

	    String logins = frm.getPersonLogins();
        for(Long id : parseIds(logins))
        {
	        operation.addPerson(id);

	        addLogParameters(new SimpleLogParametersReader("Добавляемая сущность", "id клиента", id.toString()));
        }

	    return start(mapping, form, request, response);
    }

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ModifyPersonGroupsOperation op = (ModifyPersonGroupsOperation) operation;
		op.setDeletedPerson(id);
		op.remove();

		return null;
	}

	private List<Long> parseIds(String logins)throws BusinessException
	{
		if (logins == null)
			throw new BusinessException("Не выбраны пользователи");

		int pos=0;
		int start=0;

		List<Long> list = new ArrayList<Long>();
		while((pos = logins.indexOf(DELIMETR, start)) != -1 )
		{
			String idStr = logins.substring(start, pos);
			Long id = Long.parseLong(idStr);
			list.add(id);
			start=pos+1;
		}

		return list;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		ListGroupPersonsOperation op = (ListGroupPersonsOperation) operation;
		GroupsContainPersonForm frm = (GroupsContainPersonForm) form;

		frm.setAllowedTB(op.getAllowedTB());
        frm.setGroupName(op.getGroupName());
	}
}
