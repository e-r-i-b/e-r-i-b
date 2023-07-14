package com.rssl.phizic.gate.payments.systems.recipients;

import com.rssl.phizic.gate.dictionaries.ResidentBank;

/**
 * Дополнительная информация по получателю
 * @author Gainanov
 * @ created 03.07.2008
 * @ $Author$
 * @ $Revision$
 */
public interface RecipientInfo
{
	/**
	 * ИНН получателя.
	 *
	 * @return инн получателя  или null, если данные не могут быть получены.
	 */
   String getINN();
	
	/**
	 * КПП получателя.
	 *
	 * @return КПП получателя  или null, если данные не могут быть получены.
	 */
   String getKPP();

	/**
	 * Счет получателя в бэк-офисе (т.е. не внутри платежной системы). Для печати платежных поручений.
	 *
	 * @return счет получателя   или null, если данные не могут быть получены.
	 */
   String getAccount();

   /**
    * Получить банк, в котором зарегистрирован счет получателя. Для печати платежных поручений.
    *
    * @return банк получателя или null, если данные не могут быть получены.
    */
   ResidentBank getBank();

	/**
	 * номер транзитного счета
	 *
	 * @return номер транзитного счета
	 */
	String getTransitAccount();
}