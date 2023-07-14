package com.rssl.phizic.operations.ext.sbrf.mail;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author kligina
 * @ created 19.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class RemoveClientMailOperation extends OperationBase implements RemoveEntityOperation
{
	private static final MailService mailService = new MailService();
	private static final SimpleService simpleService = new SimpleService();
	private Mail mail;
	private CommonLogin login = AuthModule.getAuthModule().getPrincipal().getLogin();

	private static final boolean DELETED = true;
	private static final boolean NOT_DELETED = false;


	public Mail getEntity()
	{
		return mail;
	}

	public void initialize(Long mailId) throws BusinessException, BusinessLogicException
	{
		mail = mailService.findMailById(mailId);

	    if (mail == null)
				throw new ResourceNotFoundBusinessException("Письмо с ID=" + mailId + " не найдено", Mail.class);
		MailHelper.checkAccess(mail, login);
		if (mail.getDirection() != MailDirection.ADMIN && !MailHelper.isRead(mail, login))
				throw new BusinessLogicException("Вы можете удалять только прочитанные письма. Сначала ознакомьтесь с содержанием письма");

	}

	public void remove() throws BusinessException
	{
		 markDeleted(DELETED);
	}

	public void rollback() throws BusinessException
	{
		 markDeleted(NOT_DELETED);
	}

	/**
	 * Метод удаления в корзину или восстановления письма.
	 * @param deleted true - удалить, false - восстановить.
	 * @throws BusinessException
	 */
	@Transactional
	public void markDeleted(Boolean deleted) throws BusinessException
	{
    	Recipient recipient = mailService.getRecipient(mail,login.getId());
    	if (mail.getDirection() == MailDirection.ADMIN)
		{
			mail.setDeleted(deleted);
			simpleService.update(mail);
		}
		else if (recipient != null)
		{
			if(recipient.getState() == RecipientMailState.DRAFT)
			{
				Mail draftMail = mailService.getMailDraftByParentId(mail.getId(), MailState.CLIENT_DRAFT);
				if(draftMail != null)
				{
					draftMail.setDeleted(deleted);
					mailService.addOrUpdate(draftMail);
				}
			}
			recipient.setDeleted(deleted);
			mailService.update(recipient);
		}
	}
}
