package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.business.BusinessException;

/**
 * Ресурс с которого можно оплачивать товары и услуги
 * @author niculichev
 * @ created 30.11.2010
 * @ $Author$
 * @ $Revision$
 */
public interface PaymentAbilityERL<LinkedObject> extends ExternalResourceLink
{
	/**
	 * Остаток на ресурсе, который привязан к линку
	 */
	Money getRest();

	/**
	 *
	 * @return офис, в котором ведется внешний ресурс
	 */
	Office getOffice() throws BusinessException;

	/**
	* Максимальная сумма списания.
	* Нельзя совершить платеж больше этой суммы.
	* Если ограничений нет то == null
	*
	* @return Money
	*/
	Money getMaxSumWrite();

	/**
	 * @return объект, связанный с линком с данными, которые хранятся в бд.
	 */
	LinkedObject toLinkedObjectInDBView();
}
