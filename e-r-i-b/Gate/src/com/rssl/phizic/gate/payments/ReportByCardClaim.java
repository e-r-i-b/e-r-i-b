package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.documents.Claim;

import java.util.Calendar;

/**
 * Заявка на получение выписке по карте на e-mail
 *
 * @author bogdanov
 * @ created 28.05.15
 * @ $Author$
 * @ $Revision$
 */

public interface ReportByCardClaim extends Claim
{
	/**
	 * @return e-mail адрес для доставки выписки по карте.
	 */
	public String getEmailAddress();

	/**
	 * @return формат выписки по карте.
	 */
	public String getReportFormat();

	/**
	 * @return язык выписки по карте.
	 */
	public String getReportLang();

	/**
	 * @return тип выписки (полная, только операции).
	 */
	public String getReportOrderType();

	/**
	 * @return дата начала периода выписки (для полной выписки содержит месяц и год).
	 */
	public Calendar getReportStartDate();

	/**
	 * @return дата окончания периода выписки (не используется в полной выписке).
	 */
	public Calendar getReportEndDate();

	/**
	 * @return форматировананя дата для начала периода выписки.
	 */
	public String getReportStartDateFormated();

	/**
	 * @return форматировананя дата для окончания периода выписки.
	 */
	public String getReportEndDateFormated();

	/**
	 * @return карта.
	 */
	public String getCardNumber();

	/**
	 * @return номер карточного контракта
	 */
	public String getContractNumber();
}
