package com.rssl.phizic.business.limits;

import com.rssl.phizic.business.BusinessException;

import java.util.List;

/**
 * @author basharin
 * @ created 06.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class GroupRiskService extends MultiInstanceGroupRiskService
{
	/**
	 * ���������� ��� ������ ����� � ������� ����
	 * @return ���� ����� �����
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<GroupRisk> getAllGroupsRisk() throws BusinessException
	{
		return getAllGroupsRisk(null);
	}

	//�������� ������ ����� � ��������� "�� ����������"
	public GroupRisk getDefaultGroupRisk() throws BusinessException
	{
         return getDefaultGroupRisk(null);
	}
}
