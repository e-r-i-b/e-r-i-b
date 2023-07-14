package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.business.web.CardBlockWidget;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author Barinov
 * @ created 05.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class CardBlockWidgetOperation extends ProductBlockWidgetOperationBase<CardBlockWidget>
{
	private static final CardLinkComparator cardLinkComparator = new CardLinkComparator();

	private GetCardsOperation cardsOperation;

	private GetCardAbstractOperation cardAbstractsOperation;

	private List<CardLink> allCardLinks;

	@Override
	protected void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize();

		OperationFactory factory = getOperationFactory();

		cardsOperation = factory.create(GetCardsOperation.class);
	    allCardLinks = cardsOperation.getPersonCardLinks();
		sortCardLinks(allCardLinks);
		cardAbstractsOperation = factory.create(GetCardAbstractOperation.class);
		List<CardLink> showOperationCardLinks = cardsOperation.getShowOperationLinks(allCardLinks);
		cardAbstractsOperation.initialize(showOperationCardLinks);

		setUseStoredResource( cardsOperation.isUseStoredResource() );

		actualizeHiddenProducts(allCardLinks);
	}

	public List<CardLink> getCardLinks()
	{
		return Collections.unmodifiableList(allCardLinks);
	}

	public boolean isAllCardDown()
	{
		return CollectionUtils.isEmpty(allCardLinks) && (cardAbstractsOperation.isBackError() || cardsOperation.isBackError());
	}

	public Map<CardLink, CardAbstract> getCardAbstract(Long count)
	{
		return cardAbstractsOperation.getCardAbstract(count);
	}

	private void sortCardLinks(List<CardLink> cardLinks)
	{
		Collections.sort(cardLinks, cardLinkComparator);
	}

	/**
	 * —ортируем массив карт. Ќам нужно, чтобы дополнительные карты были под родительской.
	 */
	private static class CardLinkComparator implements Comparator<CardLink>
	{
		public int compare(CardLink o1, CardLink o2)
		{
			String mainCard1 = o1.isMain() ? o1.getNumber() : StringHelper.getEmptyIfNull(o1.getMainCardNumber());
			//»ногда дл€ дополнительных карт не приходит номер основной карты, хот€ должен.
			// ƒл€ избежани€ ошибки при сортировке добавлены дополнительные проверки (BUG036571)
			if (StringHelper.isEmpty(mainCard1))
				return -1;
			String mainCard2 = o2.isMain() ? o2.getNumber() : StringHelper.getEmptyIfNull(o2.getMainCardNumber());
			if (StringHelper.isEmpty(mainCard2))
				return 1;
			int res = mainCard1.compareTo(mainCard2);
			if (res == 0)
			{
				if (o1.isMain())
					return -1;
				else if (o2.isMain())
					return 1;
				else
					return 0;
			}
			return res;
		}
	}

	@Override
	public boolean checkUseStoredResource()
	{
		if(super.checkUseStoredResource())
		{
			throw new InactiveExternalSystemException(StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) allCardLinks.get(0).getCard()));
		}

		return false;
	}
}
