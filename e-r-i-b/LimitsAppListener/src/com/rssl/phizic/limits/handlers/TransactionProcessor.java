package com.rssl.phizic.limits.handlers;

import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��� ��������� �������� � ����������� ����������
 */
public interface TransactionProcessor
{
	/**
	 * ���������
	 * @param request ������
	 */
	void process(Document request) throws Exception;
}
