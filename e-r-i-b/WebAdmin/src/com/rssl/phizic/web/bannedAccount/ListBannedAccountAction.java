package com.rssl.phizic.web.bannedAccount;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.bannedAccount.RemoveBannedAccountOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author: vagin
 * @ created: 01.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListBannedAccountAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("button.edit", "edit");
		return map;
	}
	
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ListBannedAccountOperation","BannedAccountManagment");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveBannedAccountOperation.class);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		addLogParameters(new BeanLogParemetersReader("Данные удаляемой сущности", operation.getEntity()));
		return super.doRemove(operation, id);
	}

}
