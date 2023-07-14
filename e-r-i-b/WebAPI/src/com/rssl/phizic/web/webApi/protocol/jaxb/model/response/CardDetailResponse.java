package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productdetail.CardDetailTag;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Ответ на запрос детальной информации по карте
 * @author Jatsky
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"status", "card"})
@XmlRootElement(name = "message")
public class CardDetailResponse extends Response
{
	private CardDetailTag card;

	@XmlElement(name = "card", required = false)
	public CardDetailTag getCard()
	{
		return card;
	}

	public void setCard(CardDetailTag card)
	{
		this.card = card;
	}
}
