/***********************************************************************
 * Module:  AbstractBase.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface AbstractBase
 ***********************************************************************/

package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.Money;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * ������� ����� ��� �������.
 */
public interface AbstractBase extends Serializable
{
   /**
    * ��������� ���� ������� (������� ��)
    * Domain: Date
    *
    * @return ��������� ����
    */
   Calendar getFromDate();
   /**
    * �������� ���� ������� (������� ��)
    * Domain: Date
    *
    * @return �������� ����
    */
   Calendar getToDate();
   /**
    * �������� ������. ������� �� ������ getFromDate().
    * �� ���� ������ ������� ��� ����� ����������� ������ ���������� �� ��������� ������
    *
    * @return �������� �������
    */
   Money getOpeningBalance();
   /**
    * ��������� ������. ������� �� ��������� getToDate(). �� ���� ������ ������� ���� ����� ���������� ��������� ���������� �� ��������� ������.
    *
    * @return ��������� �������
    */
   Money getClosingBalance();
   /**
    * ������ ���������� �� ����� �� ��������� ������. ���������� � ������ � ������� �� ����������.
    *
    * @return ������ ����������
    */
   List<TransactionBase> getTransactions();

}