package com.rssl.phizic.web.configure.sms;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.sms.ERMBSmsSettingsListOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Ёкшн просмотра текстов смс-шаблонов ≈–ћЅ
 * @author Rtischeva
 * @created 31.07.13
 * @ $Author$
 * @ $Revision$
 */
public class ERMBSmsSettingsListAction extends ListActionBase
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ERMBSmsSettingsListOperation", "SMSManagement");
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ERMBSmsSettingsListOperation oper = (ERMBSmsSettingsListOperation) operation;

		List<MessageResource> data = new ArrayList<MessageResource>();
		data.addAll(oper.getERMBSmsResources());
		frm.setData(data);
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}
}
