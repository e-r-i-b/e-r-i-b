package com.rssl.phizic.business.mail;

/**
 * @author Gainanov
 * @ created 26.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class Recipient
{
	private Long           id;
	private Long           recipientId;
	private RecipientType  recipientType;
	private String         recipientName;
	private Boolean        deleted = false;
	private RecipientMailState recipientMailState = RecipientMailState.NEW;
	private Long mailId;

	/**
	 * ¬озвращает статус письма
	 * @return статус письма
	 */
	public RecipientMailState getState()
	{
		return recipientMailState;
	}

	/**
	 * ”станавливает статус письма
	 * @param stateRecipient статус письма
	 */
	public void setState(RecipientMailState stateRecipient)
	{
		this.recipientMailState = stateRecipient;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getRecipientId()
	{
		return recipientId;
	}

	public void setRecipientId(Long recipientId)
	{
		this.recipientId = recipientId;
	}

	public RecipientType getRecipientType()
	{
		return recipientType;
	}

	public void setRecipientType(RecipientType recipientType)
	{
		this.recipientType = recipientType;
	}

	public String getRecipientName()
	{
		return recipientName;
	}

	public void setRecipientName(String recipientName)
	{
		this.recipientName = recipientName;
	}

	public Boolean getDeleted()
	{
		return deleted;
	}

	public void setDeleted(Boolean deleted)
	{
		this.deleted = deleted;
	}

	public Long getMailId()
	{
		return mailId;
	}

	public void setMailId(Long mailId)
	{
		this.mailId = mailId;
	}
}
