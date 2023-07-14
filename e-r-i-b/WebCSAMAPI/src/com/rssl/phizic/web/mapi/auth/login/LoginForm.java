package com.rssl.phizic.web.mapi.auth.login;

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
 * ����� ����� ������� � ����
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

		//������ mAPI
		fb = new FieldBuilder();
		fb.setName(Constants.VERSION_FIELD);
		fb.setDescription("������ ���������� API");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//��� ����� ���������� ����������
		fb = new FieldBuilder();
		fb.setName(Constants.IS_LIGHT_SCHEME_FIELD);
		fb.setDescription("��� ����� ���������� ����������");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//���������� ������������� ����������
		fb = new FieldBuilder();
		fb.setName(Constants.DEVICE_ID_FIELD);
		fb.setDescription("���������� ������������� ����������");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//PIN-���
		fb = new FieldBuilder();
		fb.setName(Constants.PASSWORD_FIELD);
		fb.setDescription("PIN-���");
		formBuilder.addField(fb.build());

		//������ mobileSDK
		fb = new FieldBuilder();
		fb.setName(Constants.MOBILE_SDK_DATA_FIELD);
		fb.setDescription("������ ���������� SDK");
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
