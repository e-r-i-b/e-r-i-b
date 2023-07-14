package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.favouritelinks.FavouriteLink;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.business.menulinks.MenuLinkInfo;
import com.rssl.phizic.business.profile.ProfileUtils;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.business.util.BankInfoUtil;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.operations.userprofile.ListMenuLinksOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.util.WebAPIUtil;
import com.rssl.phizic.web.util.NodeUtil;
import com.rssl.phizic.web.util.PersonInfoUtil;
import com.rssl.phizic.web.util.PersonSettingsUtil;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.helpers.CommonElementsHelper;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.utils.LinkUtils;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MenuElement;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Property;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Type;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.MenuRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.MenuRequestBody;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.MenuResponse;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.Excluded;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.MBFunctionalTransferMessage;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.MenuContainer;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.footer.AboutBank;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.footer.Footer;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.footer.FooterHelp;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.footer.Network;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.header.*;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.help.Help;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.help.HelpText;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.help.HelpTitle;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.main.MainMenu;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.personal.AutoPayments;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.personal.DropDownContainer;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.personal.DropDownMenuItem;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.personal.PersonalMenu;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.settings.Settings;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Класс реализует заполнение объекта содержащего данные о содержании визуального интерфейса
 *
 * @author Balovtsev
 * @since 25.04.14
 */
public class MenuResponseConstructor extends JAXBResponseConstructor<MenuRequest, MenuResponse>
{
	private static final String FAQ_URL = LinkUtils.createRedirectUrl("/faq.do", null);
	private static final String MAIN_URL = LinkUtils.createRedirectUrl("/private/accounts.do", null);
	private static final String HELP_URL = LinkUtils.createRedirectUrl("/help.do?id=/private/accounts", null);
	private static final String THANKS_ASYNC_URL = LinkUtils.createRedirectUrl("/private/async/loyalty.do", null);
	private static final String LOGOFF_URL = LinkUtils.createRedirectUrl("/logoff.do", null);
	private static final String PAYMENTS_URL = LinkUtils.createRedirectUrl("/private/payments.do", null);
	private static final String SETTINGS_NEW_CLIENT_URL = LinkUtils.createRedirectUrl("/private/userprofile/accountSecurity.do", null);
	private static final String SETTINGS_URL = LinkUtils.createRedirectUrl("/private/userprofile/userSettings.do", null);
	private static final String MANAGE_TEMPLATES_URL = LinkUtils.createRedirectUrl("/private/favourite/list/PaymentsAndTemplates.do", null);
	private static final String MANAGE_FAVORITES_URL = LinkUtils.createRedirectUrl("/private/favourite/list/favouriteLinks.do", null);
	private static final String OPERATIONS_HISTORY_URL = LinkUtils.createRedirectUrl("/private/payments/common.do?status=all", null);
	private static final String MOBILE_WALLET_URL = LinkUtils.createRedirectUrl("/private/mobilewallet/edit.do", null);
	private static final String MOBILE_APPLICATIONS_URL = LinkUtils.createRedirectUrl("/private/mobileApplications/view.do", null);
	private static final String BUSINESSMAN_REGISTRATION_URL = BankInfoUtil.getBusinessmanRegistrationUrl();
	private static final String INTERNET_SHOP_ORDERS_URL = LinkUtils.createRedirectUrl("/private/payments/internetShops/orderList.do", null);
	private static final String THANKS_URL = LinkUtils.createRedirectUrl("/private/loyalty/detail.do", null);
	private static final String PROMO_CODE_URL = LinkUtils.createRedirectUrl("/private/userprofile/promoCodes.do", null);
	private static final String CALLING_FOR_SERVICE_URL = LinkUtils.createRedirectUrl("/private/mail/list.do", null);
	private static final String TARGETS_URL = LinkUtils.createRedirectUrl("/private/finances/targets/targetsList.do", null);
	private static final String CALENDAR_URL = LinkUtils.createRedirectUrl("/private/finances/financeCalendar.do", null);
	private static final String OPERATIONS_URL = LinkUtils.createRedirectUrl("/private/finances/operations.do", null);
	private static final String OPERATIONS_URL_POSTFIX = "operations";
	private static final String PFP_URL = LinkUtils.createRedirectUrl("/private/pfp/edit.do", null);
	private static final String MOBILE_BANK_URL = LinkUtils.createRedirectUrl("/private/mobilebank/main", null);

