package com.rssl.phizic.gate.pfr;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.claims.pfr.PFRStatementClaim;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.rmi.RemoteException;

/**
 * @author Erkin
 * @ created 04.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ������ � ��� (���������� ������ ��)
 */
public interface PFRService extends Service
{
	/**
	 * �������� ����� ����������� �������
	 * @param claim - ������ �� �������
	 * @return �������
	 *  null => ���� �������� � ����������, ��. ������ isReady() ��� <claim>
	 * @throws GateLogicException
	 * @throws GateException
	 * @throws RemoteException ���� ����� ����� 
	 */
	String getStatement(PFRStatementClaim claim) throws GateLogicException, GateException, RemoteException;
}
