package com.rssl.phizic.web.settings;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.utils.DepositConfig;

/**
 * ����� �������������� �������� ��� ���������
 * @author basharin
 * @ created 20.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditSettingsDepositsForm extends EditPropertiesFormBase
{
	private static final Form form = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DepositConfig.ACCOUNTS_KINDS_FORBIDDEN_CLOSING);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("���� �������, ����������� ��� ��������");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"���� �������, ����������� ��� ��������\" ������ ��������� �� ������ 100 ���� ����������� ��������."),
				new RegexpFieldValidator("^\\d{0,100}?(\\,\\d{1,100}?)*$", "�� ���������� ������ ������ � ���� \"���� �������, ����������� ��� ��������\"")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	@Override
	public Form getForm()
	{
		return form;
	}
}