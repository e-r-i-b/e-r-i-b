package com.rssl.phizic.web.common.socialApi;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.finances.targets.GetTargetOperation;
import com.rssl.phizic.operations.ima.GetIMAccountOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanListOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.util.MobileApiWebUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Получение списка продуктов
 * @author Omeliyanchuk
 * @ created 09.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class ShowAccountsExtendedAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        ActionMessages msgs = new ActionMessages();
	    ShowAccountsForm frm = (ShowAccountsForm) form;
        PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	    ActivePerson person = personData.getPerson();

	    if (checkAccess(GetCardsOperation.class))
	        setCardsInfo(frm);

        frm.setUser(person);

	    if(frm.isAllCardDown())
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по картам из АБС временно недоступна. Повторите операцию позже.", false));

	    if(msgs.size()!=0)
	        saveErrors(request, msgs);
	    
        return mapping.findForward(FORWARD_SHOW);
	}

	private void setCardsInfo(ShowAccountsForm form) throws BusinessException, BusinessLogicException
	{
		GetCardsOperation operationCards = createOperation(GetCardsOperation.class);
		//получаем все кардлинки клиента. Те кардлинки, которые прописаны в PersonContext.
		List<CardLink> personCardLinks = getPersonCardLinks(operationCards);
		form.setCards(personCardLinks);

		List<CardLink> personMainCardLinks = operationCards.getPersonMainCardLinks(personCardLinks);
		Map<Long, Long> cardsAdditionalInfo = new HashMap<Long, Long>();
		for(CardLink mainCard : personMainCardLinks)
		{
			String mainCardNumber = mainCard.getNumber();
			for (CardLink card : personCardLinks)
			{
				if(StringHelper.equalsNullIgnore(card.getMainCardNumber(), mainCardNumber))
					cardsAdditionalInfo.put(card.getId(), mainCard.getId());
			}
		}

		form.setMainCardIds(cardsAdditionalInfo);
		form.setAllCardDown(personCardLinks.size()==0 && operationCards.isBackError());
	}

	protected List<CardLink> getPersonCardLinks(GetCardsOperation operationCards)
	{
        return operationCards.getPersonCardLinks();
	}

}
