package com.rssl.phizic.web.common.socialApi.ima;

import com.rssl.phizic.web.common.client.ima.ListOfficeIMAForm;

/**
 * Выбор офиса для открытия ОМС
 * @author Dorzhinov
 * @ created 16.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListOfficeImaMobileForm extends ListOfficeIMAForm
{
    private String search;

    public String getSearch()
    {
        return search;
    }

    public void setSearch(String search)
    {
        this.search = search;
    }
}
