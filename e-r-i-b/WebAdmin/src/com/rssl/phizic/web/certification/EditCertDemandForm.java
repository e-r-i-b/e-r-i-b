package com.rssl.phizic.web.certification;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 20.11.2006 Time: 17:21:02 To change this template use
 * File | Settings | File Templates.
 */
public class EditCertDemandForm extends EditFormBase
{
	private boolean isRefresh = false;

	public boolean isRefresh()
	{
		return isRefresh;
	}

	public void setRefresh(boolean refresh)
	{
		isRefresh = refresh;
	}

	public static final Form EDIT_FORM = createForm();

	@SuppressWarnings({"OverlyLongMethod"})
	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		//�������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName("surName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "������� ������ ���� �� ����� 100 ��������")
		);
		fb.addField(fieldBuilder.build());

		//���
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName("firstName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "��� ������ ���� �� ����� 100 ��������")
		);
		fb.addField(fieldBuilder.build());

		//��������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("patrName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "�������� ������ ���� �� ����� 100 ��������")
		);
		fb.addField(fieldBuilder.build());

		//������������� �����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������");
		fieldBuilder.setName("id");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,10}", "������������� ������ ���� �� ����� 10 ��������")
		);
		fb.addField(fieldBuilder.build());

		//���� ������ �����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����");
		fieldBuilder.setName(DateType.INSTANCE.getName());
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "���� ������ ���� �� ����� 100 ��������")
		);
		fb.addField(fieldBuilder.build());

		//������ �����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������");
		fieldBuilder.setName("status");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,50}", "������ ������ ���� �� ����� 50 ��������")
		);
		fb.addField(fieldBuilder.build());

		//������� ������ �����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ������");
		fieldBuilder.setName("refuseReason");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,256}", "������� ������ ������ ���� �� ����� 256 ��������")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
