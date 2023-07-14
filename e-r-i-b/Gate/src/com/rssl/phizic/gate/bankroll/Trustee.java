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
 * ������������
 */
public interface Trustee extends Serializable
{
   /**
    * ������ ��� ����������� ����.
    *
    * @return ������ ��� ����������� ����.
    */
   String getName();
   /**
    * ���� ��������� ������������
    *
    * @return ���� ��������� ������������
    */
   Calendar getEndingDate();

}
