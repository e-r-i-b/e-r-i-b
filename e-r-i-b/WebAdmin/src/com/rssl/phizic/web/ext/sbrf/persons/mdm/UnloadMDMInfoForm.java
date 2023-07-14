package com.rssl.phizic.web.ext.sbrf.persons.mdm;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * ����� �������� ����� �������� ���� ��� �������� � ���
 * @author komarov
 * @ created 20.07.15
 * @ $Author$
 * @ $Revision$
 */
public class UnloadMDMInfoForm extends EditFormBase
{
	public static final Form MDM_FORM     = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������� �������");
		fieldBuilder.setName("loginIds");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new RequiredFieldValidator("������� ���� �� ���� �������������."),
				new RegexpFieldValidator("(\\d+,){0,99}\\d+", "������� �������������� ����� �������. ���������� ��������������� �� ������ ��������� 100.")
		);

		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
