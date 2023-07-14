package com.rssl.phizic.gate.payments.systems.contact;

import com.rssl.phizic.gate.documents.AbstractAccountTransfer;

/**
 * ������� �� ���� Contact
 *
 * @author Krenev
 * @ created 15.06.2007
 * @ $Author$
 * @ $Revision$
 */
public interface ContactPayment extends AbstractAccountTransfer
{
   /**
    *
    * @return ��� ����� ���������� ��������
    */
   String getReceiverPointCode();

   /**
	 *
	 * @return ������� ����������
	 */
	String getReceiverSurName();

   /**
    *
    * @param transitAccount ����� ����������� �����.
    */
   void setTransitAccount(String transitAccount);
}