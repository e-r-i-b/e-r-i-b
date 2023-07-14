package com.rssl.phizic.gorod.senders.sevb;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.AbstractDocumentSender;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.systems.recipients.DebtImpl;
import com.rssl.phizgate.common.payments.systems.recipients.DebtRowImpl;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.gorod.GorodRequestHelper;
import com.rssl.phizic.gorod.messaging.XMLMessagingService;
import com.rssl.phizic.gorod.messaging.Constants;
import com.rssl.phizic.gorod.recipients.GorodViewSubServiceFieldComparator;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author Gainanov
 * @ created 07.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class GorodPaymentSender extends AbstractDocumentSender implements DebtService
{
	private GateFactory factory;

	public GorodPaymentSender(GateFactory factory)
	{
		this.factory = factory;
	}

	public GateFactory getFactory()
	{
		return factory;
	}

	/**
	 * ��������� ������ � ������� �����
	 * @param document ������
	 * @throws GateException, GateLogicException
	 */
	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountPaymentSystemPayment))
			throw new GateException("�������� ��� ���������, �������� AccountPaymentSystemPayment.");

		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) document;
		GorodRequestHelper requestHelper = new GorodRequestHelper(getFactory());

		try
		{
			List<Field> newExtendedFields = new ArrayList<Field>();
			List<Field> extendedFields = payment.getExtendedFields();
			// ���� � ������� ����, ����������� ������� ������ ��� ���
			Field mainServiceField = BillingPaymentHelper.getFieldById(extendedFields, Constants.NAME_MAIN_SERVICE_FIELD);
			// ���� ���� ���� ����� ��� ��������
			boolean mainService;
			if(mainServiceField != null)
			{
				mainService = Boolean.valueOf((String)mainServiceField.getValue());
			}
			else
			{
				//���� ���� ���, ������ ������ ���� ������ ������� ������ ��� ���
				mainService = requestHelper.isMainService(payment.getService().getCode());
				// ��������  ���� � ������
				extendedFields.add(requestHelper.createHiddenFlag(Constants.NAME_MAIN_SERVICE_FIELD, mainService));
			}

			CommonField licAccountField = (CommonField) requestHelper.getLicAccountField(extendedFields);

			String codeService = payment.getService().getCode();
			String recipientId = payment.getReceiverPointCode();
			String bilingClientId = payment.getBillingClientId();

			boolean personalPayment = !StringHelper.isEmpty(bilingClientId);

			//��� �������� ������ ���������� ������� ���. ���� licAccount
			if (mainService)
			{
				if (licAccountField == null)
				{
					//��� ������������ �������� ���� ���� ������� ���� �� ������� ReqCard,
					//��� �������� ������ �� ������� ReqXObject
					licAccountField = personalPayment ?
							requestHelper.createLicAccountField(codeService, bilingClientId) :
							(CommonField) requestHelper.getLicAccountField(requestHelper.getExtendedFields(codeService, mainService));

					//���� �� ������ �� ������ ���� ������� ����, ������� ����
					if (licAccountField == null)
					{
						licAccountField = requestHelper.createLicAccountField(codeService, mainService);
					}

					//���������� ���� �������
					newExtendedFields.add(licAccountField);
				}
				else
				{
					licAccountField.setEditable(false);
				}
			}

			//���� �� �������� ������, ��� ��� ���. ����� (������� ��� �� �������� �������), ��� ������������ ������, ���������� ������ �������� ����� 1
			if (!mainService || newExtendedFields.isEmpty()
					|| (FieldDataType.list == licAccountField.getType() && licAccountField.getValues().size() == 1))
			{
				//������� ������� ���� ����������, ��� ������ ���. ����� ��� ����������
				Field requestBillingAttributesField = BillingPaymentHelper.getFieldById(extendedFields, BillingPaymentHelper.REQUEST_BILLING_ATTRIBUTES_FIELD_NAME);
				if (requestBillingAttributesField == null)
				{
					newExtendedFields.add(BillingPaymentHelper.createRequestBillingAttributesField());

					//��� ������������ �������� �������� abonentId ����� �� ����, ��� ��������� ������ ������ � ����� �����
					String abonentId = personalPayment ? requestHelper.getLicAccountId(licAccountField) : requestHelper.getAbonentId(payment, mainService);

					//����������� �� ����� ����� ����������� ���������� �� ��������� ���. ���� �� �������
					List<Field> gateFields = requestHelper.getExtendedFields(abonentId, codeService, mainService);

					Field oldLicAccountField = requestHelper.getLicAccountField(extendedFields);
					Field newLicAccountField = requestHelper.getLicAccountField(gateFields);
					if (newLicAccountField != null && oldLicAccountField != null)
					{
						String oldExternalId = oldLicAccountField.getExternalId();
						String newExternalId = newLicAccountField.getExternalId();

						//���� ���� ������� ���� ��� ���� ��������, �� ������� ��� �� ���������� �����.
						if (newExternalId.equals(oldExternalId))
						{
							gateFields.remove(newLicAccountField);
						}

						//���� ������ ���� ���� ������ ����, � ������ ���� �� ������, ��������� ����, ����� ������ �������� � ����� �� �����
						if (Constants.ACCOUNT_FIELD_NAME.equals(oldExternalId) || Constants.ACCPU_FIELD_NAME.equals(oldExternalId))
						{
							newLicAccountField.setValue(oldLicAccountField.getValue());
							((CommonField) newLicAccountField).setDefaultValue(oldLicAccountField.getDefaultValue());
							((CommonField) newLicAccountField).setEditable(false);
							extendedFields.remove(oldLicAccountField);
						}
					}

					if (mainService)
					{
						//���� ��������� ������������ ������ �������������, ������������� ��
						BackRefReceiverInfoService infoService = GateSingleton.getFactory().service(BackRefReceiverInfoService.class);
						BusinessRecipientInfo recipientInfo    = infoService.getRecipientInfo(recipientId, codeService);
						if (recipientInfo.isDeptAvailable() && !payment.isTemplate())
							newExtendedFields.addAll(requestHelper.createDebtFields(codeService, requestHelper.getLicAccountValue(licAccountField)));
					}
					//���� ��� ���� � ��������� ������� �����
					if (BillingPaymentHelper.getMainSumField(gateFields) == null)
						newExtendedFields.add(BillingPaymentHelper.createAmountField());

					newExtendedFields.addAll(gateFields);
				}
				else
				{
					//��� ������������ �������� �������� abonentId ����� �� ����, ��� ��������� ������ ������ � ����� �����
					String abonentId = personalPayment ? requestHelper.getLicAccountId(licAccountField) : requestHelper.getAbonentId(payment, mainService);
					//��������� ������
					requestHelper.refreshBillingPayment(abonentId, payment, mainService);
				}
			}

			if (!newExtendedFields.isEmpty())
			{

				if (requestHelper.isComplexService(newExtendedFields))
					Collections.sort(newExtendedFields, new GorodViewSubServiceFieldComparator());
				extendedFields.addAll(newExtendedFields);
			}
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ������� ������ �� ������� �����
	 * ������ ����� ��������� �������, �.�. ��-�� ������������ ���������� ��� ���������� ��������� ���������� ������� ��������� ������ � ������,
	 * ������ ����� ������������ ����� ������������� ������, ����� �� ������� � ������ ����� �������. � ���� �� � ������ ���� ������ ������
	 * � ����� ������ ������� ��� �������� ����������� ������.
	 * @param document �������� ��� ������
	 * @throws GateException, TransformerException, GateLogicException
	 */
	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		if (document.getWithdrawType() != AccountPaymentSystemPayment.class)
			throw new GateException("�������� ��� �������!");

//		delete(document.getExternalId());
	}

	protected void delete(String unp) throws GateException, GateLogicException
	{
		/*XMLMessagingService service = XMLMessagingService.getInstance(factory);

		GateMessage msg = service.createRequest("ReqDelEOrder");
		Document doc = msg.getDocument();
		Element root = doc.getDocumentElement();
		Element eOrder = doc.createElement("EOrder");
		eOrder.setAttribute("id", unp);
		root.appendChild(eOrder);
		service.sendOnlineMessage(msg, null);*/
	}

	/**
	 * �������� � ���������(�������) ����� �������
	 * @param doc - ������
	 * @throws GateException, GateLogicException
	 */
	public void send(GateDocument doc) throws GateException, GateLogicException
	{
		//do nothing
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		//do nothing
	}


	/**
	 * ����������� ������ � ������
	 * @param gateDocument  ������
	 */
	public void confirm(GateDocument gateDocument) throws GateLogicException, GateException
	{

			AccountPaymentSystemPayment doc = (AccountPaymentSystemPayment) gateDocument;
			XMLMessagingService service = XMLMessagingService.getInstance(factory);

			GateMessage msg = service.createRequest("ReqPayAdvice");
			Document document = msg.getDocument();
			Element payAdvice = document.createElement("PayAdvice");
			document.getDocumentElement().appendChild(payAdvice);
			Element authAdvice = document.createElement("AuthAdvice");
			payAdvice.appendChild(authAdvice);
			Element lidM = document.createElement("lid-m");
//		    lidM.setAttribute("amount", doc.getChargeOffAmount().getDecimal().toString());
			lidM.setTextContent(doc.getIdFromPaymentSystem());
			authAdvice.appendChild(lidM);
			Element authCode = document.createElement("authCode");
			authCode.setTextContent("0");
			authAdvice.appendChild(authCode);
			service.sendOnlineMessage(msg, null);

	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//do nothing
	}

	/**
	 *  ������� ������ �� ������. ����� ��� ����, ����� � ��� ����� �������� � �����, ������ ��������
	 *  � �������� ��� �� �����.
	 * @param paymentId - id ������� � ������
	 */
	protected void deletePaymentFromGorod(String paymentId) throws GateException, GateLogicException
	{
		//do nothing
	}

