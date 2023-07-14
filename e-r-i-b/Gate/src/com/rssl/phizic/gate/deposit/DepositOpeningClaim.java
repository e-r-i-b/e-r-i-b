/***********************************************************************
 * Module:  DepositOpeningClaim.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface DepositOpeningClaim
 ***********************************************************************/

package com.rssl.phizic.gate.deposit;

import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;

import java.util.Calendar;

/**
 * ������ �� �������� ��������\������
 */
public interface DepositOpeningClaim extends AbstractAccountTransfer
{
   /**
    * ������� ID ������������ ������� ��� ������������ ������
    * (��� RS Retail V51 ��� AccountType)
    * Domain: ExternalID
    *
    * @return id
    */
   String getDepositConditionsId();
   /**
    * ���� �� ������� ����������� �������
    *
    * @return ����
    */
   DateSpan getPeriod();
   /**
    * ���� �� ������� ��������� ������� �� ������ �� ��������� �����
    * Domain: AccountNumber
    *
    * @return ����
    */
   String getTransferAccount();
   /**
    * ���� ������ � ���� ��� �������������� �������� ������.
    * Domain: Date
    */
   Calendar getVisitDate();
   /**
    * ����������� �� �������������� �����������
    *
    * @return true == ���������
    */
   boolean isAutomaticRenewal();
   /**
    * ������� ID ������������� ����� � ������� ����������� �������
    * Domain: ExternalID
    *
    * @return ������� ID
    */
   String getOfficeExternalId();

	/**
	 * ��������� ������ ��������������� �����
	 * @param externalId - referenc ����� � �������
	 */
	void setAccount(String externalId);

	/**
	 * ����� ��������� �� ������ ����� � RS Retail  
	 * @return referenc ����� � ������� (externalId � ����� �������)
	 */
	String getAccount() throws GateLogicException, GateException;
}