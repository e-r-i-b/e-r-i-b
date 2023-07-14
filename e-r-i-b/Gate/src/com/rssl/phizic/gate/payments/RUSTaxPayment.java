/***********************************************************************
 * Module:  RUSTaxPayment.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface RUSTaxPayment
 ***********************************************************************/

package com.rssl.phizic.gate.payments;

import java.util.Calendar;

/**
 * Налоговый платеж РФ
 */
public interface RUSTaxPayment extends AbstractJurTransfer
{
   /**
    * Код КБК
    * Domain: KBK
    *
    * @return кбк
    */
   String getTaxKBK();
   /**
    * Код ОКАТО
    * Domain: OKATO
    *
    * @return окато
    */
   String getTaxOKATO();
   /**
    * Дата налогового документа
    * Domain: Date
    *
    * @return дата
    */
   Calendar getTaxDocumentDate();
   /**
    * Номер налогового документа
    * Domain: Text
    *
    * @return номер
    */
   String getTaxDocumentNumber();
   /**
    * Налоговый период
    * Domain: TaxPeriod
    *
    * @return период
    */
   String getTaxPeriod();
   /**
    * Основание налогового платежа
    * Domain: Text
    *
    * @return основание
    */
   String getTaxGround();
    /**
    * Тип налогового платежа
    * Domain: Text
    *
    * @return тип
    */
   String getTaxPaymentType();
   /**
    * Статаус составителя налог.платежа
    * Domain: Text
    *
    * @return тип
    */
   String getTaxPaymentStatus();
}