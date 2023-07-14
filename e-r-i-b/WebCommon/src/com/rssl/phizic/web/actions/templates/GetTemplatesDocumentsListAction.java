package com.rssl.phizic.web.actions.templates;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.templates.RemoveTemplateDocumentOperation;
import com.rssl.phizic.operations.templates.TemplateDocumentsListOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author mihaylov
 * created 10.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class GetTemplatesDocumentsListAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(TemplateDocumentsListOperation.class, "ShowTemplatesDocuments");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveTemplateDocumentOperation.class);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			return super.doRemove(operation, id);
		}
		catch (Exception ex)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.templates.documents.noRemoveTemplate"));
		}
		return msgs;
	}
}

