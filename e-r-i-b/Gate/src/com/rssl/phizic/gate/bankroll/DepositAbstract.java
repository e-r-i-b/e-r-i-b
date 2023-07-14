/***********************************************************************
 * Module:  DepositAbstract.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface DepositAbstract
 ***********************************************************************/

package com.rssl.phizic.gate.bankroll;

import java.util.*;

/**
 * ������� �� ������.
 */
public interface DepositAbstract extends AbstractBase
{
   /**
    * ���� ���������� ��������. �.�. ��������� ��������, ������� ���� ���������� �� ������ ������� �������.
    *
    * @return ���� ���������� ��������
    */
   Calendar getPreviousOperationDate();

}