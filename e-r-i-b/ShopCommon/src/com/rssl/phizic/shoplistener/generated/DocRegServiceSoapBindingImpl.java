/**
 * DocRegServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.shoplistener.generated;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.ProfileNotFoundException;
import com.rssl.phizgate.common.profile.MBKCastService;
import com.rssl.phizgate.common.providers.ProviderPropertiesEntry;
import com.rssl.phizgate.common.providers.ProviderPropertiesService;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.einvoicing.*;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.shoplistener.generated.registration.Full;
import com.rssl.phizic.shoplistener.generated.registration.Offline;
import com.rssl.phizic.shoplistener.generated.registration.Partial;
import com.rssl.phizic.shoplistener.generated.registration.Registration;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class DocRegServiceSoapBindingImpl implements com.rssl.phizic.shoplistener.generated.DocRegService{

	public static final String INTERNAL_ERROR = "Internal error";
	public static final Long ERROR_STATE_CODE = -1L;

	private static final String BILLING_OFFLINE_ERROR = "Billing offline error";
	private static final String BILLING_TIMEOUT_ERROR = "Billing timeout error";
	private static final String ORDER_STATE_ERROR = "Incorrect order state error";
	private static final String RECALL_STATE_ERROR = "Incorrect recall state error";
	private static final String RECALL_AMOUNT_ERROR = "Incorrect recall amount error";
	private static final String RECEIVER_CODE_ERROR = "SPName error";
	private static final String FAC_PROVIDER_CODE_ERROR = "eShopIdBySP error";
	private static final String MB_CHECK_NOT_SUPPORTED = "Checking phone is not available";
	private static final String PROVIDER_NOT_FOUND = "Provider is not found";
	private static final String PHONE_NUMBER_ERROR = "Incorrect phone number error";

    private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
    private static final ShopOrderServiceImpl service = new ShopOrderServiceImpl(null);
    private static final ProviderPropertiesService providerPropertiesService = new ProviderPropertiesService();
	private static final FacilitatorProviderService facilitatorProviderService = new FacilitatorProviderService(null);
	private static final MBKCastService mbkCastService = new MBKCastService(null);

	private static final List<OrderState> RECALL_ALLOWED_STATES = new LinkedList<OrderState>();
	private static final List<RecallState> RECALL_COMPLETE_STATES = new LinkedList<RecallState>(); //конечные статусы отмен оплат/возвратов товара

	static {
		RECALL_ALLOWED_STATES.add(OrderState.EXECUTED);
		RECALL_ALLOWED_STATES.add(OrderState.PARTIAL_REFUND);

		RECALL_COMPLETE_STATES.add(RecallState.EXECUTED);
		RECALL_COMPLETE_STATES.add(RecallState.REFUSED);
	}

    /**
	 * Регистрация заказа в системе ЕРИБ
	 * @param parameters - информация о документе и режим регистрации
	 * @return - статус ошибки
	 * @throws java.rmi.RemoteException
	 */
	public com.rssl.phizic.shoplistener.generated.DocRegRsType docReg(com.rssl.phizic.shoplistener.generated.DocRegRqType parameters) throws java.rmi.RemoteException {
		Registration registration = getRegistration(parameters.getMode());
		return registration.registrate(parameters);
	}

	private Registration getRegistration(String mode)
	{
		if (mode.equals(TypeOrder.P.name()))
			return new Partial();
		else if (mode.equals(TypeOrder.F.name()))
			return new Full();
		else
			return new Offline();
	}

	public com.rssl.phizic.shoplistener.generated.DocFlightsInfoRsType docFlightsInfo(com.rssl.phizic.shoplistener.generated.DocFlightsInfoRqType parameters) throws java.rmi.RemoteException
	{
		int length = parameters.getDocuments().length;
        DocFlightsInfoRsDocumentsResultType[] results = new DocFlightsInfoRsDocumentsResultType[length];
		String receiverCode = null;
        for (int i = 0; i < length; ++i)
        {
            DocFlightsInfoRqDocumentType document = parameters.getDocuments()[i];
            DocFlightsInfoRsDocumentsResultType result = new DocFlightsInfoRsDocumentsResultType();
            results[i] = result;
            result.setERIBUID(document.getERIBUID());

            //ищем документ по ERIBUID
            ShopOrder order;
            try
            {
	            order = service.getOrder(document.getERIBUID());
            } catch (Exception e)
            {
                result.setStatus(new StatusType(-10L, "Ошибка поиска заказа"));
                log.error("Ошибка поиска заказа с ERIBUID=" + document.getERIBUID(), e);
                continue;
            }

            if (order == null) {
                result.setStatus(new StatusType(-1L, "Заказ не найден"));
                continue;
            }

	        if (i==0)
		        receiverCode = order.getReceiverCode();

            result.setId(order.getExternalId()); //теперь extendedId известен => сетим

            //статус должен быть "оплачен"
            if (OrderState.EXECUTED != order.getState())
            {
                log.error("Пришла информация о выпущенных билетах (DocFlightsInfoRq), но документ оплаты заказа (uuid заказа=" + order.getUuid() + ") еще не исполнен");
                result.setStatus(new StatusType(-10L, "Неверный статус заказа"));
                continue;
            }

            long ticketsStatus = document.getTicketsStatus();
            /* В поле jurPayment.ticketsInfo кладем xml в виде строки следующего формата:
                <TicketsInfo>
                  <TicketsStatus>статус</TicketsStatus>
                  <TicketsDescription>статус</TicketsDescription>
                  <TicketsList>
                    <TicketNumber> номер билета </TicketNumber>
                    …
                    <TicketNumber> номер билета </TicketNumber>
                  </TicketsList>
                  <ItineraryUrl> ссылка на страницу с полной маршрут-квитанцией </ItineraryUrl>
                </TicketsInfo>
             */
            XmlEntityBuilder builder = new XmlEntityBuilder();
            builder.openEntityTag("TicketsInfo");
	        builder.createEntityTag("TicketsStatus", Long.toString(ticketsStatus));
	        String ticketsDesc = document.getTicketsDesc();
	        if (StringHelper.isNotEmpty(ticketsDesc))
	            builder.createEntityTag("TicketsDescription", ticketsDesc);
            //номера билетов
            String[] ticketsList = document.getTicketsList();
            if(ticketsList != null && ticketsList.length > 0)
            {
                builder.openEntityTag("TicketsList");
                for(String ticketNumber : ticketsList)
                    builder.createEntityTag("TicketNumber", ticketNumber);
                builder.closeEntityTag("TicketsList");
            }
            //ссылка на маршрут-квитанцию
            builder.createEntityTag("ItineraryUrl", document.getItineraryUrl());
            builder.closeEntityTag("TicketsInfo");

            //логируем описание системной ошибки
            if(StringHelper.isNotEmpty(document.getStatusDesc()))
                log.error("Оповещение ЕРИБ о выпущенных авиабилетах DocFlightsInfoRq. RqUID=" + parameters.getRqUID()
                        + ". ERIBUID=" + document.getERIBUID()
                        + ". TicketsStatus=" + ticketsStatus
                        + ". StatusDesc=" + document.getStatusDesc());

            try
            {
	            getInvoiceBackService().sendTickets(order, builder.toString());
            }
            catch (Exception e)
            {
                log.error("Ошибка обновления информации по билетам для заказа uuid=" + order.getUuid(), e);
	            try
	            {
		            service.setTicketsInfo(order.getUuid(), builder.toString());
	            }
	            catch (GateException ge)
	            {
		            log.error("Ошибка записи информации по билетам в БД для заказа uuid=" + order.getUuid(), ge);
		            result.setStatus(new StatusType(-10L, "Ошибка обновления платежа"));
		            continue;
	            }
            }

            result.setStatus(new StatusType()); //0L*/
        }

        //формируем ответ
        DocFlightsInfoRsType response = new DocFlightsInfoRsType();

        response.setRqUID(parameters.getRqUID());
        response.setRqTm(parameters.getRqTm());

		if (StringHelper.isNotEmpty(parameters.getSystemId()))
			response.setSystemId(getSystemId(receiverCode));

        response.setDocuments(results);

        return response;
    }

	public GoodsReturnRsType goodsReturn(GoodsReturnRqType parameters) throws RemoteException
	{
		GoodsReturnRqDocumentType document = parameters.getDocument();
		int status = 0;
		String statusDescr = "";
		String receiverCode = null;
		try
		{
			String returnedGoods = null;
			if (document.getFields().getTicketsList() != null)
				returnedGoods = buildTicketsListXml(document.getFields().getTicketsList());
			else if (document.getFields().getShop() != null)
				returnedGoods = ShopDetailInfoBuilder.fillShopGoodsInfo(document.getFields().getShop());
			ShopRecallImpl recall = createShopRecall(document.getERIBUID(), document.getGoodsReturnId(), parameters.getSPName(), parameters.getEShopIdBySP(), RecallType.PARTIAL,
					new Money(document.getAmount(), new CurrencyImpl(document.getAmountCur().getValue())));
			ShopOrderImpl order = service.getOrder(document.getERIBUID());
			receiverCode = order.getReceiverCode();
			getInvoiceBackService().sendReturnGoods(order, document.getAmount(), document.getAmountCur().getValue(), recall.getUuid(), returnedGoods);
		}
		catch (InactiveExternalSystemException e)
		{
			log.error(e.getMessage(), e);
			status = -2; //биллинг недоступен, необходимо повторить запрос позже
			statusDescr = BILLING_OFFLINE_ERROR;
		}
		catch (GateTimeOutException e)
		{
			log.error(e.getMessage(), e);
			status = -3; //превышено время ожидания результата обработки запроса от биллинга
			statusDescr = BILLING_TIMEOUT_ERROR;
		}
		catch (GateLogicException e)
		{
			log.error(e.getMessage(), e);
			status = -1;
			statusDescr = e.getMessage();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			status = -1; //системная ошибка
			statusDescr = INTERNAL_ERROR;
		}

		GoodsReturnRsResultType result = new GoodsReturnRsResultType();
		result.setERIBUID(document.getERIBUID());
		result.setGoodsReturnId(document.getGoodsReturnId());
		result.setStatus(new StatusType(status, statusDescr));
		GoodsReturnRsType response = new GoodsReturnRsType();
		response.setRqUID(parameters.getRqUID());
        response.setRqTm(parameters.getRqTm());
		response.setResult(result);
		if (StringHelper.isNotEmpty(parameters.getSystemId()))
			response.setSystemId(getSystemId(receiverCode));
		return response;
	}

	public DocRollbackRsType docRollback(DocRollbackRqType parameters) throws RemoteException
	{
		DocRollbackRqDocumentType document = parameters.getDocument();
		int status = 0;
		String statusDescr = "";
		try
		{
			ShopRecallImpl recall = createShopRecall(document.getERIBUID(), document.getDocRollbackId(), parameters.getSPName(), parameters.getEShopIdBySP(), RecallType.FULL,
					new Money(document.getAmount(), new CurrencyImpl(document.getAmountCur().getValue())));
			getInvoiceBackService().sendRefundOrder(service.getOrder(document.getERIBUID()), document.getAmount(), document.getAmountCur().getValue(), recall.getUuid());
		}
		catch (InactiveExternalSystemException e)
		{
			log.error(e.getMessage(), e);
			status = -2; //биллинг недоступен, необходимо повторить запрос позже
			statusDescr = BILLING_OFFLINE_ERROR;
		}
		catch (GateTimeOutException e)
		{
			log.error(e.getMessage(), e);
			status = -3; //превышено время ожидания результата обработки запроса от биллинга
			statusDescr = BILLING_TIMEOUT_ERROR;
		}
		catch (GateLogicException e)
		{
			log.error(e.getMessage(), e);
			status = -1;
			statusDescr = e.getMessage();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			status = -1; //системная ошибка
			statusDescr = INTERNAL_ERROR;
		}

		DocRollbackRsResultType result = new DocRollbackRsResultType();
		result.setERIBUID(document.getERIBUID());
		result.setDocRollbackId(document.getDocRollbackId());
		result.setStatus(new StatusType(status, statusDescr));

		DocRollbackRsType response = new DocRollbackRsType();
		response.setRqUID(parameters.getRqUID());
        response.setRqTm(parameters.getRqTm());
		response.setResult(result);
		return response;
	}

	public ClientCheckRsType clientCheck(ClientCheckRqType parameters) throws RemoteException
	{
		String spName = parameters.getSPName();
		String eShopIdBySP = parameters.getEShopIdBySP();
		String phoneStr = parameters.getPhone();

		checkField(parameters.getRqUID(), "RqUID");
		checkField(parameters.getRqTm(), "RqTm");
		checkField(parameters.getSPName(), "SPName");
		checkField(parameters.getEShopIdBySP(), "EShopIdBySP");
		checkField(parameters.getRecipientName(), "RecipientName");
		checkField(parameters.getURL(), "URL");
		checkField(parameters.getINN(), "INN");
		checkField(phoneStr, "Phone");

		ClientCheckRsType response = new ClientCheckRsType();
		response.setRqUID(parameters.getRqUID());
		response.setRqTm(parameters.getRqTm());
		response.setSPName(spName);
		response.setEShopIdBySP(eShopIdBySP);
		StatusType stat = new StatusType();
		response.setStatus(stat);

		try
		{
			//далее реализация схемы использования запроса на проверку клиента по номеру мобильного телефона согласно схеме в РО 16 релиза
			// Убрать все не являющиеся цифрами символы из номера мобильного телефона, отобрать последние 10 символов
			String correctedPhoneNumber = phoneStr.replaceAll("[^0-9]","");
			if(correctedPhoneNumber.length() < 10)
			{
				stat.setStatusCode(ERROR_STATE_CODE); // недостаточно символов в номере телефона
				stat.setStatusDesc(PHONE_NUMBER_ERROR);
				return response;
			}
			else
			{
				correctedPhoneNumber = correctedPhoneNumber.substring(correctedPhoneNumber.length()-10);
			}
			String phone = PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(correctedPhoneNumber);

			ShopProvider activeProvider = GateSingleton.getFactory().service(InvoiceGateBackService.class).getActiveProvider(spName); //ПМБ.004
			if (activeProvider == null)//ПМБ.005
			{
				stat.setStatusCode(ERROR_STATE_CODE);//ПМБ.006
				stat.setStatusDesc(PROVIDER_NOT_FOUND);
				return response;
			}

			ProviderPropertiesEntry providerProperties = providerPropertiesService.findById(activeProvider.getId());
			if (providerProperties == null || !providerProperties.isMbCheckEnabled())//ПМБ.007
			{
				stat.setStatusCode(ERROR_STATE_CODE);//ПМБ.008
				stat.setStatusDesc(MB_CHECK_NOT_SUPPORTED);
				return response;
			}

			if (!activeProvider.isFacilitator())//ПМБ.009
			{
				stat.setStatusCode(searchPerson(phone));//ПМБ.010
			}
			else
			{
				FacilitatorProvider facilitatorProvider = facilitatorProviderService.findByCode(spName, eShopIdBySP);//ПМБ.011
				if (facilitatorProvider != null)
				{
					if (facilitatorProvider.isMbCheckEnabled()) //ПМБ.012
					{
						stat.setStatusCode(searchPerson(phone)); //ПМБ.013
					}
					else
					{
						stat.setStatusCode(ERROR_STATE_CODE);//ПМБ.014
						stat.setStatusDesc(MB_CHECK_NOT_SUPPORTED);
					}
				}
				else
				{
					if (!providerProperties.isMbCheckDefaultEnabled())//ПМБ.015
					{
						stat.setStatusCode(ERROR_STATE_CODE);//ПМБ.016
						stat.setStatusDesc(MB_CHECK_NOT_SUPPORTED);
					}
					else
					{
						//ПМБ.017
						FacilitatorProvider provider = new FacilitatorProvider();
						provider.setFacilitatorCode(spName);
						provider.setCode(eShopIdBySP);
						provider.setName(parameters.getRecipientName());
						provider.setInn(parameters.getINN());
						provider.setUrl(parameters.getURL());
						provider.setDeleted(false);
						provider.setMbCheckEnabled(true);
						provider.setEinvoiceEnabled(providerProperties.isEinvoiceDefaultEnabled());
						provider.setMobileCheckoutEnabled(providerProperties.isMcheckoutDefaultEnabled());
						try
						{
							facilitatorProviderService.add(provider);
							providerProperties.setUpdateDate(Calendar.getInstance());
							providerPropertiesService.addOrUpdate(providerProperties);
						}
						catch (ConstraintViolationException ignore) {}

						stat.setStatusCode(searchPerson(phone)); //ПМБ.019
					}
				}

			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			stat.setStatusCode(ERROR_STATE_CODE);
			stat.setStatusDesc(INTERNAL_ERROR);
		}

		return response;
	}

	private void checkField(String fieldValue, String fieldName)
	{
		if (StringHelper.isEmpty(fieldValue))
			throw new IllegalArgumentException("Не указан параметр " + fieldName);
	}

	private long searchPerson(String phone) throws BackException, BackLogicException, GateException
	{
		if (mbkCastService.isSberbankClient(phone))
			return 1;

		try
		{
			CSABackRequestHelper.sendGetUserInfoByPhoneRq(phone, false);
			return 1;
		}
		catch (ProfileNotFoundException ignore)
		{
			return 0;
		}

	}

	/**
	 * Возвращает список билетов в виде xml
	 * <tickets>
	 *   <ticket>
	 *      <number>XXX</number>
	 *       <price>
	 *          <amount>price</amount>
	 *          <currency>priceCur</currency>
	 *       </price>
	 *   </ticket>
	 * </tickets>
	 * @param ticketsList список возвращенных билетов
	 * @return xml строку
	 */
	private String buildTicketsListXml(TicketItemType[] ticketsList)
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag("tickets");
		if(ArrayUtils.isNotEmpty(ticketsList))
		{
			for (int i=0; i<ticketsList.length; i++)
			{
				TicketItemType item = ticketsList[i];

				builder.openEntityTag("ticket");

				if (StringHelper.isNotEmpty(item.getTicketNumber()))
					builder.createEntityTag("number", item.getTicketNumber());

				if (item.getPrice() != null && item.getPriceCur() != null)
				{
					builder.openEntityTag("price");
					builder.createEntityTag("amount", item.getPrice().toString());
					builder.createEntityTag("currency", item.getPriceCur().getValue());
					builder.closeEntityTag("price");
				}

				builder.closeEntityTag("ticket");
			}
		}
		builder.closeEntityTag("tickets");

		return builder.toString();
	}

	private ShopRecallImpl createShopRecall(final String orderUUID, final String recallExternalId, final String receiverCode, final String eShopIdBySP, final RecallType type, final Money recallAmount) throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<ShopRecallImpl>()
		{
			public ShopRecallImpl run(Session session) throws Exception
			{
				ShopOrderImpl order = service.findOrder(orderUUID, LockMode.UPGRADE_NOWAIT);
				if (!RECALL_ALLOWED_STATES.contains(order.getState()))
				    throw new GateLogicException(ORDER_STATE_ERROR);

				if (!order.getReceiverCode().equals(receiverCode))
					throw new GateLogicException(RECEIVER_CODE_ERROR);

				List<ShopRecallImpl> shopRecalls = service.findShopRecalls(orderUUID, order.getDate(), receiverCode, recallExternalId, OrderState.ERROR);
				if (CollectionUtils.isNotEmpty(shopRecalls))
					throw new GateException("Найдены записи с аналогичным идентификатором в статусе ERROR");

//				проверка возвратов для фасилитаторов
				String facilitatorProviderCode = order.getFacilitatorProviderCode();
				if (StringHelper.isNotEmpty(facilitatorProviderCode) && !facilitatorProviderCode.equals(eShopIdBySP))
				{
					throw new GateLogicException(FAC_PROVIDER_CODE_ERROR);
				}

				List<ShopRecallImpl> allShopOrderRecalls = service.findAllShopOrderRecalls(order.getUuid(), order.getDate());
				if (CollectionUtils.isNotEmpty(allShopOrderRecalls))
				{
					for (ShopRecallImpl recall : allShopOrderRecalls)
					{
						if (!RECALL_COMPLETE_STATES.contains(recall.getState()))
							throw new GateLogicException(RECALL_STATE_ERROR);
					}
				}


				Money amount = new Money(order.getAmount());
				if (CollectionUtils.isNotEmpty(allShopOrderRecalls))
				{
					for (ShopRecallImpl recall : allShopOrderRecalls)
					{
						if (recall.getState() == RecallState.EXECUTED)
							amount = amount.sub(recall.getAmount());
					}
				}

				if (amount.compareTo(recallAmount) == -1)
					throw new GateLogicException(RECALL_AMOUNT_ERROR);

				ShopRecallImpl recall = new ShopRecallImpl();
				recall.setOrderUuid(order.getUuid());
				recall.setExternalId(recallExternalId);
				recall.setUuid(new RandomGUID().getStringValue());
				recall.setState(RecallState.CREATED);
				recall.setReceiverCode(receiverCode);
				recall.setDate(Calendar.getInstance());
				recall.setType(type);
				recall.setAmount(recallAmount);
				return service.addShopRecall(recall);
			}
		});
	}

	private InvoiceGateBackService getInvoiceBackService()
	{
		return GateSingleton.getFactory().service(InvoiceGateBackService.class);
	}

	private String getSystemId(String receiverCode)
	{
		try
		{

			if (receiverCode == null)
			{
				log.error("Ошибка при установке systemId в DocFlightsInfoRs - не найден код поставщика");
				return null;
			}
			else
			{
				ShopProvider provider = GateSingleton.getFactory().service(InvoiceGateBackService.class).getActiveProvider(receiverCode);
				if (provider == null)
				{
					log.error("Ошибка при установке systemId в DocFlightsInfoRs - не найден поставщик. receiverCode=" + receiverCode);
					return null;
				}
				else
					return provider.getCodeRecipientSBOL();
			}
		}
		catch (GateException e)
		{
			log.error("Ошибка поиска поставщика receiverCode=" + receiverCode, e);
			return null;
		}
	}
}
