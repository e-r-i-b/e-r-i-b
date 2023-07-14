package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.TooManyCSAConnectorsException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.operations.UserLogonOperation;

import java.util.List;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 * ����������� �� ���������� �������� ����������� ��� �����
 */
public class MultipleConnetorsRestriction implements OperationRestriction<UserLogonOperation>
{
	private static final MultipleConnetorsRestriction INSTANCE = new MultipleConnetorsRestriction();

	/**
	 * @return ������� �����������
	 */
	public static MultipleConnetorsRestriction getInstance()
	{
		return INSTANCE;
	}

	public void check(UserLogonOperation operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("�������� �� ����� ���� null");
		}
		Connector connector = operation.getConnector(null);
		if (!(connector instanceof CSAConnector))
		{
			//��� ��������� ���������� ��������� ������ � ��� �����������. ��� ��������� ����������.
			return;
		}
		//�������� �������� CSA-���������� ������������.
		List<CSAConnector> connecors = CSAConnector.findNotClosedByProfileID(connector.getProfile().getId());
		//�� ��������� �� ��?
		if (connecors.size()>1)
		{
			//���������. ������� ����������. ����������� ���������.
			throw new TooManyCSAConnectorsException(connecors);
		}
	}
}