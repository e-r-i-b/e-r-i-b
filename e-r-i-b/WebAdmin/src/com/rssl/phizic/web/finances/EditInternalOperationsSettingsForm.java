package com.rssl.phizic.web.finances;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.ips.IPSConstants;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * ����� �������� ��������� ��������� ����� ������ �������
 * @author lepihina
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EditInternalOperationsSettingsForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("MCC-���� ��� �������� �����-�����");
		fieldBuilder.setName(IPSConstants.CARD_TO_CARD_OPERATIONS_MCC_CODES);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				requiredValidator,
				new RegexpFieldValidator(".{1,1000}", "���� �MCC-���� ��� �������� �����-����� ������ ��������� �� ����� 1000 ��������."),
				new RegexpFieldValidator("\\d+((, ){1,1}\\d+)*", "������������ ������. Mcc-���� ������ ������������� ����� �������. ��������: 6012, 6532, 6536")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����� ���������� �� ������");
		fieldBuilder.setName(IPSConstants.INTERNAL_OPERATIONS_MAX_TIME);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(
				requiredValidator,
				new RegexpFieldValidator("^(\\d{1,3})?$", "���� ������ ����� ���������� ������ ��������� �� ����� 3 ����.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
