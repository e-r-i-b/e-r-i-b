package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.tree.TreeLeaf;
import com.rssl.phizic.business.tree.TreeNode;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.rssl.phizic.operations.finances.FinanceHelper.CASH_PAYMENTS_ID;

/**
 * @author lepihina
 * @ created 22.04.14
 * $Author$
 * $Revision$
 * �������� ��� ����������� ������ � ���, ��� ���� ������ �� ������ (������ ������� � ���� ������).
 */
public class FilterCardFinanceOperation extends FinancesOperationBase
{
	private TreeNode root;

	public void initialize(String selectedId) throws BusinessException, BusinessLogicException
	{
		super.initialize();
		List<CardLink> allCards = getPersonCardLinks();
		List<CardLink> mainCards = getFilterCardLinks(new MainCardFilter(), allCards);
		List<CardLink> overdraftCards = getFilterCardLinks(new OverdraftCardFilter(), mainCards);
		List<CardLink> creditCards = getFilterCardLinks(new CreditCardFilter(), mainCards);
		List<CardLink> debitMainCards = getFilterCardLinks(new DebitCardFilter(), mainCards);
		List<CardLink> allAdditionalCards  = new ArrayList<CardLink>(allCards);
		allAdditionalCards.removeAll(mainCards);
		List<CardLink> ownAdditionalCards = getFilterCardLinks(new FullOwnCardFilter(), allAdditionalCards);
		List<CardLink> otherAdditionalCards = new ArrayList<CardLink>(allAdditionalCards);
		otherAdditionalCards.removeAll(ownAdditionalCards);

		root = new TreeNode(null, null);
		TreeNode filter = new TreeNode("��� ����� � ��������", "allCardsAndCash");
		root.add(filter);

		TreeNode allFilterCards = new TreeNode("��� �����", "allCards");
		filter.add(allFilterCards);

		if(CollectionUtils.isNotEmpty(debitMainCards) || CollectionUtils.isNotEmpty(creditCards) || CollectionUtils.isNotEmpty(overdraftCards))
		{
			TreeNode main = allFilterCards;
			if (CollectionUtils.isNotEmpty(allAdditionalCards))
			{
				main = new TreeNode("�������� �����", "mainCards");
				allFilterCards.add(main);
			}
			if (CollectionUtils.isNotEmpty(debitMainCards))
			{
				addCardGroup(main, debitMainCards, "��������� �����", "debitCards");
			}

			if (CollectionUtils.isNotEmpty(overdraftCards))
			{
				addCardGroup(main, overdraftCards, "������������ �����", "overdraftCards");
			}

			if (CollectionUtils.isNotEmpty(creditCards))
			{
				addCardGroup(main, creditCards, "��������� �����", "creditCards");
			}
		}

		if(CollectionUtils.isNotEmpty(ownAdditionalCards))
		{
			addCardGroup(allFilterCards, ownAdditionalCards, "�������������� �����, ���������� �� ��� ��������� �������� �����", "ownAdditionalCards");
		}
		if(CollectionUtils.isNotEmpty(otherAdditionalCards))
		{
			addCardGroup(allFilterCards, otherAdditionalCards, "�������������� �����, ���������� �� �� ��� ��������� �������� �����", "otherAdditionalCards");
		}

		filter.add(new TreeLeaf(CASH_PAYMENTS_ID, "����� ���������"));

		if (StringHelper.isNotEmpty(selectedId))
			root.setSelected(selectedId);
		else
			root.setSelected("allCardsAndCash");
	}

	private void addCardGroup(TreeNode root, List<CardLink> links, String groupName, String idName)
	{
		TreeNode element = new TreeNode(groupName, idName);
		for(CardLink link : links)
			element.add(new TreeLeaf(link.getId(), CardsUtil.getFullFormatCardName(link)));
		root.add(element);
	}

	protected List<CardLink> getSelectedCards(List<Long> cardIds)
	{
		List<CardLink> selectedCards = null;
		if(CollectionUtils.isNotEmpty(cardIds))
		{
			selectedCards =	new ArrayList<CardLink>();
			for(CardLink card : getPersonCardLinks())
				if(cardIds.contains(card.getId()))
					selectedCards.add(card);
		}
		else
			selectedCards = getPersonCardLinks();
		return selectedCards;
	}

	/**
	 * @return ������ �������� �������.
	 */
	public TreeNode getRoot()
	{
		return root;
	}
}
