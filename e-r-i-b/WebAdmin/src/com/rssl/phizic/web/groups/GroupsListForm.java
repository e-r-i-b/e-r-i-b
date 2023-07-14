package com.rssl.phizic.web.groups;

import org.apache.struts.action.ActionForm;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * User: Omeliyanchuk
 * Date: 10.11.2006
 * Time: 12:40:35
 */
public class GroupsListForm extends ListLimitActionForm
{
	private String category;

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// Наименование
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator(".{1,256}", "Название должно быть не более 256 символов")

		);

		fb.addField(fieldBuilder.build());
		return fb.build();
	}
}
