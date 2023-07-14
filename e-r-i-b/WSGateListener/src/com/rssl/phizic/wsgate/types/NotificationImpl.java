package com.rssl.phizic.wsgate.types;

import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.notifications.Notification;

import java.util.Calendar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;

/**
 * @author hudyakov
 * @ created 06.10.2009
 * @ $Author$
 * @ $Revision$
 */

public class NotificationImpl implements Notification
{
	private String   accountNumber;
	private Double   rest;
	private Double   minRest;
	private Double   newRest;
	private Double   oldRest;
	private Double   currentRest;
	private Double   transactionSum;
	private String   documentId;
	private String   gstatus;
	private String   error;
	private Calendar dateCreated;
	private Class<? extends Notification> type;
	private Long id;

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public Double getRest()
	{
		return rest;
	}

	public void setRest(Double rest)
	{
		this.rest = rest;
	}

	public Double getMinRest()
	{
		return minRest;
	}

	public void setMinRest(Double minRest)
	{
		this.minRest = minRest;
	}

	public Double getNewRest()
	{
		return newRest;
	}

	public void setNewRest(Double newRest)
	{
		this.newRest = newRest;
	}

	public Double getOldRest()
	{
		return oldRest;
	}

	public void setOldRest(Double oldRest)
	{
		this.oldRest = oldRest;
	}

	public Double getCurrentRest()
	{
		return currentRest;
	}

	public void setCurrentRest(Double currentRest)
	{
		this.currentRest = currentRest;
	}

	public Double getTransactionSum()
	{
		return transactionSum;
	}

	public void setTransactionSum(Double transactionSum)
	{
		this.transactionSum = transactionSum;
	}

	public String getDocumentId()
	{
		return documentId;
	}

	public void setDocumentId(String documentId)
	{
		this.documentId = documentId;
	}

	public String getGstatus()
	{
		return gstatus;
	}

	public void setGstatus(String gstatus)
	{
		this.gstatus = gstatus;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public Long getId()
	{
		return id;
	}

	public Calendar getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(Calendar dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public Class<? extends Notification> getType()
	{
		return type;
	}

	public void setType(Class<? extends Notification> type)
	{
		this.type = type;
	}

	public void setType(String type)
	{
		try
		{
			setType((Class<? extends Notification>) Class.forName(type));
		}
		catch (ClassNotFoundException e)
		{
		}
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
