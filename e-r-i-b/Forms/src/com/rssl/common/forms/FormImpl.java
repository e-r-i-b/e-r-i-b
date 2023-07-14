package com.rssl.common.forms;

import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Evgrafov
 * @ created 29.05.2006
 * @ $Author: komarov $
 * @ $Revision: 38195 $
 */

class FormImpl implements Form
{
	private String                     name;
	private String                     description    = "";
	private String                     templateName   = "";
	private String                     detailedDescription = "";
	private String                     confirmDescription = "";
	private List<Field>                fields         = new ArrayList<Field>();
	private List<MultiFieldsValidator> formValidators = new ArrayList<MultiFieldsValidator>();

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getTemplateName()
	{
		return StringHelper.isEmpty(templateName) ? description : templateName;
	}

	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	public String getDetailedDescription()
	{
		return detailedDescription;
	}

	public void setDetailedDescription(String detailedDescription)
	{
		this.detailedDescription = detailedDescription;
	}

	public String getConfirmDescription()
	{
		return confirmDescription;
	}

	public void setConfirmDescription(String confirmDescription)
	{
		this.confirmDescription = confirmDescription;
	}

	public List<Field> getFields()
	{
		return Collections.unmodifiableList(fields);
	}

	public void setFields(List<Field> fields)
	{
		this.fields.clear();
		this.fields.addAll(fields);
	}

	public List<MultiFieldsValidator> getFormValidators()
	{
		return Collections.unmodifiableList(formValidators);
	}

	public void setFormValidators(List<MultiFieldsValidator> formValidators)
	{
		this.formValidators.clear();
		this.formValidators.addAll(formValidators);
	}

	public void addFormValidators(List<MultiFieldsValidator> formValidators)
	{
		this.formValidators.addAll(formValidators);
	}
}