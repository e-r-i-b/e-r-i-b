package com.rssl.phizic.business.ermb.sms.config;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * Бин для XML-маппинга дефиниции СМС-команды
 */
@XmlType(name = "Command")
@XmlAccessorType(XmlAccessType.NONE)
class CommandDefinitionBean
{
	private static final String DEFAULT_PARSE_ORDER = "99";

    @XmlElement(required = true)
    private String codename;

	@XmlElement(required = true)
	private boolean enabled;

	@XmlElementWrapper(name = "keywords", required = true)
    @XmlElement(name = "keyword")
	private List<String> keywords;

	@XmlElement(required = true, defaultValue = DEFAULT_PARSE_ORDER)
	private int parseOrder;

	///////////////////////////////////////////////////////////////////////////

	String getCodename()
	{
		return codename;
	}

	void setCodename(String codename)
	{
		this.codename = codename;
	}

	boolean isEnabled()
	{
		return enabled;
	}

	void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	List<String> getKeywords()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return keywords;
	}

	void setKeywords(List<String> keywords)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.keywords = keywords;
	}

	int getParseOrder()
	{
		return parseOrder;
	}

	void setParseOrder(int parseOrder)
	{
		this.parseOrder = parseOrder;
	}
}
