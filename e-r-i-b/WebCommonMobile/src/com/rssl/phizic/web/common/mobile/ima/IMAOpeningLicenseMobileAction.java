package com.rssl.phizic.web.common.mobile.ima;

import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.web.ext.sbrf.payments.IMAOpeningLicenseAction;
import com.rssl.phizic.business.BusinessException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Заявка на открытие ОМС: Просмотр заявления
 * @author Dorzhinov
 * @ created 16.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class IMAOpeningLicenseMobileAction extends IMAOpeningLicenseAction
{
    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        IMAOpeningLicenseMobileForm frm = (IMAOpeningLicenseMobileForm) form;
        if(NumericUtil.isEmpty(frm.getImaId()) || NumericUtil.isEmpty(frm.getDocumentId()))
            throw new BusinessException("Не указан параметр imaId или documentId");

        frm.setImaProductId(frm.getImaId());
        return super.start(mapping, form, request, response);
    }
}
