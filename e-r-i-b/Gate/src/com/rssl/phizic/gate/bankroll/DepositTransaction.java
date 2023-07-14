package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.common.types.Money;
import java.util.Calendar;

/**
 * ���������� �� ������.
 */
public interface DepositTransaction extends TransactionBase
{
   /**
    * ����������. ���� ����������� == null.
    * Domain: Text
    *
    * @return ���������� ��� null
    */
   String getCounteragent();
   /**
    * ����� ����� ��������������. ���� ����������� == null
    * Domain: AccountNumber
    *
    * @return nomer ����� ��� null
    */
   String getCounteragentAccount();
   /**
    * ���� ��������������, ��������� �������� (�������� ������������ + ���). ���� ����������� == null.
    * Domain: Text
    *
    * @return ��� ��� null
    */
   String getCounteragentBank();
   /**
    * ���. ���� ����� ����������
    */
   String getCounteragentCorAccount();
   /**
    * ���� ��������
    */
   String getOperationCode();
   /**
    * ����� ���������, ��������������� ��������.
    */
   String getDocumentNumber();

}