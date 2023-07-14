package com.rssl.phizic.context;

import com.rssl.common.forms.DocumentException;
import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizgate.ext.sbrf.etsm.OfferPrior;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ClientInvoiceData;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.loanCardOffer.ConditionProductByOffer;
import com.rssl.phizic.business.menulinks.MenuLinkInfo;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.personalOffer.PersonalOfferNotification;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.TutorialProfileState;
import com.rssl.phizic.business.profile.images.AvatarInfo;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.guest.GuestType;
import com.rssl.phizic.business.resources.external.insurance.BusinessProcess;
import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.business.web.WebPage;
import com.rssl.phizic.business.web.Widget;
import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.common.types.basket.InvoicesSubscriptionsPromoMode;
import com.rssl.phizic.gate.employee.ManagerInfo;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 12:21:04 */

/**
 * Контекст пользователя (клиента)
 */
public interface PersonData extends Serializable
{
	/**
	 * @return дата создания контекста
	 */
	Calendar getCreationDate();

	/**
	 * @return пользователь
	 */
    ActivePerson getPerson();

	/**
	 * @return логин пользователя
	 */
	Login getLogin();

	/**
	 * @return подраздление пользователя
	 */
	Department getDepartment();

	/**
	 * @return ТБ пользователя
	 */
	Department getTb();
	/**
	 * @return профиль пользователя
	 */
	Profile getProfile() throws BusinessException;

	/**
	 * @return true, если пользователь заблокирован
	 */
	boolean isPersonBlocked();

	/**
	 * @return true, если пользователь мошенник
	 */
	boolean isFraud();

	/**
	 * Устанавливает признак необходимости обновить данные клиента во время вызова метода reset.
	 */
	void needPersonUpdate();

	/**
	 * Выполнить перезагрузку персон даты.
	 */
	void reset(Calendar nextUpdateTime);

	/**
	 * Установка признака блокировки пользователя.
	 * @param blocked - признак блокировки пользователя.
	 */
	public void setBlocked(boolean blocked);

	/**
	 * Установка признака подозрения пользователя в мошейничестве.
	 * @param fraud - признак мошейнической пользователя в мошейничестве.
	 */
	public void setFraud(boolean fraud);

	/**
	 * @return true, если у клиента есть ЕРМБ-профиль; false, если нет; null, если признак не установлен (наличие профиля неизвестно)
	 */
	Boolean isErmbSupported();

	/**
	 * Установка признака наличия ЕРМБ-профиля у клиента
	 * @param ermbSupported - признак наличия у клиента профиля ЕРМБ
	 */
	public void setErmbSupported(Boolean ermbSupported);

    List<AccountLink> getAccounts() throws BusinessException, BusinessLogicException;
	List<AccountLink> getAccounts(AccountFilter accountFilter) throws BusinessException, BusinessLogicException;
	List<AccountTarget> getAccountTargets() throws BusinessException, BusinessLogicException;
    List<CardLink> getCards() throws BusinessException, BusinessLogicException;
	List<CardLink> getCards(CardFilter cardFilter) throws BusinessException, BusinessLogicException;
	List<DepositLink> getDeposits() throws BusinessException, BusinessLogicException;
	List<LoanLink> getLoans() throws BusinessException, BusinessLogicException;
	List<LoanLink> getLoans(LoanFilter filter) throws BusinessException, BusinessLogicException;
	List<IMAccountLink> getIMAccountLinks() throws BusinessException, BusinessLogicException;
	List<DepoAccountLink> getDepoAccounts() throws BusinessException, BusinessLogicException;
	List<DepoAccountLink> getDepoAccounts(DepoAccountFilter filter) throws BusinessException, BusinessLogicException;
	List<AutoPaymentLink> getAutoPayments() throws BusinessException, BusinessLogicException;
	List<InsuranceLink> getInsuranceLinks(BusinessProcess businessProcess) throws BusinessException;
	List<InsuranceLink> getInsuranceLinks() throws BusinessException, BusinessLogicException;
	InsuranceLink getInsurance(Long linkId) throws BusinessException;

