package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Òýã "cards"
 * @author Jatsky
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"status", "cards"})
@XmlRootElement(name = "cards")
public class CardsTag
{
	private Status status;
	private List<CardTag> cards;

	@XmlElement(name = "status", required = true)
	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	@XmlElement(name = "card")
	public List<CardTag> getCards()
	{
		return cards;
	}

	public void setCards(List<CardTag> cards)
	{
		this.cards = cards;
	}
}
