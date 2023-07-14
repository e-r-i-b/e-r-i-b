package com.rssl.phizic.gate.ima;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.AbstractBase;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public interface IMAccountAbstract extends AbstractBase
{
   /**
    * Входящий остаток в рублях. Остаток на начало getFromDate().
    * То есть баланс который был перед совершением первой транзакции за указанный период
    *
    * @return Входящий остаток в рублях
    */
   Money getOpeningBalanceInRub();
   /**
    * Исходящий баланс в рублях. Остаток по истечении getToDate(). То есть баланс который стал после совершения последней транзакции за указанный период.
    *
    *
    * @return Исходящий баланс в рублях.
    */
   Money getClosingBalanceInRub();
   /**
    * Дата предыдущей операции. Т.е. последней операции, которая была совершенна до начала периода выписки.
    *
    * @return Дата предыдущей операции
    */
   Calendar getPreviousOperationDate();
   /**
    * Учетная цена Банка России (руб./грамм) на дату составления выписки.
    *
    * @return Учетная цена Банка России (руб./грамм) на дату составления выписки.
    */
   BigDecimal getRate();
}