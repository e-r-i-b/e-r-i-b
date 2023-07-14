package com.rssl.phizic.common.type;

import com.rssl.phizic.person.PersonDocumentType;

import java.util.Calendar;

/**
 * ����������������� ������ �������
 * @author Rtischeva
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */
public interface PersonIdentity
{
	/**
	 * @return ���
	 */
	String getFirstName();

	/**
	 * ���������� ���
	 * @param firstName ���
	 */
	void setFirstName(String firstName);

	/**
	 * @return �������
	 */
	String getSurName();

	/**
	 * ���������� �������
	 * @param surName �������
	 */
	void setSurName(String surName);

	/**
	 * @return ��������
	 */
	String getPatrName();

	/**
	 * ���������� ��������
	 * @param patrName ��������
	 */
	void setPatrName(String patrName);

	/**
	 * @return ���� ��������
	 */
	Calendar getBirthDay();

	/**
	 * ���������� ���� ��������
	 * @param birthDay ���� ��������
	 */
	void setBirthDay(Calendar birthDay);

	/**
	 * @return ��� ���
	 */
	PersonDocumentType getDocType();

	/**
	 * ���������� ��� ���
	 * @param docType ��� ���
	 */
	void setDocType(PersonDocumentType docType);

	/**
	 * @return �������� ��� (� ������ ���� OTHER)
	 */
    String getDocName();

	/**
	 * ���������� �������� ��� (� ������ ���� OTHER)
	 * @param docName �������� ���
	 */
	void setDocName(String docName);

	/**
	 * @return ����� ���
	 */
	String getDocNumber();

	/**
	 * ���������� ����� ���
	 * @param docNumber ����� ���
	 */
	void setDocNumber(String docNumber);

	/**
	 * @return ����� ���
	 */
    String getDocSeries();

	/**
	 * ���������� ����� ���
	 * @param docSeries ����� ���
	 */
    void setDocSeries(String docSeries);

	/**
	 * @return ���� ������ ���
	 */
	Calendar getDocIssueDate();

	/**
	 * ���������� ���� ������ ���
	 * @param docIssueDate ���� ������ ���
	 */
    void setDocIssueDate(Calendar docIssueDate);

	/**
	 * @return ��� ����� ���
	 */
    String getDocIssueBy();

	/**
	 * ���������� ��� ����� ���
	 * @param docIssueBy ��� ����� ���
	 */
    void setDocIssueBy(String docIssueBy);

	/**
	 * @return ��� �������������, ��������� ���
	 */
    String getDocIssueByCode();

	/**
	 * ���������� ��� �������������, ��������� ���
	 * @param docIssueByCode ��� �������������, ��������� ���
	 */
    void setDocIssueByCode(String docIssueByCode);

	/**
	 * @return ������� ��������� ���������
	 */
	boolean isDocMain();

	/**
	 * ���������� ������� ��������� ���������
	 * @param docMain ������� ��������� ���������
	 */
	void setDocMain(boolean docMain);

	/**
	 * @return ���� ��������/���������
	 */
	Calendar getDocTimeUpDate();

	/**
	 * ���������� ���� ��������/���������
	 * @param docTimeUpDate ���� ��������/���������
	 */
	void setDocTimeUpDate(Calendar docTimeUpDate);

	/**
	 * @return ������� ���
	 */
	boolean isDocIdentify();

	/**
	 * ���������� ������� ���
	 * @param docIdentify ������� ���
	 */
	void setDocIdentify(boolean docIdentify);

	/**
	 * @return ��
	 */
	public String getRegion();

	public void setRegion(String region);
}
