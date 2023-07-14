package com.rssl.phizic.gate.payments.systems.gorod;

import com.rssl.phizic.gate.clients.Client;

/**
 * Kарта системы Город
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */

public interface GorodCard
{
   /**
    * Владельц карты
    *
    * @return владельц карты
    */
   Client getOwner();
	
   /**
    * Идентификатор карты(ПАН)
    *
    * @return идентификатор карты(ПАН)
    */
   String getId();

}