	private static final String ABOUT_BANK = "<p>© 1997 — 2014 ОАО «Сбербанк России»</p><p>Россия, Москва, 117997, ул. Вавилова, д. 19," +
											 "<br/>Генеральная лицензия на осуществление банковских операций от 8 августа 2012<br/>" +
											 "Регистрационный номер — 1481.<br/>Разработано компанией R-Style Softlab</p>";

	private static final List<Property> NOVELTY_PROPERTY = Collections.singletonList(new Property("novelty", Boolean.TRUE.toString()));
	private static final List<String>        BANK_PHONES     = new ArrayList<String>(2);
	private static final Map<String, String> SOCIAL_NETWORKS = new HashMap<String, String>(5);

	static
	{
		BANK_PHONES.add("4955005550");
		BANK_PHONES.add("8005555550");

		SOCIAL_NETWORKS.put("http://vk.com/bankdruzey", "/images/logotips/socialNetworks/vk.png");
		SOCIAL_NETWORKS.put("https://twitter.com/sberbank/", "/images/logotips/socialNetworks/tw.png");
		SOCIAL_NETWORKS.put("http://www.youtube.com/sberbank", "/images/logotips/socialNetworks/yt.png");
		SOCIAL_NETWORKS.put("https://www.facebook.com/bankdruzey", "/images/logotips/socialNetworks/fb.png");
		SOCIAL_NETWORKS.put("http://www.odnoklassniki.ru/bankdruzey", "/images/logotips/socialNetworks/ok.png");
	}


	@Override
	protected MenuResponse makeResponse(MenuRequest request) throws Exception
	{
		MenuRequestBody requestBody = request.getBody();
		MenuResponse    response    = new MenuResponse();

		if (requestBody == null)
		{
			return response;
		}
		else
		{
			MenuContainer container = new MenuContainer();
			container.setMbMessage(createMbFunctionalTransferMessage());

			List<Excluded> excluded = requestBody.getExcludedVisualElements();
			Collection included = null;
			if (excluded == null)
			{
				included = Arrays.asList(Excluded.values());
			}
			else
			{
				included = CollectionUtils.disjunction(excluded, Arrays.asList(Excluded.values()));
			}

			for (Object include : included)
			{
				switch ((Excluded) include)
				{
					case HEAD:
					{
						container.setHeader(createHeader());
						break;
					}

					case FOOTER:
					{
						container.setFooter(createFooter());
						break;
					}

					case SETTINGS:
					{
						container.setSettings(createSettings());
						break;
					}

					case MAIN_MENU:
					{
						container.setMainMenu(createMainMenu());
						break;
					}

					case PERSONAL_MENU:
					{
						container.setPersonalMenu(createPersonalMenu());
						break;
					}

					case HELP:
					{
						container.setHelp(createHelp());
						break;
					}
				}
			}

			if (container.isComplete())
			{
				response.setMenuContainer(container);
			}

			return response;
		}
	}

	private Settings createSettings()
	{
		return new Settings(new Link(PermissionUtil.impliesServiceRigid("NewClientProfile") ? SETTINGS_NEW_CLIENT_URL : SETTINGS_URL, "Настройки"));
	}

	private PersonalMenu createPersonalMenu() throws BusinessException
	{
		PersonalMenu personalMenu = new PersonalMenu();

		List<Link> staticList = new ArrayList<Link>();
		// Текстовки вытаскивать из пропертей
		if (PermissionUtil.impliesService("PaymentList"))
		{
			staticList.add(new Link(OPERATIONS_HISTORY_URL, "История операций в Сбербанк Онлайн"));
		}

		if (PermissionUtil.impliesService("MobileWallet"))
		{
			staticList.add(new Link(MOBILE_WALLET_URL, "Мобильный кошелек"));
		}

		if (PermissionUtil.impliesService("ShowConnectedMobileDevicesService") && !PermissionUtil.impliesServiceRigid("NewClientProfile"))
		{
			staticList.add(new Link(MOBILE_APPLICATIONS_URL, "Мобильные приложения"));
		}

		if (PermissionUtil.impliesService("BusinessmanRegistrationService"))
		{
			staticList.add(new Link(BUSINESSMAN_REGISTRATION_URL, "Стань предпринимателем"));
		}

		if (PermissionUtil.impliesOperation("InternetOrderListOperation", "InternetOrderPayments"))
		{
			staticList.add(new Link(INTERNET_SHOP_ORDERS_URL, "Мои интернет-заказы"));
		}

		if (PermissionUtil.impliesService("LoyaltyService"))
		{
			staticList.add(new Link(THANKS_ASYNC_URL, "Спасибо от Сбербанка"));
		}

		if (PermissionUtil.impliesService("LoyaltyProgramRegistrationClaim"))
		{
			staticList.add(new Link(THANKS_URL, "Спасибо от Сбербанка"));
		}

		if (!PermissionUtil.impliesServiceRigid("NewClientProfile"))
		{
			staticList.add(new Link(PROMO_CODE_URL, "Промо-коды"));
		}

		if (CollectionUtils.isNotEmpty(staticList))
		{
			personalMenu.setStaticList(staticList);
		}

		DropDownContainer container = new DropDownContainer();
		container.setMenuItems(createPersonalMenuItems());
		container.setAutopayments(makeAutopayments());

		if (container.isComplete())
		{
			personalMenu.setDropDownList(container);
		}

		return personalMenu;
	}

