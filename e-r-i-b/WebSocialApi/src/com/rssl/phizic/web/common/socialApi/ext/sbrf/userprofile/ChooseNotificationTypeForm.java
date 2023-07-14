package com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author EgorovaA
 * @ created 15.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Форма выбора способа оповещений
 */
public class ChooseNotificationTypeForm extends EditFormBase
{
	public static final Form EDIT_LOGIN_NOTIFICATION_SETTINGS_FORM = createLoginSettingsForm();
	public static final Form EDIT_NOTIFICATION_SETTINGS_FORM = createNotificationSettingsForm();

	private static final String REQUIRED_TYPE_FIELD_MSG = "Укажите способ оповещения.";
	private static final String ERROR_TYPE_FIELD_MSG = "Данный тип оповещения не поддерживается.";

	private String notification;
	private String type;

	public String getNotification()
	{
		return notification;
	}

	public void setNotification(String notification)
	{
		this.notification = notification;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	// форма для настроек оповещений о входе клиента
	private static Form createLoginSettingsForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb;

		fb = new FieldBuilder();
		fb.setName("type");
		fb.setDescription("Способ оповещения");
		fb.addValidators(
				new RequiredFieldValidator(REQUIRED_TYPE_FIELD_MSG),
				new ChooseValueValidator(ListUtil.fromArray(new String[] { "sms", "email", "push"} ), ERROR_TYPE_FIELD_MSG)
		);
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	// форма для настроек оповещений службы поддержки/выполнения операций
	private static Form createNotificationSettingsForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb;

		fb = new FieldBuilder();
		fb.setName("type");
		fb.setDescription("Способ оповещения");
		fb.addValidators(
				new RequiredFieldValidator(REQUIRED_TYPE_FIELD_MSG),
				new ChooseValueValidator(ListUtil.fromArray(new String[] { "sms", "email", "push", "none"} ), ERROR_TYPE_FIELD_MSG)
		);
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
