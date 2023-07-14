/***********************************************************************
 * Module:  CardAbstract.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface CardAbstract
 ***********************************************************************/

package com.rssl.phizic.gate.bankroll;

import java.util.*;

/**
 * ������� �� �����.
 *
 * � ������ ������ getTransactions - ������ ���������� ���������.
 */
public interface CardAbstract extends AbstractBase
{
   /**
    * ������ ������������ �������� �� ����� �� ��������� ������.
    * �������� � ������ � ������� �� ���������� (�� ���� �������� getOperationDate).
    *
    * <p>
    * ����������
    * ���� �� �������� ��������.
    * � ������� ���� �������� �������� � ����������. � � ������� ��� ����� ������������ ����������. 
    * �� ��� ���� ��� ������������� ������� �������������� ��������.
    * </p>
    */
   List<CardOperation> getUnsettledOperations();

}