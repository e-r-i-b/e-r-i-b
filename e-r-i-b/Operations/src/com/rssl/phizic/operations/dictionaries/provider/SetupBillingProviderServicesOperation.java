package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceService;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lukina
 * @ created 27.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования принадлежности поставщика услугам
 */

public class SetupBillingProviderServicesOperation extends EditServiceProvidersOperation
{
	private static final BillingProviderServiceService billingProviderServiceService = new BillingProviderServiceService();
	private static final PaymentServiceService paymentServiceService = new PaymentServiceService();

	private Set<BillingProviderService> providerServices;
	private Set<BillingProviderService> deletedPaymentServices;

	private BillingProviderService newBillingProviderService;

	public void initialize(Long id) throws BusinessException
	{
		provider = providerService.findById(id, getInstanceName());
		if (provider == null)
			throw new BusinessException("Поставщик услуг с id = " + id + " не найден");

		if (!getRestriction().accept(provider))
			throw new RestrictionViolationException("Поставщик ID= " + provider.getId());

		if (!(provider instanceof BillingServiceProvider))
			throw new BusinessException("Некорректный тип поставщика услуг.");

		Set<BillingProviderService> prServices = new HashSet<BillingProviderService>();
		prServices.addAll(billingProviderServiceService.findByProviderId(provider.getId(), getInstanceName()));
		providerServices = prServices;
	}

	/**
	 * @return полчить список услуг, привязанных к поставщику
	 * @throws BusinessException
	 */
	public Set<BillingProviderService> getProviderServices() throws BusinessException
	{
		return Collections.unmodifiableSet(providerServices);
	}

	/**
	 * Установить принадлежность к группам услуг ПУ
	 * @param serviceId идентификаторы добавляемой услуги
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void setAddingPaymentService(Long serviceId) throws BusinessException, BusinessLogicException
	{
		if (paymentServiceService.hasChild(serviceId, getInstanceName()))
			throw new BusinessLogicException("Нельзя добавить услугу, к которой привязана подуслуга.");

		PaymentService service = paymentServiceService.findById(serviceId, getInstanceName());
		newBillingProviderService = new BillingProviderService((BillingServiceProvider)provider, service);
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		billingProviderServiceService.addOrUpdate(newBillingProviderService, getInstanceName());
		sendUpdateDictionaryEvent(BillingProviderService.class);
	}

	/**
	 * задать услуги для удаления
	 * @param ids идентификаторы услуг
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void setRemovingPaymentServices(List<Long> ids) throws BusinessException, BusinessLogicException
	{
		Set<BillingProviderService> delPaymentServices = new HashSet<BillingProviderService>();
		delPaymentServices.addAll(billingProviderServiceService.findByServiceIds(ids, provider.getId(), getInstanceName()));
		deletedPaymentServices = delPaymentServices;

		if (deletedPaymentServices != null &&	providerServices.size() - deletedPaymentServices.size() < 1)
			throw new BusinessLogicException("Удаление невозможно. Поставщик услуг должен принадлежать хотя бы к одной группе услуг. Если Вы желаете удалить эту услугу, то сначала добавьте другую, а затем повторите попытку удаления.");
	}

	/**
	 * Удалить услуги из поставщика
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void remove() throws BusinessException, BusinessLogicException
	{
		billingProviderServiceService.remove(deletedPaymentServices, getInstanceName());
		sendUpdateDictionaryEvent(BillingProviderService.class);
	}
}
