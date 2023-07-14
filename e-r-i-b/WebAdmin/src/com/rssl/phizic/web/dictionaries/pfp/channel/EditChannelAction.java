package com.rssl.phizic.web.dictionaries.pfp.channel;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.channel.Channel;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.channel.EditChannelOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;


import java.util.Map;

/**
 * @author komarov
 * @ created 12.08.13 
 * @ $Author$
 * @ $Revision$
 */

public class EditChannelAction extends EditActionBase
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditChannelOperation operation = createOperation(EditChannelOperation.class);
		Long id = form.getId();
		if (id == null)
			operation.initialize();
		else
			operation.initialize(id);
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return EditChannelForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Channel channel = (Channel) entity;
		channel.setName((String) data.get(EditChannelForm.NAME_FIELD_NAME));
	}

	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{
		Channel channel = (Channel) entity;
		form.setField(EditChannelForm.NAME_FIELD_NAME, channel.getName());
	}
}
