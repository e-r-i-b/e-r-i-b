package ru.softlab.phizicgate.rsloansV64.claims.parsers;

/**
 * @author Omeliyanchuk
 * @ created 11.01.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������� ���� Loans
 * ������� �� ���� ���������������
 * ���������� � �����������������,
 * ���� �� ��� ����� �������������.
 */
public class FieldId
{
	private String systemId;
	private String userId;

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
}
