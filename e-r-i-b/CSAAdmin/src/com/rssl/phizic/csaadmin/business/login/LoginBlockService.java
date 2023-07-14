package com.rssl.phizic.csaadmin.business.login;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.common.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 30.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с блокировками логинов
 */

public class LoginBlockService extends SimpleService<LoginBlock>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	@Override
	protected Class<LoginBlock> getEntityClass()
	{
		return LoginBlock.class;
	}

	/**
	 * получение списка блокировок для логина на дату
	 * @param login логин
	 * @param blockUntil дата блокировки
	 * @return блокировки
	 * @throws AdminException
	 */
	public List<LoginBlock> getAllForLogin(Login login, Calendar blockUntil) throws AdminException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(LoginBlock.class);
		criteria.add(Expression.eq("login", login));
		criteria.add(Expression.le("blockedFrom", blockUntil));
		criteria.add(Expression.or(Expression.isNull("blockedUntil"), Expression.ge("blockedUntil", blockUntil)));
		return find(criteria);
	}

	/**
	 * снятие блокировок с логина
	 * @param login логин
	 * @param blockUntil дата закрытия блокировки
	 * @return список снятых блокировок
	 * @throws AdminException
	 */
	public Pair<List<LoginBlock>,List<LoginBlock>> unlock(Login login, final Calendar blockUntil) throws AdminException, AdminLogicException
	{
		List<LoginBlock> loginBlocks = getAllForLogin(login, blockUntil);
		Pair<List<LoginBlock>, List<LoginBlock>> result = new Pair<List<LoginBlock>, List<LoginBlock>>(new ArrayList<LoginBlock>(), new ArrayList<LoginBlock>());
		if (CollectionUtils.isEmpty(loginBlocks))
			return result;

		for (final LoginBlock loginBlock : loginBlocks)
		{
			try
			{
				result.getFirst().add(execute(new HibernateAction<LoginBlock>()
				{
					public LoginBlock run(Session session) throws Exception
					{
						loginBlock.setBlockedUntil(blockUntil);
						session.update(loginBlock);
						return loginBlock;
					}
				}
				));
			}
			catch (AdminException e)
			{
				log.error("Ошибка снятия блокировки с id=" + loginBlock.getId(), e);
				result.getSecond().add(loginBlock);
			}

		}
		return result;
	}

	/**
	 * Заблокировать login с текущего момента
	 * @param login - логин для блокировки
	 * @param blockedTimeout - время действия блокировки (в секундах)
	 * @return блокировка
	 * @throws AdminException
	 */
	public LoginBlock wrongLogonLock(Login login, int blockedTimeout) throws AdminException
	{
		Calendar blockedUntil = null;
		if(blockedTimeout != 0)
		{
			blockedUntil = Calendar.getInstance();
			blockedUntil.add(Calendar.SECOND, blockedTimeout);
		}

		LoginBlock loginBlock = new LoginBlock();
		loginBlock.setReasonType(BlockType.wrongLogons);
		loginBlock.setBlockedFrom(Calendar.getInstance());
		loginBlock.setBlockedUntil(blockedUntil);
		loginBlock.setLogin(login);
		loginBlock.setEmployee(null);
		return super.save(loginBlock);
	}
}
