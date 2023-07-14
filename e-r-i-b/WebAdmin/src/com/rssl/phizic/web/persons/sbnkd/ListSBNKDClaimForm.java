package com.rssl.phizic.web.persons.sbnkd;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * список заявок Сбербанк на каждый день для клиента
 * @author basharin
 * @ created 19.02.15
 * @ $Author$
 * @ $Revision$
 */

public class ListSBNKDClaimForm extends ListFormBase<IssueCardDocumentImpl>
{
	public static final String FROM_DATE_FIELD = "fromDate";
	public static final String TO_DATE_FIELD = "toDate";
	public static Form FILTER_FORM = createForm();

	private Long person;
	private Boolean modified = false;
	private ActivePerson activePerson;

	private static Form createForm()
    {
        FormBuilder formBuilder = new FormBuilder();

	    FieldBuilder fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName(FROM_DATE_FIELD);
		fb.setDescription("дата создания с");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "Дата должна быть в формате ДД.ММ.ГГГГ"), new RequiredFieldValidator());
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName(TO_DATE_FIELD);
		fb.setDescription("дата создания до");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "Дата должна быть в формате ДД.ММ.ГГГГ"), new RequiredFieldValidator());
		formBuilder.addField(fb.build());

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

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
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
