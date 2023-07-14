package com.rssl.phizic.business.ermb.bankroll.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Rtischeva
 * @ created 29.11.13
 * @ $Author$
 * @ $Revision$
 */
@XmlType()
@XmlAccessorType(XmlAccessType.NONE)
class BankrollProductDefaultBean
{
	@XmlElement(name = "visibility", required = true)
	private boolean visibility;

	@XmlElement(name = "notification", required = true)
	private boolean notification;

	boolean isVisibility()
	{
		return visibility;
	}

	void setVisibility(boolean visibility)
	{
		this.visibility = visibility;
	}

	boolean isNotification()
	{
		return notification;
	}

	void setNotification(boolean notification)
	{
		this.notification = notification;
	}
}
