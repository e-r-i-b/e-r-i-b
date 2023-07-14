package com.rssl.phizic.web.dictionaries.billing;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizicgate.manager.routing.Adapter;

import java.util.List;

/**
 * @author akrenev
 * @ created 10.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditBillingForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();
	public List<Adapter> allAdaptersList;

	public List<Adapter> getAllAdaptersList()
	{
		return allAdaptersList;
	}

	public void setAllAdaptersList(List<Adapter> allAdaptersList)
	{
		this.allAdaptersList = allAdaptersList;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "������������ ������ ���� �� ����� 128 ��������.")
		);


		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� �������");
		fieldBuilder.setName("adapterUUID");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ����������� �������");
		fieldBuilder.setName("code");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,50}", "��� ����������� ������� ������ ���� �� ����� 50 ��������.")
		);

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� �������� ���");
		fieldBuilder.setName("needUploadJBT");
		fieldBuilder.setType("boolean");

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������");
		fieldBuilder.setName("userName");
		fieldBuilder.setType("string");

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ������");
		fieldBuilder.setName("connectMode");
		fieldBuilder.setType("string");

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ��������");
		fieldBuilder.setName("requisites");
		fieldBuilder.setType("boolean");

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� �������� �� ��");
		fieldBuilder.setName("comission");
		fieldBuilder.setType("boolean");

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �������� ������ �� �������");
		fieldBuilder.setName("timeout");
		fieldBuilder.setType("integer");

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� �������� ��� ������ �� ��������� ����������");
		fieldBuilder.setName("templateState");
		fieldBuilder.setType("string");

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
