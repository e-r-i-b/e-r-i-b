package com.rssl.phizic.gate.payments.systems.gorod;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * ������ ��� ������ � �������� �����
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */
public interface GorodService extends Service
{
   /**
    * ���������� �� ����� �����
    * @param cardId - ��� ����� �����a
    * @param office
    * @return ���������� �� ����� �����
    * @exception GateException
    * @exception GateLogicException
    */
   GorodCard getCard(String cardId, Office office) throws GateException, GateLogicException;
}
