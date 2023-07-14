package com.rssl.phizic.business.fund.sender;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.DepartmentsUtils;
import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.fund.FundConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.fund.Request;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с ответами на запросы сбора средств
 */
public class FundSenderResponseService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static SimpleService simpleService = new SimpleService();

	/**
	 * Создать ответ на запрос сбора средств в блоке отправителя
	 * @param fundRequest запрос на сбор средств
	 * @param initiator ФИО ДУЛ ДР ТБ инициатора
	 * @param sender ФИО ДУЛ ДР ТБ отправителя
	 * @param externalResponseId внешний идентификатор ответа
	 * @param phones телефоны инициатора
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void createResponse(Request fundRequest, GUID initiator, GUID sender, String externalResponseId, String phones) throws BusinessException, BusinessLogicException
	{
		FundSenderResponse response = new FundSenderResponse();

		response.setFirstName(sender.getFirstName());
		response.setSurName(sender.getSurName());
		response.setPatrName(sender.getPatrName());
		response.setBirthDate(sender.getBirthDay());
		response.setTb(sender.getTb());
		response.setPassport(sender.getPassport());
		response.setExternalId(externalResponseId);
		response.setExternalRequestId(fundRequest.getExternalId());
		response.setState(FundResponseState.NOT_READ);
		response.setInitiatorFirstName(initiator.getFirstName());
		response.setInitiatorSurName(initiator.getSurName());
		response.setInitiatorPatrName(initiator.getPatrName());
		response.setInitiatorPassport(initiator.getPassport());
		response.setInitiatorBirthDate(initiator.getBirthDay());
		response.setInitiatorTb(initiator.getTb());
		response.setInitiatorPhones(phones);
		response.setRequestMessage(fundRequest.getMessage());
		response.setRequestState(fundRequest.getState());
		response.setRequiredSum(fundRequest.getRequiredSum());
		response.setReccomendSum(fundRequest.getReccomendSum());
		response.setToResource(fundRequest.getResource());
		response.setClosedDate(fundRequest.getClosedDate());
		response.setExpectedClosedDate(fundRequest.getExpectedClosedDate());
		response.setCreatedDate(fundRequest.getCreatedDate());

		simpleService.add(response);
	}

	/**
	 * Получить количество неотвеченных входящих запросов.
	 * Неотвеченные - незакрытые и не прочитанные запросы.
	 * @param surName фамилия отправителя
	 * @param firstName имя отправителя
	 * @param patrName отчетсво отправителя
	 * @param birthDate дата рождения отправителя
	 * @param tb тербанк отправителя
	 * @param passport ДУЛ отправителя
	 * @return количество неотвеченных
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public int getNotAnsweredCount(final String surName, final String firstName, final String patrName, final Calendar birthDate, final String tb, final String passport) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(surName))
		{
			throw new IllegalArgumentException("Фамилия отправителя не может быть null.");
		}
		if (StringHelper.isEmpty(firstName))
		{
			throw new IllegalArgumentException("Имя отправителя не может быть null.");
		}
		if (birthDate == null)
		{
			throw new IllegalArgumentException("Дата рождения отправителя не может быть null.");
		}
		if (StringHelper.isEmpty(tb))
		{
			throw new IllegalArgumentException("Тербанк отправителя не может быть null.");
		}
		if (StringHelper.isEmpty(passport))
		{
			throw new IllegalArgumentException("Документ, удостоверяющий личность, не может быть null.");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					return (Integer) session.getNamedQuery("com.rssl.phizic.business.fund.sender.FundSenderResponse.getNotAnsweredCount")
							.setParameter("sur_name", surName)
							.setParameter("first_name", firstName)
							.setParameter("patr_name", patrName)
							.setParameter("birth_date", birthDate)
							.setParameter("tb", DepartmentsUtils.getTbBySynonymAndIdentical(tb))
							.setParameter("passport", passport)
							.setParameter("from_date", DateHelper.addMonths(Calendar.getInstance(), -1))
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить заявку по ФИО ДУЛ ДР ТБ с определенной даты, по умолчанию - за месяц
	 * @param surName фамилия отправителя
	 * @param firstName имя отправителя
	 * @param patrName отчетсво отправителя
	 * @param birthDate дата рождения отправителя
	 * @param tb тербанк отправителя
	 * @param passport ДУЛ отправителя
	 * @param fromDate начало периода
	 * @return заявка
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public List<FundSenderResponse> getByUniversalIdAndDate(final String surName, final String firstName, final String patrName, final Calendar birthDate, final String tb, final String passport, Calendar fromDate) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(surName))
		{
			throw new IllegalArgumentException("Фамилия отправителя не может быть null.");
		}
		if (StringHelper.isEmpty(firstName))
		{
			throw new IllegalArgumentException("Имя отправителя не может быть null.");
		}
		if (birthDate == null)
		{
			throw new IllegalArgumentException("Дата рождения отправителя не может быть null.");
		}
		if (StringHelper.isEmpty(tb))
		{
			throw new IllegalArgumentException("Тербанк отправителя не может быть null.");
		}
		if (StringHelper.isEmpty(passport))
		{
			throw new IllegalArgumentException("Документ, удостоверяющий личность, не может быть null.");
		}
		if (fromDate == null)
		{
			return getByUniversalIdAndDateBase(surName, firstName, patrName, birthDate, tb, passport, DateHelper.addMonths(Calendar.getInstance(), -1));
		}
		return getByUniversalIdAndDateBase(surName, firstName, patrName, birthDate, tb, passport, fromDate);
	}

	@SuppressWarnings("MethodWithTooManyParameters")
	private List<FundSenderResponse> getByUniversalIdAndDateBase(final String surName, final String firstName, final String patrName, final Calendar birthDate, final String tb, final String passport, final Calendar fromDate) throws BusinessException, BusinessLogicException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<FundSenderResponse>>()
			{
				public List<FundSenderResponse> run(Session session) throws Exception
				{
					return (List<FundSenderResponse>) session.getNamedQuery("com.rssl.phizic.business.fund.sender.FundSenderResponse.getByUniversalIdAndDate")
							.setParameter("sur_name", surName)
							.setParameter("first_name", firstName)
							.setParameter("patr_name", patrName)
							.setParameter("birth_date", birthDate)
							.setParameter("tb", DepartmentsUtils.getTbBySynonymAndIdentical(tb))
							.setParameter("passport", passport)
							.setParameter("from_date", fromDate)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Закрыть входящий запрос (Устанавливается статус запроса в "закрыт" и дата закрытия)
	 * @param request запрос
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void closeRequest(Request request) throws BusinessException, BusinessLogicException
	{
		if (request == null)
		{
			throw new IllegalArgumentException("Запрос не может быть null.");
		}

		int packSize = ConfigFactory.getConfig(FundConfig.class).getBroadcastCommitPackSize();
		int closeResult = packSize;
		//Выполняем коммиты пачками для того, что бы снизить вероятность одновременного обновления
		//одной и той же записи этим запросом и клиентом-отправителем, решившим изменить ответ на запрос
		while (closeResult == packSize)
		{
			closeResult = getCloseRequestResult(request, packSize);
		}
	}

	/**
	 * Поиск по внешнему идентификатору ответа сбора средств.
	 * @param externalId - идентификатор структуры "ответ".
	 * @return ответ в блоке плательщиика.
	 */
	public FundSenderResponse getByExternalId(final String externalId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<FundSenderResponse>()
			{
				public FundSenderResponse run(Session session) throws Exception
				{
					return (FundSenderResponse) session.getNamedQuery("com.rssl.phizic.business.fund.sender.FundSenderResponse.getByExternalId")
							.setParameter("externalId", externalId)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Установить статус обработки зарпоса Прочитан
	 * @param externalId идентификатор входящего запроса
	 * @return 1- если обновление успешно
	 * @throws BusinessException
	 */
    public int setReadState(String externalId) throws BusinessException
    {
	    if (StringHelper.isEmpty(externalId))
	    {
		    throw new IllegalArgumentException("Идентификатор входящего запроса на сбор средств не может быть null.");
	    }
	    return updateState(externalId, FundResponseState.READ, FundResponseState.NOT_READ, null, null, null);
    }

	/**
	 * Установить статус обработки зарпоса Прочитан
	 * @param externalId идентификатор входящего запроса
	 * @param message Причина отклонения запроса
	 * @return 1- если обновление успешно
	 * @throws BusinessException
	 */
	public int setRejectState(String externalId, String message) throws BusinessException
	{
		if (StringHelper.isEmpty(externalId))
		{
			throw new IllegalArgumentException("Идентификатор входящего запроса на сбор средств не может быть null.");
		}
		if (StringHelper.isEmpty(message))
		{
			throw new IllegalArgumentException("Причина отклонения запроса не может быть null.");
		}
		return updateState(externalId, FundResponseState.REJECT, FundResponseState.READ, null, message, null);
	}

	/**
	 * Установить статус обработки зарпоса Прочитан
	 * @param externalId идентификатор входящего запроса
	 * @param sum сумма перевода
	 * @param message Причина отклонения запроса
	 * @param paymentId идентификатор платежа
	 * @return 1- если обновление успешно
	 * @throws BusinessException
	 */
	public int setSuccessState(String externalId, BigDecimal sum, String message, Long paymentId) throws BusinessException
	{
		if (StringHelper.isEmpty(externalId))
		{
			throw new IllegalArgumentException("Идентификатор входящего запроса на сбор средств не может быть null.");
		}
		if (sum == null)
		{
			throw new IllegalArgumentException("Сумма не может быть null.");
		}
		if (paymentId == null)
		{
			throw new IllegalArgumentException("Идентификатор платежа не меожт быть null.");
		}
		return updateState(externalId, FundResponseState.SUCCESS, FundResponseState.READ, sum, message, paymentId);
	}

	@SuppressWarnings("MethodWithTooManyParameters")
	private int updateState(final String externalId, final FundResponseState newState, final FundResponseState validState, final BigDecimal sum, final String message, final Long paymentId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.fund.sender.FundSenderResponse.updateState");
					executorQuery.setParameter("external_id", externalId)
							.setParameter("valid_state", validState.name())
							.setParameter("new_state", newState.name())
							.setParameter("sum", sum)
							.setParameter("message", message)
							.setParameter("payment_id", paymentId);
					return executorQuery.executeUpdate();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private int getCloseRequestResult(final Request request, final int packSize)
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.fund.sender.FundSenderResponse.closeRequest");
					executorQuery.setParameter("closed_date", request.getClosedDate());
					executorQuery.setParameter("external_id", request.getExternalId());
					executorQuery.setParameter("created_date", request.getCreatedDate());
					executorQuery.setParameter("pack_size", packSize);
					return executorQuery.executeUpdate();
				}
			});
		}
		catch (Exception e)
		{
			log.error("Ошибка при обновлении результатов обработки запросов.", e);
			return packSize;
		}
	}
}