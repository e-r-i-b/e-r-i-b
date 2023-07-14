package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.common.types.Entity;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * @author Erkin
 * @ created 25.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Категория карточной операции
 */
@SuppressWarnings({"ClassNameSameAsAncestorName"})
public class CardOperationCategory extends DictionaryRecordBase implements com.rssl.phizic.gate.ips.CardOperationCategory, Entity
{
	private Long id;
	private String name;
	private boolean income;
	private boolean cash;
	private boolean cashless;
	private Long ownerId;
	private boolean visible = true;
	private String externalId;
	private boolean isDefault;
	private boolean forInternalOperations;
	private boolean isTransfer;
	private String idInmAPI;
	/**
	 * Флажок "допускается добавлять в категорию несовместимые с ней операции"
	 * См. CardOperation.isCompatibleToCategory()
	 */
	private boolean incompatibleOperationsAllowed = true;
	private String color;

	/**
	 * @return ID категории
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - ID категории
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return название категории
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name - название категории
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Флажок "категория содержит доходные / расходные операции"
	 * @return true = доходные операции, false = расходные
	 */
	public boolean isIncome()
	{
		return income;
	}

	/**
	 * @param income - true - категория содержит доходные операции
	 */
	public void setIncome(boolean income)
	{
		this.income = income;
	}
	
	/**
	 * @return true - категория содержит операции по наличному расчёту
	 */
	public boolean isCash()
	{
		return cash;
	}

	/**
	 * @param cash - true - категория содержит операции по наличному расчёту
	 */
	public void setCash(boolean cash)
	{
		this.cash = cash;
	}

	/**
	 * @return true - категория содержит операции по безналичному расчёту
	 */
	public boolean isCashless()
	{
		return cashless;
	}

	/**
	 * @param cashless - true - категория содержит операции по безналичному расчёту
	 */
	public void setCashless(boolean cashless)
	{
		this.cashless = cashless;
	}

	/**
	 * @return идентификтор логина пользователя владельц категории
	 * Если null, категория общая для всех пользователей
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId - идентификтор логина пользователя владельц категории
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

	/**
	 * @return Идентификатор категории из внешней системы
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * @param externalId - Идентификатор категории из внешней системы
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return true - допускается добавлять в категорию несовместимые с ней операции
	 */
	public boolean isIncompatibleOperationsAllowed()
	{
		return incompatibleOperationsAllowed;
	}

	/**
	 * @param incompatibleOperationsAllowed - true - допускается добавлять в категорию несовместимые с ней операции
	 */
	public void setIncompatibleOperationsAllowed(boolean incompatibleOperationsAllowed)
	{
		this.incompatibleOperationsAllowed = incompatibleOperationsAllowed;
	}

	public Comparable getSynchKey()
	{
		return this.externalId;
	}

	/**
	 * Обновление данными из источника (при загрузке справочника из внешнего ресурса)  
	 * @param that - источник для обновления
	 */
	public void updateFrom(DictionaryRecord that)
	{
		Long oldId = getId();
		super.updateFrom(that);
		//Чтобы не обновлялся Id категории
		this.setId(oldId);
		// BeanHelper null превращает в 0, возвращаем обратно.
		this.setOwnerId(null);
	}

	/**
	 * @return true - категория является категорий, установленной по умолчанию
	 */
	public boolean getIsDefault()
	{
		return isDefault;
	}

	/**
	 * @param isDefault - true - категория по умолчанию
	 */
	public void setIsDefault(boolean isDefault)
	{
		this.isDefault = isDefault;
	}

	/**
	 * @return true - категория для операций, которые являются переводами между своими картами
	 */
	public boolean isForInternalOperations()
	{
		return forInternalOperations;
	}

	/**
	 * @param forInternalOperations - категория для операций, которые являются переводами между своими картами
	 */
	public void setForInternalOperations(boolean forInternalOperations)
	{
		this.forInternalOperations = forInternalOperations;
	}

	/**
	 * @return true - категория предназначена для переводов
	 */
	public boolean getIsTransfer()
	{
		return isTransfer;
	}

	/**
	 * @param transfer - предназначена ли категория для переводов (true - для переводов)
	 */
	public void setIsTransfer(boolean transfer)
	{
		isTransfer = transfer;
	}

	/**
	 * @return ID в mAPI
	 */
	public String getIdInmAPI()
	{
		return idInmAPI;
	}

	/**
	 * @param idInmAPI - ID в mAPI
	 */
	public void setIdInmAPI(String idInmAPI)
	{
		this.idInmAPI = idInmAPI;
	}

	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || !(o instanceof CardOperationCategory))
			return false;

		CardOperationCategory category = (CardOperationCategory) o;

		if (id == null || category.id == null)
			return false;
		return id.equals(category.id);
	}

	public String toString()
	{
		return "CardOperationCategory{" +
				"id=" + id +
				", externalId=" + externalId +
				", name='" + name + '\'' +
				", income=" + income +
				", cash=" + cash +
				", cashless=" + cashless +
				", ownerLoginId=" + ownerId +
				", incompatibleOperationsAllowed=" + incompatibleOperationsAllowed +
				'}';
	}

	/**
	 * @param visible - true - категория видима клиентам
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	/**
	 * @return - true - категория видима клиентам
	 */
	public boolean getVisible()
	{
		return visible;
	}

	/**
	 * @return цвет категории
	 */
	public String getColor()
	{
		return color;
	}

	/**
	 * @param color - цвет категории
	 */
	public void setColor(String color)
	{
		this.color = color;
	}
}
