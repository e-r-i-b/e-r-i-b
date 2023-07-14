package com.rssl.phizic.test.mbvmock;

import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 13.09.13
 * Time: 17:10
 * ������ ������� CommitRq.
 */
public class CommitMigration
{
    private Long id;
	/**
	 * @return ��� ��������
	 */
    private String retCode;
    /**
     * ����� ����������
     */
    private Calendar disconnectTime;
    /**
     * �������������� ���������� � ���������� ��������.
     */
    private String resultMessage;
	/**
	 * ID ��������
	 */
	private String migrationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public Calendar getDisconnectTime() {
        return disconnectTime;
    }

    public void setDisconnectTime(Calendar disconnectTime) {
        this.disconnectTime = disconnectTime;
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
