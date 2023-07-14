package com.rssl.phizic.gate.longoffer.autopayment;

import com.rssl.phizic.gate.longoffer.LongOffer;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 28.01.2011
 * @ $Author$
 * @ $Revision$
 * информация об автоплатежах
 */
public interface AutoPayment extends LongOffer
{
	/**
	 * @return Код сервиса (маршрута)
	 */
	public String getCodeService();

	/**
	 * Установить код сервиса (маршрута)
	 * @param codeService код сервиса
	 */
	void setCodeService(String codeService);

	/**
	 * @return состояние автоплатжа
	 */
	public AutoPaymentStatus getReportStatus();

	/**
	 * @return Дата оформления заявки на автоплатеж
	 */
	public Calendar getDateAccepted();

	/**
	* @return номер лицевого счета/телефона
	*/
	public String getRequisite();

}
