package com.rssl.phizic.security;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Date;

/**
 * @author Erkin
 * @ created 23.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для работы с confirm-бинами в базе
 */
public class ConfirmBeanService
{
	/**
	 * Удалить просроченные бины
	 */
	public void removeOverdueConfirmBeans()
	{
		final Date now = new Date();
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.security.ConfirmBean.removeOverdue");
					query.setTimestamp("now", now);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @param confirmBean  Данные подтверждения для сохранения в базе
	 * @return true  если успешно
	 */
	public boolean addConfirmBean(final ConfirmBean confirmBean)
	{
		checkConfirmCode(confirmBean.getPrimaryConfirmCode());
		String secondaryConfirmCode = confirmBean.getSecondaryConfirmCode();
		if (!StringHelper.isEmpty(secondaryConfirmCode))
			checkConfirmCode(secondaryConfirmCode);

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					session.save(confirmBean);
					return true;
				}
			});
		}
		catch (ConstraintViolationException ignored)
		{
			// код не уникален
			return false;
		}
		catch (Exception e)
		{
			throw new InternalErrorException("Не удалось добавить в базу confirm-бин " + confirmBean, e);
		}
	}

	private void checkConfirmCode(String confirmCode)
	{
		if (confirmCode.length() > ConfirmBean.CONFIRM_CODE_MAX_LENGTH)
			throw new InternalErrorException("Превышен максимально допустимый размер кода подтверждения: " + confirmCode);
	}

	/**
	 * Забирает confirm-бин из базы
	 * Поиск осуществляется по клиенту, телефону и коду подтверждения
	 * Найденный confirm-бин удаляется из базы
	 * @param person - клиент
	 * @param confirmCode - код подтверждения
	 * @param phone телефон, на который отсылался код подтверждения
	 * @param primary true - основной код (набор цифр), false - дополнительный (текст смс)
	 * @return confirm-бин или null, если не найдено
	 *
	 * ВАЖНО! Из двух паралельных вызовов с одинаковыми параметрами "второй" вернёт null
	 */
	ConfirmBean captureConfirmBean(final Person person, final String confirmCode, final String phone, final boolean primary)
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ConfirmBean>()
			{
				public ConfirmBean run(Session session) throws Exception
				{
					String queryName = primary
							? "com.rssl.phizic.security.ConfirmBean.capturePrimary"
							: "com.rssl.phizic.security.ConfirmBean.captureSecondary";
					Query query = session.getNamedQuery(queryName);
					query.setLong("loginId", person.getLogin().getId());
					query.setString("confirmCode", confirmCode);
					query.setString("phone", phone);
					ConfirmBean confirmBean = (ConfirmBean) query.uniqueResult();

					if (confirmBean != null)
						session.delete(confirmBean);

					return confirmBean;
				}
			});
		}
		catch (Exception e)
		{
			throw new InternalErrorException("Сбой на получении confirm-бина", e);
		}
	}

	/**
	 * Проверяет, существует ли код потверждения, расстояние Левенштейна до которого от переданного кода подтверждения равно 1 (получается из переданного добавлением/удалением/изменением одного символа)
	 * @param person - клиент
	 * @param confirmCode - код подтверждения
	 * @param phone телефон, на который отсылался код подтверждения
	 * @return
	 */
	boolean similarConfirmBeanExists(final Person person, final String confirmCode, final String phone)
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.security.ConfirmBean.similarConfirmBeanExists");
					query.setLong("loginId", person.getLogin().getId());
					query.setString("confirmCode", confirmCode);
					query.setTimestamp("now", new Date());
					query.setString("phone", phone);
					return (!query.list().isEmpty());
				}
			});
		}
		catch (Exception e)
		{
			throw new InternalErrorException(e);
		}
	}
}
