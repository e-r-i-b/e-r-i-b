package com.rssl.phizic.web.dictionaries.routing.adapter.settings;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.routing.adapter.settings.EditAdapterSettingsOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;
import com.rssl.phizicgate.manager.routing.NodeType;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Balovtsev
 * Date: 13.07.2012
 * Time: 8:30:42
 */
public class EditAdapterSettingsAction extends EditPropertiesActionBase<EditAdapterSettingsOperation>
{
	@Override
	protected EditAdapterSettingsOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditAdapterSettingsOperation.class, "ExternalSystemSettingsManagement");
	}

	@Override
	protected void initializeViewOperation(EditAdapterSettingsOperation operation, EditPropertiesFormBase form) throws BusinessException, BusinessLogicException
	{
		EditAdapterSettingsForm frm = (EditAdapterSettingsForm) form;
		operation.initialize(frm.getId(), frm.getNodeTypeObject(), form.getFieldKeys());
	}

	@Override
	protected void initializeReplicationOperation(EditAdapterSettingsOperation operation, EditPropertiesFormBase form, Set<String> propertyKeys) throws BusinessException, BusinessLogicException
	{
		EditAdapterSettingsForm frm = (EditAdapterSettingsForm) form;
		operation.initializeReplicate(form.getId(), frm.getNodeTypeObject(), propertyKeys);
	}

	@Override
	protected EditAdapterSettingsOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditAdapterSettingsOperation operation = getEditOperation();
		operation.initialize(frm.getId());
		return operation;
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		NodeType nodeType = ((EditAdapterSettingsForm) frm).getNodeTypeObject();
		ActionMessages msgs = checkDuplicateValues(frm, EditAdapterSettingsOperation.EXCLUDED_MESSAGES_CODE_KEY );
		if (!msgs.isEmpty())
			return msgs;
		if (NodeType.COD == nodeType || NodeType.SOFIA == nodeType)
		{
			msgs = checkDuplicateValues(frm,EditAdapterSettingsOperation.WRONG_OFFICE_BRANCHES_CODE_KEY );
			if (msgs.isEmpty())
				msgs = checkDuplicateValues(frm,EditAdapterSettingsOperation.OUR_TB_CODES_CODE_KEY );
		}
		return msgs;
	}
   // проверяем на одинаковые значения
    private ActionMessages  checkDuplicateValues(EditFormBase frm, String key)
    {
		List<String>  results = new ArrayList<String>();
		for (String name : frm.getFields().keySet())
		{
			if (!name.startsWith(key))
				continue;
			String value = frm.getField(name).toString();
			if (!results.contains(value))
				results.add(value);
			else
			{
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Данная запись уже добавлена в список.", false));
				return msgs;
			}
		}
	    return new ActionMessages();
    }
	
	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		ActionRedirect redirect = new ActionRedirect(getCurrentMapping().findForward(FORWARD_SUCCESS));
		redirect.addParameter("id", frm.getId());
		redirect.addParameter("nodeType", ((EditAdapterSettingsForm) frm).getNodeType());
		return redirect;
	}
}
