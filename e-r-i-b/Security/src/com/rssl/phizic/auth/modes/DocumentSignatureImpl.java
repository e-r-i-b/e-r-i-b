package com.rssl.phizic.auth.modes;

import com.rssl.common.forms.doc.DocumentSignature;

import java.util.Calendar;

/**
 * @author Evgrafov
 * @ created 22.01.2007
 * @ $Author: emakarov $
 * @ $Revision: 9379 $
 */

public class DocumentSignatureImpl implements DocumentSignature
{
	private Long id;
	private String text;
	private String operationId;
	private String sessionId;
	private Calendar checkDate;

	public Calendar getCheckDate()
	{
		return checkDate;
	}

	public void setCheckDate(Calendar checkDate)
	{
		this.checkDate = checkDate;
	}

	public String getOperationId()
	{
		return operationId;
	}

	public void setOperationId(String operationId)
	{
		this.operationId = operationId;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	/**
	 * @return ID
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ID
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return текст подписи
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param text текст подписи
	 */
	public void setText(String text)
	{
		this.text = text;
	}
}