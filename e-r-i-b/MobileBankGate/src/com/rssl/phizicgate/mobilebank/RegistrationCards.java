package com.rssl.phizicgate.mobilebank;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jatsky
 * @ created 03.08.15
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "CARDS")
public class RegistrationCards
{
	private List<String> cards;

	public RegistrationCards()
	{
	}

	public RegistrationCards(List<String> cards)
	{
		this.cards = cards;
	}

	@XmlElement(name = "CARD")
	public List<String> getCards()
	{
		return cards;
	}

	public void setCards(List<String> cards)
	{
		this.cards = cards;
	}
}
