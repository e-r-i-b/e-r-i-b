package com.rssl.phizicgate.rsretailV6r20.notification;

import com.rssl.phizic.gate.notification.StatusDocumentChangeNotification;
import com.rssl.phizicgate.rsretailV6r20.senders.ExternalKeyCreator;
import com.rssl.phizic.notifications.Notification;

/**
 * @author Krenev
 * @ created 20.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class StatusDocumentChangeNotificationImpl extends NotificationBase implements StatusDocumentChangeNotification
{
	private long applicationKind;
	private String applicationKey;
	private String status;
	private String error;

	public Class<? extends Notification> getType()
	{
		return StatusDocumentChangeNotification.class;
	}

	public String getDocumentId()
	{
		ExternalKeyCreator creator = new ExternalKeyCreator(String.valueOf(applicationKind),applicationKey);
		return creator.createId();
	}

	public long getApplicationKind()
	{
		return applicationKind;
	}

	public String getApplicationKey()
	{
		return applicationKey;
	}

	public String getStatus()
	{
		return status;
	}

	public String getError()
	{
		return error;
	}

	public void setApplicationKind(long applicationKind)
	{
		this.applicationKind = applicationKind;
	}

	public void setApplicationKey(String applicationKey)
	{
		this.applicationKey = applicationKey;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public void setError(String error)
	{
		this.error = error;
	}
}
