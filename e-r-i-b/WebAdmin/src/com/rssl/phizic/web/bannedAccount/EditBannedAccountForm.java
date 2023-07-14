package com.rssl.phizic.web.bannedAccount;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * @author: vagin
 * @ created: 01.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditBannedAccountForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// ����� �����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����");
		fieldBuilder.setName("account");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{20}", "���� ������ ���� 20 ��������"),
				new RegexpFieldValidator("(\\d*\\**)+","����������� ���� ����� ������ �������� �*�, �������� 30202**************")
		);
		fb.addField(fieldBuilder.build());

		// ���� ��� - ������ ����� ������� ��� ������ � ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ���");
		fieldBuilder.setName("BICList");
		fieldBuilder.setType("string");
	    fieldBuilder.addValidators(
			   new RegexpFieldValidator(".{0,500}", "�� ��������� ����������� ���������� ���������� ����� ��� ����� �����")
		);
		fb.addField(fieldBuilder.build());

		//��� �������(enum : �����, ����, ���)
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �������");
		fieldBuilder.setName("banType");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

}
