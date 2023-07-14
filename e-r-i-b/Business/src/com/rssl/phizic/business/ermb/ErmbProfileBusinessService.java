package com.rssl.phizic.business.ermb;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.LoggingHelper;
import com.rssl.phizic.logging.LoggingMode;
import com.rssl.phizic.logging.operations.ActiveOperationsLoggingAccessor;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.operations.config.OperationsLogConfig;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

/**
 * ���������� ������ ��� ������ � ��������� ����
 *
 @ author: EgorovaA
 @ created: 06.02.2013
 @ $Author$
 @ $Revision$
 */
public class ErmbProfileBusinessService
{
	private final static SimpleService simpleService = new SimpleService();
	private static final PersonService personService = new PersonService();

	/**
	 * �������� ������� ���� �� ��������������
	 * @param profileId - ������������� ����-�������
	 * @return ������� ���� ��� null, ���� ������� �� ������
	 */
	public ErmbProfileImpl findByProfileId(long profileId) throws BusinessException
	{
		return simpleService.findById(ErmbProfileImpl.class, profileId);
	}

	/**
	 * �������� ������ �������� ����, � ������� ���� �������� ������(���������� � ������� ������ �� ���������)
	 * @return ������ ����-��������
	 * @throws BusinessException
	 */
	public List<ErmbProfileImpl> findProfilesToUpdate() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(null).execute(new HibernateAction<List<ErmbProfileImpl>>()
			{
				public List<ErmbProfileImpl> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.ErmbProfileBusinessService.findProfilesToUpdate");
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * �������� ��� �������� ������� ����
	 * @param profile ������� ����
	 * @return ����������� ��������
	 * @throws BusinessException
	 */
	public ErmbProfileImpl addOrUpdate(ErmbProfileImpl profile) throws BusinessException
	{
		return simpleService.addOrUpdate(profile, null);
	}

    /**
     * ��������� ������� ���� � ������  ������ + �������� ��������� �������
     * @param profile ������� ���� �� ����������
     * @param linklist ������ ������ �� ����������
     * @throws BusinessException
     */
    public void updateProfileAndLink(ErmbProfileImpl profile,List<ErmbProductLink> linklist) throws BusinessException
    {
        Map<ErmbFields,Object>  oldFields = new LinkedHashMap();
        ActiveOperationsLoggingAccessor accessor = new ActiveOperationsLoggingAccessor();
        LoggingMode loggingMode = ConfigFactory.getConfig(LoggingHelper.class).getLoggingMode(accessor);
        OperationsLogConfig config = ConfigFactory.getConfig(OperationsLogConfig.class);
        OperationsLogConfig.Level level = LoggingMode.EXTENDED == loggingMode ? config.getExtendedLevel() : config.getLevel();

        Long id = profile.getId();
        if (OperationsLogConfig.Level.MEDIUM == level && id != null)
        {
            ErmbProfileImpl oldProfile = findByPersonId(id);
            oldFields =  ErmbHelper.getErmbProfileFields(oldProfile);
        }

        simpleService.merge(profile);
        simpleService.merge(linklist);

        Map<ErmbFields,Object> fields = Collections.emptyMap();
        if (OperationsLogConfig.Level.MEDIUM == level)
        {
            Map<ErmbFields,Object>  newFields = (LinkedHashMap) ErmbHelper.getErmbProfileFields(profile);
            fields = ErmbHelper.getDifferentErmbProfileFields(newFields,oldFields);
        }
        else if (OperationsLogConfig.Level.DETAILED == level)
        {
            fields =  ErmbHelper.getErmbProfileFields(profile);
        }
        LinkedHashMap fieldsStr = (LinkedHashMap) ErmbHelper.ermbFieldsToStringView(fields);
        ErmbLogDataReader log = new ErmbLogDataReader(fieldsStr, Application.ASFilialListener.toString());
        try
        {
            OperationLogFactory.getLogWriter().writeActiveOperation(log, Calendar.getInstance(), Calendar.getInstance());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


	/**
	 * �������� ��� �� ������������� � ������� ��������
	 * @param profile ������� ����
	 * @throws BusinessException
	 */
	public void disconnect(ErmbProfileImpl profile) throws BusinessException
	{
		profile.setServiceStatus(false);
		profile.getPhones().clear();
		addOrUpdate(profile);
	}

	/**
	 * ����� ������� ��� �� id ������� ��� �� ���.
	 * @param personId id ������� ���� ��� id �������
	 * @return ������� ����
	 * @throws BusinessException
	 */
	public ErmbProfileImpl findByPersonId(final Long personId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ErmbProfileImpl>()
			{
				public ErmbProfileImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.profile.findByPersonId");
					query.setLong("personId", personId);
					return (ErmbProfileImpl) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}


    /**
     *
     * @param surName
     * @param name
     * @param patrName
     * @param docSeries
     * @param docNumber
     * @param birthDate
     * @param tb
     * @return
     * @throws BusinessException
     */
    public ErmbProfileImpl findByFIOAndDocInTB(String surName, String name, String patrName, String docSeries, String docNumber, Calendar birthDate, String tb) throws BusinessException
    {
        String FIO = (surName + name + StringHelper.getEmptyIfNull(patrName));
        String doc = (StringHelper.getEmptyIfNull(docSeries) + StringHelper.getEmptyIfNull(docNumber));
        return findByFIOAndDocInTB(FIO,doc,null, birthDate,tb);
    }

    /**
     * @param FIO
     * @param doc
     * @param instanceName
     * @param birthDate
     * @param tb
     * @return
     * @throws BusinessException
     */
    public ErmbProfileImpl findByFIOAndDocInTB(String FIO, String doc, String instanceName, final Calendar birthDate,final String tb) throws BusinessException
    {
        final String clientFIO = FIO.replace(" ", "").toUpperCase();
        final String clientDOC = doc.replace(" ", "");

        try
        {
            return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<ErmbProfileImpl>()
            {
                public ErmbProfileImpl run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.profile.findByFIOAndDoc");
                    query.setParameter("FIO",clientFIO );
                    query.setParameter("Doc", clientDOC);
                    query.setParameter("birthDay", birthDate);
	                query.setParameterList("regions", DepartmentService.getCorrectTBCodes(tb));

	                return (ErmbProfileImpl) query.uniqueResult();
                }
            });
        }
        catch(Exception ex)
        {
            throw new BusinessException("������ ��� ��������� ������� ����",ex);
        }
    }

	/**
	 * ���������� ������� �� ��������� ������ ��������
	 * @param phone - ����� ��������
	 * @return ������� ��� null, ���� �� �������
	 */
	public ErmbProfileImpl findByMainPhone(PhoneNumber phone) throws BusinessException
	{
		final String phoneAsString = PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phone);
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ErmbProfileImpl>()
			{
				public ErmbProfileImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.profile.findByMainPhone");
					query.setString("phone", phoneAsString);
					query.setMaxResults(1);
					return (ErmbProfileImpl)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������� �� ������ ��������
	 * @param phone - ����� ��������
	 * @return ������� ��� null, ���� �� �������
	 */
	public ErmbProfileImpl findByPhone(PhoneNumber phone) throws BusinessException
	{
		final String phoneAsString = PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phone);
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ErmbProfileImpl>()
			{
				public ErmbProfileImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.profile.findByPhone");
					query.setString("phone", phoneAsString);
					query.setMaxResults(1);
					return (ErmbProfileImpl)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ������� �� ������������
	 * @param person person
	 * @return ������ ����
	 * @throws BusinessException
	 */
	public ErmbProfileImpl findByUser(Person person) throws BusinessException
	{
		return findByPersonId(person.getId());
	}

	public ErmbProfileImpl findByLogin(Login login) throws BusinessException
	{
		ActivePerson person = personService.findByLogin(login);
		return findByUser(person);
	}

	/**
	 * @param loginId ����� id
	 * @return ���������� �� �������� ������� ����, � ���������� loginId
	 * @throws BusinessException
	 */
	public boolean isErmbProfileExistsByLoginId(final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.profile.ErmbProfileBusinessService.isErmbProfileExistsByLoginId");
					query.setParameter("loginId",loginId);
					Integer result = (Integer) query.uniqueResult();
					return result != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 *
	 * @param personId User id
	 * @return ���������� �� �������� ������� ����, � ���������� userId
	 * @throws BusinessException
	 */
	public boolean isErmbProfileExistsByPersonId(final Long personId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.profile.ErmbProfileBusinessService.isErmbProfileExistsByPersonId");
					query.setParameter("personId",personId);
					Integer result = (Integer) query.uniqueResult();
					return result != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� maxCount ����-�������� ��� �������� ����������� ����� � ����������� ��������� �������
	 * @param date - ����
	 * @param allJobPackSize - ������������ ���������� �������� ��� ������� ����� ������������ ���������� ����� dbms_random
	 * @param oneJobPackSize - ���������� �������� ����������� � ����� �����
	 * @return
	 * @throws BusinessException
	 */
	public List<ErmbProfileImpl> getSubscribeFeeProfiles(final Calendar date, final int allJobPackSize, final int oneJobPackSize) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(null).execute(new HibernateAction<List<ErmbProfileImpl>>()
			{
				public List<ErmbProfileImpl> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.Profile.getSubscribeeFeeProfiles.withLock");
					query.setParameter("currentDate", date);
					query.setParameter("allJobPackSize", allJobPackSize);
					query.setParameter("oneJobPackSize", oneJobPackSize);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ��������, ��� ������� ����� �������� ��������� ��������� � ���/Way
	 * @param date ���� ���������, �� ������� ����� �������� �������
	 * @param codOnly ������ ���������������� � ��� (������������ way)
	 * @param batchSize ������ �������� �� ������
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public List<ErmbProfileImpl> getProfilesToActivate(final Calendar date, final boolean codOnly, final int batchSize) throws BusinessException
	{
		final String queryName = codOnly ?
				"com.rssl.phizic.business.ermb.profile.ErmbProfileBusinessService.getProfilesToActivate.codOnly" :
				"com.rssl.phizic.business.ermb.profile.ErmbProfileBusinessService.getProfilesToActivate";
		try
		{
			return HibernateExecutor.getInstance(null).execute(new HibernateAction<List<ErmbProfileImpl>>()
			{
				public List<ErmbProfileImpl> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(queryName);
					query.setParameter("date", date);
					query.setMaxResults(batchSize);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������������� ������
	 * @param profileId ������� ����
	 * @param documentId ������
	 * @throws BusinessException
	 */
	public void updateIncompletePayment(final Long profileId, final Long documentId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizic.business.ermb.profile.ErmbProfileBusinessService.getProfilesToActivate.updateIncompletePayment")
							.setParameter("id", profileId)
							.setParameter("incompleteSmsPaymentId", documentId, Hibernate.LONG)
							.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ������ "������� ���������� ��� �������� ����������� �����" ��� ���� ����-��������
	 * @throws BusinessException
	 */
	public void unlockFppBlockedProfiles() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizic.business.ermb.ErmbProfileBusinessService.unlockFppBlockedProfiles").executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ���� ���������� ���-�������
	 * @throws BusinessException
	 */
	public void updateLastRequestTime(PhoneNumber number, Calendar currentTime) throws BusinessException
	{
		try
		{
			ErmbProfileImpl profile = findByPhone(number);
			if (profile!=null)
			{
				ErmbProfileStatistic statistic = simpleService.findById(ErmbProfileStatistic.class, profile.getId());
				if (statistic == null)
				{
					statistic = new ErmbProfileStatistic();
					statistic.setProfile(profile);
				}
				statistic.setLastRequestTime(currentTime);
				simpleService.addOrUpdate(statistic);

			}
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public ErmbProfileStatistic findStatisticById(Long id) throws BusinessException
	{
		return simpleService.findById(ErmbProfileStatistic.class, id);
	}

	/**
	 * �������� ���� ���������� ������� � ��� ������ ��� �������� �� ���� �������
	 * @param number �������
	 * @return ���� ���������� ��� ������� (null ���� �� ����)
	 * @throws BusinessException
	 */
	public Calendar getLastRequestTime(PhoneNumber number) throws BusinessException
	{
		try
		{
			ErmbProfileImpl profile = findByPhone(number);
			if (profile == null)
				return null;

			ErmbProfileStatistic statistic = simpleService.findById(ErmbProfileStatistic.class, profile.getId());
			if (statistic == null)
				return null;

			return statistic.getLastRequestTime();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
