package com.rssl.auth.csa.wsclient;

import com.rssl.auth.csa.utils.CSAResponseUtils;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * @author akrenev
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � �������
 */

public class NodeAvailabilityInfoService
{
	/**
	 * ��������� ���������� � ��������� ������
	 * @return ���������� � ��������� ������
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public List<NodeInfo> getAllNodesAvailabilityInfo() throws GateLogicException, GateException
	{
		try
		{
			return CSAResponseUtils.getNodesInfo();
		}
		catch (BackLogicException e)
		{
			throw new GateLogicException("������ ��������� ���������� � ��������� ������.", e);
		}
		catch (BackException e)
		{
			throw new GateException("������ ��������� ���������� � ��������� ������.", e);
		}
		catch (Exception e)
		{
			throw new GateException("������ ��������� ���������� � ��������� ������.", e);
		}
	}

	/**
	 * ��������� ���������� � ��������� ������
	 * @param newNodesAvailabilityInfo ����� ����������
	 * @return ���������� � ��������� ������
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public List<NodeInfo> changeAllNodesAvailabilityInfo(List<NodeInfo> newNodesAvailabilityInfo) throws GateLogicException, GateException
	{
		try
		{
			return CSAResponseUtils.parseNodeInfo(CSABackRequestHelper.changeNodesAvailabilityInfo(newNodesAvailabilityInfo));
		}
		catch (BackLogicException e)
		{
			throw new GateLogicException("������ ��������� ���������� � ��������� ������.", e);
		}
		catch (BackException e)
		{
			throw new GateException("������ ��������� ���������� � ��������� ������.", e);
		}
		catch (Exception e)
		{
			throw new GateException("������ ��������� ���������� � ��������� ������.", e);
		}
	}
}
