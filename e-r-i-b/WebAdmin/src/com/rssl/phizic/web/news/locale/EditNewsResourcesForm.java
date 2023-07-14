package com.rssl.phizic.web.news.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * @author koptyaev
 * @ created 30.09.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("ReuseOfLocalVariable")
public class EditNewsResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_LANGUAGE_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		// ���������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������");
		fieldBuilder.setName("title");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "��������� ������ ���� �� ����� 100 ��������")
		);
		formBuilder.addField(fieldBuilder.build());

		// �������� ����� �������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� �����");
		fieldBuilder.setName("shortText");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,150}", "������� ����� ������� ������ ���� �� ����� 150 ��������")
		);
		formBuilder.addField(fieldBuilder.build());

		// �����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����");
		fieldBuilder.setName("text");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("(?s).{0,5000}", "����� ������� ������ ���� �� ����� 5000 ��������")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
