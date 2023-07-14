/***********************************************************************
 * Module:  WithdrawDocument.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface WithdrawDocument
 ***********************************************************************/

package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.Money;

/**
 * ����� ���������.
 */
public interface WithdrawDocument extends SynchronizableDocument
{
   /**
    * ������� ID ����������� ���������
    * Domain: ExternalID
    *
    * @return id
    */
   String getWithdrawExternalId();

   /**
    *  ��������� �������� ID ����������� ���������
    * @param withdrawExternalId
    */
   void setWithdrawExternalId(String withdrawExternalId);

   /**
    * ���������� ID ����������� ���������
    * Domain: InternalID
    *
    * @return id
    */
   Long getWithdrawInternalId();
	/**
	 * ����������� ��� ����������� ���������
	 *
	 * @return ���������� ��� ����������� ���������
	 */
	Class<? extends GateDocument> getWithdrawType();

	/**
	 * ����� ������ ������, ��������� (�������� ������� ����� ������� �� ��������-���������)
	 * @return ����� ������
	 */
	WithdrawMode getWithdrawMode();

	/**
	 * ���������� ���������� ������
    * @return ���������� ������
    */
	GateDocument getTransferPayment();

	/**
	 * ���������� ����������� �� ���� ������� �����
    * @return ����� ������
    */
	Money getChargeOffAmount();

	/**
	 * �������� ������������� ����������� ��������� � ��������
	 * @return ������������� ����������� ��������� � ��������
	 */
	String getIdFromPaymentSystem();

	/**
	 * ���������� ������������� ����������� ��������� � ��������
	 * @param id ������������� ����������� ��������� � ��������
	 */
	void setIdFromPaymentSystem(String id);

	/**
	 * ���������� ��� �����������
	 * @param authorizeCode ���  �����������
	 */
	void setAuthorizeCode(String authorizeCode);

	/**
	 * �������� ��� �����������
 	 * @return ��� �����������
	 */
	String getAuthorizeCode();
}