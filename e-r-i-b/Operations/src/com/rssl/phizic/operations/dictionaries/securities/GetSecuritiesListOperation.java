package com.rssl.phizic.operations.dictionaries.securities;

import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.dictionaries.security.SecurityService;
import com.rssl.phizic.business.BusinessException;

import java.util.List;

/**
 * @author mihaylov
 * @ created 09.09.2010
 * @ $Author$
 * @ $Revision$
 */

// �������� ��������� ������ ������ ����� �� �����������
public class GetSecuritiesListOperation extends OperationBase implements ListEntitiesOperation 
{
	/**
	 * �������� ������ ����� ��, ������� ���� � �����������
	 * @return ������ ����� ��
	 * @throws BusinessException
	 */
	public List<String> getSecurityTypes() throws BusinessException
	{
		SecurityService service = new SecurityService();
		return service.getSecurityTypes();
	}

	/**
	 * �������� ������ ����� ��, ���������� �������� �������� ��������, ������� ���� � �����������.
	 * @return ������ ����� ��
	 * @throws BusinessException
	 */
	public List<String> getOpenSecurityTypes() throws BusinessException
	{
		SecurityService service = new SecurityService();
		List<String> securityTypes = service.getOpenSecurityTypes();
		if (securityTypes.isEmpty())
		{
			securityTypes.add("");
		}
		return securityTypes;
	}
}
