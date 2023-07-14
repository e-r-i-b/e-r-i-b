package com.rssl.phizic.business.documents;

/**
 * @author akrenev
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �������, ��������� ���� � �����
 */

public interface NodeInfoContainer
{
	/**
	 * @return ������������� ���������� �����, � ������� ��������� ��� ���������� ��������
	 */
	public Long getTemporaryNodeId();

	/**
	 * �������� ������������� ���������� �����, � ������� ��������� ��� ���������� ��������
	 * @param temporaryNodeId �������������
	 */
	public void setTemporaryNodeId(Long temporaryNodeId);
}
