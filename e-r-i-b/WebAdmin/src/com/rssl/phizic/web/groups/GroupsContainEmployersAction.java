package com.rssl.phizic.web.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.groups.ListGroupEmployersOperation;
import com.rssl.phizic.operations.groups.ModifyEmployeeGroupsOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.common.forms.Form;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 15.11.2006 Time: 15:35:49 To change this template use
 * File | Settings | File Templates.
 */
public class GroupsContainEmployersAction extends ListActionBase
{
	private static final String DELIMETR   = ";";

	protected Map getAditionalKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.add" , "add");
        return map;
    }

	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		GroupsContainEmployersForm frm = (GroupsContainEmployersForm)form;
		if( frm.getId() == null)
			throw new BusinessException("Не установлен идентификатор группы");

        ListGroupEmployersOperation operation = createOperation(ListGroupEmployersOperation.class);
		operation.setGroupId(frm.getId());

		return operation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		GroupsContainEmployersForm frm = (GroupsContainEmployersForm) form;

		ModifyEmployeeGroupsOperation operation = createOperation(ModifyEmployeeGroupsOperation.class);
		operation.initialize(frm.getId());

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return GroupsContainEmployersForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		String state = (String) filterParams.get("state");

        if (state == null || state.equals(""))
            query.setParameter("blocked", null);
        else if (state.equals("0"))
            query.setParameter("blocked", 0);
        else if (state.equals("1"))
            query.setParameter("blocked", 1);

		query.setParameter("surName",    filterParams.get("surName") );
		query.setParameter("firstName",  filterParams.get("firstName") );
		query.setParameter("patrName",   filterParams.get("patrName") );
		query.setParameter("info",       filterParams.get("info") );
        query.setParameter("branchCode", filterParams.get("branchCode") );
        query.setParameter("departmentCode", filterParams.get("departmentCode") );
        query.setParameter("blockedUntil",   Calendar.getInstance().getTime() );

	    query.setMaxResults(webPageConfig().getListLimit() + 1);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        GroupsContainEmployersForm frm = (GroupsContainEmployersForm) form;

		ModifyEmployeeGroupsOperation operation = createOperation(ModifyEmployeeGroupsOperation.class);
	    operation.initialize( frm.getId() );

	    for (Long id: parseIds(frm.getEmployeeLogins()))
	    {
			operation.addEmloyee(id);

		    addLogParameters(new SimpleLogParametersReader("Добавляемая сущность", "id сотрудника", id.toString()));
	    }

	    return start(mapping, form, request, response);
    }

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ModifyEmployeeGroupsOperation op = (ModifyEmployeeGroupsOperation) operation;
		op.setDeletedEmployee(id);
		op.remove();

		return null;
	}

	private List<Long> parseIds(String logins)throws BusinessException
	{
		int pos=0,start=0;

		List<Long> list = new ArrayList<Long>();
		if(logins != null)
	    {

		    while( (pos = logins.indexOf(DELIMETR, start)) != -1 )
		    {
				String idStr = logins.substring(start, pos);
			    Long id = Long.parseLong(idStr);
			    list.add(id);
			    start=pos+1;
		    }
	    }
		if(list.size()==0)
			throw new BusinessException("Не выбраны сотрудники");
		else
			return list;
	}

}
