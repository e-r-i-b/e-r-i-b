package com.rssl.phizic.web.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.operations.departments.EditDepartmentOperation;
import com.rssl.phizic.operations.departments.ListDepartmentsOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pankin
 * @ created 18.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditSendSMSPreferredMethodAction extends OperationalActionBase
{
	protected static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListDepartmentsOperation operation = createOperation(ListDepartmentsOperation.class);
		EditSendSMSPreferredMethodForm frm = (EditSendSMSPreferredMethodForm) form;
		updateForm(operation.getAllowedTBList(), frm);

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditDepartmentOperation operation = createOperation("EditDepartmentOperation");
		EditSendSMSPreferredMethodForm frm = (EditSendSMSPreferredMethodForm) form;

		operation.initializeSendSMSMethods(frm.getDepartmentMethods());
		operation.saveSendSMSMethods();
		updateForm(operation.getDepartments(), frm);

		return mapping.findForward(FORWARD_START);
	}

	private void updateForm(List<Department> departments, EditSendSMSPreferredMethodForm frm) throws BusinessException
	{
		Map<String, String> departmentMethods = new HashMap<String, String>();
		for (Department department : departments)
		{
			departmentMethods.put(department.getId().toString(),
					((ExtendedDepartment) department).getSendSMSPreferredMethod().toString());
		}
		frm.setDepartments(departments);
		frm.setDepartmentMethods(departmentMethods);
	}
}
