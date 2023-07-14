package com.rssl.phizic.operations.loanOffer;

/**
 * User: Moshenko
 * Date: 10.06.2011
 * Time: 13:44:24
 * ��������� �������� �������� � �� �������������� ����������� �� ���������� �������� �������
 */
public class LoanOfferLoadResults
{
	/**
	 * id
	 */
	private Long id;
	/**
	 * R��-�� ������������ �����������
	 */
	private Long allCount;
	/**
	 * R��-�� �����������, ������� ����������� � ����,
	 */
	private Long loadCount;
	/*
	 * �������� ������ �� ������ ���������� ������
	 */
	private String loadOfferErr;
	/**
	 * �������� ����� ������ ��������
	 */
	private String loadCommonErr;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getAllCount()
	{
		return allCount;
	}

	public void setAllCount(Long allCount)
	{
		this.allCount = allCount;
	}

	public Long getLoadCount()
	{
		return loadCount;
	}

	public void setLoadCount(Long loadCount)
	{
		this.loadCount = loadCount;
	}

	public String getLoadOfferErr()
	{
		return loadOfferErr;
	}

	public void setLoadOfferErr(String loadOfferErr)
	{
		this.loadOfferErr = loadOfferErr;
	}

	public String getLoadCommonErr()
	{
		return loadCommonErr;
	}

	public void setLoadCommonErr(String loadCommonErr)
	{
		this.loadCommonErr = loadCommonErr;
	}
}
