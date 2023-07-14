package com.rssl.phizic.web.client.insurance;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * @author lukina
 * @ created 21.03.2013
 * @ $Author$
 * @ $Revision$
 * Форма для просмотра/печати детальной информации по страховому/НПФ продукту
 */

public class ShowInsuranceDetailForm extends EditFormBase
{
	private InsuranceLink link;
	private InsuranceApp insuranceApp; //детельная информация по старховому/НПФ продукту
	private ActivePerson user;    // текущий пользователь
	public static final Form EDIT_INSURANCE_NAME_FORM  = editInsuranceNameForm();

	public InsuranceLink getLink()
	{
		return link;
	}

	public void setLink(InsuranceLink link)
	{
		this.link = link;
	}

	public ActivePerson getUser()
	{
		return user;
	}

	public void setUser(ActivePerson user)
	{
		this.user = user;
	}

	public InsuranceApp getInsuranceApp()
	{
		return insuranceApp;
	}

	public void setInsuranceApp(InsuranceApp insuranceApp)
	{
		this.insuranceApp = insuranceApp;
	}

	private static Form editInsuranceNameForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("insuranceName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,256}", "Название должно быть не более 256 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
