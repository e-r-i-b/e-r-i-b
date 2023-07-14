package com.rssl.phizic.business.loans.products;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.LoanGlobal;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.util.List;

import org.hibernate.Session;

/**
 * @author gladishev
 * @ created 17.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoanProductService
{
	private static final SimpleService service = new SimpleService();

	public static final String CURRENT = "current";

	/**
	 * ��������/�������� �������
	 * @param product
	 * @return
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public LoanProduct addOrUpdate(LoanProduct product) throws BusinessException
	{
		return service.addOrUpdate(product);
	}

	/**
	 * ������� ��������� �������
	 * @param product �������
	 */
	public void remove(LoanProduct product) throws BusinessException
	{
		service.remove(product);
	}

	/**
	 * ������ ���� ���������
	 * @return
	 * @throws BusinessException
	 */
	public List<LoanProduct> getAllProducts() throws BusinessException
	{
		return service.getAll(LoanProduct.class);
	}

	public LoanProduct findById ( final Long id ) throws BusinessException
	{
		return service.findById(LoanProduct.class,id);
	}

	/**
	 * �������� ����� �������� ��� ��
	 * @return
	 * @throws BusinessException
	 */
	public LoanGlobal getGlobal() throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<LoanGlobal>()
		    {
		        public LoanGlobal run(Session session) throws Exception
		        {
			        LoanGlobal global = (LoanGlobal)session.getNamedQuery("GetLoanGlobal")
					        .uniqueResult();
			        if( global==null )
			            throw new BusinessException("�� ������� ���������� �������� ��������� ���������.");
			        return global;
		        }
		    });
		}
		catch(BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ����� ��������.
	 * @param global
	 * @throws BusinessException
	 */
	public void setGlobal(final LoanGlobal global) throws BusinessException
	{
		try
		{
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        global.setKey(CURRENT);
			        session.saveOrUpdate(global);
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
