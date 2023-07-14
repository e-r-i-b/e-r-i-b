package com.rssl.phizic.business.basket.invoiceSubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.basketident.*;
import com.rssl.phizic.business.documents.payments.BillingPaymentHelper;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.MapUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * Класс для синхронизации подписок при создании документа
 * @author niculichev
 * @ created 17.07.15
 * @ $Author$
 * @ $Revision$
 */
public class InvoiceSubscriptionSyncUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final Long STUB_ID = -1L;

	public static final String SERIES_FIELD_DL = "series_dl";
	public static final String NUMBER_FIELD_DL = "number_dl";
	public static final String ISSUE_DATE_FIELD_DL = "issueDate";
	public static final String EXPIRE_DATE_FIELD_Dl = "expireDate";
	public static final String ISSUE_BY_FIELD_DL = "issueBy";

	public static final String SERIES_FIELD_RC = "series_rc";
	public static final String NUMBER_FIELD_RC = "number_rc";

	private static final String BASKET_MESSAGE_WITH_KEY_PREFIX = "basket_message_with_";
	private static final String BASKET_MESSAGE_WITHOUT_KEY_PREFIX = "basket_message_without_";

	private static final FieldFormulaService fieldFormulaService = new FieldFormulaService();
	private static final InvoiceSubscriptionService invoiceService = new InvoiceSubscriptionService();

	public static String syncDueToAddDocument(UserDocument addedDocument, Map<String, BasketIndetifierType> allowTypes) throws BusinessException
	{
		if(addedDocument == null)
			throw new IllegalArgumentException("addedDocument не может быть null");

		if(allowTypes == null)
			throw new IllegalArgumentException("allowTypes не может быть null");

		BasketIndetifierType indetifierType = allowTypes.get(addedDocument.getDocumentType().name());
		if(indetifierType == null)
			throw new BusinessException("Не найден идентификатор документа с именем " + addedDocument.getDocumentType().name());

		if(!PermissionUtil.impliesService("PaymentBasketManagment"))
			return null;

		// существующие связки по данному документу
		Map<Long, Map<String, FieldFormula>> formulas =
				fieldFormulaService.getWithNameRelatedProvider(indetifierType.getId());

		if(MapUtils.isEmpty(formulas))
			return null;

		// относительно типа документа определяем поведеление синхронизации подписок
		switch (addedDocument.getDocumentType())
		{
			case INN:
			{
				createNewInvoiceSubscriptions(addedDocument, indetifierType, formulas);
				return BASKET_MESSAGE_WITH_KEY_PREFIX + DocumentType.INN;
			}
			case DL:
			{
				// если у клиента уже есть созданное СТС
				UserDocument documentRC = UserDocumentService.get().getUserDocumentByLoginAndType(addedDocument.getLoginId(), DocumentType.RC);
				if(documentRC != null)
				{
					BasketIndetifierType indetifierTypeRC = allowTypes.get(DocumentType.RC.name());
					if(indetifierTypeRC != null)
					{
						List<InvoiceSubscription> subscriptions =
								invoiceService.findByLoginWithFormulas(addedDocument.getLoginId(), indetifierType.getId(), indetifierTypeRC.getId());

						// существующие связки по созданному СТС
						Map<Long, Map<String, FieldFormula>> formulasRC =
								fieldFormulaService.getWithNameRelatedProvider(indetifierTypeRC.getId());

						// удаляем подписки, поставщики которых персекаются
						Collection<Long> notRemovedProviderIds =
								removeCommonSubscriptions(subscriptions, documentRC, formulasRC, addedDocument, formulas);

						// создаем композитные подписки(т.е. такие, у которых поля заполнены по формулам и RC и DL)
						Collection<Long> intersectionProviderIds =
								createNewCompositeInvoiceSubscription(notRemovedProviderIds, documentRC, formulasRC, addedDocument, formulas);

						Collection<Long> newProviderIds =
								mergeProviderId(formulas.keySet(), CollectionUtils.union(intersectionProviderIds, notRemovedProviderIds));
						// создаем новые подписки
						createInvoiceSubscriptions(newProviderIds, addedDocument, formulas);
						return BASKET_MESSAGE_WITH_KEY_PREFIX + DocumentType.DL;
					}
				}
				else
				{
					createNewInvoiceSubscriptions(addedDocument, indetifierType, formulas);
					return BASKET_MESSAGE_WITHOUT_KEY_PREFIX + DocumentType.DL;
				}
				break;
			}
			case RC:
			{
				UserDocument documentDL = UserDocumentService.get().getUserDocumentByLoginAndType(addedDocument.getLoginId(), DocumentType.DL);
				if(documentDL != null)
				{
					BasketIndetifierType indetifierTypeDL = allowTypes.get(DocumentType.DL.name());
					if(indetifierTypeDL != null)
					{
						List<InvoiceSubscription> subscriptions =
								invoiceService.findByLoginWithFormulas(addedDocument.getLoginId(), indetifierType.getId(), indetifierTypeDL.getId());

						// существующие связки по созданному ВУ
						Map<Long, Map<String, FieldFormula>> formulasDL =
								fieldFormulaService.getWithNameRelatedProvider(indetifierTypeDL.getId());

						// удаляем подписки, поставщики которых персекаются
						Collection<Long> notRemovedProviderIds =
								removeCommonSubscriptions(subscriptions, addedDocument, formulas, documentDL, formulasDL);

						// создаем композитные подписки(т.е. такие, у которых поля заполнены по формулам и RC и DL)
						Collection<Long> intersectionProviderIds =
								createNewCompositeInvoiceSubscription(notRemovedProviderIds, addedDocument, formulas, documentDL, formulasDL);

						Collection<Long> newProviderIds =
								mergeProviderId(formulas.keySet(), CollectionUtils.union(intersectionProviderIds, notRemovedProviderIds));
						// создаем новые подписки
						createInvoiceSubscriptions(newProviderIds, addedDocument, formulas);
						return BASKET_MESSAGE_WITH_KEY_PREFIX + DocumentType.RC;
					}
				}
				else
				{
					createNewInvoiceSubscriptions(addedDocument, indetifierType, formulas);
					return BASKET_MESSAGE_WITHOUT_KEY_PREFIX + DocumentType.RC;
				}
				break;
			}
		}

		return null;
	}

	private static Collection<Long> removeCommonSubscriptions(Collection<InvoiceSubscription> subscriptions, UserDocument documentRC, Map<Long, Map<String, FieldFormula>> formulasRC, UserDocument documentDL, Map<Long, Map<String, FieldFormula>> formulasDL) throws BusinessException
	{
		Collection<Long> notRemovedProviderIds = new HashSet<Long>();
		for(InvoiceSubscription subscription : subscriptions)
		{
			// если соблюдаеются определенные условия, то нужно пересоздать подписку (удалить старую, создать новую)
			if(isRemoveInvoiceSubscription(subscription, documentRC, formulasRC, documentDL, formulasDL))
			{
				removeInvoiceSubscription(subscription);
			}
			else
			{
				notRemovedProviderIds.add(subscription.getRecipientId());
			}
		}

		return notRemovedProviderIds;
	}

	/**
	 * Создать подписки по поставщикам, которые пересекаются
	 * @param notRemovedProviderIds поставщики, по которым было принято решение о НЕ удалении
	 * @param documentRC документ СТС
	 * @param formulasRC связи поставщиков и документа СТС
	 * @param documentDL документ ВУ
	 * @param formulasDL связи поставщиков и документа ВУ
	 * @return множество поставщиков, которые пересекаются у СТС и ВУ, либо не прошли изза notRemovedProviderIds
	 * @throws BusinessException
	 */
	private static Collection<Long> createNewCompositeInvoiceSubscription(Collection<Long> notRemovedProviderIds, UserDocument documentRC, Map<Long, Map<String, FieldFormula>> formulasRC, UserDocument documentDL, Map<Long, Map<String, FieldFormula>> formulasDL) throws BusinessException
	{
		Collection<Long> providerIdsRC = formulasRC.keySet();
		if(CollectionUtils.isEmpty(providerIdsRC))
			return Collections.emptySet();

		Collection<Long> providerIdsDL = formulasDL.keySet();
		if(CollectionUtils.isEmpty(providerIdsDL))
			return Collections.emptySet();

		// находим множество поставщиков с пересечениями
		Collection<Long> intersectionIds = new HashSet<Long>();
		for(Long providerIdRC : providerIdsRC)
		{
			// если содержится в обоих документах
			if(providerIdsDL.contains(providerIdRC))
				intersectionIds.add(providerIdRC);
		}

		// создаем подписку с вычисленными значениям
		for(Long providerId : intersectionIds)
		{
			// если было принято решение что подписку в адрес этого поставщика не нужно удалять, то и создавать новую тоже не нужно
			if(notRemovedProviderIds.contains(providerId))
				continue;

			Map<String, FieldFormula> fieldFormulasRC = formulasRC.get(providerId);
			Map<String, FieldFormula> fieldFormulasDL = formulasDL.get(providerId);

			// не нашли формул заполнения(не должно такого быть)
			if(MapUtils.isEmpty(fieldFormulasRC) && MapUtils.isEmpty(fieldFormulasDL))
				continue;

			// про пересечение наименований полей ничего не сказано - делаем как проще
			Map<String, Object> fieldsValues = new HashMap<String, Object>();
			for(Map.Entry<String, FieldFormula> entryField : fieldFormulasRC.entrySet())
			{
				fieldsValues.put(entryField.getKey(), computeValue(documentRC, entryField.getValue()));
			}

			for(Map.Entry<String, FieldFormula> entryField : fieldFormulasDL.entrySet())
			{
				fieldsValues.put(entryField.getKey(), computeValue(documentDL, entryField.getValue()));
			}

			log.warn("Создаем подписку с providerId = " + providerId + " и значениями [" + MapUtil.format(fieldsValues, "=", ";") + "]");
			invoiceService.createAndSaveInvoiceSubscription(providerId, fieldsValues);
		}

		return intersectionIds;
	}

	private static void createNewInvoiceSubscriptions(UserDocument addedDocument, BasketIndetifierType indetifierType, Map<Long, Map<String, FieldFormula>> formulas) throws BusinessException
	{
		List<InvoiceSubscription> subscriptions =
				invoiceService.findByLoginWithFormulas(addedDocument.getLoginId(), indetifierType.getId(), STUB_ID);

		// определяем недостающие идентификаторы поставщиков, в адрес которых должны быть подписки
		Collection<Long> providerIds = new HashSet<Long>();
		if(CollectionUtils.isEmpty(subscriptions))
		{
			providerIds.addAll(formulas.keySet());
		}
		else
		{
			Set<Long> subscriptionProviderIds = new HashSet<Long>();
			for(InvoiceSubscription subscription : subscriptions)
			{
				Map<String, FieldFormula> fieldFormulas = formulas.get(subscription.getRecipientId());
				// поставщик не привязан - не рассматриваем
				if(fieldFormulas == null)
					continue;

				List<Field> subscriptionFields = subscription.getRequisitesAsList();
				Pair<Boolean, Boolean> matchInfo =
						getMatchInfo(addedDocument, fieldFormulas.keySet(), fieldFormulas, subscriptionFields);

				// оба false - плохо вычислили формулы
				if(!matchInfo.getFirst() && !matchInfo.getSecond())
					continue;

				// если не нашли несоответсвтий, добавляем в список ислючений
				if(!matchInfo.getSecond())
					subscriptionProviderIds.add(subscription.getRecipientId());
			}

			providerIds.addAll(mergeProviderId(formulas.keySet(), subscriptionProviderIds));
		}

		// добавляем недостающие подписки
		createInvoiceSubscriptions(providerIds, addedDocument, formulas);
	}

	/**
	 * Есть ли необходимость в удалении данного автопоиска
	 * @param subscription автопоиск
	 * @param documentRC документ СТС
	 * @param formulasRC связи поставщиков и документа СТС
	 * @param documentDL документ ВУ
	 * @param formulasDL связи поставщиков и документа ВУ
	 * @return true - необходимо удалить
	 */
	private static boolean isRemoveInvoiceSubscription(InvoiceSubscription subscription, UserDocument documentRC, Map<Long, Map<String, FieldFormula>> formulasRC, UserDocument documentDL, Map<Long, Map<String, FieldFormula>> formulasDL)
	{
		// ПУ не привязан к документам ВУ и/или СТС
		Long providerId = subscription.getRecipientId();

		boolean isContainsInDL = formulasDL.keySet().contains(providerId);
		boolean isContainsInRC = formulasRC.keySet().contains(providerId);

		if(!isContainsInDL || !isContainsInRC)
			return false;

		// если нет пересечений поставщиков у RC и DL
		if((isContainsInRC && !isContainsInDL) || (!isContainsInRC && isContainsInDL))
			return false;

		// для остальных условий нужны распасенные поля и формулы
		List<Field> subscriptionFields = subscription.getRequisitesAsList();
		Map<String, FieldFormula> linksRC = formulasRC.get(providerId);
		Map<String, FieldFormula> linksDL = formulasDL.get(providerId);

		// Какие поля содержат СТС?
		Collection<String> fieldNamesRC = getFieldNameContainingValue(linksRC);
		// проверяем совпадение вычисленных по формуле значений полей, содержащих СТС, с полями в подписке
		Pair<Boolean, Boolean> matchInfoRC =
				getMatchInfo(documentRC, fieldNamesRC, linksRC, subscriptionFields);

		// Какие поля содержат ВУ?
		Collection<String> fieldNamesDL = getFieldNameContainingValue(linksDL);
		// проверяем совпадение вычисленных по формуле значений полей, содержащих ВУ, с полями в подписке
		Pair<Boolean, Boolean> matchInfoDL =
				getMatchInfo(documentDL, fieldNamesDL, linksDL, subscriptionFields);

		// либо все совпадают, либо ни одного не совпадает
		if((!matchInfoRC.getFirst() && !matchInfoDL.getFirst()) || (!matchInfoRC.getSecond()&& !matchInfoDL.getSecond()))
			return false;

		return true;
	}

	/**
	 * Получить информацию по совпадению значений полей автопоиска со значениями полей документа, вычисленные по формулам
	 * @param document документ
	 * @param processedFieldNames обрабатываемые именя полей
	 * @param links связи полей и формул заполения
	 * @param subscriptionFields распасенные поля существующего автопоиска
	 * @return пара[true - найно хотя бы одно соответствие значений, true - найдено хотя бы одно НЕ соответствие]
	 */
	private static Pair<Boolean, Boolean> getMatchInfo(UserDocument document, Collection<String> processedFieldNames, Map<String, FieldFormula> links, List<Field> subscriptionFields)
	{
		boolean hasMatch = false;
		boolean hasNotMatch = false;
		for(String fieldName : processedFieldNames)
		{
			FieldFormula fieldFormula = links.get(fieldName);
			if(fieldFormula == null)
				continue;

			Object value = computeValue(document, fieldFormula);
			if(value == null)
				continue;

			Field field = BillingPaymentHelper.getFieldById(subscriptionFields, fieldName);
			if(field == null)
			{
				hasNotMatch = true;
			}
			else if(value.equals(field.getValue()))
			{
				hasMatch = true;
			}
			else
			{
				hasNotMatch = true;
			}
		}

		return new Pair<Boolean, Boolean>(hasMatch, hasNotMatch);
	}

	private static void removeInvoiceSubscription(InvoiceSubscription subscription) throws BusinessException
	{
		// без внешнего идентификатора заявки не сделать
		if(subscription.getState() == InvoiceSubscriptionState.DRAFT)
		{
			invoiceService.updateState(InvoiceSubscriptionState.DELETED, subscription.getId());
		}
		else if(StringHelper.isNotEmpty(subscription.getAutoPayId()))
		{
			// удаляем старую подписку
			log.warn("Удаляем подписку с id = " + subscription.getId());
			invoiceService.updateState(InvoiceSubscriptionState.DELAY_DELETE, subscription.getId());
		}
	}

	/**
	 * Создать автопоиски для указанных поставщиков с заполнением полей по документу и формулам
	 * @param providersIds идентификаторы поставщиков
	 * @param addedDocument документа
	 * @param formulas формулы заполнения полей поставщика
	 * @throws BusinessException
	 */
	private static void createInvoiceSubscriptions(Collection<Long> providersIds, UserDocument addedDocument, Map<Long, Map<String, FieldFormula>> formulas) throws BusinessException
	{
		if(CollectionUtils.isEmpty(providersIds))
			return;

		for(Long providerId : providersIds)
		{
			Map<String, FieldFormula> filedFormulas = formulas.get(providerId);
			if(MapUtils.isEmpty(filedFormulas))
				continue;

			Map<String, Object> fieldsValues = new HashMap<String, Object>(filedFormulas.size());
			for(Map.Entry<String, FieldFormula> entryField : filedFormulas.entrySet())
				fieldsValues.put(entryField.getKey(), computeValue(addedDocument, entryField.getValue()));

			log.warn("Создаем подписку с providerId = " + providerId + " и значениями [" + MapUtil.format(fieldsValues, "=", ";") + "]");
			invoiceService.createAndSaveInvoiceSubscription(providerId, fieldsValues);
		}
	}

	/**
	 * Находим недостающие идентификаторы поставщиков
	 * @param linkProviderIds идентификаторы поставщиков в связях
	 * @param excludes идентификаторы поставщиков которые неообходимо исключить
	 * @return множество недостающих идентификаторов поставщиков
	 */
	private static Collection<Long> mergeProviderId(Collection<Long> linkProviderIds, Collection<Long> excludes)
	{
		if(CollectionUtils.isEmpty(linkProviderIds))
			return Collections.emptyList();

		if(CollectionUtils.isEmpty(excludes))
			return linkProviderIds;

		Set<Long> res = new HashSet<Long>();
		for(Long id : linkProviderIds)
		{
			if(!excludes.contains(id))
				res.add(id);
		}

		return res;
	}

	/**
	 * Вычислить значение по формуле и документу
	 * @param document документ
	 * @param formula формула
	 * @return вычисленное значение
	 */
	private static Object computeValue(UserDocument document, FieldFormula formula)
	{
		if(document == null)
			throw new IllegalArgumentException("document не может быть null");

		if(formula == null)
			throw new IllegalArgumentException("formula не может быть null");

		List<FieldFormulaAttribute> attributes = formula.getAttributes();
		if(CollectionUtils.isEmpty(attributes))
			return null;

		StringBuilder builder = new StringBuilder();
		for(FieldFormulaAttribute attribute : attributes)
		{
			builder.append(StringHelper.getEmptyIfNull(attribute.getValue()));

			String systemId = attribute.getSystemId();
			if(StringHelper.isNotEmpty(systemId))
			{
				switch (AttributeSystemId.valueOf(systemId))
				{
					case SERIES:
					{
						builder.append(document.getSeries());
						break;
					}
					case NUMBER:
					{
						builder.append(document.getNumber());
						break;
					}
					case EXPIRE_DATE:
					{
						if(document.getExpireDate() != null)
							builder.append(String.format("%1$td.%1$tm.%1$tY", document.getExpireDate()));
						break;
					}
					case ISSUE_DATE:
					{
						if(document.getIssueDate() != null)
							builder.append(String.format("%1$td.%1$tm.%1$tY", document.getIssueDate()));
						break;
					}
					case ISSUE_BY:
					{
						builder.append(StringHelper.getEmptyIfNull(document.getIssueBy()));
						break;
					}
				}
			}
		}

		return builder.toString();
	}

	private static Collection<String> getFieldNameContainingValue(Map<String, FieldFormula> links)
	{
		Set<String> res = new HashSet<String>();
		for(Map.Entry<String, FieldFormula> entry : links.entrySet())
		{
			FieldFormula formula = entry.getValue();
			for(FieldFormulaAttribute attribute : formula.getAttributes())
			{
				if(StringHelper.isNotEmpty(attribute.getSystemId()) && attribute.getSystemId().equals(AttributeSystemId.NUMBER.name()))
					res.add(entry.getKey());
			}
		}

		return res;
	}
}
