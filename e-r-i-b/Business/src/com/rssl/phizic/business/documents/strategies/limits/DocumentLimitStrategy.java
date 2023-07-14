package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.common.types.Money;

/**
 * ��������� ������ �� �����������, ������������ ������ �� ���������
 *
 * @author khudyakov
 * @ created 10.04.2012
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentLimitStrategy extends CheckDocumentStrategy
{
	/**
	 * @return ����������� �����
	 */
	Limit getCurrentLimit();

	/**
	 * @return �����, ������� ����� �������� �� ������� ������ � ������ ������
	 */
	Money getAvailableAmount();

	/**
	 * @return ����� �� ������, ����������� �� 24 ���� ��������
	 * (�������� ������������ ������ ��� ������� �� ����� ��������)
	 */
	Money getAccumulatedAmount();
}
