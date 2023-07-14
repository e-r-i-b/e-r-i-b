package com.rssl.phizic.business.longoffer.autopayment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceService;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.AutoSubscriptionPaymentBase;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.services.IDHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author osminin
 * @ created 08.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentHelper
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final PaymentServiceService paymentServiceService = new PaymentServiceService();

	/**
	 * получить линк по идендификатору
	 * @param links список линков
	 * @param externalId идендификатор
	 * @return линк автоплатежа
	 */
	public static AutoPaymentLink findAutoPaymentLinkByEternalId(List<AutoPaymentLink> links, String externalId)
	{
		for (AutoPaymentLink link: links)
			if (link.getExternalId().equals(externalId))
				return link;
		return null;
	}

	/**
	 * Получить код группы услуг поставщика услуг
	 * @param providerId идентификатор поставщика услуг
	 * @return код группы услуг поставщика услуг
	 */
	public static String getGroupServiceByProvider(Long providerId) throws BusinessException
	{
		if (providerId == null)
		{
			//Если получателя нет, значит по свободным реквизитам, передаем значение по умолчанию
			return AutoSubscriptionPaymentBase.DEFAULT_GROUP_SERVICE;
		}

		List<PaymentService> paymentServices = paymentServiceService.getServicesForProvider(providerId);
		if (CollectionUtils.isEmpty(paymentServices))
		{
			//Если услуги не найдены, нечего передавать
			return null;
		}
		//возвращаем код первой попавшейся услуги
		return (String) paymentServices.get(0).getSynchKey();
	}

	/**
	 * Определяет является ли поставщик iqwave
	 * @param provider билинговый поставщик
	 * @return true - iqwave
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static boolean isIQWProvider(ServiceProviderBase provider) throws BusinessException
	{
		if (provider == null)
		{
			throw new IllegalArgumentException("Поставщик услуг не может быть null");
		}

		return isIQWProvider((String) provider.getSynchKey());
	}

	/**
	 * Определяет является ли поставщик iqwave
	 * @param synchKey внешний идентификатор поставщика
	 * @return является ли поставщик iqwave
	 * @throws BusinessException
	 */
	public static boolean isIQWProvider(String synchKey) throws BusinessException
	{
		if (StringHelper.isEmpty(synchKey))
		{
			throw new IllegalArgumentException("Внешний идентификатор поставщика услуг не может быть null");
		}

		AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
		Adapter adapter = config.getCardTransfersAdapter();
		if (adapter == null)
			throw new BusinessException("Не задана внешняя система для карточных переводов");

		return adapter.getUUID().equals(IDHelper.restoreRouteInfo(synchKey));
	}

	/**
	 * Допустимо ли создание автоплатежа по документу
	 * На текущий момент происходит проверка только для оплаты услуг. Для остальных типов платежей возвращается false
	 * @param document документ
	 * @return да/нет
	 */
	public static boolean isAutoPaymentAllowed(BusinessDocument document)
	{
		if (document == null)
		{
			return false;
		}

		if (!(document instanceof JurPayment))
		{
			//создание автоплатежей возможно для оплаы услуг платежей, а не заявок.
			return false;
		}

		try
		{
			JurPayment jurPayment = (JurPayment) document;
			if (jurPayment.asGateDocument().getType() != CardPaymentSystemPayment.class)
			{
				return false;
			}
			return isAutoPaymentAllowed(ServiceProviderHelper.getServiceProvider(jurPayment.getReceiverInternalId()));
		}
		catch (Exception e)
		{
			log.error("Ошибка при определении доступности создания автоплатежа", e);
			return false;
		}
	}

	/**
	 * Допустимо ли создание автоплатежа P2P по документу
	 * @param document документ
	 * @return да/нет
	 */
	public static boolean isAutoTransferAllowed(BusinessDocument document)
	{
		if (document == null)
		{
			return false;
		}

		if (document instanceof RurPayment)
		{
			return document.getType() == CardPaymentSystemPayment.class && "ourCard".equals(((RurPayment)document).getReceiverSubType());
		}

		if (document instanceof InternalTransfer)
		{
			return ((InternalTransfer)document).getChargeOffResourceType() == ResourceType.CARD
                    && ((InternalTransfer)document).getDestinationResourceType() == ResourceType.CARD;
		}

        return false;
	}

	public static boolean isAutoPaymentAllowed(ServiceProviderShort receiver) throws BusinessException
	{
		if (receiver != null )
		{
			if (!(receiver.getKind().equals("B")) || !(checkAutoPaymentSupport(receiver)) || receiver.getState() != ServiceProviderState.ACTIVE)
			{
				//небиллинговые получатели и получатели с явно запрещенными автоплатежами не поддерживают создания автоплатежа
				return false;
			}
			if (isIQWProvider(receiver.getSynchKey()))
			{
				//для iqw
				return PermissionUtil.impliesOperation("CreateFormPaymentOperation", "CreateAutoPaymentPayment");
			}
			//Биллинговый платеж через шину
			return PermissionUtil.impliesOperation("CreateESBAutoPayOperation", "ClientCreateAutoPayment");
		}
		else
		{
			//для автоплатежа по свободным реквизитам - свои права.
			return PermissionUtil.impliesOperation("CreateFreeDetailAutoSubOperation", "ClientFreeDetailAutoSubManagement");
		}
	}

	/**
	 * Допустимо ли создание автоплатежа по шаблону
	 * На текущий момент происходит проверка только для оплаты услуг. Для остальных типов платежей возвращается false
	 * @param templateId идентификатор шаблона
	 * @return да/нет
	 */
	public static boolean isAutoPaymentAllowedForTemplate(Long templateId)
	{
		try
		{
			if (templateId == null)
			{
				return false;
			}

			TemplateDocument template = TemplateDocumentService.getInstance().findById(templateId);
			return isAutoPaymentAllowedForTemplate(template);
		}
		catch (Exception e)
		{
			log.error("Ошибка при определении доступности создания автоплатежа", e);
			return false;
		}
	}

	/**
	 * Допустимо ли создание автоплатежа по шаблону
	 * На текущий момент происходит проверка только для оплаты услуг. Для остальных типов платежей возвращается false
	 * @param template шаблон
	 * @return да/нет
	 */
	public static boolean isAutoPaymentAllowedForTemplate(TemplateDocument template)
	{
		try
		{
			if (template == null)
			{
				return false;
			}
			if (FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER != template.getFormType())
			{
				return false;
			}
			Long receiverId = template.getReceiverInternalId();
			ServiceProviderShort provider = ServiceProviderHelper.getServiceProvider(receiverId);

			if (receiverId != null && provider == null)
			{
				//поставщик отсутствует в БД
				return false;
			}
			return isAutoPaymentAllowed(provider);
		}
		catch (Exception e)
		{
			log.error("Ошибка при определении доступности создания автоплатежа", e);
			return false;
		}
	}

	/**
	 * Проверка поддержки автоплатежа в зависимости от приложения
	 * @param billingProvider поставщик услуг
	 * @return признак поддержки автоплатежа
	 */
	public static boolean checkAutoPaymentSupport(final BillingServiceProvider billingProvider)
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		if (applicationInfo.isATM())
			return billingProvider.isAutoPaymentSupportedInATM();
		else if (applicationInfo.isMobileApi())
			return billingProvider.isAutoPaymentSupportedInApi();
		else
			return billingProvider.isAutoPaymentSupported();
	}


	/**
	 * Проверка поддержки автоплатежа в зависимости от приложения
	 * @param provider поставщик услуг
	 * @return признак поддержки автоплатежа
	 */
	public static boolean checkAutoPaymentSupport(final ServiceProviderShort provider)
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		if (applicationInfo.isATM())
			return provider.isAutoPaymentSupportedInATM();
		else if (applicationInfo.isMobileApi())
			return provider.isAutoPaymentSupportedInApi();
		else
			return provider.isAutoPaymentSupported();
	}

	/**
	 * @param autoPayment автоплатеж
	 * @return поддерживается ли отмена автоплатежа в данном статусе
	 */
	public static boolean isRefuseSupported(AutoPayment autoPayment)
	{
		if (autoPayment == null)
			return false;

		AutoPaymentStatus status = autoPayment.getReportStatus();
		return status == AutoPaymentStatus.NEW || status == AutoPaymentStatus.ACTIVE ||
				status == AutoPaymentStatus.UPDATING || status == AutoPaymentStatus.BLOCKED;
	}
}
