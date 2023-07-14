package com.rssl.phizic.web.dictionaries.pages;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 01.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListPagesForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		// ��������
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,64}", "�������� �������� ������ ���� �� ����� 64 ��������")
		);
		fb.addField(fieldBuilder.build());
		//������������ �������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ �������");
		fieldBuilder.setName("parentId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		return fb.build();
	}
}
