package com.rssl.phizic.web.dictionaries.regions;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.types.StringType;

/**
 * @author khudyakov
 * @ created 03.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class EditRegionForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	private Long parentId;

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	private static Form createForm()
	{
		FormBuilder  formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������");
		fieldBuilder.setName("synchKey");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators
		(
			new RequiredFieldValidator(),
			new RegexpFieldValidator("[^�-��-߸�]*", "���� ������������� �� ������ ��������� ����� �������� ��������"),
			new RegexpFieldValidator(".{1,20}", "���� ������������� �� ������ ��������� 20 ��������")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators
		(
			new RequiredFieldValidator(), new RegexpFieldValidator(".{1,128}", "���� ������������ �� ������ ��������� 128 ��������")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ������������� �������");
		fieldBuilder.setName("parent");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ��");
		fieldBuilder.setName("codeTB");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.parent==null || form.parent==''"));
		fieldBuilder.addValidators(new RegexpFieldValidator(".{2}", "���� ��� �� ������ �������� �� 2 ��������"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ��� ������ �� (mAPI)");
		fieldBuilder.setName("providerCodeMAPI");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators
		(
			new RegexpFieldValidator(".{1,128}", "���� \"��� ��� ������ �� (mAPI)\" �� ������ ��������� 128 ��������")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ��� ������ ��(atmAPI)");
		fieldBuilder.setName("providerCodeATM");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators
		(
			new RegexpFieldValidator(".{1,128}", "���� \"��� ��� ������ ��(atmAPI)\" �� ������ ��������� 128 ��������")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}