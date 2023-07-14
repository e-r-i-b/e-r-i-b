package com.rssl.phizic.security.certification;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.crypto.Certificate;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

/**
 * @author Evgrafov
 * @ created 22.12.2006
 * @ $Author: krenev $
 * @ $Revision: 58098 $
 */

public class CertificateOwnService
{

	/**
	 * Добавить сертификат пользователю, если этот сертификат уже добавлен ничего не происходит
	 * @param login пользователь
	 * @param certificate сертификат
	 * @throws SecurityLogicException сертификат выдан другому
	 */
	public void add(final CommonLogin login, final Certificate certificate) throws SecurityDbException, SecurityLogicException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.lock(login, LockMode.NONE);
					CertOwn own = findInternal(session, certificate);

					checkOwner(own, login);

					if (own != null)
						return null;

					addInternal(session, login, certificate);

					return null;
				}
			});
		}
		catch(SecurityLogicException e)
		{
			throw e;
		}
		catch(SecurityDbException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new SecurityDbException(e);
		}

	}

	public CertOwn update(final CertOwn certificate) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CertOwn>()
			{
				public CertOwn run(Session session) throws Exception
				{
					session.update(certificate);
					return certificate;
				}
			});
		}
		catch(SecurityDbException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	private void checkOwner(CertOwn own, CommonLogin login) throws SecurityLogicException
	{
		if(own != null && !own.getOwner().getId().equals(login.getId()))
			throw new SecurityLogicException("Сертификат уже принадлежит другому пользователю");
	}

	/**
	 * Удалить (отвязать) сертификат пользователя, если этот сертификат уже отвязан ничего не происходит
	 *
	 * @param login       пользователь
	 * @param certificate сертификат
	 * @throws SecurityLogicException сертификат выдан другому
	 */
	public void remove(final CommonLogin login, final Certificate certificate) throws SecurityDbException, SecurityLogicException
	{
		try
		{
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        session.lock(login, LockMode.NONE);
			        CertOwn own = findInternal(session, certificate);

			        checkOwner(own, login);

			        if (own == null)
				        return null; // уже отвязан

			        removeInternal(session, own);

			        return null;
		        }
		    });
		}
		catch (SecurityLogicException e)
		{
			throw e;
		}
		catch(SecurityDbException e)
		{
			throw e;
		}
		catch (Exception e)
		{
		   throw new SecurityDbException(e);
		}
	}

	private void removeInternal(Session session, final CertOwn own) throws HibernateException
	{
		session.delete(own);
	}

	private void addInternal(Session session, final CommonLogin login, final Certificate certificate) throws HibernateException
	{
		CertOwn own = new CertOwn();
		own.setOwner(login);
		own.setCertificate(certificate);
		own.setStatus(CertOwnStatus.STATUS_NOT_ACTIVE);
		own.setEndDate(DateHelper.toCalendar(certificate.getExpiration()));
		session.save(own);
	}

	private CertOwn findInternal(Session session, final Certificate certificate)
	{
		CertOwn own = (CertOwn) session
				.getNamedQuery("findCertOwn")
				.setParameter("certificate", certificate)
				.uniqueResult();

		if(own == null)
			return null;

		return own;
	}

	/**
	 * Все сертификаты пользователя
	 * @param login логин для которого ищутся сертификаты
	 * @return список серификатов
	 */
	public List<Certificate> find(final CommonLogin login) throws SecurityDbException
	{
		List owns;

		try
		{
			owns = HibernateExecutor.getInstance().execute(new HibernateAction<List>()
			{
			    public List run(Session session) throws Exception
			    {
				    return session.getNamedQuery("findAllCertOwn")
						    .setParameter("owner", login)
						    .list();
			    }
			});
		}
		catch (Exception e)
		{
		   throw new SecurityDbException(e);
		}

		List<Certificate> certs = new ArrayList<Certificate>(owns.size());

		for (Object own : owns)
		{
			certs.add(((CertOwn)own).getCertificate());
		}
		
		return certs;
	}

	public CertOwn findById(final Long id)
	{
		HibernateExecutor lightExecutor = HibernateExecutor.getInstance();
		try
		{
			return lightExecutor.execute(new HibernateAction<CertOwn>()
			{
				public CertOwn run(Session session) throws Exception
				{
					DetachedCriteria detachedCriteria =
							DetachedCriteria.forClass(CertOwn.class).add(Expression.eq("id", id));
					List<CertOwn> list = detachedCriteria.getExecutableCriteria(session).list();
					return list.size() == 1 ? list.get(0) : null;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	/**
	 * Активный сертификат пользователя
	 * @param login логин для которого ищется сертификат
	 * @return активный сертификат или null если не найден
	 */
	public Certificate findActive(final CommonLogin login) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Certificate>()
			{
		        public Certificate run(Session session) throws Exception
		        {
			        session.lock(login, LockMode.NONE);
			        CertOwn own = findActiveInternal(session, login);
			        if( (own = checkExpirationAndUpdate(own)) == null)
			            return null;
			        else
				        return own.getCertificate();
		        }
		    });
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * проверяем дату истечения сертификата, и изменяем статус если нужно
	 * @param cert
	 * @return
	 * @throws SecurityDbException
	 */
	private CertOwn checkExpirationAndUpdate(final CertOwn cert) throws SecurityDbException
	{
		if(cert == null)
			return null;

		Date currentDate = DateHelper.getCurrentDate().getTime();
		Date certDate = cert.getCertificate().getExpiration();
		if( currentDate.compareTo(certDate)>0 )
		{
			cert.setStatus( CertOwnStatus.STATUS_EXPIRED );
			try
			{
				return HibernateExecutor.getInstance().execute(new HibernateAction<CertOwn>()
				{
					public CertOwn run(Session session) throws Exception
					{
						session.update(cert);
						return cert;
					}
				});
			}
			catch (Exception e)
			{
				throw new SecurityDbException(e);
			}
		}
		else return cert;
	}

	private CertOwn findActiveInternal(Session session, final CommonLogin login)
	{
		return (CertOwn) session
				.getNamedQuery("findActiveCertOwn")
				.setParameter("owner", login)
				.uniqueResult();
	}

	/**
	 * Установить активный сертификат. Перед активацией сертификат должен бфть добавлен ользователю
	 * @param login логин для которого нужно установить сертификат
	 * @param certificate сертификат
	 */
	public CertOwn setActive(final CommonLogin login, final Certificate certificate) throws SecurityLogicException, SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CertOwn>()
			{
				public CertOwn run(Session session) throws Exception
				{
					session.lock(login, LockMode.NONE);
					return setActiveInternal(session, login, certificate);
				}
			});
		}
		catch (SecurityLogicException e)
		{
			throw e;
		}
		catch (SecurityDbException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	private CertOwn setActiveInternal(Session session, CommonLogin login, Certificate certificate) throws SecurityDbException, SecurityLogicException

	{
		CertOwn oldActive = findActiveInternal(session, login);
		CertOwn newActive = findInternal(session, certificate);

		checkOwner(oldActive, login);

		if (newActive == null)
			throw new SecurityLogicException("Сертификат c ID " + certificate.getId() + " не найден у логина ID " + login.getId());

		if(newActive.equals(oldActive))
						return newActive; // он уже активен

		if(oldActive != null)
		{
			oldActive.setEndDate(Calendar.getInstance());
			oldActive.setStatus(CertOwnStatus.STATUS_EXPIRED);
			session.update(oldActive);
		}


		newActive.setEndDate( DateHelper.toCalendar(newActive.getCertificate().getExpiration()) );
		newActive.setStartDate(Calendar.getInstance());
		newActive.setStatus(CertOwnStatus.STATUS_ACTIVE);
		session.update(newActive);

		return newActive;
	}

	/**
	 * Найти владельца сертификата
	 * @param certificate сертификат по которому осуществляется поиск
	 * @return dkfltktw сертификата или null если не найден
	 */
	public CommonLogin findOwner(final Certificate certificate) throws SecurityDbException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<CommonLogin>()
		    {
		        public CommonLogin run(Session session) throws Exception
		        {
			        CertOwn own = findInternal(session, certificate);
			        return own == null ? null : own.getOwner();
		        }
		    });
		}
		catch(SecurityDbException e)
		{
			throw e;
		}
		catch (Exception e)
		{
		   throw new SecurityDbException(e);
		}
	}

	public void setExpiredStatus()
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			    {
			        public Void run(Session session) throws Exception
			        {
				        Query query = session.getNamedQuery("setExpired")
						        .setParameter("currentDate", DateHelper.getCurrentDate())
						        .setParameter("oldStatus", CertOwnStatus.STATUS_ACTIVE)
						        .setParameter("status", CertOwnStatus.STATUS_EXPIRED);

						query.executeUpdate();

				        return null;
			        }
			    });
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public List<Certificate> getAllActiveForAdmin() throws SecurityDbException
	{
		List results;
		try
		{
			results = HibernateExecutor.getInstance().execute(new HibernateAction<List>()
			{
				public List run(Session session) throws Exception
				{
					return session.getNamedQuery("getAllStatusCerts")
						.setParameter("status", CertOwnStatus.STATUS_ACTIVE)
						.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}

		List<Certificate> certs = new ArrayList<Certificate>(results.size());

		for (Object result : results)
		{
			certs.add(((CertOwn)result).getCertificate());
		}

		return certs;
	}

	public void setDeletedStatus(final CommonLogin login, final Certificate certificate) throws SecurityDbException
    {
	    try
        {
            HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
            {
                public Void run(Session session) throws Exception
                {

	                CertOwn certOwn = findInternal(session, certificate);
	                certOwn.setStatus(CertOwnStatus.STATUS_DELETED);
	                session.update(certOwn);
	                session.evict(certOwn.getOwner());

	                return null;
                }
            });
		}
	    catch (Exception e)
	    {
		    throw new SecurityDbException(e);
	    }
    }
}
