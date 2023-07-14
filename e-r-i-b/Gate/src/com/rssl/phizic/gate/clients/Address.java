/***********************************************************************
 * Module:  Address.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Class Address
 ***********************************************************************/

package com.rssl.phizic.gate.clients;

import java.io.Serializable;

/**
 * Тип данных для представления адреса
 */
public interface Address extends Serializable
{
	/**
    * @return Почтовый индекс
    */
   String getPostalCode();

   /**
    * @return Область
    */
   String getProvince();

   /**
    * @return Район
    */
   String getDistrict();

   /**
    * @return Город
    */
   String getCity();

	/**
    * @return Населённый пункт
    */
   String getSettlement();

   /**
    * @return Улица
    */
   String getStreet();

   /**
    * @return Номер дома
    */
   String getHouse();

   /**
    * @return Номер корпуса
    */
   String getBuilding();

   /**
    * @return Номер квартиры
    */
   String getFlat();

   /**
    * @return Домашний телефон
    */
   String getHomePhone();

   /**
    * @return Рабочий телефон
    */
   String getWorkPhone();

   /**
    * @return Мобильный телефон
    */
   String getMobilePhone();

   /**
    * @return Мобильный оператор
    */
   String getMobileOperator();

	/**
	 * @return Полный адрес одной строкой. В таком виде, например, возвращается из шины для запроса CEDBO
	 */
	String getUnparseableAddress();
}