	AccountLink getAccount(Long accountLinkId) throws BusinessException, BusinessLogicException;
    CardLink getCard(Long cardLinkId) throws BusinessException;
	DepositLink getDeposit(Long depositLinkId) throws BusinessException;
	DepositLink getDepositByExternalId(String depositExternalId) throws BusinessException;
	LoanLink getLoan(Long loanLinkId) throws BusinessException, BusinessLogicException;
	DepoAccountLink getDepoAccount(Long depoAccountLinkId) throws BusinessException;
	String getSkinUrl();
	void setSkinUrl(String skinUrl);
	String getGlobalUrl() throws BusinessException;

	/**
	 * Получение текущего региона клиента.
	 * @return Регион. В случае если у клиента не задан регион, то вернётся null.
	 * @throws BusinessException
	 */
	Region getCurrentRegion() throws BusinessException;

	/**
	 * Установка текущего региона клиента.
	 * @param region Регион клиента
	 */
	void setCurrentRegion(Region region);

	IMAccountLink getImAccountLinkById(Long id) throws BusinessException;
	AutoPaymentLink getAutoPayment(Long autoPaymentLinkId) throws BusinessException;
	DepoAccountLink findDepo(String accountNumber) throws BusinessException;
	LoyaltyProgramLink getLoyaltyProgram() throws BusinessException;
	List<MenuLinkInfo> getMenuLinkInfoList();
	ExternalResourceLink getExternalResourceLink(Class clazz, Long id) throws BusinessException;

	public void setMenuLinkInfoList(List<MenuLinkInfo> linkInfoList);
	/**
	 * Установка признака необходимости повторного получения счетов из шины
	 * и очистка счетов
	 */
	public void setNeedReloadAccounts();

	/**
	 * Установка признака необходимости повторного получения металлических счетов
	 */
	public void setNeedReloadIMAccounts();

	/**
	 * Устанавливает признак необходимости повторного получения карт.
	 */
	void setNeedReloadCards();

	/**
     * Поиск счет по его номеру
     * @param accountNumber
     * @return
     * @throws BusinessException
     */
    AccountLink findAccount(String accountNumber) throws BusinessException, BusinessLogicException;

	/**
     * Поиск карты по её номеру
     * @param cardNumber номер карты
     * @return CardLink
     * @throws BusinessException
     */
	CardLink findCard(String cardNumber) throws BusinessException;

	/**
	 * Поиск кредита по номеру ссудного счета
	 * @param accountNumber - номер ссудного счета
	 * @return LoanLink
	 * @throws BusinessException
	 */
	LoanLink findLoan(String accountNumber) throws BusinessException;

	/**
	 * Поиск продукта по его алиасу (автоматическому или заданному пользователем)
	 * @param alias - алиас (автоматический или заданный пользователем)
	 * @return ErmbProductLink или null, если не найден
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	ErmbProductLink findProductByAlias(String alias) throws BusinessException, BusinessLogicException;

	/**
	 * Поиск ОМС по номеру
	 * @param imAccountNumber номер ОМС
	 * @return IMAccountLink
	 * @throws BusinessException
	 */
	IMAccountLink findIMAccount(String imAccountNumber) throws BusinessException;

	/**
	 * Получить ссылку на ресурс по уникальному коду.
	 * @param code код ссылки на внешний ресурс
	 * @return ссылка или null.
	 */
	ExternalResourceLink getByCode(String code) throws BusinessException, BusinessLogicException;

	/**
	 * Получить список ОМС с учетом фильтра
	 * @param filter фильтр
	 * @return список ссылок на ОМС
	 */
	List<IMAccountLink> getIMAccountLinks(IMAccountFilter filter) throws BusinessException, BusinessLogicException;

	/**
	 * Возвращает линк на шаблон SMS-платежа в Мобильном Банке
	 * @param linkId - ID шаблона SMS-платежа
	 * @return линк (never null)
	 * @deprecated избавление от шаблонов МБК
	 */
	@Deprecated
	//todo CHG059738 удалить
	PaymentTemplateLink getMobileBankTemplateLinkById(Long linkId) throws BusinessException, BusinessLogicException;

	/**
	 * Возвращает список линков подписок автоплатежей (автоплатежи по шинной технологии).
	 * @return список подписок.
	 */
	List<AutoSubscriptionLink> getAutoSubscriptionLinks() throws BusinessException, BusinessLogicException;

