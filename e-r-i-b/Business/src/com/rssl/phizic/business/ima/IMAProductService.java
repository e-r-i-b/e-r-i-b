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
 * ������ ��� ������ � ���
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
	 * �������� ��� ���� ��� ��� ��������
	 * @return ������ ���
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
	 * ���������� ��������� ���������� �� ��� �� ��������������
	 * @param id - ��� ����� �� �����������
	 * @param additionalId - ������ ����� �� �����������
	 * @return ��������� ���������� �� ���
	 * @throws BusinessException
	 */
	public IMAProductPart findByExternalId(Long id, Long additionalId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(IMAProductPart.class).add(Expression.eq("type", id)).add(Expression.eq("subType", additionalId));
		return service.findSingle(criteria);
	}

	/**
	 * ���������� ���������� �� ��� �� ��������������
	 * @param id �������������
	 * @return ���
	 * @throws BusinessException
	 */
	public IMAProduct findById(Long id) throws BusinessException
	{
		/**
		 * ������� ������: PK_IMAPRODUCT
		 * ��������� �������: "ID"=TO_NUMBER(:ID)
		 * ��������������: 1
		 */
		return localedSimpleService.findById(IMAProduct.class, id, null);
	}

	/**
	 * ������� �������, ����������� � ���
	 * @param id ������������� ���
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
