package com.rssl.phizic.web.dictionaries.pfp.targets;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.types.LongType;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListTargetsForm extends ListFormBase
{
	public static final Form FILTER_FORM = createEditForm();
	
	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,250}", "���� �������� ������ ��������� �� ����� 250 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanKind");
		fieldBuilder.setDescription("��������� �������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d*", "���� \"��������� �������\" ������ ��������� ������ �����."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanKindName");
		fieldBuilder.setDescription("��������� �������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
