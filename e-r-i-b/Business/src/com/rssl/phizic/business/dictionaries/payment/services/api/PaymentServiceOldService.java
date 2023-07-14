package com.rssl.phizic.business.dictionaries.payment.services.api;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.payment.services.DublicatePaymentServiceException;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author lukina
 * @ created 27.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class PaymentServiceOldService
{
	private static final SimpleService simpleService = new SimpleService();
	private static final ImageService imageService = new ImageService();
	/**
	 * ��������� ������ �� id
	 * @param id ������
	 * @return ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public PaymentServiceOld findById(final Long id) throws BusinessException
	{
		return simpleService.findById(PaymentServiceOld.class, id);
	}

	/**
	 * ��������� ������ ����� ����� �� ������ ��������������� ����� �����
	 * @param  ids �������������� ����� �����
	 * @return ������ ����� �����
	 * @throws BusinessException
	 */
	public List<PaymentServiceOld> findByIds(final List<Long> ids) throws BusinessException
	{
		return simpleService.findByIds(PaymentServiceOld.class, ids);
	}

		/**
	 * �������� ������
	 * @param paymentService ��������� ������
	 * @throws BusinessException
	 */
	public void remove (final PaymentServiceOld paymentService) throws BusinessException, BusinessLogicException
	{
		try
		{
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        session.delete(paymentService);
			        session.flush();
		            return null;
		        }
		    });
			//�������� ����� �������, ���� �� ��� ��� ������.
			if (paymentService.getImageId() != null)
				imageService.removeImageById(paymentService.getImageId(), null);

		}
		catch (ConstraintViolationException e)
		{
		   throw new BusinessLogicException(paymentService.getName());
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������
	 * @param paymentService ������ ������
	 * @return ����������� ������
	 * @throws BusinessException
	 */
	public PaymentServiceOld addOrUpdate( final PaymentServiceOld paymentService) throws BusinessException, DublicatePaymentServiceException
	{
		{
			try
			{
				PaymentServiceOld dbPaymentService = HibernateExecutor.getInstance().execute(new HibernateAction<PaymentServiceOld>()
					{
						public PaymentServiceOld run(Session session) throws Exception
						{
							session.saveOrUpdate(paymentService);
							session.flush();
							return paymentService;
						}
					}
				);
				return dbPaymentService;
			}
			catch(ConstraintViolationException e)
			{
				throw new DublicatePaymentServiceException();
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
	}


   /**
	 * ��������� �� � ������ ����������
	 * @param id ������
	 * @return  true - ���������; false - �� ���������
	 * @throws BusinessException
	 */
	public boolean hasProvider(final Long id) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PaymentServiceOld.class.getName() + ".getCountOfProvider");
					query.setParameter("id", id);
					return Integer.decode(query.uniqueResult().toString()) > 0;
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ������ ����������� ����� (������ �������)
	 * @param paymentService ������-������
	 * @return ������ �����-��������
	 * @throws BusinessException
	 */
	public List<PaymentServiceOld> getChildrens(final PaymentServiceOld paymentService) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<PaymentServiceOld>>()
			{
				public List<PaymentServiceOld> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.payment.services.api.getPaymentServiceChildren");
					query.setParameter("parent", paymentService);
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
	 * ������� �������� ������
	 *
	 * @param id ������
	 * @return  ������� ��������
	 * @throws BusinessException
	 */
	public Integer getLevelOfHierarchy(final Long id) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PaymentServiceOld.class.getName() + ".getLevelOfHierarchy");
					query.setParameter("id", id);
					Object res = query.uniqueResult();
					return res == null ? 0 : Integer.decode(res.toString());
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

}
