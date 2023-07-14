package com.rssl.phizic.web.security.node;

import org.apache.struts.action.ActionForm;

/**
 * @author mihaylov
 * @ created 08.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��������������� ����� �����
 */
public class SelfChangeNodeForm extends ActionForm
{
	private Long nodeId; //������������� ����� �� ������� ���������

	/**
	 * @return ������������� ����� �� ������� ���������
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * ���������� ������������� �����
	 * @param nodeId - �������������
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

}
