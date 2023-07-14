/***********************************************************************
 * Module:  AccountClosingClaim.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface AccountClosingClaim
 ***********************************************************************/

package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.GateDocument;

import java.util.*;

/**
 * Заявка на закрытие счета
 */
public interface AccountClosingClaim extends SynchronizableDocument
{
   /**
    * Дата закрытия счета
    *
    * @return Желаемая дата закрытия
    */
   Calendar getClosingDate();
   /**
    * Платеж, которым переводятся денежные средства со счета.
    * Реализация шлюза определяет список доступных типов платежей
    * с помощью которых возможен перевод
    *
    * @return Платеж для перевода средств
    */
   GateDocument getTransferPayment();
   /**
    * Счет, который необходимо закрыть
    */
   String getClosedAccount();
}