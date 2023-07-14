package com.rssl.phizic.business.ima;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.locale.MultiLocaleDetachedCriteria;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.business.locale.MultiLocaleSimpleService;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * Сервис для работы с ОМС
 * @author Pankin
 * @ created 27.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAProductService extends SimpleService
{
	private static final SimpleService service = new SimpleService();
	private static final MultiLocaleSimpleService localedSimpleService = new MultiLocaleSimpleService();

	/**
	 * Получить все виды ОМС для открытия
	 * @return список ОМС
	 */
	public List<IMAProduct> getAll() throws BusinessException
	{
		try
		{
			BeanQuery query = MultiLocaleQueryHelper.getQuery("com.rssl.phizic.business.ima.IMAProduct.getAll");
			return query.executeListInternal();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает частичную информацию об ОМС по идентификатору
	 * @param id - вид счета из справочника
	 * @param additionalId - подвид счета из справочника
	 * @return частичная информация об ОМС
	 * @throws BusinessException
	 */
	public IMAProductPart findByExternalId(Long id, Long additionalId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(IMAProductPart.class).add(Expression.eq("type", id)).add(Expression.eq("subType", additionalId));
		return service.findSingle(criteria);
	}

	/**
	 * Возвращает информацию об ОМС по идентификатору
	 * @param id идентификатор
	 * @return ОМС
	 * @throws BusinessException
	 */
	public IMAProduct findById(Long id) throws BusinessException
	{
		/**
		 * Опорный объект: PK_IMAPRODUCT
		 * Предикаты доступа: "ID"=TO_NUMBER(:ID)
		 * Кардинальность: 1
		 */
		return localedSimpleService.findById(IMAProduct.class, id, null);
	}

	/**
	 * Удаляет ресурсы, привязанные к ОМС
	 * @param id идентификатор ОМС
	 * @throws BusinessException
	 */
	public void deleteIMAProductResources(final Long id) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ima.IMAProduct.deleteIMAProductResources");
					query.setParameter("imaId", id);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
