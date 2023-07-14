package com.rssl.phizic.business.documents.metadata.checkers;

import com.rssl.phizic.business.documents.payments.BusinessDocument;

import java.io.Serializable;

/**
 * @author Krenev
 * @ created 15.08.2007
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentChecker extends Serializable
{
	/**
	 * ���������� �������� ���������
	 * @param name ���
	 * @param value ��������
	 */
	void setParameter(String name, String value);

	/**
	 * �������� ��������
	 * @param name ��� ���������
	 * @return ��������
	 */
	String getParameter(String name);

	/**
	 * ��������� ��������
	 * @param doc ��������
	 * @return ��������� ��������
	 */
	boolean check(BusinessDocument doc);
}
