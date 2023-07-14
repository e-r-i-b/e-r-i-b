package com.rssl.phizic.business.mail;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.hibernate.DataBaseTypeQueryHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * Сервис для работы с письмами.
 * @author Gainanov
 * @ created 27.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class MailService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Поиск письма по Id
	 * @param id  Id письма
	 * @return письмо
	 * @throws BusinessException
	 */
	public Mail findMailById(Long id) throws BusinessException
	{
		return simpleService.findById(Mail.class,id);
	}

	/**
	 * Ищет получателя по идентификатору
	 * @param id идентификатор
	 * @return получатель
	 * @throws BusinessException
	 */
	public Recipient findRecipientById(Long id) throws BusinessException
	{
		return simpleService.findById(Recipient.class,id);
	}

	/**
	 * Обновляет получателя
	 * @param recipient получатель
	 * @return получатель
	 * @throws BusinessException
	 */
	public Recipient update(Recipient recipient) throws BusinessException
	{
		return simpleService.update(recipient);
	}

	/**
	 * Удаляет получателя
	 * @param recipient получетель
	 * @throws BusinessException
	 */
	public void remove(Recipient recipient) throws BusinessException
	{
		simpleService.remove(recipient);
	}

	/**
	 * Удяляет получателей
	 * @param mail письмо
	 * @throws BusinessException
	 */
	public void removeRecipients(final Mail mail) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute( new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.mail.Recipient.delete");
					query.setParameter("mailId", mail.getId());
					query.executeUpdate();
					return null;
				}
			});
		}
		catch(Exception e)
		{
		  throw new BusinessException(e);
		}
	}

	/**
	 * Удаляет письмо
	 * @param mail письмо
	 * @throws BusinessException
	 */
	public void remove(Mail mail) throws BusinessException
	{
		simpleService.remove(mail);
	}

	/**
	 * @param login логин пользователя
	 * @return список новых обязательных для прочтения писем
	 * @throws BusinessException
	 */
	public List<Mail> getNewImportantClientMails(final CommonLogin login) throws BusinessException
	{
		try
		{
			return  HibernateExecutor.getInstance().execute( new HibernateAction<List<Mail>>()
			{
				public List<Mail> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.mail.getNewImportantClientMails");
					query.setParameter("recipient_id", login.getId());
					//noinspection unchecked
					return (List<Mail>)query.list();
				}
			});
		}
		catch(Exception e)
		{
		  throw new BusinessException(e);
		}
	}

	/**
	 * Добавляет письмо
	 * @param mail письмо
	 * @return письмо
	 * @throws BusinessException
	 */
	public Mail addOrUpdate(Mail mail) throws BusinessException
	{
		return simpleService.addOrUpdate(mail);
	}

	/**
	 * Добавляет получателя
	 * @param recipient получатель
	 * @return получатель
	 * @throws BusinessException
	 */
	public Recipient addOrUpdate(Recipient recipient) throws BusinessException
	{
		return simpleService.addOrUpdate(recipient);
	}

	/**
	 * Метод для восстановления писем из архива!!!
	 * @param mail письмо
	 * @return письмо
	 * @throws BusinessException
	 */
	public Mail insertMailFromArchive(Mail mail) throws BusinessException
	{
		return simpleService.add(mail);
	}


	/**
	 * Метод для восстановления писем из архива!!!
	 * @param recipient получатель
	 * @return получатель
	 * @throws BusinessException
	 */
	public Recipient insertRecipientFromArchive(Recipient recipient) throws BusinessException
	{
		return simpleService.add(recipient);
	}


	/**
	 * Получение получателя для письма
	 * @param mail - письмо
	 * @param recipientId - id получателя.
	 * В случае, если письмо отправлено
	 *      сотрудником клиенту, то id клиента.
	 *      если клиентом сотруднику, то id департамента, к которому привязан клиент. 
	 * @return получатель
	 * @throws BusinessException
	 */
	public Recipient getRecipient(final Mail mail, final Long recipientId) throws BusinessException
	{
		try
		{
			return  HibernateExecutor.getInstance().execute( new HibernateAction<Recipient>()
			{
				public Recipient run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.mail.Recipient.getRecipient");
					query.setParameter("recipientId", recipientId);
					query.setParameter("mailId", mail.getId());
					return (Recipient)query. uniqueResult();
				}
			});
		}
		catch(Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает всех получателей для письма.
	 * @param mailId id письма
	 * @return получатели
	 */
	public Iterator<Recipient> getRecipients(final Long mailId) throws BusinessException
	{
		try
		{
			return  HibernateExecutor.getInstance().execute( new HibernateAction<Iterator<Recipient>>()
			{
				public Iterator<Recipient> run(Session session) throws Exception
				{
					BeanQuery query = new BeanQuery("com.rssl.phizic.business.mail.Recipient.getRecipients");
					query.setParameter("mailId", mailId);
					return query.executeIterator();
				}
			});
		}
		catch(Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * Получает черновик письма по parentId(MAIL.PARENT_ID)
	 * @param parentId - идентификатор письма для которого создан черновик.
	 * @param state - статус(должен быть MailState.CLIENT_DRAFT или MailState.EMPLOYEE_DRAFT)
	 * @return черновик письма
	 * @throws BusinessException
	 */
	public Mail getMailDraftByParentId(final Long parentId, final MailState state) throws BusinessException
	{
		try
		{
			return  HibernateExecutor.getInstance().execute( new HibernateAction<Mail>()
			{
				public Mail run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.mail.getMailDraftByParentId");
					query.setParameter("parentId", parentId);
					query.setParameter("state", state);
					return (Mail)query. uniqueResult();
				}
			});
		}
		catch(Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * Получает переписку
	 * @param mail последнее письмо из переписки
	 * @return письма
	 * @throws BusinessException
	 */
	public List<Mail> getCorrespondence(final Mail mail) throws BusinessException
	{
		return getCorrespondence(mail.getId());
	}

	/**
	 * Получает переписку
	 * @param id последнего письмa из переписки
	 * @return письма
	 * @throws BusinessException
	 */
	public List<Mail> getCorrespondence(final Long id) throws BusinessException
	{
		try
		 {
		 	 return  HibernateExecutor.getInstance().execute( new HibernateAction<List<Mail>>()
			 {
				 public List<Mail> run(Session session) throws Exception
				 {
					 Query query = DataBaseTypeQueryHelper.getNamedQuery(session, "com.rssl.phizic.business.mail.getCorrespondence");
					 query.setParameter("mailId", id);
					 //noinspection unchecked
					 return (List<Mail>)query.list();
				 }
			 });
		 }
		 catch(Exception e)
		 {
		     throw new BusinessException(e);
		 }
	}


	/**
	 * Получает количество писем в переписке.
	 * @param mail последнее письмо из переписки
	 * @return количество писем в переписке
	 * @throws BusinessException
	 */
	public Long getCorrespondenceCounter(final Mail mail) throws BusinessException
	{
		return getCorrespondenceCounter(mail.getId());
	}

	/**
	 * Получает количество писем в переписке.
	 * @param mailId Id последнего письма из переписки
	 * @return количество писем в переписке
	 * @throws BusinessException
	 */
	public Long getCorrespondenceCounter(final Long mailId) throws BusinessException
	{
		try
		 {
		 	 return  HibernateExecutor.getInstance().execute( new HibernateAction<Long>()
			 {
				 public Long run(Session session) throws Exception
				 {
					 Query query = DataBaseTypeQueryHelper.getNamedQuery(session, "com.rssl.phizic.business.mail.getCountCorrespondence");
					 query.setParameter("mailId", mailId);
					 return (Long)query.uniqueResult();
				 }
			 });
		 }
		 catch(Exception e)
		 {
		     throw new BusinessException(e);
		 }
	}

	/**
	 * Возвращает количество новых писем клиента
	 * @param login логин клиента
	 * @return количество писем
	 * @throws BusinessException
	 */
	public Long countNewClientLetters(final CommonLogin login) throws BusinessException
	{
		try
		 {
		 	 return  HibernateExecutor.getInstance().execute( new HibernateAction<Long>()
			 {
				 public Long run(Session session) throws Exception
				 {
					 Query query = DataBaseTypeQueryHelper.getNamedQuery(session, "com.rssl.phizic.business.mail.Recipient.countNewClientLetters");
					 query.setParameter("loginId", login.getId());
					 return ((Integer)query.uniqueResult()).longValue();
				 }
			 });
		 }
		 catch(Exception e)
		 {
		     throw new BusinessException(e);
		 }

	}
}

