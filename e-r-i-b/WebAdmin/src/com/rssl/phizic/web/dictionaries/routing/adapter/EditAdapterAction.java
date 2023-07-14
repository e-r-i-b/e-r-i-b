package com.rssl.phizic.web.dictionaries.routing.adapter;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.routing.adapter.EditAdapterOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterType;
import com.rssl.phizicgate.manager.routing.Node;
import org.apache.struts.action.ActionForward;

import java.util.Map;

/**
 * @author akrenev
 * @ created 08.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditAdapterAction  extends EditActionBase
{
	private static final String FORWARD_LIST  = "List";

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditAdapterOperation operation = createOperation(EditAdapterOperation.class);
		Long id = frm.getId();
		if ((id != null) && (id != 0))
		{
			operation.initialize(id);
		}
		else
		{
			operation.initializeNew();
		}
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditAdapterForm.EDIT_FORM;
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditAdapterForm form = (EditAdapterForm) frm;
		EditAdapterOperation op = (EditAdapterOperation) operation;
		form.setAllNodesList(op.getAllNodes());
		Node node = op.getNode();
		frm.setField("addressWebService", op.getAddressWebService());
		frm.setField("nodeType", node == null? null: node.getType());
		form.setField("nodeId",node == null? null: node.getId());
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditAdapterForm form = (EditAdapterForm) editForm;
		EditAdapterOperation op = (EditAdapterOperation) editOperation;
		op.setAddressWebService((String) form.getField("addressWebService"));
		if((AdapterType.valueOf((String)validationResult.get("adapterType")) == AdapterType.NONE))
			op.setNode(Long.valueOf((String) form.getField("nodeId")));
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Adapter adapter = (Adapter) entity;
		adapter.setName((String) data.get("name"));
		adapter.setUUID((String) data.get("UUID"));
		adapter.setListenerUrl((String) data.get("listenerUrl"));
		adapter.setType(AdapterType.valueOf((String) data.get("adapterType")));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		Adapter adapter = (Adapter) entity;
		frm.setField("name", adapter.getName());
		frm.setField("UUID", adapter.getUUID());
		frm.setField("adapterType", adapter.getType());
		frm.setField("listenerUrl", adapter.getListenerUrl());
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_LIST);
	}
}
