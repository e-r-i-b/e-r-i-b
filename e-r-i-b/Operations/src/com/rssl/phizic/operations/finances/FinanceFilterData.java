package com.rssl.phizic.operations.finances;

import java.util.Calendar;

/**
 * Объект для хранения параметров фильтра в функционале "Анализ личных финансов"
 * @author rydvanskiy
 * @ created 15.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class FinanceFilterData
{
	// тип периода
	private String typeDate;
	// дата с
	private Calendar fromDate;
	// дата по
	private Calendar toDate;
	// учитывать операции с наличными
	private Boolean cash;
	// является ли доходной, в противном случае расходная
	private Boolean income;
	// показать ли операции являющиеся переводами
	private Boolean isTransfer;
	// признак обозначающий, что необходимо использовать только карты владельцем которых является сам пользователь
	private boolean onlyOwnCards;

	private boolean creditCards;
	// отображать траты наличными
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
	 * @return показать ли операции являющиеся переводами (true - показать)
	 */
	public Boolean isTransfer()
	{
		return isTransfer;
	}

	/**
	 * @param transfer - показать ли операции являющиеся переводами
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
	 * @return true - отобразить траты наличными
	 */
	public Boolean getShowCashPayments()
	{
		return showCashPayments;
	}

	/**
	 * @param showCashPayments - отображать ли траты наличными
	 */
	public void setShowCashPayments(Boolean showCashPayments)
	{
		this.showCashPayments = showCashPayments;
	}

	/**
	 * @return идентификаторы выбранных карт в фильтре
	 */
	public String getSelectedCardIds()
	{
		return selectedCardIds;
	}

	/**
	 * @param selectedCardIds - идентификаторы выбранных карт в фильтре
	 */
	public void setSelectedCardIds(String selectedCardIds)
	{
		this.selectedCardIds = selectedCardIds;
	}

	/**
	 * @return максимальная дата загрузки операции
	 */
	public Calendar getMaxOperationLoadDate()
	{
		return maxOperationLoadDate;
	}

	/**
	 * @param maxOperationLoadDate - максимальная дата загрузки операции
	 */
	public void setMaxOperationLoadDate(Calendar maxOperationLoadDate)
	{
		this.maxOperationLoadDate = maxOperationLoadDate;
	}

	/**
	 * @return исключаемые категории операций
	 */
	public String[] getExcludeCategories()
	{
		return excludeCategories;
	}

	/**
	 * @param excludeCategories - исключаемые категории операций
	 */
	public void setExcludeCategories(String[] excludeCategories)
	{
		this.excludeCategories = excludeCategories;
	}

	/**
	 * @return страна совершения операции
	 */
	public String getOperationCountry()
	{
		return operationCountry;
	}

	/**
	 * @param operationCountry - страна совершения операции
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
	 * @param onlyCash если true - только наличные, если false - учитывать наличные
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
	 * @param internalTransfer если true - отображать операции перевода между своими счетами
	 */
	 public void setInternalTransfer(Boolean internalTransfer)
	 {
	 this.internalTransfer = internalTransfer;
	 }

	 /**
	 * @param noTransfer если true - отображать операции не связанные с переводом
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
