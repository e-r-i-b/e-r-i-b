package com.rssl.phizic.gate.ima;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.TransactionBase;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public interface IMAccountTransaction extends TransactionBase
{
   /**
    * Счет получателя/плательщика
    *
    *
    * @return Номер счет получателя/плательщика – номер корреспондирующего счета
    */
   String getCorrespondentAccount();
   /**
    * Расход в физической форме
    *
    * @return Сумма расхода в граммах в физической форме
    */
   Money getDebitSumInPhizicalForm();
   /**
    * Приход в физической форме
    *
    * @return Сумма прихода в граммах в физической форме
    */
   Money getCreditSumInPhizicalForm();
   /**
    * Остаток в физической форме
    *
    * @return Сумма остатка в граммах в физической форме
    */
   Money getBalanceInPhizicalForm();
   /**
    * Номер операции
    *
    * @return Номер операции
    */
   String getOperationNumber();
   /**
    * Шифр операции
    *
    * @return Шифр операции
    */
   String getOperationCode();

   /**
    * Признак: операция с металлом или денежными средствами
    * @return признак: слиток, денежные средства
    */
   IMAOperationType getOperationType();

	/**
	 * Сумма операции в рублях
	 * @return сумма операции в рублях
	 */
   Money getOperationRurSumm();   
}