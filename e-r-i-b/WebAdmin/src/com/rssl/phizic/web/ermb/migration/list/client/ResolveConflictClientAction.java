package com.rssl.phizic.web.ermb.migration.list.client;

import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.ermb.migration.list.operations.EditMigrationConflictsOperation;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.util.EmployeeInfoUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн для страницы разрешения конфликтов при миграции
 * @author Puzikov
 * @ created 10.12.13
 * @ $Author$
 * @ $Revision$
 */

public class ResolveConflictClientAction extends OperationalActionBase
{
	protected static final String FORWARD_SUCCESS = "Success";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.save", "save");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResolveConflictClientForm frm = (ResolveConflictClientForm) form;
		EditMigrationConflictsOperation operation = createOperation(EditMigrationConflictsOperation.class);

		String id = frm.getId();
		operation.initialize(id);
		frm.setConflicts(operation.getConflicts());

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		ResolveConflictClientForm frm = (ResolveConflictClientForm) form;
		EditMigrationConflictsOperation operation = createOperation(EditMigrationConflictsOperation.class);

		Employee employeeInfo = EmployeeInfoUtil.getEmployeeInfo();
		String employeeFio = employeeInfo.getFirstName() + ' ' + employeeInfo.getPatrName() + ' ' + employeeInfo.getSurName();

		operation.initialize(frm.getId());
		operation.save(frm.getResult(), employeeFio);

		return createSuccessForward(mapping, frm);
	}

	private ActionForward createSuccessForward(ActionMapping mapping, ResolveConflictClientForm frm)
	{
		UrlBuilder urlBuilder = new UrlBuilder();

		ActionForward forward = mapping.findForward(FORWARD_SUCCESS);
		urlBuilder.setUrl(forward.getPath());
		if (frm.isVip())
			urlBuilder.addParameter("vip", "true");

		return new ActionForward(urlBuilder.getUrl(), true);
	}
}
