package com.rssl.phizic.web.blockingrules.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.blockingrules.EditConstants;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * ����� �������������� ������������ ���������
 * @author komarov
 * @ created 15.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditBlockingRulesResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_LANGUAGE_FORM = createForm();

	@SuppressWarnings("ReuseOfLocalVariable")
	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.ERIB_MESSAGE_FIELD);
		fieldBuilder.setDescription("������� ���������� ����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(.|\\s){1,1024}$", "�� ����������� ������� ������� ���������� ����. ����������, ������� �� ����� 1024 ��������."));
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.MAPI_MESSAGE_FIELD);
		fieldBuilder.setDescription("����� ������� ���������� mApi");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(.|\\s){1,1024}$", "�� ����������� ������� ������� ���������� mApi. ����������, ������� �� ����� 1024 ��������."));
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.ATM_MESSAGE_FIELD);
		fieldBuilder.setDescription("����� ������� ���������� atmApi");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(.|\\s){1,1024}$", "�� ����������� ������� ������� ���������� atmApi. ����������, ������� �� ����� 1024 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.ERMB_MESSAGE_FIELD);
		fieldBuilder.setDescription("����� ������� ���������� ����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(.|\\s){1,1024}$", "�� ����������� ������� ������� ���������� ����. ����������, ������� �� ����� 1024 ��������."));
		formBuilder.addField(fieldBuilder.build());


		return formBuilder.build();
	}
}
