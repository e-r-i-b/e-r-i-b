package com.rssl.phizicgate.mobilebank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Xml для бки сообщений хп
 * @see UESIMessagesJDBCAction
 *
 * @author Puzikov
 * @ created 05.08.15
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "bkisbol")
@XmlAccessorType(XmlAccessType.NONE)
public class BkiMessageBean
{
	@XmlElement(name = "phone", required = true)
	private String phone;

	@XmlElement(name = "query", required = true)
	private String query;

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}
}
