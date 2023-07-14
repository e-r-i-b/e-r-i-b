/***********************************************************************
 * Module:  CommissionOptions.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface CommissionOptions
 ***********************************************************************/

package com.rssl.phizic.gate.documents;

/**
 * ������ ��������� ��������
 */
public interface CommissionOptions
{
   /**
    * ������ ���������� ��������
    *
    * @return ������
    */
   CommissionTarget getTarget();
   /**
    * ���� � �������� ���������� ��������. ���������� ��� ���������� � ������ getTarget == other.
    * � ��������� ������� �������� �������� ������������.
    * 
    * ���� ������ ������������ (���� ������������������ � ����)  �� �������, ������������ ������.
    * Domain: AccountNumber
    */
   String getAccount();

}