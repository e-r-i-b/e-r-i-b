package com.rssl.phizic.business.documents.payments;

import java.util.Calendar;

/**
 * Сущность истории статусов документа
 * @author Rtischeva
 * @ created 18.05.15
 * @ $Author$
 * @ $Revision$
 */
public class BusinessDocumentStatusEntry
{
	private Calendar statusChangedDate;

	private String statusCode;

	public BusinessDocumentStatusEntry(Calendar statusChangedDate, String statusCode)
	{
		this.statusChangedDate = statusChangedDate;
		this.statusCode = statusCode;
	}

	public Calendar getStatusChangedDate()
	{
		return statusChangedDate;
	}

	public String getStatusCode()
	{
		return statusCode;
	}
}
