package com.rssl.phizic.ws.esberiblistener.depo;

import java.rmi.RemoteException;

/**
 * ��������� ����������� �������� ��������� �� ����������� �� ����
 *
 * @author gladishev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface EsbEribDepoBackService
{
	/**
	 * ��������� �������
	 * @param req - ������
	 * @return - �����
	 */
	public String doIFX(String req) throws RemoteException;
}
