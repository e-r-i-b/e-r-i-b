package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import java.util.Calendar;

/**
 * �������� �������� �� �����
 *
 * getDate - ���� ���������� �������� �� ����-�����.
 * ����� ������������� (==null) ��� ������������ ����������.
 */
public interface CardOperation extends TransactionBase
{
   /**
    * ����-����� �������� �� �����.
    * Domain: DateTime
    *
    * @return ���� ��������
    */
   Calendar getOperationDate();
   /**
    * ����-����� �������� �������.
    * Domain: DateTime
    *
    * @return ���� ��������
    */
   Calendar getDate();
   /**
    * ����� �� ������� ����������� ��������.
    * ��� �������� �������� ����� �� ������� ��������� �������� (�� ����������� ����� ��� ������ �������� �������).
    * � ������ ������� �� �������� ����� - ��� ���������� ���� �������� �����, ���� ��������������.
    * � ������ ���. ���� ==  ����� ��� ������� �������� �������.
    *
    * ����� ������������� (== null) ��� ������������ ����������.
    * 
    * @return ����� ��������
    */
   Card getOperationCard();
   /**
    * ����� ������� � ������ ����� (���)
    *
    * @return ����� ������� � ������ ����������, null - ���� ������ �������� �� ������� � �����������
    */
   Money getAccountDebitSum();
   /**
    * @return ����� ������� � ������ ����� (���)
    */
   Money getAccountCreditSum();

	/**
	 * @return ���������� � �������� �����
	 */
	String getShopInfo();

}