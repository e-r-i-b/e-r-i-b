package com.rssl.auth.csa.back.protocol.handlers.profile;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.protocol.handlers.ResponseBuilderHelper;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.ProfileHistory;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.LockMode;

import java.util.List;

/**
 * @author mihaylov
 * @ created 10.11.13
 * @ $Author$
 * @ $Revision$
 */
public abstract class FindOrCreateProfileInformationRequestProcessorBase extends RequestProcessorBase
{

	/**
	 * Поиск профиля по шаблону.
	 * 1. Ищем профиль клиента.
	 * 2. Если не нашли профиль, то ищем историю профиля и возвращаем профиль к которому привязана история.
	 * @param template - шаблон для поиска.
	 * @return профиль клиента
	 * @throws Exception
	 */
	protected Profile findProfileByTemplate(Profile template) throws Exception
	{
		Profile profile = Profile.getByTemplate(template,false);

		if(profile == null)
		{
			ProfileHistory profileHistory = ProfileHistory.findHistoryByProfileTemplate(template);
			if(profileHistory != null)
				profile = Profile.findById(profileHistory.getProfileId(), LockMode.NONE);
		}
		return profile;
	}

	/**
	 * Собрать результат запроса о профиле и его истории
	 * @param profile найденный профиль
	 * @return информация о профиле и его истории
	 * @throws Exception
	 */
	protected ResponseInfo fillProfileWithHistoryResponseInfo(Profile profile) throws Exception
	{
		ResponseBuilderHelper responseBuilder = getSuccessResponseBuilder();
		addProfileInformation(responseBuilder,profile);

		List<ProfileHistory> profileHistoryList = ProfileHistory.findHistoryForProfile(profile);
		addProfileHistoryInformation(responseBuilder,profileHistoryList);

		addNodeInfo(responseBuilder, profile);

		return responseBuilder.end().getResponceInfo();
	}

	protected void addProfileInformation(ResponseBuilderHelper responseBuilder, Profile profile) throws Exception
	{
		responseBuilder.openTag(Constants.PROFILE_INFO_TAG)
			.addParameter(Constants.SURNAME_TAG, profile.getSurname())
			.addParameter(Constants.FIRSTNAME_TAG, profile.getFirstname())
			.addParameterIfNotEmpty(Constants.PATRNAME_TAG, profile.getPatrname())
			.addParameter(Constants.PASSPORT_TAG, profile.getPassport())
			.addParameter(Constants.BIRTHDATE_TAG, profile.getBirthdate())
			.addParameter(Constants.TB_TAG, profile.getTb());
		responseBuilder.closeTag();
	}

	protected void addProfileHistoryInformation(ResponseBuilderHelper responseBuilder, List<ProfileHistory> profileHistoryList) throws Exception
	{
		if(CollectionUtils.isEmpty(profileHistoryList))
			return;

		responseBuilder.openTag(Constants.PROFILE_HISTORY_TAG);
		for(ProfileHistory profileHistory: profileHistoryList)
		{
			responseBuilder.openTag(Constants.PROFILE_HISTORY_INFO_TAG)
				.addParameter(Constants.SURNAME_TAG, profileHistory.getSurname())
				.addParameter(Constants.FIRSTNAME_TAG, profileHistory.getFirstname())
				.addParameterIfNotEmpty(Constants.PATRNAME_TAG, profileHistory.getPatrname())
				.addParameter(Constants.PASSPORT_TAG, profileHistory.getPassport())
				.addParameter(Constants.BIRTHDATE_TAG, profileHistory.getBirthdate())
				.addParameter(Constants.TB_TAG, profileHistory.getTb());
			responseBuilder.closeTag();
		}
		responseBuilder.closeTag();
	}

	protected void addNodeInfo(ResponseBuilderHelper responseBuilder, Profile profile) throws Exception
	{
		ProfileNode profileNode = Utils.getActiveProfileNode(profile.getId(), CreateProfileNodeMode.CREATION_ALLOWED_FOR_ALL_NODES);
		responseBuilder.openTag(Constants.NODE_INFO_TAG)
			.addParameter(Constants.NODE_ID_TAG, profileNode.getNode().getId());
		responseBuilder.closeTag();
	}
}
