package com.rssl.phizic.web.ermb.migration.list.settings;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.migration.list.operations.EditMigrationSettingsOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nady
 * @ created 14.12.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditMigrationSettingsAction extends OperationalActionBase
{


	private static final String DEPARTMENT_DELIMITER = ",";

	protected Map<String, String> getKeyMethodMap ()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMigrationSettingsOperation operation = createOperation(EditMigrationSettingsOperation.class);

		EditMigrationSettingsForm frm = (EditMigrationSettingsForm)form;
		frm.setField("text",
				StringUtils.join(operation.getListDepartments(), DEPARTMENT_DELIMITER)
		);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		EditMigrationSettingsForm frm = (EditMigrationSettingsForm)form;
		String depts = frm.getField("text").toString();
		EditMigrationSettingsOperation operation = createOperation(EditMigrationSettingsOperation.class);
		try
		{
			operation.setListDepartments(depts);
			operation.save();
		}
		catch (BusinessLogicException e)
		{
			saveError(request,e);
		}
		return mapping.findForward(FORWARD_START);
	}
}
