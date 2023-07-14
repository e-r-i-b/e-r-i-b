package com.rssl.phizic.business.basket;

import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.config.BasketConfig;
import com.rssl.phizic.business.basket.config.ServiceCategory;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.LightInvoiceSubscription;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.basket.InvoiceConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author osminin
 * @ created 15.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер для работы с корзиной платежей
 */
public class BasketHelper
{
	private static ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static ImageService imageService = new ImageService();

	private static final String DICTIONARY_NAME = "extendedFields.xml";
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final String[] daysOfWeekDesc = {"понедельникам", "вторникам", "средам", "четвергам", "пятницам", "субботам", "воскресеньям"};
	private static final String SERIES_AND_NUMBER_DOC_FIELD_POSTFIX = "@seriesAndNumber";
	private static final String ISSUE_BY_DOC_FIELD_POSTFIX          = "@issueBy";
	private static final String ISSUE_DATE_DOC_FIELD_POSTFIX        = "@issueDate";
	private static final String EXPIRE_DATE_DOC_FIELD_POSTFIX       = "@expireDate";

	/**
	 * Получить иконку поставщика услуг
	 * @param recipientId идентификатор поставщика услуг
	 * @return иконка поставщика услуг или null
	 */
	public static Image getProviderIcon(Long recipientId)
	{
		if (recipientId == null)
		{
			return null;
		}
		try
		{
			Long imageId = serviceProviderService.getImageIdById(recipientId);

			if (imageId == null)
			{
				return null;
			}

			return imageService.findById(imageId, null);
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении иконки поставщика улуг с id " + recipientId, e);
			return null;
		}
	}

	/**
	 * Доступна ли оплата задолженности по статусу услуги
	 * @param subscription услуга
	 * @return true - доступна
	 */
	public static boolean isInvoicePayAvailable(LightInvoiceSubscription subscription) throws BusinessException
	{
		if (subscription == null)
		{
			throw new IllegalArgumentException("Услуга не может быть null.");
		}
		return InvoiceSubscriptionState.WAIT != subscription.getState() || InvoiceSubscriptionState.DELETED != subscription.getNextState();
	}

	/**
	 * Получить категорию услуг по коду
	 * @param code код категории
	 * @return категория услуг
	 */
	public static ServiceCategory getServiceCategoryByCode(String code)
	{
		if (StringHelper.isEmpty(code))
		{
			return null;
		}

		try
		{
			BasketConfig basketConfig = ConfigFactory.getConfig(BasketConfig.class);
			return basketConfig.readCategoryByCode(code);
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении категории услуг по коду " + code, e);
			return null;
		}
	}

	/**
	 * Получить начало временного периода, для которого отображаются инвойсы
	 * @return дата
	 */
	public static Calendar getInvoiceLiveDate()
	{
		InvoiceConfig invoiceConfig = ConfigFactory.getConfig(InvoiceConfig.class);
		Calendar invoiceLiveDate = Calendar.getInstance();
		invoiceLiveDate.add(Calendar.DATE, -invoiceConfig.getDaysInvoiceLive());
		return invoiceLiveDate;
	}

	/**
	 * Построить справочник extendedFields
	 *
	 * @param requisites реквизиты
	 * @return справочник
	 * @throws BusinessException
	 */
	public static Map<String, Element> createDictionary(Document requisites) throws BusinessException
	{
		if (requisites == null)
		{
			return null;
		}
		return Collections.singletonMap(DICTIONARY_NAME, requisites.getDocumentElement());
	}

	/**
	 * @return паспорта профиля для корзины
	 */
	public static List<PersonDocument> getProfileDocuments()
	{
		if(!PersonContext.isAvailable())
			return Collections.emptyList();

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		return PersonHelper.getDocumentForProfile(personData.getPerson().getPersonDocuments());
	}

