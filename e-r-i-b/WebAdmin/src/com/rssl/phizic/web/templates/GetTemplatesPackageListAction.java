package com.rssl.phizic.web.templates;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.template.PackageTemplate;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.templates.RemoveTemplatePackageOperation;
import com.rssl.phizic.operations.templates.TemplatePackageListOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

/**
 * User: Novikov_A
 * Date: 01.02.2007
 * Time: 13:21:02
 */
public class GetTemplatesPackageListAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(TemplatePackageListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveTemplatePackageOperation.class);
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
			PackageTemplate entity = (PackageTemplate) operation.getEntity();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.templates.package.noRemoveTemplate", entity.getName()));
		}
		return msgs;
	}
}
