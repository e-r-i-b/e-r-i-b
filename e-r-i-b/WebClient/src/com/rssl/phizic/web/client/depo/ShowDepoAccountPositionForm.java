package com.rssl.phizic.web.client.depo;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.gate.depo.DepoAccountPosition;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author lukina
 * @ created 31.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowDepoAccountPositionForm extends EditFormBase
{
	public static final Form NAME_FORM     = ShowDepoAccountPositionForm.createNameForm();

	private DepoAccountLink depoAccountLink;
	private DepoAccountPosition depoAccountPosition;
	private List<DepoAccountLink> anotherDepoAccounts;	
	private String  accountDivision;

	public List<DepoAccountLink> getAnotherDepoAccounts()
	{
		return anotherDepoAccounts;
	}

	public void setAnotherDepoAccounts(List<DepoAccountLink> anotherDepoAccounts)
	{
		this.anotherDepoAccounts = anotherDepoAccounts;
	}

	public DepoAccountLink getDepoAccountLink()
	{
		return depoAccountLink;
	}

	public void setDepoAccountLink(DepoAccountLink depoAccountLink)
	{
		this.depoAccountLink = depoAccountLink;
	}

	public DepoAccountPosition getDepoAccountPosition()
	{
		return depoAccountPosition;
	}

	public void setDepoAccountPosition(DepoAccountPosition depoAccountPosition)
	{
		this.depoAccountPosition = depoAccountPosition;
	}

	public String getAccountDivision()
	{
		return accountDivision;
	}

	public void setAccountDivision(String accountDivision)
	{
		this.accountDivision = accountDivision;
	}

	private static Form createNameForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("depoAccountName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,50}", "Наименование должно быть не более 50 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
