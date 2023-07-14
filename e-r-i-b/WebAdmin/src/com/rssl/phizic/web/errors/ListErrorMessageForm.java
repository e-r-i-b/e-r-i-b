package com.rssl.phizic.web.errors;

import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * @author gladishev
 * @ created 16.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListErrorMessageForm extends ListLimitActionForm
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// ���������� ���������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� ���������");
		fieldBuilder.setName("regExp");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator(".{1,256}", "���������� ��������� ������ ���� �� ����� 256 ��������")
		);

		fb.addField(fieldBuilder.build());

		//��� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ������");
		fieldBuilder.setName("errorType");
		fieldBuilder.setType("string");

		fb.addField(fieldBuilder.build());

		//�������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName("system");
		fieldBuilder.setType("string");

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
