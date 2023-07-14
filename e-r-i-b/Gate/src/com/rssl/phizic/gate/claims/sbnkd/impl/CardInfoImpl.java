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
	 * @return дата созданияя заявки.
	 */
	public final Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate дата создания заявки.
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
	 * @param contractProductCode Код продукта счетового контракта.
	 */
	public void setContractProductCode(String contractProductCode)
	{
		this.contractProductCode = contractProductCode;
	}

	/**
	 * @return Код продукта счетового контракта.
	 */
	public String getContractProductCode()
	{
		return contractProductCode;
	}

	/**
	 * @param cardAcctProductCode Код карточного продукта.
	 */
	public void setCardAcctProductCode(String cardAcctProductCode)
	{
		this.cardAcctProductCode = cardAcctProductCode;
	}

	/**
	 * @return Код карточного продукта.
	 */
	public String getCardAcctProductCode()
	{
		return cardAcctProductCode;
	}

	/**
	 * @param cardAcctCreditLimit Размер кредитного лимита.
	 */
	public void setCardAcctCreditLimit(BigDecimal cardAcctCreditLimit)
	{
		this.creditLimit = cardAcctCreditLimit;
	}

	/**
	 * @return Размер кредитного лимита.
	 */
	public BigDecimal getCardAcctCreditLimit()
	{
		return creditLimit;
	}

	/**
	 * @param cardAcctPinPack Печать Пин-конверта .
	 */
	public void setCardAcctPinPack(Long cardAcctPinPack)
	{
		this.pinPack = cardAcctPinPack;
	}

	/**
	 * @return Печать Пин-конверта .
	 */
	public Long getCardAcctPinPack()
	{
		return pinPack;
	}

	/**
	 * @param mBCStatus Подключена услага мобильный банк.
	 */
	public void setMBCStatus(Boolean mBCStatus)
	{
		this.mbkStatus = mBCStatus;
	}

	/**
	 * @return Подключена услага мобильный банк.
	 */
	public Boolean getMBCStatus()
	{
		return mbkStatus;
	}

	/**
	 * @param mBCContractType Тип пакета мобильный банк.
	 */
	public void setMBCContractType(Long mBCContractType)
	{
		this.mbkContractType = mBCContractType;
	}

	/**
	 * @return Тип пакета мобильный банк.
	 */
	public Long getMBCContractType()
	{
		return mbkContractType;
	}

	/**
	 * @param mBCPhone Номер телефона.
	 */
	public void setMBCPhone(String mBCPhone)
	{
		this.phone = mBCPhone;
	}

	/**
	 * @return Номер телефона.
	 */
	public String getMBCPhone()
	{
		return phone;
	}

	/**
	 * @param mBCPhoneCode Код оператора связи.
	 */
	public void setMBCPhoneCode(String mBCPhoneCode)
	{
		this.mbkPhoneCode = mBCPhoneCode;
	}

	/**
	 * @return Код оператора связи.
	 */
	public String getMBCPhoneCode()
	{
		return mbkPhoneCode;
	}

	/**
	 * @param cardAcctCardOrderDate Дата завяления-анкеты на получение карты.
	 */
	public void setCardAcctCardOrderDate(String cardAcctCardOrderDate)
	{
		this.orderDate = cardAcctCardOrderDate;
	}

	/**
	 * @return Дата завяления-анкеты на получение карты.
	 */
	public String getCardAcctCardOrderDate()
	{
		return orderDate;
	}

	/**
	 * @param cardAcctRiskFactor Риск-фактор.
	 */
	public void setCardAcctRiskFactor(String cardAcctRiskFactor)
	{
		this.riskFactor = cardAcctRiskFactor;
	}

	/**
	 * @return Риск-фактор.
	 */
	public String getCardAcctRiskFactor()
	{
		return riskFactor;
	}

	/**
	 * @param bonusInfoCode Код бонусной программы.
	 */
	public void setBonusInfoCode(String bonusInfoCode)
	{
		this.bonusInfoCode = bonusInfoCode;
	}

	/**
	 * @return Код бонусной программы.
	 */
	public String getBonusInfoCode()
	{
		return bonusInfoCode;
	}

	/**
	 * @param bonusInfoValue Номер программы бонусов.
	 */
	public void setBonusInfoValue(String bonusInfoValue)
	{
		this.bonusInfoValue = bonusInfoValue;
	}

	/**
	 * @return Номер программы бонусов.
	 */
	public String getBonusInfoValue()
	{
		return bonusInfoValue;
	}

	/**
	 * @param contractCardType Тип карты.
	 */
	public void setContractCardType(Long contractCardType)
	{
		this.cardType = contractCardType;
	}

	/**
	 * @return Тип карты.
	 */
	public Long getContractCardType()
	{
		return cardType;
	}

	/**
	 * @param contractClientCategory Категория клиента.
	 */
	public void setContractClientCategory(Long contractClientCategory)
	{
		this.clientCategory = contractClientCategory;
	}

	/**
	 * @return Категория клиента.
	 */
	public Long getContractClientCategory()
	{
		return clientCategory;
	}

	/**
	 * @param contractCurrency Валюта карты.
	 */
	public void setContractCurrency(String contractCurrency)
	{
		this.contractCurrency = contractCurrency;
	}

	/**
	 * @return Валюта карты.
	 */
	public String getContractCurrency()
	{
		return contractCurrency;
	}

	/**
	 * @param contractBIOData Признак наличия БИО-приложения на карте.
	 */
	public void setContractBIOData(Boolean contractBIOData)
	{
		this.bioData = contractBIOData;
	}

	/**
	 * @return Признак наличия БИО-приложения на карте.
	 */
	public Boolean getContractBIOData()
	{
		return bioData;
	}

	/**
	 * @param contractIsPin Признак выдачи карты с ПИН-конвертом.
	 */
	public void setContractIsPin(Boolean contractIsPin)
	{
		this.isPinPack = contractIsPin;
	}

	/**
	 * @return Признак выдачи карты с ПИН-конвертом.
	 */
	public Boolean getContractIsPin()
	{
		return isPinPack;
	}

	/**
	 * @param contractIsOwner Признак владельца счета.
	 */
	public void setContractIsOwner(Boolean contractIsOwner)
	{
		this.isOwner = contractIsOwner;
	}

	/**
	 * @return Признак владельца счета.
	 */
	public Boolean getContractIsOwner()
	{
		return isOwner;
	}

	/**
	 * @return Название карты.
	 */
	public String getCardName()
	{
		return cardName;
	}

	/**
	 * @param cardName Название карты.
	 */
	public void setCardName(String cardName)
	{
		this.cardName = cardName;
	}

	/**
	 * @return Тип карты текстовый.
	 */
	public String getCardTypeString()
	{
		return cardTypeString;
	}

	/**
	 * @param cardTypeString Тип карты текстовый.
	 */
	public void setCardTypeString(String cardTypeString)
	{
		this.cardTypeString = cardTypeString;
	}
}
