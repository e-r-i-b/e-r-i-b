package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author bogdanov
 * @ created 11.06.14
 * @ $Author$
 * @ $Revision$
 */

public class ChangeTemplateSettingsForm extends EditPropertiesFormBase
{
	private static Form EDIT_FORM = createForm();
	private static final String PLANING_FOR_DEACTIVATE_MESSAGE_KEY = "com.rssl.iccs.payment.templates.message.planForDeacitivate";
	private static final String INACTIVE_MESSAGE_KEY = "not.active.provider.message";

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сообщение клиенту при просмотре и оплате шаблона в адрес поставщика, планируемого к отключению");
		fieldBuilder.setName(PLANING_FOR_DEACTIVATE_MESSAGE_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,300}", "Длина сообщения не должна превышать 300 символов"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сообщение клиенту при просмотре и оплате шаблона в адрес отключенного поставщика");
		fieldBuilder.setName(INACTIVE_MESSAGE_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,300}", "Длина сообщения не должна превышать 300 символов"));
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
