package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Erkin
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ������ � XSLT-������
 */
public class XSLTService
{
	private static final String QUERY_PREFIX = "com.rssl.phizic.business.payments.forms.meta.XSLTBean.";

	private static final SimpleService simpleService = new SimpleService();

	public static final String PFR_STATEMENT_NAME = "pfr-statement";

	public static final String PFR_STATEMENT_ATM_NAME = "pfr-statement-atm";

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ������ ���� ��������� XSLT-��� � ��.
	 * � ���� ������ ���� ��������� ����:
	 *  - xsltName,
	 *  - xsltTemplate,
	 *  - xsd
	 * @param xsltBean - XSLT-���
	 * @throws BusinessException
	 */
	public void addOrUpdate(XSLTBean xsltBean) throws BusinessException
	{
		if (xsltBean == null)
			throw new NullPointerException("�������� 'xsltBean' �� ����� ���� null");

		simpleService.addOrUpdate(xsltBean);
	}

	/**
	 * ���������� XSLT-��� �� �����
	 * @param name - ��� XSLT-����
	 * @return XSLT-��� ���� null, ���� ��� �� ������
	 * @throws BusinessException
	 */
	public XSLTBean getBeanByName(final String name) throws BusinessException
	{
		if (StringHelper.isEmpty(name))
			throw new IllegalArgumentException("�������� 'name' �� ����� ���� ������");

		try
	    {
			return HibernateExecutor.getInstance().execute(new HibernateAction<XSLTBean>()
			{
				public XSLTBean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getBeanByName");
					query.setParameter("name", name);
					return (XSLTBean) query.uniqueResult();
				}
			});
	    }
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * ���������� XSLT-������ �� ����� XSLT-����
	 * @param name - ��� XSLT-����
	 * @return XSLT-������ ���� null, ���� XSLT-��� �� ������
	 * @throws BusinessException
	 */
	public String getXSLTByName(final String name) throws BusinessException
	{
		if (StringHelper.isEmpty(name))
			throw new IllegalArgumentException("�������� 'name' �� ����� ���� ������");

		try
	    {
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getXSLTByName");
					query.setParameter("name", name);
					return (String) query.uniqueResult();
				}
			});
	    }
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * ���������� XSD-����� �� ����� XSLT-����
	 * @param name - ��� XSLT-����
	 * @return XSD-����� ���� null, ���� XSLT-��� �� ������
	 * @throws BusinessException
	 */
	public String getXSDByName(final String name) throws BusinessException
	{
		if (StringHelper.isEmpty(name))
			throw new IllegalArgumentException("�������� 'name' �� ����� ���� ������");

		try
	    {
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getXSDByName");
					query.setParameter("name", name);
					return (String) query.uniqueResult();
				}
			});
	    }
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}
}
