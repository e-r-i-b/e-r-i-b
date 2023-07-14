package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import static com.rssl.phizic.logging.Constants.SHOW_CARDS_ON_MAIN_PAGE_KEY;
import com.rssl.phizic.operations.widget.CardBlockWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.web.component.WidgetForm;
import com.rssl.phizic.web.common.client.Constants;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 08.06.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Виджет "блок с картами клиента"
 */
public class CardBlockWidgetAction extends WidgetActionBase
{
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation(CardBlockWidgetOperation.class);
	}

	protected void updateForm(WidgetForm form, WidgetOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateForm(form, operation);

		CardBlockWidgetForm frm = (CardBlockWidgetForm)form;
		CardBlockWidgetOperation oper = (CardBlockWidgetOperation)operation;

		Calendar start = GregorianCalendar.getInstance();

		List<CardLink> cardLinks = oper.getCardLinks();
		Map<CardLink, CardAbstract> cardAbstract = oper.getCardAbstract(Constants.MAX_COUNT_OF_TRANSACTIONS);

		frm.setCards(cardLinks);
		frm.setCardAbstract(cardAbstract);
		frm.setAllCardDown(oper.isAllCardDown());

		List<Long> showCardLinkIds = oper.getShowProducts();
		frm.setShowCardLinkIds(showCardLinkIds);

		if (!cardLinks.isEmpty())
			writeLogInformation(start, "Получение остатка по картам", SHOW_CARDS_ON_MAIN_PAGE_KEY);
	}
}
