package com.rssl.phizic.business.limits;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.auth.imsi.LoginIMSIError;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.*;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.strategies.DocumentLimitManager;
import com.rssl.phizic.business.documents.strategies.limits.GroupRiskDocumentLimitStrategy;
import com.rssl.phizic.business.documents.strategies.limits.IMSIDocumentLimitStrategy;
import com.rssl.phizic.business.documents.strategies.limits.MobileLightPlusLimitStrategy;
import com.rssl.phizic.business.documents.strategies.limits.ObstructionDocumentLimitStrategy;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.strategies.limits.BlockTemplateOperationLimitStrategy;
import com.rssl.phizic.business.limits.link.*;
import com.rssl.phizic.business.payments.forms.meta.ReceiverSocialCardFilter;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.EditAutoPayment;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author khudyakov
 * @ created 21.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class LimitHelper
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private static final LimitPaymentsLinkService limitPaymentsLinkService = new LimitPaymentsLinkService();
	private static final LimitService limitService = new LimitService();

	/**
	 * Проверка на необходимость дополнительного подтверждения операции
	 *
	 * @param document операция
	 * @return true - необходима
	 */
	public static boolean needAdditionalConfirm(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		BusinessDocumentOwner documentOwner = document.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
		ClientAccumulateLimitsInfo limitsInfo = DocumentLimitManager.buildLimitAmountInfoByLogin(documentOwner.getLogin(), LimitHelper.getChannelType(document));

		return !(new GroupRiskDocumentLimitStrategy(document).check(limitsInfo) && new MobileLightPlusLimitStrategy(document).check(limitsInfo));
	}

	/**
	 * Проверка на необходимость перечитывания sim-карты (проверка IMSI лимита)
	 * Для шаблонов всегда необходимо делать проверку на перечитывание sim-карты.
	 *
	 * @param document операция
	 * @return true - необходима
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static boolean needAdditionalCheck(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		BusinessDocumentOwner documentOwner = document.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
		ClientAccumulateLimitsInfo limitsInfo = DocumentLimitManager.buildLimitAmountInfoByLogin(documentOwner.getLogin(), LimitHelper.getChannelType(document));

		return !(new IMSIDocumentLimitStrategy(document).check(limitsInfo));
	}

	/**
	 * Проверка на необходимость запретить операцию
	 *
	 * @param document операция
	 * @return true - необходима
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static boolean needObstructOperation(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		ClientAccumulateLimitsInfo limitsInfo = DocumentLimitManager.buildLimitAmountInfoByPerson(PersonHelper.getContextPerson(), LimitHelper.getChannelType(document));
		return !(new ObstructionDocumentLimitStrategy(document).check(limitsInfo));
	}

	/**
	 * Проверка на необходимость запретить операцию
	 *
	 * @param template шаблон
	 * @return true - необходима
	 * @throws BusinessException
	 */
	public static boolean needObstructOperation(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		return !(new BlockTemplateOperationLimitStrategy(template).check(null));
	}

	/**
	 * @param tb ТБ, для которого устанавливается
	 * @return создать незаполненный список платежа и лимитов по группе риска
	 */
	public static List<LimitPaymentsLink> createPaymentLimitLinks(String tb)
	{
		List<LimitPaymentsLink> links = new ArrayList<LimitPaymentsLink>();

		links.add(new PhysicalExternalAccountLimitPaymentLink());
		links.add(new PhysicalExternalCardLimitPaymentLink());
		links.add(new PhysicalInternalLimitPaymentLink());
		links.add(new InternalSocialLimitPaymentLink());
		links.add(new ConversionLimitPaymentLink());
		links.add(new JuridicalExternalLimitPaymentLink());

		for (LimitPaymentsLink link : links)
		{
			((LimitPaymentsLinkBase)link).setTb(tb);
		}

		return links;
	}

	/**
	 * Вернуть список лимитов по группе риска для биллингового документ в разрезе канала исполнения
	 * @param document документ
	 * @param channelType тип канала
	 * @return список лимитов
	 */
	public static List<Limit> findBillingDocumentLimits(BusinessDocument document, ChannelType channelType) throws BusinessException
	{
		if (!(document instanceof RurPayment))
			throw new BusinessException("некорректный тип платежа, ожидался RurPayment");

		Long receiverInternalId = ((RurPayment) document).getReceiverInternalId();
		if (receiverInternalId != null)
		{
			return limitService.findActiveLimits(document, channelType, DocumentHelper.getGroupRisk(receiverInternalId));
		}

		//для бил. платежей по свободным реквизитам ищем по связи платежа и группы риска
		return findDocumentLimits(document, channelType);
	}

	/**
	 * Вернуть список лимитов по группе риска для биллингового документ в разрезе канала исполнения
	 * @param document документ
	 * @param channelType тип канала
	 * @return список лимитов
	 */
	public static List<Limit> findAutoPaymentLimits(BusinessDocument document, ChannelType channelType) throws BusinessException
	{
		if (!(document instanceof CreateAutoPayment || document instanceof EditAutoPayment))
			throw new BusinessException("некорректный тип платежа, ожидался CreateAutoPayment или EditAutoPayment");

		AutoPaymentBase autoPayment = (AutoPaymentBase) document;

		return limitService.findActiveLimits(document, channelType, DocumentHelper.getGroupRisk(autoPayment.getReceiverInternalId()));
	}

	/**
	 * Вернуть список лимитов по группе риска для документа по связи документа и группы риска в разрезе канала исполнения
	 * @param document документ
	 * @param channelType канал исполнения
	 * @return список лимитов
	 */
	public static List<Limit> findDocumentLimits(BusinessDocument document, ChannelType channelType) throws BusinessException
	{
		try
		{
			//лимиты ищем по связи платежа и группы риска
			List<LimitPaymentsLink> limitPaymentsLinks = limitPaymentsLinkService.findAll(((Department) document.getDepartment()).getRegion());
			if (CollectionUtils.isEmpty(limitPaymentsLinks))
				return Collections.emptyList();

			if (!(document instanceof ExchangeCurrencyTransferBase))
				return Collections.emptyList();

			List<Limit> limits = new ArrayList<Limit>();
			ExchangeCurrencyTransferBase convertionPayment = (ExchangeCurrencyTransferBase) document;

			for (LimitPaymentsLink limitPaymentsLink : limitPaymentsLinks)
			{
				//находим в списке типов платежей соответствие нашему платежу
				if (limitPaymentsLink.getPaymentTypes().indexOf(convertionPayment.getType()) >= 0)
				{
					//если операция без учета конверсии то мы не должны добавить лимиты по конверсионной группе риска
					if (LimitPaymentsType.CONVERSION_OPERATION_PAYMENT_LINK == limitPaymentsLink.getLinkType()
							&& !convertionPayment.isConvertion())
						continue;

					//если не перевод на соц. карту
					if (LimitPaymentsType.INTERNAL_SOCIAL_TRANSFER_LINK == limitPaymentsLink.getLinkType()
							&& !(new ReceiverSocialCardFilter().isEnabled(convertionPayment)))
						continue;

					limits.addAll(limitService.findActiveLimits(document, channelType, limitPaymentsLink.getGroupRisk()));
				}
			}

			return limits;
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Найти все активные лимиты по группе риска для документа в разрезе тербанка, в разрезе канала исполнения документа
	 * @param document документ
	 * @param channelType канал исполнения документа
	 * @return список активных лимитов по группе риска
	 */
	public static List<Limit> findAllDocumentLimits(BusinessDocument document, ChannelType channelType) throws BusinessException
	{
		if (!(document instanceof GateDocument))
			throw new BusinessException("Некорректный тип платежа, ожидался GateDocument.");

		GateDocument gateDocument = (GateDocument) document;
		//для автоплатежей лимит ищем по поставщику
		if (CreateAutoPayment.class == gateDocument.getType() || EditAutoPayment.class == gateDocument.getType())
		{
			return findAutoPaymentLimits(document, channelType);
		}

		//для бил. платежа лимиты ищем по группе риска поставщика
		if (AbstractPaymentSystemPayment.class.isAssignableFrom(gateDocument.getType()))
		{
			return findBillingDocumentLimits(document, channelType);
		}

		//для остальных платежей
		return findDocumentLimits(document, channelType);
	}

	/**
	 * @return текущий канал для лимитов
	 */
	public static ChannelType getCurrentChannelType()
	{
		ApplicationConfig config = ApplicationConfig.getIt();
		ApplicationInfo applicationInfo = config.getApplicationInfo();

		if (applicationInfo.isMobileApi())
		{
			return ChannelType.MOBILE_API;
		}

		if (applicationInfo.isATM())
		{
			return ChannelType.SELF_SERVICE_DEVICE;
		}

		if (applicationInfo.isWeb())
		{
			return ChannelType.INTERNET_CLIENT;
		}

		if (applicationInfo.isSMS())
		{
			return ChannelType.ERMB_SMS;
		}

        if (applicationInfo.isSocialApi())
        {
            return ChannelType.SOCIAL_API;
        }

		return null;
	}

	/**
	 * @param document документ
	 * @return тип канала, в котором выполняется операция
	 */
	public static ChannelType getChannelType(BusinessDocument document)
	{
		ChannelType channelType = getCurrentChannelType();
		if(channelType != null)
		{
			return channelType;
		}

		if (document.getClientOperationChannel() != null)
		{
			return getChannelType(document.getClientOperationChannel());
		}

		return getChannelType(document.getCreationType());
	}

	/**
	 * @param template документ
	 * @return тип канала, в котором выполняется операция
	 */
	public static ChannelType getChannelType(TemplateDocument template)
	{
		ChannelType channelType = getCurrentChannelType();
		if(channelType != null)
		{
			return channelType;
		}

		if (template.getClientOperationChannel() != null)
		{
			return getChannelType(template.getClientOperationChannel());
		}

		return getChannelType(template.getClientCreationChannel());
	}

	private static ChannelType getChannelType(CreationType type)
	{
		switch (type)
		{
			case atm:       return ChannelType.SELF_SERVICE_DEVICE;
			case mobile:    return ChannelType.MOBILE_API;
			case internet:  return ChannelType.INTERNET_CLIENT;
			case sms:       return ChannelType.ERMB_SMS;
			case social:    return ChannelType.SOCIAL_API;
			default: throw new IllegalArgumentException("Некорректный тип канала подтверждения операции.");
		}
	}

	/**
	 * Сумма по документу (в национальной валюте)
	 * @param document документ
	 * @return сумма
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static Money getOperationAmount(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		Money operationAmount = document.getExactAmount();
		if (operationAmount == null)
		{
			return new Money(BigDecimal.ZERO, MoneyUtil.getNationalCurrency());
		}
		return DocumentHelper.moneyConvert(operationAmount, MoneyUtil.getNationalCurrency(), document);
	}


	public static Money getOperationAmount(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		Money operationAmount = template.getExactAmount();

		if (operationAmount == null)
		{
			return new Money(BigDecimal.ZERO, MoneyUtil.getNationalCurrency());
		}

		return DocumentHelper.moneyConvert(operationAmount, MoneyUtil.getNationalCurrency(), template);
	}

	/**
	 * Получить значение номера карты (получателя платежа)
	 * @param document документ
	 * @return номер карты
	 */
	public static String getExternalCardProviderValue(BusinessDocument document) throws BusinessException
	{
		if (!(document instanceof AbstractPaymentDocument))
		{
			throw new BusinessException("Некорректный тип документа id = " + document.getId() + ", ожидался наследник AbstractPaymentDocument");
		}

		AbstractAccountsTransfer payment = (AbstractAccountsTransfer) document;
		return payment.getReceiverAccount();
	}

	/**
	 * Получить номер телефона (получателя платежа)
	 * @param document документ
	 * @return номер телефона
	 */
	public static String getExternalPhoneProviderValue(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		if (document instanceof JurPayment)
		{
			List<Field> fields = BillingPaymentHelper.getKeyFields((JurPayment) document);
			if (CollectionUtils.isEmpty(fields))
			{
				throw new BusinessException("У документа id " + document.getId() + " не могут отсутствовать ключевые доп. поля");
			}
			return (String) fields.get(0).getValue();
		}
		if (document instanceof AutoPaymentBase)
		{
			AutoPaymentBase autoPayment = (AutoPaymentBase) document;
			return autoPayment.getRequisite();
		}
		throw new BusinessException("У документа id " + document.getId() + " не могут отсутствовать ключевые доп. поля");
	}

	public static boolean needAdditionalConfirm(BusinessDocument document, RequireAdditionConfirmLimitException e)
	{
		//если не mAPI
		if (ApplicationUtil.isNotMobileApi())
			return true;

		//не оплата по шаблону
		if (RestrictionType.MAX_AMOUNT_BY_TEMPLATE != e.getRestrictionType())
			return true;

		//в mAPI для не-full-схемы всегда требуется доп. подтверждение
		if (!MobileApiUtil.isFullScheme())
			return true;

		if (!(document instanceof AbstractPaymentDocument))
			return true;

		try
		{
			//в mAPI full-версии при превышении кратности и включенной настройке "Проверка кратности суммы в PRO-зоне" требуется подтверждение в КЦ
			AbstractPaymentDocument apd = (AbstractPaymentDocument) document;
			return apd.getSumIncreasedOverLimit() && DocumentHelper.getUseTemplateFactorInFullMAPI(((Department) document.getDepartment()).getId());
		}
		catch (BusinessException ex)
		{
			//log.error("Ошибка при чтении настройки \"Проверка кратности суммы в PRO-зоне\"", ex);
			return true;
		}
	}

	/**
	 * Определение накопленной суммы по лимиту
	 * @param limitsInfo накопленные суммы по лимитам
	 * @param limit лимит
	 * @return сумма
	 */
	public static Money getClientTotalAmountByLimits(ClientAccumulateLimitsInfo limitsInfo, Limit limit)
	{
		try
		{
			return limitsInfo.getAccumulateAmount(limit);
		}
		catch (Exception e)
		{
			log.error("Ошибка определения накопленной суммы по лимиту " + limit.getId(), e);
			return null;
		}
	}

	/**
	 * Определение количества операций по лимиту
	 * @param limitsInfo счетчик операций по лимитам
	 * @param limit лимит
	 * @return количество операций
	 */
	public static Long getClientOperCountByLimits(ClientAccumulateLimitsInfo limitsInfo, Limit limit)
	{
		try
		{
			return limitsInfo.getAccumulateCount(limit);
		}
		catch (Exception e)
		{
			log.error("Ошибка определения накопленной суммы по лимиту " + limit.getId(), e);
			return null;
		}

	}

	/**
	 * Была ли смена сим карты с прошлого вызова.
	 *
	 * @param document документ
	 * @return true - смены sim-карты не было
	 * @throws BusinessException
	 */
	public static boolean isGoodIMSIChange(BusinessDocument document) throws BusinessException
	{
		long currentTimeInMs = Calendar.getInstance().getTimeInMillis();
		LoginIMSIError imsiError = limitService.getIMSICkeckResult(document);

		// если время проверки меньше суток, то возвращаем значение последней проверки
		if (imsiError != null && (currentTimeInMs - imsiError.getCheckDate().getTime() <= DateHelper.MILLISECONDS_IN_DAY))
		{
			return imsiError.isGoodIMSI();
		}
		// в остальных случаях требуется проверка на смену сим карты
		return false;
	}

}
