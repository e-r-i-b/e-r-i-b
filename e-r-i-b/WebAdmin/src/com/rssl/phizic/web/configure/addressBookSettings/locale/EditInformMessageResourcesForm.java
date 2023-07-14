package com.rssl.phizic.web.configure.addressBookSettings.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.math.BigInteger;

/**
 * @author koptyaev
 * @ created 08.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditInformMessageResourcesForm extends EditLanguageResourcesBaseForm
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

		return formBuilder.build();
	}
}
