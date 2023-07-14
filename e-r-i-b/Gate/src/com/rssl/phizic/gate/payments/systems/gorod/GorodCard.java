package com.rssl.phizic.gate.payments.systems.gorod;

import com.rssl.phizic.gate.clients.Client;

/**
 * K���� ������� �����
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */

public interface GorodCard
{
   /**
    * �������� �����
    *
    * @return �������� �����
    */
   Client getOwner();
	
   /**
    * ������������� �����(���)
    *
    * @return ������������� �����(���)
    */
   String getId();

}