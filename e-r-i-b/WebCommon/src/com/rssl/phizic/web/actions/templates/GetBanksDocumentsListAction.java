package com.rssl.phizic.web.actions.templates;

import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.templates.BanksDocumentsListOperation;
import com.rssl.phizic.operations.templates.RemoveBanksDocumentOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

import org.apache.struts.action.*;

/**
 * User: Novikov_A
 * Date: 01.02.2007
 * Time: 13:21:02
 */
public class GetBanksDocumentsListAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(BanksDocumentsListOperation.class, "ShowBankDocuments");
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveBanksDocumentOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id)
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			super.doRemove(operation, id);
		}
		catch(Exception ex)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.templates.documents.noRemoveTemplate", ex.getMessage()));
		}
		return msgs;
	}

}
