package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author shapin
 * @ created 29.10.14
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeSettingsForm extends EditPropertiesFormBase
{
	private static final String TIME_TO_BLOCK_ACCOUNT_BY_INACTIVITY = "com.rssl.iccs.employeeSettings.timeToBlockAccountByInactivity";
	private static Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(TIME_TO_BLOCK_ACCOUNT_BY_INACTIVITY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("����� �� ���������� ������� ������ �� ������������");
		fieldBuilder.setValidators(
				new RegexpFieldValidator("[1-9]|[1-9][0-9]|[1-9][0-9]{2}", "���������� ����� ������ ���� ����� ������������� ������")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
