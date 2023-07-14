package com.rssl.phizic.web.socialApi.auth.login;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.web.auth.ActionFormBase;

/**
 * @author osminin
 * @ created 05.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Форма входа клиента в SocialAPI
 */
public class LoginForm extends ActionFormBase
{
	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		//mGUID
		fb = new FieldBuilder();
		fb.setName(Constants.MGUID_FIELD);
		fb.setDescription("mGUID");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//уникальный идентификатор устройства
		fb = new FieldBuilder();
		fb.setName("extClientID");   /** написать сюда extClientID **/
		fb.setDescription("уникальный идентификатор устройства");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//тип приложения
		fb = new FieldBuilder();
		fb.setName(Constants.APP_TYPE_FIELD);
		fb.setDescription("тип приложения");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//PIN-код
		fb = new FieldBuilder();
		fb.setName(Constants.PASSWORD_FIELD);
		fb.setDescription("PIN-код");
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
