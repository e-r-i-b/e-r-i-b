package com.rssl.phizic.operations.dictionaries.payment.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.payment.services.DublicatePaymentServiceException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityWithImageOperationBase;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author akrenev
 * @ created 01.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentServiceOperation extends EditDictionaryEntityWithImageOperationBase
{
	private PaymentService paymentService;
	private static final PaymentServiceService paymentServiceService = new PaymentServiceService();
	private static final String IMAGE_NAME = "/payment_service/tovary_uslugi.png";
	private  List<Long> сhildrenServiceIds;
	private Long categoryId;

	/**
	 * Инициализируем операцию новой услугой
	 * @param parentId id родительской услуги
	 * @exception BusinessException
	 */
	public void initializeNew(Long parentId) throws BusinessException
	{
		PaymentService parent = paymentServiceService.findById(parentId, getInstanceName());
		paymentService = new PaymentService();
		if (parent != null)
		{
			List<PaymentService> services = new ArrayList<PaymentService>();
			services.add(parent);
			paymentService.setParentServices(services);
			paymentService.setCategory(false);
		}
		else
			paymentService.setCategory(true);

		paymentService.setDefaultImage(IMAGE_NAME);
		setImage(null);
	}

	/**
	 * Инициализируем операцию
	 * @param id услуги
	 * @exception BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		paymentService = paymentServiceService.findById(id, getInstanceName());
		setImage(paymentService.getImageId());
		сhildrenServiceIds = paymentServiceService.getChildrenIds(id, getInstanceName());
	}

	public void doSave() throws BusinessException, DublicatePaymentServiceException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					paymentService.setImageId(saveImage());
					paymentServiceService.addOrUpdate(paymentService, getInstanceName());
					if (categoryId != null)
					{
						paymentServiceService.saveCardOperationCategoryToPaymentService(paymentService.getSynchKey().toString(), categoryId);
					}
					return null;
				}
			});
		}
		catch (DublicatePaymentServiceException e)
		{
			throw e;
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

	public PaymentService getEntity() throws BusinessException, BusinessLogicException
	{
		return paymentService;
	}

	/**
	 * Привязаны ли к услуге поставщики
	 * @param id - идентификатор услуги
	 * @return  true - привязаны; false - не привязаны
	 * @throws BusinessException
	 */
	public boolean hasProvider(Long id) throws BusinessException
	{
		return paymentServiceService.hasProvider(id, getInstanceName());
	}

	/**
	 * уровень иерархии услуги "сверху"
	 * @param id услуги
	 * @return  уровень иерархии
	 * @throws BusinessException
	 */
	public int getLevelOfHierarchy(Long id) throws BusinessException
	{
		return paymentServiceService.getLevelOfHierarchy(id, getInstanceName());
	}

	/**
	 * уровень иерархии услуги "снизу"
	 * @param id услуги
	 * @return  уровень иерархии
	 * @throws BusinessException
	 */
	public int getLevelOfHierarchyDown(Long id) throws BusinessException
	{
		return paymentServiceService.getLevelOfHierarchyDown(id, getInstanceName());
	}

	/**
	 * @return список id дочерних услуг
	 * @throws BusinessException
	 */
	public List<Long> getChildrenServiceIds() throws BusinessException
	{
		return сhildrenServiceIds;
	}

	public void setParentServices(Long[] serviceIds) throws BusinessException
	{
		if (paymentService.getCategory())
		{
			paymentService.setParentServices(null);
		}
		else
		{
			List<PaymentService> paymentServiceList = paymentServiceService.findByIds(Arrays.asList(serviceIds), getInstanceName());
			paymentService.setParentServices(paymentServiceList);
		}
	}

	/**
	 * @param categoryId идентификатор категории
	 * @throws BusinessException
	 */
	public void setCardOperationCategory(Long categoryId) throws BusinessException
	{
		this.categoryId = categoryId;
	}

	/**
	 * @return категория
	 */
	public CardOperationCategory getCardOperationCategory() throws BusinessException
	{
		if (paymentService.getSynchKey() == null)
			return null;

		return paymentServiceService.findCardOperationCategoryByServiceCode(paymentService.getSynchKey().toString());
	}
}
