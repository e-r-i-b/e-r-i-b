package com.rssl.phizic.web.actions.payments.forms;

/**
 * ��������� ��� ����, ���������� ������� (�� ���������� �� ������) � ����������� (���������� � ������ ������) ������������� �������
 * @ author: Gololobov
 * @ created: 21.06.14
 * @ $Author$
 * @ $Revision$
 */
public interface ContainRegionGuidActionFormInterface
{
	//������������� �������
	Long getRegionId();
	//����������� ������������� �������
	String getRegionGuid();

	void setRegionId(Long regionId);
	void setRegionGuid(String regionGuid);
}
