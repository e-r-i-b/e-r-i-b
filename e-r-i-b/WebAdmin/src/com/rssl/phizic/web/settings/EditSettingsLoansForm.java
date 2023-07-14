package com.rssl.phizic.web.settings;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.util.BankInfoUtil;

/**
 * ����� �������������� �������� ��� ��������
 * @author basharin
 * @ created 20.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditSettingsLoansForm extends EditPropertiesFormBase
{
	private static final Form form = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BankInfoUtil.BANK_LOAN_LINK_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.setValidators();
		fieldBuilder.setDescription("������ �� �������� ��������� ��������� �� ����� �����");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "���� \"������ �� �������� ��������� ��������� �� ����� �����\" �� ������ ��������� 100 ��������"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BankInfoUtil.BANK_CARD_LINK_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.setValidators();
		fieldBuilder.setDescription("������ �� �������� � ������������� �� ��������� ������ �� ����� �����");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "���� \"������ �� �������� � ������������� �� ��������� ������ �� ����� �����\" �� ������ ��������� 100 ��������"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	@Override
	public Form getForm()
	{
		return form;
	}
}