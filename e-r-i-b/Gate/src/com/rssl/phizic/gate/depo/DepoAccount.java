package com.rssl.phizic.gate.depo;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

public interface DepoAccount extends Serializable
{
	/**
	 * Внешний ID счета ДЕПО
	 * @return Id
	 */
	String getId();

	/**
	 * Статус счета ДЕПО клиента
	 * @return DepoAccountState
	 */
    DepoAccountState getState();

	/**
	 * Номер счета ДЕПО клиента
	 * @return AccountNumber
	 */
	String getAccountNumber();

	/**
	 * Номер договора
	 * @return AgreementNumber
	 */
	String getAgreementNumber();

	/**
	 * Дата договора
	 * @return agreementDate
	 */
	Calendar getAgreementDate();

	/**
	 * Общая сумма задолженности
	 * @return debt
	 */
	Money getDebt();

	/**
	 * Разрешены ли операции со счетом
	 * @return isOperationAllowed
	 */
	boolean getIsOperationAllowed();

	/**
	 * Офис в котором заведен счет
	 * @return office
	 */
	Office getOffice();
}
