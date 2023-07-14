package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * ‘орма дл€ настроек ѕереводов и платежей
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class PaymentsConfigureForm extends EditPropertiesFormBase
{
	private static Form EDIT_FORM = createForm();
	private static final String FIELD_NAME_PROPERTY_PREFIX = "settings.payments.field.";

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		//¬ключить отправку сообщени€ получателю при переводе на яндекс.ƒеньги
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "sendMessageToReceiver.yandex", "configureBundle"));
		fieldBuilder.setName(PaymentsConfig.SEND_MESSAGE_TO_RECEIVER_YANDEX);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