	/**
	 * Возвращает линк подписки автоплатежа (автоплатеж по шинной технологии).
	 * @param id идентификатор автоплатежа
	 * @return линк подписки автоплатежа.
	 */
	AutoSubscriptionLink getAutoSubscriptionLink(Long id) throws BusinessException, BusinessLogicException;

	/**
	 * Возвращает количество новых писем
	 * @return количество
	 * @throws BusinessException
	 */
	Long getCountNewLetters() throws BusinessException;

	/**
	 * Уменьшает счётчик количества непрочитанных писем.
	 */
	void decCountNewLetters();

	/**
	 * Возвращает список id баннеров, которые необходимо отображать клиенту на главной странице.
	 * @return список id баннеров.
	 */
	List<Long> getBannersList();

	/**
	 * Устанавливает список id баннеров, которые необходимо отображать клиенту на главной странице.
	 */
	void setBannersList(List<Long> bannersList);

	/**
	 * Возвращает список уведомлений клиента, которые уже были показаны
	 */
	List<Long> getShownNotificationList();
	/**
	 * Устанавливает список id уведомлений, которые уже были показаны
	 */
	void setShownNotificationList(List<Long> notificationList);

	/**
	 * Возвращает список  уведомлений о предодобренных предложениях
	 */
	List<PersonalOfferNotification> getOfferNotificationList();
	/**
	 * Устанавливает список уведомлений о предодобренных предложениях
	 */
	void setOfferNotificationList(List<PersonalOfferNotification> offerNotificationList);

	/**
	 * @return виджет-дефиниции, доступные пользователю в течении сессии
	 */
	Map<String, WidgetDefinition> getWidgetDefinitions();

	void setWidgetDefinitions(Map<String, WidgetDefinition> widgetDefinitions);

	WidgetDefinition getWidgetDefinition(String codename);

	/**
	 * Возвращает список текущих (ещё не сохранённых) веб-страниц
	 * @return список текущих веб-страниц (never null)
	 */
	Map<String,WebPage> getPages();

	/**
	 * Устанавливает список текущих (ещё не сохранённых) веб-страниц
	 * @param pages - список текущих веб-страниц (never null)
	 */
	void setPages(Map<String, WebPage> pages);

	/**
	 * @return список сохраненнных веб-страниц (never null)
	 */
	Map<String,WebPage> getSavedPages();

	/**
	 * @param pages  - сохраненные веб-страницы (never null)
	 */
	void setSavedPages(Map<String, WebPage> pages);

	/**
	 * Возвращает веб-страницу по её кодификатору
	 * @param codename - кодификатор (never null)
	 * @return веб-страница или null, если по указанному кодификатору не найдена страница
	 */
	WebPage getPage(String codename);

	/**
	 * Возвращает виджет по его кодификатору
	 * @param codename - кодификатор виджета (never null)
	 * @return виджет или null, если по указанному кодификатору не найден виджет
	 */
	Widget getWidget(String codename);


	/**
	 * Нужно ли запросить во внешней системе список кредитов клиента
	 * @return true - нужно получить кредиты клиента из внешней системы
	 */
	boolean isNeedLoadLoans();

	/**
	 * установить признак необходимости запроса списка кредитов из внешней системы перед получением линков
	 * @param needLoadLoans признак
	 */
	void setNeedLoadLoans(boolean needLoadLoans);

	/**
	 * Нужно ли запросить во внешней системе список счетов клиента
	 * @return true - нужно получить счета клиента из внешней системы
	 */
	boolean isNeedLoadAccounts();

	/**
	 * установить признак необходимости запроса списка счетов из внешней системы перед получением линков
	 * @param needLoadAccounts признак
	 */
	void setNeedLoadAccounts(boolean needLoadAccounts);

	/**
	 * Нужно ли запросить во внешней системе список ОМС клиента
	 * @return true - нужно получить ОМС клиента из внешней системы
	 */
	boolean isNeedLoadIMAccounts();

	/**
	 * установить признак необходимости запроса списка ОМС из внешней системы перед получением линков
	 * @param needLoadIMAccounts признак
	 */
	void setNeedLoadIMAccounts(boolean needLoadIMAccounts);

