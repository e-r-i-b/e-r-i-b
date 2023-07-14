package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.depo.TransferSubOperation;
import com.rssl.phizic.gate.depo.TransferOperation;
import com.rssl.phizic.gate.depo.DeliveryType;

/**
 * @author mihaylov
 * @ created 26.08.2010
 * @ $Author$
 * @ $Revision$
 */

public interface SecuritiesTransferClaim extends DepoAccountClaimBase
{

	/**
	 * “ип операции
	 * 231 Ц ѕеревод,
	 * 240 Ц ѕрием,
	 * 220 Ц ѕеревод между разделами счета депо
	 * @return operType
	 */
	TransferOperation getOperType();

	/**
	 * —одержание операции, зависит от типа операции
	 * 231
	 *      INTERNAL_TRANSFER Ц перевод на счет депо внутри депозитари€,
	 *		LIST_TRANSFER Ц перевод на счет в реестре,
	 *		EXTERNAL_TRANSFER Ц перевод на счет в другом депозитарии.
	 * 240
	 *      INTERNAL_RECEPTION Ц прием перевода со счета депо внутри депозитари€,
	 *      LIST_RECEPTION Ц прием перевода со счета в реестре,
	 *      EXTERNAL_RECEPTION Ц прием перевода со счета в другом депозитарии.
	 *
	 * 220
	 *       INTERNAL_ACCOUNT_TRANSFER Ц перевод между разделами счета депо
	 * @return operationSubType
	 */
	TransferSubOperation getOperationSubType();

	/**
	 *  омментарий к операции
	 * @return operationDesc
	 */
	String getOperationDesc();

	/**
	 * @return тип раздела счета ƒ≈ѕќ
	 */
	String getDivisionType();

	/**
	 * “ип и номер раздела счета депо
	 * @return divisionNumber
	 */
	String getDivisionNumber();

	/**
	 * ƒепозитарный  код выпуска ценной бумаги
	 * @return insideCode
	 */
	String getInsideCode();

	/**
	 *  ол-во ценных бумаг
	 * @return securityCount
	 */
	Long getSecurityCount();

	/**
	 * ќснование операции
	 * @return operationReason
	 */
	String getOperationReason();

	/**
	 * Ќаименование депозитари€ получател€, указываетс€ только дл€ операций
	 * 231 Ц EXTERNAL_TRANSFER и 240 Ц EXTERNAL_RECEPTION
	 * @return corrDepositary
	 */
	String getCorrDepositary();

	/**
	 * Ќомер счета депо на который осуществить перевод или с которого необходимо прин€ть ценные бумаги.
	 * ѕоле не об€зательно дл€ операции 220.
	 * @return corrDepoAcctountId
	 */
	String getCorrDepoAccount();

	/**
	 * ¬ладелец счета депо на который осуществить перевод или с которого необходимо прин€ть ценные бумаги.
	 * @return corrDepoAccountOwner
	 */
	String getCorrDepoAccountOwner();

	/**
	 * ƒополнительные реквизиты
	 * @return additionalInfo
	 */
	String getAdditionalInfo();

	/**
	 * —пособ доставки сертификатов
	 * @return deliveryType
	 */
	DeliveryType getDeliveryType();

	/**
	 * –егистрационный номер ценной бумаги
	 * @return registrationNumber
	 */
	String getRegistrationNumber();
}
