package com.rssl.phizic.web.common.mobile.cards;

import com.rssl.phizic.web.common.client.cards.ShowCardInfoForm;

/**
 * Детальная информация по карте
 * Изменение названия карты
 * @author Krenev
 * @ created 09.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class ShowCardInfoMobileForm extends ShowCardInfoForm
{
    //in
    private String cardName;
    //out
	private Long mainCardId;

    public String getCardName()
    {
        return cardName;
    }

    public void setCardName(String cardName)
    {
        this.cardName = cardName;
    }

	public Long getMainCardId()
	{
		return mainCardId;
	}

	public void setMainCardId(Long mainCardId)
	{
		this.mainCardId = mainCardId;
	}
}