package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.BusinessException;

/**
 * @author Gulov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ����������� �������� ������ ����.
 */
public interface MenuLinkCondition
{
	/**
	 * ��������� ������� ����������� �������� ������ ����.
	 * @return true - ����������, false - ���
	 * @throws BusinessException
	 */
	boolean accept() throws BusinessException;
}
