package com.rssl.phizic.web.errors;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.errors.ErrorMessage;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * @author gladishev
 * @ created 19.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditErrorMessageForm extends EditFormBase
{
	protected ErrorMessage errorMessage;

	public ErrorMessage getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public static final Form FORM  = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// ���������� ���������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� ���������");
		fieldBuilder.setName("regExp");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator(".{0,256}", "���������� ��������� ������ ���� �� ����� 256 ��������")
		);

		fb.addField(fieldBuilder.build());

		//��� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ������");
		fieldBuilder.setName("errorType");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator());

		fb.addField(fieldBuilder.build());

		//�������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName("system");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator());

		fb.addField(fieldBuilder.build());

		// ��������� � ������� ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� � ����");
		fieldBuilder.setName("message");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator(".{0,256}", "��������� � ���� ������ ���� �� ����� 256 ��������")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
