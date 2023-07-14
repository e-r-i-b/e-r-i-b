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
	 * ���������� ���� � �������� ���-�� ��������� ������ �������
	 * @return ����
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
			log.error("������ ����������� ������ ��������� ������ �������", e);
			return null;
		}
	}

	/**
	 * ���������� ���� � ����������� �� �������� �������� �������  ({id �������, ��� �������, ��� ��������� ������� (��� ����������� ���� �����})
	 * @return ����
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
			log.error("������ ����������� ������ �������� �������� �������", e);
			return null;
		}
	}

	/**
	 * ���������� ������ ���������� ������� �������� ����
	 * @return ����
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
			log.error("������ ��������� �������� �������� ����", e);
			return null;
		}
	}

	/**
	 * @return ��������, ����������� � �������
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
	 * ���������� ������ �������� ��� ����������� �� ������� ��������
	 * @return  ������ ��������
	 */
	public static List<News> getClientNews() throws BusinessException
	{
		return getClientNews(ConfigFactory.getConfig(ClientConfig.class).getNewsCount());
	}

	/**
	 * ���������� ������ �������� ��� ����������� � ������� "�������"
	 * @param news_count - ���������� ��������
	 * @return  ������ ��������
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
			log.error("������ ��������� ������ ��������", e);
			return Collections.emptyList();
		}
	}

	/**
	 * �������� ������ �� ������ �� ���������
	 * @param template ������
	 * @return ������.
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
					//��� ������������ ������� � ������ ����������� ���������� ���������� ������ ��� ������
					return "/private/payments/servicesPayments/edit.do";
				}
				return "/private/payments/jurPayment/edit.do";
			}
			catch (Exception e)
			{
				log.error("������ ����������� ������ ��� ������� � id=" + template.getId(), e);
				return null;
			}
		}

		return "/private/payments/payment.do";
	}

	/**
	 * �������� ������ �� ������ �� ���������
	 * @param businessDocument ��������
	 * @return ������.
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
					//��� ������������ ������� � ������ ����������� ���������� ���������� ������ ��� ������
					return "/private/payments/servicesPayments/edit.do";
				}
				//��� ������� ����������� ��������� �� ����� ����� ������ �����.
				//����� ��������� � ������ ���� ���������� � ���� ������ �� ����������
				return "/private/payments/jurPayment/edit.do";
			}

			// ��� �������� ������������ ���� ��������� �� ����� ����� ������ �����
			if(FormConstants.JUR_PAYMENT_FORM.equals(businessDocument.getFormName()))
			{
				return "/private/payments/jurPayment/edit.do";
			}
			//��� ��������� �� ����� ����� �������.
			return "/private/payments/payment.do";
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������ ��� ���������  � id=" + businessDocument.getId(), e);
			return null;
		}
	}

	/**
	 * �������� ��� �������� ��� �������. ������������ � mAPI.
	 * @param businessDocument ��������
	 * @return ��� �������� ��� �������
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
			log.error("������ ����������� ���� �������� ��� ���������  � id=" + businessDocument.getId(), e);
			return null;
		}
	}

	/**
	 * ��������� ������ �� ������ �� ��������� ��������, ���� ��������� � id �������
	 * @param pageContext �������� ��������
	 * @param template ������
	 * @return ������.
	 */
	public static String getTemplateLinkByTemplate(PageContext pageContext, TemplateDocument template)
	{
		String url = StrutsUtils.calculateActionURL(pageContext, formTypeToLink.get(template.getFormType()));
		return url + "?template=" + String.valueOf(template.getId());
	}

	/**
	 * ��������� ������ �� ������ �� ��������� ��������, ���� ��������� � id �������
	 * @param pageContext �������� ��������
	 * @param templateId ������������� �������
	 * @param formType ��� ����� �������
	 * @return ������.
	 */
	public static String getTemplateLinkByTemplate(PageContext pageContext, Long templateId, FormType formType)
	{
		String url = StrutsUtils.calculateActionURL(pageContext, formTypeToLink.get(formType));
		return url + "?template=" + String.valueOf(templateId);
	}

	/**
	 * ���������� ���������� �� ������� "������� � �������" ��� ���������� ��������
	 * @param productType  - ��� ��������
	 * @return - true - ����������, false - �� ����������
	 */
	public static boolean showTemplatesForProduct(String productType)
	{
		return ConfigFactory.getConfig(ClientConfig.class).isShowTemplateForProduct(productType);
	}

	/**
	 * ���������� ������ "��������"
	 * @return true - ����������, false - �� ����������.
	 */
	public static boolean showOperationsButton()
	{
		return ConfigFactory.getConfig(ClientConfig.class).isShowOperationsButton();
	}

	/**
	 * @param type ��� ����� �������
	 * @return ������
	 */
	public static String getFormLinkByType(final FormType type)
	{
		return formTypeToLink.get(type);
	}

	/**
	 * @return - true, ���� ����� ���������� ����� �� ������������ �����-�����
	 */
	public static boolean isNeedToShowP2PPromo()
	{
		UserPropertiesConfig config = ConfigFactory.getConfig(UserPropertiesConfig.class);
		return config.isNeedToShowP2PPromo() && PermissionUtil.impliesServiceRigid(FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM.getName());
	}

	/**
	 * @return - true, ���� ����� ���������� ������� "�������" � ������������ � ������ ����
	 */
	public static boolean isNeedToShowP2PNewMark()
	{
		return ConfigFactory.getConfig(UserPropertiesConfig.class).isNeedToShowP2PNewMark();
	}

	/**
	 * @return Url ��� ������� �������
	 */
	public static String getUrlMetricPixel()
	{
		try
		{
			return ConfigFactory.getConfig(ClientConfig.class).getUrlPixelMetric();
		}
		catch (Exception e)
		{
			log.error("�� ������� �������� url ��� Pixel-������� �� �������:" + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * ����� �� ���������� ��������� �������� ������
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
