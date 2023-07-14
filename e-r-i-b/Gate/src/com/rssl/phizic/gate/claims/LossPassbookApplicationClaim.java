package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.SynchronizableDocument;

/**
 * @author Egorova
 * @ created 25.04.2008
 * @ $Author$
 * @ $Revision$
 */
public interface LossPassbookApplicationClaim extends SynchronizableDocument
{
	/**
    * Счет утерянной сберкнижки
    *
    * @return Счет для приоставления операций
    */
   String getDepositAccount();

	/**
    * Действие со счётом
	* 1 - выдать наличными
	* 2 - перечислить на другой счёт
    * 3 - выдать дубликат сберкнижки
	*
    * @return Действие со счётом
    */
   int getAccountAction();

	/**
    * Счет для перечисления средств
    *
    * @return Счет для перечисления средств
    */
   String getReceiverAccount();
}
