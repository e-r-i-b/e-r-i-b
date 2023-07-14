package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.basket.links.LinkInfo;
import com.rssl.phizic.business.dictionaries.basketident.BasketIdentifierService;
import com.rssl.phizic.business.dictionaries.basketident.BasketIndetifierType;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.MultiInstancePersonService;
import com.rssl.phizic.business.persons.PersonOperationMode;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.profile.TutorialProfileState;
import com.rssl.phizic.business.profile.UserContactInfo;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.userSettings.UserPropertiesConfig;

import java.util.*;

/**
 * @author Gulov
 * @ created 21.05.2010
 * @ $Authors$
 * @ $Revision$
 */
public class EditUserSettingsOperation extends ConfirmableOperationBase
{
	private ActivePerson user;
	private Profile profile;
	private Map<String, Object> unsavedData;
	private Map<String, Object> originalData = new HashMap<String, Object>();          //оригинальные данные. нужны для отката состояния person если произошла ошибка сохранения
	private static final ProfileService profileService = new ProfileService();
	private static final SimpleService simpleService = new SimpleService();
	private static final MultiInstancePersonService personService = new MultiInstancePersonService();
	private final MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();
	private static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();
	private static final BasketIdentifierService basketIdentifierService = new BasketIdentifierService();
	private Map<String, BasketIndetifierType> allowedTypes = new HashMap<String, BasketIndetifierType>();
	private Region profileRegion;

	public void initialize() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		user = personData.getPerson();
		profile = personData.getProfile();
		profileRegion = personData.getCurrentRegion();
		allowedTypes = basketIdentifierService.getIdentifierMap();

		UserPropertiesConfig config = ConfigFactory.getConfig(UserPropertiesConfig.class);
		if  (config.isFirstUserEnter() && PermissionUtil.impliesServiceRigid("NewClientProfile"))
		{
			config.setFirstUserEnter(false);
			personData.setShowTrainingInfo(true);
			personData.setNewPersonalInfo(true);
			personData.setNewBillsToPay(true);
			personData.setStateProfilePromo(TutorialProfileState.SHOWING);
		}
	}

	public ActivePerson getUser()
	{
		return user;
	}

	public Profile getProfile()
	{
		return profile;
	}

	public Map<String, BasketIndetifierType> getAllowedTypes()
	{
		return allowedTypes;
	}

	/**
	 * @return список информации по связанным услугам.
	 * @throws BusinessException
	 */
	public Map<String, List<LinkInfo>> getLinks() throws BusinessException
	{
		Map<String, List<LinkInfo>> links = new HashMap<String, List<LinkInfo>>();
		for (PersonDocument doc : user.getPersonDocuments())
		{
			if (!doc.isDocumentIdentify())
				continue;
			String docType = doc.getDocumentType().name();
			links.put(docType, invoiceSubscriptionService.getLinkInfoForUserDocument(user.getLogin().getId(), docType));
		}

		return links;
	}

	/**
	 * Возвращает мобильные телефоны пользователя
	 * @return
	 */
	public Collection<String> getMobilePhones() throws BusinessException
	{
		return Collections.singletonList(user.getMobilePhone());
	}

	public void setUnsavedData(Map<String, Object> unsavedData)
	{
		this.unsavedData = unsavedData;
	}

	public Map<String, Object> getOriginalData()
	{
		return originalData;
	}

	public void save() throws BusinessException
	{
		updatePerson();
		//очищаем кеш справочников
		XmlEntityListCacheSingleton.getInstance().clearCache(user, Person.class);
		//При редиктировании своих данных клиентом, необходимо сохранить данные как в основной базе, так и в теневой
		personService.update(user, null);
		if (personService.getPersonMode(user.getId()) == PersonOperationMode.shadow)
			personService.update(user, MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME);
		profileService.update(profile);
		//проносим изменения региона в ЦСА
		Long csaProfileId = AuthenticationContext.getContext().getCsaProfileId();
		if (csaProfileId!=null)
			RegionHelper.updateCsaRegion(csaProfileId, RegionHelper.getParentRegion(profile));
	}

	public ConfirmableObject getConfirmableObject()
	{
		return new PersonalSettings(user.getChangedFields(unsavedData));
	}

	protected void saveConfirm() throws BusinessException
	{
		save();
	}

	public void rememberOriginalPerson()
	{
		if (user.getCreationType() != CreationType.UDBO)
		{
			originalData.put("email",user.getEmail());
			originalData.put("homePhone",user.getHomePhone());
			originalData.put("jobPhone",user.getJobPhone());
		}

		originalData.put("SNILS",user.getSNILS());

		if (profileRegion != null)
		{
			originalData.put("regionId",profileRegion.getId());
		}
	}

	public void updatePerson() throws BusinessException
	{
		if (user.getCreationType() != CreationType.UDBO)
		{
			user.setHomePhone((String) unsavedData.get("homePhone"));
			user.setJobPhone((String) unsavedData.get("jobPhone"));
			UserContactInfo contactInfo = new UserContactInfo();
			contactInfo.setHomePhone((String) unsavedData.get("homePhone"));
			contactInfo.setJobPhone((String) unsavedData.get("jobPhone"));
			PersonSettingsManager.savePersonData(PersonSettingsManager.USER_CONTACTS_KEY, contactInfo);
		}

		UserDocumentService.get().resetUserDocument(user.getLogin(), DocumentType.SNILS, (String) unsavedData.get("SNILS"));
		Long regionId = (Long) unsavedData.get("regionId");
		if (RegionHelper.isOneRegionSelected(regionId))
		{
			Region region = simpleService.findById(Region.class, regionId);
			if (region == null)
				throw new BusinessException("Не найден регион с id = " + regionId);
			PersonSettingsManager.savePersonData(PersonSettingsManager.USER_REGION_KEY, region);
			profile.selectRegion(region);
			PersonContext.getPersonDataProvider().getPersonData().setCurrentRegion(region);
		}
		else
		{
			profile.selectRegion(null);
			PersonContext.getPersonDataProvider().getPersonData().setCurrentRegion(null);
			PersonSettingsManager.savePersonData(PersonSettingsManager.USER_REGION_KEY, RegionHelper.createAllRegion());
		}

	}

	/**
	 * Отправить СМС сообщение с информацией о том, что были изменены настройки персональных параметров
	 * @throws BusinessException Ошибка при работе с бизнес-сервисом
	 */
	public void sendSmsNotification(Map<String, Object> changedFields) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (!changedFields.isEmpty()){
				StringBuilder fields = new StringBuilder(" ");
				int i = 0;
				for (String key : changedFields.keySet())
				{
					fields.append(key);
					i++;
					if (i != changedFields.size())
						fields.append(", ");
				}

				IKFLMessage ikflMessage = MessageComposer.buildInformingSmsMessage(user.getLogin().getId(), "com.rssl.iccs.user.sms.change-user-settings");
				ikflMessage.setText( ikflMessage.getText() + fields + "." );
				messagingService.sendSms(ikflMessage);
			}
		}
		catch (IKFLMessagingException ex)
		{
			throw new BusinessException("Не удалось отправить SMS сообщение с подтверждением операции", ex);
		}
		catch (IKFLMessagingLogicException ex)
		{
			throw new BusinessLogicException(ex.getMessage(), ex);
		}
	}

	/**
	 * Получение региона профиля
	 * @return Регион
	 */
	public Region getProfileRegion()
	{
		return profileRegion;
	}
}
