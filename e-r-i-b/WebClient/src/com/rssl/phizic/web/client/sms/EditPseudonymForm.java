package com.rssl.phizic.web.client.sms;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author eMakarov
 * @ created 22.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditPseudonymForm extends ActionFormBase
{
	private Long pseudonymId;
	private String pseudonym;

	public Long getPseudonymId()
	{
		return pseudonymId;
	}

	public void setPseudonymId(Long pseudonymId)
	{
		this.pseudonymId = pseudonymId;
	}

	public String getPseudonym()
	{
		return pseudonym;
	}

	public void setPseudonym(String pseudonym)
	{
		this.pseudonym = pseudonym;
	}
}
