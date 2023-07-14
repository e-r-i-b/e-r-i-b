/***********************************************************************
 * Module:  DepositClosingClaim.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface DepositClosingClaim
 ***********************************************************************/

package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.SynchronizableDocument;

import java.util.*;

/**
 * ������ �� �������� ��������
 */
public interface DepositClosingClaim extends SynchronizableDocument
{
   /**
    * �������� ���� ��������
    *
    * @return �������� ���� ��������
    */
   Calendar getClosingDate();
   /**
    * ������� ������������� ������
    *
    * @return ������� ������������� ������
    */
   String getExternalDepositId();
   /**
    * ����� ����� ��� ���������� �������
    *
    * @return ����� ����� ��� ���������� �������
    */
   String getDestinationAccount();

	/**
	 * ���������� ������� ������������� ������
	 * @param id ������� ������������� ������
	 */
	@Deprecated
	void setExternalDepositId(String id);
}