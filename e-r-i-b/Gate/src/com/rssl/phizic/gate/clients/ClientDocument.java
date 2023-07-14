package com.rssl.phizic.gate.clients;

import com.rssl.phizic.common.types.client.ClientDocumentType;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author Danilov
 * @ created 19.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� �������
 */
public interface ClientDocument extends Serializable
{
   /**
    * ��� ���������
    *
    * @return ��� ���������
    */
   public ClientDocumentType getDocumentType();

	/**
	 * ���������� ��� ���������
	 * @param clientDocumentType - ��� ���������
	 */
   public void setDocumentType(ClientDocumentType clientDocumentType);

   /**
    * �������� ��������� ��� ����������� �������.
    *
    * @return �������� ���������
    */
   public String getDocTypeName();

   /**
    * ����� ���������
    *
    * @return ����� ���������
    */
   public String getDocNumber();

   /**
    * ����� ���������
    */
   public String getDocSeries();

   /**
    * ���� ������ ���������
    *
    * @return ���� ������ ���������
    */
   public Calendar getDocIssueDate();

   /**
    * ��� �����.
    *
    * @return ��� �����
    */
   public String getDocIssueBy();

   /**
    * ��� �������������, ��������� ��������
    *
    * @return ��� �������������
    */
   public String getDocIssueByCode();

	/**
	 * �������, ����, ��� �������� ������������ ��������
	 *
	 * @return �������� �� ��������, �������������� ��������
	 */
	public boolean isDocIdentify();

	/**
    * ���� ��������� ����� ��������/���������� �� ���������� ��(��� ������������ �����)
    *
    * @return ���� ��������� ��������
    */
   public Calendar getDocTimeUpDate();
}