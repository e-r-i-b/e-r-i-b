package com.rssl.phizic.web.client.deposits;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Kosyakov
 * @ created 12.05.2006
 * @ $Author: kosyakov $
 * @ $Revision: 1314 $
 */

public class DepositCalculatorForm extends ActionFormBase
{
    private Long         id;
    private StringBuffer html;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public StringBuffer getHtml()
    {
        return html;
    }

    public void setHtml(StringBuffer html)
    {
        this.html = html;
    }

}
