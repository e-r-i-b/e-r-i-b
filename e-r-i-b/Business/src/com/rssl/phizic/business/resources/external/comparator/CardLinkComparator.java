package com.rssl.phizic.business.resources.external.comparator;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.bankroll.CardState;

import java.util.Comparator;

/**
 * @author kuzmin
 * @ created 09.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class CardLinkComparator  extends ProductComparator  implements Comparator<Pair<CardLink,CardAbstract>>
{
	public int compare(Pair<CardLink,CardAbstract> o1, Pair<CardLink,CardAbstract> o2)
	{
		Card card1 = o1.getFirst().getCard();
		Card card2 = o2.getFirst().getCard();

		if (card1.getCardState() == null || card2.getCardState() == null)
		{
			int result = compareToNull(card1.getCardState(), card2.getCardState());
			if (result != 0)
				return result;
		}

		//������������ ����� �� �������� "��������"
		if (CardState.active == card1.getCardState() && CardState.active != card2.getCardState())
		{
			return -1;
		}

		if (CardState.active != card1.getCardState() && CardState.active == card2.getCardState())
		{
			return 1;
		}

		CardAbstract abs1 = o1.getSecond();
		CardAbstract abs2 = o2.getSecond();

		//��������� � �����, � ������� �������� �������
		if (abs1 == null || abs2 == null)
		{
			int result = compareToNull(abs2,  abs1);
			if(result != 0)
			{
				return result;
			}
		}
		else
		{
			//��������� � �����, �� ������� ���� ��������
			if(abs1.getTransactions() == null || abs2.getTransactions()== null)
			{
				int result = compareToNull(abs2.getTransactions(), abs1.getTransactions());
				if(result != 0)
				{
					return result;
				}
			}
			else
			{
				//������������ �����, �� ������� ���� ��������� ��������� �������� � ����� ������� ����
				if (!abs1.getTransactions().isEmpty() && !abs2.getTransactions().isEmpty())
				{
					int result = abs2.getTransactions().get(0).getDate().compareTo(abs1.getTransactions().get(0).getDate());
					if (result != 0)
						return result;
				}
				if (abs1.getTransactions().isEmpty() && !abs2.getTransactions().isEmpty())
				{
					return 	1;
				}
				if (!abs1.getTransactions().isEmpty() && abs2.getTransactions().isEmpty())
				{
					return 	-1;
				}
			}
		}

		if (card1.getAvailableLimit() == null || card2.getAvailableLimit() == null)
		{
			int result = compareToNull(card1.getAvailableLimit(), card2.getAvailableLimit());
			if (result != 0)
				return result;
		}
		else
		{
			//������������ ����� � ���������� ��������
			int result = card1.getAvailableLimit().compareTo(card2.getAvailableLimit());
			if (result != 0)
				return result;
		}

		if (card1.getIssueDate() == null || card2.getIssueDate() == null)
		{
			return compareToNull(card1.getIssueDate(), card2.getIssueDate());
		}

		//������������ ����� � ����� ������� ����� ��������
		return card1.getIssueDate().compareTo(card2.getIssueDate());
	}
}
