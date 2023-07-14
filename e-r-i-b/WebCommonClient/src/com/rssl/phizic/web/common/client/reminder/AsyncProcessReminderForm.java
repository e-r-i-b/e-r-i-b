package com.rssl.phizic.web.common.client.reminder;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.payments.forms.ConfirmCreateTemplateForm;

/**
 * Форма для асинхронной работы с напоминаниями
 * @author niculichev
 * @ created 29.10.14
 * @ $Author$
 * @ $Revision$
 */
public class AsyncProcessReminderForm extends ConfirmCreateTemplateForm
{
	public static final String DELAY_DATE_FIELD_NAME = "chooseDelayDateReminder";

	public static Form DELAY_FORM = createDelayForm();
	private Long reminderId;
	private String state;

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public Long getReminderId()
	{
		return reminderId;
	}

	public void setReminderId(Long reminderId)
	{
		this.reminderId = reminderId;
	}

	private static Form createDelayForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder builder = new FieldBuilder();
		builder.setName(DELAY_DATE_FIELD_NAME);
		builder.setType(DateType.INSTANCE.getName());
		builder.setDescription("Дата откладывания");
		builder.addValidators(new RequiredFieldValidator());
		fb.addField(builder.build());

		return fb.build();
	}
}
