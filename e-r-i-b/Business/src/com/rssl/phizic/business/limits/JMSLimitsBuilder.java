package com.rssl.phizic.business.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.business.limits.users.LimitInfo;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;

import java.util.List;

import static com.rssl.phizic.common.types.limits.Constants.*;

/**
 * Построение сообщений jms для обмена с отдельной базой лимитов
 * @author niculichev
 * @ created 29.01.14
 * @ $Author$
 * @ $Revision$
 */
public class JMSLimitsBuilder
{
	/**
	 * Построить сообщение для фиксации транзакции по лимитам
	 * @param documentInfo информация по транзакции
	 * @param person персона, к которой относится запись
	 * @return jsm сообщение ввиде строки
	 * @throws BusinessException
	 */
	public static String buildCommit(LimitDocumentInfo documentInfo, Person person) throws BusinessException
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag(ADD_TRANSACTION_REQUEST_NAME);
		builder.append(buildProfileInfo(person));       // profileInfo
		builder.append(buildTransaction(documentInfo)); // transaction
		builder.closeEntityTag(ADD_TRANSACTION_REQUEST_NAME);

		return builder.toString();
	}

	/**
	 * Построить сообщение для отката транзакции по лимитам
	 * @param documentInfo информация по транзакции
	 * @return jsm сообщение ввиде строки
	 */
	public static String buildRevert(LimitDocumentInfo documentInfo)
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag(ROLLBACK_TRANSACTION_REQUEST_NAME);
		builder.createEntityTag(EXTERNAL_ID_TAG, documentInfo.getExternalId()) ;
		builder.createEntityTag(DOCUMENT_EXTERNAL_ID_TAG, documentInfo.getDocumentExternalId()) ;
		builder.createEntityTag(OPERATION_DATE_TAG, XMLDatatypeHelper.formatDateTime(documentInfo.getOperationDate()));
		builder.closeEntityTag(ROLLBACK_TRANSACTION_REQUEST_NAME);

		return builder.toString();
	}

	private static String buildTransaction(LimitDocumentInfo info)
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag(TRANSACTION_TAG);
		builder.createEntityTag(EXTERNAL_ID_TAG, info.getExternalId());
		builder.createEntityTag(DOCUMENT_EXTERNAL_ID_TAG, info.getDocumentExternalId());
		builder.append(buildAmount(info.getAmount()));
		builder.createEntityTag(OPERATION_DATE_TAG, XMLDatatypeHelper.formatDateTime(info.getOperationDate()));
		builder.createEntityTag(CHANNEL_TYPE_TAG, info.getChannelType().name());
		builder.append(buildLimits(info.getLimitInfos()));
		builder.closeEntityTag(TRANSACTION_TAG);

		return builder.toString();
	}

	private static String buildAmount(Money money)
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag(AMOUNT_TAG);
		builder.createEntityTag(AMOUNT_VALUE_TAG, money.getDecimal().toString());
		builder.createEntityTag(AMOUNT_CUR_TAG, money.getCurrency().getCode());
		builder.closeEntityTag(AMOUNT_TAG);

		return builder.toString();
	}

	private static String buildProfileInfo(Person person) throws BusinessException
	{
		PersonDocument personDocument = PersonHelper.getPersonPassportWay(person);
		if(personDocument == null)
			throw new IllegalStateException("У персоны нет паспорта way");

		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag(PROFILE_INFO_TAG);
		builder.createEntityTag(FIRST_NAME_TAG, person.getFirstName());
		builder.createEntityTag(SUR_NAME_TAG, person.getSurName());
		builder.createEntityTag(PATR_NAME_TAG, person.getPatrName());
		builder.createEntityTag(PASSPORT_NAME_TAG,
				StringHelper.getEmptyIfNull(personDocument.getDocumentSeries()) + StringHelper.getEmptyIfNull(personDocument.getDocumentNumber()));
		builder.createEntityTag(BIRTH_DATE_TAG, DateHelper.getXmlDateTimeFormat(person.getBirthDay().getTime()));
		builder.createEntityTag(TB_TAG, PersonHelper.getPersonTb(person))                                        ;
		builder.closeEntityTag(PROFILE_INFO_TAG);

		return builder.toString();
	}

	private static String buildLimits(List<LimitInfo> limitInfos)
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag(LIMITS_TAG);

		for(LimitInfo limitInfo : limitInfos)
		{
			builder.openEntityTag(LIMIT_TAG);
			builder.createEntityTag(LIMIT_TYPE_TAG, limitInfo.getLimitType().name());
			builder.createEntityTag(RESTRICTION_TYPE_TAG, limitInfo.getRestrictionType().name());
			if(StringHelper.isNotEmpty(limitInfo.getExternalGroupRisk()))
				builder.createEntityTag(EXTERNAL_GROUP_RISK_ID_TAG, limitInfo.getExternalGroupRisk());
			builder.closeEntityTag(LIMIT_TAG);
		}

		builder.closeEntityTag(LIMITS_TAG);

		return builder.toString();
	}
}
