package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.XmlCalendarAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.ExcludeCategory;

import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Balovtsev
 * @since 12.05.2014
 */
@XmlRootElement(name = "body")
public class AlfHistoryRequestBody extends SimpleRequestBody
{
	private Long            categoryId;
	private Integer         paginationSize;
	private Integer         paginationOffset;
	private Boolean         income;
	private Boolean         showCash;
	private Boolean         showCashPayments;
	private Boolean         showOtherAccounts;
	private Boolean         showCreditCards;
	private Calendar        from;
	private Calendar        to;
	private List<Integer>   selectedCardId;
	private Boolean         showTransfers;

	/**
	 * Необязательный элемент
	 *
	 * @return Идентификатор категории
	 */
	@XmlElement(name = "categoryId", required = false)
	public Long getCategoryId()
	{
		return categoryId;
	}

	/**
	 * Необязательный элемент. Может встречаться несколько раз
	 * @return Идентификатор карты
	 */
	@XmlElement(name = "selectedCardId", required = false)
	public List<Integer> getSelectedCardId()
	{
		return selectedCardId;
	}

	/**
	 * Необязательный элемент
	 *
	 * @return Размер результирующей выборки. При отсутствии данного
	 * параметра вернутся все результаты соответствующие запросу
	 */
	@XmlElement(name = "paginationSize", required = false)
	public Integer getPaginationSize()
	{
		return paginationSize;
	}

	/**
	 * Необязательный элемент
	 *
	 * @return Смещение относительно начала выборки. По умолчанию
	 * равно нулю.
	 */
	@XmlElement(name = "paginationOffset", required = false)
	public Integer getPaginationOffset()
	{
		return paginationOffset == null ? 0 : paginationOffset;
	}

	/**
	 * Обязательный элемент
	 *
	 * @return Признак приходной операции
	 */
	@XmlElement(name = "income", required = true)
	public Boolean getIncome()
	{
		return income;
	}

	/**
	 * Обязательный элемент
	 *
	 * @return Показывать ли операции с наличными
	 */
	@XmlElement(name = "showCash", required = true)
	public Boolean getShowCash()
	{
		return showCash;
	}

	/**
	 * Обязательный элемент
	 *
	 * @return Отображать траты наличными
	 */
	@XmlElement(name = "showCashPayments", required = true)
	public Boolean getShowCashPayments()
	{
		return showCashPayments;
	}

	/**
	 * Обязательный элемент
	 *
	 * @return Показывать ли операции по дополнительным
	 * картам к чужим счетам
	 */
	@XmlElement(name = "showOtherAccounts", required = true)
	public Boolean getShowOtherAccounts()
	{
		return showOtherAccounts;
	}

	/**
	 * Обязательный элемент
	 *
	 * @return Дата начала построения списка операций
	 */
	@XmlJavaTypeAdapter(XmlCalendarAdapter.class)
	@XmlElement(name = "from", required = true)
	public Calendar getFrom()
	{
		return from;
	}

	/**
	 * Обязательный элемент
	 *
	 * @return Дата окончания построения списка операций
	 */
	@XmlJavaTypeAdapter(XmlCalendarAdapter.class)
	@XmlElement(name = "to", required = true)
	public Calendar getTo()
	{
		return to;
	}

	/**
	 * Необязательный элемент
	 *
	 * @return Нужно ли отображать переводы
	 */
	@XmlElement(name = "showTransfers", required = false)
	public Boolean getShowTransfers()
	{
		return showTransfers;
	}

	/**
	 * Обязательный элемент
	 *
	 * @return Показывать ли операции по кредитным картам
	 */
	@XmlElement(name = "showCreditCards", required = true)
	public Boolean getShowCreditCards()
	{
		return showCreditCards;
	}

	public void setCategoryId(Long categoryId)
	{
		this.categoryId = categoryId;
	}

	public void setPaginationSize(Integer paginationSize)
	{
		this.paginationSize = paginationSize;
	}

	public void setPaginationOffset(Integer paginationOffset)
	{
		this.paginationOffset = paginationOffset;
	}

	public void setIncome(Boolean income)
	{
		this.income = income;
	}

	public void setShowCash(Boolean showCash)
	{
		this.showCash = showCash;
	}

	public void setShowCashPayments(Boolean showCashPayments)
	{
		this.showCashPayments = showCashPayments;
	}

	public void setShowOtherAccounts(Boolean showOtherAccounts)
	{
		this.showOtherAccounts = showOtherAccounts;
	}

	public void setShowCreditCards(Boolean showCreditCards)
	{
		this.showCreditCards = showCreditCards;
	}

	public void setFrom(Calendar from)
	{
		this.from = from;
	}

	public void setTo(Calendar to)
	{
		this.to = to;
	}

	public void setSelectedCardId(List<Integer> selectedCardId)
	{
		this.selectedCardId = selectedCardId;
	}

	public void setShowTransfers(Boolean showTransfers)
	{
		this.showTransfers = showTransfers;
	}
}
