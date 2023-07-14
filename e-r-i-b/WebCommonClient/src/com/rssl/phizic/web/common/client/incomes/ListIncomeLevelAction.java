package com.rssl.phizic.web.common.client.incomes;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.creditcards.CreditCardsUtil;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferShortCut;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.creditcards.incomes.GetIncomeLevelListOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.ext.sbrf.loanclaim.LoanClaimFunctions;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 04.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListIncomeLevelAction extends ListActionBase
{
	private static final String FORWARD_NEXT = "Next";
	private static final String FORWARD_OFFERS = "CardOffer";
	private static final String FORWARD_CLAIM = "Claim";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.next", "next");
		map.put("next", "next");
		map.put("init", "start");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetIncomeLevelListOperation.class, "LoanCardProduct");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListIncomeLevelForm frm = (ListIncomeLevelForm) form;

		if (frm.getDoRedirectToLoanCardOffer() == null && CardsUtil.getClientCreditCard() != null)
		{
			return start(mapping, form, request, response);
		}
		
		GetIncomeLevelListOperation operation = (GetIncomeLevelListOperation) createListOperation((ListFormBase) form);
		ActionForward actionForward;
		final Long incomeId = getIncomeId(frm);
		if (operation.productByIncomeExists(incomeId))
		{
			actionForward = new ActionForward(getCurrentMapping().findForward(FORWARD_NEXT));
			actionForward.setPath(actionForward.getPath() + "?income=" + incomeId);
			return actionForward;
		}
		else
		{
			ActionMessage msg = new ActionMessage("Для указанного Вами уровня дохода банк не выдает кредитных карт", false);
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveErrors(request, msgs);
			return start(mapping, form, request, response);
		}
	}

	protected ActionForward createActionForward(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ListIncomeLevelForm frm = (ListIncomeLevelForm) form;
		boolean redirectToAllProducts = false;
		boolean useNewLoanClaimAlgorithm = LoanClaimFunctions.isUseNewLoanClaimAlgorithm();
		//Право на создание заявки на предодобренную кредитную карту (новый механизм)
		boolean haveRightForNewLoanCardClaim = PermissionUtil.impliesServiceRigid("LoanCardClaim");
		//Право на создание заявки на предодобренную кредитную карту (старый механизм)
		boolean haveRightForLoanCardClaim = PermissionUtil.impliesServiceRigid("LoanCardOffer");

		if ( useNewLoanClaimAlgorithm &&
			 Application.PhizIC == LogThreadContext.getApplication() &&
			 haveRightForNewLoanCardClaim )
		{
			LoanCardOfferShortCut loanOfferClaimType = CreditCardsUtil.getLoanOfferClaimType();
			if (loanOfferClaimType != null && !"LoanCardProduct".equals(loanOfferClaimType.getType()))
			{
				ActionRedirect redirect = new ActionRedirect(getCurrentMapping().findForward("LoanCardClaim"));
				redirect.addParameter("offerId", loanOfferClaimType.getId());
				return redirect;
			}
			redirectToAllProducts = true;
		}
		//редирект на стр. с общими условиями если:
		// - используется старый алгоритм работы с кредитами
		// - это основное приложение
		// - недоступна "Заявка на предодобренную кредитную карту (старый механизм)", то
		if ( !useNewLoanClaimAlgorithm &&
			  Application.PhizIC == LogThreadContext.getApplication() &&
			  !haveRightForLoanCardClaim )
			redirectToAllProducts = true;

		if ( frm.getDoRedirectToLoanCardOffer() == null ||
			 (!haveRightForNewLoanCardClaim && !haveRightForLoanCardClaim) ||
			 redirectToAllProducts)
		{
			//если нет диапазонов доходов, то редирект на страницу со всеми продуктами по кредитным картам
			if(!frm.isIncomeLevelsExists())
				return doRedirectToAllProductList();
			else
			{
				//Проверка на наличие у клиента кредитных карт Сбербанка
				if (CardsUtil.getCreditCardAllCards() != null)
				{
					frm.setLoanCardClaimAvailable(false);
					ActionMessages msgs = new ActionMessages();
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							StrutsUtils.getMessage("credit.card.exist", "loansBundle"), false));
					saveErrors(currentRequest(), msgs);
				}
				return super.createActionForward(frm);
			}
		}
		//Редирект на форму с предложениями по кредитным картам
		else if (frm.getDoRedirectToLoanCardOffer() < 0)
			return doRedirectToListLoanCardOffer();
		else
		{
			frm.setLoanId(frm.getDoRedirectToLoanCardOffer().toString());
			ActionForward actionForward = new ActionForward(getCurrentMapping().findForward(FORWARD_CLAIM));
			actionForward.setPath(actionForward.getPath() + getParamsURL(frm));
			return actionForward;
		}
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		GetIncomeLevelListOperation op = (GetIncomeLevelListOperation) operation;
		ListIncomeLevelForm frm = (ListIncomeLevelForm) form;
		frm.setDoRedirectToLoanCardOffer(op.checkLoanCardOfferClient());
		frm.setIncomeLevelsExists(op.isIncomeLevelsExists());
		if (!frm.isFromRefresh())
			frm.setTimeToRefresh(op.getTimeToRefresh());
		else
		{
			frm.setTimeToRefresh(0);
			log.error("Назавершенный запрос в CRM. Превышен интервал ожидания.");
		}
	}

	private ActionForward doRedirectToListLoanCardOffer() throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_OFFERS);
	}

	private ActionForward doRedirectToAllProductList() throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_NEXT);
	}

	/**
	 * Получить строку get-параметров
	 * @param frm - форма, с нее читаем данные для передачи
	 * @return  строка параметров
	 */
	private String getParamsURL(ListIncomeLevelForm frm)
	{
		return "?form=" + FormConstants.LOAN_CARD_OFFER_FORM + "&loan=" + frm.getLoanId() +
				"&changeLimit=true"+"&offerId=" + frm.getLoanId();
	}

	protected Long getIncomeId(ListIncomeLevelForm form)
	{
		return form.getSelectedId();
	}
}
