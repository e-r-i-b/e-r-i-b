package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * ����� �������������� �������� ������� � ����������� � �������������� ������
 * @ author: Gololobov
 * @ created: 13.02.14
 * @ $Author$
 * @ $Revision$
 */
public class SpoobkConfigureForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createForm();
	private static final String SPOOBK_TABLE_FIELD_NAME = "com.rssl.phizic.web.configure.spoobk_table_name";
	private static final String SPOOBK_TABLE_FIELD_DESCRIPTION = "��� ������� � ������";


	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		//�������� ������� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SPOOBK_TABLE_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription(SPOOBK_TABLE_FIELD_DESCRIPTION);
		fieldBuilder.addValidators(requiredFieldValidator,
				new RegexpFieldValidator(".{1,30}", "� �������� ������� ������ ���� �� ����� 30 ��������"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
