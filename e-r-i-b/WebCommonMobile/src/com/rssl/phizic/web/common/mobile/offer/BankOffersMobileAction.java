package com.rssl.phizic.web.common.mobile.offer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

/**
 * Список предложений банка (на входе и после)
 * @author Dorzhinov
 * @ created 10.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class BankOffersMobileAction extends BankOffersMobileActionBase
{
	@Override
	protected void updateForm(BankOffersMobileForm frm) throws BusinessException, BusinessLogicException
	{
	    if (MobileApiUtil.isMobileApiGT(MobileAPIVersions.V9_00))
	    {
	        //Для версии АПИ от 9.10.
		    super.updateForm(frm);
	    }
	    else
	    {
	        //Для версий до 9
		    if (frm.getIsLogin())
		    {
		        //на входе - показываем только непрочитанные
			    updateMainLoanOffer(frm);
			    updateMainCardLoanOffer(frm);
		    }
		    else
		    {
		        //после входа
			    updateLoanOffer(frm);
			    updateCardLoanOffer(frm);
		    }
	    }
    }
}
