/***********************************************************************
 * Module:  AccountAbstract.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface AccountAbstract
 ***********************************************************************/

package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.Money;

import java.util.*;

/**
 * ������� �� �����
 */
public interface AccountAbstract extends AbstractBase
{
   /**
    * ���� ���������� ��������. �.�. ��������� ��������, ������� ���� ���������� �� ������ ������� �������.
    *
    * @return ���� ���������� ��������
    */
   Calendar getPreviousOperationDate();

	/**
    * �������� ������������.
    * @return ������ ������������� ��� ������ ������
    */
   List<Trustee> getTrusteesDocuments();

	/**
	 * ���� �������� �����
	 * @return ���� ���� �� ������, �� null
	 */
	Calendar getClosedDate();

	/**
	 * ����������� ��� �������� ����� �����.
	 * @return ���� ���� �� ������, �� null
	 */
	Money getClosedSum();

	/**
	 * �������������� ���������� ��� ����������� ������������
	 * @return ������ ���� null
	 */
	String getAdditionalInformation();

}