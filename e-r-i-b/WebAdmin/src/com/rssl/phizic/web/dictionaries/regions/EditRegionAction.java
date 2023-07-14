package com.rssl.phizic.web.dictionaries.regions;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.exceptions.DublicateRegionException;
import com.rssl.phizic.business.dictionaries.asyncSearch.AsynchSearchObjectState;
import com.rssl.phizic.business.dictionaries.asyncSearch.AsynchSearchDictionariesService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.regions.EditRegionOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author khudyakov
 * @ created 03.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class EditRegionAction extends EditActionBase
{
	private static final AsynchSearchDictionariesService asynchSearchDictionariesService = new AsynchSearchDictionariesService();

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditRegionForm frm = (EditRegionForm) form;
		EditRegionOperation operation = createOperation(EditRegionOperation.class, "RegionDictionaryManagement");

		Long id = frm.getId();
		Long parentId = frm.getParentId();

		if (id != null && id != 0)
			operation.initialize(id);
		else
		    operation.initializeNew(parentId);

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditRegionForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Region region = (Region) entity;
		region.setSynchKey((String) data.get("synchKey"));
		region.setName((String) data.get("name"));
		if (region.getParent() == null)
			region.setCodeTB((String) data.get("codeTB"));
		else
			region.setCodeTB(region.getParent().getCodeTB());
		region.setProviderCodeMAPI((String) data.get("providerCodeMAPI"));
		region.setProviderCodeATM((String) data.get("providerCodeATM"));
	}

	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{
		Region region = (Region) entity;
		EditRegionForm frm = (EditRegionForm) form;

		frm.setField("name",     region.getName());
		frm.setField("synchKey", region.getSynchKey());
		frm.setField("codeTB",   region.getCodeTB());
		frm.setField("providerCodeMAPI",   region.getProviderCodeMAPI());
		frm.setField("providerCodeATM",   region.getProviderCodeATM());

		Region parent = region.getParent();
		if (parent != null)
		{
			frm.setField("parent", parent.getName());
			frm.setParentId(parent.getId());
		}
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		try
		{
			Region region = (Region) operation.getEntity();
			AsynchSearchObjectState state = region.getId() != null ? AsynchSearchObjectState.UPDATED : AsynchSearchObjectState.INSERTED;
			ActionForward actionForward = super.doSave(operation, mapping, frm);
			asynchSearchDictionariesService.addObjectInfoForAsynchSearch(region, state);
			return new ActionForward(actionForward.getPath() +"?id=" + region.getId(), true);

		}
		catch (DublicateRegionException e)
		{
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.dictionaries.regions.addException"));
			saveErrors(currentRequest(), messages);

			return mapping.findForward(FORWARD_START);
		}
	}
}
