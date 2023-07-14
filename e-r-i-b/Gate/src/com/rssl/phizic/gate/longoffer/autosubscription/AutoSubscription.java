package com.rssl.phizic.gate.longoffer.autosubscription;

import com.rssl.phizic.common.types.LongOfferPayDay;
import com.rssl.phizic.gate.longoffer.LongOffer;

import java.util.Calendar;

/**
 * @author: vagin
 * @ created: 19.01.2012
 * @ $Author$
 * @ $Revision$
 * Подписка на автоплатеж
 */
public interface AutoSubscription extends LongOffer
{
	/**
	 * @return дата следующего платежа
	 */
	public Calendar getNextPayDate();

	/**
	 * @return день оплаты для регулярных актоподписок.
	 */
	public LongOfferPayDay getLongOfferPayDay();

	/**
	 * Статус автоплатежа.
	 *
	 * @return статус.
	 */
	AutoPayStatusType getAutoPayStatusType();

	/**
	 * сеттим новый статус аавтоплатежа
	 * @param autoPayStatusType установить статус автоподписки
	 */
	void setAutoPayStatusType(AutoPayStatusType autoPayStatusType);

	/**
	 * @return номер документа в ЕРИБ
	 */
	String getDocumentNumber();

	/**
	 * @return номер карты получателя
	 */
	String getReceiverCard();

	/**
	 * @return Номер Автоплатежа, печатаемый в чеке.
	 */
	String getAutopayNumber();

	/**
	 * Установка номера Автоплатежа.
	 * @param number - номер
	 */
	void setAutopayNumber(String number);
}
