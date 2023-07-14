package com.rssl.phizic.business.finances;

import com.rssl.phizic.business.dictionaries.country.CountryCode;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MaskUtil;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Erkin
 * @ created 25.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� �� �����
 */
public class CardOperation
{
	private Long id;
	private String externalId;
	private Calendar date;
	private Calendar loadDate;
	private String cardNumber;
	private String originalDescription;
	private String description = "";
	private BigDecimal cardAmount;
	private BigDecimal nationalAmount;
	private boolean cash;
	private String deviceNumber;
	private Long ownerId;
	private CardOperationCategory category;
	private String originalCategoryName;
	private Long mccCode;
	private OperationType operationType;
	private Long businessDocumentId;
	private Boolean hidden;
	private CountryCode originalCountry;
	private CountryCode clientCountry;

	/**
	 * @return ID ��������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - ������������� ��������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������� ID �������� (��������, � ���)
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * @param externalId - ������� ID �������� (<id_��������_�_���>^<login_id>)
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return ����+����� ���������� ��������
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @param date - ����+����� ���������� ��������
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * @return ���� �������� ��������� � �������
	 */
	public Calendar getLoadDate()
	{
		return loadDate;
	}

	/**
	 * @param loadDate - ���� �������� ��������� � �������
	 */
	public void setLoadDate(Calendar loadDate)
	{
		this.loadDate = loadDate;
	}

	/**
	 * @return ����� �����, �� ������� ��������� ��������
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	/**
	 * @param cardNumber - ����� �����, �� ������� ��������� ��������
	 */
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	/**
	 * @return �������� �������� ������ � ������ � ����������, ��������� �� ���
	 */
	public String getOriginalDescription()
	{
		return originalDescription;
	}

	/**
	 * @param originalDescription - �������� �������� ������ � ������ � ����������, ��������� �� ���
	 */
	public void setOriginalDescription(String originalDescription)
	{
		this.originalDescription = originalDescription;
	}

	/**
	 * @return �������� �������� (�������� ��� �������������� �������)
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description - �������� �������� (�������� ��� �������������� �������)
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return ����� �������� � ������ �����
	 */
	public BigDecimal getCardAmount()
	{
		return cardAmount;
	}

	/**
	 * @param cardAmount - ����� �������� � ������ �����
	 */
	public void setCardAmount(BigDecimal cardAmount)
	{
		this.cardAmount = cardAmount;
	}

	/**
	 * @return ����� �������� � ������������ ������
	 */
	public BigDecimal getNationalAmount()
	{
		return nationalAmount;
	}

	/**
	 * @param nationalAmount - ����� �������� � ������������ ������
	 */
	public void setNationalAmount(BigDecimal nationalAmount)
	{
		this.nationalAmount = nationalAmount;
	}

	/**
	 * @return true - �������� �������� ���������/������� ��������
	 */
	public boolean isCash()
	{
		return cash;
	}

	/**
	 * @param cash - ��������/������ ��������
	 */
	public void setCash(boolean cash)
	{
		this.cash = cash;
	}

	/**
	 * @return ����� ����������, � ������� ���� ��������� ��������
	 */
	public String getDeviceNumber()
	{
		return deviceNumber;
	}

	/**
	 * @param deviceNumber - ����� ����������, � ������� ���� ��������� ��������
	 */
	public void setDeviceNumber(String deviceNumber)
	{
		this.deviceNumber = deviceNumber;
	}

	/**
	 * @return ����� ��������� (�����), ������� ����� ��������
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId - ����� ��������� (�����), ������� ����� ��������
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

	/**
	 * @return ��������� �������� 
	 */
	public CardOperationCategory getCategory()
	{
		return category;
	}

	/**
	 * @param category - ��������� ��������
	 */
	public void setCategory(CardOperationCategory category)
	{
		this.category = category;
	}

	/**
	 * @return �������� ��������, ������������ �� ���-����
	 */
	public String getOriginalCategoryName()
	{
		return originalCategoryName;
	}

	/**
	 * @param originalCategoryName - �������� ��������, ������������ �� ���-����
	 */
	public void setOriginalCategoryName(String originalCategoryName)
	{
		this.originalCategoryName = originalCategoryName;
	}

	/**
	 * @return MCC-���
	 */
	public Long getMccCode()
	{
		return mccCode;
	}

	/**
	 * @param mccCode - MCC-���
	 */
	public void setMccCode(Long mccCode)
	{
		this.mccCode = mccCode;
	}

	/**
	 * @return ��� ��������
	 */
	public OperationType getOperationType()
	{
		return operationType;
	}

	/**
	 * @param operationType - ��� ��������
	 */
	public void setOperationType(OperationType operationType)
	{
		this.operationType = operationType;
	}

	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}

	public String toString()
	{
		return "CardOperation{" +
				"id=" + id +
				", externalId='" + externalId + '\'' +
				", date=" + DateHelper.toISO8601DateFormat(date) +
				", cardNumber='" + MaskUtil.getCutCardNumberForLog(cardNumber) + '\'' +
				", description='" + description + '\'' +
				", cardAmount=" + cardAmount +
				", nationalAmount=" + nationalAmount +
				", deviceNumber=" + deviceNumber +
				", cash=" + cash +
				", ownerLoginId=" + ownerId +
				", categoryId=" + (category != null ? category.getId() : "null") +
				", mccCode=" + (mccCode != null ? mccCode : "null") +
				'}';
	}

	/**
	 * @return id ������ � ������� BUSINESS_DOCUMENTS
	 */
	public Long getBusinessDocumentId()
	{
		return businessDocumentId;
	}

	/**
	 * ���������� ������������� ������-���������
	 * @param businessDocumentId ������������� ������
	 */
	public void setBusinessDocumentId(Long businessDocumentId)
	{
		this.businessDocumentId = businessDocumentId;
	}

	/**
	 * �������� ������ �������� �� ������ ��������
	 * @param that ������ ��������
	 */
	public void updateFrom(CardOperation that)
	{
		this.setLoadDate(that.loadDate);
		this.setCash(that.cash);
		this.setExternalId(that.externalId);
		this.setDeviceNumber(that.getDeviceNumber());
		this.setCardAmount(that.getCardAmount());
		this.setMccCode(that.getMccCode());
		this.setOriginalDescription(that.getDescription());
		this.setOriginalCategoryName(that.getOriginalCategoryName());
		if (this.getCategory() == null)
		{
			this.setCategory(that.getCategory());
		}
	}

	/**
	 * @return ������ �� ��������
	 */
	public Boolean getHidden()
	{
		return hidden;
	}

	/**
	 * ���������� ��������� ��������
	 * @param hidden ���������
	 */
	public void setHidden(Boolean hidden)
	{
		this.hidden = hidden;
	}

	public CountryCode getClientCountry()
	{
		return clientCountry;
	}

	public void setClientCountry(CountryCode clientCountry)
	{
		this.clientCountry = clientCountry;
	}

	public CountryCode getOriginalCountry()
	{
		return originalCountry;
	}

	public void setOriginalCountry(CountryCode originalCountry)
	{
		this.originalCountry = originalCountry;
	}
}
