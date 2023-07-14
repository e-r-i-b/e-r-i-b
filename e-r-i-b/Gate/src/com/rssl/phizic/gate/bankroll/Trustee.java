package com.rssl.phizic.gate.bankroll;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 08.04.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Доверенность
 */
public interface Trustee extends Serializable
{
   /**
    * Полное имя доверенного лица.
    *
    * @return Полное имя доверенного лица.
    */
   String getName();
   /**
    * Дата окончания доверенности
    *
    * @return Дата окончания доверенности
    */
   Calendar getEndingDate();

}
