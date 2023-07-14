package com.rssl.phizic.test.mbvmock;

import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 13.09.13
 * Time: 17:10
 * объект запроса BeginRq.
 */
public class BeginMigration
{
    private Long id;
    /**
     * Дата рождения
     */
    private Calendar birthday;
    /**
     * Имя
     */
    private String firstName;
    /**
     * Фамилия
     */
    private String lastName;

    /**
     * Отчество
     */
    private String middleName;
    /**
     * Тип документа
     */
    private String idType;
    /**
     * Серия документа
     */
    private String idSeries;
    /**
     * Номер документа.
     */
    private String idNum;
    /**
     * @return код возврата
     */
    private String retCode;
    /**
     * Дополнительная информация о результате операции.
     */
    private String resultMessage;
	/**
	 * ID миграции
	 */
	private String migrationId;

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

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

	public String getMigrationId()
	{
		return migrationId;
	}

	public void setMigrationId(String migrationId)
	{
		this.migrationId = migrationId;
	}
}
