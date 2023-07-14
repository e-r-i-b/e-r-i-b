package com.rssl.phizic.web.common.mobile.offer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

/**
 * ������ ����������� ����� (�� ����� � �����)
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
	        //��� ������ ��� �� 9.10.
		    super.updateForm(frm);
	    }
	    else
	    {
	        //��� ������ �� 9
		    if (frm.getIsLogin())
		    {
		        //�� ����� - ���������� ������ �������������
			    updateMainLoanOffer(frm);
			    updateMainCardLoanOffer(frm);
		    }
		    else
		    {
		        //����� �����
			    updateLoanOffer(frm);
			    updateCardLoanOffer(frm);
		    }
	    }
    }
}
