package com.rssl.phizic.operations.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * �������� ��������� ������ ��������� ��� ����������� ���� ���.
 *
 * @author bogdanov
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class GetAllowedReissueCardOfficesOperation extends ListOfficesOperation
{
	/**
	 * ������������� ��������
	 */
	public void initialize(String filter) throws BusinessException, BusinessLogicException
	{
		offices = departmentService.getAllowedCreditCardOffices(filter);
	}
}
