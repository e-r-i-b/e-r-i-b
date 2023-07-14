package com.rssl.phizic.web.persons;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.groups.EditPersonGroupsOperation;
import com.rssl.phizic.operations.groups.ListPersonGroupsOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.log.ArrayLogParametersReader;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Omeliyanchuk
 * Date: 15.11.2006
 * Time: 11:18:27
 */
public class ListPersonGroupsAction  extends ListActionBase
{
    protected Map<String, String> getAditionalKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();

        map.put("button.save", "save");
        return map;
    }

	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ListPersonGroupsForm      frm       = (ListPersonGroupsForm) form;
		ListPersonGroupsOperation operation = createOperation(ListPersonGroupsOperation.class);
		operation.initialize( frm.getPerson() );

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListPersonGroupsForm form = (ListPersonGroupsForm) frm;
		ListPersonGroupsOperation op = (ListPersonGroupsOperation) operation;

		if (!StringHelper.isEmpty(form.getMode()) &&  form.getMode().equals("personGroups"))
		{
			frm.setData(op.getPersonGroups());
		}
		else
		{
			frm.setData(op.getAllGroups());
		}
		updateFormAdditionalData(frm, operation);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
   		ListPersonGroupsForm frm = (ListPersonGroupsForm)form;
		ListPersonGroupsOperation op = (ListPersonGroupsOperation) operation;

		List<Group> selectedGroups = op.getPersonGroups();

		String[] selectedIds = new String[selectedGroups.size()];
		for(int i = 0; i < selectedGroups.size(); i++)
		{
			selectedIds[i] = selectedGroups.get(i).getId().toString();
		}

		frm.setSelectedIds( selectedIds );
		frm.setActivePerson( op.getActivePerson() );
	}

    public ActionForward save ( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                            HttpServletResponse response ) throws Exception
	{
   		ListPersonGroupsForm frm = (ListPersonGroupsForm)form;

		EditPersonGroupsOperation operation = createOperation( EditPersonGroupsOperation.class );
		operation.initialize( frm.getPerson() );
		operation.modifyPersonGroups( frm.getSelectedIds() );

		addLogParameters(new ArrayLogParametersReader("ID выбранных групп", frm.getSelectedIds()));

		return start(mapping, form, request, response);
	}
}
