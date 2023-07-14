package com.rssl.phizic.gate.mbv;

import com.rssl.phizic.person.PersonDocumentType;

import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 04.09.13
 * Time: 16:31
 * ����������������� ������ ������� ��� + ��� + �/�
 */
public class  MbvClientIdentity
{
    private String firstName;
    private String surName;
    private String patrName;
    private Calendar birthDay;
    private PersonDocumentType docType;
    private String docNumber;
    private String docSeries;

	/**
	 * @return ���
	 */
    public String getFirstName()
    {
        return firstName;
    }

	/**
	 * ���������� ���
	 * @param firstName ���
	 */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

	/**
	 * @return �������
	 */
    public String getSurName()
    {
        return surName;
    }

	/**
	 * ���������� �������
	 * @param surName �������
	 */
    public void setSurName(String surName)
    {
        this.surName = surName;
    }

	/**
	 * @return ��������
	 */
    public String getPatrName()
    {
        return patrName;
    }

	/**
	 * ���������� ��������
	 * @param patrName ��������
	 */
    public void setPatrName(String patrName)
    {
        this.patrName = patrName;
    }

	/**
	 * @return ���� ��������
	 */
    public Calendar getBirthDay()
    {
        return birthDay;
    }

	/**
	 * ���������� ���� ��������
	 * @param birthDay ���� ��������
	 */
    public void setBirthDay(Calendar birthDay)
    {
        this.birthDay = birthDay;
    }

	/**
	 * @return ��� ���
	 */
    public PersonDocumentType getDocType()
    {
        return docType;
    }

	/**
	 * ���������� ��� ���
	 * @param docType ��� ���
	 */
    public void setDocType(PersonDocumentType docType)
    {
        this.docType = docType;
    }


	/**
	 * @return ����� ���
	 */
    public String getDocNumber()
    {
        return docNumber;
    }

	/**
	 * ���������� ����� ���
	 * @param docNumber ����� ���
	 */
    public void setDocNumber(String docNumber)
    {
        this.docNumber = docNumber;
    }

	/**
	 * @return ����� ���
	 */
    public String getDocSeries()
    {
        return docSeries;
    }

	/**
	 * ���������� ����� ���
	 * @param docSeries ����� ���
	 */
    public void setDocSeries(String docSeries)
    {
        this.docSeries = docSeries;
    }


}
