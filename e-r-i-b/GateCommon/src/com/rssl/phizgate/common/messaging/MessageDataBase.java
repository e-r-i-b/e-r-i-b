package com.rssl.phizgate.common.messaging;

import com.rssl.phizic.gate.messaging.MessageData;

/**
 * @author Roshka
 * @ created 10.11.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class MessageDataBase implements MessageData
{
	private String id;
	private Long   entityId;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Long getEntityId()
	{
		return entityId;
	}

	public void setEntityId(Long entityId)
	{
		this.entityId = entityId;
	}
}