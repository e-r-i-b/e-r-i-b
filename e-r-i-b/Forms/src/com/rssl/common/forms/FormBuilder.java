package com.rssl.common.forms;

import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.phizic.utils.ListUtil;

import java.util.*;

/**
 * @author Evgrafov
 * @ created 08.12.2005
 * @ $Author: komarov $
 * @ $Revision: 38195 $
 */

@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
public class FormBuilder
{
	private FormImpl form;
	private List<Field> fields = new ArrayList<Field>();
	public static final Form EMPTY_FORM = new FormImpl(); 
	public FormBuilder()
	{
		form = new FormImpl();
		setFormValidators(new ArrayList<MultiFieldsValidator>());
	}

	public String getName()
	{
		return form.getName();
	}

	public void setName(String name)
	{
		form.setName(name);
	}

	public String getDescription()
	{
		return form.getDescription();
	}

	public void setDescription(String value)
	{
		form.setDescription(value);
	}

	public void setTemplateName(String value)
	{
		form.setTemplateName(value);
	}

	public String getTemplateName()
	{
		return form.getTemplateName();
	}

	public String getDetailedDescription()
	{
		return form.getDetailedDescription();
	}

	public void setDetailedDescription(String value)
	{
		form.setDetailedDescription(value);
	}

	public String getConfirmDescription()
	{
		return form.getConfirmDescription();
	}

	public void setConfirmDescription(String value)
	{
		form.setConfirmDescription(value);
	}

	public List<Field> getFields()
	{
		return form.getFields();
	}

	public void setFields(Field... fields)
	{
		this.fields = ListUtil.fromArray(fields);
	}

	public void setFields(List<Field> fields)
	{
		this.fields = fields;
	}

	public void addField(Field field)
	{
		fields.add(field);
	}

	public void addFields(List<Field> fields)
	{
		this.fields.addAll(fields);
	}

	public void addFields(Field... fields)
	{
		this.fields.addAll(Arrays.asList(fields));
	}

	public List<MultiFieldsValidator> getFormValidators()
	{
		return form.getFormValidators();
	}

	public void setFormValidators(MultiFieldsValidator... formValidators)
	{
		form.setFormValidators(ListUtil.fromArray(formValidators));
	}

	public void setFormValidators(List<MultiFieldsValidator> formValidators)
	{
		form.setFormValidators(formValidators);
	}

	public void addFormValidators(MultiFieldsValidator... formValidators)
	{
		form.addFormValidators(ListUtil.fromArray(formValidators));
	}

	public void addFormValidators(List<MultiFieldsValidator> formValidators)
	{
		form.addFormValidators(formValidators);
	}

	public Form build()
	{
		form.setFields(fields);
		return form;
	}
}
