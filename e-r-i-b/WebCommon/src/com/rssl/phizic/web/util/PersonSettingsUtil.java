package com.rssl.phizic.web.util;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.favouritelinks.FavouriteLink;
import com.rssl.phizic.business.menulinks.MenuLinkInfo;
import com.rssl.phizic.business.news.News;
import com.rssl.phizic.business.news.NewsService;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.employee.ManagerInfo;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.favouritelinks.ListFavouriteLinksOperation;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.operations.userprofile.ListMenuLinksOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.StrutsUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.PageContext;

/**
 * @author mihaylov
 * @ created 09.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class PersonSettingsUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final Map<FormType, String> formTypeToLink = new HashMap<FormType, String>();

	public static final String SHOW_GUEST_BANNER_ATTRIBUTE_NAME = "showGuestBanner";

	static
	{
		formTypeToLink.put(FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER,   "/private/payments/servicesPayments/edit");
		formTypeToLink.put(FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER,   "/private/payments/jurPayment/edit");
		formTypeToLink.put(FormType.JURIDICAL_TRANSFER,                 "/private/payments/jurPayment/edit");
		formTypeToLink.put(FormType.INDIVIDUAL_TRANSFER,                "/private/payments/payment");
		formTypeToLink.put(FormType.INDIVIDUAL_TRANSFER_NEW,            "/private/payments/payment");
		formTypeToLink.put(FormType.LOAN_PAYMENT,                       "/private/payments/payment");
		formTypeToLink.put(FormType.INTERNAL_TRANSFER,                  "/private/payments/payment");
		formTypeToLink.put(FormType.CONVERT_CURRENCY_TRANSFER,          "/private/payments/payment");
		formTypeToLink.put(FormType.IMA_PAYMENT,                        "/private/payments/payment");
		formTypeToLink.put(FormType.SECURITIES_TRANSFER_CLAIM,          "/private/payments/payment");
	}
	/**
	 * Возвращает лист с заданным кол-ом избранных ссылок клиента
	 * @return лист
	 */
	public static List<FavouriteLink> getUserLinks()
	{
		try
		{
			ListFavouriteLinksOperation operation = new ListFavouriteLinksOperation();
			return operation.getUsedFavouriteLinks();
		}
		catch (Exception e)
		{
			log.error("Ошибка определения списка избранных ссылок клиента", e);
			return null;
		}
	}

	/**
	 * Возвращает лист с информацией по шаблонам платежей клиента  ({id шаблона, имя шаблона, тип документа шаблона (для проеделения урла линка})
	 * @return лист
	 */
	public static List<TemplateDocument> getTemplates()
	{
		try
		{
			ListTemplatesOperation operation = new ListTemplatesOperation();
			operation.initializeForPersonalMenu();

			return operation.getEntity();
		}
		catch (Exception e)
		{
			log.error("Ошибка определения списка шаблонов платежей клиента", e);
			return null;
		}
	}

	/**
	 * Возвращает список параметров пунктов верхнего меню
	 * @return лист
	 */
	public static List<MenuLinkInfo> getMenuLink()
	{
		try
		{
			if (!PersonContext.isAvailable())
				return Collections.emptyList();
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			List<MenuLinkInfo> menuLinkList = personData.getMenuLinkInfoList();
			if(menuLinkList == null)
			{
				ListMenuLinksOperation menuLinksOperation = new ListMenuLinksOperation();
				menuLinksOperation.initialize(false);
				menuLinkList = menuLinksOperation.getMenuLinksInfo();
				personData.setMenuLinkInfoList(menuLinkList);
			}
			return menuLinkList;

		}
		catch (Exception e)
		{
			log.error("Ошибка получения настроек главного меню", e);
			return null;
		}
	}

	/**
	 * @return менеджер, привязанный к клиенту
	 */
	public static ManagerInfo getManager()
	{
		PersonDataProvider personDataProvider = PersonContext.getPersonDataProvider();
		if (personDataProvider == null)
			return null;
		PersonData data = personDataProvider.getPersonData();
		if (data == null)
			return null;

		return data.getManager();
	}

	/**
	 * Возвращает список новостей для отображения на главной странице
	 * @return  список новостей
	 */
	public static List<News> getClientNews() throws BusinessException
	{
		return getClientNews(ConfigFactory.getConfig(ClientConfig.class).getNewsCount());
	}

	/**
	 * Возвращает список новостей для отображения в виджете "События"
	 * @param news_count - количество новостей
	 * @return  список новостей
	 */
	public static List getClientNewsCount(String news_count)
	{
		return getClientNews(Integer.parseInt(news_count));
	}

	private static List<News> getClientNews(int news_count)
	{
		try
		{
			NewsService newsService = new NewsService();
			Region region = PersonContext.getPersonDataProvider().getPersonData().getCurrentRegion();
			String departmentTB = region == null ? null : region.getCodeTB();
			return newsService.getNewsListForMainPage(departmentTB, news_count);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения списка новостей", e);
			return Collections.emptyList();
		}
	}

	/**
	 * Получить ссылку на оплату по документу
	 * @param template шаблон
	 * @return ссылка.
	 */
	public static String getLinkByTemplate(TemplateDocument template)
	{
		if (FormType.JURIDICAL_TRANSFER.name().equals(template.getFormType().getName()))
		{
			return "/private/payments/jurPayment/edit.do";
		}

		if (FormType.isPaymentSystemPayment(template.getFormType()))
		{
			if (template.getReceiverInternalId() == null)
			{
				return "/private/payments/jurPayment/edit.do";
			}

			try
			{
				ServiceProviderService serviceProviderService = new ServiceProviderService();
				ServiceProviderState providerState = serviceProviderService.findStateById(template.getReceiverInternalId());
				if (providerState != null && (providerState == ServiceProviderState.ACTIVE || providerState == ServiceProviderState.MIGRATION))
				{
					//для биллингового платежа в пользу внутреннего поставщика используем первый шаг оплаты
					return "/private/payments/servicesPayments/edit.do";
				}
				return "/private/payments/jurPayment/edit.do";
			}
			catch (Exception e)
			{
				log.error("Ошибка определения ссылки для шаблона с id=" + template.getId(), e);
				return null;
			}
		}

		return "/private/payments/payment.do";
	}

	/**
	 * Получить ссылку на оплату по документу
	 * @param businessDocument документ
	 * @return ссылка.
	 */
	public static String getLinkPaymentByDocument(BusinessDocument businessDocument)
	{
		try
		{
			if (businessDocument instanceof JurPayment)
			{
				JurPayment jurPayment = (JurPayment) businessDocument;
				ServiceProviderService serviceProviderService = new ServiceProviderService();
				ServiceProviderState providerState = serviceProviderService.findStateById(jurPayment.getReceiverInternalId());
				if (providerState != null && (providerState == ServiceProviderState.ACTIVE || providerState == ServiceProviderState.MIGRATION))
				{
					//для биллингового платежа в пользу внутреннего поставщика используем первый шаг оплаты
					return "/private/payments/servicesPayments/edit.do";
				}
				//Для внешних поставщиков переходим на общую форму оплаты юрику.
				//Также переходим в случае если поставщика в базе больеш не существует
				return "/private/payments/jurPayment/edit.do";
			}

			// Для перевода юридическому лицу переходим на общую форму оплаты юрику
			if(FormConstants.JUR_PAYMENT_FORM.equals(businessDocument.getFormName()))
			{
				return "/private/payments/jurPayment/edit.do";
			}
			//для остальных на общую форму платежа.
			return "/private/payments/payment.do";
		}
		catch (Exception e)
		{
			log.error("Ошибка определения ссылки для документа  с id=" + businessDocument.getId(), e);
			return null;
		}
	}

	/**
	 * Получить тип операции для повтора. Используется в mAPI.
	 * @param businessDocument документ
	 * @return Тип операции для повтора
	 */
	public static String getPaymentTypeByDocument(BusinessDocument businessDocument)
	{
		try
		{
			if (businessDocument instanceof JurPayment)
			{
				JurPayment jurPayment = (JurPayment) businessDocument;
				ServiceProviderService serviceProviderService = new ServiceProviderService();
				ServiceProviderState providerState = serviceProviderService.findStateById(jurPayment.getReceiverInternalId());
				if (providerState != null && (providerState == ServiceProviderState.ACTIVE || providerState == ServiceProviderState.MIGRATION))
				{
					return "servicePayment";
				}
				return "jurPayment";
			}

			if(businessDocument != null && FormConstants.JUR_PAYMENT_FORM.equals(businessDocument.getFormName()))
			{
				return "jurPayment";
			}
			return "payment";
		}
		catch (Exception e)
		{
			log.error("Ошибка определения типа операции для документа  с id=" + businessDocument.getId(), e);
			return null;
		}
	}

	/**
	 * Получение ссылки на платеж по контексту страницы, типу документа и id шаблона
	 * @param pageContext контекст страницы
	 * @param template шаблон
	 * @return ссылка.
	 */
	public static String getTemplateLinkByTemplate(PageContext pageContext, TemplateDocument template)
	{
		String url = StrutsUtils.calculateActionURL(pageContext, formTypeToLink.get(template.getFormType()));
		return url + "?template=" + String.valueOf(template.getId());
	}

	/**
	 * Получение ссылки на платеж по контексту страницы, типу документа и id шаблона
	 * @param pageContext контекст страницы
	 * @param templateId идентификатор шаблона
	 * @param formType тип формы платежа
	 * @return ссылка.
	 */
	public static String getTemplateLinkByTemplate(PageContext pageContext, Long templateId, FormType formType)
	{
		String url = StrutsUtils.calculateActionURL(pageContext, formTypeToLink.get(formType));
		return url + "?template=" + String.valueOf(templateId);
	}

	/**
	 * Определяем показывать ли вкладку "Шаблоны и платежи" для указанного продукта
	 * @param productType  - тип продукта
	 * @return - true - показывать, false - не показывать
	 */
	public static boolean showTemplatesForProduct(String productType)
	{
		return ConfigFactory.getConfig(ClientConfig.class).isShowTemplateForProduct(productType);
	}

	/**
	 * Определяет кнопку "Операции"
	 * @return true - отображать, false - не отображать.
	 */
	public static boolean showOperationsButton()
	{
		return ConfigFactory.getConfig(ClientConfig.class).isShowOperationsButton();
	}

	/**
	 * @param type тип формы платежа
	 * @return Ссылка
	 */
	public static String getFormLinkByType(final FormType type)
	{
		return formTypeToLink.get(type);
	}

	/**
	 * @return - true, если нужно показывать промо по автоплатежам карта-карта
	 */
	public static boolean isNeedToShowP2PPromo()
	{
		UserPropertiesConfig config = ConfigFactory.getConfig(UserPropertiesConfig.class);
		return config.isNeedToShowP2PPromo() && PermissionUtil.impliesServiceRigid(FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM.getName());
	}

	/**
	 * @return - true, если нужно отображать пометку "Новинка" у автоплатежей в личном меню
	 */
	public static boolean isNeedToShowP2PNewMark()
	{
		return ConfigFactory.getConfig(UserPropertiesConfig.class).isNeedToShowP2PNewMark();
	}

	/**
	 * @return Url для метрики пиксель
	 */
	public static String getUrlMetricPixel()
	{
		try
		{
			return ConfigFactory.getConfig(ClientConfig.class).getUrlPixelMetric();
		}
		catch (Exception e)
		{
			log.error("Не удалось получить url для Pixel-метрики из конфига:" + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Нужно ли показывать неклиенту гостевой баннер
	 * @return
	 */
	public static boolean isNeedToShowGuestBanner()
	{
		Object value = WebContext.getCurrentRequest().getSession().getAttribute(SHOW_GUEST_BANNER_ATTRIBUTE_NAME);
		return (value != null
				&& value instanceof Boolean
				&& (Boolean)value);
	}

}
