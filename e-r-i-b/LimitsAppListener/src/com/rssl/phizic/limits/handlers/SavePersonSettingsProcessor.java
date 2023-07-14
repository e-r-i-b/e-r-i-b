package com.rssl.phizic.limits.handlers;

import com.rssl.phizic.common.types.limits.Constants;
import com.rssl.phizic.limits.profile.information.ProfileInformation;
import com.rssl.phizic.limits.servises.Profile;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * @author komarov
 * @ created 27.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class SavePersonSettingsProcessor extends TransactionProcessorBase
{
	private static final String XSD_SCHEME_PATH = "com/rssl/phizic/limits/handlers/schemes/savePersonSettingsRq.xsd";

	/**
	 * ctor
	 */
	public SavePersonSettingsProcessor()
	{
		super(XSD_SCHEME_PATH);
	}

	@Override
	protected void doProcess(Element root) throws Exception
	{
		Profile profile = getProfileInfo(root);
		String informationType = XmlHelper.getSimpleElementValue(root, Constants.PERSON_SETTING_INFORMATION_TYPE_TAG);
		String data = XmlHelper.getSimpleElementValue(root, Constants.PERSON_SETTING_DATA_TAG);
		ProfileInformation profileInformation = new ProfileInformation(profile.getId(), informationType, data);
		profileInformation.addOrUpdate();
	}

	private Profile getProfileInfo(Element root) throws Exception
	{
		//Получаем из запроса данные о клиенте
		Element profileInfo = XmlHelper.selectSingleNode(root, Constants.PROFILE_INFO_TAG);

		String firstName = XmlHelper.getSimpleElementValue(profileInfo, Constants.FIRST_NAME_TAG);
		String surName = XmlHelper.getSimpleElementValue(profileInfo, Constants.SUR_NAME_TAG);
		String patrName = XmlHelper.getSimpleElementValue(profileInfo,  Constants.PATR_NAME_TAG);
		String tb = XmlHelper.getSimpleElementValue(profileInfo, Constants.TB_TAG);
		String birthDateValue = XmlHelper.getSimpleElementValue(profileInfo, Constants.BIRTH_DATE_TAG);
		Calendar birthDate = XMLDatatypeHelper.parseDateTime(birthDateValue);

		String passport = XmlHelper.getSimpleElementValue(profileInfo, Constants.PASSPORT_NAME_TAG);

		ProfileInfo info = new ProfileInfo(firstName, surName, patrName, passport, tb, birthDate);
		Profile profile = Profile.findByProfileInfo(info);
		//Если профиль не пришедшим значениям не найден, создаем по ним новый профиль.
		if (profile == null)
		{
			profile = new Profile(info);
			profile.add();
		}

		return profile;
	}
}
