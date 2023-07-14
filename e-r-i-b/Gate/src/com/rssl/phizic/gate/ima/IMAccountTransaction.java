package com.rssl.phizic.gate.ima;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.TransactionBase;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public interface IMAccountTransaction extends TransactionBase
{
   /**
    * ���� ����������/�����������
    *
    *
    * @return ����� ���� ����������/����������� � ����� ������������������ �����
    */
   String getCorrespondentAccount();
   /**
    * ������ � ���������� �����
    *
    * @return ����� ������� � ������� � ���������� �����
    */
   Money getDebitSumInPhizicalForm();
   /**
    * ������ � ���������� �����
    *
    * @return ����� ������� � ������� � ���������� �����
    */
   Money getCreditSumInPhizicalForm();
   /**
    * ������� � ���������� �����
    *
    * @return ����� ������� � ������� � ���������� �����
    */
   Money getBalanceInPhizicalForm();
   /**
    * ����� ��������
    *
    * @return ����� ��������
    */
   String getOperationNumber();
   /**
    * ���� ��������
    *
    * @return ���� ��������
    */
   String getOperationCode();

   /**
    * �������: �������� � �������� ��� ��������� ����������
    * @return �������: ������, �������� ��������
    */
   IMAOperationType getOperationType();

	/**
	 * ����� �������� � ������
	 * @return ����� �������� � ������
	 */
   Money getOperationRurSumm();   
}