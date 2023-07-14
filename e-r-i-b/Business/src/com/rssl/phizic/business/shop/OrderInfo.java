package com.rssl.phizic.business.shop;

/**
 * @author Mescheryakova
 * @ created 06.12.2010
 * @ $Author$
 * @ $Revision$
 */

/*
Интерфейс должны реализовывать все типы внешних заказов (ФНС)
 */
public interface OrderInfo
{
	/**
	 * получить основные поля заказа
	 */
	Order getOrder();

	/**
	 * Задать основные поля заказа
	 * @return
	 */
	void setOrder(Order order);

	/*
	 * получить id заказа
	 */
	Long getId();
}