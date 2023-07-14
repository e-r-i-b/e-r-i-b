package com.rssl.phizic.web.client.crediting;

/**
 * @author Nady
 * @ created 22.01.2015
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Форма отправки отклика "Проинформирован"
 */
public class SendFeedBackInformedForm extends ActionFormBase
{
	private String id;
	private boolean isCardOffer;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public boolean isCardOffer()
	{
		return isCardOffer;
	}

	public void setCardOffer(boolean cardOffer)
	{
		isCardOffer = cardOffer;
	}
}
