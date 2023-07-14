package com.rssl.phizic.operations.person.search.multinode;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author mihaylov
 * @ created 03.11.13
 * @ $Author$
 * @ $Revision$
 * ���������� ��������������� � ������������� ����� �����
 */
public class ChangeNodeLogicException extends BusinessLogicException
{
	private Long nodeId;//������������� �����, � ������� ���������� �������

	/**
	 * �����������
	 * @param message - ���������
	 */
	public ChangeNodeLogicException(String message)
	{
		super(message);
	}

	/**
	 * �����������
	 * @param message - ���������
	 * @param nodeId - ������������� �����, � ������� ���������� �������
	 */
	public ChangeNodeLogicException(String message, Long nodeId)
	{
		super(message);
		this.nodeId = nodeId;
	}

	/**
	 * @return ������������� �����, � ������� ���������� �������
	 */
	public Long getNodeId()
	{
		return nodeId;
	}
}
