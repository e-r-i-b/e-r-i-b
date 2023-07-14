package com.rssl.phizicgate.rsV55.notification;

import com.rssl.phizic.gate.notification.StatusDocumentChangeNotification;
import com.rssl.phizicgate.rsV55.senders.ExternalKeyCreator;
import com.rssl.phizic.notifications.Notification;

/**
 * @author Novikov
 * @ created 23.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class StatusDocumentChangeNotificationImpl extends AbstractNotificationImpl implements StatusDocumentChangeNotification
{
	private Double   oldRest;
	private Double   currentRest;
	private Double   transactionSum;
    private Long     applicationKind;
	private String   applicationKey;
	private Double   newRest;
	private String   gstatus;
	private String   error;
	private String documentId;

	public double getOldRest()
	{
		return oldRest;
	}

	public void setOldRest(Double oldRest)
	{
		this.oldRest = oldRest;
	}

	public double getCurrentRest()
	{
		return currentRest;
	}

	public void setCurrentRest(Double currentRest)
	{
		this.currentRest = currentRest;
	}

	public double getTransactionSum()
	{
		return transactionSum;
	}

	public void setTransactionSum(Double transactionSum)
	{
		this.transactionSum = transactionSum;
	}

	public long getApplicationKind()
	{
		return applicationKind;
	}

	public void setApplicationKind(Long applicationKind)
	{
		this.applicationKind = applicationKind;
	}

	public String getApplicationKey()
	{
		return applicationKey;
	}

	public void setApplicationKey(String applicationKey)
	{
		this.applicationKey = applicationKey;
	}

	public double getNewRest()
	{
		return newRest;
	}

	public void setNewRest(Double newRest)
	{
		this.newRest = newRest;
	}

	public String getStatus()
	{
		return gstatus;
	}

	public void setStatus(String gstatus)
	{
		this.gstatus = gstatus;
	}

	public String getError()
	{
		return error;
	}

	public String getDocumentId()
	{
		ExternalKeyCreator creator = new ExternalKeyCreator(String.valueOf(applicationKind),applicationKey);
		return creator.createId();
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public void setDocumentId(String documentId)
	{
		this.documentId = documentId;
	}

	public Class<? extends Notification> getType()
	{
		return StatusDocumentChangeNotification.class;
	}
}
