package com.rssl.phizic.web.client.securities;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author lukina
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 */

public class ShowSecurityDetailForm  extends EditFormBase
{
	private SecurityAccountLink link;
	private SecurityAccount securityAccount; //детельная информация по сберегательному сертификату
	private ActivePerson user;    // текущий пользователь
	public static final Form EDIT_SECURITY_NAME_FORM  = editSecurityNameForm();
	private boolean isBackError;

	public SecurityAccountLink getLink()
	{
		return link;
	}

	public void setLink(SecurityAccountLink link)
	{
		this.link = link;
	}

	public SecurityAccount getSecurityAccount()
	{
		return securityAccount;
	}

	public void setSecurityAccount(SecurityAccount securityAccount)
	{
		this.securityAccount = securityAccount;
	}

	public ActivePerson getUser()
	{
		return user;
	}

	public void setUser(ActivePerson user)
	{
		this.user = user;
	}

	public boolean getBackError()
	{
		return isBackError;
	}

	public void setBackError(boolean backError)
	{
		isBackError = backError;
	}

	private static Form editSecurityNameForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("securityName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,100}", "Название должно быть не более 100 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
