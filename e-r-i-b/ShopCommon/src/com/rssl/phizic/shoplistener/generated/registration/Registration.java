package com.rssl.phizic.shoplistener.generated.registration;

import com.rssl.phizgate.common.providers.ProviderPropertiesEntry;
import com.rssl.phizgate.common.providers.ProviderPropertiesService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ShopListenerConfig;
import com.rssl.phizic.einvoicing.FacilitatorProviderService;
import com.rssl.phizic.einvoicing.ShopOrderImpl;
import com.rssl.phizic.einvoicing.ShopOrderServiceImpl;
import com.rssl.phizic.einvoicing.ShopProviderImpl;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.FacilitatorProvider;
import com.rssl.phizic.gate.einvoicing.InvoiceGateBackService;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.einvoicing.ShopProvider;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.shoplistener.generated.DocRegRqType;
import com.rssl.phizic.shoplistener.generated.DocRegRsType;
import com.rssl.phizic.shoplistener.generated.StatusType;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.util.ExceptionLogHelper;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Calendar;

/**
 * @author gulov
 * @ created 12.01.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Асбтрактный класс регистрации заказов (заполнение объекта заказ и сохранение его в базу)
 */
public abstract class Registration
{
	/**
	 * Текст ошибки во внешнюю систему
	 */
	protected static final String INTERNAL_ERROR = "Internal error";

	protected static final ShopOrderServiceImpl shopOrderService = new ShopOrderServiceImpl(null);
	protected static final FacilitatorProviderService facilitatorProviderService = new FacilitatorProviderService(null);
	protected static final ProviderPropertiesService providerPropertiesService = new ProviderPropertiesService();

