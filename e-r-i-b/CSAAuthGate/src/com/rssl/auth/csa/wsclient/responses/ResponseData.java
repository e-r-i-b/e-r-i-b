package com.rssl.auth.csa.wsclient.responses;

import org.w3c.dom.Document;

/**
 * Ответ из CSABack
 * @author niculichev
 * @ created 29.08.2012
 * @ $Author$
 * @ $Revision$
 */
public interface ResponseData
{
	/**
	 * @return тело ответа
	 */
	Document getBody();
}
