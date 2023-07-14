package com.rssl.phizic.gate.payments.systems.contact;

import com.rssl.phizic.gate.documents.AbstractAccountTransfer;

/**
 * Перевод по сети Contact
 *
 * @author Krenev
 * @ created 15.06.2007
 * @ $Author$
 * @ $Revision$
 */
public interface ContactPayment extends AbstractAccountTransfer
{
   /**
    *
    * @return Код точки получателя перевода
    */
   String getReceiverPointCode();

   /**
	 *
	 * @return Фамилие получателя
	 */
	String getReceiverSurName();

   /**
    *
    * @param transitAccount Номер транзитного счета.
    */
   void setTransitAccount(String transitAccount);
}