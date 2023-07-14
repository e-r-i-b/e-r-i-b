package com.rssl.phizic.web.common.client.product;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.loans.offer.LoanOfferListOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.ext.sbrf.loanclaim.LoanClaimFunctions;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mescheryakova
 * @ created 23.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanCardProductAction extends LoanActionBase
{
	private static final String FORWARD_OFFERS = "CardOffer";

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
    {
	    LoanOfferListOperation operation = getOperation();
	    Class clazz = getClass();
	    if(clazz.equals(LoanCardProductAction.class))
	    {
	        String incomeLevelId = currentRequest().getParameter("income");
	        operation.checkExistIncomeLevels(incomeLevelId);
	    }
	    return operation;
    }

	protected ActionForward createActionForward(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		LoanProductListForm frm = (LoanProductListForm) form;
		boolean useNewLoanClaimAlgorithm = LoanClaimFunctions.isUseNewLoanClaimAlgorithm();
		boolean useOldLoanClaimAlgorithm = !useNewLoanClaimAlgorithm;
		if ( frm.getDoRedirectToLoanCardOffer() == null ||
			 Application.PhizIC == LogThreadContext.getApplication() &&
			    ( useNewLoanClaimAlgorithm && PermissionUtil.impliesServiceRigid("LoanCardClaim") ) ||
			    ( useOldLoanClaimAlgorithm && PermissionUtil.impliesServiceRigid("LoanCardOffer") )	)
		{
			validateLoanCardProduct(frm);
			return super.createActionForward(frm);
		}
		else if (frm.getDoRedirectToLoanCardOffer() < 0)
			return doRedirectToListLoanCardOffer(frm);

		frm.setLoanId(frm.getDoRedirectToLoanCardOffer().toString());
		ActionForward actionForward = new ActionForward(getCurrentMapping().findForward(FORWARD_NEXT));
		actionForward.setPath(actionForward.getPath() + getParamsURL(frm));
		return actionForward;
	}

	private ActionForward doRedirectToListLoanCardOffer(ListFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_OFFERS);
	}

	/**
	 * @param form форма
	 * @return Возвращается имя запроса, привязанного к операции(по умолчанию "list").
	 */
	protected String getQueryName(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		return "card_product";
	}

	public String getFormNameForRedirect()
	{
		return FormConstants.LOAN_CARD_PRODUCT_FORM;
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanOfferListOperation operation = getOperation();
		LoanProductListForm frm = (LoanProductListForm) form;

		if (frm.getDoRedirectToLoanCardOffer() == null && CardsUtil.hasClientActiveCreditCard())
		{
			return start(mapping, form, request, response);
		}

		Long conditionId = Long.valueOf(frm.getLoanId());
		if (operation.productIsChange(conditionId,
			Long.parseLong(currentRequest().getParameter("changeDate"))))
		{
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("По выбранной кредитной карте изменились условия предоставления денежных средств. Пожалуйста, ознакомьтесь с новыми условиями.", false));
			saveMessages(request, messages);
			return start(mapping, form, request, response);
		}
		return super.next(mapping, form, request, response);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{

		LoanOfferListOperation loanProductListOperation = (LoanOfferListOperation) operation;

		LoanProductListForm frm = (LoanProductListForm) form;
		frm.setDoRedirectToLoanCardOffer(loanProductListOperation.checkLoanCardOfferClient());

		if (!loanProductListOperation.checkClientPassportType())
		{
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("Обратите внимание! Оформить заявку на кредит может только резидент РФ.",false)
			);
			saveMessages(currentRequest(), messages);
		}

		loanProductListOperation.sortProductObject(form.getData());
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		String incomeLevelId = currentRequest().getParameter("income");
		query.setParameter("incomeLevelId", incomeLevelId);
	}

	protected String getParamsURL(LoanProductListForm frm)
	{
		String incomeLevelId = currentRequest().getParameter("income");
		return super.getParamsURL(frm) + (StringHelper.isEmpty(incomeLevelId) ? "" : "&income=" + incomeLevelId);
	}

	protected void validateLoanCardProduct(LoanProductListForm frm) throws BusinessException, BusinessLogicException
	{
		getExistsCreditCardMessage(frm);
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

	protected LoanOfferListOperation getOperation() throws BusinessException, BusinessLogicException
	{
		return checkAccess(LoanOfferListOperation.class, "LoanCardOffer") ?
				createOperation(LoanOfferListOperation.class, "LoanCardOffer") :
				createOperation(LoanOfferListOperation.class, "LoanCardProduct");
	}
}

