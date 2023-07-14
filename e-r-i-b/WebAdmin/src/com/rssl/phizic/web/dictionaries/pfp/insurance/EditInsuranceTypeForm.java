package com.rssl.phizic.web.dictionaries.pfp.insurance;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.MultiLineTextValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.dictionaries.pfp.products.PFPImageEditFormBase;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditInsuranceTypeForm extends PFPImageEditFormBase
{
	public static final Form EDIT_FORM = createEditForm();
	private Long parentId;

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("������������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,50}", "���� ������������ ������ ��������� �� ����� 50 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("parentName");
		fieldBuilder.setDescription("��� ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("parentId");
		fieldBuilder.setDescription("��� ��������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("description");
		fieldBuilder.setDescription("��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new MultiLineTextValidator("���� \"��������\" ������ ��������� �� ����� 250 ��������.", 250));
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addFields(getImageField());

		return formBuilder.build();
	}
}
