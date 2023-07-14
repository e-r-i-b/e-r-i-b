package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.Money;
import java.util.Calendar;
import java.io.Serializable;

/**
 * Описание транзакции.
 */
public interface TransactionBase extends Serializable
{
   /**
    * Дата-время транзакции
    * Domain: DateTime
    *
    * @return дата-время
    */
   Calendar getDate();
   /**
    * Сумма расхода. Еcли отсутствует то == 0.00
    *
    * @return сумма
    */
   Money getDebitSum();
   /**
    * Сумма прихода. Если отсутствует == 0.00
    *
    * @return сумма
    */
   Money getCreditSum();
   /**
    * Баланс после совершения транзакции.
    *
    * @return сумма
    * Для com.rssl.phizic.gate.ips.CardOperation = null
    */
   Money getBalance();
   /**
    * Описание (основание) транзакции
    * Domain: Text
    *
    * @return основание
    */
   String getDescription();
   /**
    * Номер документа, соответствующий операции.
    *  @return номер
    */
   String getDocumentNumber();

	/**
    * Наименование получателя/плательщика.
    *
    * @return Наименование получателя/плательщика в зависимости от типа операции: приход, расход
    */
   String getRecipient();
}