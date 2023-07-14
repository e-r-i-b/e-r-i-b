package com.rssl.phizic;

/**
 * @author Erkin
 * @ created 02.08.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� ��������
 */
public interface RequestProcessor<Rq extends Request, Rs extends Response>
{
	/**
	 * ���������� ������
	 * @param request - ������ (never null)
	 * @return �����
	 */
	Rs processRequest(Rq request) throws Exception;

	/**
	 * @return ��� ��������������� �������
	 */
	String getRequestName();
}
