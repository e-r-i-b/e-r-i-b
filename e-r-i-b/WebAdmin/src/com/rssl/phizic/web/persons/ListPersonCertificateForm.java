package com.rssl.phizic.web.persons;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.ListLimitActionForm;

/**
 * @author Omeliyanchuk
 * @ created 06.04.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListPersonCertificateForm extends ListLimitActionForm
{
	private ActivePerson activePerson;

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		DateFieldValidator dateFieldValidator = new DateFieldValidator();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата начала действия");
		fieldBuilder.setName("startDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setValidators(dateFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата окончания действия");
		fieldBuilder.setName("endDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setValidators(dateFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("status");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
