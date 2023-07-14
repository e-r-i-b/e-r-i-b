package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Set;

/**
 * User: moshenko
 * Date: 10.10.12
 * Time: 15:22
 * Сервис для работы с телефонами  ЕРМБ
 */
public class ErmbClientPhoneService
{
	private final static SimpleService simpleService = new SimpleService();
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	/**
	 * обновить/сохранить номер клиента ЕРМБ
	 * @param ermbClientPhone номер клиента ЕРМБ
	 * @throws BusinessException
	 * @throws DublicatePhoneException
	 */
	public void addOrUpdate(final ErmbClientPhone ermbClientPhone) throws BusinessException, DublicatePhoneException
	{
		try
		{
			simpleService.addOrUpdateWithConstraintViolationException(ermbClientPhone);
		}
		catch(ConstraintViolationException e)
		{
			throw new DublicatePhoneException(e,ermbClientPhone.getNumber());
		}
	}

	/**
	 * Обновить существующий номер телефона.
	 * @param ermbClientPhone - телефон
	 * @throws BusinessException
	 */
	public void update(final ErmbClientPhone ermbClientPhone) throws BusinessException
	{
		simpleService.update(ermbClientPhone);
	}

	/**
	 * Найти телефон по номеру
	 * @param phoneNumber phoneNumber
	 * @return телефон ЕРМБ
	 * @throws Exception
	 */
	public ErmbClientPhone findPhoneByNumber(final String phoneNumber) throws BusinessException
	{
		try
		{
			return  HibernateExecutor.getInstance(null).execute(new HibernateAction<ErmbClientPhone>()
			{
				public ErmbClientPhone run(Session session) throws BusinessException
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.ErmbClientPhone.findPhoneByNumber");
					query.setParameter("number", phoneNumber);
					return (ErmbClientPhone)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * Проверка номера на доступность
	 * @param number Номер добавляемого телефона
	 * @return true если телефон свободен
	 */
	public boolean isPhoneNumberAvailable(final String number) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{

					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.ErmbClientPhone.isPhoneNumberAvailable");
					query.setParameter("number", number);
					return (Boolean) query.uniqueResult();
					}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}


	/**
	 * получить сущность временного телефона по номеру телефона
	 * @param phoneNumber номер телефона
	 * @return сущность временного телефона
	 * @throws BusinessException ошибочка
	 */
	public ErmbTmpPhone findTempPhoneByNumber(final String phoneNumber) throws BusinessException
	{
		try
		{
			return  HibernateExecutor.getInstance(null).execute(new HibernateAction<ErmbTmpPhone>()
			{
				public ErmbTmpPhone run(Session session) throws BusinessException
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.ErmbTmpPhone.findPhoneByNumber");
					query.setParameter("number", phoneNumber);
					return (ErmbTmpPhone)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * Определяет существует ли в ЕРМБ указанный телефон
	 * @param phone - номер телефона
	 * @return true - ЕРМБ-телефон существует
	 */
	public boolean isErmbPhone(PhoneNumber phone) throws BusinessException
	{
		final String phoneAsString = PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phone);
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.phone.isErmbPhone");
					query.setString("phone", phoneAsString);
					query.setMaxResults(1);
					return Integer.valueOf(1).equals(query.uniqueResult());
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public void remove(ErmbClientPhone clientPhone) throws BusinessException
	{
		simpleService.remove(clientPhone);
	}

	/**
	 * Удаляем телефон с проверкой профиля
	 * Если телефон был единственный, то профиль блокируется.
	 * Если телефон был основное и есть еще телефон, то следующий телефон становится основным.
	 * @param phone удаляемый телефон.
	 * @throws BusinessException
	 */
	public void removeWhithProfileCheck(ErmbClientPhone phone) throws BusinessException
	{
		//профиль текущего владельца
		ErmbProfileImpl holder = phone.getProfile();
		//если телефон был основной
		if (phone.isMain())
		{
			Set<ErmbClientPhone> holderPhones = holder.getPhones();
			if (holderPhones.size() == 1)
			{
				// Не выставляем клиентскую блокировку согласно
				// BUG075813: ЕРМБ : АРМ Сотрудника : Блокировка услуги при удалении номера
				// ErmbHelper.blockProfile(oldHolder, ErmbHelper.NO_PHONE_BLOCK_DESCRIPTION);
				holder.setServiceStatus(false);
			}
			else
			{//иначе установим первый оставшийся телефон активным
				holderPhones.remove(phone);
				ErmbClientPhone nextMainPhone = holderPhones.iterator().next();
				nextMainPhone.setMain(true);
			}
			profileService.addOrUpdate(holder);
		}
		remove(phone);
	}
}
