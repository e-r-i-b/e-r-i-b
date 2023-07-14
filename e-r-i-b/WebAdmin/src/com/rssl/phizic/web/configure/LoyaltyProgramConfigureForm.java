package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import static com.rssl.phizic.config.loyalty.LoyaltyConfig.*;

/**
 * @author lukina
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoyaltyProgramConfigureForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(URL_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("URL-����� ����� ��������� ����������");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "URL-����� ����� ��������� ���������� �� ������ ��������� 100 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CERT_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("���������� ��������� ���������� � �������� ������");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "���������� ��������� ���������� � �������� ������ �� ������ ��������� 100 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PRIV_CERT_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("���������� ��������� ���������� � �������� ������");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "���������� ��������� ���������� � �������� ������ �� ������ ��������� 100 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(STORE_TYPE);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("��� ��������� ������������ ��������� ����������");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "��� ��������� ������������ ��������� ���������� �� ������ ��������� 100 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(STORE_PATH);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("���� � ��������� ������������ ��������� ����������");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "���� � ��������� ������������ ��������� ���������� �� ������ ��������� 100 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(STORE_PASSWORD);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("������ ��� ������� � ��������� ������������ ��������� ����������");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "������ ��� ������� � ��������� ������������ ��������� ���������� �� ������ ��������� 100 ��������."));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
