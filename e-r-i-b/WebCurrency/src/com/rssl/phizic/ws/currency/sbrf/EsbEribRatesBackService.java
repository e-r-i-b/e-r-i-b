package com.rssl.phizic.ws.currency.sbrf;

/**
 * ���������� �������� �������� �� ���� �� ���������� ������ �����
 *
 * @author gladishev
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface EsbEribRatesBackService
{
	/**
	 * ��������� �������
	 * @param request - ������
	 * @return - �����
	 */
	public String doIFX(String request) throws java.rmi.RemoteException;
}
