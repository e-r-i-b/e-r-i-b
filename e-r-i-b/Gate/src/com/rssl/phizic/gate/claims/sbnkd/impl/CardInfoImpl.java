package com.rssl.phizic.gate.claims.sbnkd.impl;

import com.rssl.phizic.gate.claims.sbnkd.CardInfo;
import com.rssl.phizic.gate.claims.sbnkd.CardInfoStatus;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class CardInfoImpl implements CardInfo
{
	private Long claimId;
	private IssueCardDocumentImpl parent;
	private boolean firstCard;
	private String UID;
	private CardInfoStatus status = CardInfoStatus.NEW;
	private String accountNumber;
	private String cardNum;
	private String contractNumber;
	private Calendar endDtForWay;
	private String contractProductCode;
	private String cardAcctProductCode;
	private BigDecimal creditLimit;
	private Long pinPack;
	private boolean mbkStatus;
	private Long mbkContractType;
	private String phone;
	private String mbkPhoneCode;
	private String orderDate;
	private String riskFactor;
	private String bonusInfoCode;
	private String bonusInfoValue;
	private Long cardType;
	private Long clientCategory;
	private String contractCurrency;
	private boolean bioData;
	private boolean isPinPack;
	private boolean isOwner = true;
	private String cardName;
	private String cardTypeString;
	private Calendar creationDate;
	private String contractEmbossedText;

	/**
	 * @return ���� ��������� ������.
	 */
	public final Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate ���� �������� ������.
	 */
	public final void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public void setContractEmbossedText(String contractEmbossedText)
	{
		this.contractEmbossedText = contractEmbossedText;
	}

	public String getContractEmbossedText()
	{
		return contractEmbossedText;
	}

	public String getUID()
	{
		return UID;
	}

	public void setUID(String UID)
	{
		this.UID = UID;
	}

	public CardInfoStatus getStatus()
	{
		return status;
	}

	public void setStatus(CardInfoStatus status)
	{
		this.status = status;
	}

	public Long getClaimId()
	{
		return claimId;
	}

	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}

	public IssueCardDocumentImpl getParent()
	{
		return parent;
	}

	public void setParent(IssueCardDocumentImpl parent)
	{
		this.parent = parent;
	}

	public boolean isFirstCard()
	{
		return firstCard;
	}

	public void setFirstCard(boolean firstCard)
	{
		this.firstCard = firstCard;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setCardNumber(String cardNum)
	{
		this.cardNum = cardNum;
	}

	public String getCardNumber()
	{
		return cardNum;
	}

	public void setAccountNumber(String acctId)
	{
		this.accountNumber = acctId;
	}

	public void setContractNumber(String contractNumber)
	{
		this.contractNumber = contractNumber;
	}

	public String getContractNumber()
	{
		return contractNumber;
	}

	public void setEndDtForWay(Calendar endDtForWay)
	{
		this.endDtForWay = endDtForWay;
	}

	public Calendar getEndDtForWay()
	{
		return endDtForWay;
	}

	/**
	 * @param contractProductCode ��� �������� ��������� ���������.
	 */
	public void setContractProductCode(String contractProductCode)
	{
		this.contractProductCode = contractProductCode;
	}

	/**
	 * @return ��� �������� ��������� ���������.
	 */
	public String getContractProductCode()
	{
		return contractProductCode;
	}

	/**
	 * @param cardAcctProductCode ��� ���������� ��������.
	 */
	public void setCardAcctProductCode(String cardAcctProductCode)
	{
		this.cardAcctProductCode = cardAcctProductCode;
	}

	/**
	 * @return ��� ���������� ��������.
	 */
	public String getCardAcctProductCode()
	{
		return cardAcctProductCode;
	}

	/**
	 * @param cardAcctCreditLimit ������ ���������� ������.
	 */
	public void setCardAcctCreditLimit(BigDecimal cardAcctCreditLimit)
	{
		this.creditLimit = cardAcctCreditLimit;
	}

	/**
	 * @return ������ ���������� ������.
	 */
	public BigDecimal getCardAcctCreditLimit()
	{
		return creditLimit;
	}

	/**
	 * @param cardAcctPinPack ������ ���-�������� .
	 */
	public void setCardAcctPinPack(Long cardAcctPinPack)
	{
		this.pinPack = cardAcctPinPack;
	}

	/**
	 * @return ������ ���-�������� .
	 */
	public Long getCardAcctPinPack()
	{
		return pinPack;
	}

	/**
	 * @param mBCStatus ���������� ������ ��������� ����.
	 */
	public void setMBCStatus(Boolean mBCStatus)
	{
		this.mbkStatus = mBCStatus;
	}

	/**
	 * @return ���������� ������ ��������� ����.
	 */
	public Boolean getMBCStatus()
	{
		return mbkStatus;
	}

	/**
	 * @param mBCContractType ��� ������ ��������� ����.
	 */
	public void setMBCContractType(Long mBCContractType)
	{
		this.mbkContractType = mBCContractType;
	}

	/**
	 * @return ��� ������ ��������� ����.
	 */
	public Long getMBCContractType()
	{
		return mbkContractType;
	}

	/**
	 * @param mBCPhone ����� ��������.
	 */
	public void setMBCPhone(String mBCPhone)
	{
		this.phone = mBCPhone;
	}

	/**
	 * @return ����� ��������.
	 */
	public String getMBCPhone()
	{
		return phone;
	}

	/**
	 * @param mBCPhoneCode ��� ��������� �����.
	 */
	public void setMBCPhoneCode(String mBCPhoneCode)
	{
		this.mbkPhoneCode = mBCPhoneCode;
	}

	/**
	 * @return ��� ��������� �����.
	 */
	public String getMBCPhoneCode()
	{
		return mbkPhoneCode;
	}

	/**
	 * @param cardAcctCardOrderDate ���� ���������-������ �� ��������� �����.
	 */
	public void setCardAcctCardOrderDate(String cardAcctCardOrderDate)
	{
		this.orderDate = cardAcctCardOrderDate;
	}

	/**
	 * @return ���� ���������-������ �� ��������� �����.
	 */
	public String getCardAcctCardOrderDate()
	{
		return orderDate;
	}

	/**
	 * @param cardAcctRiskFactor ����-������.
	 */
	public void setCardAcctRiskFactor(String cardAcctRiskFactor)
	{
		this.riskFactor = cardAcctRiskFactor;
	}

	/**
	 * @return ����-������.
	 */
	public String getCardAcctRiskFactor()
	{
		return riskFactor;
	}

	/**
	 * @param bonusInfoCode ��� �������� ���������.
	 */
	public void setBonusInfoCode(String bonusInfoCode)
	{
		this.bonusInfoCode = bonusInfoCode;
	}

	/**
	 * @return ��� �������� ���������.
	 */
	public String getBonusInfoCode()
	{
		return bonusInfoCode;
	}

	/**
	 * @param bonusInfoValue ����� ��������� �������.
	 */
	public void setBonusInfoValue(String bonusInfoValue)
	{
		this.bonusInfoValue = bonusInfoValue;
	}

	/**
	 * @return ����� ��������� �������.
	 */
	public String getBonusInfoValue()
	{
		return bonusInfoValue;
	}

	/**
	 * @param contractCardType ��� �����.
	 */
	public void setContractCardType(Long contractCardType)
	{
		this.cardType = contractCardType;
	}

	/**
	 * @return ��� �����.
	 */
	public Long getContractCardType()
	{
		return cardType;
	}

	/**
	 * @param contractClientCategory ��������� �������.
	 */
	public void setContractClientCategory(Long contractClientCategory)
	{
		this.clientCategory = contractClientCategory;
	}

	/**
	 * @return ��������� �������.
	 */
	public Long getContractClientCategory()
	{
		return clientCategory;
	}

	/**
	 * @param contractCurrency ������ �����.
	 */
	public void setContractCurrency(String contractCurrency)
	{
		this.contractCurrency = contractCurrency;
	}

	/**
	 * @return ������ �����.
	 */
	public String getContractCurrency()
	{
		return contractCurrency;
	}

	/**
	 * @param contractBIOData ������� ������� ���-���������� �� �����.
	 */
	public void setContractBIOData(Boolean contractBIOData)
	{
		this.bioData = contractBIOData;
	}

	/**
	 * @return ������� ������� ���-���������� �� �����.
	 */
	public Boolean getContractBIOData()
	{
		return bioData;
	}

	/**
	 * @param contractIsPin ������� ������ ����� � ���-���������.
	 */
	public void setContractIsPin(Boolean contractIsPin)
	{
		this.isPinPack = contractIsPin;
	}

	/**
	 * @return ������� ������ ����� � ���-���������.
	 */
	public Boolean getContractIsPin()
	{
		return isPinPack;
	}

	/**
	 * @param contractIsOwner ������� ��������� �����.
	 */
	public void setContractIsOwner(Boolean contractIsOwner)
	{
		this.isOwner = contractIsOwner;
	}

	/**
	 * @return ������� ��������� �����.
	 */
	public Boolean getContractIsOwner()
	{
		return isOwner;
	}

	/**
	 * @return �������� �����.
	 */
	public String getCardName()
	{
		return cardName;
	}

	/**
	 * @param cardName �������� �����.
	 */
	public void setCardName(String cardName)
	{
		this.cardName = cardName;
	}

	/**
	 * @return ��� ����� ���������.
	 */
	public String getCardTypeString()
	{
		return cardTypeString;
	}

	/**
	 * @param cardTypeString ��� ����� ���������.
	 */
	public void setCardTypeString(String cardTypeString)
	{
		this.cardTypeString = cardTypeString;
	}
}