	/**
	 * @return идентификатор сессии в рамках которой произведена последняя аутентификация под данным клиентом
	 */
	String getLogonSessionId();

	/**
	 * Возвращает персонального менеджера, привязанного к данному клиенту.
	 * @return менеджер.
	 */
	ManagerInfo getManager();

	/**
	 * Устанавливает персонального менеджера, привязанного к данному клиенту.
	 */
	void setManager(ManagerInfo manager);

	/**
	 * Устанавливаем,  есть ли у клиента непогашенные кредиты с дифференцированным способом погашения
	 * @param hasOpenedDifLoan
	 */
	void setHasOpenedDifLoan(Boolean hasOpenedDifLoan);

	/**
	 * @return true - есть непогашенные кредиты с дифференцированным способом погашения
	 */
	boolean isHasOpenedDifLoan();

	/**
	 * @return true - пришло время проверить сессию ЦСА
	 */
	boolean isTimeToCheckCSASession();

	/**
	 * @return true - отображаем клиенту сообщение о том, что у него устаревший браузер
	 */
	boolean isShowOldBrowserMessage();

	/**
	 * Устанавливаем, отображать ли клиенту сообщение о том, что у него устаревший браузер
	 * @param showOldBrowserMessage
	 */
	void setShowOldBrowserMessage(boolean showOldBrowserMessage);

	/**
	 * Получение всех ссылок на счета, вне зависимости от видимости
	 * @return ссылки на счета
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	List<AccountLink> getAccountsAll() throws BusinessException, BusinessLogicException;

	/**
	 * Получение всех ссылок на карты, вне зависимости от видимости
	 * @return ссылки на карты
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	List<CardLink> getCardsAll() throws BusinessException, BusinessLogicException;

	/**
	 * Получение всех ссылок на кредиты, вне зависимости от видимости
	 * @return ссылки на кредиты
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	List<LoanLink> getLoansAll() throws BusinessException, BusinessLogicException;

	/**
	 * Получение всех ссылок на ОМС, вне зависимости от видимости
	 * @return ссылки на ОМС
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	List<IMAccountLink> getIMAccountLinksAll() throws BusinessException, BusinessLogicException;

	/**
	 * Получение всех ссылок на счета депо, вне зависимости от видимости
	 * @return ссылки на счета депо
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	List<DepoAccountLink> getDepoAccountsAll() throws BusinessException, BusinessLogicException;

	List<SecurityAccountLink> getSecurityAccountLinks() throws BusinessException, BusinessLogicException;

	List<SecurityAccountLink> getSecurityAccountLinksAll() throws BusinessException, BusinessLogicException;

	SecurityAccountLink getSecurityAccountLink(Long linkId) throws BusinessException;

	/**
	 * @return признак, указывающий на то, что полученные при входе карты перечитаны
	 */
	boolean isWayCardsReloaded();

	/**
	 * установить признак, указывающий на то, что полученные при входе карты перечитаны
	 * @param wayCardsReloaded boolean значение
	 */
	void setWayCardsReloaded(boolean wayCardsReloaded);

	/**
	 * @return признак, указывающий на необходимость показывать обучающую информацию в новом профиле
	 */
	boolean getShowTrainingInfo();

	/**
	 * установить признак, указывающий на необходимость показывать обучающую информацию в новом профиле
	 * @param showTrainingInfo - значение
	 */
	void setShowTrainingInfo(boolean showTrainingInfo);

	/**
	 * @return признак отображения значка "новых пунктов меню" для "Личной информации"
	 */
	boolean getNewPersonalInfo();

	/**
	 * установить признак отображения значка "новых пунктов меню" для "Личной информации"
	 * @param isNewPersonalInfo - значение
	 */
	void setNewPersonalInfo(boolean isNewPersonalInfo);

	/**
	 * @return признак отображения значка "новых пунктов меню" для "Автопоиска счетов к оплате"
	 */
	boolean getNewBillsToPay();

	/**
	 * установить признак отображения значка "новых пунктов меню" для "Автопоиска счетов к оплате"
	 * @param isNewBillsToPay - значение
	 */
	void setNewBillsToPay(boolean isNewBillsToPay);

