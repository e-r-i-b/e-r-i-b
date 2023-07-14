package com.rssl.phizic.web.configure.sms;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.sms.SmsSettingsListOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 *
 *
 * @author  Balovtsev
 * @version 22.03.13 9:55
 */
public class SmsSettingsListAction extends ListActionBase
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("SmsSettingsListOperation", "SMSManagement");
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation o, ListFormBase frm) throws Exception
	{
		SmsSettingsListOperation operation = (SmsSettingsListOperation) o;

		List<MessageResource> data = new ArrayList<MessageResource>();
		data.addAll(operation.getSmsResources(ChannelType.INTERNET_CLIENT));
		frm.setData(data);
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}
}
