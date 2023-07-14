package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 27.02.14
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceDepositProductService
{
	private static final MultiInstanceSimpleService multiInstanceSimpleService = new MultiInstanceSimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	private static final String CURRENT = "current";

	/**
	 * @param instanceName - ������� ��
	 * @return ������ ���� ���������� ���������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<DepositProduct> getAllProducts(String instanceName) throws BusinessException
	{
		return multiInstanceSimpleService.getAll(DepositProduct.class,instanceName);
	}

	/**
	 * ������ ���������� ���������, �������� ������� �������� nameFilter
	 * @param nameFilter �������� (����� ��������) ������
	 * @param instanceName - ������� ��
	 * @return ������ DepositProduct
	 */
	public List<DepositProduct> getProductsByName(String nameFilter, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(DepositProduct.class);
		criteria.add(Expression.ilike("name", nameFilter, MatchMode.ANYWHERE));
		return multiInstanceSimpleService.find(criteria,instanceName);
	}

	/**
	 * ���������� ������ ���������� ���������, ������� ����������� ��� �������� � ����
	 * @param instanceName - ������� ��
	 * @return ������ ���������� ���������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<DepositProduct> getNotAvailableOnlineProducts(String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<DepositProduct>>()
			{
				public List<DepositProduct> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.deposits.DepositProduct.getNotOnlineAvailable");
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
	 * ��������� ����������� �������� �� ������ ����
	 * @param productId - ����� ���� ����������� ��������
	 * @param instanceName - ������� ��
	 * @return DepositProduct
	 */
	public DepositProduct findByProductId(final Long productId, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<DepositProduct>()
			{
				public DepositProduct run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.deposits.DepositProduct.getByProductId");
					query.setParameter("productId", productId);
					return (DepositProduct) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param instanceName - ������� ��
	 * @return ����� �������� ��� ��
	 * @throws BusinessException
	 */
	public DepositGlobal getGlobal(String instanceName) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<DepositGlobal>()
		    {
		        public DepositGlobal run(Session session) throws Exception
		        {
			        DepositGlobal global = (DepositGlobal)session.getNamedQuery("GetDepositGlobal").uniqueResult();
			        if( global==null )
			            throw new BusinessException("�� ������� ���������� �������� ���������� ���������.");
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
	 * ���������� ����� �������� ���������� ���������.
	 * @param global - ����� �������� ���������� ���������.
	 * @param instanceName - ������� ��
	 * @throws BusinessException
	 */
	public void setGlobal(final DepositGlobal global, String instanceName) throws BusinessException
	{
		try
		{
		    HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        global.setKey(CURRENT);
			        session.saveOrUpdate(global);
			        dictionaryRecordChangeInfoService.addChangesToLog(session, global, ChangeType.update);
			        return null;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������� � ��
	 * @param product - ������� ��� ����������
	 * @param instanceName - ������� ��
	 * @return ����������� �������
	 * @throws BusinessException
	 */
	public DepositProduct update(final DepositProduct product, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<DepositProduct>()
			{
				public DepositProduct run(Session session) throws Exception
				{
					session.saveOrUpdate(product);
					dictionaryRecordChangeInfoService.addChangesToLog(session, product, ChangeType.update);
					return product;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� ���������� �������
	 * @param product �������
	 * @param instanceName ������� ��
	 */
	public void remove(final DepositProduct product, String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(product);
					dictionaryRecordChangeInfoService.addChangesToLog(session, product, ChangeType.delete);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param productsForDelete ���� ����� - ���������
	 * @param instanceName ������� ��
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public void removeDepositProducts(Map<Long, List<Long>> productsForDelete, String instanceName) throws BusinessException, BusinessLogicException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(DepositProduct.class);
		criteria.add(Expression.in("productId", productsForDelete.keySet()));
		List<DepositProduct> depositProducts = multiInstanceSimpleService.find(criteria,instanceName);

		for (DepositProduct depositProduct : depositProducts)
		{
			List<Long> subKindIds = productsForDelete.get(depositProduct.getProductId());

			if(depositProduct == null)
				throw new BusinessLogicException("�� ������ ��� ������ � id = " + depositProduct.getId());

			if (CollectionUtils.isEmpty(subKindIds))  // ���� ������ ������ ��-������ ���� ������
			{
				remove(depositProduct, instanceName); //������� ��� �������, ������� �������
				continue;
			}
			try
			{
				// �������� DOM �������� ��������
				Document document = XmlHelper.parse(depositProduct.getDescription());

				//������� �� ��������� �������
				DepositProductHelper.removeSubKinds(document.getDocumentElement(), subKindIds);

				// �������� �������� ������ �� ������ ���������� ������
				DepositProductHelper.updateProductName(document.getDocumentElement());

				//���������� ���������� �������� �� �����
				depositProduct.setDescription(XmlHelper.convertDomToText(document, "windows-1251"));
				//���������� ����� ���������� ������� - �������
				depositProduct.setLastUpdateDate(Calendar.getInstance());
				//� ��������
				update(depositProduct, instanceName);
			}
			catch (BusinessException e)
			{
				throw e;
			}
			catch(BusinessLogicException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				throw new BusinessException(e);
			}
		}
	}

}
