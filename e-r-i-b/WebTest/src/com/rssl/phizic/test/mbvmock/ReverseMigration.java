package com.rssl.phizic.test.mbvmock;

/**
 * @author Puzikov
 * @ created 27.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ReverseMigration
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
