package com.rssl.auth.csa.back.exceptions;

import com.rssl.auth.csa.back.servises.connectors.CSAConnector;

import java.util.List;
import java.util.Collections;

/**
 * @author krenev
 * @ created 20.09.2012
 * @ $Author$
 * @ $Revision$
 * ����������, ��������������� � ������� ���������� CSA-����������� ��� �������.
 */
public class TooManyCSAConnectorsException extends RestrictionException
{
	private final List<CSAConnector> connectors;

	public TooManyCSAConnectorsException(List<CSAConnector> connectors)
	{
		super("��� ������� " + connectors.get(0).getProfile().getId() + " ���������� " + connectors.size() + " �����������");
		this.connectors = connectors;
	}

	/**
	 * @return ������ ���������� 
	 */
	public List<CSAConnector> getConnectors()
	{
		return Collections.unmodifiableList(connectors);
	}
}