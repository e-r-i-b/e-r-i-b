package com.rssl.phizic.web.ermb.migration.list.report;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.ermb.migration.list.operations.ClientManagerMigrationOperation;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.web.actions.UnloadOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gulov
 * @ created 06.12.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ёкшн отображени€ формы выгрузки отчета дл€ клиентских менеджеров
 */
public class ClientManagerAction extends UnloadOperationalActionBase<File>
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ClientManagerMigrationOperation operation = createOperation(ClientManagerMigrationOperation.class, "ErmbMigrationService");
		List<Department> departments = operation.getPilotZoneDepartments();
		ClientManagerForm frm = (ClientManagerForm) form;
		frm.setDepartments(departments);
		return super.start(mapping, form, request, response);
	}

	public ActionForward actionAfterUnload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return start(mapping, form, request, response);
	}

	@Override
	public Pair<String, File> createData(ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ClientManagerMigrationOperation operation = createOperation(ClientManagerMigrationOperation.class, "ErmbMigrationService");
		ClientManagerForm frm = (ClientManagerForm) form;
		operation.setSegments(frm.getSelectedSegments());
		operation.setSelectedDepartments(frm.getSelectedDepartments());
		String fileName = operation.createFileName();
		File file = operation.makeReport(fileName);
		return new Pair<String, File>(fileName, file);
	}
}
