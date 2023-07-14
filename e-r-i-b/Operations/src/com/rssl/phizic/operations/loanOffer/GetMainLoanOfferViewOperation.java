package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.offers.LoanOffer;

import java.util.List;

/**
 * User: Moshenko
 * Date: 31.05.2011
 * Time: 12:11:59
 * Отображение кредитных предложений,в верхней части главной страницы
 */
public class GetMainLoanOfferViewOperation extends GetLoanOfferViewOperationBase
{
    public void initialize() throws BusinessException, BusinessLogicException
    {
	    super.initialize();
        //если логин не первый
	   Login login = personData.getLogin();
       if (!login.isFirstLogin())
	       loanOffers = getOneLoanOfferByLogin();
       else
           loanOffers = null;
    }

    private List<LoanOffer> getOneLoanOfferByLogin() throws BusinessException
    {
        //для отображения берем только одно последнее предложения на получение кредита, только не показанное.
       try
       {
	       return personData.getLoanOfferByPersonData(1,false);
       }
       catch (Exception e)
       {
           getFailOffer(personData.getLogin(),e);
       }
         return null;
    }
}
