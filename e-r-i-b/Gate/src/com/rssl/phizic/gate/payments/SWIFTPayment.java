/***********************************************************************
 * Module:  SWIFTPayment.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface SWIFTPayment
 ***********************************************************************/

package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.payments.systems.SWIFTPaymentConditions;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;

/**
 * �������� ����������� ������ �� ���� � ������ �����.
 *
 * ��� ������� ������� �������� ��������� ������� �������� ��������
 * <ul>
 * <li>other - � ���������� ����� �������</li>
 * <li>receiver - �� ����� ����������</li>
 * </ul>
 */
public interface SWIFTPayment extends AbstractAccountTransfer
{
   /**
    * ������������ ���������� (���)
    * Domain: Text
    *
    * @return ������������
    */
   String getReceiverName();
   /**
    * ���� ����������
    * Domain: AccountNumber
    *
    * @return ����� �����
    */
   String getReceiverAccount();
   /**
    * ��� ������ ����������. �� ISO 3166-1 (Alpha 3).
    * Domain: CountryCode
    *
    * @return ��� ������ ����������
    */
   String getReceiverCountryCode();
   /**
    * SWIFT ��� ����� ����������
    * Domain: SWIFT
    *
    * @return ��� swift
    */
   String getReceiverSWIFT();
   /**
    * ������������ ����� ����������
    * Domain: Text
    *
    * @return ������������ �����
    */
   String getReceiverBankName();
    /**
	 * 
	 * @return ������� �������� 
	 */
	SWIFTPaymentConditions getConditions();
}
