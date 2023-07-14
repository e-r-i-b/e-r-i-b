package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.utils.CurrencyUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Òýã "currency"
 * @author Jatsky
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"code", "name"})
@XmlRootElement(name = "currency")
public class CurrencyTag
{
	private String code;
	private String name;

	@XmlElement(name = "code", required = true)
	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	@XmlElement(name = "name", required = true)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public CurrencyTag()
	{
	}

	public CurrencyTag(Currency currency)
	{
		String currencyCode = currency.getCode();
		this.setCode(currencyCode);
		this.setName(CurrencyUtils.getCurrencySign(currencyCode));
	}

	public CurrencyTag(String code, String name)
	{
		this.code = code;
		this.name = name;
	}
}
