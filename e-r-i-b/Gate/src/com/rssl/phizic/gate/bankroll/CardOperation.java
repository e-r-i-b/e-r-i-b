package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import java.util.Calendar;

/**
 * Атрибуты операции по карте
 *
 * getDate - дата совершения операции по карт-счету.
 * Может остутствовать (==null) для неоплаченных транзакций.
 */
public interface CardOperation extends TransactionBase
{
   /**
    * Дата-время операции по карте.
    * Domain: DateTime
    *
    * @return дата операции
    */
   Calendar getOperationDate();
   /**
    * Дата-время списания средств.
    * Domain: DateTime
    *
    * @return дата списания
    */
   Calendar getDate();
   /**
    * Карта по которой совершалась операция.
    * Это свойство содержит карту по которой совершена операция (не обязательно карта для котоой строится выписка).
    * В случае выписки по основной карте - тут содержится либо основная карта, либо дополнительная.
    * В случае доп. карт ==  карте для которой строится выписка.
    *
    * Может остутствовать (== null) для неоплаченных транзакций.
    * 
    * @return карта операции
    */
   Card getOperationCard();
   /**
    * Сумма расхода в валюте счета (СКС)
    *
    * @return сумма расхода в валюте транзакции, null - если данная операция не связана с транзакцией
    */
   Money getAccountDebitSum();
   /**
    * @return Сумма прихода в валюте счета (СКС)
    */
   Money getAccountCreditSum();

	/**
	 * @return Информация о торговой точке
	 */
	String getShopInfo();

}