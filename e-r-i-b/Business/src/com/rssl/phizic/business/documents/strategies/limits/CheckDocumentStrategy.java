package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;

/**
 * ��������� ��������� �������� ���������� ���������
 *
 * @author khudyakov
 * @ created 08.11.13
 * @ $Author$
 * @ $Revision$
 */
public interface CheckDocumentStrategy extends ProcessDocumentStrategy
{
	/**
	 * �������� �� �������� � �������� ������
	 *
	 * �����������:
	 * 1. ��� �������� ���������� ������ �������, ��� ����������� �������� ���������� � ������������� ������,
	 * �� ������������
	 * 2. ���������� ���������� ���������������
	 *
	 * @param limitsInfo ���. ����������
	 * @return true - ��������
	 */
	boolean check(ClientAccumulateLimitsInfo limitsInfo) throws BusinessException, BusinessLogicException;

	/**
	 * �������� �� �������� � �������� ������
	 *
	 * �����������:
	 * 1. ��� �������� ���������� ������ �������, ��� ����������� �������� ���������� � ������������� ������,
	 * �� ������������
	 * 2. ���������� ���������� �� ���������������
	 *
	 * @param limitsInfo ���. ����������
	 * @return true - ��������
	 */
	boolean checkAndThrow(ClientAccumulateLimitsInfo limitsInfo) throws BusinessException, BusinessLogicException;
}
