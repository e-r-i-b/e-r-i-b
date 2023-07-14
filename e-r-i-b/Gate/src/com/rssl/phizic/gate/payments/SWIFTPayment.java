/***********************************************************************
 * Module:  SWIFTPayment.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface SWIFTPayment
 ***********************************************************************/

package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.payments.systems.SWIFTPaymentConditions;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;

/**
 * ѕреравод иностранной валюты на счет в другом банке.
 *
 * ƒл€ данного платежа доступны следующие способы взымани€ комиссии
 * <ul>
 * <li>other - с указанного счета клиента</li>
 * <li>receiver - со счета получател€</li>
 * </ul>
 */
public interface SWIFTPayment extends AbstractAccountTransfer
{
   /**
    * Ќаименование получател€ (‘»ќ)
    * Domain: Text
    *
    * @return наименование
    */
   String getReceiverName();
   /**
    * —чет зачислени€
    * Domain: AccountNumber
    *
    * @return номер счета
    */
   String getReceiverAccount();
   /**
    *  од страны получател€. ѕо ISO 3166-1 (Alpha 3).
    * Domain: CountryCode
    *
    * @return код страны получател€
    */
   String getReceiverCountryCode();
   /**
    * SWIFT код банка получател€
    * Domain: SWIFT
    *
    * @return код swift
    */
   String getReceiverSWIFT();
   /**
    * Ќаименование банка получател€
    * Domain: Text
    *
    * @return наименование банка
    */
   String getReceiverBankName();
    /**
	 * 
	 * @return услови€ перевода 
	 */
	SWIFTPaymentConditions getConditions();
}
