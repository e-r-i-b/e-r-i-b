package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.documents.AbstractAccountTransfer;

/**
 * ������ ����� ���
 *
 * @author Krenev
 * @ created 26.06.2007
 * @ $Author$
 * @ $Revision$
 */
public interface UtilityPayment extends AbstractAccountTransfer
{
   /**
    * ��� ����������� (���)
    * Domain: ExternalID
    */
   String getPayerId();
   /** TODO ADD
    * ����� ����������� � ������������ ������.
    * ������ ������ ��������� � ������� getAmount � ������� ����� ��������
    *
    * ����������: ����� � getAmount ��� ����� ���� �����
    */
//   Money getInsuranceAmount();

}