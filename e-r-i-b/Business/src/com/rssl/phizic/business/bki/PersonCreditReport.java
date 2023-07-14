package com.rssl.phizic.business.bki;

/**
 * @author Gulov
 * @ created 02.10.14
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.DeclensionUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * ��������� ������������� ���������� ������ �������.
 */
public class PersonCreditReport
{
	private static final String[] MONTHS = new String[]{"������", "�������", "����", "������", "���", "����", "����", "������", "��������", "�������", "������", "�������"};

	/**
	 * ��������� �������: 1, 2, 3, 4, 5
	 */
	private Integer rating;

	/**
	 * ������ �������� ��������
	 */
	private List<Credit> credits;

	/**
	 * ������ �������� ����
	 */
	private List<Card> cards;

	/**
	 * ���� ���������� ������� ��������� �������
	 */
	private Calendar updated;

	/**
	 * �������� ����� �� �������� � ������
	 */
	private List<Money> totalPayment;

	/**
	 * �������� ��������� �� �������� � ������
	 */
	private List<TotalArrears> totalArrears;

	/**
	 * �������� �������
	 */
	private List<Credit> closedCredits;

	/**
	 * �������� ����� �� �������� �������� � ������
	 */
	private List<Money> totalClosedCredits;

	/**
	 * �������� �����
	 */
	private List<Card> closedCards;

	/**
	 * ������� ��������� �������
	 */
	private List<CreditHistoryRequest> creditHistoryRequests;

	/**
	 * ������� ��������� ������� �� �������
	 */
	private List<CreditHistoryRequest> creditHistoryRequestsHalfYear;

	public boolean getMustUpdated()
	{
		return PersonContext.getPersonDataProvider().getPersonData().getMustUpdateCreditReport();
	}

	public Integer getRating()
	{
		return rating;
	}

	public void setRating(Integer rating)
	{
		this.rating = rating;
	}

	public void setCredits(List<Credit> credits)
	{
		this.credits = credits;
	}

	public List<Credit> getCredits()
	{
		return Collections.unmodifiableList(credits);
	}

	public void setUpdated(Calendar updated)
	{
		this.updated = updated;
	}

	public Calendar getUpdated()
	{
		return updated;
	}

	public String getMonthUpdated()
	{
		if (updated == null)
			return "";
		int monthNumber = updated.get(Calendar.MONTH);
		return MONTHS[monthNumber];
	}

	public void setTotalPayment(List<Money> totalPayment)
	{
		this.totalPayment = totalPayment;
	}

	public List<Money> getTotalPayment()
	{
		return Collections.unmodifiableList(totalPayment);
	}

	public void setTotalArrears(List<TotalArrears> totalArrears)
	{
		this.totalArrears = totalArrears;
	}

	public List<TotalArrears> getTotalArrears()
	{
		return Collections.unmodifiableList(totalArrears);
	}

	public List<CreditHistoryRequest> getCreditHistoryRequests()
	{
		return Collections.unmodifiableList(creditHistoryRequests);
	}

	public void setCreditHistoryRequests(List<CreditHistoryRequest> creditHistoryRequests)
	{
		this.creditHistoryRequests = creditHistoryRequests;
	}

	public List<CreditHistoryRequest> getCreditHistoryRequestsHalfYear()
	{
		return Collections.unmodifiableList(creditHistoryRequestsHalfYear);
	}

	public void setCreditHistoryRequestsHalfYear(List<CreditHistoryRequest> creditHistoryRequestsHalfYear)
	{
		this.creditHistoryRequestsHalfYear = creditHistoryRequestsHalfYear;
	}

	public String getCreditsCount()
	{
		return productCount(credits, cards);
	}

	public String getClosedCount()
	{
		return productCount(closedCredits, closedCards);
	}

	private String productCount(List<Credit> credits, List<Card> cards)
	{
		if (CollectionUtils.isEmpty(credits) && CollectionUtils.isEmpty(cards))
			return "";
		return creditCountString(credits.size(), cards.size());
	}

	private String creditCountString(int creditCount, int cardCount)
	{
		if (creditCount == 0 && cardCount == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		if (creditCount != 0)
			sb.append(creditCount + " " + DeclensionUtils.numeral(creditCount, "������", "", "�", "��"));
		if (cardCount != 0)
		{
			if (creditCount != 0)
				sb.append(" � ");
			String str1 = DeclensionUtils.numeral(cardCount, "�������", "��", "��", "��");
			String str2 = DeclensionUtils.numeral(cardCount, "����", "�", "�", "");
			sb.append(cardCount + " " + str1 + " " + str2);
		}
		return sb.toString();
	}

	public int getClosedCreditsCount()
	{
		return closedCredits.size();
	}

	public int getClosedCardsCount()
	{
		return closedCards.size();
	}

	public void setClosedCredits(List<Credit> closedCredits)
	{
		this.closedCredits = closedCredits;
	}

	public List<Credit> getClosedCredits()
	{
		return Collections.unmodifiableList(closedCredits);
	}

	public void setTotalClosedCredits(List<Money> totalClosedCredits)
	{
		this.totalClosedCredits = totalClosedCredits;
	}

	public List<Money> getTotalClosedCredits()
	{
		return Collections.unmodifiableList(totalClosedCredits);
	}

	public void setClosedCards(List<Card> closedCards)
	{
		this.closedCards = closedCards;
	}

	public List<Card> getClosedCards()
	{
		return Collections.unmodifiableList(closedCards);
	}

	public Integer getCreditHistoryRequestsCount()
	{
		return creditHistoryRequests.size();
	}

	public Integer getCreditHistoryRequestsHalfYearCount()
	{
		return creditHistoryRequestsHalfYear.size();
	}

	public void setCards(List<Card> cards)
	{
		this.cards = cards;
	}

	public List<Card> getCards()
	{
		return Collections.unmodifiableList(cards);
	}
}
