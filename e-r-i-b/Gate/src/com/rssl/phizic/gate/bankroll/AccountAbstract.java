/***********************************************************************
 * Module:  AccountAbstract.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface AccountAbstract
 ***********************************************************************/

package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.Money;

import java.util.*;

/**
 * Выписка по счету
 */
public interface AccountAbstract extends AbstractBase
{
   /**
    * Дата предыдущей операции. Т.е. последней операции, которая была совершенна до начала периода выписки.
    *
    * @return Дата предыдущей операции
    */
   Calendar getPreviousOperationDate();

	/**
    * Получить доверенности.
    * @return Список доверенностей или пустой список
    */
   List<Trustee> getTrusteesDocuments();

	/**
	 * Дата закрытия счета
	 * @return если счет не закрыт, то null
	 */
	Calendar getClosedDate();

	/**
	 * Выплаченная при закрытии счета сумма.
	 * @return если счет не закрыт, то null
	 */
	Money getClosedSum();

	/**
	 * Дополнительная информация для отображения пользователю
	 * @return строка либо null
	 */
	String getAdditionalInformation();

}