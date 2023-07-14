package com.rssl.phizic.gate.payments.systems;

import com.rssl.phizic.gate.documents.AbstractCardTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.logging.operations.context.PossibleAddingOperationUIDObject;

/**
 * @author krenev
 * @ created 02.12.2010
 * @ $Author$
 * @ $Revision$
 * Биллинговый платеж с карты
 */
public interface CardPaymentSystemPayment extends AbstractPaymentSystemPayment, AbstractCardTransfer, PossibleAddingOperationUIDObject
{
	/**
	 * @return данные о сотруднике создавшем платеж
	 */
	EmployeeInfo getCreatedEmployeeInfo() throws GateException;

	/**
	 * @return данные о сотруднике подтвердившем платеж
	 */
	EmployeeInfo getConfirmedEmployeeInfo() throws GateException;

	/**
	 * Возвращает признак, является ли данный документ оплатой интернет-заказа
	 * @return true - оплата интернет-заказа
	 */
	boolean isEinvoicing();
}
