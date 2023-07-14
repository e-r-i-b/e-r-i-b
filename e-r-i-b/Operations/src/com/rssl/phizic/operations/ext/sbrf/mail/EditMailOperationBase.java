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
	private static final int MAX_LENGTH_SUBJECT = 40; // ����������� ���������� ����� ���� ������
	private static final CounterService counterService = new CounterService();

	private CommonLogin login;   //����� �����������

	protected static final MailService mailService = new MailService();
	protected static final MailSubjectService mailSubjectService = new MailSubjectService();
	protected Mail mail;         //������� ������

	/**
	 * �������� ������������� ���������
	 * @param mail ������ ������� ������
	 * @return ������������� ���������� ������
	 */
	protected abstract Long getOwnerId(Mail mail);

	/**
	 * @return ������ ��� ���������
	 */
	protected abstract MailState getMailDraftState();

	/**
	 * �������� ������� � ������
	 * @throws BusinessException
	 */
	protected abstract void checkMailAccess(Mail checkingMail) throws BusinessException;

	/**
	 * ���������� ������ ����������� ����� ���������� ������
	 * @throws BusinessException
	 */
	protected abstract void updateRecipients() throws BusinessException;

	public Mail getEntity()
	{
		return mail;
	}

	/**
	 * �������� ���������
	 * @return ������
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
	 * @return ����� �������� ������������ (������/���������)
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
		//mAPI: num ����� ������������ ��� ���������� (� save())
		ApplicationConfig applicatinConfig = ApplicationConfig.getIt();
		if (applicatinConfig.getApplicationInfo().isNotMobileApi())
			mail.setNum(getNextMailNumber());
	}

	protected void initializeReply(Long parentId) throws BusinessException
	{
		mail.setParentId(parentId);
	}

	/**
	 * @return ������������� ������ ������
	 * @throws BusinessException
	 */
	public Long createNewMail() throws BusinessException
	{
		initializeContext();
		initializeNewMail();
		//mAPI: ���������� � �� ����� ��������� � save()
		ApplicationConfig applicatinConfig = ApplicationConfig.getIt();
		if (applicatinConfig.getApplicationInfo().isNotMobileApi())
			saveCurrentMail();
		return mail.getId();
	}

	protected abstract void checkReply(Long id) throws BusinessException, BusinessLogicException;

	/**
	 * @param parentId id ������ �� ������� ��������
	 * @return �������������� ������
	 * @throws BusinessException
	 */
	public Long createReply(Long parentId) throws BusinessException, BusinessLogicException
	{
		initializeContext();
		checkReply(parentId);
		//���� �������� id ���������, �� ������ ������ �� �������
		mail = findMailDraftByParentId(parentId);
		if (mail != null)
			return mail.getId();

		initializeNewMail();
		initializeReply(parentId);
		//mAPI: ���������� � �� ����� ��������� � save()
		ApplicationConfig applicatinConfig = ApplicationConfig.getIt();
		if (applicatinConfig.getApplicationInfo().isNotMobileApi())
			saveCurrentMail();
		return mail.getId();
	}

	/**
	 * @param id ������������� ������ ������� ����� ����������
	 * @return ������������� ���������������� ������ (���� ���� ����������� �������� ������, �� ���������� ���)
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
	 * ��������� ������� � ��������� ������.
	 * @param deliveredMail ������ ������� ������
	 * @return ���������� �� �������
	 * @throws BusinessException
	 */
	protected boolean markMailReceived(Mail deliveredMail) throws BusinessException
	{
		//�������� �������� ������������, ������ ����� � ���������.
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
	 * @param parentMail ������ �� ������� ��������
	 * @return ���� ������
	 * @throws BusinessException
	 */
	private String getSubject(Mail parentMail) throws BusinessException
	{
		if (parentMail == null)
				return "";

		// � ������, ���� ��� ������ �����, ��������� RE: � ���� ������
		// ���� ������� ������ ������ - ����� ������� �������
		long counter = mailService.getCorrespondenceCounter(parentMail);
		if (counter <= 1)
			return getSubSubject("RE: " + parentMail.getSubject());

		String subject = parentMail.getSubject();
		subject = subject.substring(subject.indexOf(": ") + 2);
		return getSubSubject("RE[" + counter + "]: " + subject);
	}

	private void initializeNewReply() throws BusinessException
	{
		//�������� ������ �� ������� ��������
		Mail parentMail = findMailById(mail.getParentId());

		mail.setData(null);
		mail.setFileName(null);
		updateReplyByParentMail(parentMail);
	}

	/**
	 * ���������� ������ �� ������ ������� ������ �� ������� ��������
	 * @param parent ������ �� ������� ��������
	 * @throws BusinessException
	 */
	private void updateReplyByParentMail(Mail parent) throws BusinessException
	{
		//�������� �����������
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
	 * ������������� ��������
	 * @param id ������������� ������
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		//�������������� ��������
		initializeContext();
		Mail tempMail = findMailById(id);
		if (tempMail == null)
			throw new ResourceNotFoundBusinessException("������ � id=" + id + " �� �������", Mail.class);

		//��������� ������ � ������
		checkMailAccess(tempMail);
		mail = tempMail;

		//���� ������������� ������, �� ������ ���
		//���� ��������, �� ������ ��� � initializeNewReply();
		if (!mail.getSender().equals(login))
			markMailReceived(mail);

		if(mail.getState() == MailState.EMPLOYEE_DRAFT)
			mail.setSender(getLogin());
		//���� ������ ����� (MailState == TEMPLATE) � ��� �� ����� (mail.getParentId() == null), �� ������ ������ �� �����
		//���� ������ �� ����� (MailState != TEMPLATE), �� ������ ������ �� �����
		if (mail.getState() != MailState.TEMPLATE || mail.getParentId() == null)
			return;

		//���� ������ ����� (MailState == TEMPLATE) � �����, �� ������ ������������� ������ ������
		initializeNewReply();
	}

	/**
	 * �������� � ���������� ������� ����� ���������� ��������� ������
	 * @param parentMail ������, �������� ������ �� ������� ���������
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
			//������ ������ ���������� ������ �� ������� ��������
			Mail parentMail = findMailById(mail.getParentId());
			Long recipientId = getOwnerId(parentMail);
			Recipient recipient = mailService.getRecipient(parentMail, recipientId);
			if(mail.getDirection() == MailDirection.CLIENT && !isDraft)
			{
				mail.setResponseTime(DateHelper.diff(mail.getDate(), parentMail.getDate()));
			}
			if (recipient == null)
				throw new AccessException("������������ � ������� ID=" + recipientId +" �� �������� ����������� ������ ID="+mail.getId());

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
	 * ������� ������ � ������� TEMPLATE
	 * @param id ������������� ������
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
