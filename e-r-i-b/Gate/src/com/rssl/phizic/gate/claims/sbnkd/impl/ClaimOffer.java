package com.rssl.phizic.gate.claims.sbnkd.impl;

import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 25.02.15
 * Time: 0:28
 * Обертка для отображения информации по картам и кредитам в гостевой сэссии.
 */
public class ClaimOffer
{
	private String documentNumber;
	private Calendar documentDate;
	private String documentStatus;
	private String productName;
	private String producCurrency;
	private Long producCount;
	private Money prodSumm;
	private BigDecimal prodRate;
	private Long prodDuration;
	private Long documentId;
	private boolean isCard;
    private boolean isCredit;
	private String formName;

	public ClaimOffer(String documentNumber, Calendar documentDate, String documentStatus, String productName, String producCurrency, Long producCount, boolean isCard)
	{
		this.documentNumber = documentNumber;
		this.documentDate = documentDate;
		this.documentStatus = documentStatus;
		this.productName = productName;
		this.producCurrency = producCurrency;
		this.producCount = producCount;
		this.isCard = isCard;
	}

	public ClaimOffer(String documentNumber, Calendar documentDate, String documentStatus, String productName, String producCurrency, Long producCount, Long documentId, boolean isCard, boolean isCredit)
	{
		this(documentNumber, documentDate, documentStatus, productName, producCurrency, producCount, isCard);
		this.documentId = documentId;
		this.isCredit = isCredit;
	}

    public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public Calendar getDocumentDate()
	{
		return documentDate;
	}

	public void setDocumentDate(Calendar documentDate)
	{
		this.documentDate = documentDate;
	}

	public String getDocumentStatus()
	{
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus)
	{
		this.documentStatus = documentStatus;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getProducCurrency()
	{
		return producCurrency;
	}

	public void setProducCurrency(String producCurrency)
	{
		this.producCurrency = producCurrency;
	}


	public Money getProdSumm()
	{
		return prodSumm;
	}

	public void setProdSumm(Money prodSumm)
	{
		this.prodSumm = prodSumm;
	}

	public BigDecimal getProdRate()
	{
		return prodRate;
	}

	public void setProdRate(BigDecimal prodRate)
	{
		this.prodRate = prodRate;
	}

	public Long getProdDuration()
	{
		return prodDuration;
	}

	public void setProdDuration(Long prodDuration)
	{
		this.prodDuration = prodDuration;
	}

	public Long getProducCount()
	{
		return producCount;
	}

	public void setProducCount(Long producCount)
	{
		this.producCount = producCount;
	}

	public boolean isCard()
	{
		return isCard;
	}

	public void setCard(boolean card)
	{
		isCard = card;
	}

    public Long getDocumentId()
    {
        return documentId;
    }

    public void setDocumentId(Long documentId)
    {
        this.documentId = documentId;
	}

    public boolean isCredit()
    {
        return isCredit;
    }

    public void setCredit(boolean credit)
    {
        isCredit = credit;
	}

	/**
	 * Получить название формы (Машины состояния)
	 * @return Название
	 */
	public String getFormName()
	{
		return formName;
	}

	/**
	 * Установить название формы
	 * @param formName Название
	 */
	public void setFormName(String formName)
	{
		this.formName = formName;
	}
}
