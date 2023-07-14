package com.rssl.phizic.web.atm.ext.sbrf.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * ����� ����������� �� ���
 * @author Jatsky
 * @ created 05.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class CheckDULForm extends ActionFormBase
{
	private static final String CODE_FIELD_DESCRIPTION = "��������� 4 ����� ���������";

	public static final Form ATM_CHECK_DUL_FORM = createForm();

	private String code;

	private String errMessage;

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb = new FieldBuilder();
		fb.setName("code");
		fb.setDescription(CODE_FIELD_DESCRIPTION);
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("\\d{4}", "���� "+CODE_FIELD_DESCRIPTION+" ������ ��������� 4 �����"));
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getErrMessage()
	{
		return errMessage;
	}

	public void setErrMessage(String errMessage)
	{
		this.errMessage = errMessage;
	}
}
