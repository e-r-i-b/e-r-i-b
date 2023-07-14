package com.rssl.phizic.web.finances;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.ips.IPSConstants;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author koptyaev
 * @ created 28.03.14
 * @ $Author$
 * @ $Revision$
 */
public class EditAutomaticRecategorizationForm  extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ���� ���������� � �������� BUSSINES_DOCUNENTS, CARD_OPERATION �� �����");
		fieldBuilder.setName(IPSConstants.LINKING_MAX_DATE_DIFF);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new  RequiredFieldValidator(),
				new RegexpFieldValidator("^(\\d{1,3})?$", "���� ������������ ���� ����������� ������ ��������� �� ����� 3 ����.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� �������� �� ������� BUSSINES_DOCUNENTS.");
		fieldBuilder.setName(IPSConstants.LINKING_ENABLED);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
