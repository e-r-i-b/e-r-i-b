package com.rssl.phizic.web.client.component;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.loanOffer.GetLoanCardOfferViewOperation;
import com.rssl.phizic.operations.loanOffer.GetLoanOfferViewOperation;
import com.rssl.phizic.operations.widget.OffersWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.web.component.WidgetForm;

import java.security.AccessControlException;

/**
 * @author Dorzhinov
 * @ created 15.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class OffersWidgetAction extends WidgetActionBase
{
	protected WidgetOperation createWidgetOperation() throws BusinessException, AccessControlException
	{
		return createOperation(OffersWidgetOperation.class);
	}

	protected void updateForm(WidgetForm form, WidgetOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateForm(form, operation);

		OffersWidgetForm frm = (OffersWidgetForm) form;
		OffersWidgetOperation widgetOperation = (OffersWidgetOperation) operation;
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

		//Заполняем предодобренные заявки:
		//На НЕвиджетовой странице возможно максимум 3 предложения (1 на кред. карту и 2 на кредит).
		//Но на виджетовой бизнес хочет настраивать максимальное кол-во предложений в виджете (3, 6 или 12).
		//Пока требований к пропорциям предложений нет, кладем так: 1 на кред. карту (т.к. отношение "клиент - кред.карта" one-to-one), а все остальное до лимита заполняем предложениями на кредит.
		GetLoanCardOfferViewOperation cardOfferOperation = createOperation(GetLoanCardOfferViewOperation.class);
		cardOfferOperation.initialize(false);
		frm.setLoanCardOffers(cardOfferOperation.getLoanCardOffers());
		
		GetLoanOfferViewOperation loanOfferOperation = createOperation(GetLoanOfferViewOperation.class);
		loanOfferOperation.initialize(widgetOperation.getWidget().getNumberOfShowItems() - 1);
		frm.setLoanOffers(loanOfferOperation.getLoanOffers());


	}
}
