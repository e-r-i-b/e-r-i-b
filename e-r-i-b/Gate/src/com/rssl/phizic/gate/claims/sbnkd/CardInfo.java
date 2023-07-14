package com.rssl.phizic.gate.claims.sbnkd;

import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public interface CardInfo
{
	/**
	 * @return первая карта.
	 */
	public boolean isFirstCard();

	/**
	 * @param firstCard первая карта.
	 */
	public void setFirstCard(boolean firstCard);

	/**
	 * @return связка с заявкой.
	 */
	public Long getClaimId();

	/**
	 * @param claimId связка с заявкой.
	 */
	public void setClaimId(Long claimId);

	/**
	 * @return заявка.
	 */
	public IssueCardDocumentImpl getParent();

	/**
	 * @param parent заявка.
	 */
	public void setParent(IssueCardDocumentImpl parent);

	/**
	 * @param status статус.
	 */
	public void setStatus(CardInfoStatus status);

	/**
	 * @return статус.
	 */
	public CardInfoStatus getStatus();

	/**
	 * @param cardNum номер карты.
	 */
	void setCardNumber(String cardNum);

	/**
	 * @return номер карты.
	 */
	String getCardNumber();

	/**
	 * @param acctId номер счета.
	 */
	void setAccountNumber(String acctId);

	/**
	 * @return номер счета.
	 */
	String getAccountNumber();

	/**
	 * @param contractNumber номер контракта.
	 */
	void setContractNumber(String contractNumber);

	/**
	 * @return номер контракта.
	 */
	String getContractNumber();

	void setEndDtForWay(Calendar endDtForWay);
	Calendar getEndDtForWay();

	/**
	 * @return дата созданияя заявки.
	 */
	public Calendar getCreationDate();

	/**
	 * @param creationDate дата создания заявки.
	 */
	public void setCreationDate(Calendar creationDate);

	/**
	 * @param contractEmbossedText Эмбоссированный текст.
	 */
	public void setContractEmbossedText(String contractEmbossedText);

	/**
	 * @return Эмбоссированный текст.
	 */
	public String getContractEmbossedText();

	/**
	 * @param uID Идентификатор запроса.
	 */
	public void setUID(String uID);

	/**
	 * @return Идентификатор запроса.
	 */
	public String getUID();

	/**
	 * @param contractCardType Тип карты.
	 */
	public void setContractCardType(Long contractCardType);

	/**
	 * @return Тип карты.
	 */
	public Long getContractCardType();

	/**
	 * @param contractCurrency Валюта карты.
	 */
	public void setContractCurrency(String contractCurrency);

	/**
	 * @return Валюта карты.
	 */
	public String getContractCurrency();

	/**
	 * @param cardAcctPinPack Печать Пин-конверта .
	 */
	public void setCardAcctPinPack(Long cardAcctPinPack);

	/**
	 * @return Печать Пин-конверта .
	 */
	public Long getCardAcctPinPack();

	/**
	 * @param mBCStatus Подключена услага мобильный банк.
	 */
	public void setMBCStatus(Boolean mBCStatus);

	/**
	 * @return Подключена услага мобильный банк.
	 */
	public Boolean getMBCStatus();

	/**
	 * @param mBCContractType Тип пакета мобильный банк.
	 */
	public void setMBCContractType(Long mBCContractType);

	/**
	 * @return Тип пакета мобильный банк.
	 */
	public Long getMBCContractType();

	/**
	 * @param mBCPhone Номер телефона.
	 */
	public void setMBCPhone(String mBCPhone);

	/**
	 * @return Номер телефона.
	 */
	public String getMBCPhone();

	/**
	 * @param mBCPhoneCode Код оператора связи.
	 */
	public void setMBCPhoneCode(String mBCPhoneCode);

	/**
	 * @return Код оператора связи.
	 */
	public String getMBCPhoneCode();

	/**
	 * @param cardAcctProductCode Код карточного продукта.
	 */
	public void setCardAcctProductCode(String cardAcctProductCode);

	/**
	 * @return Код карточного продукта.
	 */
	public String getCardAcctProductCode();

	/**
	 * @param cardAcctCreditLimit Размер кредитного лимита.
	 */
	public void setCardAcctCreditLimit(BigDecimal cardAcctCreditLimit);

	/**
	 * @return Размер кредитного лимита.
	 */
	public BigDecimal getCardAcctCreditLimit();

	/**
	 * @param cardAcctCardOrderDate Дата завяления-анкеты на получение карты.
	 */
	public void setCardAcctCardOrderDate(String cardAcctCardOrderDate);

	/**
	 * @return Дата завяления-анкеты на получение карты.
	 */
	public String getCardAcctCardOrderDate();

	/**
	 * @param bonusInfoCode Код бонусной программы.
	 */
	public void setBonusInfoCode(String bonusInfoCode);

	/**
	 * @return Код бонусной программы.
	 */
	public String getBonusInfoCode();

	/**
	 * @param bonusInfoValue Номер программы бонусов.
	 */
	public void setBonusInfoValue(String bonusInfoValue);

	/**
	 * @return Номер программы бонусов.
	 */
	public String getBonusInfoValue();

	/**
	 * @param contractProductCode Код продукта счетового контракта.
	 */
	public void setContractProductCode(String contractProductCode);

	/**
	 * @return Код продукта счетового контракта.
	 */
	public String getContractProductCode();

	/**
	 * @param cardAcctRiskFactor Риск-фактор.
	 */
	public void setCardAcctRiskFactor(String cardAcctRiskFactor);

	/**
	 * @return Риск-фактор.
	 */
	public String getCardAcctRiskFactor();

	/**
	 * @param contractClientCategory Категория клиента.
	 */
	public void setContractClientCategory(Long contractClientCategory);

	/**
	 * @return Категория клиента.
	 */
	public Long getContractClientCategory();

	/**
	 * @param contractBIOData Признак наличия БИО-приложения на карте.
	 */
	public void setContractBIOData(Boolean contractBIOData);

	/**
	 * @return Признак наличия БИО-приложения на карте.
	 */
	public Boolean getContractBIOData();

	/**
	 * @param contractIsPin Признак выдачи карты с ПИН-конвертом.
	 */
	public void setContractIsPin(Boolean contractIsPin);

	/**
	 * @return Признак выдачи карты с ПИН-конвертом.
	 */
	public Boolean getContractIsPin();

	/**
	 * @param contractIsOwner Признак владельца счета.
	 */
	public void setContractIsOwner(Boolean contractIsOwner);

	/**
	 * @return Признак владельца счета.
	 */
	public Boolean getContractIsOwner();

	/**
	 * @return Название карты.
	 */
	public String getCardName();

	/**
	 * @param cardName Название карты.
	 */
	public void setCardName(String cardName);

	/**
	 * @return Тип карты текстовый.
	 */
	public String getCardTypeString();

	/**
	 * @param cardType Тип карты текстовый.
	 */
	public void setCardTypeString(String cardType);
}