	private static final String ORDER_ERROR = "Ordering is not available";
	private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]+$";
	private static final String ILLEGAL_NAME_ERROR = "Illegal service provider name.";
	private static final String EMPTY_URL_ERROR = "Endpoint provider URL should not be empty.";

	private ShopProvider activeProvider;
	private FacilitatorProvider facilitatorProvider;

	protected ShopOrder registerOrder(DocRegRqType request) throws GateException
	{
		ShopOrder order = fillOrder(request);

		// генерация идентификатора запроса
		RandomGUID randomGUID = new RandomGUID();
		((ShopOrderImpl)order).setUuid(randomGUID.getStringValue());

		return shopOrderService.addOrder(order);
	}

	/**
	 * @return поставщик, инициатор оформления заказа
	 */
	protected ShopProvider getActiveProvider()
	{
		return activeProvider;
	}

	/**
	 * @return КПУ
	 */
	protected FacilitatorProvider getFacilitatorProvider()
	{
		return facilitatorProvider;
	}

	protected abstract ShopOrder fillOrder(DocRegRqType request);

	/**
	* Проверяет поля запроса на валидность.
	* @param request описание заказа.
	*/
	protected void checkFields(DocRegRqType request) throws GateException
	{
		String name = request.getEShopIdBySP();
		boolean nameIsNotEmpty = StringHelper.isNotEmpty(name);
		if(nameIsNotEmpty && !name.matches(ALPHANUMERIC_REGEX))
		{
	        throw new IllegalArgumentException(ILLEGAL_NAME_ERROR);
		}
		if (nameIsNotEmpty && StringHelper.isEmpty(request.getEShopURL()))
		{
			throw new IllegalArgumentException(EMPTY_URL_ERROR);
		}
	}

	/**
	 * Находит КПУ или создаёт новый по необходимости и проверяет возможность регистрации заказа.
	 * @param request описание заказа
	 * @throws GateException
	 */
	protected void checkProvider(DocRegRqType request) throws GateException{
		String receiverCode = request.getSPName();
		activeProvider = GateSingleton.getFactory().service(InvoiceGateBackService.class).getActiveProvider(receiverCode);
		if (activeProvider == null)
			throw new ProviderException();

		//Реализация проверок согласно "Общая схема приема заказа с динамической загрузкой КПУ", РО 16 релиз
		if (!activeProvider.isFacilitator()) //Д.007
			return; //Д.008

		String eShopIdBySP = request.getEShopIdBySP();

		if (StringHelper.isEmpty(eShopIdBySP)) //Д.009
			return; //Д.010

		facilitatorProvider = facilitatorProviderService.findByCode(receiverCode, eShopIdBySP); //Д.011

		if (facilitatorProvider == null) //Д.011
		{
			try
			{
				ProviderPropertiesEntry providerPropertiesEntry = providerPropertiesService.findById(((ShopProviderImpl) activeProvider).getId());
				facilitatorProvider = new FacilitatorProvider();
				facilitatorProvider.setFacilitatorCode(receiverCode);
				facilitatorProvider.setCode(eShopIdBySP);
				facilitatorProvider.setName(request.getDocument().getRecipientName());
				facilitatorProvider.setInn(request.getDocument().getTaxId());
				facilitatorProvider.setUrl(request.getEShopURL());
				facilitatorProvider.setDeleted(false);
				boolean propsExist = providerPropertiesEntry != null;
				facilitatorProvider.setMbCheckEnabled(propsExist && providerPropertiesEntry.isMbCheckDefaultEnabled());
				facilitatorProvider.setEinvoiceEnabled(propsExist && providerPropertiesEntry.isEinvoiceDefaultEnabled());
				facilitatorProvider.setMobileCheckoutEnabled(propsExist && providerPropertiesEntry.isMcheckoutDefaultEnabled());
				facilitatorProviderService.add(facilitatorProvider);  //Д.012
				if (providerPropertiesEntry == null)
				{
					providerPropertiesEntry = new ProviderPropertiesEntry();
					providerPropertiesEntry.setProviderId(((ShopProviderImpl) activeProvider).getId());
				}
				providerPropertiesEntry.setUpdateDate(Calendar.getInstance());
				providerPropertiesService.addOrUpdate(providerPropertiesEntry);
			}
			catch (ConstraintViolationException ignore)
			{
				facilitatorProvider = facilitatorProviderService.findByCode(receiverCode, eShopIdBySP);
			}
		}

		if (BooleanUtils.isNotTrue(request.getMobileCheckout())) //Д.013
		{
			if (!facilitatorProvider.isEinvoiceEnabled()) //Д.014
				throw new RegistrationException(-2L, ORDER_ERROR);
		}
		else
		{
			if (!activeProvider.isAvailableMobileCheckout()) //Д.017
				return; //Д.018

			if (!(facilitatorProvider.isEinvoiceEnabled() && facilitatorProvider.isMobileCheckoutEnabled())) //Д.019
				throw new RegistrationException(Offline.MC_PAYMENT_INIT_ERROR_CODE, ORDER_ERROR);
		}
	}

	/**
	 * Регистрация заказа
	 * @param request - информация о документе и режим регистрации
	 * @return - идентификатор операции
	 */
	public DocRegRsType registrate(DocRegRqType request)
	{
		StatusType registrationStatus = new StatusType(0L, "");
		ShopOrder order = null;
		try
		{
			checkFields(request);
			checkProvider(request);
			order = registerOrder(request);
		}
		catch (RegistrationException re)
		{
			registrationStatus = new StatusType(re.getStateCode(), re.getStateDescription());
			ExceptionLogHelper.writeLogMessage(re);
		}
		catch (ExceededFacilitatorLimitException ex)
		{
			registrationStatus = new StatusType( -1L, INTERNAL_ERROR);
			ExceptionLogHelper.writeLogMessage(ex);
		}
		catch (Exception ex)
		{
			registrationStatus = new StatusType(BooleanUtils.isTrue(request.getMobileCheckout()) ? Offline.MC_PAYMENT_INIT_ERROR_CODE : -1L, INTERNAL_ERROR);
			ExceptionLogHelper.writeLogMessage(ex);
		}

		return createResponse(request, order, registrationStatus);
	}

	protected DocRegRsType createResponse(DocRegRqType request, ShopOrder order, StatusType status)
	{
		DocRegRsType result = new DocRegRsType();

		result.setERIBUID(order != null ? order.getUuid() : null);
		result.setRqTm(request.getRqTm());
		result.setRqUID(request.getRqUID());
		result.setStatus(status);
		if (StringHelper.isNotEmpty(request.getSystemId()))
			result.setSystemId(activeProvider == null ? null : activeProvider.getCodeRecipientSBOL());

		return result;
	}
}
