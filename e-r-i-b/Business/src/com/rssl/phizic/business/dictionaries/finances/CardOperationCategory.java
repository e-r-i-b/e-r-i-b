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
 * ��������� ��������� ��������
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
	 * ������ "����������� ��������� � ��������� ������������� � ��� ��������"
	 * ��. CardOperation.isCompatibleToCategory()
	 */
	private boolean incompatibleOperationsAllowed = true;
	private String color;

	/**
	 * @return ID ���������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - ID ���������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return �������� ���������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name - �������� ���������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * ������ "��������� �������� �������� / ��������� ��������"
	 * @return true = �������� ��������, false = ���������
	 */
	public boolean isIncome()
	{
		return income;
	}

	/**
	 * @param income - true - ��������� �������� �������� ��������
	 */
	public void setIncome(boolean income)
	{
		this.income = income;
	}
	
	/**
	 * @return true - ��������� �������� �������� �� ��������� �������
	 */
	public boolean isCash()
	{
		return cash;
	}

	/**
	 * @param cash - true - ��������� �������� �������� �� ��������� �������
	 */
	public void setCash(boolean cash)
	{
		this.cash = cash;
	}

	/**
	 * @return true - ��������� �������� �������� �� ������������ �������
	 */
	public boolean isCashless()
	{
		return cashless;
	}

	/**
	 * @param cashless - true - ��������� �������� �������� �� ������������ �������
	 */
	public void setCashless(boolean cashless)
	{
		this.cashless = cashless;
	}

	/**
	 * @return ������������ ������ ������������ �������� ���������
	 * ���� null, ��������� ����� ��� ���� �������������
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId - ������������ ������ ������������ �������� ���������
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

	/**
	 * @return ������������� ��������� �� ������� �������
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * @param externalId - ������������� ��������� �� ������� �������
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return true - ����������� ��������� � ��������� ������������� � ��� ��������
	 */
	public boolean isIncompatibleOperationsAllowed()
	{
		return incompatibleOperationsAllowed;
	}

	/**
	 * @param incompatibleOperationsAllowed - true - ����������� ��������� � ��������� ������������� � ��� ��������
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
	 * ���������� ������� �� ��������� (��� �������� ����������� �� �������� �������)  
	 * @param that - �������� ��� ����������
	 */
	public void updateFrom(DictionaryRecord that)
	{
		Long oldId = getId();
		super.updateFrom(that);
		//����� �� ���������� Id ���������
		this.setId(oldId);
		// BeanHelper null ���������� � 0, ���������� �������.
		this.setOwnerId(null);
	}

	/**
	 * @return true - ��������� �������� ���������, ������������� �� ���������
	 */
	public boolean getIsDefault()
	{
		return isDefault;
	}

	/**
	 * @param isDefault - true - ��������� �� ���������
	 */
	public void setIsDefault(boolean isDefault)
	{
		this.isDefault = isDefault;
	}

	/**
	 * @return true - ��������� ��� ��������, ������� �������� ���������� ����� ������ �������
	 */
	public boolean isForInternalOperations()
	{
		return forInternalOperations;
	}

	/**
	 * @param forInternalOperations - ��������� ��� ��������, ������� �������� ���������� ����� ������ �������
	 */
	public void setForInternalOperations(boolean forInternalOperations)
	{
		this.forInternalOperations = forInternalOperations;
	}

	/**
	 * @return true - ��������� ������������� ��� ���������
	 */
	public boolean getIsTransfer()
	{
		return isTransfer;
	}

	/**
	 * @param transfer - ������������� �� ��������� ��� ��������� (true - ��� ���������)
	 */
	public void setIsTransfer(boolean transfer)
	{
		isTransfer = transfer;
	}

	/**
	 * @return ID � mAPI
	 */
	public String getIdInmAPI()
	{
		return idInmAPI;
	}

	/**
	 * @param idInmAPI - ID � mAPI
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
	 * @param visible - true - ��������� ������ ��������
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	/**
	 * @return - true - ��������� ������ ��������
	 */
	public boolean getVisible()
	{
		return visible;
	}

	/**
	 * @return ���� ���������
	 */
	public String getColor()
	{
		return color;
	}

	/**
	 * @param color - ���� ���������
	 */
	public void setColor(String color)
	{
		this.color = color;
	}
}
