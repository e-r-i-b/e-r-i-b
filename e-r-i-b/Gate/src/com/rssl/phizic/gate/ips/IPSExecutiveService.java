package com.rssl.phizic.gate.ips;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.List;

/**
 * @author Erkin
 * @ created 26.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ���������� �������� � ���
 */
public interface IPSExecutiveService extends Service
{
	/**
	 * ��������� ������ �� ��������� �������� �� ������
	 * ���������� ��������� IPSReceiverService
	 * @param claims - ������ ������
	 */
	void executeCardOperationClaims(List<IPSCardOperationClaim> claims) throws GateException;
}
