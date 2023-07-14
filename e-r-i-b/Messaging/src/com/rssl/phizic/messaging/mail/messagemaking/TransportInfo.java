package com.rssl.phizic.messaging.mail.messagemaking;

/**
 * Настройки транспорта
 * @author Evgrafov
 * @ created 23.06.2006
 * @ $Author: kosyakov $
 * @ $Revision: 3224 $
 */
public interface TransportInfo
{
	String getTransportName();
	Object getAttribute(String name);
}
