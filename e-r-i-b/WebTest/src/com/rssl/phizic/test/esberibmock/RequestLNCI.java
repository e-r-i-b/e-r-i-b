package com.rssl.phizic.test.esberibmock;

/**
 @author: Egorovaa
 @ created: 07.03.2012
 @ $Author$
 @ $Revision$
 */
public class RequestLNCI
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
	 * Номер ссудного счета
	 */
	private String acctId;
	/**
	 * Номер ОСБ, где ведется ссудный счет
	 */
	private String rbBrchId;
	/**
	 * Кредит, возвращаемый по такому запросу
	 */
	private MockCredit mockCredit;

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

	public MockCredit getMockCredit()
	{
		return mockCredit;
	}

	public void setMockCredit(MockCredit mockCredit)
	{
		this.mockCredit = mockCredit;
	}
}
