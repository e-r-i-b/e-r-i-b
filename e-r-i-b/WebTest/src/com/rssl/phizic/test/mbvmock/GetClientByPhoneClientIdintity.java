package com.rssl.phizic.test.mbvmock;

import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 13.09.13
 * Time: 16:18
 */
public class GetClientByPhoneClientIdintity
{
    private Long id;
    /**
     * ���� ��������
     */
    private Calendar birthday;
    /**
     * ���
     */
    private String firstName;
    /**
     * �������
     */
    private String lastName;

    /**
     * ��������
     */
    private String middleName;
    /**
     * ��� ���������
     */
    private String idType;
    /**
     * ����� ���������
     */
    private String idSeries;
    /**
     * ����� ���������.
     */
    private String idNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdSeries() {
        return idSeries;
    }

    public void setIdSeries(String idSeries) {
        this.idSeries = idSeries;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }
}
