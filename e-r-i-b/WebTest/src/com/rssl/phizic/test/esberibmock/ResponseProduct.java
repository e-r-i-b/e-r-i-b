package com.rssl.phizic.test.esberibmock;

/**
 * ����� �� ��������, ���������� ������������� �������� ��������, ��� �������� (�����, ����� � �.�.)
 * � id ������ � ���� (��� ������� ����, ��� �� �������� ���������� ������������ �����-�� ������)
 * User: Egorovaa
 * Date: 28.12.2011
 * Time: 14:59:31
 */
public class ResponseProduct
{
	private Long id;
	private Long gflId;
	private Long productId;
	private String productType;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getGflId()
	{
		return gflId;
	}

	public void setGflId(Long gflId)
	{
		this.gflId = gflId;
	}

	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	public String getProductType()
	{
		return productType;
	}

	public void setProductType(String productType)
	{
		this.productType = productType;
	}
}
