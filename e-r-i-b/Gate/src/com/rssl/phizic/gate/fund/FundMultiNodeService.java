package com.rssl.phizic.gate.fund;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author osminin
 * @ created 17.09.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ � ��������� � ���������� ��������� �������� �� ���� ������� ����� �������
 */
public interface FundMultiNodeService extends Service
{
	/**
	 * ������� ������ �� ���� �������
	 * @param fundInfoList ������ ���������� � ������ �������
	 * @param nodeNumber ����� �����
	 * @return ������ ������� ��������������� ��������� �������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	List<String> createFundSenderResponses(List<FundInfo> fundInfoList, Long nodeNumber) throws GateException, GateLogicException;

	/**
	 * �������� ������ ��������� ������� �� ���� �������
	 * @param fundResponse ����� ����������� �����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void updateResponseInfo(Response fundResponse) throws GateException, GateLogicException;

	/**
	 * �������� ������ ��������� ������� �� ���� ������� � ������ ����������� ��������� �����
	 * @param fundResponse ����� ����������� �����
	 * @param requiredSum ����������� �����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void updateResponseInfoBySum(Response fundResponse, BigDecimal requiredSum) throws GateException, GateLogicException;

	/**
	 * ������� ������� �� ���� �������
	 * @param requests ������ �������� �� ��������
	 * @param nodeNumber ����� �����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void closeRequests(List<Request> requests, Long nodeNumber) throws GateException, GateLogicException;

	/**
	 * �������� ������ �������
	 * @param externalResponseId ������� ������������� ������
	 * @return ������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	RequestInfo getRequestInfo(String externalResponseId) throws GateException, GateLogicException;
}