	private List<DropDownMenuItem> createPersonalMenuItems() throws BusinessException
	{
		List<DropDownMenuItem> result = new ArrayList<DropDownMenuItem>();
		result.add(makeFinance());
		result.add(makeFavorites());
		result.add(makeTemplates());
		return result;
	}

	private MainMenu createMainMenu() throws BusinessException
	{
		MainMenu mainMenu = new MainMenu();

		List<MenuElement> main = new ArrayList<MenuElement>();
		main.add(new MenuElement(new Link(MAIN_URL, "Главная"), Type.MAIN));
		main.add(new MenuElement(new Link(PAYMENTS_URL, "Переводы и платежи"), Type.PAYMENTS));

		ListMenuLinksOperation menuLinksOperation = new ListMenuLinksOperation();
		menuLinksOperation.initialize(false);
		Pair<List<MenuLinkInfo>, List<MenuLinkInfo>> menus = divideMenuLinks( menuLinksOperation.getMenuLinksInfo() );

		for (MenuLinkInfo link : menus.getFirst())
		{
			main.add(new MenuElement(new Link(LinkUtils.createRedirectUrl(link.getAction() + ".do", null), link.getText()), Type.getTypeByActivity(link.getActivity())));
		}

		if (CollectionUtils.isNotEmpty(menus.getSecond()))
		{
			List<MenuElement> other = new ArrayList<MenuElement>();

			for (MenuLinkInfo link : menus.getSecond())
			{
				other.add(new MenuElement(new Link(LinkUtils.createRedirectUrl(link.getAction() + ".do", null), link.getText()), Type.getTypeByActivity(link.getActivity())));
			}

			mainMenu.setOthers(other);
		}

		mainMenu.setSelectedType(Type.MAIN);
		mainMenu.setMain(main);
		return mainMenu;
	}

	private Header createHeader()
	{
		Header header = new Header();
		header.setPhones(BANK_PHONES);
		header.setHelpdesc(makeHelpDesc());
		header.setLogotype(new Logotype(MAIN_URL));
		header.setExit(new Exit(new Link(LOGOFF_URL, "Выход", "Безопасный выход из системы")));
		header.setPerson(CommonElementsHelper.createPersonTag());

		try
		{
			header.setAvatar(new Avatar(LinkUtils.createUserImageUri(AvatarType.ICON)));
		}
		catch (ConfigurationException e)
		{
			Utils.error("Ошибка построения пути к изображению", e);
		}

		return header;
	}

	private Footer createFooter() throws BusinessException
	{
		List<Network> networks = new ArrayList<Network>();

		for (String key : SOCIAL_NETWORKS.keySet())
		{
			networks.add(new Network(key, LinkUtils.createPictureUrl(SOCIAL_NETWORKS.get(key), null)));
		}

		Footer footer = new Footer();
		footer.setPhones(BANK_PHONES);
		footer.setNetwork(networks);
		footer.setAboutBank(new AboutBank(ABOUT_BANK));

		Helpdesc helpdesc = new Helpdesc();
		helpdesc.setLink(new Link(CALLING_FOR_SERVICE_URL, "Письмо в банк"));
		footer.setHelpdesc(helpdesc);

		FooterHelp help = new FooterHelp();
		help.setLink(new Link(HELP_URL, "Помощь онлайн"));
		footer.setHelp(help);

		return footer;
	}

	private Help createHelp()
	{
		return new Help(new HelpTitle(new Link(HELP_URL, "Помощь", "Помощь по работе в системе")),
						new HelpText (new Link(FAQ_URL,  "Часто задаваемые вопросы", "Часто задаваемые вопросы")));
	}

