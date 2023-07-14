package com.rssl.phizic.operations.ext.sbrf.mail;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 29.05.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditMailOperationBase extends OperationBase implements EditEntityOperation
{
	private static final int MAX_LENGTH_SUBJECT = 40; // Максимально допустимая длина темы письма
	private static final CounterService counterService = new CounterService();

	private CommonLogin login;   //логин отправителя

	protected static final MailService mailService = new MailService();
	protected static final MailSubjectService mailSubjectService = new MailSubjectService();
	protected Mail mail;         //текущее письмо

	/**
	 * Получить идентификатор читающего
	 * @param mail письмо которое читаем
	 * @return идентификатор получателя письма
	 */
	protected abstract Long getOwnerId(Mail mail);

	/**
	 * @return статус для черновика
	 */
	protected abstract MailState getMailDraftState();

	/**
	 * проверка доступа к письму
	 * @throws BusinessException
	 */
	protected abstract void checkMailAccess(Mail checkingMail) throws BusinessException;

	/**
	 * обновление списка получателей после сохранения письма
	 * @throws BusinessException
	 */
	protected abstract void updateRecipients() throws BusinessException;

	public Mail getEntity()
	{
		return mail;
	}

	/**
	 * Получает переписку
	 * @return письма
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<Mail> getCorrespondence() throws BusinessException
	{
		if(mail.getId() != null)
		{
			 return mailService.getCorrespondence(mail);
		}
		if(mail.getParentId() != null)
		{
			return mailService.getCorrespondence(mail.getParentId());
		}
		return Collections.emptyList();
	}

	/**
	 * @return логин текущего пользователя (клиент/сотрудник)
	 */
	public CommonLogin getLogin()
	{
		return login;
	}

	protected Mail findMailById(Long id) throws BusinessException
	{
		return mailService.findMailById(id);
	}


	private Mail findMailDraftByParentId(Long parentId) throws BusinessException
	{
		return mailService.getMailDraftByParentId(parentId, getMailDraftState());
	}

	private void saveCurrentMail() throws BusinessException
	{
		mailService.addOrUpdate(mail);
	}

	private Long getNextMailNumber() throws BusinessException
	{
		try
		{
			Long next = counterService.getNext(Counters.LETTER_NUMBER);
			Long nodeNumber = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
			return new Long(nodeNumber + "0" + next);
		}
		catch (CounterException e)
		{
			throw new BusinessException(e);
		}
	}

	protected void initializeContext()
	{
		login = AuthModule.getAuthModule().getPrincipal().getLogin();
	}

	protected void initializeNewMail() throws BusinessException
	{
		mail = new Mail();
		mail.setDate(Calendar.getInstance());
		mail.setSender(login);
		mail.setState(MailState.TEMPLATE);
		mail.setType(MailType.CONSULTATION);
		//mAPI: num будет сгенерирован при сохранении (в save())
		ApplicationConfig applicatinConfig = ApplicationConfig.getIt();
		if (applicatinConfig.getApplicationInfo().isNotMobileApi())
			mail.setNum(getNextMailNumber());
	}

	protected void initializeReply(Long parentId) throws BusinessException
	{
		mail.setParentId(parentId);
	}

	/**
	 * @return идентификатор нового письма
	 * @throws BusinessException
	 */
	public Long createNewMail() throws BusinessException
	{
		initializeContext();
		initializeNewMail();
		//mAPI: сохранение в БД будет выполнено в save()
		ApplicationConfig applicatinConfig = ApplicationConfig.getIt();
		if (applicatinConfig.getApplicationInfo().isNotMobileApi())
			saveCurrentMail();
		return mail.getId();
	}

	protected abstract void checkReply(Long id) throws BusinessException, BusinessLogicException;

	/**
	 * @param parentId id письма на которое отвечаем
	 * @return идентификактор ответа
	 * @throws BusinessException
	 */
	public Long createReply(Long parentId) throws BusinessException, BusinessLogicException
	{
		initializeContext();
		checkReply(parentId);
		//если передали id черновика, то нового письма не создаем
		mail = findMailDraftByParentId(parentId);
		if (mail != null)
			return mail.getId();

		initializeNewMail();
		initializeReply(parentId);
		//mAPI: сохранение в БД будет выполнено в save()
		ApplicationConfig applicatinConfig = ApplicationConfig.getIt();
		if (applicatinConfig.getApplicationInfo().isNotMobileApi())
			saveCurrentMail();
		return mail.getId();
	}

	/**
	 * @param id идентификатор письма которое хотим посмотреть
	 * @return идентификатор просматриваемого письма (если есть сохраненный черновик ответа, то показываем его)
	 * @throws BusinessException
	 */
	public Long createView(Long id) throws BusinessException
	{
		initializeContext();
		mail = findMailDraftByParentId(id);
		if (mail == null)
			return id;

		return mail.getId();
	}

	/**
	 * Поставить пометку о прочтении письма.
	 * @param deliveredMail письмо которое читаем
	 * @return поставлена ли пометка
	 * @throws BusinessException
	 */
	protected boolean markMailReceived(Mail deliveredMail) throws BusinessException
	{
		//Получаем текущего пользователя, ставим метку о прочтении.
		Recipient recipientMark = mailService.getRecipient(deliveredMail, getOwnerId(deliveredMail));
		if (recipientMark == null || recipientMark.getState() != RecipientMailState.NEW)
			return false;

		recipientMark.setState(RecipientMailState.READ);
		mailService.update(recipientMark);
		return true;
	}

	protected String getSubSubject(String subject)
	{
		return subject.length() > MAX_LENGTH_SUBJECT ? subject.substring(0, MAX_LENGTH_SUBJECT): subject;
	}

	/**
	 * @param parentMail письмо на которое отвечаем
	 * @return тема письма
	 * @throws BusinessException
	 */
	private String getSubject(Mail parentMail) throws BusinessException
	{
		if (parentMail == null)
				return "";

		// В случае, если это первый ответ, добавляем RE: к теме письма
		// Если ответов больше одного - ведем счетчик ответов
		long counter = mailService.getCorrespondenceCounter(parentMail);
		if (counter <= 1)
			return getSubSubject("RE: " + parentMail.getSubject());

		String subject = parentMail.getSubject();
		subject = subject.substring(subject.indexOf(": ") + 2);
		return getSubSubject("RE[" + counter + "]: " + subject);
	}

	private void initializeNewReply() throws BusinessException
	{
		//получаем письмо на которое отвечаем
		Mail parentMail = findMailById(mail.getParentId());

		mail.setData(null);
		mail.setFileName(null);
		updateReplyByParentMail(parentMail);
	}

	/**
	 * Обновление ответа на письмо данными письма на которое отвечаем
	 * @param parent письмо на которое отвечаем
	 * @throws BusinessException
	 */
	private void updateReplyByParentMail(Mail parent) throws BusinessException
	{
		//помечаем прочитанным
		markMailReceived(parent);
		mail.setSubject(getSubject(parent));

		if(parent == null)
			return;
		mail.setResponseMethod(parent.getResponseMethod());
		mail.setEmail(parent.getEmail());
		mail.setPhone(parent.getPhone());
		mail.setTheme(parent.getTheme());
		mail.setType(parent.getType());
		mail.setEmployee(parent.getEmployee());
	}

	/**
	 * инициализация операции
	 * @param id идентификатор письма
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		//инициализируем клиентом
		initializeContext();
		Mail tempMail = findMailById(id);
		if (tempMail == null)
			throw new ResourceNotFoundBusinessException("Письмо с id=" + id + " не найдено", Mail.class);

		//проверяем доступ к письму
		checkMailAccess(tempMail);
		mail = tempMail;

		//если просматриваем письмо, то читаем его
		//если отвечаем, то читаем уже в initializeNewReply();
		if (!mail.getSender().equals(login))
			markMailReceived(mail);

		if(mail.getState() == MailState.EMPLOYEE_DRAFT)
			mail.setSender(getLogin());
		//если письмо новое (MailState == TEMPLATE) и это не ответ (mail.getParentId() == null), то делать ничего не нужно
		//если письмо не новое (MailState != TEMPLATE), то делать ничего не нужно
		if (mail.getState() != MailState.TEMPLATE || mail.getParentId() == null)
			return;

		//если письмо новое (MailState == TEMPLATE) и ответ, то делаем инициализацию нового ответа
		initializeNewReply();
	}

	/**
	 * действия с полученным письмом после сохранения черновика ответа
	 * @param parentMail письмо, черновик ответа на которое сохраняем
	 * @throws BusinessException
	 */
	protected void updateParentMailAfterUpdateRecipientState(Mail parentMail) throws BusinessException
	{}

	protected void addRecipient(RecipientType recipientType, Long recipientId, String recipientName) throws BusinessException
	{
		Recipient	recipient = new Recipient();
		recipient.setRecipientType(recipientType);
		recipient.setRecipientId(recipientId);
		recipient.setRecipientName(recipientName);
		recipient.setMailId(mail.getId());
		recipient = mailService.addOrUpdate(recipient);
	}

	@Transactional
	public void save() throws BusinessException, BusinessLogicException
	{
		mail.setDate(Calendar.getInstance());

		boolean isDraft = mail.getState() == getMailDraftState();
		if (mail.getParentId() != null)
		{
			//меняем статус получателя письма на которое отвечаем
			Mail parentMail = findMailById(mail.getParentId());
			Long recipientId = getOwnerId(parentMail);
			Recipient recipient = mailService.getRecipient(parentMail, recipientId);
			if(mail.getDirection() == MailDirection.CLIENT && !isDraft)
			{
				mail.setResponseTime(DateHelper.diff(mail.getDate(), parentMail.getDate()));
			}
			if (recipient == null)
				throw new AccessException("Пользователь с логином ID=" + recipientId +" не является получателем письма ID="+mail.getId());

			recipient.setState(isDraft ? RecipientMailState.DRAFT : RecipientMailState.ANSWER);
			mailService.update(recipient);
			updateParentMailAfterUpdateRecipientState(parentMail);
		}

		if (ApplicationUtil.isMobileApi() && mail.getNum() == null)
			mail.setNum(getNextMailNumber());

		saveCurrentMail();

		if (isDraft)
			return;

		updateRecipients();
	}

	/**
	 * Удаляет письмо в статусе TEMPLATE
	 * @param id иеднтификатор письма
	 * @throws BusinessException
	 */
	public void removeTemplate(Long id) throws BusinessException
	{
		initializeContext();
		mail = findMailById(id);
		if (mail == null)
			return;

		checkMailAccess(mail);
		if (mail.getState() == MailState.TEMPLATE)
			mailService.remove(mail);
	}
}
