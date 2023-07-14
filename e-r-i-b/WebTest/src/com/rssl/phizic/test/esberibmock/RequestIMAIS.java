package com.rssl.phizic.test.esberibmock;

/**
 * Входные параметры для IMA_IS
 @author: Egorovaa
 @ created: 27.02.2012
 @ $Author$
 @ $Revision$
 */
public class RequestIMAIS
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
	 * Номер ОСБ, ведущего счет ОМС
	 */
	private String rbBrchId;
	/**
	 * ОМС, возвращаемый по такому запросу
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
