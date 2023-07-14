package com.rssl.phizic.business.fund.initiator;

import com.rssl.auth.csa.utils.UserInfoHelper;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.common.types.fund.FundRequestState;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.fund.FundConfig;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.Session;
import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с запросами на сбор средств
 */
public class FundRequestService
{
	private static SimpleService simpleService = new SimpleService();

	/**
	 * Получить запрос по идентификатору
	 * @param id идентификатор
	 * @return запрос
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public FundRequest getById(Long id) throws BusinessException, BusinessLogicException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор запроса на сбор средств не может быть null.");
		}
		return simpleService.findById(FundRequest.class, id);
	}

	/**
	 * Получить статус запроса на сбор средств по внешнему идентификатору ответа отправителя
	 * @param externalResponseId внешний идентификатор ответа
	 * @return статус запроса
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public FundRequestInfo getRequestInfoByExternalResponseId(final String externalResponseId) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(externalResponseId))
		{
			throw new IllegalArgumentException("Внешний идентификатор не может быть null.");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<FundRequestInfo>()
			{
				public FundRequestInfo run(Session session) throws Exception
				{
					return (FundRequestInfo) session.getNamedQuery("com.rssl.phizic.business.fund.initiator.FundRequest.getStateByExternalResponseId")
							.setParameter("external_id", externalResponseId)
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
	 * Получить список исходящих запросов по статусу за месяц
	 * @param state статус
	 * @return список запросов
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<FundRequest> getByStateForLifeTime(final FundRequestState state) throws BusinessException, BusinessLogicException
	{
		if (state == null)
		{
			throw new IllegalArgumentException("Статус не может быть null.");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<FundRequest>>()
			{
				public List<FundRequest> run(Session session) throws Exception
				{
					return (List<FundRequest>) session.getNamedQuery("com.rssl.phizic.business.fund.initiator.FundRequest.getByStateAndDate")
							.setParameter("state", state)
							.setParameter("from_date", DateHelper.addDays(Calendar.getInstance(), -ConfigFactory.getConfig(MobileApiConfig.class).getFundLifeTimeInDays()))
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
	 * Закрытие запроса на сбор средств по инициативе инициатора запроса
	 * @param loginId - id логин инициатора
	 * @param requestId - id запроса
	 * @return - количество обновленных строк
	 * @throws BusinessException
	 */
	public Integer closeFundRequest (final Long loginId, final Long requestId) throws BusinessException
	{
		if (loginId == null)
		{
			throw new IllegalArgumentException("Логин клиента не может быть null.");
		}
		if (requestId == null)
		{
			throw new IllegalArgumentException("Идентификатор запроса не может быть null.");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.fund.initiator.FundRequest.close")
							.setParameter("loginId", loginId)
							.setParameter("id", requestId)
							.executeUpdate();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить все запросы, набравшие необходимую сумму
	 * @return список запросов
	 * @throws BusinessException
	 */
	public List<FundRequest> getAccumulated() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<FundRequest>>()
			{
				public List<FundRequest> run(Session session) throws Exception
				{
					return (List<FundRequest>) session.getNamedQuery("com.rssl.phizic.business.fund.initiator.FundRequest.getAccumulated")
							.setParameter("pack_size", ConfigFactory.getConfig(FundConfig.class).getAccumulatedPackSize())
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
	 * Завершить открытие запроса (перевести в статус OPEN, если запрос ожидал синхронизации)
	 * @param id идентификатор запроса
	 * @return 1 - если запрос был обновлен, 0 - если нет
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public int finishOpen(final Long id) throws BusinessException, BusinessLogicException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор запроса на сбор средств не может быть null.");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.fund.initiator.FundRequest.finishOpen")
							.setParameter("id", id)
							.executeUpdate();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Есть ли у клиента с данным номером телефона мАпи про-версии
	 * @param phone номер телефона
	 * @return true - если есть
	 */
	public boolean isContainsPro(String phone) throws BusinessLogicException, BusinessException
	{
		if (StringHelper.isEmpty(phone))
		{
			throw new IllegalArgumentException("Номер телефона не может быть null.");
		}

		UserInfo userInfo = UserInfoHelper.getByPhoneWithoutTb(phone);
		return userInfo != null && getContainsPro(userInfo);
	}

	private boolean getContainsPro(UserInfo userInfo) throws BusinessException, BusinessLogicException
	{
		try
		{
			Document document = CSABackRequestHelper.sendGetContainsProMAPIInfoRq(userInfo);
			return Boolean.valueOf(XmlHelper.getSimpleElementValue(document.getDocumentElement(), RequestConstants.CONTAINS_PRO_MAPI_TAG));
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}
}
