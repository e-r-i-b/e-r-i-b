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
 * ����� ��� ������������� �������� ��� �������� ���������
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
			throw new IllegalArgumentException("addedDocument �� ����� ���� null");

		if(allowTypes == null)
			throw new IllegalArgumentException("allowTypes �� ����� ���� null");

		BasketIndetifierType indetifierType = allowTypes.get(addedDocument.getDocumentType().name());
		if(indetifierType == null)
			throw new BusinessException("�� ������ ������������� ��������� � ������ " + addedDocument.getDocumentType().name());

		if(!PermissionUtil.impliesService("PaymentBasketManagment"))
			return null;

		// ������������ ������ �� ������� ���������
		Map<Long, Map<String, FieldFormula>> formulas =
				fieldFormulaService.getWithNameRelatedProvider(indetifierType.getId());

		if(MapUtils.isEmpty(formulas))
			return null;

		// ������������ ���� ��������� ���������� ����������� ������������� ��������
		switch (addedDocument.getDocumentType())
		{
			case INN:
			{
				createNewInvoiceSubscriptions(addedDocument, indetifierType, formulas);
				return BASKET_MESSAGE_WITH_KEY_PREFIX + DocumentType.INN;
			}
			case DL:
			{
				// ���� � ������� ��� ���� ��������� ���
				UserDocument documentRC = UserDocumentService.get().getUserDocumentByLoginAndType(addedDocument.getLoginId(), DocumentType.RC);
				if(documentRC != null)
				{
					BasketIndetifierType indetifierTypeRC = allowTypes.get(DocumentType.RC.name());
					if(indetifierTypeRC != null)
					{
						List<InvoiceSubscription> subscriptions =
								invoiceService.findByLoginWithFormulas(addedDocument.getLoginId(), indetifierType.getId(), indetifierTypeRC.getId());

						// ������������ ������ �� ���������� ���
						Map<Long, Map<String, FieldFormula>> formulasRC =
								fieldFormulaService.getWithNameRelatedProvider(indetifierTypeRC.getId());

						// ������� ��������, ���������� ������� �����������
						Collection<Long> notRemovedProviderIds =
								removeCommonSubscriptions(subscriptions, documentRC, formulasRC, addedDocument, formulas);

						// ������� ����������� ��������(�.�. �����, � ������� ���� ��������� �� �������� � RC � DL)
						Collection<Long> intersectionProviderIds =
								createNewCompositeInvoiceSubscription(notRemovedProviderIds, documentRC, formulasRC, addedDocument, formulas);

						Collection<Long> newProviderIds =
								mergeProviderId(formulas.keySet(), CollectionUtils.union(intersectionProviderIds, notRemovedProviderIds));
						// ������� ����� ��������
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

						// ������������ ������ �� ���������� ��
						Map<Long, Map<String, FieldFormula>> formulasDL =
								fieldFormulaService.getWithNameRelatedProvider(indetifierTypeDL.getId());

						// ������� ��������, ���������� ������� �����������
						Collection<Long> notRemovedProviderIds =
								removeCommonSubscriptions(subscriptions, addedDocument, formulas, documentDL, formulasDL);

						// ������� ����������� ��������(�.�. �����, � ������� ���� ��������� �� �������� � RC � DL)
						Collection<Long> intersectionProviderIds =
								createNewCompositeInvoiceSubscription(notRemovedProviderIds, addedDocument, formulas, documentDL, formulasDL);

						Collection<Long> newProviderIds =
								mergeProviderId(formulas.keySet(), CollectionUtils.union(intersectionProviderIds, notRemovedProviderIds));
						// ������� ����� ��������
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
			// ���� ������������ ������������ �������, �� ����� ����������� �������� (������� ������, ������� �����)
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
	 * ������� �������� �� �����������, ������� ������������
	 * @param notRemovedProviderIds ����������, �� ������� ���� ������� ������� � �� ��������
	 * @param documentRC �������� ���
	 * @param formulasRC ����� ����������� � ��������� ���
	 * @param documentDL �������� ��
	 * @param formulasDL ����� ����������� � ��������� ��
	 * @return ��������� �����������, ������� ������������ � ��� � ��, ���� �� ������ ���� notRemovedProviderIds
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

		// ������� ��������� ����������� � �������������
		Collection<Long> intersectionIds = new HashSet<Long>();
		for(Long providerIdRC : providerIdsRC)
		{
			// ���� ���������� � ����� ����������
			if(providerIdsDL.contains(providerIdRC))
				intersectionIds.add(providerIdRC);
		}

		// ������� �������� � ������������ ���������
		for(Long providerId : intersectionIds)
		{
			// ���� ���� ������� ������� ��� �������� � ����� ����� ���������� �� ����� �������, �� � ��������� ����� ���� �� �����
			if(notRemovedProviderIds.contains(providerId))
				continue;

			Map<String, FieldFormula> fieldFormulasRC = formulasRC.get(providerId);
			Map<String, FieldFormula> fieldFormulasDL = formulasDL.get(providerId);

			// �� ����� ������ ����������(�� ������ ������ ����)
			if(MapUtils.isEmpty(fieldFormulasRC) && MapUtils.isEmpty(fieldFormulasDL))
				continue;

			// ��� ����������� ������������ ����� ������ �� ������� - ������ ��� �����
			Map<String, Object> fieldsValues = new HashMap<String, Object>();
			for(Map.Entry<String, FieldFormula> entryField : fieldFormulasRC.entrySet())
			{
				fieldsValues.put(entryField.getKey(), computeValue(documentRC, entryField.getValue()));
			}

			for(Map.Entry<String, FieldFormula> entryField : fieldFormulasDL.entrySet())
			{
				fieldsValues.put(entryField.getKey(), computeValue(documentDL, entryField.getValue()));
			}

			log.warn("������� �������� � providerId = " + providerId + " � ���������� [" + MapUtil.format(fieldsValues, "=", ";") + "]");
			invoiceService.createAndSaveInvoiceSubscription(providerId, fieldsValues);
		}

		return intersectionIds;
	}

	private static void createNewInvoiceSubscriptions(UserDocument addedDocument, BasketIndetifierType indetifierType, Map<Long, Map<String, FieldFormula>> formulas) throws BusinessException
	{
		List<InvoiceSubscription> subscriptions =
				invoiceService.findByLoginWithFormulas(addedDocument.getLoginId(), indetifierType.getId(), STUB_ID);

		// ���������� ����������� �������������� �����������, � ����� ������� ������ ���� ��������
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
				// ��������� �� �������� - �� �������������
				if(fieldFormulas == null)
					continue;

				List<Field> subscriptionFields = subscription.getRequisitesAsList();
				Pair<Boolean, Boolean> matchInfo =
						getMatchInfo(addedDocument, fieldFormulas.keySet(), fieldFormulas, subscriptionFields);

				// ��� false - ����� ��������� �������
				if(!matchInfo.getFirst() && !matchInfo.getSecond())
					continue;

				// ���� �� ����� ��������������, ��������� � ������ ���������
				if(!matchInfo.getSecond())
					subscriptionProviderIds.add(subscription.getRecipientId());
			}

			providerIds.addAll(mergeProviderId(formulas.keySet(), subscriptionProviderIds));
		}

		// ��������� ����������� ��������
		createInvoiceSubscriptions(providerIds, addedDocument, formulas);
	}

	/**
	 * ���� �� ������������� � �������� ������� ����������
	 * @param subscription ���������
	 * @param documentRC �������� ���
	 * @param formulasRC ����� ����������� � ��������� ���
	 * @param documentDL �������� ��
	 * @param formulasDL ����� ����������� � ��������� ��
	 * @return true - ���������� �������
	 */
	private static boolean isRemoveInvoiceSubscription(InvoiceSubscription subscription, UserDocument documentRC, Map<Long, Map<String, FieldFormula>> formulasRC, UserDocument documentDL, Map<Long, Map<String, FieldFormula>> formulasDL)
	{
		// �� �� �������� � ���������� �� �/��� ���
		Long providerId = subscription.getRecipientId();

		boolean isContainsInDL = formulasDL.keySet().contains(providerId);
		boolean isContainsInRC = formulasRC.keySet().contains(providerId);

		if(!isContainsInDL || !isContainsInRC)
			return false;

		// ���� ��� ����������� ����������� � RC � DL
		if((isContainsInRC && !isContainsInDL) || (!isContainsInRC && isContainsInDL))
			return false;

		// ��� ��������� ������� ����� ����������� ���� � �������
		List<Field> subscriptionFields = subscription.getRequisitesAsList();
		Map<String, FieldFormula> linksRC = formulasRC.get(providerId);
		Map<String, FieldFormula> linksDL = formulasDL.get(providerId);

		// ����� ���� �������� ���?
		Collection<String> fieldNamesRC = getFieldNameContainingValue(linksRC);
		// ��������� ���������� ����������� �� ������� �������� �����, ���������� ���, � ������ � ��������
		Pair<Boolean, Boolean> matchInfoRC =
				getMatchInfo(documentRC, fieldNamesRC, linksRC, subscriptionFields);

		// ����� ���� �������� ��?
		Collection<String> fieldNamesDL = getFieldNameContainingValue(linksDL);
		// ��������� ���������� ����������� �� ������� �������� �����, ���������� ��, � ������ � ��������
		Pair<Boolean, Boolean> matchInfoDL =
				getMatchInfo(documentDL, fieldNamesDL, linksDL, subscriptionFields);

		// ���� ��� ���������, ���� �� ������ �� ���������
		if((!matchInfoRC.getFirst() && !matchInfoDL.getFirst()) || (!matchInfoRC.getSecond()&& !matchInfoDL.getSecond()))
			return false;

		return true;
	}

	/**
	 * �������� ���������� �� ���������� �������� ����� ���������� �� ���������� ����� ���������, ����������� �� ��������
	 * @param document ��������
	 * @param processedFieldNames �������������� ����� �����
	 * @param links ����� ����� � ������ ���������
	 * @param subscriptionFields ����������� ���� ������������� ����������
	 * @return ����[true - ����� ���� �� ���� ������������ ��������, true - ������� ���� �� ���� �� ������������]
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
		// ��� �������� �������������� ������ �� �������
		if(subscription.getState() == InvoiceSubscriptionState.DRAFT)
		{
			invoiceService.updateState(InvoiceSubscriptionState.DELETED, subscription.getId());
		}
		else if(StringHelper.isNotEmpty(subscription.getAutoPayId()))
		{
			// ������� ������ ��������
			log.warn("������� �������� � id = " + subscription.getId());
			invoiceService.updateState(InvoiceSubscriptionState.DELAY_DELETE, subscription.getId());
		}
	}

	/**
	 * ������� ���������� ��� ��������� ����������� � ����������� ����� �� ��������� � ��������
	 * @param providersIds �������������� �����������
	 * @param addedDocument ���������
	 * @param formulas ������� ���������� ����� ����������
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

			log.warn("������� �������� � providerId = " + providerId + " � ���������� [" + MapUtil.format(fieldsValues, "=", ";") + "]");
			invoiceService.createAndSaveInvoiceSubscription(providerId, fieldsValues);
		}
	}

	/**
	 * ������� ����������� �������������� �����������
	 * @param linkProviderIds �������������� ����������� � ������
	 * @param excludes �������������� ����������� ������� ����������� ���������
	 * @return ��������� ����������� ��������������� �����������
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
	 * ��������� �������� �� ������� � ���������
	 * @param document ��������
	 * @param formula �������
	 * @return ����������� ��������
	 */
	private static Object computeValue(UserDocument document, FieldFormula formula)
	{
		if(document == null)
			throw new IllegalArgumentException("document �� ����� ���� null");

		if(formula == null)
			throw new IllegalArgumentException("formula �� ����� ���� null");

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
