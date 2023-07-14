package com.rssl.phizic.web.common.socialApi.mail;

import com.rssl.phizic.business.mail.MailSubject;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author Dorzhinov
 * @ created 10.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListMailThemesMobileForm extends ActionFormBase
{
	//out
	private List<MailSubject> mailThemes;

	public List<MailSubject> getMailThemes()
	{
		return mailThemes;
	}

	public void setMailThemes(List<MailSubject> mailThemes)
	{
		this.mailThemes = mailThemes;
	}
}
