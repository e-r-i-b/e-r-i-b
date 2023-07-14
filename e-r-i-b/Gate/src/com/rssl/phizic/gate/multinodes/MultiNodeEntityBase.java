package com.rssl.phizic.gate.multinodes;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ������� �������� ��� ������� �� ���������� ������
 */
public abstract class MultiNodeEntityBase implements Serializable
{

	private Long nodeId;//������������� �����

	/**
	 * @return ������������� �����
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * ���������� ������������� �����
	 * @param nodeId - ������������� �����
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}
}
