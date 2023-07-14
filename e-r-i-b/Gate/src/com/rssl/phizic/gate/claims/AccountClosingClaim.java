/***********************************************************************
 * Module:  AccountClosingClaim.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface AccountClosingClaim
 ***********************************************************************/

package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.GateDocument;

import java.util.*;

/**
 * ������ �� �������� �����
 */
public interface AccountClosingClaim extends SynchronizableDocument
{
   /**
    * ���� �������� �����
    *
    * @return �������� ���� ��������
    */
   Calendar getClosingDate();
   /**
    * ������, ������� ����������� �������� �������� �� �����.
    * ���������� ����� ���������� ������ ��������� ����� ��������
    * � ������� ������� �������� �������
    *
    * @return ������ ��� �������� �������
    */
   GateDocument getTransferPayment();
   /**
    * ����, ������� ���������� �������
    */
   String getClosedAccount();
}