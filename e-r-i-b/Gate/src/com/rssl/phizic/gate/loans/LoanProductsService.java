package com.rssl.phizic.gate.loans;

import org.w3c.dom.Document;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.Service;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 03.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * Получение информации о кредитах.
 */
public interface LoanProductsService extends Service
{
   /**
    * Получение информации о всех кредитных продуктах.
    * Возвращенный документ содержит информацию, необходимую для создания кредитного продукта.
    * Формат возвращенного xml документа определяется реализацией.
    *
    *
    * @return xml
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   Document getLoansInfo() throws GateException;
   /**
    * Получение информации о кредитном продукте.
    * Формат документа определяется реализацией.
    *
    * @param conditions Список ID условий (Domain: ExternalID)
    * @return xml
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   Document getLoanProduct(List<String> conditions) throws GateException;
}