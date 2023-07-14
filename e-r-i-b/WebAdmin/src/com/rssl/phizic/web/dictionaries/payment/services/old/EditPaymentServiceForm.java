package com.rssl.phizic.web.dictionaries.payment.services.old;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.payment.services.CategoryServiceType;
import com.rssl.phizic.web.image.ImageEditFormBase;
import org.apache.struts.upload.FormFile;

/**
 * @author lukina
 * @ created 29.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class EditPaymentServiceForm  extends ImageEditFormBase
{
	public static final Form FILTER_FORM = createForm();
	private Long parentId;
	private FormFile fileImage;

	public FormFile getFileImage()
	{
		return fileImage;
	}

	public void setFileImage(FormFile fileImage)
	{
		this.fileImage = fileImage;
		setField("fileName", fileImage.getFileName());
	}

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
		FormBuilder fb = new FormBuilder();
		fb.addFields(getImageField());
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "������������ ������ ���� �� ����� 128 ��������.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������");
		fieldBuilder.setName("synchKey");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,50}", "������������� ������ ���� �� ����� 50 ��������."),
				new RegexpFieldValidator("[^�-��-ߨ�]*", "������������� �� ������ ��������� ����� �������� ��������.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("description");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(	new RegexpFieldValidator(".{1,512}", "�������� ������ ���� �� ����� 512 ��������."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� � ���������� �������� ��������");
		fieldBuilder.setName("popular");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("system");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������");
		fieldBuilder.setName("priority");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		for (CategoryServiceType category : CategoryServiceType.values())
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription(category.toValue());
			fieldBuilder.setName("category_"+category.toString());
			fieldBuilder.setType(BooleanType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());
		}
		return fb.build();
	}
}