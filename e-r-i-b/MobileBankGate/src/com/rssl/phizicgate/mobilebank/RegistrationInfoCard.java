package com.rssl.phizicgate.mobilebank;

import javax.xml.bind.annotation.*;

/**
 * @author Jatsky
 * @ created 03.08.15
 * @ $Author$
 * @ $Revision$
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CARD")
public class RegistrationInfoCard

{
	/**
	 * Номер информационной карты
	 */
	@XmlValue
	private String card;

	/**
	 * Признак блокировки
	 */
	@XmlAttribute(name = "TYPE")
	private String type;

	public RegistrationInfoCard()
	{
	}

	public String getCard()
	{
		return card;
	}

	public void setCard(String card)
	{
		this.card = card;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
