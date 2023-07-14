package com.rssl.phizicgate.rsV51.demand;

/**
 * User: Novikov_A
 * Date: 04.06.2007
 * Time: 11:47:48
 */
public class DestinationDepositAccount
{
	private Long isCurrency;
	private Long receiverOffice;
	private Long referenc;
	private String destinationAccoun;
	private Long currencyCode;
    private Long recType = 3L;
    private Long accountPart = 8L;
    private Long applicationKind = 1L;
    private Long referenceKind = 1L;

	/**
	 * конструктор
	 */
	DestinationDepositAccount()
	{

	}

	/**
	 * конструктор
	 * @param document платёжный документ
	 */
	DestinationDepositAccount(PaymentDemandBase document)
	{
		this.isCurrency = document.getIsCur();
		this.currencyCode = document.getCurrencyCode();
		this.destinationAccoun = document.getDestinationDepositAccount();
		this.receiverOffice = document.getDepartment();
		this.referenc = document.getReferenc();
	}

	/**
	 * @return признак валюты
	 */
	public Long getIsCurrency()
	{
		return isCurrency;
	}

	/**
	 * @param isCurrency признак валюты
	 */
	public void setIsCurrency(boolean isCurrency)
	{
		if (isCurrency)
			this.isCurrency = 1L;
		else
			this.isCurrency = 0L;
	}

	/**
	 * @return подразделение 
	 */
	public Long getReceiverOffice()
	{
		return receiverOffice;
	}

	/**
	 * @param receiverOffice подразделение
	 */
	public void setReceiverOffice(Long receiverOffice)
	{
		this.receiverOffice = receiverOffice;
	}

	/**
	 * @return референс открытого вклада
	 */
	public Long getReferenc()
	{
		return referenc;
	}

	/**
	 * @param referenc референс открытого вклада
	 */
	public void setReferenc(Long referenc)
	{
		this.referenc = referenc;
	}

	/**
	 * @return тип записи (счёт/вклад)
	 */
	public Long getRecType()
	{
		return recType;
	}

	/**
	 * @param recType тип записи (счёт/вклад)
	 */
	public void setRecType(Long recType)
	{
		this.recType = recType;
	}

	/**
	 * @return тип записи (вклад/счёт)
	 */
	public String getDestinationAccoun()
	{
		return destinationAccoun;
	}

	/**
	 * @param destinationAccoun номер счета для перевода средст по окончанию срока договора по вкладутип записи (счёт/вклад)
	 */
	public void setDestinationAccoun(String destinationAccoun)
	{
		this.destinationAccoun = destinationAccoun;
	}

	/**
	 * @return номер счета для перевода средст по окончанию срока договора по вкладутип записи (счёт/вклад)
	 */
	public Long getAccountPart()
	{
		return accountPart;
	}

	/**
	 * @param accountPart номер счета для перевода средст по окончанию срока договора по вкладутип записи (счёт/вклад)
	 */
	public void setAccountPart(Long accountPart)
	{
		this.accountPart = accountPart;
	}

	/**
	 * @return код валюты
	 **/
	public Long getCurrencyCode()
	{
		return currencyCode;
	}

	/**
	 * @param currencyCode код валюты
	 **/
	public void setCurrencyCode(Long currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	/**
	 * @return вид БП
	 */
	public Long getApplicationKind()
	{
		return applicationKind;
	}

	/**
	 * @param applicationKind вид БП
	 */
	public void setApplicationKind(Long applicationKind)
	{
		this.applicationKind = applicationKind;
	}

	/**
	 * @return вид объекта (счет/договор)
	 */
	public Long getReferenceKind()
	{
		return referenceKind;
	}

	/**
	 * @param referenceKind вид объекта (счет/договор)
	 */
	public void setReferenceKind(Long referenceKind)
	{
		this.referenceKind = referenceKind;
	}
}
