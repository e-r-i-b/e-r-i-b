package com.rssl.phizic.web.common.mobile.offer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.loanOffer.GetLoanCardOfferViewOperation;
import com.rssl.phizic.operations.loanOffer.GetLoanOfferViewOperation;
import com.rssl.phizic.operations.loanOffer.GetMainLoanCardOfferViewOperation;
import com.rssl.phizic.operations.loanOffer.GetMainLoanOfferViewOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 25.01.15
 * Time: 22:35
 * Базовый класс для отображения списка предодобренных предложений(mAPI)
 */
abstract public class BankOffersMobileActionBase extends OperationalActionBase
{
	private static final String CARD = "card";
	private static final String LOAN = "loan";

    final public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    BankOffersMobileForm frm = (BankOffersMobileForm) form;

	    updateForm(frm);

	    return mapping.findForward(FORWARD_START);
    }

	protected void updateForm(BankOffersMobileForm frm) throws BusinessException, BusinessLogicException
	{
		if (frm.getIsLogin())
		{
			//на входе - показываем только непрочитанные
			if (StringHelper.isEmpty(frm.getClaimType()))
			{
				updateMainLoanOffer(frm);
				updateMainCardLoanOffer(frm);
			}
			else
			{
				if (StringHelper.equals(frm.getClaimType(), LOAN))
					updateMainLoanOffer(frm);
				else if (StringHelper.equals(frm.getClaimType(), CARD))
					updateMainCardLoanOffer(frm);
				else
					throw new BusinessException("В параметре сlaimType допустимо передавать только значения 'card' или 'loan'");
			}
		}
		else
		{
			if (StringHelper.isEmpty(frm.getClaimType()))
			{
				updateLoanOffer(frm);
				updateCardLoanOffer(frm);
			}
			else
			{
				//после входа
				if (StringHelper.equals(frm.getClaimType(), LOAN))
					updateLoanOffer(frm);
				else if (StringHelper.equals(frm.getClaimType(), CARD))
					updateCardLoanOffer(frm);
				else
					throw new BusinessException("В параметре сlaimType допустимо передавать только значения 'card' или 'loan'");
			}
		}
	}

	protected void updateMainLoanOffer(BankOffersMobileForm frm) throws BusinessException, BusinessLogicException
	{
		if (checkAccess(GetMainLoanOfferViewOperation.class))
		{
			GetMainLoanOfferViewOperation operation = createOperation(GetMainLoanOfferViewOperation.class);
			operation.initialize();
			frm.setLoanOffers(operation.getLoanOffers());
			operation.informByLoanOffers();
			operation.setAllLoanOfferLikeViewed();
		}
	}

	protected void updateMainCardLoanOffer(BankOffersMobileForm frm) throws BusinessException, BusinessLogicException
	{
		if (checkAccess(GetMainLoanCardOfferViewOperation.class))
		{
			GetMainLoanCardOfferViewOperation operation = createOperation(GetMainLoanCardOfferViewOperation.class);
			operation.initialize(true);
			frm.setLoanCardOffers(operation.getLoanCardOffers());
			operation.informByLoanCardOffers();
			operation.setAllLoanCardOfferLikeViewed();
		}
	}

	protected void updateLoanOffer(BankOffersMobileForm frm) throws BusinessException, BusinessLogicException
	{
		if (checkAccess(GetLoanOfferViewOperation.class))
		{
			GetLoanOfferViewOperation operation = createOperation(GetLoanOfferViewOperation.class);
			operation.initialize(true);
			frm.setLoanOffers(operation.getLoanOffers());
			operation.informByLoanOffers();
		}
	}

	protected void updateCardLoanOffer(BankOffersMobileForm frm) throws BusinessException, BusinessLogicException
	{
		if (checkAccess(GetLoanCardOfferViewOperation.class))
		{
			GetLoanCardOfferViewOperation operation = createOperation(GetLoanCardOfferViewOperation.class);
			operation.initialize(true);
			frm.setLoanCardOffers(operation.getLoanCardOffers());
			operation.informByLoanCardOffers();
		}
	}
}
