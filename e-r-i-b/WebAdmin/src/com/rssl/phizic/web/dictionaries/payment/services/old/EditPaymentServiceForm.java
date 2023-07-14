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
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "Наименование должно быть не более 128 символов.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор");
		fieldBuilder.setName("synchKey");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,50}", "Идентификатор должен быть не более 50 символов."),
				new RegexpFieldValidator("[^а-яА-ЯЁё]*", "Идентификатор не должен содержать буквы русского алфавита.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Описание");
		fieldBuilder.setName("description");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(	new RegexpFieldValidator(".{1,512}", "Описание должно быть не более 512 символов."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Показывать в популярных платежах клиентам");
		fieldBuilder.setName("popular");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("system");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Приоритет");
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