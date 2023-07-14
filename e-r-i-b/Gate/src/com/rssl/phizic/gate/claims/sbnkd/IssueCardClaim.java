package com.rssl.phizic.gate.claims.sbnkd;

import com.rssl.phizic.logging.operations.context.PossibleAddingOperationUIDObject;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * Заявка на выпуск карты.
 *
 * @author bogdanov
 * @ created 15.12.14
 * @ $Author$
 * @ $Revision$
 */

public interface IssueCardClaim extends CreateCardContractDocument, ConfirmableObject, PossibleAddingOperationUIDObject
{
	/**
	 * @return идентификатор системы, предоставляющей услугу (провайдер сервиса).
	 */
    String getSystemId();

	/**
	 * @param systemId идентификатор системы, предоставляющей услугу (провайдер сервиса).
	 */
	void setSystemId(String systemId);

	/**
	 * @param messageDeliveryType Способ отправки отчетов по счету карты.
	*/
	public void setMessageDeliveryType(String messageDeliveryType);
	/**
	 * @return Способ отправки отчетов по счету карты.
	*/
	public String getMessageDeliveryType();

	/**
	 * @return Номер стадии заполнения заявки
	 */
	public int getStageNumber();

	/**
	 * @param stageNumber Номер стадии заполнения заявки
	 */
	public void setStageNumber(int stageNumber);

	/**
	 * @param cardAcctAutoPayInfo Информация об автоплатеже.
	 */
	public void setCardAcctAutoPayInfo(double cardAcctAutoPayInfo);

	/**
	 * @return Информация об автоплатеже.
	 */
	public double getCardAcctAutoPayInfo();
}
