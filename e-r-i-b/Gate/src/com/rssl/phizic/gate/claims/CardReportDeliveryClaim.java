package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.bankroll.ReportDeliveryLanguage;
import com.rssl.phizic.gate.bankroll.ReportDeliveryType;
import com.rssl.phizic.gate.documents.SynchronizableDocument;

/**
 * @author akrenev
 * @ created 05.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Заявка на изменение параметров подписки по карте
 */

public interface CardReportDeliveryClaim extends SynchronizableDocument
{
	/**
	 * @return внешний идентификатор клиента
	 */
	public String getCardClientIdReportDelivery();

	/**
	 * @return внешний идентификатор карты
	 */
	public String getCardExternalIdReportDelivery();

	/**
	 * @return включена ли подписка
	 */
	public boolean isUseReportDelivery();

	/**
	 * @return адрес подписки
	 */
	public String getEmailReportDelivery();

	/**
	 * @return тип данных отчета
	 */
	public ReportDeliveryType getReportDeliveryType();

	/**
	 * @return язык отчета
	 */
	public ReportDeliveryLanguage getReportDeliveryLanguage();

	/**
	 * @return номер договора
	 */
	public String getContractNumber();
}
