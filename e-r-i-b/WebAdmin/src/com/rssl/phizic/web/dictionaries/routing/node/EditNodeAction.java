package com.rssl.phizic.web.dictionaries.routing.node;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.routing.node.EditNodeOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizicgate.manager.routing.Node;
import com.rssl.phizicgate.manager.routing.NodeType;
import org.apache.struts.action.ActionForward;

import java.util.Arrays;
import java.util.Map;

/**
 * @author akrenev
 * @ created 08.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditNodeAction extends EditActionBase	
{
	private static final String FORWARD_LIST  = "List";

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditNodeOperation operation = createOperation(EditNodeOperation.class);
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
		return EditNodeForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Node node = (Node) entity;
		node.setName((String) data.get("name"));
		node.setURL((String) data.get("URL"));
		node.setType(NodeType.valueOf((String)data.get("type")));
		if (node.getType() == NodeType.SOFIA)
			node.setPrefix((String) data.get("prefix"));
		else
			node.setPrefix(null);
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		Node node = (Node) entity;
		frm.setField("name", node.getName());
		frm.setField("URL", node.getURL());
		frm.setField("type", node.getType());
		frm.setField("prefix", node.getPrefix());
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_LIST);
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		((EditNodeForm)frm).setNodeTypes(Arrays.asList(NodeType.values()));

	}

}
