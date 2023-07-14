package com.rssl.phizic.web.configure.addressBookSettings;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.FilterActionForm;

import java.math.BigInteger;

/**
 * @author koptyaev
 * @ created 16.06.14
 * @ $Author$
 * @ $Revision$
 */
public class EditInformMessageForm extends FilterActionForm
{
	private static final BigInteger maxLength = BigInteger.valueOf(2000L);
	public static final Form EDIT_FORM = createEditForm();

	@SuppressWarnings("ReuseOfLocalVariable")
	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		LengthFieldValidator lengthFieldValidator = new LengthFieldValidator(maxLength);
		lengthFieldValidator.setMessage("�������������� ���������, ��������� � ���������� ����������, ������ ��������� �� ����� "+ maxLength + " ������.");
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("message");
		fieldBuilder.setDescription("�������������� ���������, ��������� � ���������� ����������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator("������� �������������� ���������, ��������� � ���������� ���������� ��� ������������� ������"),
									lengthFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showMessage");
		fieldBuilder.setDescription("���������� �������������� ���������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
