package com.rssl.phizgate.messaging.internalws.client;

import org.w3c.dom.Document;

/**
 * @author gladishev
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */
public interface ErrorHandler
{
	void process(String errorCode, String errorDescription, Document document) throws Exception;
}
