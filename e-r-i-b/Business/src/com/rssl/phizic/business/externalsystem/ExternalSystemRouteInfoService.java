package com.rssl.phizic.business.externalsystem;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.routing.Adapter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;

import java.util.List;

/**
 * Сервис для соответствий продуктов и внешних систем(таблица EXTERNAL_SYSTEM_ROUTE_INFO)
 * @author niculichev
 * @ created 05.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class ExternalSystemRouteInfoService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Коды внешних систем из таблицы соответствий по продуктам, в разрезе тербанка
	 * @param office офис клиента
	 * @param product продукт
	 * @return список кодов внешних систем
	 */
	public List<Adapter> findByRouteInfo(final Office office, final BankProductType product) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Adapter>>()
			{
				public List<Adapter> run(Session session)
				{
					Query query = session.getNamedQuery(ExternalSystemRouteInfo.class.getName() + ".products.list")
							.setParameter("tbCode", StringHelper.addLeadingZeros(new SBRFOfficeCodeAdapter(office.getCode()).getRegion(), 2))
							.setParameter("productType", product.name());

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
	 * Коды внешних систем из таблицы соответствий по запросу УДБО, в разрезе тербанка
	 * @param tbCode код ТБ
	 * @return список кодов внешних систем
	 */
	public List<Adapter> findByRouteInfo(final String tbCode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Adapter>>()
			{
				public List<Adapter> run(Session session)
				{
					Query query = session.getNamedQuery(ExternalSystemRouteInfo.class.getName() + ".udbo.list")
							.setParameter("tbCode", tbCode);

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
	 * Список продуктов для данной внешней системы.
	 *
	 * @param adapterId идентификатор внешней системы.
	 * @return список продуктов.
	 * @throws BusinessException
	 */
	public List<String> getProductTypeForAdapterId(String adapterId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ExternalSystemRouteInfo.class);
		criteria.add(Expression.eq("systemId", adapterId));
		criteria.setProjection(Projections.property("productType"));
		return simpleService.find(criteria);
	}
}
