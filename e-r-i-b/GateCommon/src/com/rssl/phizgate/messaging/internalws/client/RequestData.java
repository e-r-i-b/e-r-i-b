package com.rssl.phizgate.messaging.internalws.client;

import org.w3c.dom.Document;

/**
 * ������
 * @author niculichev
 * @ created 29.08.2012
 * @ $Author$
 * @ $Revision$
 */
public interface RequestData
{
	/**
	 * @return ��� �������
	 */
	String getName();

	/**
	 * @return ���� ������� ����� ������
	 */
	Document getBody() throws Exception;
}
