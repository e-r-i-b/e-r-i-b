package com.rssl.phizic.test.mbvmock;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.test.MbvMockHibernateExecutor;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * User: Moshenko
 * Date: 10.09.13
 * Time: 10:47
 * Сервис для работы с заглушкой MBV
 */
public class MbvMockService
{
	/**
	 * @param phoneNumber номер телефона
	 * @return Данные по запросу GetClientByPhone
	 */
	public GetClientByPhone getGetClientByPhone(final String phoneNumber) throws BusinessException
	{
		try
		{
			return MbvMockHibernateExecutor.getInstance(false).execute(new HibernateAction<GetClientByPhone>()
			{
				public GetClientByPhone run(Session session) throws Exception
				{
                    Query query = session.getNamedQuery("com.rssl.phizic.test.mbvmock.MbvMockService.getGetClientByPhone");
                    query.setParameter("phoneNumber", phoneNumber);
                    return (GetClientByPhone) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

    /**
     * @param phoneNumber номер телефона
     * @return Данные по запросу DiscByPhone
     */
    public DiscByPhone getDiscByPhone(final String phoneNumber) throws BusinessException
    {
        try
        {
            return MbvMockHibernateExecutor.getInstance(false).execute(new HibernateAction<DiscByPhone>()
            {
                public DiscByPhone run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.test.mbvmock.MbvMockService.getDiscByPhone");
                    query.setParameter("phoneNumber", phoneNumber);
                    return (DiscByPhone) query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

    /**
     * @param lastName   Ф
     * @param firstName  И
     * @param middleName О
     * @param birthday   ДР
     * @param docType    Тип документа
     * @param docSeries  Серия документа
     * @param docNum     Номер документа
     * @return Данные по запросу ClientAccPh
     * @throws BusinessException
     */
    public ClientAccPh getClientAccPhRq(String firstName,
                                        String lastName,
                                        String middleName,
                                        final Calendar birthday,
                                        final String docType,
                                        final String docSeries,
                                        final String docNum) throws BusinessException {

        final String clientFIO =  getFIO(lastName,firstName,middleName);

        try
        {
            return MbvMockHibernateExecutor.getInstance(false).execute(new HibernateAction<ClientAccPh>()
            {
                public ClientAccPh run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.test.mbvmock.MbvMockService.getClientAccPhRq");
                    query.setParameter("FIO", clientFIO);
                    query.setParameter("birthday", birthday);
                    query.setParameter("docType", docType);
                    query.setParameter("docSeries", docSeries);
                    query.setParameter("docNum", docNum);
                    return  (ClientAccPh)query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

    /**
     * @param lastName   Ф
     * @param firstName  И
     * @param middleName О
     * @param birthday   ДР
     * @param docType    Тип документа
     * @param docSeries  Серия документа
     * @param docNum     Номер документа
     * @return Данные по запросу BeginMigration
     * @throws BusinessException
     */
    public BeginMigration getBeginMigration(String firstName,
                                            String lastName,
                                            String middleName,
                                            final Calendar birthday,
                                            final String docType,
                                            final String docSeries,
                                            final String docNum) throws BusinessException {

        final String clientFIO =  getFIO(lastName,firstName, middleName);

        try
        {
            return MbvMockHibernateExecutor.getInstance(false).execute(new HibernateAction<BeginMigration>()
            {
                public BeginMigration run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.test.mbvmock.MbvMockService.getBeginMigration");
                    query.setParameter("FIO", clientFIO);
                    query.setParameter("birthday", birthday);
                    query.setParameter("docType", docType);
                    query.setParameter("docSeries", docSeries);
                    query.setParameter("docNum", docNum);
                    return  (BeginMigration)query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }


    /**
     * @param migrationId   id миграции
     * @return Данные по запросу CommitMigration
     * @throws BusinessException
     */
    public CommitMigration getCommitMigration(final String migrationId) throws BusinessException
    {
        try
        {
            return MbvMockHibernateExecutor.getInstance(false).execute(new HibernateAction<CommitMigration>()
            {
                public CommitMigration run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.test.mbvmock.MbvMockService.getCommitMigration");
                    query.setParameter("migId", migrationId);
                    return  (CommitMigration)query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

    /**
     * @param migrationId  id миграции
     * @return Данные по запросу RollbackMigration
     * @throws BusinessException
     */
    public RollbackMigration getRollbackMigration(final String migrationId) throws BusinessException
    {
        try
        {
            return MbvMockHibernateExecutor.getInstance(false).execute(new HibernateAction<RollbackMigration>()
            {
                public RollbackMigration run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.test.mbvmock.MbvMockService.getRollbackMigration");
                    query.setParameter("migId", migrationId);
                    return  (RollbackMigration)query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

    /**
     * @param migrationId  id миграции
     * @return Данные по запросу ReverseMigration
     * @throws BusinessException
     */
    public ReverseMigration getReverseMigration(final String migrationId) throws BusinessException
    {
        try
        {
            return MbvMockHibernateExecutor.getInstance(false).execute(new HibernateAction<ReverseMigration>()
            {
                public ReverseMigration run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.test.mbvmock.MbvMockService.getReverseMigration");
                    query.setParameter("migId", migrationId);
                    return  (ReverseMigration)query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

    private String getFIO(String firstName,String lastName,String middleName)
    {
        String FIO = (firstName + lastName + StringHelper.getEmptyIfNull(middleName));
        return FIO.replace(" ", "").toUpperCase();
    }
}


