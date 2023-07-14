package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.Money;
import java.util.Calendar;
import java.io.Serializable;

/**
 * �������� ����������.
 */
public interface TransactionBase extends Serializable
{
   /**
    * ����-����� ����������
    * Domain: DateTime
    *
    * @return ����-�����
    */
   Calendar getDate();
   /**
    * ����� �������. �c�� ����������� �� == 0.00
    *
    * @return �����
    */
   Money getDebitSum();
   /**
    * ����� �������. ���� ����������� == 0.00
    *
    * @return �����
    */
   Money getCreditSum();
   /**
    * ������ ����� ���������� ����������.
    *
    * @return �����
    * ��� com.rssl.phizic.gate.ips.CardOperation = null
    */
   Money getBalance();
   /**
    * �������� (���������) ����������
    * Domain: Text
    *
    * @return ���������
    */
   String getDescription();
   /**
    * ����� ���������, ��������������� ��������.
    *  @return �����
    */
   String getDocumentNumber();

	/**
    * ������������ ����������/�����������.
    *
    * @return ������������ ����������/����������� � ����������� �� ���� ��������: ������, ������
    */
   String getRecipient();
}