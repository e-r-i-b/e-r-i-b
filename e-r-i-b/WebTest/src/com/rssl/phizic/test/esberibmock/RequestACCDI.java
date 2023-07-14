package com.rssl.phizic.test.esberibmock;

/**
 * Входные параметры для ACC_DI
 * User: Egorovaa
 * Date: 15.12.2011
 * Time: 12:00:35
 */
public class RequestACCDI
{
	private Long id;
	/**
	 * Код территориального банка, в котором открыт счет МБК, по которой клиент произвел идентификацию на УС
	 */
	private String rbTbBrchId;
	/**
	 * Идентификатор системы-источника продукта 
	 */
	private String systemId;
	/**
	 * Номер счета
	 */
	private String acctId;
	/**
	 * Номер ОСБ, ведущего счет по вкладу
	 */
	private String rbBrchId;
	/**
	 * Вклад, возвращаемый по такому запросу
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
