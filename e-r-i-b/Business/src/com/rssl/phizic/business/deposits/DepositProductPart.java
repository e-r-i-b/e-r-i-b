package com.rssl.phizic.business.deposits;

/**
 * �������� �������� ������ �������� "���������� �������".
 * ������ ���� ����������� ��� ����� ����������� �������� � ������ ���.
 * @author lepihina
 * @ created 22.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductPart
{
	private Long id;
	private String name;
	private Long productId;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name - ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ����� ���� ������ �� �������� �����������
	 */
	public Long getProductId()
	{
		return productId;
	}

	/**
	 * @param productId - ����� ���� ������ �� �������� �����������
	 */
	public void setProductId(Long productId)
	{
		this.productId = productId;
	}
}
