package com.rssl.phizic.business.operations.background;

import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author krenev
 * @ created 19.07.2011
 * @ $Author$
 * @ $Revision$
 * ��������, ������� ����� ����������� � ����
 */
public interface BackgroundOperation<TR extends TaskResult, T extends BackgroundTask<TR>, R extends Restriction> extends Operation<R>
{
	/**
	 * ������������������� �������� ������� �� ������� ������
	 * @param backroundTask ������� ������
	 */
	void initialize(T backroundTask) throws BusinessException, BusinessLogicException;

	/**
	 * ������� � ���������������� ������� ������.
	 * ��� ���������������� ���������, ����������� ��� ���������� ������ ������ ������� ��
	 * ��������������������� �������� � ����������� � ������
	 * @return ������� ������, �������������������� ����������������� �������.
	 */
	T createBackroundTask() throws BusinessException, BusinessLogicException;

	/**
	 * ��������� ��������(��������).
	 * ������ ����� ������������� ������ ������ ����������� �������� � ������������ ��� ��� ������,
	 * ��� � ��� ������� ����������.
	 * @return ��������� ���������� ��������
	 */
	TR execute() throws BusinessException, BusinessLogicException;
}
