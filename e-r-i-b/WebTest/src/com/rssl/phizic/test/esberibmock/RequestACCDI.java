package com.rssl.phizic.test.esberibmock;

/**
 * ������� ��������� ��� ACC_DI
 * User: Egorovaa
 * Date: 15.12.2011
 * Time: 12:00:35
 */
public class RequestACCDI
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
	 * ����� ���, �������� ���� �� ������
	 */
	private String rbBrchId;
	/**
	 * �����, ������������ �� ������ �������
	 */
	private MockDeposit mockDeposit;

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

	public MockDeposit getMockDeposit()
	{
		return mockDeposit;
	}

	public void setMockDeposit(MockDeposit mockDeposit)
	{
		this.mockDeposit = mockDeposit;
	}
}
