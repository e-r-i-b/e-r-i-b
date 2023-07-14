package com.rssl.phizic.web.dictionaries.payment.services.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * @author mihaylov
 * @ created 23.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ����� �������������� �������������� ������ ������
 */
public class EditPaymentServiceResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������");
		fieldBuilder.setName("localedName");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "������������ ������ ���� �� ����� 128 ��������.")
		);
		formBuilder.addField(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("localedDescription");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,512}", "�������� ������ ���� �� ����� 512 ��������."));
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
