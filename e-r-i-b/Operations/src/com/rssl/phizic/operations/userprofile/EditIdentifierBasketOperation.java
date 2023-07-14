package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionSyncUtil;
import com.rssl.phizic.business.basket.links.LinkInfo;
import com.rssl.phizic.business.dictionaries.basketident.*;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessagesService;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * Просмотр/редактирование идентификаторов корзины
 * @author lukina
 * @ created 17.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditIdentifierBasketOperation extends ConfirmableOperationBase
{
	private static final StaticMessagesService staticMessagesService = new StaticMessagesService();
	private static final InvoiceSubscriptionService invoiceService = new InvoiceSubscriptionService();
	private static final BasketIdentifierService basketIdentifierService = new BasketIdentifierService();

	private final UserDocumentService service = UserDocumentService.get();
	private UserDocument userDocument;
	private List<UserDocument> userDocumentList;
	private boolean isNumberChanged = false;
	private boolean isSeriesChanged = false;
	private boolean isIssueDateChanged = false;
	private boolean isIssueByChanged = false;
	private boolean isExpireDateChanged = false;
	private boolean isNameChanged = false;

	public void initialize() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		userDocumentList = service.getUserDocumentByLogin(personData.getLogin().getId());
	}

	public void initialize(Long docId) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		userDocument = service.getUserDocumentById(docId);
		if (userDocument != null && !userDocument.getLoginId().equals(personData.getLogin().getId()))
			throw new AccessException("Документ с id=" + docId + " не принадлежит пользователю с login_id=" + personData.getLogin().getId());
	}

	public void initialize(Long docId, DocumentType docType) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		userDocument = service.getUserDocumentById(docId);
		if (userDocument == null || userDocument.getDocumentType() != docType)
			throw new ResourceNotFoundBusinessException("Документ с id=" + docId + " не найден.", UserDocument.class);
		if (userDocument != null && !userDocument.getLoginId().equals(personData.getLogin().getId()))
			throw new AccessException("Документ с id=" + docId + " не принадлежит пользователю с login_id=" + personData.getLogin().getId());
	}

	public void initializeNew(String docType) throws BusinessException
	{
		Login login = PersonContext.getPersonDataProvider().getPersonData().getLogin();
		userDocument = new UserDocument();
		userDocument.setLoginId(login.getId());
		userDocument.setDocumentType(DocumentType.valueOf(docType));
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		save();
		syncInvoiceSubscription();
	}

	public List<UserDocument> getUserDocumentList()
	{
		return userDocumentList;
	}

	public UserDocument getUserDocument()
	{
		return userDocument;
	}

	public Map<String, BasketIndetifierType> getAllowedTypes()
	{
		try
		{
			return basketIdentifierService.getIdentifierMap();
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void setName(String name)
	{
		if (userDocument.getDocumentType() != DocumentType.SNILS && !getAllowedTypes().containsKey(userDocument.getDocumentType().name()))
			return;

		if (!StringHelper.getEmptyIfNull(name).equals(StringHelper.getEmptyIfNull(this.userDocument.getName())))
		{
			this.userDocument.setName(name);
			isNameChanged = true;
		}
	}

	public void setSeries(String series)
	{
		if (!getAllowedTypes().containsKey(userDocument.getDocumentType().name()))
			return;

		if (!getAllowedTypes().get(userDocument.getDocumentType().name()).getAttributes().containsKey(AttributeSystemId.SERIES.name()))
			return;

		if (!StringHelper.getEmptyIfNull(series).equals(StringHelper.getEmptyIfNull(this.userDocument.getSeries())))
		{
			this.userDocument.setSeries(series);
			isSeriesChanged = true;
		}
	}

	public void setNumber(String number)
	{
		if (userDocument.getDocumentType() != DocumentType.SNILS) {
			if (!getAllowedTypes().containsKey(userDocument.getDocumentType().name()))
				return;

			if (!getAllowedTypes().get(userDocument.getDocumentType().name()).getAttributes().containsKey(AttributeSystemId.NUMBER.name()))
				return;
		}

		if (!StringHelper.getEmptyIfNull(number).equals(StringHelper.getEmptyIfNull(this.userDocument.getNumber())))
		{
			this.userDocument.setNumber(number);
			isNumberChanged = true;
		}
	}

	public void setIssueDate(Calendar issueDate)
	{
		if (!getAllowedTypes().containsKey(userDocument.getDocumentType().name()))
			return;

		if (!getAllowedTypes().get(userDocument.getDocumentType().name()).getAttributes().containsKey(AttributeSystemId.ISSUE_DATE.name()))
			return;

		if (!issueDate.equals(this.userDocument.getIssueDate()))
		{
			this.userDocument.setIssueDate(issueDate);
			isIssueDateChanged = true;
		}
	}

	public void setExpireDate(Calendar expireDate)
	{
		if (!getAllowedTypes().containsKey(userDocument.getDocumentType().name()))
			return;

		if (!getAllowedTypes().get(userDocument.getDocumentType().name()).getAttributes().containsKey(AttributeSystemId.EXPIRE_DATE.name()))
			return;

		if (!expireDate.equals(this.userDocument.getExpireDate()))
		{
			this.userDocument.setExpireDate(expireDate);
			isExpireDateChanged = true;
		}
	}

	public void setIssueBy(String issueBy)
	{
		if (!getAllowedTypes().containsKey(userDocument.getDocumentType().name()))
			return;

		if (!getAllowedTypes().get(userDocument.getDocumentType().name()).getAttributes().containsKey(AttributeSystemId.ISSUE_BY.name()))
			return;

		if (!StringHelper.getEmptyIfNull(issueBy).equals(StringHelper.getEmptyIfNull(this.userDocument.getIssueBy())))
		{
			this.userDocument.setIssueBy(issueBy);
			isIssueByChanged = true;
		}
	}

	public ConfirmableObject getConfirmableObject()
	{
		Map<String, Object> unsavedData = new HashMap<String, Object>();
		BasketIndetifierType tp = getAllowedTypes().get(userDocument.getDocumentType().name());
		if (isNameChanged)
			unsavedData.put(tp == null ? "" : tp.getName(), userDocument.getName());
		if (isNumberChanged)
			if (tp != null)
				unsavedData.put(tp.getAttributes().get(AttributeSystemId.NUMBER.name()).getName(), userDocument.getNumber());
			else
				unsavedData.put("Номер", userDocument.getNumber());
		if (isSeriesChanged)
			unsavedData.put(tp.getAttributes().get(AttributeSystemId.SERIES.name()).getName(), userDocument.getSeries());
		if (isIssueDateChanged)
			unsavedData.put(tp.getAttributes().get(AttributeSystemId.ISSUE_DATE.name()).getName(), DateHelper.formatDateToStringWithSlash(userDocument.getIssueDate()));
		if (isIssueByChanged)
			unsavedData.put(tp.getAttributes().get(AttributeSystemId.ISSUE_BY.name()).getName(), userDocument.getIssueBy());
		if (isExpireDateChanged)
			unsavedData.put(tp.getAttributes().get(AttributeSystemId.EXPIRE_DATE.name()).getName(), DateHelper.formatDateToStringWithSlash(userDocument.getExpireDate()));
		return new PersonalSettings(unsavedData);
	}

	/**
	 * @return Список связанных услуг.
	 * @throws BusinessException
	 */
	public List<LinkInfo> getLink() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		return invoiceService.getLinkInfoForUserDocument(personData.getLogin().getId(), userDocument.getDocumentType().name());
	}

	public void save() throws BusinessException
	{
		service.addOrUpdate(userDocument);
		PersonSettingsManager.savePersonData(PersonSettingsManager.USER_DOCUMENTS_KEY + userDocument.getDocumentType().name(), userDocument);
		if (isNumberChanged || isSeriesChanged || isExpireDateChanged || isIssueByChanged || isIssueDateChanged)
			invoiceService.removeUserDocumentsLinks(userDocument.getLoginId(), userDocument.getDocumentType().name());
	}

	private void syncInvoiceSubscription() throws BusinessException
	{
		try
		{
			BasketIndetifierType indetifierType = getAllowedTypes().get(userDocument.getDocumentType().name());
			if(indetifierType == null)
				throw new BusinessException("Не найден идентификатор документа с именем " + userDocument.getDocumentType().name());

			// синхронизируем подписки
			String messageKey = InvoiceSubscriptionSyncUtil.syncDueToAddDocument(userDocument, getAllowedTypes());
			// сохраняем сообщение если есть
			if(StringHelper.isNotEmpty(messageKey))
			{
				StaticMessage message = staticMessagesService.findByKey(messageKey);
				if(message != null && StringHelper.isNotEmpty(message.getText()))
					getMessageCollector().addMessage(message.getText());
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}

	}
}
