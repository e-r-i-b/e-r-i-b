package com.rssl.phizic.business.loanclaim.etsm;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.ikfl.crediting.CRMMessageService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.etsm.offer.OfferEribPrior;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.InitiateConsumerProductOfferRq;
import org.hibernate.Session;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Moshenko
 * @ created 22.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ETSMLoanOfferUpdaterRelease19
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final SimpleService simpleService = new SimpleService();
	private static final PersonService personService = new PersonService();
	private static final CRMMessageService crmMessageService = new CRMMessageService();
	private final static PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final JAXBContext jaxbContext;

	private static final Class[] CLASSES = new Class[]
			{
					InitiateConsumerProductOfferRq.class
			};

	public ETSMLoanOfferUpdaterRelease19()
	{
		try
		{
			jaxbContext = JAXBContext.newInstance(CLASSES);
		}
		catch (JAXBException e)
		{
			throw new InternalErrorException("[ETSM] Сбой на загрузке JAXB-контекста", e);
		}
	}

	public void update(String requestXML) throws BusinessException, BusinessLogicException
	{
		//  Разбираем запрос
		InitiateConsumerProductOfferRq request = readRequest(requestXML);
		// Создаем/сохнаяем оферту в бд.
		Pair<Integer,String>  status = saveOffer(request);
		// Отсылаем оповещение
		try
		{
			crmMessageService.sendOfferTicket(request.getRqUID(),request.getRqTm(),status.getFirst(), status.getSecond());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private Pair<Integer,String> saveOffer(InitiateConsumerProductOfferRq request)
	{
		// Проверяем наличие необходимых элементов  для создания оферты
		//operUID(25)  без 2х последних знаков + 00r19 + 2 последних знака operUID.
		String operUID = request.getOperUID();
		//Исходный  operUID документа
		String currentOperUID = operUID.substring(0,25) +  operUID.substring(operUID.length() - 2) ;

		ExtendedLoanClaim claim = null;
		try
		{
			claim = businessDocumentService.findExtendedLoanClaimByUID(currentOperUID);
		}
		catch (BusinessException e)
		{
			return getStatus(-1, e.getMessage());
		}
		if (claim == null)
		     return getStatus(1, "Кредитная заявка с OperUID:" + operUID + " не найдена.");
		Long ownerLoginId = claim.getOwnerLoginId();
		if (ownerLoginId == null)
			return getStatus(1, "В кредитной заявке с id:" + claim.getId() + " не указан параметр OwnerLoginId");
		Person person = null;
		try
		{
			person = personService.findByLogin(ownerLoginId);
		}
		catch (BusinessException e)
		{
			return getStatus(-1, e.getMessage());
		}
		if (person == null)
			return getStatus(1, "По кредитной заявке с id:" + claim.getId() + " не удалось определить владельца.");

		InitiateConsumerProductOfferRq.Offer offer = request.getOffer();
		List<OfferEribPrior> ermbOfferList = new ArrayList<OfferEribPrior>();
		if (offer != null)

			for (InitiateConsumerProductOfferRq.Offer.Alternative alternative:offer.getAlternatives())
			{
				OfferEribPrior ermbOffer = new OfferEribPrior();
				ermbOffer.setRqUid(request.getRqUID());
				ermbOffer.setClaimId(claim.getId());
				ermbOffer.setClientLoginId(ownerLoginId);
				ermbOffer.setAltAmount(alternative.getAltAmount());
				ermbOffer.setAltCreditCardLimit(alternative.getAltCreditCardlimit());
				ermbOffer.setAltAnnuityPayment(alternative.getAltAnnuitentyPayment());
				ermbOffer.setAltFullLoanCost(alternative.getAltFullLoanCost());
				ermbOffer.setAltInterestRate(alternative.getAltInterestRate());
				ermbOffer.setAltPeriod(alternative.getAltPeriodM());
				ermbOffer.setState("ACTIVE");
				ermbOffer.setApplicationNumber(request.getApplicationNumber());
				ermbOffer.setClientCategory(request.getOffer().getClientCategory());
				ermbOffer.setOfferDate(request.getRqTm());
				ermbOfferList.add(ermbOffer);
			}
		return updateOffer(ermbOfferList, claim);
	}

	private Pair<Integer,String> updateOffer(final List<OfferEribPrior> ermbOfferList,final ExtendedLoanClaim claim )
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					//обновляем статус заявки
					updateState(claim);
					simpleService.addOrUpdate(claim);
					//сохраняем оферты
					simpleService.addOrUpdateList(ermbOfferList);
					return null;
				}
			}
			);
		}
		catch (BusinessLogicException e)
		{
			return getStatus(1, e.getMessage());
		}
		catch (Exception e)
		{
			return getStatus(-1, e.getMessage());
		}
		return getStatus(0,"");
	}

	/**
	 *  Обновляем статус докмента на «Одобрено, требуется подтверждение для выдачи».
	 *  Данный переход для заявки выполняется автоматически по факту загрузки для нее из ETSM заявления-оферты.
	 *
	 * @param claim
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected void updateState(ExtendedLoanClaim claim) throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(claim.getFormName()));
		executor.initialize(claim);
		executor.fireEvent(new ObjectEvent(DocumentEvent.APPROVE_MUST_CONFIRM, ObjectEvent.SYSTEM_EVENT_TYPE));
	}

	private Pair<Integer,String> getStatus(int statusCode, String errorDesk)
	{
		Pair<Integer,String> status = new Pair<Integer, String>();
		status.setFirst(statusCode);
		status.setSecond(errorDesk);
		if (StringHelper.isNotEmpty(errorDesk))
			log.error(errorDesk);
		return status;
	}

	private InitiateConsumerProductOfferRq readRequest(String requestXML) throws BusinessException
	{
		try
		{
			// Валидацию по XSD-Схеме здесь не проводим, т.к. валидация - задача прокси-листенера
			Reader reader = new StringReader(requestXML);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return (InitiateConsumerProductOfferRq) unmarshaller.unmarshal(reader);
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}
}
