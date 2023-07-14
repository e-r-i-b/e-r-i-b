package com.rssl.phizic.monitoring.fraud.operations;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.auth.BlockingReasonType;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.MessagingHelper;
import com.rssl.phizic.monitoring.fraud.ClientTransactionCompositeId;
import com.rssl.phizic.monitoring.fraud.messages.jaxb.generated.Request;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

import static com.rssl.phizic.monitoring.fraud.Constants.*;

/**
 * Операция блокировки профиля клиента
 *
 * @author khudyakov
 * @ created 23.06.15
 * @ $Author$
 * @ $Revision$
 */
public class BlockClientOperation
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final SecurityService securityService = new SecurityService();
	private static final PersonService personService = new PersonService();

	private Request request;
	private String transactionId;
	private ClientTransactionCompositeId compositeId;

	public BlockClientOperation(Request request) throws BusinessException
	{
		this.request = request;
		this.transactionId  = request.getIdentificationData().getClientTransactionId();
		this.compositeId    = new ClientTransactionCompositeId(transactionId);
	}

	/**
	 * Блокировать профиль клинта
	 */
	public void block() throws BusinessException, BusinessLogicException
	{
		try
		{
			if (compositeId.getNodeProfileId() != null)
			{
				//при блокировке по событию из ЦСА идентификатор клиента в блоке не известен,
				//поэтому блокируем только профиль ЦСА
				Person person = personService.findById(compositeId.getNodeProfileId());
				if (person == null)
				{
					throw new BusinessException(String.format(CLIENT_NOT_FOUND_ERROR_MESSAGE, compositeId.getNodeProfileId(), transactionId));
				}

				securityService.lock(person.getLogin(), Calendar.getInstance().getTime(), null, BlockingReasonType.system, request.getRiskResult().getTriggeredRule().getComments(), null, null);
				MessagingHelper.sendMessage(person.getLogin().getId(), CLIENT_BLOCK_SMS_KEY);

				log.info(String.format(BLOCK_CLIENT_PROFILE_BLOCKING_INFO_MESSAGE, compositeId.getNodeProfileId(), transactionId));
			}

			CSABackRequestHelper.sendLockProfileCHG071536Rq(compositeId.getCSAProfileId(), Calendar.getInstance(), null, request.getRiskResult().getTriggeredRule().getComments(), LOCKER_FIO);
			log.info(String.format(CSA_CLIENT_PROFILE_BLOCKING_INFO_MESSAGE, compositeId.getCSAProfileId(), transactionId));
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessException(e);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}
}
