package com.rssl.phizic.web.passwordcards;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.payments.forms.validators.PasswordCardExistValidator;
import com.rssl.phizic.business.payments.forms.validators.PasswordCardNumberValidator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 22.11.2006 Time: 18:01:22 To change this template use
 * File | Settings | File Templates.
 */
public class AddUserPasswordCardsForm  extends ActionFormBase
{
	private ActivePerson activePerson;
	private Long         id;
    private Long         person;
	private Map<String, Object> fields = new HashMap<String, Object>();
	
	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
        this.person=id;
    }

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
        this.id = person;
    }

	public Object getField(String name)
	{
		return fields.get(name);
	}

	public void setField(String key, Object obj)
	{
		fields.put(key, obj);
	}

	public Map<String, Object> getFields()
	{
		return fields;
	}

	public void setFields(Map<String, Object> fields)
	{
		this.fields = fields;
	}	

	public static final Form ADD_FORM     = createForm();

	@SuppressWarnings({"OverlyLongMethod"})
	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер карты");
		fieldBuilder.setName("cardNumber");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators( new PasswordCardNumberValidator(),
									new RequiredFieldValidator(), new PasswordCardExistValidator() );

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
