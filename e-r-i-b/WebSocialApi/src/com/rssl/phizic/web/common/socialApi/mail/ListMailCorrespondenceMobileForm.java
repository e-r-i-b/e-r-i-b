package com.rssl.phizic.web.common.socialApi.mail;

import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * Форма получения переписки
 * @author Dorzhinov
 * @ created 17.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListMailCorrespondenceMobileForm extends ActionFormBase
{
	//in
	private Long parentId;

	//out
	private List<Mail> correspondence;

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	public List<Mail> getCorrespondence()
	{
		return correspondence;
	}

	public void setCorrespondence(List<Mail> correspondence)
	{
		this.correspondence = correspondence;
	}
}
