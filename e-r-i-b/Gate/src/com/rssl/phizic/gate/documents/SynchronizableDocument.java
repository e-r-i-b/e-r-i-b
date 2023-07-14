/***********************************************************************
 * Module:  SynchronizableDocument.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface SynchronizableDocument
 ***********************************************************************/

package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.documents.State;

import java.util.Calendar;

/**
 * Интерфейс для документов, имеющих аналоги в гейте и требующих синхронизации
 */
public interface SynchronizableDocument extends GateDocument
{
   /**
    * ID документа во внешней системе.
    * ID должен быть уникален среди всех плетежей.
    * Domain: ExternalID
    *
    * @return id
    */
   String getExternalId();
   /**
    * Установить привязку к документу во внешней системе
    *
    * @param externalId (Domain: ExternalID)
    */
   void setExternalId(String externalId);

   /**
    * Текущий статус документа
    *
    * @return статус
    */
   State getState();

	/**
	 * Получить дату исполнения платежа
	 * @return дата исполнения платежа
	 */
	Calendar getExecutionDate();

	/**
	 * Установить дату исполнения платежа
	 * @param executionDate дата исполнения платежа
	 */
	void setExecutionDate(Calendar executionDate);

	/**
	 * Получить код мобильного банка
	 * @return
	 */
	String getMbOperCode();

	/**
	 * Установить код мобильного банка
	 * @param mbOperCode код мобильного банка
	 */
	void setMbOperCode(String mbOperCode);

	/**
	 * @return номер блока, из которого инициирована отправка документа во ВС
	 */
	Long getSendNodeNumber();

	/**
	 * Установить номер блока, из которого инициирована отправка документа во ВС
	 * @param nodeNumber - номер блока
	 */
	void setSendNodeNumber(Long nodeNumber);
}