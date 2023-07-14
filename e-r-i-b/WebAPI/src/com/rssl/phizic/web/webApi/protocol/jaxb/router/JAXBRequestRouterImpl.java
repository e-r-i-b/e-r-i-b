package com.rssl.phizic.web.webApi.protocol.jaxb.router;

import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.*;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf.*;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.*;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.*;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Balovtsev
 * @since  24.04.14
 */
public enum JAXBRequestRouterImpl implements JAXBRequestRouter
{
	INSTANCE;

	/**
	 * Список классов использующихся в JAXBContext для сериализации/десериализации
	 */
	private final static String JAXB_CONTEXT_PATH = Request.class.getPackage().getName() + ":" +
													Response.class.getPackage().getName();

	/**
	 * Обрабатыватывает запрос и возвращает подготовленный ответ в виде строки
	 *
	 * @param source запрос
	 * @return Возвращает сформированный ответ в виде строки. МОЖЕТ возвращать null в случае, когда
	 * операция указанная в запросе не содержится в RequestOperationConstant т.е данная операция обрабатывается
	 * другим способом (смотреть RequestRouter#process()).
	 *
	 * @throws JAXBException
	 */
	public String process(Request request, String source) throws JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT_PATH);

		/*
		 * Нет операции - значит используется не JAXB реализация
		 */
		RequestOperationConstant operation = request.getOperation();
		if (operation == null)
		{
			return null;
		}
		else
		{
			appendRouteContext(request);

			Response response = null;
			switch (operation)
			{
				case AUTOPAYMENTS_OPERATION:
				{
					response = new AutoPaymentResponseConstructor().construct(request);
					break;
				}

				case FAST_PAYMENT_PANEL_OPERATION:
				{
					response = new FastPaymentResponseConstructor().construct(request);
					break;
				}

				case MENU_OPERATION:
				{
					response = new MenuResponseConstructor().construct(unmarshalByType(context, source, MenuRequest.class));
					break;
				}

				case LOGIN_OPERATION:
				{
					response = new LoginResponseConstructor().construct(unmarshalByType(context, source, LoginRequest.class));
					break;
				}

				case PRODUCT_LIST:
				{
					response = new ProductListResponseConstructor().construct(unmarshalByType(context, source, ProductListRequest.class));
					break;
				}

				case CARD_DETAIL:
				{
					response = new CardDetailResponseConstructor().construct(unmarshalByType(context, source, IdRequest.class));
					break;
				}

				case ACCOUNT_DETAIL:
				{
					response = new AccountDetailResponseConstructor().construct(unmarshalByType(context, source, IdRequest.class));
					break;
				}
				case CARD_ABSTRACT:
				{
					response = new CardAbstractResponseConstructor().construct(unmarshalByType(context, source, ProductAbstractRequest.class));
					break;
				}

				case ACCOUNT_ABSTRACT:
				{
					response = new AccountAbstractResponseConstructor().construct(unmarshalByType(context, source, ProductAbstractRequest.class));
					break;
				}

				case CHECK_SESSION:
				{
					response = new SimpleResponseConstructor().construct(request);
					break;
				}

				case ALF_HISTORY_OPERATION:
				{
					response = new AlfHistoryConstructor().construct(unmarshalByType(context, source, AlfHistoryRequest.class));
					break;
				}

				case ADD_ALF_OPERATION:
				{
					response = new AddAlfOperationConstructor().construct(unmarshalByType(context, source, AddAlfOperationRequest.class));
					break;
				}

				case ALF_EDIT_OPERATION:
				{
					response = new AlfEditOperationConstructor().construct(unmarshalByType(context, source, AlfEditOperationRequest.class));
					break;
				}

				case ALF_GRAPHIC_DATA_REQUEST_OPERATION:
				{
					response = new AlfGraphicDataConstructor().construct(unmarshalByType(context, source, AlfGraphicDataRequest.class));
					break;
				}

				case ALF_SERVICE_STATUS:
				{
					response = new ALFStatusResponseConstructor().construct(request);
					break;
				}

				case ALF_CONNECT:
				{
					response = new ALFConnectResponseConstructor().construct(request);
					break;
				}

				case ALF_CATEGORY_LIST:
				{
					response = new ALFCategoryListResponseConstructor().construct(unmarshalByType(context, source, ALFCategoryListRequest.class));
					break;
				}

				case ALF_CATEGORY_EDIT:
				{
					response = new ALFCategoryEditResponseConstructor().construct(unmarshalByType(context, source, ALFCategoryEditRequest.class));
					break;
				}

				case ALF_CATEGORY_FILTER:
				{
					response = new ALFCategoryFilterResponseConstructor().construct(unmarshalByType(context, source, ALFCategoryFilterRequest.class));
					break;
				}

				case ALF_CATEGORY_BUDGETSET:
				{
					response = new ALFCategoryBudgetsetResponseConstructor().construct(unmarshalByType(context, source, ALFCategoryBudgetsetRequest.class));
					break;
				}

				case ALF_CATEGORY_BUDGET_REMOVE:
				{
					response = new ALFCategoryBudgetRemoveResponseConstructor().construct(unmarshalByType(context, source, IdRequest.class));
					break;
				}

				case ALF_FINANCECONTENT:
				{
					response = new ALFFinanceContentResponseConstructor().construct(request);
					break;
				}

				case ALF_EDIT_GROUP_OPERATION:
				{
					response = new AlfEditOperationGroupConstructor().construct(unmarshalByType(context, source, AlfEditOperationGroupRequest.class));
					break;
				}

				case LOGOFF:
				{
					response = new LogoffResponseConstructor().construct(request);
					break;
				}

				case MOBILE_BANK_HIDE:
				{
					response = new HideMobileBankResponseConstructor().construct(request);
					break;
				}
			}

			return marshal(context, response);
		}
	}

	public void clearRouteContext()
	{
		LogThreadContext.clear();
	}

	private void appendRouteContext(final Request request)
	{
		String uid = new RandomGUID().getStringValue();

		LogThreadContext.setLogUID(uid);
		LogThreadContext.setIPAddress(request.getIp());
	}

	private <T extends Request>T unmarshalByType(JAXBContext context, String request, Class<T> clazz) throws JAXBException
	{
		return context.createUnmarshaller().unmarshal(new StreamSource(new StringReader(request)), clazz).getValue();
	}

	private Request unmarshal(JAXBContext context, String request) throws JAXBException
	{
		return unmarshalByType(context, request, SimpleRequest.class);
	}

	private String marshal(JAXBContext context, Response response) throws JAXBException
	{
		StringWriter writer = new StringWriter();

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler()
		{
			public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException
			{
				out.write(ch, start, length);
			}
		});
		marshaller.marshal(response, writer);

		return writer.toString();
	}
}
