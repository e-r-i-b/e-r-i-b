/***********************************************************************
 * Module:  SynchronizableDocument.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface SynchronizableDocument
 ***********************************************************************/

package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.documents.State;

import java.util.Calendar;

/**
 * ��������� ��� ����������, ������� ������� � ����� � ��������� �������������
 */
public interface SynchronizableDocument extends GateDocument
{
   /**
    * ID ��������� �� ������� �������.
    * ID ������ ���� �������� ����� ���� ��������.
    * Domain: ExternalID
    *
    * @return id
    */
   String getExternalId();
   /**
    * ���������� �������� � ��������� �� ������� �������
    *
    * @param externalId (Domain: ExternalID)
    */
   void setExternalId(String externalId);

   /**
    * ������� ������ ���������
    *
    * @return ������
    */
   State getState();

	/**
	 * �������� ���� ���������� �������
	 * @return ���� ���������� �������
	 */
	Calendar getExecutionDate();

	/**
	 * ���������� ���� ���������� �������
	 * @param executionDate ���� ���������� �������
	 */
	void setExecutionDate(Calendar executionDate);

	/**
	 * �������� ��� ���������� �����
	 * @return
	 */
	String getMbOperCode();

	/**
	 * ���������� ��� ���������� �����
	 * @param mbOperCode ��� ���������� �����
	 */
	void setMbOperCode(String mbOperCode);

	/**
	 * @return ����� �����, �� �������� ������������ �������� ��������� �� ��
	 */
	Long getSendNodeNumber();

	/**
	 * ���������� ����� �����, �� �������� ������������ �������� ��������� �� ��
	 * @param nodeNumber - ����� �����
	 */
	void setSendNodeNumber(Long nodeNumber);
}