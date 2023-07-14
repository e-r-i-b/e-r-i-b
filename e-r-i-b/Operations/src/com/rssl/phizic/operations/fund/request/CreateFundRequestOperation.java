package com.rssl.phizic.operations.fund.request;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.fund.initiator.FundInitiatorResponse;
import com.rssl.phizic.business.fund.initiator.FundRequest;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.fund.Constants;
import com.rssl.phizic.common.types.fund.FundRequestState;
import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.fund.ExternalIdImpl;
import com.rssl.phizic.gate.fund.ResponseExternalIdImpl;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.ErmbProfile;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author osminin
 * @ created 25.11.14
 * @ $Author$
 * @ $Revision$
 *
 * —оздание запроса на сбор средств
 */
public class CreateFundRequestOperation extends OperationBase
{
	private FundRequest request = new FundRequest();
	private List<FundInitiatorResponse> responses = new ArrayList<FundInitiatorResponse>();
	private String errorFieldKey = null;
	private String date = null;

	/**
	 * —охранение запроса и ответов в базу.
	 * @throws BusinessException
	 */
	public void save() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.save(request);
					for (FundInitiatorResponse response : responses)
					{
						response.setRequestId(request.getId());
						session.save(response);
					}
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * —генерировать по данным формы список ответов и запрос на сбор средств в блоке инициатора.
	 * @param data - formData
	 */
	public void fillOperationByFormData(Map<String, Object> data) throws BusinessException, BusinessLogicException
	{
		//провер€ем врем€ закрыти€. ќно не должно превышать врем€ жизни запроса.
		Date closeDate = (Date) data.get("closeDate");
		String lifeTime = ConfigFactory.getConfig(MobileApiConfig.class).getProperty(MobileApiConfig.LIFETIME_REQUEST_ON_FUNDRAISER);
		Calendar deadLine = DateHelper.addDays(Calendar.getInstance(), Integer.valueOf(lifeTime));
		if (closeDate != null)
		{
			if (deadLine.compareTo(DateHelper.toCalendar(closeDate)) < 0)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				String message = "ѕланова€ дата закрыти€ запроса превышает врем€ жизни запроса на сбор средств. ƒата должна быть в диапазоне с " + dateFormat.format(Calendar.getInstance().getTime())+ " по " + dateFormat.format(deadLine.getTime());
				errorFieldKey = "closeDate";
				throw new BusinessLogicException(message);
			}
		}
		else
		{
			request.setExpectedClosedDate(deadLine);
		}

		//проверка карты зачислени€
		Long cardLinkId = (Long) data.get("resource");

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		CardLink cardLink = personData.getCard(cardLinkId);

		if (cardLink == null)
		{
			throw new BusinessLogicException(" арта по указанному номеру не найдена.");
		}
		if (!cardLink.getCurrency().compare(MoneyUtil.getNationalCurrency()))
		{
			throw new BusinessLogicException("¬алюта карты зачислени€ должна быть рублем.");
		}

		//убираем дубли в номерах телефонов получателей.
		List<String> numbers = getUniquePhoneNumbers(data);

		fillRequest(data, numbers.size(), cardLink.getNumber());
		fillResponses(numbers);
	}

	/**
	 * »нициализаци€ данных дл€ проверки лимита
	 * @return остаток по лимиту
	 * @throws BusinessException
	 */
	public Integer initLimitData() throws BusinessException
	{
		Integer accumulated = null;
		Calendar maxDate = null;
		try
		{
			Query query = this.createQuery("getCountSendersForDay");
			query.setParameter("login_id", PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
			Object[] result =  query.executeUnique();
			accumulated = (Integer)result[0];
			maxDate = (Calendar)result[1];

		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}

		if (maxDate != null)
		{
			maxDate.add(Calendar.DAY_OF_YEAR, 1);
			date = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(maxDate.getTime());
		}
		Integer limit = Integer.valueOf(ConfigFactory.getConfig(MobileApiConfig.class).getProperty(MobileApiConfig.FUND_SENDERS_COUNT_DAILY_LIMIT));
		return accumulated != null ? limit - accumulated : limit;
	}

	/**
	 * @return ƒата последнего запроса
	 */
	public String getDate()
	{
		return date;
	}

	/**
	 * ѕолучение сущности запроса на сбор средств.
	 * @return запрос на сбор средств.
	 */
	public FundRequest getRequest()
	{
		return request;
	}

	private void fillRequest(Map<String, Object> data, int phoneCounter, String cardNumber) throws BusinessException, BusinessLogicException
	{
		request.setCreatedDate(Calendar.getInstance());
		request.setExternalId(ExternalIdImpl.generateExternalId());
		request.setState(FundRequestState.SYNC_OPEN);
		request.setSendersCount(phoneCounter);
		request.setLoginId(PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
		request.setMessage((String) data.get("message"));
		request.setRequiredSum((BigDecimal) data.get("requiredSum"));
		request.setReccomendSum((BigDecimal) data.get("reccomendSum"));
		request.setResource(cardNumber);
		request.setInitiatorPhones(getInitiatorPhones());

		Date closeDate = (Date) data.get("closeDate");
		if (closeDate != null)
			request.setExpectedClosedDate(DateHelper.toCalendar(closeDate));
	}

	private void fillResponses(List<String> listPhones)
	{
		//создаем ответ в блоке инициатора по каждому номеру получает€л.
		for (String phone : listPhones)
		{
			responses.add(createResponse(phone));
		}
	}

	private FundInitiatorResponse createResponse(String phoneNumber)
	{
		FundInitiatorResponse response = new FundInitiatorResponse();

		response.setCreatedDate(Calendar.getInstance());
		response.setPhone(phoneNumber);
		response.setState(FundResponseState.NOT_READ);
		response.setExternalId(ResponseExternalIdImpl.generateExternalId(phoneNumber));

		return response;
	}

	private List<String> getUniquePhoneNumbers(Map<String, Object> data)
	{
		String[] phoneNumbers = ((String) data.get("phones")).split(Constants.INITIATOR_PHONES_DELIMITER);
		List<String> resultNumbers = new ArrayList<String>();
		for (String phone : phoneNumbers)
		{
			if (!resultNumbers.contains(phone))
			{
				resultNumbers.add(phone);
			}
		}
		return resultNumbers;
	}

	/**
	 * ѕолучение всех номеров телефонов инициатора запроса.
	 * @return —трока, содержаща€ номера телефонов через зап€тую.
	 */
	private String getInitiatorPhones() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

		ErmbProfile ermbProfile = ErmbHelper.getErmbProfileByLogin(personData.getLogin());
		if (ermbProfile != null && ermbProfile.isServiceStatus())
		{
			String phone = ermbProfile.getMainPhoneNumber();
			return PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phone);
		}

		Collection<String> phones = personData.getPhones(null, Boolean.FALSE);

		StringBuilder phonesInString = new StringBuilder(100);
		Iterator<String> iterator = phones.iterator();
		phonesInString.append(PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(iterator.next()));
		while (iterator.hasNext())
		{
			phonesInString.append(Constants.INITIATOR_PHONES_DELIMITER);
			phonesInString.append(PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(iterator.next()));
		}
		return phonesInString.toString();
	}

	public String getErrorFieldKey()
	{
		return errorFieldKey;
	}

}
