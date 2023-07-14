package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.operations.access.AssignAccessHelper;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.util.List;

@SuppressWarnings({"JavaDoc", "ReturnOfCollectionOrArrayField", "AssignmentToCollectionOrArrayFieldFromParameter"})
public class SchemesConfigureForm extends EditPropertiesFormBase
{
	private static final Form FORM = createForm();
    private List<AssignAccessHelper> helpers;

	public List<AssignAccessHelper> getHelpers()
	{
		return helpers;
	}

	public void setHelpers(List<AssignAccessHelper> helpers)
	{
		this.helpers = helpers;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(Constants.DEFAULT_SCHEME + AccessType.employee);
		fieldBuilder.setDescription("Способ загрузки");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	@Override
	public Form getForm()
	{
		return FORM;
	}
}
