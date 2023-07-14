package com.rssl.phizic.business.loans.kinds;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.LongType;

import java.util.List;
import java.util.Map;

/**
 * @author gladishev
 * @ created 17.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoanKindService
{
	private static final SimpleService service = new SimpleService();

	/**
	 * Добавить/Обновить вид кредита
	 * @param kind - вид кредита
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void addOrUpdate(final LoanKind kind) throws BusinessException
	{
		service.addOrUpdateWithConstraintViolationException(kind);
	}

	/**
	 * Удалить вид кредита
	 * @param kind - вид кредита
	 */
	public void remove(final LoanKind kind) throws BusinessException
	{
		service.removeWithConstraintViolationException(kind);
	}

	/**
	 * Список всех видов кредита
	 * @throws BusinessException
	 */
	public List<LoanKind> getAll() throws BusinessException
	{
		return service.getAll(LoanKind.class);
	}

	/**
	 * Найти вид кредита по Id
	 * @throws BusinessException
	 */
	public LoanKind findById ( final Long id ) throws BusinessException
	{
		return service.findById(LoanKind.class,id);
	}

	/**
	 * Список видов кредитов с учетом фильтрации
	 * @param filterParams параметры фильтрации
	 * @return Список видов кредитов
	 * @throws BusinessException
	 */
	public List<LoanKind> findByFilter(final Map<String, Object> filterParams) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<LoanKind>>()
		    {
		        public List<LoanKind> run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery(LoanKind.class.getName() + ".findByFilter");
			        query.setParameter("kindId", filterParams.get("kind"), new LongType());
		            return query.list();
				}
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}
}
