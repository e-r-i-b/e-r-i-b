package com.rssl.phizic.web.common.dictionaries.groupsRisk;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.limits.GroupRiskRank;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.groupsRisk.EditGroupRiskOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Map;

/**
 * @author basharin
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 * экшен редактирования группы риска
 */

public class EditGroupRiskAction extends EditActionBase
{
	private static final String SAVE_ERROR_MESSAGE = "Указанная Вами группа риска уже существует в системе. Измените название группы риска.";

	protected EditEntityOperation createEditOperation(EditFormBase frm)	throws BusinessException, BusinessLogicException
	{
		EditGroupRiskOperation operation = createOperation(EditGroupRiskOperation.class, "GroupRiskManagment");
		initOperation(operation, frm.getId());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditGroupRiskForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		GroupRisk groupRisk = (GroupRisk) entity;

		groupRisk.setName((String) data.get("name"));
		groupRisk.setIsDefault((Boolean) data.get("isDefault"));
		groupRisk.setRank(GroupRiskRank.valueOf((String)data.get("rank")));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		GroupRisk groupRisk = (GroupRisk) entity;
		frm.setField("name", groupRisk.getName());
		frm.setField("isDefault", groupRisk.getIsDefault());
		frm.setField("rank", groupRisk.getRank());
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath(), true);
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		try
		{
			return super.doSave(operation, mapping, frm);
		}
		catch(ConstraintViolationException e)
		{
			throw new BusinessLogicException(SAVE_ERROR_MESSAGE, e);
		}
	}

	protected void initOperation(EditGroupRiskOperation operation, Long id) throws BusinessException
	{
		if  ((id != null) && (id != 0))
			operation.initialize(id);
		else
			operation.initializeNew();
	}
}
