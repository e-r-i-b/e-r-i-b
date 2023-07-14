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
 * Операция по карте
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
	 * @return ID операции
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - идентификатор операции
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return внешний ID операции (например, в ИПС)
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * @param externalId - внешний ID операции (<id_операции_в_ИПС>^<login_id>)
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return дата+время проведения операции
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @param date - дата+время проведения операции
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * @return дата загрузки операциии в систему
	 */
	public Calendar getLoadDate()
	{
		return loadDate;
	}

	/**
	 * @param loadDate - дата загрузки операциии в систему
	 */
	public void setLoadDate(Calendar loadDate)
	{
		this.loadDate = loadDate;
	}

	/**
	 * @return номер карты, по которой проведена операция
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	/**
	 * @param cardNumber - номер карты, по которой проведена операция
	 */
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	/**
	 * @return описание операции вместе с местом её совершения, пришедшее из ИПС
	 */
	public String getOriginalDescription()
	{
		return originalDescription;
	}

	/**
	 * @param originalDescription - описание операции вместе с местом её совершения, пришедшее из ИПС
	 */
	public void setOriginalDescription(String originalDescription)
	{
		this.originalDescription = originalDescription;
	}

	/**
	 * @return описание операции (доступно для редактирования клиенту)
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description - описание операции (доступно для редактирования клиенту)
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return сумма операции в валюте карты
	 */
	public BigDecimal getCardAmount()
	{
		return cardAmount;
	}

	/**
	 * @param cardAmount - сумма операции в валюте карты
	 */
	public void setCardAmount(BigDecimal cardAmount)
	{
		this.cardAmount = cardAmount;
	}

	/**
	 * @return сумма операции в национальной валюте
	 */
	public BigDecimal getNationalAmount()
	{
		return nationalAmount;
	}

	/**
	 * @param nationalAmount - сумма операции в национальной валюте
	 */
	public void setNationalAmount(BigDecimal nationalAmount)
	{
		this.nationalAmount = nationalAmount;
	}

	/**
	 * @return true - операция является внесением/выдачей наличных
	 */
	public boolean isCash()
	{
		return cash;
	}

	/**
	 * @param cash - внесение/выдача наличных
	 */
	public void setCash(boolean cash)
	{
		this.cash = cash;
	}

	/**
	 * @return номер устройства, в котором была проведена операция
	 */
	public String getDeviceNumber()
	{
		return deviceNumber;
	}

	/**
	 * @param deviceNumber - номер устройства, в котором была проведена операция
	 */
	public void setDeviceNumber(String deviceNumber)
	{
		this.deviceNumber = deviceNumber;
	}

	/**
	 * @return логин владельца (карты), который провёл операцию
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId - логин владельца (карты), который провёл операцию
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

	/**
	 * @return категория операции 
	 */
	public CardOperationCategory getCategory()
	{
		return category;
	}

	/**
	 * @param category - категория операции
	 */
	public void setCategory(CardOperationCategory category)
	{
		this.category = category;
	}

	/**
	 * @return название операции, определенной по МСС-коду
	 */
	public String getOriginalCategoryName()
	{
		return originalCategoryName;
	}

	/**
	 * @param originalCategoryName - название операции, определенной по МСС-коду
	 */
	public void setOriginalCategoryName(String originalCategoryName)
	{
		this.originalCategoryName = originalCategoryName;
	}

	/**
	 * @return MCC-код
	 */
	public Long getMccCode()
	{
		return mccCode;
	}

	/**
	 * @param mccCode - MCC-код
	 */
	public void setMccCode(Long mccCode)
	{
		this.mccCode = mccCode;
	}

	/**
	 * @return тип операции
	 */
	public OperationType getOperationType()
	{
		return operationType;
	}

	/**
	 * @param operationType - тип операции
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
	 * @return id записи в таблице BUSINESS_DOCUMENTS
	 */
	public Long getBusinessDocumentId()
	{
		return businessDocumentId;
	}

	/**
	 * Установить идентификатор бизнес-документа
	 * @param businessDocumentId идентификатор записи
	 */
	public void setBusinessDocumentId(Long businessDocumentId)
	{
		this.businessDocumentId = businessDocumentId;
	}

	/**
	 * Обновить данные сущности из другой сущности
	 * @param that другая сущность
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
	 * @return скрыта ли операция
	 */
	public Boolean getHidden()
	{
		return hidden;
	}

	/**
	 * Установить видимость операции
	 * @param hidden видимость
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
