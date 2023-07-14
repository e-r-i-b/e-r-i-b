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
	 * �����������
	 */
	DestinationDepositAccount()
	{

	}

	/**
	 * �����������
	 * @param document �������� ��������
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
	 * @return ������� ������
	 */
	public Long getIsCurrency()
	{
		return isCurrency;
	}

	/**
	 * @param isCurrency ������� ������
	 */
	public void setIsCurrency(boolean isCurrency)
	{
		if (isCurrency)
			this.isCurrency = 1L;
		else
			this.isCurrency = 0L;
	}

	/**
	 * @return ������������� 
	 */
	public Long getReceiverOffice()
	{
		return receiverOffice;
	}

	/**
	 * @param receiverOffice �������������
	 */
	public void setReceiverOffice(Long receiverOffice)
	{
		this.receiverOffice = receiverOffice;
	}

	/**
	 * @return �������� ��������� ������
	 */
	public Long getReferenc()
	{
		return referenc;
	}

	/**
	 * @param referenc �������� ��������� ������
	 */
	public void setReferenc(Long referenc)
	{
		this.referenc = referenc;
	}

	/**
	 * @return ��� ������ (����/�����)
	 */
	public Long getRecType()
	{
		return recType;
	}

	/**
	 * @param recType ��� ������ (����/�����)
	 */
	public void setRecType(Long recType)
	{
		this.recType = recType;
	}

	/**
	 * @return ��� ������ (�����/����)
	 */
	public String getDestinationAccoun()
	{
		return destinationAccoun;
	}

	/**
	 * @param destinationAccoun ����� ����� ��� �������� ������ �� ��������� ����� �������� �� ��������� ������ (����/�����)
	 */
	public void setDestinationAccoun(String destinationAccoun)
	{
		this.destinationAccoun = destinationAccoun;
	}

	/**
	 * @return ����� ����� ��� �������� ������ �� ��������� ����� �������� �� ��������� ������ (����/�����)
	 */
	public Long getAccountPart()
	{
		return accountPart;
	}

	/**
	 * @param accountPart ����� ����� ��� �������� ������ �� ��������� ����� �������� �� ��������� ������ (����/�����)
	 */
	public void setAccountPart(Long accountPart)
	{
		this.accountPart = accountPart;
	}

	/**
	 * @return ��� ������
	 **/
	public Long getCurrencyCode()
	{
		return currencyCode;
	}

	/**
	 * @param currencyCode ��� ������
	 **/
	public void setCurrencyCode(Long currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	/**
	 * @return ��� ��
	 */
	public Long getApplicationKind()
	{
		return applicationKind;
	}

	/**
	 * @param applicationKind ��� ��
	 */
	public void setApplicationKind(Long applicationKind)
	{
		this.applicationKind = applicationKind;
	}

	/**
	 * @return ��� ������� (����/�������)
	 */
	public Long getReferenceKind()
	{
		return referenceKind;
	}

	/**
	 * @param referenceKind ��� ������� (����/�������)
	 */
	public void setReferenceKind(Long referenceKind)
	{
		this.referenceKind = referenceKind;
	}
}
