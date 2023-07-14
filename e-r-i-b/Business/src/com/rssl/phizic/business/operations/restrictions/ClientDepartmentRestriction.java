package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author mihaylov
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������, �� �� ��� ���� ������� ��������� � ������ ��������� ������������� � ����������
 */
public class ClientDepartmentRestriction implements ClientRestriction
{
	public boolean accept(Client client) throws BusinessException
	{
		if (client == null)
			return true;

		Office office = client.getOffice();
		if (office == null)
			return true;

		return AllowedDepartmentsUtil.isDepartmentAllowedInNode(office.getCode());
	}
}
