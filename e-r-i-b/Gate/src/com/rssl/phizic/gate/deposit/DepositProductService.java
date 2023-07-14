package com.rssl.phizic.gate.deposit;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.w3c.dom.Document;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author Danilov
 * @ created 30.07.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * Получение информации о депозитных продуктах
 */
public interface DepositProductService extends Service
{
	/**
	 * Получение информации о всех депозинных продуктах.
	 * Возвращенный документ содержит информацию, необходимую для создания депозитного продукта.
	 * Формат возвращенного xml документа определяется реализацией.
	 *
	 * @param office - Офис, из которого необходимо получить информацию
	 * @return xml
	 * @exception GateException
	 */
	Document getDepositsInfo(Office office) throws GateException, GateLogicException;
	
	/**
	 * Получение информации о депозитном продукте. Формат документа определяется реализацией.
	 *
	 * @param params Запрос на информацию о депозитном продукте.
	 * @param office - Офис, из которого необходимо получить информацию
	 * @return xml
	 * @exception GateException
	 */
	Document getDepositProduct(Document params, Office office) throws GateException, GateLogicException;
}