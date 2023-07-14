package com.rssl.phizic.web.common.socialApi.ima;

import com.rssl.phizic.web.common.socialApi.common.FilterFormBase;
import com.rssl.phizic.gate.ima.IMAccountAbstract;

/**
 * Выписка по ОМС
 * @author Dorzhinov
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowImaAbstractMobileForm extends FilterFormBase
{
    private IMAccountAbstract imAccountAbstract;

    public IMAccountAbstract getImAccountAbstract()
    {
        return imAccountAbstract;
    }

    public void setImAccountAbstract(IMAccountAbstract imAccountAbstract)
    {
        this.imAccountAbstract = imAccountAbstract;
    }
}
