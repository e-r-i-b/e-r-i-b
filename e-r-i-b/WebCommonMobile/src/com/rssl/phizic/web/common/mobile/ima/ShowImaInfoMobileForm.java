package com.rssl.phizic.web.common.mobile.ima;

import com.rssl.phizic.web.common.client.ima.IMAccountInfoForm;

/**
 * Детальная информация по ОМС
 * @author Dorzhinov
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowImaInfoMobileForm extends IMAccountInfoForm
{
    //in
    private String imAccountName;

    public String getImAccountName()
    {
        return imAccountName;
    }

    public void setImAccountName(String imAccountName)
    {
        this.imAccountName = imAccountName;
    }
}
