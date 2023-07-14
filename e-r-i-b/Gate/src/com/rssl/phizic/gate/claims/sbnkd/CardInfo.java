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
	 * @return ������ �����.
	 */
	public boolean isFirstCard();

	/**
	 * @param firstCard ������ �����.
	 */
	public void setFirstCard(boolean firstCard);

	/**
	 * @return ������ � �������.
	 */
	public Long getClaimId();

	/**
	 * @param claimId ������ � �������.
	 */
	public void setClaimId(Long claimId);

	/**
	 * @return ������.
	 */
	public IssueCardDocumentImpl getParent();

	/**
	 * @param parent ������.
	 */
	public void setParent(IssueCardDocumentImpl parent);

	/**
	 * @param status ������.
	 */
	public void setStatus(CardInfoStatus status);

	/**
	 * @return ������.
	 */
	public CardInfoStatus getStatus();

	/**
	 * @param cardNum ����� �����.
	 */
	void setCardNumber(String cardNum);

	/**
	 * @return ����� �����.
	 */
	String getCardNumber();

	/**
	 * @param acctId ����� �����.
	 */
	void setAccountNumber(String acctId);

	/**
	 * @return ����� �����.
	 */
	String getAccountNumber();

	/**
	 * @param contractNumber ����� ���������.
	 */
	void setContractNumber(String contractNumber);

	/**
	 * @return ����� ���������.
	 */
	String getContractNumber();

	void setEndDtForWay(Calendar endDtForWay);
	Calendar getEndDtForWay();

	/**
	 * @return ���� ��������� ������.
	 */
	public Calendar getCreationDate();

	/**
	 * @param creationDate ���� �������� ������.
	 */
	public void setCreationDate(Calendar creationDate);

	/**
	 * @param contractEmbossedText ��������������� �����.
	 */
	public void setContractEmbossedText(String contractEmbossedText);

	/**
	 * @return ��������������� �����.
	 */
	public String getContractEmbossedText();

	/**
	 * @param uID ������������� �������.
	 */
	public void setUID(String uID);

	/**
	 * @return ������������� �������.
	 */
	public String getUID();

	/**
	 * @param contractCardType ��� �����.
	 */
	public void setContractCardType(Long contractCardType);

	/**
	 * @return ��� �����.
	 */
	public Long getContractCardType();

	/**
	 * @param contractCurrency ������ �����.
	 */
	public void setContractCurrency(String contractCurrency);

	/**
	 * @return ������ �����.
	 */
	public String getContractCurrency();

	/**
	 * @param cardAcctPinPack ������ ���-�������� .
	 */
	public void setCardAcctPinPack(Long cardAcctPinPack);

	/**
	 * @return ������ ���-�������� .
	 */
	public Long getCardAcctPinPack();

	/**
	 * @param mBCStatus ���������� ������ ��������� ����.
	 */
	public void setMBCStatus(Boolean mBCStatus);

	/**
	 * @return ���������� ������ ��������� ����.
	 */
	public Boolean getMBCStatus();

	/**
	 * @param mBCContractType ��� ������ ��������� ����.
	 */
	public void setMBCContractType(Long mBCContractType);

	/**
	 * @return ��� ������ ��������� ����.
	 */
	public Long getMBCContractType();

	/**
	 * @param mBCPhone ����� ��������.
	 */
	public void setMBCPhone(String mBCPhone);

	/**
	 * @return ����� ��������.
	 */
	public String getMBCPhone();

	/**
	 * @param mBCPhoneCode ��� ��������� �����.
	 */
	public void setMBCPhoneCode(String mBCPhoneCode);

	/**
	 * @return ��� ��������� �����.
	 */
	public String getMBCPhoneCode();

	/**
	 * @param cardAcctProductCode ��� ���������� ��������.
	 */
	public void setCardAcctProductCode(String cardAcctProductCode);

	/**
	 * @return ��� ���������� ��������.
	 */
	public String getCardAcctProductCode();

	/**
	 * @param cardAcctCreditLimit ������ ���������� ������.
	 */
	public void setCardAcctCreditLimit(BigDecimal cardAcctCreditLimit);

	/**
	 * @return ������ ���������� ������.
	 */
	public BigDecimal getCardAcctCreditLimit();

	/**
	 * @param cardAcctCardOrderDate ���� ���������-������ �� ��������� �����.
	 */
	public void setCardAcctCardOrderDate(String cardAcctCardOrderDate);

	/**
	 * @return ���� ���������-������ �� ��������� �����.
	 */
	public String getCardAcctCardOrderDate();

	/**
	 * @param bonusInfoCode ��� �������� ���������.
	 */
	public void setBonusInfoCode(String bonusInfoCode);

	/**
	 * @return ��� �������� ���������.
	 */
	public String getBonusInfoCode();

	/**
	 * @param bonusInfoValue ����� ��������� �������.
	 */
	public void setBonusInfoValue(String bonusInfoValue);

	/**
	 * @return ����� ��������� �������.
	 */
	public String getBonusInfoValue();

	/**
	 * @param contractProductCode ��� �������� ��������� ���������.
	 */
	public void setContractProductCode(String contractProductCode);

	/**
	 * @return ��� �������� ��������� ���������.
	 */
	public String getContractProductCode();

	/**
	 * @param cardAcctRiskFactor ����-������.
	 */
	public void setCardAcctRiskFactor(String cardAcctRiskFactor);

	/**
	 * @return ����-������.
	 */
	public String getCardAcctRiskFactor();

	/**
	 * @param contractClientCategory ��������� �������.
	 */
	public void setContractClientCategory(Long contractClientCategory);

	/**
	 * @return ��������� �������.
	 */
	public Long getContractClientCategory();

	/**
	 * @param contractBIOData ������� ������� ���-���������� �� �����.
	 */
	public void setContractBIOData(Boolean contractBIOData);

	/**
	 * @return ������� ������� ���-���������� �� �����.
	 */
	public Boolean getContractBIOData();

	/**
	 * @param contractIsPin ������� ������ ����� � ���-���������.
	 */
	public void setContractIsPin(Boolean contractIsPin);

	/**
	 * @return ������� ������ ����� � ���-���������.
	 */
	public Boolean getContractIsPin();

	/**
	 * @param contractIsOwner ������� ��������� �����.
	 */
	public void setContractIsOwner(Boolean contractIsOwner);

	/**
	 * @return ������� ��������� �����.
	 */
	public Boolean getContractIsOwner();

	/**
	 * @return �������� �����.
	 */
	public String getCardName();

	/**
	 * @param cardName �������� �����.
	 */
	public void setCardName(String cardName);

	/**
	 * @return ��� ����� ���������.
	 */
	public String getCardTypeString();

	/**
	 * @param cardType ��� ����� ���������.
	 */
	public void setCardTypeString(String cardType);
}
