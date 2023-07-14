package com.rssl.phizic.web.configure.sms;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

/**
 *
 * Action для работы со списком смс ресурсов из ЦСА
 *
 * @author  Balovtsev
 * @version 05.04.13 12:17
 */
public class CSASmsSettingsListAction extends ListActionBase
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("CSASmsSettingsListOperation", "SMSManagement");
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}
}
