package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankUtils;
import com.rssl.phizic.business.ext.sbrf.mobilebank.UncompatibleServiceProviderException;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.PaymentTemplateLink;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.mobilebank.GatePaymentTemplate;
import com.rssl.phizic.gate.mobilebank.GatePaymentTemplateService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

import static com.rssl.phizic.business.dictionaries.providers.ServiceProviderState.ACTIVE;

/**
 * @author Erkin
 * @ created 11.10.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от шаблонов МБК
 */

/**
 * Операция получения списка линков-на-шаблоны платежей Мобильного банка.
 * В мобильной версии эти шаблоны используются для оплаты услуг.
 */
@Deprecated
//todo CHG059734 удалить
public class ListMobilePaymentTemplatesOperation extends OperationBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String EXTERNAL_ID_SEPARATOR = "\\|";
	private static final String CREDIT_REPAYMENT = "CREDIT";

	private static final ServiceProviderService providerService
			= new ServiceProviderService();

	private static final ExternalResourceService resourceService
			= new ExternalResourceService();

	private CommonLogin login;

	private List<CardLink> cardLinks;

	private Map<String, PaymentTemplateLink> templateLinks;

	private Map<CardLink, List<PaymentTemplateLink>> cardPaymentTemplateLinks;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализация
	 */
	@SuppressWarnings({"ReuseOfLocalVariable"})
	public void initialize() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		login = personData.getPerson().getLogin();
		log.trace("Инициализация операции получения списка линков-на-шаблоны платежей Мобильного Банка." +
				"LOGIN_ID=" + login.getId());

		cardLinks = personData.getCards();
		if (log.isTraceEnabled())
			log.trace("Карты пользователя: " + LogUtils.formatCardLinkList(cardLinks));

		// 1. Загрузим линки пользователя из базы
		List<PaymentTemplateLink> templateLinkList = getPersonTemplateLinks();
		// Линки из базы понадобятся чуть позже: в getGateTemplateLinks()->...->getTemplateLink()
		templateLinks = mapTemplateLinks(templateLinkList);

		// 2. Соорудим линки пользователя по шаблонам из шлюза
		// (к старым шаблонам привяжем старые линки, а для новых шаблонов создадим новые линки)
		List<Card> cards = getCardsByLinks(cardLinks);
		templateLinkList = getGateTemplateLinks(cards);
		if (log.isTraceEnabled())
			log.debug("Сформированы следующие линки-на-шаблоны платежей МБ: " + LogUtils.formatPaymentTemplateLinkList(templateLinkList));

		// 3. Отфильтруем неподходящие линки
		templateLinkList = filterPaymentTemplateLinks(templateLinkList);

		templateLinks = mapTemplateLinks(templateLinkList);
		cardPaymentTemplateLinks = mapCardTemplateLinks(templateLinkList);
	}

	/**
	 * Возвращает линки шаблонов платежей,
	 * которые есть у текущего пользователя,
	 * разбитые по картам
	 * @return мап "линк-на-карту => список линков-на-шаблон-платежа"
	 */
	public Map<CardLink, List<PaymentTemplateLink>> getCardPaymentTemplateLinks()
	{
		if (cardPaymentTemplateLinks == null)
			throw new IllegalStateException("Operation is not ready");
		return Collections.unmodifiableMap(cardPaymentTemplateLinks);
	}

	///////////////////////////////////////////////////////////////////////////

	private List<PaymentTemplateLink> getPersonTemplateLinks()
			throws BusinessException, BusinessLogicException
	{
		List<PaymentTemplateLink> list
				= resourceService.getLinks(login, PaymentTemplateLink.class);
		if (CollectionUtils.isEmpty(list))
			return Collections.emptyList();
		return list;
	}

	private List<Card> getCardsByLinks(List<CardLink> cards) throws BusinessException
	{
		if (CollectionUtils.isEmpty(cards))
			return Collections.emptyList();

		String[] cardIds = new String[cards.size()];
		int i = 0;
		for (CardLink link : cards)
			cardIds[i++] = link.getExternalId();

		BankrollService bankrollService	= GateSingleton.getFactory().service(BankrollService.class);
		GroupResult<String, Card> result = bankrollService.getCard(cardIds);

		if (result.isOneError()!=null && result.isOneError())
			throw new BusinessException(result.getOneErrorException());

		for (Map.Entry<String, IKFLException> entry : result.getExceptions().entrySet()) {
			String cardExternalId = entry.getKey();
			IKFLException ex = entry.getValue();
			log.warn("Ошибка при получении карты. " +
					"Внешний ID карты: " + cardExternalId + ". " +
					"Сообщение: " + ex.getMessage(), ex);
		}

		List<Card> cardList = GroupResultHelper.getResults(result);
		if (cardList.size() != cards.size())
			log.warn("Из запрашиваемых " + cards.size() + " карт вернулась информация только по " + cardList.size() + " из них");

		if (cardList.isEmpty())
			return Collections.emptyList();
		else return cardList;
	}

	/**
	 * Возвращает шаблоны пользователя
	 * @param cards - список карт, по которым нужно получить список шаблонов
	 * @return список линков-на-шаблоны-платежей
	 */
	private List<PaymentTemplateLink> getGateTemplateLinks(List<Card> cards)
			throws BusinessException
	{
		log.trace("Формирование списка линков-на-шаблоны платежей МБ...");
		if (CollectionUtils.isEmpty(cards))
			return Collections.emptyList();

		// 1. Получим гейтовое представление шаблонов
		List<GatePaymentTemplate> templates = getGatePaymentTemplates(cards);
		if (log.isDebugEnabled())
			log.debug("Получены следующие шаблоны платежей из шлюза МБ: " + LogUtils.formatGatePaymentTemplateList(templates));
		if (CollectionUtils.isEmpty(templates))
			return Collections.emptyList();

		// 2. Соберём список кодов поставщиков в Мобильном Банке
		List<String> providerMBCodes = listMobilebankCodes(templates);

		// 3. По кодам из п.2 соберём мап поставщиков
		// Если в ИКФЛ не найден поставщик по коду, код будет проигнорирован
		Map<String, BillingServiceProvider> providers = getServiceProviders(providerMBCodes);
		if (log.isDebugEnabled())
			log.debug("Для полученных шаблонов платежей МБ в ИКФЛ найдены следующие поставщики: " + LogUtils.formatProviderList(providers.values()));

		// 4. По поставщикам получим список ключевых полей
		Map<BillingServiceProvider, List<FieldDescription>> keyFields
				= getProviderKeyFields(providers.values());

		// 5. Соберём линки
		return collectTemplateLinks(templates, providers, keyFields);
	}

	private List<GatePaymentTemplate> getGatePaymentTemplates(List<Card> cards)
			throws BusinessException
	{
		if (CollectionUtils.isEmpty(cards))
			return Collections.emptyList();

		GatePaymentTemplateService gateTemplateService = GateSingleton.getFactory().service(GatePaymentTemplateService.class);
		String[] cardNumbersArray = new String[cards.size()];
		int i = 0;
		for (Card card : cards)
			cardNumbersArray[i++] = card.getNumber();

		GroupResult<String, List<GatePaymentTemplate>> result
				= gateTemplateService.getPaymentTemplates(cardNumbersArray);

		if (result.isOneError())
			throw new BusinessException(result.getOneErrorException());

		for (Map.Entry<String, IKFLException> entry : result.getExceptions().entrySet()) {
			IKFLException ex = entry.getValue();
			log.warn("Ошибка при получении шаблонов платежей МБ. " +
					"Карта №: " + MaskUtil.getCutCardNumber(entry.getKey()) + ". " +
					"Сообщение: " + ex.getMessage(), ex);
		}

		return GroupResultHelper.joinValues(result);
	}

	/**
	 * Возвращает ключевые поля для указанных поставщиков
	 * @param providers - поставщики, для которых нужно загрузить ключевые поля
	 * @return мап "поставщик => список-ключевых-полей"
	 * @throws BusinessException
	 */
	private Map<BillingServiceProvider, List<FieldDescription>> getProviderKeyFields(
			Collection<BillingServiceProvider> providers
	) throws BusinessException
	{
		if (CollectionUtils.isEmpty(providers))
			return Collections.emptyMap();

		Map<BillingServiceProvider, List<FieldDescription>> keyFieldsMap
				= new LinkedHashMap<BillingServiceProvider, List<FieldDescription>>(providers.size());
		for (BillingServiceProvider provider : providers) {
			List<FieldDescription> fields = new LinkedList<FieldDescription>();
			for (FieldDescription field : provider.getFieldDescriptions()) {
				if (field.isKey())
					fields.add(field);
			}
			keyFieldsMap.put(provider, fields);
		}
		return keyFieldsMap;
	}

	private List<PaymentTemplateLink> collectTemplateLinks(
			List<GatePaymentTemplate> templates,
			Map<String, BillingServiceProvider> providers,
			Map<BillingServiceProvider, List<FieldDescription>> keyFields
	) throws BusinessException
	{
		if (templates.isEmpty() || providers.isEmpty())
			return Collections.emptyList();

		List<PaymentTemplateLink> links
				= new ArrayList<PaymentTemplateLink>(templates.size());
		for (GatePaymentTemplate template : templates) {
			String mbCode = template.getRecipientCode();
			BillingServiceProvider provider = providers.get(mbCode);

			if (provider == null) {
				log.warn("Шаблон платежей МБ " + LogUtils.formatGatePaymentTemplate(template) + " проигноирован, " +
						"т.к. не найден поставщик по МБ-коду " + mbCode);
				continue;
			}

			if (StringHelper.isEmpty(provider.getMobilebankCode())) {
				log.warn("Шаблон платежей МБ " + LogUtils.formatGatePaymentTemplate(template) + " проигноирован, " +
						"т.к. поставщик " + LogUtils.formatProvider(provider) + " не поддерживает работу с МБ " +
						"(не заполнено поле \"Код поставщика в Мобильном Банке\")");
				continue;
			}

			if (!provider.isAvailablePaymentsForMApi()) {
				log.warn("Шаблон платежей МБ " + LogUtils.formatGatePaymentTemplate(template) + " проигноирован, " +
						"т.к. поставщик " + LogUtils.formatProvider(provider) + " не доступен из мобильного приложения " +
						"(выключен флажок \"Доступ из мобильного приложения \")");
				continue;
			}

			if (provider.getState() != ACTIVE) {
				log.warn("Шаблон платежей МБ " + LogUtils.formatGatePaymentTemplate(template) + " проигноирован, " +
						"т.к. поставщик " + LogUtils.formatProvider(provider) + " не активен");
				continue;
			}

			PaymentTemplateLink link = getTemplateLink(template, provider, keyFields.get(provider));
			links.add(link);
		}

		if (links.isEmpty())
			links = Collections.emptyList();
		return links;
	}

	/**
	 * Возвращает поставщиков по их коду в шлюзе
	 * @param mbCodes - коды поставщиков в шлюзе
	 * @return мап "код_поставщика => поставщик"
	 */
	private Map<String, BillingServiceProvider> getServiceProviders(List<String> mbCodes)
			throws BusinessException
	{
		List<BillingServiceProvider> list
				= providerService.getAllByMobilebankCodes(mbCodes);

		Map<String, BillingServiceProvider> map
				= new LinkedHashMap<String, BillingServiceProvider>(list.size());
		for (BillingServiceProvider provider : list)
			map.put(provider.getMobilebankCode(), provider);
		return map;
	}

	private PaymentTemplateLink getTemplateLink(
			GatePaymentTemplate template,
			BillingServiceProvider provider,
			List<FieldDescription> fieldDescriptions
	) throws BusinessException
	{
		String externalId = template.getExternalId();
		PaymentTemplateLink link = templateLinks.get(externalId);
		if (link == null)
			link = resourceService.addPaymentTemplateLink(login, provider, template);
		link.setTemplate(template);

		// Делаем проверку на соответствие поставщика условиям мобильного банка
		try
		{
			MobileBankUtils.testServiceProviderMobilebankCompatible(provider);
		}
		catch (UncompatibleServiceProviderException ex)
		{
			log.warn("Поставщик " + LogUtils.formatProvider(provider) + " не подходит для оплаты по шаблону платежа МБ: " + ex.getMessage());
			link.setError(message("paymentsBundle", "message.payment.not.available"));
			return link;
		}

		FieldDescription payerField = fieldDescriptions.iterator().next();
		String payerFieldValue = template.getPayerCode();

		link.setPayerFieldName(payerField.getName());
		link.setPayerFieldValue(payerFieldValue);

		return link;
	}

	///////////////////////////////////////////////////////////////////////////

	private static List<String> listMobilebankCodes(List<GatePaymentTemplate> gateTemplates)
	{
		Set<String> codes = new LinkedHashSet<String>(gateTemplates.size());
		for (GatePaymentTemplate template : gateTemplates)
			codes.add(template.getRecipientCode());
		return new ArrayList<String>(codes);
	}

	private Map<String, PaymentTemplateLink> mapTemplateLinks(List<PaymentTemplateLink> list)
	{
		if (CollectionUtils.isEmpty(list))
			return Collections.emptyMap();

		Map<String, PaymentTemplateLink> map
				= new LinkedHashMap<String, PaymentTemplateLink>(list.size());
		for (PaymentTemplateLink link : list)
			map.put(link.getExternalId(), link);
		return map;
	}

	/**
	 * @param list - список линков-на-шаблоны
	 * @return мап "кард-линк -> линк-на-шаблон"
	 */
	private Map<CardLink, List<PaymentTemplateLink>>
	mapCardTemplateLinks(List<PaymentTemplateLink> list)
	{
		if (CollectionUtils.isEmpty(list))
			return Collections.emptyMap();

		Map<CardLink, List<PaymentTemplateLink>> map
				= new LinkedHashMap<CardLink, List<PaymentTemplateLink>>();
		for (PaymentTemplateLink link : list)
		{
			String cardNumber = link.getTemplate().getCardNumber();
			CardLink cardLink = cardToLink(cardNumber);
			if (cardLink == null) {
				log.error("Среди кард-линков пользователя " +
						"не найден кард-линк по карте " + MaskUtil.getCutCardNumber(cardNumber) + ". " +
						"LOGIN_ID=" + login.getId());
				continue;
			}

			List<PaymentTemplateLink> links = map.get(cardLink);
			if (links == null) {
				links = new LinkedList<PaymentTemplateLink>();
				map.put(cardLink, links);
			}
			links.add(link);
		}

		return map;
	}

	private CardLink cardToLink(String cardNumber)
	{
		if (cardNumber == null)
			return null;

		for (CardLink link : cardLinks) {
			if (link.getNumber().equals(cardNumber))
				return link;
		}

		return null;
	}

	// Исключаем из списка доступных шаблонов погашение кредита
	private List<PaymentTemplateLink> filterPaymentTemplateLinks(List<PaymentTemplateLink> list)
	{
		List<PaymentTemplateLink> filteredList = new ArrayList<PaymentTemplateLink>();
		for(PaymentTemplateLink link : list)
		{
			if (isServicePayment(link))
				filteredList.add(link);
			else log.warn("Линк-на-шаблон платежа МБ " + link.getExternalId() + " проигнорирован, т.к. " +
					"это погашение кредита");
		}
		return filteredList;
	}

	private boolean isServicePayment(PaymentTemplateLink link)
	{
		String[] externalId = link.getExternalId().split(EXTERNAL_ID_SEPARATOR);
		if (externalId.length != 4)
			return false;
		return !(externalId[2].equalsIgnoreCase(CREDIT_REPAYMENT));
	}
}
