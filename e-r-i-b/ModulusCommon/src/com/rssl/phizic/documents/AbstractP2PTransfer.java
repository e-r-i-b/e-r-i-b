package com.rssl.phizic.documents;

import com.rssl.common.forms.DocumentException;

/**
 * Итерфейс перевода частному лицу
 *
 * @author khudyakov
 * @ created 17.11.14
 * @ $Author$
 * @ $Revision$
 */
public interface AbstractP2PTransfer extends AbstractPaymentTransfer
{
	/**
	 * Тип получателя
	 * @return физик/юрик (PHIZ_RECEIVER_TYPE_VALUE/JUR_RECEIVER_TYPE_VALUE)
	 */
	String getReceiverType();

	/**
	 * Подтип получателя
	 * @return на счет в Сбербанке/на карту в Сберанке/на счет в другом банке
	 */
	String getReceiverSubType();

	/**
	 * @return Карта зачисления.
	 */
	public String getReceiverCard();

	/**
	 * @return номер мобильного телефона
	 */
	public String getMobileNumber();

	/**
	 * @return номер телефона из адресной книги
	 */
	public String getContactPhone() throws DocumentException;

	/**
	 * @return true -- переврд на карту другого банка на самом деле является переводом в сбербанк
	 */
	public boolean isOurBankCard();

	/**
	 * Возвращает текст для смс-сообщения получателю перевода, если он задан на форме редактирования
	 * @return текст для смс-сообщения или пустая строка
	 */
	public String getMessageToReceiver();

	/**
	 * Устанавливает текст для смс-сообщения получателю перевода, если он задан на форме редактирования
	 * @param status статус
	 */
	public void setMessageToReceiverStatus(String status);
}
