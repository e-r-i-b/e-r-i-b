package com.rssl.phizic.web.common.mobile.basket.reminders;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.web.actions.payments.forms.EditTemplateForm;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.client.reminder.AsyncProcessReminderAction;
import com.rssl.phizic.web.common.client.reminder.AsyncProcessReminderForm;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author Pankin
 * @ created 21.05.15
 * @ $Author$
 * @ $Revision$
 */
public class MobileProcessReminderAction extends AsyncProcessReminderAction
{

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> res = super.getKeyMethodMap();
		res.put("changeReminderInfo", "changeReminderInfo");
		res.put("delay", "delay");
		res.put("delete", "delete");
		return res;
	}

	protected boolean isAjax()
	{
		return false;
	}

	protected Map<String, Object> checkDelayFormData(EditFormBase frm) throws Exception
	{
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new RequestValuesSource(currentRequest()), AsyncProcessReminderForm.DELAY_FORM);
		if (!processor.process())
			throw new FormProcessorException(processor.getErrors());

		return processor.getResult();
	}

	protected Map<String, Object> checkReminderFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new RequestValuesSource(currentRequest()), EditTemplateForm.REMINDER_FORM);
		if (!processor.process())
			throw new FormProcessorException(processor.getErrors());

		return processor.getResult();
	}
}
