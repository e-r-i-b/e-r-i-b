package com.rssl.phizic.test.web.mbv;

import org.apache.struts.action.ActionForm;

/**
 * User: Moshenko
 * Date: 12.09.13
 * Time: 17:39
 */
public class MBVForm extends ActionForm
{
    private String MBVMigratorUrl;
    private String messagesText;

    private String phoneNumber;

    private String lastName;
    private String firstName;
    private String middleName;
    private String birthday;
    private String idType;
    private String idSeries;
    private String idNum;
    private String migrationID;

    public String getMBVMigratorUrl() {
        return MBVMigratorUrl;
    }

    public void setMBVMigratorUrl(String MBVMigratorUrl) {
        this.MBVMigratorUrl = MBVMigratorUrl;
    }

    public String getMessagesText() {
        return messagesText;
    }

    public void setMessagesText(String messagesText) {
        this.messagesText = messagesText;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

	public String getMigrationID()
	{
		return migrationID;
	}

	public void setMigrationID(String migrationID)
	{
		this.migrationID = migrationID;
	}
}