//	private Client getClient(String clientId) throws GateException, GateLogicException
//	{
//		GateInfoService gateInfoService = factory.service(GateInfoService.class);
//
//		if (gateInfoService.isClientImportEnable(null))
//		{
//			ClientService clientService = factory.service(ClientService.class);
//			return clientService.getClientById(clientId);
//		}
//		else
//		{
//			Adapter adapter = (Adapter) factory.config(WSGateConfig.class);
//			String newId = IDHelper.storeRouteInfo(clientId, adapter.getUUID());
//			ClientServiceImpl clientService = new ClientServiceImpl(factory);
//
//			return clientService.getClientById(newId);
//		}
//	}

	public List<Debt> getDebts(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		List<Debt> debts = new ArrayList<Debt>();
		XMLMessagingService xmlService = XMLMessagingService.getInstance(getFactory());
		String recipientSynchKey = (String) recipient.getSynchKey();
		try
		{
			GorodRequestHelper requestHelper = new GorodRequestHelper(getFactory());
			boolean mainService  =  requestHelper.isMainService(recipient.getService().getCode());
			GateMessage message = requestHelper.buildReqXObjectRequest(recipient.getService().getCode(), keyFields.get(0).getValue().toString(), mainService);
			Document   response = xmlService.sendOnlineMessage(message, null);

			DebtImpl debt = new DebtImpl();
			debt.setDescription("�������������");
			debt.setCode(recipientSynchKey);
			debt.setFixed(false);
			List<DebtRow> debtRows = new ArrayList<DebtRow>();

			Element debtElement = XmlHelper.selectSingleNode(response.getDocumentElement(), "/AnsGlobal/Global/ListOfAbonent/Abonent/Debt");

			DebtRowImpl debtRow = new DebtRowImpl();
			debtRow.setDescription("�������������");
			debtRow.setCode(debtElement.getAttribute("id"));
			Currency currency = getFactory().service(CurrencyService.class).getNationalCurrency();
			String balance=debtElement.getAttribute("amount");

			debtRow.setDebt(new Money(NumericUtil.parseBigDecimal(balance),currency));
			debtRow.setCommission(new Money(BigDecimal.valueOf(0), currency));
			debtRow.setFine(new Money(BigDecimal.valueOf(0), currency));

			debtRows.add(debtRow);

			debt.setRows(debtRows);
			debts.add(debt);

			return debts;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}
	}
}
