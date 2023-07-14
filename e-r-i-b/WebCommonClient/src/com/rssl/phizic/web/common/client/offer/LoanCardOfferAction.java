package com.rssl.phizic.web.common.client.offer;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.creditcards.CreditCardsUtil;
import com.rssl.phizic.business.loanCardClaim.LoanCardClaimHelper;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferShortCut;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.operations.loans.offer.LoanOfferListOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.product.LoanProductListForm;
import com.rssl.phizic.web.ext.sbrf.loanclaim.LoanClaimFunctions;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mescheryakova
 * @ created 14.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanCardOfferAction extends OperationalActionBase
{
	private static final String FORWARD_PAYMENT = "Payment";
	private static final String FORWARD_OFFERS = "CardOffer";
	protected static final String FORWARD_NEXT = "Next";

	private static final List<String> offerTypes = new ArrayList<String>(2){{
		add(LoanCardOfferType.changeType.toString());
		add(LoanCardOfferType.newCard.toString());
	}};

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map =   new HashMap<String, String>();
		map.put("button.next", "next");
		map.put("next", "next");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanProductListForm frm = (LoanProductListForm) form;
		LoanOfferListOperation loanOfferListOperation = getOperation();
		frm.setData(loanOfferListOperation.getCardOfferCompositProductCondition(offerTypes));
		return createActionForward(frm);
	}

	protected ActionForward createActionForward(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		LoanProductListForm frm = (LoanProductListForm) form;
		Long conditionId;
		try
		{
			LoanOfferListOperation operation = getOperation();
			conditionId = operation.checkLoanCardOfferClient();
			if (conditionId != null && conditionId.equals(LoanCardClaimHelper.CHANGE_TYPE_OFFER))
				frm.setChangeType(true);
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessException(e);
		}

		boolean haveRightForLoanCardClaim = PermissionUtil.impliesServiceRigid("LoanCardClaim");
		if (haveRightForLoanCardClaim &&
				LoanClaimFunctions.isUseNewLoanClaimAlgorithm() &&
				Application.PhizIC == LogThreadContext.getApplication())
		{
			LoanCardOfferShortCut loanOfferClaimType = CreditCardsUtil.getLoanOfferClaimType();
			if (loanOfferClaimType != null)
			{
				if ("LoanCardProduct".equals(loanOfferClaimType.getType()))
					return getCurrentMapping().findForward("AllLoanProductsClaim");
				else
				{
					ActionRedirect redirect = new ActionRedirect(getCurrentMapping().findForward("LoanCardClaim"));
					redirect.addParameter("offerId", loanOfferClaimType.getId());
					redirect.addParameter("offerType", loanOfferClaimType.getType());
					return redirect;
				}
			}
		}
		else if (conditionId != null && conditionId > LoanCardClaimHelper.NEW_CARD_OFFER)
		{
			ActionForward actionForward = new ActionForward(getCurrentMapping().findForward(FORWARD_PAYMENT));
			actionForward.setPath(actionForward.getPath() + "?form=LoanCardOffer&loan=" + conditionId
					+ "&changeLimit=true" + "&offerId=" + conditionId);

			return actionForward;
		}
		boolean useNewLoanClaimAlgorithm = LoanClaimFunctions.isUseNewLoanClaimAlgorithm();
		if ( frm.getDoRedirectToLoanCardOffer() == null ||
				Application.PhizIC == LogThreadContext.getApplication() &&
						( useNewLoanClaimAlgorithm && PermissionUtil.impliesServiceRigid("LoanCardClaim") ) ||
				( !useNewLoanClaimAlgorithm && PermissionUtil.impliesServiceRigid("LoanCardOffer") )	)
		{
			validateLoanCardProduct(frm);
			frm.setPaymentForm(getFormNameForRedirect());
			return getCurrentMapping().findForward(FORWARD_START);
		}
		else if (frm.getDoRedirectToLoanCardOffer() < 0)
			return getCurrentMapping().findForward(FORWARD_OFFERS);

		frm.setLoanId(frm.getDoRedirectToLoanCardOffer().toString());
		ActionForward actionForward = new ActionForward(getCurrentMapping().findForward(FORWARD_NEXT));
		actionForward.setPath(actionForward.getPath() + getParamsURL(frm));
		return actionForward;
	}

	public String getFormNameForRedirect()
	{
		return FormConstants.LOAN_CARD_OFFER_FORM;
	}

	protected String getParamsURL(LoanProductListForm frm)
	{
		String params = "?form=" + getFormNameForRedirect() + "&loan=" + frm.getLoanId() + "&offerId=" + frm.getOfferId();

		if (frm.isChangeType())
			params += "&changeType=true";

		return params;
	}

	protected void validateLoanCardProduct(LoanProductListForm frm) throws BusinessException, BusinessLogicException
	{
		//если тип предложения = "изменение типа и лимита карты", то валидировать не нужно.
		if(!frm.isChangeType())
			getExistsCreditCardMessage(frm);
	}

	protected LoanOfferListOperation getOperation() throws BusinessException, BusinessLogicException
	{
		return checkAccess(LoanOfferListOperation.class, "LoanCardOffer") ?
				createOperation(LoanOfferListOperation.class, "LoanCardOffer") :
				createOperation(LoanOfferListOperation.class, "LoanCardProduct");
	}

	protected void getExistsCreditCardMessage(LoanProductListForm frm) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (CardsUtil.hasClientActiveCreditCard())
			{
				frm.setLoanCardClaimAvailable(false);
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						StrutsUtils.getMessage("credit.card.exist", "loansBundle"), false));
				saveErrors(currentRequest(), msgs);
			}
		}
		catch(BusinessException e)
		{
			throw new BusinessException("Ошибка получения карт клиента");
		}
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanProductListForm frm = (LoanProductListForm) form;
		ActionForward actionForward = new ActionForward(getCurrentMapping().findForward(FORWARD_NEXT));
		actionForward.setPath(actionForward.getPath() + getParamsURL(frm));

		return actionForward;
	}
}