	/**
	 * @param type тип аватара.
	 * @return информация об аватаре.
	 */
	AvatarInfo getAvatarInfoByType(AvatarType type) throws BusinessException;

	/**
	 * Установка информации об аватаре.
	 * @param info информация об аватаре.
	 */
	void setAvatarInfo(AvatarInfo info);

	/**
	 * Удалить информацию об аватарах.
	 * @param type тип.
	 */
	void deleteAvatarInfo(AvatarType type);

	/**
	 * Получаем список файлов, которые надо удалить.
	 * @return список файлов.
	 */
	List<File> getMarkedDeleteFiles();

	/**
	 * Помечаем файл для удаления.
	 */
	void markDeleteFile(File file);

	/**
	 * @return пользовательские настройки.
	 */
	Properties getUserProperties();

	/**
	 * @param properties пользовательские настройки.
	 */
	void setUserProperties(Properties properties);

	/**
	 * @return состояние отображения промо при входе в профиль
	 */
	TutorialProfileState getStateProfilePromo();

	/**
	 * установить состояние отображения промо при входе в профиль
	 * @param showPromo - значение
	 */
	void setStateProfilePromo(TutorialProfileState showPromo);

	/**
	 * @param type тип социальной сети.
	 * @return идентификатор пользователя для указанной социальной сети.
	 */
	String getUserIdForSocialNet(String type) throws BusinessException;

	InvoicesSubscriptionsPromoMode getPromoMode() throws BusinessException;

	void setPromoMode(InvoicesSubscriptionsPromoMode mode);

	/**
	 * @return множество арестованных карт клиента
	 */
	Set<String> getClientArrestedCards();

	/**
	 * Задаём множество аресованных карт
	 * @param clientArrestedCards - set из номеров карт
	 */
	void setClientArrestedCards(Set<String> clientArrestedCards);

	/**
	 * Получение данных о счетах корзины платежей клиента.
	 * @param errorCollector - коллектор
	 * @param withRefresh - true - актуализировать инвойсы если необходимо
	 * @return счетчик новых, дата послежднего обновления, список счетов
	 * @throws DocumentException
	 * @throws BusinessException
	 */
	ClientInvoiceData getClientInvoiceData(Set<String> errorCollector, boolean withRefresh) throws BusinessException;

	/**
	 * @param incognito признак инкогнито клиента.
	 */
	void setIncognito(boolean incognito);

	/**
	 * @return клиент инкогнто.
	 */
	boolean isIncognito();

	/**
	 * @return список телефонов клиента.
	 * @param alternative альтернативные телефоны.
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	Collection<String> getPhones(Boolean alternative) throws BusinessLogicException, BusinessException;

	/**
	 * Получение всех номеров клиента
	 * @param alternative альтернативные телефоны (только для МБК)
	 * @param checkPhoneInERMB нужно ли проверять ЕРМБ профиль
	 * @return список телефонов клиента.
	 */
	Collection<String> getPhones(Boolean alternative, Boolean checkPhoneInERMB) throws BusinessLogicException, BusinessException;

	/**
	 * @param flag признак отображения активных кредитов и кредитных карт в блочном режиме
	 */
	void setActiveCreditViewBlock(Boolean flag);

	/**
	 * @return отображать активные кредиты и кредитные карты в блочном режиме
	 */
	Boolean getActiveCreditViewBlock();

	/**
	 * Флаг обновления кредитного отчета. Ленивая инициализация флага.
	 * @return true - нужно обновить
	 */
	Boolean getMustUpdateCreditReport();

	/**
	 * Установить флаг обновления кредитного отчета.
	 * @param mustUpdateCreditReport флаг обновления кредитного отчета.
	 */
	void setMustUpdateCreditReport(Boolean mustUpdateCreditReport);

	/**
	 * @return true - если клиент уже подтверждал одноразовым паролем разрешение на перевод с Android устройств
	 */
	boolean isTrustedContactConfirmed();

	/**
	 * @param trustedContactConfirmed  - признак, что клиент уже подтверждал одноразовым паролем разрешение на перевод с Android устройств
	 */
	void setTrustedContactConfirmed(boolean trustedContactConfirmed);

