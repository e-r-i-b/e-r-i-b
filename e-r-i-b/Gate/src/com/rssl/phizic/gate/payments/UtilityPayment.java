package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.documents.AbstractAccountTransfer;

/**
 * оплата услуг ЖКХ
 *
 * @author Krenev
 * @ created 26.06.2007
 * @ $Author$
 * @ $Revision$
 */
public interface UtilityPayment extends AbstractAccountTransfer
{
   /**
    * Код плательщика (ЕПД)
    * Domain: ExternalID
    */
   String getPayerId();
   /** TODO ADD
    * Сумма страхования в национальной валюте.
    * Валюта должна совпадать с валютой getAmount и валютой счета списания
    *
    * Примечание: сумма в getAmount без учета этой суммы
    */
//   Money getInsuranceAmount();

}