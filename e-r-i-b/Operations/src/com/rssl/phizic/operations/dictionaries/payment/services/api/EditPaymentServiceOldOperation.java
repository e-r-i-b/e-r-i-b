package com.rssl.phizic.operations.dictionaries.payment.services.api;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.payment.services.DublicatePaymentServiceException;
import com.rssl.phizic.business.dictionaries.payment.services.api.PaymentServiceOld;
import com.rssl.phizic.business.dictionaries.payment.services.api.PaymentServiceOldService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.SaveImageOperationBase;

/**
 * @author lukina
 * @ created 27.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class EditPaymentServiceOldOperation extends SaveImageOperationBase implements EditEntityOperation
{
	protected PaymentServiceOld paymentService;
	private static final PaymentServiceOldService paymentServiceService = new PaymentServiceOldService();
	private static String IMAGE_NAME = "/payment_service/tovary_uslugi.png";
	/**
	 * »нициализируем операцию новой услугой
	 * @param parentId id родительской услуги
	 * @exception com.rssl.phizic.business.BusinessException
	 */
	public void initializeNew(Long parentId) throws BusinessException
	{
		PaymentServiceOld parent = paymentServiceService.findById(parentId);
		paymentService = new PaymentServiceOld();
		paymentService.setParent(parent);
		paymentService.setDefaultImage(IMAGE_NAME);
		setImage(null);
	}

	/**
	 * »нициализируем операцию
	 * @param id услуги
	 * @exception BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		paymentService = paymentServiceService.findById(id);
		setImage(paymentService.getImageId());
	}

	@Transactional
	public void save() throws BusinessException, DublicatePaymentServiceException
	{
		paymentService.setImageId(saveImage());
		paymentServiceService.addOrUpdate(paymentService);
	}

	public PaymentServiceOld getEntity() throws BusinessException, BusinessLogicException
	{
		return paymentService;
	}

	public boolean hasProvider(Long id) throws BusinessException
	{
		return paymentServiceService.hasProvider(id);
	}

	public Integer getLevelOfHierarchy(Long id) throws BusinessException
	{
		return paymentServiceService.getLevelOfHierarchy(id);
	}
}
