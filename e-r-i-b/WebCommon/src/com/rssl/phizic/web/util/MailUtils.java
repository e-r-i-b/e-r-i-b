package com.rssl.phizic.web.util;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.mail.MailDirection;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.business.mail.MailResponseMethod;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.StrutsUtils;

import java.util.List;

/**
 * @author koptyaev
 * @ created 26.08.13
 * @ $Author$
 * @ $Revision$
 */
public final class MailUtils
{

	public static String getPlainTextMail(Mail mail, String sender)
	{
		StringBuffer buf = new StringBuffer();
		buf.append(StrutsUtils.getMessage("label.number", "mailBundle"));
		buf.append(": ");
		buf.append(mail.getNum());
		buf.append("\r\n");

		if (mail.getTheme() != null)
		{
			buf.append(StrutsUtils.getMessage("label.mail.theme", "mailBundle"));
			buf.append(": ");
			buf.append(mail.getTheme().getDescription());
			buf.append("\r\n");
		}

		buf.append(StrutsUtils.getMessage("response_method", "mailBundle"));
		buf.append(": ");
		buf.append(mail.getResponseMethod().getDescription());
		buf.append("\r\n");

		if (StringHelper.isNotEmpty(mail.getPhone()) && mail.getResponseMethod() == MailResponseMethod.BY_PHONE)
		{
			buf.append(StrutsUtils.getMessage("label.phone", "mailBundle"));
			buf.append(": ");
			buf.append(mail.getPhone());
			buf.append("\r\n");
		}
		else if (StringHelper.isNotEmpty(mail.getEmail()) && mail.getResponseMethod() == MailResponseMethod.IN_WRITING)
		{
			buf.append(StrutsUtils.getMessage("label.email", "mailBundle"));
			buf.append(": ");
			buf.append(mail.getEmail());
			buf.append("\r\n");
		}
		if ( mail.getType() !=  null)
		{
			buf.append(StrutsUtils.getMessage("label.mailType", "mailBundle"));
			buf.append(": ");
			buf.append(mail.getType().getDescription());
			buf.append("\r\n");
		}

		if (mail.getDirection() == MailDirection.CLIENT)
			buf.append(StrutsUtils.getMessage("label.employee", "mailBundle"));
		else
			buf.append(StrutsUtils.getMessage("label.clientMail", "mailBundle"));
		buf.append(": ");
		buf.append(sender);
		buf.append("\r\n");

		buf.append(StrutsUtils.getMessage("label.subject", "mailBundle"));
		buf.append(": ");
		buf.append(mail.getSubject());
		buf.append("\r\n");

		buf.append(StrutsUtils.getMessage("label.message", "mailBundle"));
		buf.append(": ");
		buf.append(mail.getBody());
		buf.append("\r\n");

		buf.append(StrutsUtils.getMessage("label.attachments", "mailBundle"));
		buf.append(": ");
		buf.append(StringHelper.getEmptyIfNull(mail.getFileName()));
		buf.append("\r\n");

		return buf.toString();
	}

	public static String getPlainTextListMail(List<Mail> mails) throws BusinessException
	{
		StringBuffer buf = new StringBuffer();
		for (Mail mail: mails)
		{
			buf.append(getPlainTextMail(mail, MailHelper.getSenderFIO(mail)));
			buf.append("\r\n");
		}
		return buf.toString();
	}
}
