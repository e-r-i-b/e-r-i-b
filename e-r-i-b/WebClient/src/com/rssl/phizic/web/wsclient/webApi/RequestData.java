package com.rssl.phizic.web.wsclient.webApi;

import org.w3c.dom.Document;

/**
 * ������ � WebAPI
 * @author Jatsky
 * @ created 22.04.14
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
	Document getBody();
}
