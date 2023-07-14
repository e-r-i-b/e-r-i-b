package com.rssl.phizic.payment;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.task.ExecutableTask;

/**
 * @author Erkin
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ������
 */
public interface PaymentTask extends ExecutableTask
{
	/**
	 * �������� ������������� ��������
	 * @param <T>
	 * @return ������������� ��������
	 */
	<T extends BusinessDocument> T getDocument();

	/**
	 * �������� ����������� ����� (���������� null, ���� �� ���� ����� �� ��������)
	 * @return ����������� �����
	 */
	Limit getCurrentLimit();

	/**
	 * �������� ����������� ����� �� ������������ ������ (���������� null, ���� �� ���� ����� �� ��������)
	 * @return ����������� ����� �� ������������ ������
	 */
	Money getAccumulatedAmount();
}
