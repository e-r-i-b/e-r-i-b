package com.rssl.phizic.gate.cms;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.Service;

/**
 * ������ �� ���������� ������������ ����������.
 * @author Egorova
 * @ created 03.12.2008
 * @ $Author$
 * @ $Revision$
 */

public interface CardManagmentService extends Service
{
   /**
    * ����������� ����� �� �������������� ����.
    *
    * @param card ����� ��� ����������.
    * @param reason ������� ����������.
    */
   void blockCard(Card card, BlockReason reason);

}