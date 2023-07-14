package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.documents.payments.ChangePaymentStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;

/**
 * @author osminin
 * @ created 28.04.15
 * @ $Author$
 * @ $Revision$
 *
 * ����������� �� ��������� ������� �������
 */
public class ChangeStatusMoneyBoxRestriction implements Restriction
{
	/**
	 * �������� �� �������� ������ ��� ������� �� changeStatus
	 * @param currentStatus ������� ������
	 * @param changeStatus ����� ������
	 * @return ��, ���� ��������. ���, � ��������� ������.
	 */
	public boolean accept(AutoPayStatusType currentStatus, ChangePaymentStatusType changeStatus)
	{
		boolean isPausedAndRecover = currentStatus == AutoPayStatusType.Paused && changeStatus == ChangePaymentStatusType.RECOVER;
		boolean isActiveAndRefuse = currentStatus == AutoPayStatusType.Active && changeStatus == ChangePaymentStatusType.REFUSE;
		boolean isClosed = changeStatus == ChangePaymentStatusType.CLOSE;

		return isPausedAndRecover || isActiveAndRefuse || isClosed;
	}
}
