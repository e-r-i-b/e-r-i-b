package com.rssl.phizicgate.rsV51.contact;

/**
 * @author Novikov
 * @ created 21.05.2007
 * @ $Author$
 * @ $Revision$
 */

//TODO ������ ������������ ��������� ������� ������������ StatusDocumentChangeNotification ��� ����������(?), ������������ ���������� ����� retail-notification-config.xml
class StatusContactDocumentChange
{
    private String   id;
	private Long     applicationKind;
	private String   applicationKey;
	private String   status;
	private String   error;

	/**
	 * @return  id
	 */

	public String getId()
	{
		return id;
	}

	/**
	 * @param id ������
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	public long getApplicationKind()
	{
		return applicationKind;
	}

	/**
	 * @param applicationKind ��� ����� ��������
	 */

	public void setApplicationKind(Long applicationKind)
	{
		this.applicationKind = applicationKind;
	}

	public String getApplicationKey()
	{
		return applicationKey;
	}

	/**
	 * @param applicationKey ���� ��������
	 */

	public void setApplicationKey(String applicationKey)
	{
		this.applicationKey = applicationKey;
	}

	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status ������ ��������
	 */

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getError()
	{
		return error;
	}

	/**
	 * @param error ������ ��������
	 */

	public void setError(String error)
	{
		this.error = error;
	}
}
