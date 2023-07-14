package com.rssl.phizic.web.configure.exceptions.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.math.BigInteger;

/**
 * @author koptyaev
 * @ created 13.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class ExceptionDefaultMessageEditResourcesForm extends EditLanguageResourcesBaseForm
{
	private static final BigInteger maxLength = BigInteger.valueOf(2000L);
	public static final Form EDIT_FORM = createEditForm();

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("clientMessage");
		fieldBuilder.setDescription("��������� �� ���������, ��������� � ���������� ����������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator("������� ���������,  ��������� � ���������� ���������� �� ���������, ��� ������������� ������"),
				new LengthFieldValidator(maxLength));
		formBuilder.addField(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("adminMessage");
		fieldBuilder.setDescription("��������� �� ���������, ��������� � ��� ����������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator("������� ���������,  ��������� � ��� ���������� �� ���������, ��� ������������� ������."),
				new LengthFieldValidator(maxLength));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

}
