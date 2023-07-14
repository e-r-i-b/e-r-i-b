package com.rssl.phizic.gate.payments.systems.gorod;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * Сервис для работы с системой Город
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */
public interface GorodService extends Service
{
   /**
    * Информация по карте Город
    * @param cardId - пан карты городa
    * @param office
    * @return информация по карте Город
    * @exception GateException
    * @exception GateLogicException
    */
   GorodCard getCard(String cardId, Office office) throws GateException, GateLogicException;
}