	private MBFunctionalTransferMessage createMbFunctionalTransferMessage()
	{
		boolean impliesMobileBank  = PermissionUtil.impliesService("MobileBank");
		boolean impliesNewProfile  = PermissionUtil.impliesServiceRigid("NewClientProfile");
		boolean impliesShowDevices = PermissionUtil.impliesService("ShowConnectedMobileDevicesService");

		if ((impliesMobileBank || impliesShowDevices) && impliesNewProfile)
		{
			if (!PersonInfoUtil.isMobileItemsMovedClosed())
			{
				StringBuilder builder = new StringBuilder();

				if (impliesMobileBank)
				{
					String href = NodeUtil.getCurrentNode().getHostname() + MOBILE_BANK_URL;
					builder.append(String.format("<a href='https://%s'>Мобильный банк</a> ", href));

					if (impliesShowDevices)
					{
						builder.append("и ");
					}
				}

				if (impliesShowDevices)
				{
					String href = NodeUtil.getCurrentNode().getHostname() + MOBILE_APPLICATIONS_URL;
					builder.append(String.format("<a href='https://%s'>Мобильные приложения</a> ", href));
				}

				if (impliesMobileBank && !impliesShowDevices)
				{
					builder.append("переехал на страницу вашего профиля");
				}
				else
				{
					builder.append("переехали на страницу вашего профиля");
				}

				return new MBFunctionalTransferMessage(builder.toString());
			}
		}

		return null;
	}

	private AutoPayments makeAutopayments()
	{
		if (PermissionUtil.impliesService("AutoPaymentsManagment"))
		{
			AutoPayments autoPayments = new AutoPayments();
			autoPayments.setTitle("Мои автоплатежи");
			autoPayments.setHidden(true);
			return autoPayments;
		}

		return null;
	}

	private DropDownMenuItem makeTemplates() throws BusinessException
	{
		if (PermissionUtil.impliesService("FavouriteManagment"))
		{
			DropDownMenuItem result = new DropDownMenuItem();

			MenuElement menuElement = new MenuElement();
			menuElement.setType(Type.TEMPLATES);
			Link templatesLink = new Link(null, "Мои шаблоны");
			menuElement.setLink(templatesLink);
			result.setMenu(menuElement);

			List<Link> commands = new ArrayList<Link>();
			commands.add(new Link(MANAGE_TEMPLATES_URL, "Управление шаблонами"));
			result.setCommands(commands);

			List<TemplateDocument> documents = PersonSettingsUtil.getTemplates();
			if (CollectionUtils.isNotEmpty(documents))
			{
				List<Link> links = new ArrayList<Link>();
				for (TemplateDocument document : documents)
				{
					if (document.getActivityInfo().isAvailablePay())
					{
						String link = LinkUtils.createRedirectUrl(PersonSettingsUtil.getFormLinkByType(document.getFormType()) + ".do?template=%d", null, document.getId());
						links.add(new Link(link, document.getTemplateInfo().getName()));
					}
				}

				if (CollectionUtils.isNotEmpty(links))
				{
					result.setLinks(links);
				}
			}
			else
			{
				List<Property> properties = Collections.singletonList(new Property("massage", "Для того чтобы быстро и легко совершать операции, добавьте сюда шаблоны платежей."));
				templatesLink.setProperties(properties);
			}
			return result;
		}

		return null;
	}

	private DropDownMenuItem makeFavorites()
	{
		if (PermissionUtil.impliesService("FavouriteManagment"))
		{
			DropDownMenuItem result = new DropDownMenuItem();

			MenuElement menuElement = new MenuElement();
			menuElement.setType(Type.FAVORITES);
			Link favoriteLink = new Link(null, "Избранное");
			menuElement.setLink(favoriteLink);
			result.setMenu(menuElement);

			List<Link> commands = new ArrayList<Link>();
			commands.add(new Link(MANAGE_FAVORITES_URL, "Управление избранным"));
			result.setCommands(commands);

			List<FavouriteLink> userLinks = PersonSettingsUtil.getUserLinks();

			if (CollectionUtils.isNotEmpty(userLinks))
			{
				List<Link> links = new ArrayList<Link>();
				for (FavouriteLink userLink : userLinks)
				{
					links.add(new Link(userLink.getLink(), userLink.getName()));
				}
				result.setLinks(links);
			}
			else
			{
				List<Property> properties = Collections.singletonList(new Property("massage", "Для того чтобы с любой страницы выполнять избранные операции, добавьте ссылки в Личное меню"));
				favoriteLink.setProperties(properties);
			}
			return result;
		}

		return null;
	}

