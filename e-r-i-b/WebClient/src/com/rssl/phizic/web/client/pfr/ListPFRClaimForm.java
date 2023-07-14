package com.rssl.phizic.web.client.pfr;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.actions.FilterPeriodHelper;

/**
 * @author Dorzhinov
 * @ created 18.02.2011
 * @ $Author $
 * @ $Revision $
 */
public class ListPFRClaimForm extends ListFormBase
{
	private PFRLink pfrLink;
	private ActivePerson user;
	private boolean isError; //возникла ошибка при получении заявок
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		return FilterPeriodHelper.createFilterPeriodFormBuilder().build();
	}

	public PFRLink getPfrLink()
	{
		return pfrLink;
	}

	public void setPfrLink(PFRLink pfrLink)
	{
		this.pfrLink = pfrLink;
	}

	public ActivePerson getUser()
	{
		return user;
	}

	public void setUser(ActivePerson user)
	{
		this.user = user;
	}

	public boolean isError()
	{
		return isError;
	}

	public void setError(boolean error)
	{
		isError = error;
	}
}
