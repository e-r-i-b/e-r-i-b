package com.rssl.phizic.web.client.userprofile;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;
import com.rssl.phizic.business.messaging.info.SubscriptionService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.profile.*;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.operations.userprofile.UpdatePersonDataOperation;
import com.rssl.phizic.userSettings.SettingsProcessor;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.web.security.LoginStageActionSupport;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author koptyaev
 * @ created 29.05.14
 * @ $Author$
 * @ $Revision$
 */
public class UpdatePersonDataAction  extends LoginStageActionSupport
{
	private static final ProfileService profileService = new ProfileService();
	private static final PersonService personService = new PersonService();
	private static final SubscriptionService subscriptionService = new SubscriptionService();
	private static final RegionDictionaryService regionService = new RegionDictionaryService();
	private final UserDocumentService userDocumentService = UserDocumentService.get();

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		updateRegion();
		updateUserProperties(getAuthenticationContext());
		updateConfirmType(getAuthenticationContext());
		updateContactInfo();
		updateUserDocuments();
		completeStage();
		return null;
	}

	private void updateUserProperties(AuthenticationContext authContext) throws BusinessException
	{
		final Boolean useInternetSecurity = (Boolean)PersonSettingsManager.getPersonData(PersonSettingsManager.NEED_USE_INTERNET_SECURITY_KEY);
		if(useInternetSecurity != null)
		{
			UserPropertiesConfig.processUserSettingsWithoutPersonContext(authContext.getLogin(), new SettingsProcessor<Void>()
			{
				public Void onExecute(UserPropertiesConfig userProperties)
				{
					userProperties.setUseInternetSecurity(useInternetSecurity);
					return null;
				}
			});
		}

		final Boolean showContactsMessage = (Boolean)PersonSettingsManager.getPersonData(PersonSettingsManager.SHOW_CONTACTS_MESSAGE_KEY);
		if(showContactsMessage != null)
		{
			UserPropertiesConfig.processUserSettingsWithoutPersonContext(authContext.getLogin(), new SettingsProcessor<Void>()
			{
				public Void onExecute(UserPropertiesConfig userProperties)
				{
					userProperties.setShowContactsMessage(showContactsMessage);
					return null;
				}
			});
		}
	}

	private void updateRegion() throws BusinessException
	{
		CommonLogin login = getAuthenticationContext().getLogin();
		Profile profile = profileService.findByLogin(login);
		Region regionCode = (Region)PersonSettingsManager.getPersonData(PersonSettingsManager.USER_REGION_KEY);
		if (regionCode != null)
		{
			Region region = regionService.findBySynchKey((String)regionCode.getSynchKey());
			profile.selectRegion(region);
		}
		else
		{
			profile.selectRegion(null);
		}
		profileService.update(profile);
	}

	private void updateConfirmType(AuthenticationContext authContext) throws BusinessException
	{
		UserConfirmStrategySettings userConfirmStrategySettings = (UserConfirmStrategySettings)PersonSettingsManager.getPersonData(PersonSettingsManager.CONFIRM_STRATEGY_SETTINGS_DATA_KEY);
		if(userConfirmStrategySettings == null)
			return;

		UpdatePersonDataOperation operation = new UpdatePersonDataOperation();
		operation.initialize(authContext, userConfirmStrategySettings);
		operation.updateConfirmationSettings();
	}

	private void updateContactInfo() throws BusinessException
	{
		CommonLogin login = getAuthenticationContext().getLogin();
		//вытащим из хранилища информацию
		UserContactInfo contactInfo = (UserContactInfo)PersonSettingsManager.getPersonData(PersonSettingsManager.USER_CONTACTS_KEY);
		UserSubscriptionInfo subscriptionInfo = (UserSubscriptionInfo)PersonSettingsManager.getPersonData(PersonSettingsManager.USER_SUBSCRIPTION_KEY);
		//обновим домашний и рабочий телефоны, хранящиеся в персоне
		if(contactInfo != null)
		{
			ActivePerson person = personService.findByLoginId(login.getId());
			person.setJobPhone(contactInfo.getJobPhone());
			person.setHomePhone(contactInfo.getHomePhone());
			personService.update(person);
		}
		//обновим мобилный телефон и почту
		if (subscriptionInfo != null)
		{
			PersonalSubscriptionData subscriptionData = subscriptionService.findPersonalData(login);
			if (subscriptionData == null)
			{
				subscriptionData = new PersonalSubscriptionData();
				subscriptionData.setLogin(login);
			}
			subscriptionData.setEmailAddress(subscriptionInfo.getEmailAddress());
			subscriptionData.setMailFormat(subscriptionInfo.getMailFormat());
			subscriptionService.changePersonalData(subscriptionData);
		}
	}

	private void updateUserDocuments() throws BusinessException
	{
		CommonLogin login = getAuthenticationContext().getLogin();
		List<UserDocument> documents = userDocumentService.getUserDocumentByLogin(login.getId());
		for (DocumentType documentType: DocumentType.values())
		{
			UserDocument userDocument = (UserDocument)PersonSettingsManager.getPersonData(PersonSettingsManager.USER_DOCUMENTS_KEY + documentType.name());
			if (userDocument == null)
				continue;
			userDocument.setId(null);
			for (UserDocument document : documents) //квадратично, но их всего 3
			{
				if (document.getDocumentType() == documentType)
				{
					userDocument.setId(document.getId());
				}
			}
			userDocument.setLoginId(login.getId());
			userDocumentService.addOrUpdate(userDocument);
		}
	}
}
