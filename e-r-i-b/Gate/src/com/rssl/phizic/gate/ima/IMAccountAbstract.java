package com.rssl.phizic.gate.ima;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.AbstractBase;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public interface IMAccountAbstract extends AbstractBase
{
   /**
    * �������� ������� � ������. ������� �� ������ getFromDate().
    * �� ���� ������ ������� ��� ����� ����������� ������ ���������� �� ��������� ������
    *
    * @return �������� ������� � ������
    */
   Money getOpeningBalanceInRub();
   /**
    * ��������� ������ � ������. ������� �� ��������� getToDate(). �� ���� ������ ������� ���� ����� ���������� ��������� ���������� �� ��������� ������.
    *
    *
    * @return ��������� ������ � ������.
    */
   Money getClosingBalanceInRub();
   /**
    * ���� ���������� ��������. �.�. ��������� ��������, ������� ���� ���������� �� ������ ������� �������.
    *
    * @return ���� ���������� ��������
    */
   Calendar getPreviousOperationDate();
   /**
    * ������� ���� ����� ������ (���./�����) �� ���� ����������� �������.
    *
    * @return ������� ���� ����� ������ (���./�����) �� ���� ����������� �������.
    */
   BigDecimal getRate();
}