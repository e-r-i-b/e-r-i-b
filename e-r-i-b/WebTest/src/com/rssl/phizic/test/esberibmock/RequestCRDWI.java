package com.rssl.phizic.test.esberibmock;

/**
 * Входные параметры для CRDWI
 * User: Egorovaa
 * Date: 06.12.2011
 * Time: 15:24:07
 */
public class RequestCRDWI
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
	 * Номер карты
	 */
	private String cardNum;
	/**
	 * Номер ОСБ, ведущего карту
	 */
	private String rbBrchId;
	/**
	 * Карта, возвращаемая по такому запросу
	 */
	private MockCard mockCard;

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

	public String getCardNum()
	{
		return cardNum;
	}

	public void setCardNum(String cardNum)
	{
		this.cardNum = cardNum;
	}

	public String getRbBrchId()
	{
		return rbBrchId;
	}

	public void setRbBrchId(String rbBrchId)
	{
		this.rbBrchId = rbBrchId;
	}

	public MockCard getMockCard()
	{
		return mockCard;
	}

	public void setMockCard(MockCard mockCard)
	{
		this.mockCard = mockCard;
	}
}
