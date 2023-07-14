package com.rssl.phizic.operations.loanOffer;

/**
 * User: Moshenko
 * Date: 10.06.2011
 * Time: 13:44:24
 * Результат загрузки операции в бд предодобренных предложений по кредитному продукту клиента
 */
public class LoanOfferLoadResults
{
	/**
	 * id
	 */
	private Long id;
	/**
	 * Rол-во обработанных предложений
	 */
	private Long allCount;
	/**
	 * Rол-во предложений, успешно загруженных в базу,
	 */
	private Long loadCount;
	/*
	 * Перечень ошибок по каждой проблемной записи
	 */
	private String loadOfferErr;
	/**
	 * Перечень общих ошибок загрузки
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
