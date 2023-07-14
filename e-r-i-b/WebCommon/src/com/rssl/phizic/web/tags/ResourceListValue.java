package com.rssl.phizic.web.tags;

import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;

/**
 * @author sergunin
 * @ created 28.04.14
 * @ $Author$
 * @ $Revision$
 *
 * ƒл€ случа€ когда списочное поле - продукт клиента (card, account, loan imaccount).
 */

public class ResourceListValue extends ListValue {

    private ExternalResourceLink link = null;

   	public ResourceListValue()
   	{
   	}

    public ResourceListValue(String value, String id)
    {
        super(value, id);
    }

    public ResourceListValue(String value, String id, ExternalResourceLink link)
    {
        super(value, id);
        this.link = link;
    }

    public ExternalResourceLink getLink() {
        return link;
    }

    public void setLink(ExternalResourceLink link) {
        this.link = link;
    }

}
