package com.rssl.phizic.test.esberibmock;

/**
 * ������� ��������� ��� CRDWI
 * User: Egorovaa
 * Date: 06.12.2011
 * Time: 15:24:07
 */
public class RequestCRDWI
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
	private String cardNum;
	/**
	 * ����� ���, �������� �����
	 */
	private String rbBrchId;
	/**
	 * �����, ������������ �� ������ �������
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
