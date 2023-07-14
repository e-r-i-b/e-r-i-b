/***********************************************************************
 * Module:  AbstractBase.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface AbstractBase
 ***********************************************************************/

package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.Money;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Базовый класс для выписки.
 */
public interface AbstractBase extends Serializable
{
   /**
    * Начальная дата выписки (включая ее)
    * Domain: Date
    *
    * @return начальная дата
    */
   Calendar getFromDate();
   /**
    * Конечная дата выписки (включая ее)
    * Domain: Date
    *
    * @return конечная дата
    */
   Calendar getToDate();
   /**
    * Входящий баланс. Остаток на начало getFromDate().
    * То есть баланс который был перед совершением первой транзакции за указанный период
    *
    * @return входящий остаток
    */
   Money getOpeningBalance();
   /**
    * Исходящий баланс. Остаток по истечении getToDate(). То есть баланс который стал после совершения последней транзакции за указанный период.
    *
    * @return исходящий остаток
    */
   Money getClosingBalance();
   /**
    * Список транзакций по счету за указанный период. Транзакции в списке в порядке их совершения.
    *
    * @return список транзакций
    */
   List<TransactionBase> getTransactions();

}