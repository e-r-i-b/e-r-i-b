package com.rssl.auth.csa.wsclient.requests;

import org.w3c.dom.Document;

/**
 * ������ � CSABack
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
	Document getBody();
}
