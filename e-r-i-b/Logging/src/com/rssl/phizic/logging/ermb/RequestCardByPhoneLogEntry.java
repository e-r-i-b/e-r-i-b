package com.rssl.phizic.logging.ermb;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Запись о превышении лимита запросов в ЕРМБ/МБК  по номеру телефона
 * @author lukina
 * @ created 04.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class RequestCardByPhoneLogEntry implements Serializable
{
	private Long     id;
	private Long     loginId;
	private String   fio;
	private String   docType;
	private String docNumber;
	private Calendar birthday;
	private Calendar eventDate;
	private Long     blockId;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public String getFio()
	{
		return fio;
	}

	public void setFio(String fio)
	{
		this.fio = fio;
	}

	public String getDocType()
	{
		return docType;
	}

	public void setDocType(String docType)
	{
		this.docType = docType;
	}

	public String getDocNumber()
	{
		return docNumber;
	}

	public void setDocNumber(String docNumber)
	{
		this.docNumber = docNumber;
	}

	public Calendar getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	public Calendar getEventDate()
	{
		return eventDate;
	}

	public void setEventDate(Calendar eventDate)
	{
		this.eventDate = eventDate;
	}

	public Long getBlockId()
	{
		return blockId;
	}

	public void setBlockId(Long blockId)
	{
		this.blockId = blockId;
	}
}
