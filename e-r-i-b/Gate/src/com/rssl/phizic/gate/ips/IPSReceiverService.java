package com.rssl.phizic.gate.ips;

import com.rssl.phizic.common.types.transmiters.GroupResult;
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
 * ������ ��� ����� ����������� �������� � ���
 */
public interface IPSReceiverService extends Service
{
	/**
	 * ������� ���������� ��������� ������ �� ��������� �������� �� ������
	 * @param result - ��������� ��������� � ������� �� ��������� ���������
	 * @return ������ ������, �������� �� ������� �� ������� �������
	 */
	List<IPSCardOperationClaim> receiveCardOperationClaimResult(GroupResult<IPSCardOperationClaim, List<IPSCardOperation>> result) throws GateException;

	/**
	 * ��������� ������ � ������ TIMEOUT
	 * @param claims - ������ ������
	 */
	void setTimeoutStatusClaims(List<IPSCardOperationClaim> claims) throws GateException;
}
