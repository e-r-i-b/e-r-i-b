package com.rssl.phizic.test.esberibmock;

/**
 * ������� ��������� ��� IMA_IS
 @author: Egorovaa
 @ created: 27.02.2012
 @ $Author$
 @ $Revision$
 */
public class RequestIMAIS
{
	private Long id;
	/**
	 * ��� ���������������� �����, � ������� ������ ���� ���, �� ������� ������ �������� ������������� �� ��
	 */
	private String rbTbBrchId;
	/**
	 * ������������� �������-��������� ��������
	 */
	private String systemId;
	/**
	 * ����� �����
	 */
	private String acctId;
	/**
	 * ����� ���, �������� ���� ���
	 */
	private String rbBrchId;
	/**
	 * ���, ������������ �� ������ �������
	 */
	private MockIMAccount mockIMAccount;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getRbTbBrchId()
	{
		return rbTbBrchId;
	}

	public void setRbTbBrchId(String rbTbBrchId)
	{
		this.rbTbBrchId = rbTbBrchId;
	}

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	public String getAcctId()
	{
		return acctId;
	}

	public void setAcctId(String acctId)
	{
		this.acctId = acctId;
	}

	public String getRbBrchId()
	{
		return rbBrchId;
	}

	public void setRbBrchId(String rbBrchId)
	{
		this.rbBrchId = rbBrchId;
	}

	public MockIMAccount getMockIMAccount()
	{
		return mockIMAccount;
	}

	public void setMockIMAccount(MockIMAccount mockIMAccount)
	{
		this.mockIMAccount = mockIMAccount;
	}
}
