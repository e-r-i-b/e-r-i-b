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
 * ������ ��� ������ � ��������.
 * @author Gainanov
 * @ created 27.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class MailService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ����� ������ �� Id
	 * @param id  Id ������
	 * @return ������
	 * @throws BusinessException
	 */
	public Mail findMailById(Long id) throws BusinessException
	{
		return simpleService.findById(Mail.class,id);
	}

	/**
	 * ���� ���������� �� ��������������
	 * @param id �������������
	 * @return ����������
	 * @throws BusinessException
	 */
	public Recipient findRecipientById(Long id) throws BusinessException
	{
		return simpleService.findById(Recipient.class,id);
	}

	/**
	 * ��������� ����������
	 * @param recipient ����������
	 * @return ����������
	 * @throws BusinessException
	 */
	public Recipient update(Recipient recipient) throws BusinessException
	{
		return simpleService.update(recipient);
	}

	/**
	 * ������� ����������
	 * @param recipient ����������
	 * @throws BusinessException
	 */
	public void remove(Recipient recipient) throws BusinessException
	{
		simpleService.remove(recipient);
	}

	/**
	 * ������� �����������
	 * @param mail ������
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
	 * ������� ������
	 * @param mail ������
	 * @throws BusinessException
	 */
	public void remove(Mail mail) throws BusinessException
	{
		simpleService.remove(mail);
	}

	/**
	 * @param login ����� ������������
	 * @return ������ ����� ������������ ��� ��������� �����
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
	 * ��������� ������
	 * @param mail ������
	 * @return ������
	 * @throws BusinessException
	 */
	public Mail addOrUpdate(Mail mail) throws BusinessException
	{
		return simpleService.addOrUpdate(mail);
	}

	/**
	 * ��������� ����������
	 * @param recipient ����������
	 * @return ����������
	 * @throws BusinessException
	 */
	public Recipient addOrUpdate(Recipient recipient) throws BusinessException
	{
		return simpleService.addOrUpdate(recipient);
	}

	/**
	 * ����� ��� �������������� ����� �� ������!!!
	 * @param mail ������
	 * @return ������
	 * @throws BusinessException
	 */
	public Mail insertMailFromArchive(Mail mail) throws BusinessException
	{
		return simpleService.add(mail);
	}


	/**
	 * ����� ��� �������������� ����� �� ������!!!
	 * @param recipient ����������
	 * @return ����������
	 * @throws BusinessException
	 */
	public Recipient insertRecipientFromArchive(Recipient recipient) throws BusinessException
	{
		return simpleService.add(recipient);
	}


	/**
	 * ��������� ���������� ��� ������
	 * @param mail - ������
	 * @param recipientId - id ����������.
	 * � ������, ���� ������ ����������
	 *      ����������� �������, �� id �������.
	 *      ���� �������� ����������, �� id ������������, � �������� �������� ������. 
	 * @return ����������
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
	 * ���������� ���� ����������� ��� ������.
	 * @param mailId id ������
	 * @return ����������
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
	 * �������� �������� ������ �� parentId(MAIL.PARENT_ID)
	 * @param parentId - ������������� ������ ��� �������� ������ ��������.
	 * @param state - ������(������ ���� MailState.CLIENT_DRAFT ��� MailState.EMPLOYEE_DRAFT)
	 * @return �������� ������
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
	 * �������� ���������
	 * @param mail ��������� ������ �� ���������
	 * @return ������
	 * @throws BusinessException
	 */
	public List<Mail> getCorrespondence(final Mail mail) throws BusinessException
	{
		return getCorrespondence(mail.getId());
	}

	/**
	 * �������� ���������
	 * @param id ���������� �����a �� ���������
	 * @return ������
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
	 * �������� ���������� ����� � ���������.
	 * @param mail ��������� ������ �� ���������
	 * @return ���������� ����� � ���������
	 * @throws BusinessException
	 */
	public Long getCorrespondenceCounter(final Mail mail) throws BusinessException
	{
		return getCorrespondenceCounter(mail.getId());
	}

	/**
	 * �������� ���������� ����� � ���������.
	 * @param mailId Id ���������� ������ �� ���������
	 * @return ���������� ����� � ���������
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
	 * ���������� ���������� ����� ����� �������
	 * @param login ����� �������
	 * @return ���������� �����
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

