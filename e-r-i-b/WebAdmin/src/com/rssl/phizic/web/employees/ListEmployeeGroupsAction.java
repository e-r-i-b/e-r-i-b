package com.rssl.phizic.web.employees;

import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.groups.EditEmployeeGroupsOperation;
import com.rssl.phizic.operations.groups.ListEmployeeGroupsOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 16.11.2006 Time: 12:25:39 To change this template use
 * File | Settings | File Templates.
 */
public class ListEmployeeGroupsAction  extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
        map.put("button.save", "save");

        return map;
    }

	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ListEmployeeGroupsForm      frm       = (ListEmployeeGroupsForm) form;
		ListEmployeeGroupsOperation operation = createOperation(ListEmployeeGroupsOperation.class);
		operation.initialize(frm.getEmployeeId());

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		updateFormAdditionalData(frm, operation);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
	    ListEmployeeGroupsForm      frm = (ListEmployeeGroupsForm)      form;
		ListEmployeeGroupsOperation op  = (ListEmployeeGroupsOperation) operation;
		frm.setEmployee(op.getEmployee());
		List<Group> selectedGroups = op.getPersonGroups();

		String[] selectedIds = new String[selectedGroups.size()];
		for(int i = 0; i < selectedGroups.size(); i++)
		{
			selectedIds[i] = selectedGroups.get(i).getId().toString();
		}

		frm.setSelectedIds( selectedIds );
		frm.setData( op.getAllGroups() );
	}

    public ActionForward save ( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                            HttpServletResponse response ) throws Exception
	{
   		ListEmployeeGroupsForm frm = (ListEmployeeGroupsForm)form;

		EditEmployeeGroupsOperation operation = createOperation( EditEmployeeGroupsOperation.class );
		operation.initialize( frm.getEmployeeId() );
		operation.modifyPersonGroups( frm.getSelectedIds() );

		addLogParameters(new SimpleLogParametersReader("ID сотрудника банка", frm.getEmployeeId()));

		return start(mapping, form, request, response);
	}
}

