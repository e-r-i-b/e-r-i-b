/***********************************************************************
 * Module:  RUSTaxPayment.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface RUSTaxPayment
 ***********************************************************************/

package com.rssl.phizic.gate.payments;

import java.util.Calendar;

/**
 * ��������� ������ ��
 */
public interface RUSTaxPayment extends AbstractJurTransfer
{
   /**
    * ��� ���
    * Domain: KBK
    *
    * @return ���
    */
   String getTaxKBK();
   /**
    * ��� �����
    * Domain: OKATO
    *
    * @return �����
    */
   String getTaxOKATO();
   /**
    * ���� ���������� ���������
    * Domain: Date
    *
    * @return ����
    */
   Calendar getTaxDocumentDate();
   /**
    * ����� ���������� ���������
    * Domain: Text
    *
    * @return �����
    */
   String getTaxDocumentNumber();
   /**
    * ��������� ������
    * Domain: TaxPeriod
    *
    * @return ������
    */
   String getTaxPeriod();
   /**
    * ��������� ���������� �������
    * Domain: Text
    *
    * @return ���������
    */
   String getTaxGround();
    /**
    * ��� ���������� �������
    * Domain: Text
    *
    * @return ���
    */
   String getTaxPaymentType();
   /**
    * ������� ����������� �����.�������
    * Domain: Text
    *
    * @return ���
    */
   String getTaxPaymentStatus();
}