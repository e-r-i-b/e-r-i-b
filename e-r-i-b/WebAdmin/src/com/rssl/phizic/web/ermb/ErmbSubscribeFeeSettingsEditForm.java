package com.rssl.phizic.web.ermb;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.ermb.SubscribeFeeConstants;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * ����� ��� �������������� �������� ��� ��� �������� ����������� ����� ����
 * @author Rtischeva
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbSubscribeFeeSettingsEditForm extends EditPropertiesFormBase
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

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SubscribeFeeConstants.FPP_UNLOAD_TIME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("����� �������� ���");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,250}", "���� \"����� �������� ���\" �� ������ ��������� 250 ��������"));
		fieldBuilder.addValidators(new RegexpFieldValidator("^((\\s*([0-1][0-9]|[2][0-3]):([0-5][0-9])\\s*)(;\\s*([0-1][0-9]|[2][0-3]):([0-5][0-9])\\s*)*)?$",
				"������� ���������� �������� ������� �������� ��� � ������� HH:�� ����� ;"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SubscribeFeeConstants.FPP_PROC_TOTAL_COUNT);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.setDescription("���������� ��������� �������� ���");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator("����������, ������� ���������� ��������� �������� ���"),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{"1", "5", "10", "50", "100"})));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SubscribeFeeConstants.FPP_PATH);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("���� ����� �������� ���");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "���� \"���� ����� �������� ���\" �� ������ ��������� 100 ��������"));
		formBuilder.addField(fieldBuilder.build());

//		BUG089151 ����� �������� ���������� �� ���� ��������� � ���� ������� �� ������, ���� ������������ ����������

//		fieldBuilder = new FieldBuilder();
//		fieldBuilder.setName(SubscribeFeeConstants.FPP_WAITING_DAYS_NUMBER);
//		fieldBuilder.setType(LongType.INSTANCE.getName());
//		fieldBuilder.setDescription("����� �������� ������ �� ���");
//		fieldBuilder.setType(LongType.INSTANCE.getName());
//		fieldBuilder.addValidators(new RequiredFieldValidator("����������, ������� ���������� ���� �������� ������ �� ���"),
//				new RegexpFieldValidator("(\\d{0,3})", "���� \"����� �������� ������ �� ���\" ������ ��������� ������ �����"));
//		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SubscribeFeeConstants.MAX_TRANSACTION_COUNT);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.setDescription("������������ ���������� ���������� � ����� ���-�����");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator("����������, ������� ������������ ���������� ���������� � ����� ���-�����"),
				new RegexpFieldValidator("(\\d{0,10})", "���� \"������������ ���������� ���������� � ����� ���-�����\" ������ ��������� ������ �����"));
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
