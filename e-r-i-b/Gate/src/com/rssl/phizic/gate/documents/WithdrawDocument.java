/***********************************************************************
 * Module:  WithdrawDocument.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface WithdrawDocument
 ***********************************************************************/

package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.Money;

/**
 * Отзыв документа.
 */
public interface WithdrawDocument extends SynchronizableDocument
{
   /**
    * Внешний ID отзываемого документа
    * Domain: ExternalID
    *
    * @return id
    */
   String getWithdrawExternalId();

   /**
    *  Установка внешнего ID отзываемого документа
    * @param withdrawExternalId
    */
   void setWithdrawExternalId(String withdrawExternalId);

   /**
    * Внутренний ID отзываемого документа
    * Domain: InternalID
    *
    * @return id
    */
   Long getWithdrawInternalId();
	/**
	 * Фактический тип отзываемого документа
	 *
	 * @return фактичский тип отзываемого документа
	 */
	Class<? extends GateDocument> getWithdrawType();

	/**
	 * Режим отзыва полный, частичный (например возврат части товаров из интернет-магазинов)
	 * @return режим отзыва
	 */
	WithdrawMode getWithdrawMode();

	/**
	 * Возвращает отзываемый платеж
    * @return Отзываемый платеж
    */
	GateDocument getTransferPayment();

	/**
	 * Возвращает списываемую на счет клиента сумму
    * @return сумма отзыва
    */
	Money getChargeOffAmount();

	/**
	 * получить идентификатор отозванного документа в биллинге
	 * @return идентификатор отозванного документа в биллинге
	 */
	String getIdFromPaymentSystem();

	/**
	 * установить идентификатор отозванного документа в биллинге
	 * @param id идентификатор отозванного документа в биллинге
	 */
	void setIdFromPaymentSystem(String id);

	/**
	 * Установить код авторизации
	 * @param authorizeCode код  авторизации
	 */
	void setAuthorizeCode(String authorizeCode);

	/**
	 * Получить код авторизации
 	 * @return код авторизации
	 */
	String getAuthorizeCode();
}