	/**
	 * @return иные докумены профиля для корзины
	 */
	public static List<UserDocument> getUserDocuments()
	{
		if(!PersonContext.isAvailable())
			return Collections.emptyList();

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

		try
		{
			return UserDocumentService.get().getUserDocumentByLogin(personData.getLogin().getId());
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	/**
	 * Построить информацию о выставлении счета для подписки
	 * @param subscription подписка
	 * @return информация о выставлении счета
	 */
	public static String createInvoiceInfo(InvoiceSubscription subscription)
	{
		if (subscription == null)
		{
			return null;
		}

		ExecutionEventType eventType = subscription.getExecutionEventType();
		Calendar payDate = subscription.getPayDate();

		if (ExecutionEventType.ONCE_IN_WEEK == eventType)
		{
			return getOnceInWeekDesc(payDate);
		}
		else if (ExecutionEventType.ONCE_IN_MONTH == eventType)
		{
			return getOnceInMonthDesc(payDate);
		}
		else if (ExecutionEventType.ONCE_IN_QUARTER == eventType)
		{
			return getOnceInQuarterDesc(payDate);
		}

		log.error("Тип события исполнения: " + eventType.name() + " не поддерживается при работе с услугами.");
		return null;
	}

	/**
	 * @return Режим взаимодейтствия с автопэй в разрезе корзины
	 */
	public static BasketPaymentsListenerConfig.WorkingMode getBasketInteractMode()
	{
		try
		{
			BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class, Application.BasketProxyListener);
			return config.getWorkingMode();
		}
		catch (Throwable e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	private static String getOnceInQuarterDesc(Calendar payDate)
	{
		StringBuilder builder = new StringBuilder("ежеквартально, ");

		builder.append(DateHelper.getDayOfDate(payDate));
		builder.append("-го числа, ");
		builder.append(DateHelper.getMonthOfQuarter(payDate));
		builder.append("-го месяца.");

		return builder.toString();
	}

	private static String getOnceInMonthDesc(Calendar payDate)
	{
		StringBuilder builder = new StringBuilder("ежемесячно, ");

		builder.append(DateHelper.getDayOfDate(payDate));
		builder.append("-го числа.");

		return builder.toString();
	}

	private static String getOnceInWeekDesc(Calendar payDate)
	{
		StringBuilder builder = new StringBuilder("еженедельно по ");

		int dayOfWeek = (DateHelper.getDayOfWeek(payDate)+5)%7;
		builder.append(daysOfWeekDesc[dayOfWeek]);
		builder.append(".");

		return builder.toString();
	}

	/**
	 * получить название дня недели в дательном падеже
	 * @param dayOfWeek - номер дня недели
	 * @return
	 */
	public static String getNameDayOfWeek(int dayOfWeek)
	{
		return daysOfWeekDesc[dayOfWeek - 1];
	}

	/**
	 * Список типов документов профиля, которые использовались при создании заявки
	 * @param payment заявка
	 * @return Список типов документов профиля
	 */
	public static Set<String> getUsedPersonDocuments(CreateInvoiceSubscriptionPayment payment)
	{
		List<Pair<String, String>> chosenDocumentsFields = payment.getChosenDocumentsFields();
		if(CollectionUtils.isEmpty(chosenDocumentsFields))
			return Collections.emptySet();

		Map<String, String> profileDocumentInfo = getProfileDocumentInfo();
		Set<String> types = new HashSet<String>();

		for(Pair<String, String> pair : chosenDocumentsFields)
		{
			String chosenValue = pair.getSecond();
			if(StringHelper.isEmpty(chosenValue))
				continue;

			String value = getChosenDocumentsFieldValue(payment, pair.getFirst());
			if(StringHelper.isEmpty(value))
				continue;

			if(value.equals(profileDocumentInfo.get(chosenValue)))
				types.add(chosenValue.substring(0, chosenValue.indexOf('@')));
		}

		return types;
	}

	private static String getChosenDocumentsFieldValue(CreateInvoiceSubscriptionPayment payment, String name)
	{
		ExtendedAttribute attribute = payment.getAttribute(name);
		if(attribute == null)
			return null;

		if(ExtendedAttribute.DATE_TYPE.equals(attribute.getType()))
			return String.format("%1$td.%1$tm.%1$tY", (Date) attribute.getValue());

		return attribute.getStringValue();
	}

	private static Map<String, String> getProfileDocumentInfo()
	{
		Map<String, String> res = new HashMap<String, String>();
		for(PersonDocument personDocument :  getProfileDocuments())
		{
			String type = personDocument.getDocumentType().name();
			res.put(type + SERIES_AND_NUMBER_DOC_FIELD_POSTFIX,
					StringHelper.getEmptyIfNull(personDocument.getDocumentSeries()) + StringHelper.getEmptyIfNull(personDocument.getDocumentNumber()));
			res.put(type + ISSUE_BY_DOC_FIELD_POSTFIX, personDocument.getDocumentIssueBy());
			res.put(type + ISSUE_DATE_DOC_FIELD_POSTFIX, DateHelper.formatDateToStringWithPoint(personDocument.getDocumentIssueDate()));
			res.put(type + EXPIRE_DATE_DOC_FIELD_POSTFIX, DateHelper.formatDateToStringWithPoint(personDocument.getDocumentTimeUpDate()));
		}

		for(UserDocument userDocument : getUserDocuments())
		{
			String type = userDocument.getDocumentType().name();
			res.put(type + SERIES_AND_NUMBER_DOC_FIELD_POSTFIX,
					StringHelper.getEmptyIfNull(userDocument.getSeries()) + StringHelper.getEmptyIfNull(userDocument.getNumber()));
			res.put(type + ISSUE_BY_DOC_FIELD_POSTFIX, userDocument.getIssueBy());
			res.put(type + ISSUE_DATE_DOC_FIELD_POSTFIX, DateHelper.formatDateToStringWithPoint(userDocument.getIssueDate()));
			res.put(type + EXPIRE_DATE_DOC_FIELD_POSTFIX, DateHelper.formatDateToStringWithPoint(userDocument.getExpireDate()));
		}

		return res;
	}

	/**
	 * Получение счетчика новых инвойсов клиента.
	 * @return значение счетчика.
	 */
	public static int getClientInvoicesNewCounter()
	{
		//если в правах нет возможности пользоваться корзиной-значит 0.
		if(!PermissionUtil.impliesOperation("ListInvoicesOperation", "PaymentBasketManagment"))
			return 0;

		//считаем интернет-заказы
		try
		{
			return PersonContext.getPersonDataProvider().getPersonData().getClientInvoiceData(null, true).getCounterNew();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return 0;
		}
	}

	/**
	 * Настраиваемый параметр отсчета интернет заказов
	 * @return - дата начала
	 */
	public static Calendar getEInvoiceFromDate()
	{
		InvoiceConfig config = ConfigFactory.getConfig(InvoiceConfig.class);
		Calendar date = Calendar.getInstance();
		date = DateHelper.getOnlyDate(date);
		date.add(Calendar.DATE, -config.getDaysInvoiceLive());
		return date;
	}
}
