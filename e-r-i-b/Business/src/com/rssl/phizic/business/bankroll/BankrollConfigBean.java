package com.rssl.phizic.business.bankroll;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "BankrollConfig")
@XmlAccessorType(XmlAccessType.NONE)
class BankrollConfigBean
{
	@XmlElement(name = "in-use", required = true)
	private boolean used;

	@XmlElement(name = "list-lifetime", required = true)
	private long listLifetime;

	///////////////////////////////////////////////////////////////////////////

	boolean isUsed()
	{
		return used;
	}

	void setUsed(boolean used)
	{
		this.used = used;
	}

	long getListLifetime()
	{
		return listLifetime;
	}

	void setListLifetime(long listLifetime)
	{
		this.listLifetime = listLifetime;
	}
}