	/**
	 * @param externalId - external Id карты
	 */
	boolean isCardDetailInfoUpdated(String externalId);

	/**
	 * @return время окончания блокировки ввода промокода
	 */
	Calendar getPromoBlockUntil() throws BusinessException;

	/**
	 * @param promoBlockUntil время окончания блокировки ввода промокода
	 */
	void setPromoBlockUntil(Calendar promoBlockUntil);

	/**
	 * получение списка всех предложений клиента по картам.
	 * @param number -  кол-во возвращаемых записей. Если null то все.
	 * @param isViewed - true(показанные)/false(непоказанные)/null(все подряд)
	 * @throws com.rssl.phizic.business.BusinessException
	 * @return список number предложений клиента по картам или всех предложений, если number = null.
	 */
	List<LoanCardOffer> getLoanCardOfferByPersonData(final Integer number, final Boolean isViewed) throws BusinessException;

	/**
	 * Получить предложения клиента по кредитам.
	 * @param number - колличества предложений
	 * @param isViewed - true(показанные)/false(непоказанные)/null(все подряд)
	 * @throws com.rssl.phizic.business.BusinessException
	 * @return список number предложений клиента по кредитам или всех предложений, если number = null.
	 */
	List<LoanOffer> getLoanOfferByPersonData(final Integer number, final Boolean isViewed) throws BusinessException;

	/**
	 * @param offerTypes Типы предложений по картам для которых будет делаться выборка
	 * @return Список содержащий информацию по связанным объектам: продукт, заявка и условие по кредитной карте.
	 */
	List<ConditionProductByOffer> getCardOfferCompositProductCondition(List<String> offerTypes) throws BusinessException;

	/**
	 * @param conditionId  id условия в разрезе валюты для Карточного кредитного продукта
	 * @return Класс-сущность который содержит продукт, завку и условие по предложению на кредитную карту
	 * @throws BusinessException
	 */
	ConditionProductByOffer findConditionProductByOffer(final Long conditionId) throws BusinessException;

	/**
	 * Проверяет существует ли еще предложение, по которому была оформлена заявка на кред. карту
	 * @param conditionId id условия по кредитной карте
	 * @return true - предложение еще существует, false - предложение уже удалено
	 * @throws BusinessException
	 */
	boolean existCreditCardOffer(final Long conditionId) throws BusinessException;

	/**
	 * Получени предложения по кредитной карте со связанным с ним условием и продуктом по id предложения, и id условия
	 * @param loanCardOfferId  - id предложения по кредитной карте
	 * @param conditionId      - id условия
	 * @return  объект состоящий из 3-х элементов: CreditCardCondition, LoanCardOffer, CreditCardProduct
	 *          null - если ничего не найдено
	 * @throws BusinessException
	 */
	ConditionProductByOffer findConditionProductByOfferIdAndConditionId(final OfferId loanCardOfferId,final Long conditionId) throws BusinessException;

	/**
	 * Получение условия по id предложения по карте
	 * @param loanCardOfferId  - id предложения по карте
	 * @return id условия
	 * @throws BusinessException
	 */
	Long findConditionIdByOfferId(final OfferId loanCardOfferId) throws BusinessException;

	/**
	 * Получить предложение по кредиту по offerId
	 * @param offerId - идентификатор предложения
	 * @return предложение по кредиту
	 * @throws BusinessException
	 */
	LoanOffer findLoanOfferById(OfferId offerId) throws BusinessException;

	/**
	 * Получить предложение по карте по offerId
	 * @param offerId - идентификатор предложения
	 * @return предложение по карте
	 * @throws BusinessException
	 */
	LoanCardOffer findLoanCardOfferById(OfferId offerId) throws BusinessException;

	/**
	 * Отправить отклик на предложения по кредитам
	 * @param loanOfferIds - список идентификаторов предложений по кредитам
	 * @param feedbackType - тип отклика
	 * @throws BusinessException
	 */
	void sendLoanOffersFeedback(List<OfferId> loanOfferIds, FeedbackType feedbackType) throws BusinessException;

