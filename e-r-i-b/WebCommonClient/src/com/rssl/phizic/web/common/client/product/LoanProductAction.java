package com.rssl.phizic.web.common.client.product;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.loans.product.LoanProductListOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mescheryakova
 * @ created 02.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanProductAction extends LoanActionBase
{
	private static final String TB = "tb";
	private static final String URL_TO_OFFERS =  "/private/payments/loan_offer.do";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("init", "start");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
    {
	  LoanProductListOperation operation = createOperation(LoanProductListOperation.class, "LoanProduct");
	  return operation;
    }

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		LoanProductListOperation loanProductListOperation = (LoanProductListOperation) operation;

		Map<String, Object> map = super.getDefaultFilterParameters(frm, loanProductListOperation);
		map.put(TB, loanProductListOperation.getTbId());

		return map;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter(TB, filterParams.get(TB));

	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanProductListOperation loanProductListOperation = (LoanProductListOperation) createListOperation((ListFormBase) form);
		LoanProductListForm frm = (LoanProductListForm) form;
		Long conditionId = Long.valueOf(frm.getLoanId());
		if (loanProductListOperation.productIsChange(conditionId,
			Long.parseLong(currentRequest().getParameter("changeDate"))))
		{
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("По выбранному кредиту изменились условия предоставления денежных средств. Пожалуйста, ознакомьтесь с новыми условиями.", false));
			saveMessages(request, messages);
			return start(mapping, form, request, response);
		}
		return super.next(mapping, form, request, response);
	}

	public String getFormNameForRedirect()
	{
		return FormConstants.LOAN_PRODUCT_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		LoanProductListOperation loanProductListOperation = (LoanProductListOperation) operation;
		boolean hasPassportRF = loanProductListOperation.checkClientPassportType();
		if (loanProductListOperation.checkLoanOfferClient() && hasPassportRF)
		{
			ActionMessages messages = new ActionMessages();
			String msgTxt = "Уважаемый клиент, для Вас есть индивидуальные предложения по оформлению кредита.";
			String loanOfferErrMsg = currentRequest().getParameter("errorMsg");
			// в мобильном Api недопустим присутствие тэгов в теле сообщения;
			ApplicationConfig applicationConfig = ApplicationConfig.getIt();
			if (applicationConfig.getApplicationInfo().isNotMobileApi())
			{
				if (StringHelper.isEmpty(loanOfferErrMsg))
				{
					msgTxt = msgTxt + " Ознакомиться с предложением банка можно <a class='orangeText' href='/"	+ ApplicationInfo.getCurrentApplication().name() + URL_TO_OFFERS + "'><span>здесь</span></a>.";
					messages.add(ActionMessages.GLOBAL_MESSAGE,	new ActionMessage(msgTxt,false));
				}
				else
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage(loanOfferErrMsg,false));
			}

			saveMessages(currentRequest(), messages);
		}
		else if (!hasPassportRF)
		{
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Обратите внимание! Оформить заявку на кредит может только резидент РФ.",false)
			);
			saveMessages(currentRequest(), messages);
		}
	}

}
