package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.ForceRedirectDocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.LoanOfferClaim;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.loanOffer.LoanOfferAmoutValidator;
import com.rssl.phizic.common.types.Money;

import java.util.Map;

/**
 * User: Moshenko
 * Date: 22.10.2012
 * Time: 13:09:11
 * ≈сли  введенна€ пользователем сумма кредита меньше либо равна разрешенной,
 * то переходим к оформлению за€вки на общих услови€х
 * ѕо запросу CHG046548: »зменить механизм работы за€вки на предодобренный кредит
 */
public class LoanOfferConvertHandler extends BusinessDocumentHandlerBase
{
	private static final String WARNING_MESSAGE_PARAM_NAME = "warning-message";
	private static final String URL_RO_REDIRECT = "urlToRedirect";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if ((document instanceof LoanOfferClaim))
		{
			LoanOfferClaim doc =(LoanOfferClaim)document;
			Map values = doc.getAttributes();
			ExtendedAttribute loanOfferIdAttr = (ExtendedAttribute)values.get(LoanOfferAmoutValidator.LOAN_PRODUCT_ID);
			String loanOfferId = (String)loanOfferIdAttr.getValue();
			ExtendedAttribute durationAttr = (ExtendedAttribute)values.get(LoanOfferAmoutValidator.DURATION);
			Long duration = (Long)durationAttr.getValue();
			Money amount = doc.getDestinationAmount();
			LoanOfferAmoutValidator amoutValidator = new LoanOfferAmoutValidator();
			try
			{
				if (!amoutValidator.validate(loanOfferId,duration,amount))
				{
					if (doc.isMoneyOutBounds())
					 //если признак уже установлен в true то делаем редирект
						throw new ForceRedirectDocumentLogicException("",getParameter(URL_RO_REDIRECT));
					else
					{//иначе устанавливаем признак дл€ понимани€ что
						doc.setMoneyOutBounds(true);
						throw new DocumentLogicException(getParameter(WARNING_MESSAGE_PARAM_NAME));
					}
				}
				else
					doc.setMoneyOutBounds(false);
			}
			catch (BusinessException e)
			{
			 throw new DocumentException(e);
			}
		}
	}
}
