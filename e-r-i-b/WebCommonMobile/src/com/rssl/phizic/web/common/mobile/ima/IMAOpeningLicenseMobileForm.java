package com.rssl.phizic.web.common.mobile.ima;

import com.rssl.phizic.web.ext.sbrf.payments.IMAOpeningLicenseForm;

/**
 * Заявка на открытие ОМС: Просмотр заявления
 * @author Dorzhinov
 * @ created 16.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class IMAOpeningLicenseMobileForm extends IMAOpeningLicenseForm
{
    private Long imaId;

    public Long getImaId()
    {
        return imaId;
    }

    public void setImaId(Long imaId)
    {
        this.imaId = imaId;
    }
}
