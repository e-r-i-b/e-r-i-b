package com.rssl.phizic.gate.messaging;

/**
 * @author Evgrafov
 * @ created 06.03.2007
 * @ $Author: egorova $
 * @ $Revision: 7505 $
 */

public interface MessageData
{
	String getId();

	void setId(String id);

	Object getBody();

	void setBody(Object text);

	String getBodyAsString(String coding);

	Long getEntityId();

	void setEntityId(Long entityId);
}
