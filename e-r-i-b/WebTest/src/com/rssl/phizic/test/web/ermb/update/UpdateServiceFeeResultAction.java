package com.rssl.phizic.test.web.ermb.update;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.MultiInstancePersonService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.messaging.ermb.ErmbJndiConstants;
import com.rssl.phizic.messaging.ermb.TransportMessageSerializer;
import com.rssl.phizic.synchronization.types.*;
import com.rssl.phizic.utils.*;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Заглушка отправки сообщения списания абонентской платы (СОС - ЕРИБ)
 * @author Puzikov
 * @ created 04.06.14
 * @ $Author$
 * @ $Revision$
 */

public class UpdateServiceFeeResultAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";

	private static final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();
	private static final MultiInstancePersonService personService = new MultiInstancePersonService();

	private static final int TRANSACTION_CODE_POSITION_BEGIN = 2 - 1;
	private static final int TRANSACTION_CODE_POSITION_END = 6;
	private static final int CARD_NUMBER_POSITION_BEGIN = 35 - 1;
	private static final int CARD_NUMBER_POSITION_END = 53;

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		return Collections.singletonMap("send", "send");
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Загрузить файл и послать тестовый запрос
	 * @param mapping  стратс-маппинг
	 * @param frm      стратс-форма
	 * @param request  запрос
	 * @param response ответ
	 * @return форвард на успешное выполнение операции
	 */
	@SuppressWarnings("UnusedParameters")
	public ActionForward send(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response)
	{
		UpdateServiceFeeResultForm form = (UpdateServiceFeeResultForm) frm;
		StringBuilder status = new StringBuilder();

		int sentCount = 0;
		try
		{
			List<String> cardNumbers = extractCardNumbers(form.getFile().getInputStream());
			status.append("Прочитано транзакций списания : ").append(cardNumbers.size()).append('\n');

			Map<Card, Client> toClient = getCardToClient(cardNumbers);
			status.append("Найдено карт : ").append(toClient.size()).append('\n');

			Collection<ServiceFeeResultRq> rqs = buildRequestFromFile(toClient, form.isSuccess());

			TransportMessageSerializer serializer = new TransportMessageSerializer();
			JmsService jmsService = new JmsService();
			for (ServiceFeeResultRq rq : rqs)
			{
				String rqXml = serializer.marshallServiceFeeResultRequest(rq);
				jmsService.sendMessageToQueue(rqXml, ErmbJndiConstants.ERMB_QUEUE, ErmbJndiConstants.ERMB_QCF, null, null);
				sentCount++;
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			status.append(ExceptionUtil.printStackTrace(e));
			form.setStatus(status.toString());
			return mapping.findForward(FORWARD_START);
		}

		status.append("Сообщения отправлены. Успешно отправлено: ").append(sentCount);
		form.setStatus(status.toString());
		return mapping.findForward(FORWARD_START);
	}

	private Collection<ServiceFeeResultRq> buildRequestFromFile(Map<Card, Client> cardToClient, boolean success) throws IOException
	{
		Collection<ServiceFeeResultRq> requests = new ArrayList<ServiceFeeResultRq>();

		for (Card card : cardToClient.keySet())
		{
			Client owner = cardToClient.get(card);

			ServiceFeeResultRq request = new ServiceFeeResultRq();
			request.setRqVersion(new VersionNumber(1, 0));
			request.setRqUID(new RandomGUID().toUUID());
			request.setRqTime(Calendar.getInstance());
			request.setClientIdentity(makeClientIdentity(owner));
			request.setResource(makeRecourseId(card));
			request.setTb(StringHelper.addLeadingZeros(card.getOffice().getCode().getFields().get("region"), 3));
			request.setPaymentStatus(success ? "0" : "-1");
			request.setPaymentID(RandomHelper.rand(10));

			requests.add(request);
		}

		return requests;
	}

	private Map<Card, Client> getCardToClient(Collection<String> cardNumbers) throws IOException, BusinessException, BusinessLogicException
	{
		Map<Card, Client> result = new HashMap<Card, Client>();

		for (String cardNumber : cardNumbers)
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(CardLink.class).
					add(Expression.eq("number", cardNumber));

			List<CardLink> cardLinkList = simpleService.find(criteria, null);
			if (cardLinkList.isEmpty())
				continue;
			CardLink cardLink = getMainCardLink(cardLinkList);

			ActivePerson byLogin = personService.findByLogin(cardLink.getLoginId(), null);
			result.put(cardLink.getCard(), byLogin.asClient());
		}

		return result;
	}

	private CardLink getMainCardLink(List<CardLink> cardLinkList)
	{
		for (CardLink cardLink : cardLinkList)
		{
			if (cardLink.isMain())
				return cardLink;
		}
		return cardLinkList.get(0);
	}

	private List<String> extractCardNumbers(InputStream inputStream) throws IOException
	{
		List<String> result = new ArrayList<String>();

		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inputStreamReader);

		try
		{
			String text;
			//noinspection NestedAssignment
			while ((text = reader.readLine()) != null)
			{
				String code = text.substring(TRANSACTION_CODE_POSITION_BEGIN, TRANSACTION_CODE_POSITION_END).trim();
				if ("CSHTX".equals(code))
				{
					String cardNumber = text.substring(CARD_NUMBER_POSITION_BEGIN, CARD_NUMBER_POSITION_END).trim();
					result.add(cardNumber);
				}
			}
		}
		finally
		{
			if (inputStreamReader != null)
				try
				{
					inputStreamReader.close();
				}
				catch (IOException ignored) {}

			if (reader != null)
				try
				{
					reader.close();
				}
				catch (IOException ignored) {}
		}
		return result;
	}

	private IdentityType makeClientIdentity(Client owner) throws IOException
	{
		IdentityType result = new IdentityType();
		result.setTb(StringHelper.addLeadingZeros(owner.getOffice().getCode().getFields().get("region"), 3));
		result.setLastname(owner.getSurName());
		result.setFirstname(owner.getFirstName());
		result.setMiddlename(owner.getPatrName());
		result.setBirthday(owner.getBirthDay());
		result.setIdentityCard(makeIdentityCard(owner.getDocuments()));

		return result;
	}

	//по спеке MSS номер документа обязателен
	private IdentityCardType makeIdentityCard(List<? extends ClientDocument> documents)
	{
		if (CollectionUtils.isEmpty(documents))
			throw new IllegalArgumentException("У клиента нет ДУЛ");

		Collections.sort(documents, new DocumentTypeComparator());
		ClientDocument document = documents.get(0);

		String docSeries = document.getDocSeries();
		String docNumber = document.getDocNumber();
		if (document.getDocumentType() == ClientDocumentType.PASSPORT_WAY)
		{
			docNumber = DocumentHelper.getPassportWayNumber(docSeries, docNumber);
			docSeries = null;
		}

		IdentityCardType identityCardType = new IdentityCardType();
		identityCardType.setIdType(PassportTypeWrapper.getPassportType(document.getDocumentType()));
		identityCardType.setIdSeries(docSeries);
		identityCardType.setIdNum(docNumber);
		identityCardType.setIssuedBy(document.getDocIssueBy());
		identityCardType.setIssueDt(document.getDocIssueDate());
		return identityCardType;
	}

	private ResourceIDType makeRecourseId(Card card) throws IOException
	{
		ResourceIDType result = new ResourceIDType();
		result.setType(ResourceType.CARD);
		result.setCard(card.getNumber());

		return result;
	}
}
