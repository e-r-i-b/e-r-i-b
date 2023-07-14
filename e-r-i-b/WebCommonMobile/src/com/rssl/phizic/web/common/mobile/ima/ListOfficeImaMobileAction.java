package com.rssl.phizic.web.common.mobile.ima;

import com.rssl.phizic.web.common.client.ima.ListOfficeIMAAction;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * Выбор офиса для открытия ОМС
 * @author Dorzhinov
 * @ created 16.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListOfficeImaMobileAction extends ListOfficeIMAAction
{
    protected String getFilterString(ListFormBase frm)
    {
        ListOfficeImaMobileForm form = (ListOfficeImaMobileForm) frm;
        return form.getSearch();
    }
}