	/**
	 * Отправить отклик на предложения по картам
	 * @param loanCardOfferIds - список идентификаторов предложений по картам
	 * @param feedbackType - тип отклика
	 * @throws BusinessException
	 */
	void sendLoanCardOffersFeedback(List<OfferId> loanCardOfferIds, FeedbackType feedbackType) throws BusinessException;

	/**
	 * Определение ожидания ответа (получения предложений) от CRM
	 * @return true - ожидается ответ
	 * @throws BusinessException
	 */
	boolean isOfferReceivingInProgress() throws BusinessException;

	/**
	 * Пометить предложение по кредиту как использованное
	 * @param offerId - идентификатор предложения
	 * @throws BusinessException
	 */
	void updateLoanOfferAsUsed(final OfferId offerId) throws BusinessException;

	/**
	 * Пометить предложение по карте как использованное
	 * @param offerId - идентификатор предложения
	 * @throws BusinessException
	 */
	void updateLoanCardOfferAsUsed(final OfferId offerId) throws BusinessException;

	/**
	 * Возвращает параметры кредита для отображения на главной странице
	 * @return параметры кредита
	 * @throws BusinessException
	 */
	LoanOffer getLoanOfferForMainPage() throws BusinessException;

	/**
	 * Возвращает параметры кредитной карты для отображения на главной странице
	 * @return параметры кредитной карты
	 * @throws BusinessException
	 */
	LoanCardOffer getLoanCardOfferForMainPage() throws BusinessException;

	/**
	 * Отправлен ли отклик на предложение на кредит
	 * @param offerId - идентификатор предложения
	 * @param feedbackType - тип отклика
	 * @return
	 */
	boolean isFeedbackForLoanOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException;

	/**
	 * Отправлен ли отклик на предложение на кредитную карту
	 * @param offerId - идентификатор предложения
	 * @param feedbackType - тип отклика
	 * @return
	 */
	boolean isFeedbackForLoanCardOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException;

	/**
	 * Получить предложение по карте с типом "новая карта"
	 * @return предложение по карте с типом "новая карта"
	 * @throws BusinessException
	 */
	LoanCardOffer getNewLoanCardOffer() throws BusinessException;

	/**
	 * Обновить дату регистрации заявки на кредитную карту
	 * @param offerId - идентификатор предложения на кредитную карту
	 * @throws BusinessException
	 */
	void updateLoanCardOfferRegistrationDate(OfferId offerId) throws BusinessException;

	/**
	 * Обновить дату перехода на страницу оформления заявки на кредитную карту
	 * @param offerId - идентификатор предложения на кредитную карту
	 * @throws BusinessException
	 */
	void updateLoanCardOfferTransitionDate(OfferId offerId) throws BusinessException;

	/**
	 * @return подключение к МБ
	 */
	Boolean isMB();

	/**
	 * @return id логина CSA
	 */
	Long getCSAProfileId();

	/**
	 * @return  id гостевого логина
	 */
	Long getGuestProfileId();

	/**
	 * Создать владельца документа
	 * @return владелец документ
	 */
	BusinessDocumentOwner createDocumentOwner();

	/**
	 * @return true - гостевой режим, false в противном случае
	 */
	boolean isGuest();

	/**
	 * @return строка логина гостя
	 */
	String getGuestLoginAlias();

	/**
	 * @return тип гостя
	 */
	GuestType getGuestType();

	/**
	 * доступно ли клиенту оформление заявки на кредитную карту
	 * @return
	 */
	boolean isLoanCardClaimAvailable();

	/**
	 * установить флажок доступности оформления заявки на кредитную карту
	 * @param loanCardClaimAvailable
	 */
	void setLoanCardClaimAvailable(boolean loanCardClaimAvailable);

	/**
	 * Получить оферты по кредитным заявкам
	 * @param incrementCounter - обновлять ли счетчик количества отображений оферт на главной странице
	 * @return
	 * @throws BusinessException
	 */
	OfferPrior getOfferPrior(boolean incrementCounter) throws BusinessException;

	/**
	 *
	 * @param  offerId идентификатор предложения
	 * @return предложение. Может возвращать null
	 * @throws BusinessException
	 */
	LoanOffer getLoanOffer(final OfferId offerId) throws BusinessException;
}
