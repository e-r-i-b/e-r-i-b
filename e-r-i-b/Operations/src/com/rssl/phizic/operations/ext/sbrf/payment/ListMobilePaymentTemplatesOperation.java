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
 * @deprecated ���������� �� �������� ���
 */

/**
 * �������� ��������� ������ ������-��-������� �������� ���������� �����.
 * � ��������� ������ ��� ������� ������������ ��� ������ �����.
 */
@Deprecated
//todo CHG059734 �������
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
	 * �������������
	 */
	@SuppressWarnings({"ReuseOfLocalVariable"})
	public void initialize() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		login = personData.getPerson().getLogin();
		log.trace("������������� �������� ��������� ������ ������-��-������� �������� ���������� �����." +
				"LOGIN_ID=" + login.getId());

		cardLinks = personData.getCards();
		if (log.isTraceEnabled())
			log.trace("����� ������������: " + LogUtils.formatCardLinkList(cardLinks));

		// 1. �������� ����� ������������ �� ����
		List<PaymentTemplateLink> templateLinkList = getPersonTemplateLinks();
		// ����� �� ���� ����������� ���� �����: � getGateTemplateLinks()->...->getTemplateLink()
		templateLinks = mapTemplateLinks(templateLinkList);

		// 2. �������� ����� ������������ �� �������� �� �����
		// (� ������ �������� �������� ������ �����, � ��� ����� �������� �������� ����� �����)
		List<Card> cards = getCardsByLinks(cardLinks);
		templateLinkList = getGateTemplateLinks(cards);
		if (log.isTraceEnabled())
			log.debug("������������ ��������� �����-��-������� �������� ��: " + LogUtils.formatPaymentTemplateLinkList(templateLinkList));

		// 3. ����������� ������������ �����
		templateLinkList = filterPaymentTemplateLinks(templateLinkList);

		templateLinks = mapTemplateLinks(templateLinkList);
		cardPaymentTemplateLinks = mapCardTemplateLinks(templateLinkList);
	}

	/**
	 * ���������� ����� �������� ��������,
	 * ������� ���� � �������� ������������,
	 * �������� �� ������
	 * @return ��� "����-��-����� => ������ ������-��-������-�������"
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
			log.warn("������ ��� ��������� �����. " +
					"������� ID �����: " + cardExternalId + ". " +
					"���������: " + ex.getMessage(), ex);
		}

		List<Card> cardList = GroupResultHelper.getResults(result);
		if (cardList.size() != cards.size())
			log.warn("�� ������������� " + cards.size() + " ���� ��������� ���������� ������ �� " + cardList.size() + " �� ���");

		if (cardList.isEmpty())
			return Collections.emptyList();
		else return cardList;
	}

	/**
	 * ���������� ������� ������������
	 * @param cards - ������ ����, �� ������� ����� �������� ������ ��������
	 * @return ������ ������-��-�������-��������
	 */
	private List<PaymentTemplateLink> getGateTemplateLinks(List<Card> cards)
			throws BusinessException
	{
		log.trace("������������ ������ ������-��-������� �������� ��...");
		if (CollectionUtils.isEmpty(cards))
			return Collections.emptyList();

		// 1. ������� �������� ������������� ��������
		List<GatePaymentTemplate> templates = getGatePaymentTemplates(cards);
		if (log.isDebugEnabled())
			log.debug("�������� ��������� ������� �������� �� ����� ��: " + LogUtils.formatGatePaymentTemplateList(templates));
		if (CollectionUtils.isEmpty(templates))
			return Collections.emptyList();

		// 2. ������ ������ ����� ����������� � ��������� �����
		List<String> providerMBCodes = listMobilebankCodes(templates);

		// 3. �� ����� �� �.2 ������ ��� �����������
		// ���� � ���� �� ������ ��������� �� ����, ��� ����� ��������������
		Map<String, BillingServiceProvider> providers = getServiceProviders(providerMBCodes);
		if (log.isDebugEnabled())
			log.debug("��� ���������� �������� �������� �� � ���� ������� ��������� ����������: " + LogUtils.formatProviderList(providers.values()));

		// 4. �� ����������� ������� ������ �������� �����
		Map<BillingServiceProvider, List<FieldDescription>> keyFields
				= getProviderKeyFields(providers.values());

		// 5. ������ �����
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
			log.warn("������ ��� ��������� �������� �������� ��. " +
					"����� �: " + MaskUtil.getCutCardNumber(entry.getKey()) + ". " +
					"���������: " + ex.getMessage(), ex);
		}

		return GroupResultHelper.joinValues(result);
	}

	/**
	 * ���������� �������� ���� ��� ��������� �����������
	 * @param providers - ����������, ��� ������� ����� ��������� �������� ����
	 * @return ��� "��������� => ������-��������-�����"
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
				log.warn("������ �������� �� " + LogUtils.formatGatePaymentTemplate(template) + " �������������, " +
						"�.�. �� ������ ��������� �� ��-���� " + mbCode);
				continue;
			}

			if (StringHelper.isEmpty(provider.getMobilebankCode())) {
				log.warn("������ �������� �� " + LogUtils.formatGatePaymentTemplate(template) + " �������������, " +
						"�.�. ��������� " + LogUtils.formatProvider(provider) + " �� ������������ ������ � �� " +
						"(�� ��������� ���� \"��� ���������� � ��������� �����\")");
				continue;
			}

			if (!provider.isAvailablePaymentsForMApi()) {
				log.warn("������ �������� �� " + LogUtils.formatGatePaymentTemplate(template) + " �������������, " +
						"�.�. ��������� " + LogUtils.formatProvider(provider) + " �� �������� �� ���������� ���������� " +
						"(�������� ������ \"������ �� ���������� ���������� \")");
				continue;
			}

			if (provider.getState() != ACTIVE) {
				log.warn("������ �������� �� " + LogUtils.formatGatePaymentTemplate(template) + " �������������, " +
						"�.�. ��������� " + LogUtils.formatProvider(provider) + " �� �������");
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
	 * ���������� ����������� �� �� ���� � �����
	 * @param mbCodes - ���� ����������� � �����
	 * @return ��� "���_���������� => ���������"
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

		// ������ �������� �� ������������ ���������� �������� ���������� �����
		try
		{
			MobileBankUtils.testServiceProviderMobilebankCompatible(provider);
		}
		catch (UncompatibleServiceProviderException ex)
		{
			log.warn("��������� " + LogUtils.formatProvider(provider) + " �� �������� ��� ������ �� ������� ������� ��: " + ex.getMessage());
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
	 * @param list - ������ ������-��-�������
	 * @return ��� "����-���� -> ����-��-������"
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
				log.error("����� ����-������ ������������ " +
						"�� ������ ����-���� �� ����� " + MaskUtil.getCutCardNumber(cardNumber) + ". " +
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

	// ��������� �� ������ ��������� �������� ��������� �������
	private List<PaymentTemplateLink> filterPaymentTemplateLinks(List<PaymentTemplateLink> list)
	{
		List<PaymentTemplateLink> filteredList = new ArrayList<PaymentTemplateLink>();
		for(PaymentTemplateLink link : list)
		{
			if (isServicePayment(link))
				filteredList.add(link);
			else log.warn("����-��-������ ������� �� " + link.getExternalId() + " ��������������, �.�. " +
					"��� ��������� �������");
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
