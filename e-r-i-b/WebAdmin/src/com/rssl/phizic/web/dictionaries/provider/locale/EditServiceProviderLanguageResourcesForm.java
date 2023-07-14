package com.rssl.phizic.web.dictionaries.provider.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * @author komarov
 * @ created 06.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditServiceProviderLanguageResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_FORM = getEditForm();


	@SuppressWarnings({"OverlyLongMethod", "ReuseOfLocalVariable"})
	private static Form getEditForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,160}", "������������ ������� ���� �� ����� 160 ��������."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ������������ ���������� �����");
		fieldBuilder.setName("legalName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^.{1,250}$", "���� ����������� ������������ ���������� ����� �� ������ ��������� 250 ��������.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� ���������� �����");
		fieldBuilder.setName("alias");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ �����");
		fieldBuilder.setName("bankName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());



		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ��� ��������");
		fieldBuilder.setName("description");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(?s).{0,255}", "���� ����������� ��� �������� �� ������ ��������� 255 ��������."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� �� ���������� �����");
		fieldBuilder.setName("tip");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(?s).{0,255}", "���� ��������� �� ���������� ����� �� ������ ��������� 255 ��������."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� � ��������");
		fieldBuilder.setName("commissionMessage");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(?s).{0,250}", "���� ��������� � �������� �� ������ ��������� 250 ��������."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ����������, ��������� � ����");
		fieldBuilder.setName("nameOnBill");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ������ � ����������� �������");
		fieldBuilder.setName("nameService");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{1,150}", "���� �������� ������ � ����������� ������� �� ������ ��������� 150 ��������.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
