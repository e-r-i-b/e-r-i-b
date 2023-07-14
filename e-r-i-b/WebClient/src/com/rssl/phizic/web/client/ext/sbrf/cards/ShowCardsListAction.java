package com.rssl.phizic.web.client.ext.sbrf.cards;

import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.DocumentNotice;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.sbnkd.SberbankForEveryDayHelper;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollHelper;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.web.client.ShowAccountsForm;
import com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 22.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowCardsListAction extends ShowAccountsExtendedAction
{
	private static final BusinessDocumentService service = new BusinessDocumentService();

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
	    ShowAccountsForm frm = (ShowAccountsForm) form;
	    PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	    ActivePerson person = personData.getPerson();

	    try
		{
			if (checkAccess(GetCardAbstractOperation.class) && checkAccess(GetCardsOperation.class))
			{
				setCardAbstract(frm);
			}
		}
	    catch (InactiveExternalSystemException e)
	    {
		    saveInactiveESMessage(request, e);
	    }

        frm.setUser(person);

	    if(frm.isAllCardDown() )
	    {
		    ActionMessages msgs = new ActionMessages();
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по картам из АБС временно недоступна. Повторите операцию позже.", false));
		    saveMessages(request, msgs);
		    return mapping.findForward(FORWARD_SHOW);
	    }

	    if (SberbankForEveryDayHelper.availableSBNKD())
		    frm.setClaimSBNKDData(SberbankForEveryDayHelper.findClaimDataByOwnerId(person.getLogin().getId(), false));

		//Поиск заявок на кредитные карты
	    //Макс приод поиска берём из конфига кредитной заявки
	    LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
	    ClientConfig clientConfig = ConfigFactory.getConfig(ClientConfig.class);
	    int limit = (int)clientConfig.getClaimsLimit();
	    int maxResult = limit + 1;
	    frm.setLoanCardClaimList(service.findCardClaimByOwnerLoginId(person.getLogin(), config.getPeriodRequestLoanStatus(), maxResult));
	    frm.setLoanCardClaimCount(frm.getLoanCardClaimList().size());

	    List<CardLink> blockedCardLinks = frm.getBlockedCards();
        if (CollectionUtils.isNotEmpty(blockedCardLinks))
        {
	        List<String> cardNames = new ArrayList<String>();
	        GetCardsOperation getCardsOperation = createOperation(GetCardsOperation.class);
	        for(CardLink blockedCardLink: blockedCardLinks)
	        {
		        if(blockedCardLink.getClosedState() != null && blockedCardLink.getClosedState())
		        {
			        cardNames.add(blockedCardLink.getName());
			        getCardsOperation.setCardLinksFalseClosedState(blockedCardLink.getNumber());
		        }
	        }
	        if(CollectionUtils.isNotEmpty(cardNames))
	        {
		        BankrollHelper bankrollHelper = new BankrollHelper(GateSingleton.getFactory());
                Map<String, String> messaage = bankrollHelper.createBlockedLinkMessage(cardNames);

                ActionMessages msgs = new ActionMessages();
                msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("cardInfoBundle", messaage.get("captionKey")), false));
                msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(messaage.get("bodyText") + getResourceMessage("cardInfoBundle", messaage.get("bodyKey")), false));
                saveMessages(request, msgs);
	        }
        }
		//для отображнеия статуса заявки на изменения кредитного лимита перекладываем оповещения из сессии в реквест и удаляем из сессии
	    if (request.getSession().getAttribute("documentNotice") != null)
	    {
		    currentRequest().setAttribute("documentNotice", request.getSession().getAttribute("documentNotice"));
		    request.getSession().removeAttribute("documentNotice");
	    }

	    return mapping.findForward(FORWARD_SHOW);
    }

	protected List<CardLink> getPersonCardLinks(GetCardsOperation operationCards)
	{
		return operationCards.getPersonCardLinks();
	}
}
