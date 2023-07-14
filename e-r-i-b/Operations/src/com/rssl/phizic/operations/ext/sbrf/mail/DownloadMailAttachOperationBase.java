package com.rssl.phizic.operations.ext.sbrf.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.mail.MailService;
import com.rssl.phizic.business.mail.MailState;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author kligina
 * @ created 19.05.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class DownloadMailAttachOperationBase extends OperationBase
{
	private static final MailService mailService = new MailService();
	private Mail mail;

	public void initialize(Long id) throws BusinessException
	{
		mail = mailService.getMailDraftByParentId(id, getDraftMailState());
		if (mail == null)
			mail = mailService.findMailById(id);
		
		if (mail == null)
			throw new BusinessException("Письмо с id=" + id + " не найдено");

		checkMailAccess(mail);
	}

	/**
	 * @return Статус черновика
	 */
	protected abstract MailState getDraftMailState();

	/**
	 * проверка доступа к письму
	 * @throws BusinessException
	 */
	protected abstract void checkMailAccess(Mail checkingMail) throws BusinessException;

	public Mail getEntity()
	{
		return mail;
	}

	public byte[] getData()
	{
		return mail.getData();
	}

	public String getFileName()
	{
		return mail.getFileName();
	}
}
