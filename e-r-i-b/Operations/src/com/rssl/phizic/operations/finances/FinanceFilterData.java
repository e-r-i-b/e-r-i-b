package com.rssl.phizic.operations.finances;

import java.util.Calendar;

/**
 * ������ ��� �������� ���������� ������� � ����������� "������ ������ ��������"
 * @author rydvanskiy
 * @ created 15.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class FinanceFilterData
{
	// ��� �������
	private String typeDate;
	// ���� �
	private Calendar fromDate;
	// ���� ��
	private Calendar toDate;
	// ��������� �������� � ���������
	private Boolean cash;
	// �������� �� ��������, � ��������� ������ ���������
	private Boolean income;
	// �������� �� �������� ���������� ����������
	private Boolean isTransfer;
	// ������� ������������, ��� ���������� ������������ ������ ����� ���������� ������� �������� ��� ������������
	private boolean onlyOwnCards;

	private boolean creditCards;
	// ���������� ����� ���������
	private Boolean showCashPayments;
	private String selectedCardIds;
	private Calendar maxOperationLoadDate;
	private String[] excludeCategories;
	private String operationCountry;
	private int maxResult;
	private int firstResult;
	private Long categoryId;
	private Boolean onlyCash;
	private Boolean internalTransfer;
	private Boolean noTransfer;

	public String getTypeDate()
	{
		return typeDate;
	}

	public void setTypeDate(String typeDate)
	{
		this.typeDate = typeDate;
	}

	public Calendar getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(Calendar fromDate)
	{
		this.fromDate = fromDate;
	}

	public Calendar getToDate()
	{
		return toDate;
	}

	public void setToDate(Calendar toDate)
	{
		this.toDate = toDate;
	}

	public boolean isOnlyOwnCards()
	{
		return onlyOwnCards;
	}

	public void setOnlyOwnCards(Boolean onlyOwnCards)
	{
		this.onlyOwnCards = onlyOwnCards;
	}

	public boolean isCash()
	{
		return cash;
	}

	public void setCash(Boolean cash)
	{
		this.cash = cash;
	}

	public Boolean isIncome()
	{
		return income;
	}

	public void setIncome(Boolean income)
	{
		this.income = income;
	}

	/**
	 * @return �������� �� �������� ���������� ���������� (true - ��������)
	 */
	public Boolean isTransfer()
	{
		return isTransfer;
	}

	/**
	 * @param transfer - �������� �� �������� ���������� ����������
	 */
	public void setTransfer(Boolean transfer)
	{
		isTransfer = transfer;
	}

	public boolean isCreditCards()
	{
		return creditCards;
	}

	public void setCreditCards(boolean creditCards)
	{
		this.creditCards = creditCards;
	}

	/**
	 * @return true - ���������� ����� ���������
	 */
	public Boolean getShowCashPayments()
	{
		return showCashPayments;
	}

	/**
	 * @param showCashPayments - ���������� �� ����� ���������
	 */
	public void setShowCashPayments(Boolean showCashPayments)
	{
		this.showCashPayments = showCashPayments;
	}

	/**
	 * @return �������������� ��������� ���� � �������
	 */
	public String getSelectedCardIds()
	{
		return selectedCardIds;
	}

	/**
	 * @param selectedCardIds - �������������� ��������� ���� � �������
	 */
	public void setSelectedCardIds(String selectedCardIds)
	{
		this.selectedCardIds = selectedCardIds;
	}

	/**
	 * @return ������������ ���� �������� ��������
	 */
	public Calendar getMaxOperationLoadDate()
	{
		return maxOperationLoadDate;
	}

	/**
	 * @param maxOperationLoadDate - ������������ ���� �������� ��������
	 */
	public void setMaxOperationLoadDate(Calendar maxOperationLoadDate)
	{
		this.maxOperationLoadDate = maxOperationLoadDate;
	}

	/**
	 * @return ����������� ��������� ��������
	 */
	public String[] getExcludeCategories()
	{
		return excludeCategories;
	}

	/**
	 * @param excludeCategories - ����������� ��������� ��������
	 */
	public void setExcludeCategories(String[] excludeCategories)
	{
		this.excludeCategories = excludeCategories;
	}

	/**
	 * @return ������ ���������� ��������
	 */
	public String getOperationCountry()
	{
		return operationCountry;
	}

	/**
	 * @param operationCountry - ������ ���������� ��������
	 */
	public void setOperationCountry(String operationCountry)
	{
		this.operationCountry = operationCountry;
	}

	public void setMaxResult(int maxResult)
	{
		this.maxResult = maxResult;
	}

	public int getMaxResult()
	{
		return maxResult;
	}

	public void setFirstResult(int firstResult)
	{
		this.firstResult = firstResult;
	}

	public int getFirstResult()
	{
		return firstResult;
	}

	public void setCategoryId(Long categoryId)
	{
		this.categoryId = categoryId;
	}

	public Long getCategoryId()
	{
		return categoryId;
	}

	public Boolean isOnlyCash()
	{
		return onlyCash;
	}

	/**
	 * @param onlyCash ���� true - ������ ��������, ���� false - ��������� ��������
	 */
	 public void setOnlyCash(Boolean onlyCash)
	 {
	 this.onlyCash = onlyCash;
	 }

	 public Boolean isInternalTransfer()
	 {
	 return internalTransfer;
	 }

	/**
	 * @param internalTransfer ���� true - ���������� �������� �������� ����� ������ �������
	 */
	 public void setInternalTransfer(Boolean internalTransfer)
	 {
	 this.internalTransfer = internalTransfer;
	 }

	 /**
	 * @param noTransfer ���� true - ���������� �������� �� ��������� � ���������
	 */
	public void setNoTransfer(Boolean noTransfer)
	{
		this.noTransfer = noTransfer;
	}

	public Boolean isNoTransfer()
	{
		return noTransfer;
	}
}
