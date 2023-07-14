package com.rssl.phizic.web.skins;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * User: Balovtsev
 * Date: 23.05.2011
 * Time: 14:58:47
 */
public class EditPersonUIForm extends EditFormBase
{
	private Long       person;
	private Skin       skin;
	private List<Skin> skins;
	private Person     activePerson;
	private Boolean    modified=false;

	public static final Form UI_PERSON_FORM = buildForm();
	private static Form buildForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("skinType");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setDescription("Тип стиля");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setEnabledExpression(new RhinoExpression("form.skinType == 'personal'"));
		fieldBuilder.setName("skin");
		fieldBuilder.setType("long");
		fieldBuilder.addValidators(requiredFieldValidator);
		fieldBuilder.setDescription("Идентификатор персонального стиля");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public Long getPerson()
	{
		return person;
	}

	public void setPerson(Long person)
	{
		this.person = person;
	}

	public List<Skin> getSkins()
	{
		return skins;
	}

	public void setSkins(List<Skin> skins)
	{
		this.skins = skins;
	}

	public Skin getSkin()
	{
		return skin;
	}

	public void setSkin(Skin skin)
	{                 
		this.skin = skin;
	}

	public Person getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(Person activePerson)
	{
		this.activePerson = activePerson;
	}

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}
}
