package com.rssl.phizic.web.client.cards;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.client.cards.ShowCardInfoForm;

/**
 * @author Krenev
 * @ created 15.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class PrintCardInfoForm extends ShowCardInfoForm
{
	private ActivePerson user;

	/**
	 * @return текущий пользователь
	 */
	public ActivePerson getUser()
	{
		return user;
	}

	/**
	 * @param user текущий пользователь
	 */
	public void setUser(ActivePerson user)
	{
		this.user = user;
	}
}
