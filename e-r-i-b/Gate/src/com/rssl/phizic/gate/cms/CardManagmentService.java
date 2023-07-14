package com.rssl.phizic.gate.cms;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.Service;

/**
 * Сервис по управлению пластиковыми карточками.
 * @author Egorova
 * @ created 03.12.2008
 * @ $Author$
 * @ $Revision$
 */

public interface CardManagmentService extends Service
{
   /**
    * Блокировать карту на неопределенный срок.
    *
    * @param card Карта для блокировки.
    * @param reason Причина блокировки.
    */
   void blockCard(Card card, BlockReason reason);

}