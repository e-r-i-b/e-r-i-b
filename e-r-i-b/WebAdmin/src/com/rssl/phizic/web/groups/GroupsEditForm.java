package com.rssl.phizic.web.groups;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 10.11.2006 Time: 15:04:15 To change this template use
 * File | Settings | File Templates.
 */
public class GroupsEditForm extends EditFormBase
{
	private Group group;

	private String category;

	/**
	 * Стили, доступные группе для выбора стиля по-умолчанию
	 */
	private List<Skin> skins;

	///////////////////////////////////////////////////////////////////////////

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public Group getGroup()
	{
		return group;
	}

	public void setGroup(Group group)
	{
		this.group = group;
	}

	public List<Skin> getSkins()
	{
		return skins;
	}

	public void setSkins(List<Skin> skins)
	{
		this.skins = skins;
	}

	///////////////////////////////////////////////////////////////////////////

	public static final Form GROUP_FORM     = createForm();

	@SuppressWarnings({"OverlyLongMethod"})
	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		Expression defaultStyleExpression = new RhinoExpression("form.useDefaultStyle == 'true'");
		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(defaultStyleExpression);

		FieldBuilder fieldBuilder;

		// Название группы
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название группы");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,25}", "Название группы должно быть не более 25 символов"));

		fb.addField(fieldBuilder.build());

		// Название группы
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Описание группы");
		fieldBuilder.setName("description");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator(".{0,250}", "Описание группы должно быть не более 250 символов"));

		fb.addField(fieldBuilder.build());

		// Приоритет
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Приоритет");
		fieldBuilder.setName("priority");
		fieldBuilder.setType("long");
		fieldBuilder.addValidators( new RequiredFieldValidator() );
		fb.addField(fieldBuilder.build());

		// дефолтный скин
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Стиль по умолчанию");
		fieldBuilder.setName("useDefaultStyle");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Стиль по умолчанию");
		fieldBuilder.setName("defaultStyle");
		fieldBuilder.setType("long");
		fb.addField(fieldBuilder.build());
		fieldBuilder.addValidators( requiredValidator );



		return fb.build();
	}
}
