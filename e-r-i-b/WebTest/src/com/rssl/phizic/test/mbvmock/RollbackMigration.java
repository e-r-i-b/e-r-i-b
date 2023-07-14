package com.rssl.phizic.test.mbvmock;

import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 14.09.13
 * Time: 14:51
 * ������ ������� RollbackRq.
 */
public class RollbackMigration
{
    private Long id;
	/**
	 * ID ��������
	 */
	private String migrationId;
    /**
     * @return ��� ��������
     */
    private String retCode;
    /**
     * �������������� ���������� � ���������� ��������.
     */
    private String resultMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getMigrationId()
	{
		return migrationId;
	}

	public void setMigrationId(String migrationId)
	{
		this.migrationId = migrationId;
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
}
