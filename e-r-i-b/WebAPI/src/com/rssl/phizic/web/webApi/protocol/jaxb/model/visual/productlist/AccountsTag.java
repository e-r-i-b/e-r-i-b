package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Òýã "accounts"
 * @author Jatsky
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"status", "accounts"})
@XmlRootElement(name = "accounts")
public class AccountsTag
{
	private Status status;
	private List<AccountTag> accounts;

	@XmlElement(name = "status", required = true)
	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	@XmlElement(name = "account")
	public List<AccountTag> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(List<AccountTag> accounts)
	{
		this.accounts = accounts;
	}
}
