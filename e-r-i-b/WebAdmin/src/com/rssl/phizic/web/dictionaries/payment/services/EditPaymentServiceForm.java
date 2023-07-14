package com.rssl.phizic.web.dictionaries.payment.services;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.image.ImageEditFormBase;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.upload.FormFile;

import java.util.List;
import java.util.ArrayList;

/**
 * @author akrenev
 * @ created 01.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentServiceForm extends ImageEditFormBase
{
	private Long parentId;
	private FormFile fileImage;
	private Long[] parentServiceIds = new Long[]{};
	private List<Long> childrenServiceIds = new ArrayList<Long>(); //список id дочерних услуг

	public Long[] getParentServiceIds()
	{
		return parentServiceIds;
	}

	public void setParentServiceIds(Long[] parentServiceIds)
	{
		this.parentServiceIds = parentServiceIds;
	}

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

	public List<Long> getChildrenServiceIds()
	{
		return childrenServiceIds;
	}

	public void setChildrenServiceIds(List<Long> childrenServiceIds)
	{
		this.childrenServiceIds = childrenServiceIds;
	}

	protected Form createForm()
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
		fieldBuilder.setDescription("Категория верхнего уровня");
		fieldBuilder.setName("isCategory");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Показывать в каталоге");
		fieldBuilder.setName("showInSystem");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Показывать в каталоге в mApi");
		fieldBuilder.setName("showInMApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Показывать в каталоге в atmApi");
		fieldBuilder.setName("showInAtmApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Показывать в каталоге в socialApi");
		fieldBuilder.setName("showInSocialApi");
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

		for (Long id: parentServiceIds)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("parentServiceName" + id);
			fieldBuilder.setDescription("Название услуги");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());
		}

		RequiredFieldValidator validator = new RequiredFieldValidator("Необходимо добавить хотя бы одну родительскую услугу.");
		validator.setEnabledExpression(new ConstantExpression(ArrayUtils.isEmpty(parentServiceIds)));
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("parentServices");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.isCategory != true"));
	    fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("cardOperationCategoryId");
		fieldBuilder.setDescription("Идентификатор категории карточных операций");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
