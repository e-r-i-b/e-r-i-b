/***********************************************************************
 * Module:  CardAbstract.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface CardAbstract
 ***********************************************************************/

package com.rssl.phizic.gate.bankroll;

import java.util.*;

/**
 * Выписка по карте.
 *
 * В данном случае getTransactions - список оплаченных тразакций.
 */
public interface CardAbstract extends AbstractBase
{
   /**
    * Список неоплаченных операций по карте за указанный период.
    * Операции в списке в порядке их совершения (по дате операции getOperationDate).
    *
    * <p>
    * ПРИМЕЧАНИЕ
    * Чтоб не возникло путаницы.
    * В ретейле есть отдельно операции и транзакции. И в ретейле это будут неоплаченные транзакции. 
    * Но для ИКФЛ нет необходимости вводить дополнительную сущность.
    * </p>
    */
   List<CardOperation> getUnsettledOperations();

}