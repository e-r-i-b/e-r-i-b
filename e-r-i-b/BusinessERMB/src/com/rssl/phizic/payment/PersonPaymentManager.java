package com.rssl.phizic.payment;

/**
 * @author Erkin
 * @ created 22.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Персональный менеджер платежей
 */
public interface PersonPaymentManager
{
	/**
	 * Создать платёжную задачу "Перевод между своими счетами"
	 * @return новая задача ПЕРЕВОД (never null)
	 */
	InternalTransferTask createInternalTransferTask();

	/**
	 * Создать платёжную задачу "Перевод по номеру карты"
	 * @return новая задача ПЕРЕВОД (never null)
	 */
	CardTransferTask createCardTransferTask();

	/**
	 * Создать платёжную задачу "Перевод по номеру телефона"
	 * @return новая задача ПЕРЕВОД (never null)
	 */
	PhoneTransferTask createPhoneTransferTask();

	/**
	 * Создать платёжную задачу "Блокировка счёта"
	 * @return новая задача БЛОКИРОВКА (never null)
	 */
	LossPassbookTask createLossPassbookTask();

	/**
	 * Создать платежную задачу "Блокировка карты"
	 * @return новая задача БЛОКИРОВКА (never null)
	 */
	BlockingCardTask createBlockingCardTask();

	/**
	 * Создать платежную задачу "Оплата услуг (поставщики)"
	 * @return новая задача ОПЛАТА (never null)
	 */
	ServicePaymentTask createServicePaymentTask();

	/**
	 * Создать платежную задачу "Оплата телефона"
	 * @return новая задача опдата телефона
	 */
	RechargePhoneTask createRechargePhoneTask();

	/**
	 * Создать платежную задачу "Оплата услуг (шаблоны по поставщикам)"
	 * @return новая задача ОПЛАТА (never null)
	 */
	TemplatePaymentTask createTemplatePaymentTask();

	/**
	 * Создать платежную задачу "Оплата кредита"
	 * @return новая задача "Оплата кредита" (never null)
	 */
	LoanPaymentTask createLoanPaymentTask();

	/**
	 * Создать платежную задачу "Регистрация программы Спасибо от Сбербанка"
	 * @return новая задача "Регистрация программы Спасибо от Сбербанка" (never null)
	 */
	LoyaltyRegistrationPaymentTask createLoyalRegistrationPaymentTask();

	/**
	 * Создать платежную задачу "Создание автоплатежа"
	 * @return новая задача "Создание автоплатежа"  (never null)
	 */
	CreateAutoPaymentTask createAutoPaymentTask();

	/**
	 * Создать платежную задачу "Отключение автоплатежа"
	 * @return новая задача "Отключение автоплатежа"  (never null)
	 */
	RefuseAutoPaymentTask createRefuseAutoPaymentTask();

	/**
	 * Создать платежную задачу "Перевыпуск"
	 * @return новая задача "Перевыпуск" (never null)
	 */
	CardIssueTask createCardIssueTask();
}
