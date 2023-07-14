package com.rssl.phizic.business.dictionaries.billing;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author lepihina
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceBillingService
{
	private static final SimpleService simpleService = new SimpleService();
	private static final String UNIQUE_KEY_ON_ADAPTER_UUID_NAME = "UK_BILLINGS_ADAPTER_UUID";
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	/**
	 * Получение биллинговой системы по id
	 * @param id - идентификатор биллинговой системы
	 * @param instance - имя инстанса модели БД
	 * @return биллинговая система
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Billing getById(Long id, String instance) throws BusinessException
	{
		return simpleService.findById(Billing.class, id, instance);
	}

	/**
	 * Получение биллинговой системы по коду биллинговой системы
	 * @param code - код биллинговой системы
	 * @param instance - имя инстанса модели БД
	 * @return биллинговая система
	 * @throws BusinessException
	 */
	public Billing getByCode(final String code, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Billing>()
			{
				public Billing run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(Billing.class.getName() + ".findByCode");
					query.setParameter("code", code);

					return (Billing) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Сохранение биллинговой системы
	 * @param billing - биллинговая система
	 * @param instance - имя инстанса модели БД
	 * @return сохраненная биллинговая система
	 * @throws BusinessException, DublicateBillingException
	 */
	public Billing addOrUpdate(final Billing billing, String instance) throws BusinessException, DublicateBillingException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Billing>()
			{
				public Billing run(Session session) throws Exception
				{
					session.saveOrUpdate(billing);
					addToLog(session, billing, ChangeType.update);
					session.flush();
					return billing;
				}
			}
			);
		}
		catch(ConstraintViolationException e)
		{
			String constraintName = StringHelper.getEmptyIfNull(e.getSQLException().getMessage());
			if (constraintName.contains(UNIQUE_KEY_ON_ADAPTER_UUID_NAME))
				throw new DublicateBillingException("К одному адаптеру можно подключить только одну " +
						"биллинговую систему. Пожалуйста, укажите другой адаптер.", e);
			else
				throw new DublicateBillingException("Биллинговая система с таким идентификатором уже существует", e);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаление биллинговой системы
	 * @param billing - биллинговая система
	 * @param instance - имя инстанса модели БД
	 * @exception BusinessException
	 * */
	public void remove (final Billing billing, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(billing);
					addToLog(session, billing, ChangeType.delete);
					session.flush();
					return null;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Система " + billing.getName() + " не может быть удалена из справочника, так как имеются поставщики услуг, использующие ее.", e);
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void addToLog(Session session, Billing billing, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(session, billing, changeType);
	}
}
