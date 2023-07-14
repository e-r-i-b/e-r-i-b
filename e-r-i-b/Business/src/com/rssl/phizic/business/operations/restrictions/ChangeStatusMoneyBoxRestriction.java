package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.documents.payments.ChangePaymentStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;

/**
 * @author osminin
 * @ created 28.04.15
 * @ $Author$
 * @ $Revision$
 *
 * Ограничение на изменение статуса копилки
 */
public class ChangeStatusMoneyBoxRestriction implements Restriction
{
	/**
	 * Возможно ли изменить статус для копилки на changeStatus
	 * @param currentStatus текущий статус
	 * @param changeStatus новый статус
	 * @return Да, если возможно. Нет, в противном случае.
	 */
	public boolean accept(AutoPayStatusType currentStatus, ChangePaymentStatusType changeStatus)
	{
		boolean isPausedAndRecover = currentStatus == AutoPayStatusType.Paused && changeStatus == ChangePaymentStatusType.RECOVER;
		boolean isActiveAndRefuse = currentStatus == AutoPayStatusType.Active && changeStatus == ChangePaymentStatusType.REFUSE;
		boolean isClosed = changeStatus == ChangePaymentStatusType.CLOSE;

		return isPausedAndRecover || isActiveAndRefuse || isClosed;
	}
}
