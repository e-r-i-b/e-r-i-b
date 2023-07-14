package com.rssl.phizic.common.types.basket;

/**
 * @author vagin
 * @ created 26.05.14
 * @ $Author$
 * @ $Revision$
 * Состояние инвойса.
 */
public enum InvoiceStatus
{
	NEW,      //новый счет к оплате
	PAID,     //оплаченный, ждем ответа от внешней системы о статусах платежа.
	INACTIVE, //удаленный, замененный счет к оплате, устаревший.
	DELAYED,  //отложеный
	ERROR     //ошибка
}
