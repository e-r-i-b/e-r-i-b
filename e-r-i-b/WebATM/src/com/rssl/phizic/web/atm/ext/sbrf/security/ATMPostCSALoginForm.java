package com.rssl.phizic.web.atm.ext.sbrf.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.web.actions.StrutsUtils;

/**
 * @author osminin
 * @ created 27.08.13
 * @ $Author$
 * @ $Revision$
 *
 * ����� ���������� �������������� ������������ �TM
 */
public class ATMPostCSALoginForm extends ActionFormBase
{
	private static final String ERROR_MESSAGE = StrutsUtils.getMessage("error.login.failed", "securityBundle");
	public static final Form ATM_LOGIN_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb = new FieldBuilder();
		fb.setName("token");
		fb.setDescription("����� ��������������");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator("����� �������������� �� �����"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("codeATM");
		fb.setDescription("����� ���������� ����������������");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(ERROR_MESSAGE));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("isChipCard");
		fb.setDescription("������� ����� � �����");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("atmRegionCode");
		fb.setDescription("������ 5 ���� ���� ����� �������");
		fb.setType(IntegerType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