	private DropDownMenuItem makeFinance() throws BusinessException
	{
		boolean viewCostsEnabled = PermissionUtil.impliesService("CategoriesCostsService");
		boolean viewFinanceEnabled = PermissionUtil.impliesService("ViewFinance");
		boolean viewTargetsEnabled = PermissionUtil.impliesService("TargetsService");
		boolean viewCalendarEnabled = PermissionUtil.impliesService("FinanceCalendarService");
		boolean applyFinanceEnabled = PermissionUtil.impliesService("AddFinanceOperationsService");
		boolean viewFinanceOperations = PermissionUtil.impliesService("FinanceOperationsService");
		boolean showPersonalFinanceEnabled = ProfileUtils.isPersonalFinanceEnabled();

		DropDownMenuItem result = new DropDownMenuItem();

		MenuElement menuElement = new MenuElement();
		menuElement.setType(Type.MY_FINANCE);
		Link myFinances = new Link(null, "Мои финансы");
		myFinances.setProperties(NOVELTY_PROPERTY);
		menuElement.setLink(myFinances);
		result.setMenu(menuElement);

		List<Link> links = new ArrayList<Link>();

		if (viewCostsEnabled && (applyFinanceEnabled || showPersonalFinanceEnabled))
		{
			Link link = new Link(null, "Расходы");
			link.setProperties(NOVELTY_PROPERTY);
			links.add(link);
		}

		if (viewFinanceEnabled)
		{
			links.add(new Link(null, "Доступные средства"));
		}

		if (viewTargetsEnabled)
		{
			Link link = new Link(TARGETS_URL, "Цели");
			link.setProperties(NOVELTY_PROPERTY);
			links.add(link);
		}

		if (viewCalendarEnabled)
		{
			Link link = new Link(CALENDAR_URL, "Календарь");
			link.setProperties(NOVELTY_PROPERTY);
			links.add(link);
		}

		if (viewFinanceOperations && (applyFinanceEnabled || showPersonalFinanceEnabled))
		{
			links.add(new Link(PermissionUtil.impliesService("UseWebAPIService") ? WebAPIUtil.getWebAPIUrl(OPERATIONS_URL_POSTFIX) : OPERATIONS_URL, "Операции"));
		}

		if (SecurityUtil.hasAccessToPFP())
			links.add(new Link(PFP_URL, "Финансовое планирование"));

		result.setLinks(links);

		return result;
	}

	private Helpdesc makeHelpDesc()
	{
		Link         helpDescLink = new Link(CALLING_FOR_SERVICE_URL, "Обращение в службу помощи", "Вопрос в Контактный центр банка");
		int          lettersCount = MailHelper.getCountNewLetters().intValue();
		Notification notification = null;

		if (lettersCount > 0)
		{
			notification = new Notification(lettersCount, true);
		}
		else
		{
			notification = new Notification(null, false);
		}

		return new Helpdesc(helpDescLink, notification);
	}

	private Pair<List<MenuLinkInfo>, List<MenuLinkInfo>> divideMenuLinks(final List<MenuLinkInfo> menuLinks)
	{
		Pair<List<MenuLinkInfo>, List<MenuLinkInfo>> pair = new Pair<List<MenuLinkInfo>, List<MenuLinkInfo>>(new ArrayList<MenuLinkInfo>(), new ArrayList<MenuLinkInfo>());

		/*
		 * Магические цифры как и волшебный алгоритм взяты из WEF-INF/jsp-sbrf/mainMenu.jsp
		 */
		if (CollectionUtils.isNotEmpty(menuLinks))
		{
			int maxLetters             = 26;
			int maxLettersWithoutOther = 50;

			int linksCount             = 0;
			int linksCountWithoutOther = 0;

			int lettersCount = 0;
			for (MenuLinkInfo link : menuLinks)
			{
				lettersCount += link.getText().toCharArray().length;

				if (lettersCount <= maxLetters)
				{
					linksCount ++;
				}

				if (lettersCount <= maxLettersWithoutOther - 5*linksCountWithoutOther)
				{
					linksCountWithoutOther = linksCountWithoutOther + 1;
				}
			}

			if (linksCountWithoutOther == menuLinks.size())
			{
				linksCount = linksCountWithoutOther;
			}

			for (int i=0; i<linksCount; i++)
			{
				pair.getFirst().add(menuLinks.get(i));
			}

			if (menuLinks.size() > linksCount)
			{
				for (int i=linksCount; i<menuLinks.size(); i++)
				{
					pair.getSecond().add(menuLinks.get(i));
				}
			}
		}

		return pair;
	}
}
