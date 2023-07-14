package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.common.types.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Информация о заказе.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface ShopOrder extends Serializable
{
	/**
	* @return Идентификатор (PK)
	*/
	public Long getId();

	/**
	* @return Внешний идентификатор заказа в ЕРИБ
	*/
	public String getUuid();

	/**
	* @return Внутренний идентификатор заказа у получателя
	*/
	public String getExternalId();

	/**
	* @return Partial, full,offline
	*/
	public TypeOrder getType();

	/**
	* @return Дата регистрации заказа
	*/
	public Calendar getDate();

	/**
	* @return Внутренний статус заказа
	*/
	public OrderState getState();

	/**
	* @return профиль клиента, связанного с данным заказом
	*/
	public ShopProfile getProfile();

	/**
	* @return номер телефона клиента для MobileCheckout
	*/
	public String getPhone();

	/**
	* @return Код получателя
	*/
	public String getReceiverCode();

	/**
	* @return Наименование получателя (для отображения пользователю)
	*/
	public String getReceiverName();

	/**
	* @return Сумма заказа.
	*/
	public Money getAmount();

	/**
	* @return Описание заказа
	*/
	public String getDescription();

	/**
	* @return Номер счета получателя
	*/
	public String getReceiverAccount();

	/**
	* @return БИК банка получателя
	*/
	public String getBic();

	/**
	* @return кор. счет банка получателя
	*/
	public String getCorrAccount();

	/**
	* @return ИНН получателя
	*/
	public String getInn();

	/**
	* @return КПП получателя
	*/
	public String getKpp();

	/**
	* @return информация о заказе для печатной формы
	*/
	public String getPrintDescription();

	/**
	* @return url редиректа для перехода после оплаты заказа (для partial/full)
	*/
	public String getBackUrl();

	/**
	* @return Номер блока, в котором инициирована оплата заказа
	*/
	public Long getNodeId();

	/**
	* @return Идентификатор документа оплаты заказа в биллинге
	*/
	public String getUtrrno();

	/**
	 * @return вид заказа (интренет-магазин, аэрофлот)
	 */
	public OrderKind getKind();

	/**
	 * @return является ли заказ, заказом через MC.
	 */
	public boolean isMobileCheckout();

	public Calendar getDelayedPayDate();

	public Boolean getIsNew();

	public void setIsNew(Boolean isNew);

	/**
	 * @return код конечного поставщика за фасилитатором
	 */
	public String getFacilitatorProviderCode();
}
