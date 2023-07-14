package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productdetail.AccountDetailTag;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Ответ на запрос детальной информации по вкладу
 * @author Jatsky
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"status", "account"})
@XmlRootElement(name = "message")
public class AccountDetailResponse extends Response
{
	private AccountDetailTag account;

	@XmlElement(name = "account", required = false)
	public AccountDetailTag getAccount()
	{
		return account;
	}

	public void setAccount(AccountDetailTag account)
	{
		this.account = account;
	}
}
