package com.rssl.phizic.business.ermb;

/**
 * Тип платежа в ЕРМБ
 * @author Rtischeva
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 */
public enum ErmbPaymentType
{
	SERVICE_PAYMENT,//оплата услуг

	TEMPLATE_PAYMENT, //оплата по шаблону

	RECHARGE_PHONE,//пополнение телефона

	LOAN_PAYMENT, //оплата кредита

	BLOCKING_CARD, //блокировка карты

	CARD_TRANSFER, //перевод по номеру карты

	PHONE_TRANSFER, //перевод по номеру телефона

	LOSS_PASSBOOK, //блокировка счета

	INTERNAL_TRANSFER, //перевод между своими счетами

	LOYALTY_PROGRAM_REGISTRATION_CLAIM, //регистрация в программе лояльности

	CREATE_AUTOPAYMENT, //создание автоплатежа

	REFUSE_AUTOPAYMENT, //отключение автоплатежа

	CARD_ISSUE // перевыпуск карты
}
