package com.rssl.phizic.web.common.client.deposits;

import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Kosyakov
 * @ created 12.05.2006
 * @ $Author: egorovaav $
 * @ $Revision: 73169 $
 */

public class DepositDetailsForm extends EditFormBase
{
    private String html;
    private String       name;
	private String description;
	protected String group;
	protected String segment;

    public String getHtml()
    {
        return html;
    }

    public void setHtml(String html)
    {
        this.html = html;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getDescription() {
        return description;
    }

	public void setDescription(String description){
		this.description = description;
	}

	public String getGroup()
	{
		return group;
	}

	public void setGroup(String group)
	{
		this.group = group;
	}

	public String getSegment()
	{
		return segment;
	}

	public void setSegment(String segment)
	{
		this.segment = segment;
	}
}
