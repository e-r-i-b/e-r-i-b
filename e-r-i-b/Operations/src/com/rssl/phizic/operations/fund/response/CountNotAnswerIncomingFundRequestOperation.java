package com.rssl.phizic.operations.fund.response;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fund.sender.FundSenderResponseService;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author saharnova
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 * Операция для подсчета количества неотвеченных входящих запросов краудгифтинга у отправителя средств
 */

public class CountNotAnswerIncomingFundRequestOperation extends OperationBase
{
	private static final FundSenderResponseService fundRequestService = new FundSenderResponseService();

	private Integer count;

	/**
	 * Возвращает количество неотвеченных входящих запросов краудгифтинга у отправителя средств
	 * @return количество запросов
	 */
	public Integer getCount()
	{
		return count;
	}

	/**
	 * Инициализация операции - подсчет количества запросов
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize() throws BusinessException, BusinessLogicException
	{

		try
		{
			Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			UserInfo userInfo = PersonHelper.buildUserInfo(person);
			Document document = CSABackRequestHelper.sendGetProfileHistoryInfo(userInfo);
			List<UserInfo> userInfos = CSABackResponseSerializer.getHistoryUserInfoList(document);
			if (CollectionUtils.isEmpty(userInfos))
				throw new BusinessException("История профиля не может быть пустой");
			Integer countNotAnswer = 0;
			for (UserInfo info : userInfos)
			{
				countNotAnswer += fundRequestService.getNotAnsweredCount(info.getSurname(), info.getFirstname(), info.getPatrname(), info.getBirthdate(), info.getTb(), info.getPassport());
			}
			count = countNotAnswer;
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
	}
}
