package com.rssl.phizic.gate.payments.systems.recipients;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * Получатель платежа
 * @author Gainanov
 * @ created 03.07.2008
 * @ $Author$
 * @ $Revision$
 */

public interface Recipient extends DictionaryRecord
{
   /**
    * Сервис, в который входит этот получатель. Например "Водоканал", "Антена".
    *
    * @return сервис.
    */
   Service getService();
	
   /**
    * Название компании получателя  для отображения пользователю. Например, ООО "Водоканал"
    *
    * @return имя компании получателя
    */
   String getName();

    /**
     * Информация( коментарий) по поставщику.
     *
     * @return информация о поставщику.
     */
    String getDescription();

	/**
	 * основной сервис или нет
	 * @return main
	 */
	Boolean isMain();
}
