package com.rssl.phizic.operations.ext.sbrf.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;

import java.util.List;

/**
 * @author kligina
 * @ created 19.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditClientMailOperation extends EditMailOperationBase
{
	private static final DepartmentService departmentService = new DepartmentService();
	private ActivePerson person;

	public void updateRecipients() throws BusinessException
	{
		addRecipient(RecipientType.ADMIN, mail.getRecipientId(), mail.getRecipientName());
	}

	protected void initializeContext()
	{
		super.initializeContext();
		person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
	}

	protected void initializeNewMail() throws BusinessException
	{
		super.initializeNewMail();
		mail.setDirection(MailDirection.ADMIN);
		Long departmentId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId();
		Department department = departmentService.findById(departmentId);
		mail.setRecipientType(RecipientType.ADMIN);
		mail.setRecipientName(department.getName());
		mail.setRecipientId(departmentId);
	}

	protected void initializeReply(Long parentId) throws BusinessException
	{
		super.initializeReply(parentId);
	}

	@Override
	protected void checkReply(Long id) throws BusinessException, BusinessLogicException
	{}

	protected Long getOwnerId(Mail deliveredMail)
	{
		return getLogin().getId();
	}

	protected MailState getMailDraftState()
	{
		return MailState.CLIENT_DRAFT;
	}

	protected boolean markMailReceived(Mail deliveredMail) throws BusinessException
	{
		boolean isMarked = super.markMailReceived(deliveredMail);
		if (isMarked)
			MailHelper.decCountNewLetters();
		return isMarked;
	}

	protected void checkMailAccess(Mail checkingMail) throws BusinessException
	{
		MailHelper.checkAccess(checkingMail, getLogin());
	}

	/**
	 * Установить тематику обращения
	 * @param id тематики
	 * @throws BusinessException
	 */
	public void setTheme(String id) throws BusinessException
	{
		setTheme(Long.parseLong(id));
	}

	/**
	 * Установить тематику обращения
	 * @param id тематики
	 * @throws BusinessException
	 */
	public void setTheme(Long id) throws BusinessException
	{
		MailSubject mailSubject = mailSubjectService.getMailSubjectById(id, null);
		mail.setTheme(mailSubject);
	}

	/**
	 * Получает все тематики обращения.
	 * @return Список тематик обращений
	 * @throws BusinessException
	 */
	public List<MailSubject> getAllThemes() throws BusinessException
	{
		return mailSubjectService.getAllSubjects(null);
	}

	/**
	 * @return текущий клиент
	 */
	public ActivePerson getPerson()
	{
		return person;
	}


	/**
	 * mAPI: Получает переписку
	 * @param parentId идентификатор
	 * @return письма
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<Mail> getCorrespondence(Long parentId) throws BusinessException
	{
		return mailService.getCorrespondence(parentId);
	}

	/**
	 * mAPI: Помечает прочитанным письмо, на которое отвечаем
	 * @throws BusinessException
	 */
	public void markParentReceived() throws BusinessException
	{
		Long parentId = mail.getParentId();
		if (parentId == null)
			return;

		//получаем письмо на которое отвечаем
		Mail parentMail = findMailById(parentId);
		if (parentMail == null)
			throw new BusinessException("Письмо с id=" + parentId + " не найдено.");

		//помечаем прочитанным
		markMailReceived(parentMail);
	}

	/**
	 * mAPI: Добавляет к теме письма префикс "RE[N]: ", если это ответ
	 * @throws BusinessException
	 */
	public void updateSubject() throws BusinessException
	{
		Long parentId = mail.getParentId();
		if (parentId == null)
			return;

		String subject = mail.getSubject();

		// В случае, если это первый ответ, добавляем RE: к теме письма
		// Если ответов больше одного - ведем счетчик ответов
		long counter = mailService.getCorrespondenceCounter(parentId);
		if (counter <= 1)
			mail.setSubject(getSubSubject("RE: " + subject));
		else
		{
			if (subject.contains(": "))
				subject = subject.substring(subject.indexOf(": ") + 2);
			mail.setSubject(getSubSubject("RE[" + counter + "]: " + subject));
		}
	}

	/**
	 * mAPI: Добавляет прикрепленный файл
	 * @param file Байтовый массив файла
	 * @param fileName Имя файла
	 */
	public void updateFile(byte[] file, String fileName)
	{
		mail.setData(file);
		mail.setFileName(fileName);
	}
}
