package com.rssl.phizic.limits.handlers;

import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.common.types.limits.Constants;
import com.rssl.phizic.common.types.limits.OperationType;
import com.rssl.phizic.limits.servises.DocumentTransaction;
import com.rssl.phizic.limits.servises.Profile;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Процессор для операции записи транзакции
 */
public class AddTransactionProcessor extends TransactionProcessorBase
{
	private static final String XSD_SCHEME_PATH = "com/rssl/phizic/limits/handlers/schemes/addTransactionRq.xsd";

	/**
	 * ctor
	 */
	public AddTransactionProcessor()
	{
		super(XSD_SCHEME_PATH);
	}

	public void doProcess(Element root) throws Exception
	{
		Profile profile = getProfileInfo(root);
		//Получаем из запроса данные о транзакции
		Element transaction = XmlHelper.selectSingleNode(root, Constants.TRANSACTION_TAG);
		String externalId = XmlHelper.getSimpleElementValue(transaction, Constants.EXTERNAL_ID_TAG);
		String documentExternalId = XmlHelper.getSimpleElementValue(transaction, Constants.DOCUMENT_EXTERNAL_ID_TAG);
		Element amount = XmlHelper.selectSingleNode(transaction, Constants.AMOUNT_TAG);
		String operationDateValue = XmlHelper.getSimpleElementValue(transaction, Constants.OPERATION_DATE_TAG);
		Calendar operationDate = XMLDatatypeHelper.parseDateTime(operationDateValue);
		ChannelType channelType = ChannelType.valueOf(XmlHelper.getSimpleElementValue(transaction, Constants.CHANNEL_TYPE_TAG));
		String limitsInfo = createLimitsInfo(XmlHelper.selectSingleNode(transaction, Constants.LIMITS_TAG));
		//создаем транзакцию
		DocumentTransaction documentTransaction = new DocumentTransaction(externalId, documentExternalId, profile.getId(), getAmount(amount), getCurrency(amount), operationDate, OperationType.ADD, channelType, limitsInfo);
		documentTransaction.add();
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

	/**
	 * Построить информацию о лимитах в строку вида limitType,restrictionType,externalGroupRiskId;
	 * @param limits dom-описание лимитов
	 * @return информация о лимитах
	 */
	private String createLimitsInfo(Element limits)
	{
		StringBuilder limitsInfo = new StringBuilder();
		NodeList listLimit = limits.getElementsByTagName(Constants.LIMIT_TAG);

		for (int i = 0; i < listLimit.getLength(); i++)
		{
			Element limit = (Element) listLimit.item(i);

			String limitType = XmlHelper.getSimpleElementValue(limit, Constants.LIMIT_TYPE_TAG);
			String restrictionType = XmlHelper.getSimpleElementValue(limit, Constants.RESTRICTION_TYPE_TAG);
			String externalGroupRiskId = XmlHelper.getSimpleElementValue(limit, Constants.EXTERNAL_GROUP_RISK_ID_TAG);

			limitsInfo.append(limitType).append(Constants.LIMIT_PROPERTY_DELIMITER);
			limitsInfo.append(restrictionType).append(Constants.LIMIT_PROPERTY_DELIMITER);

			if (StringHelper.isNotEmpty(externalGroupRiskId))
			{
				limitsInfo.append(externalGroupRiskId);
			}

			limitsInfo.append(Constants.LIMIT_DELIMITER);
		}

		return limitsInfo.toString();
	}

	private BigDecimal getAmount(Element amount)
	{
		if (amount == null)
		{
			return null;
		}
		String value = XmlHelper.getSimpleElementValue(amount, Constants.AMOUNT_VALUE_TAG);
		return new BigDecimal(value);
	}

	private String getCurrency(Element amount)
	{
		if (amount == null)
		{
			return null;
		}
		return XmlHelper.getSimpleElementValue(amount, Constants.AMOUNT_CUR_TAG);
	}
}
