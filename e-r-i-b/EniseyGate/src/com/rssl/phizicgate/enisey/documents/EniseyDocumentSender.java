package com.rssl.phizicgate.enisey.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.AbstractDocumentSender;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.BackRefReceiverInfoService;
import com.rssl.phizic.gate.payments.systems.recipients.BusinessRecipientInfo;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.enisey.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author mihaylov
 * @ created 09.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class EniseyDocumentSender extends AbstractDocumentSender implements DocumentSender
{
	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		if (!(document.getWithdrawType()==(AccountPaymentSystemPayment.class)))
		{
			throw new GateException(Constants.PAYMENT_TYPE_ERROR);
		}
		EniseyRequestHelper requestHepler = new EniseyRequestHelper(GateSingleton.getFactory());
		requestHepler.sendRevokeBillingPayment((AccountPaymentSystemPayment) document);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountPaymentSystemPayment))
		{
			throw new GateException(Constants.PAYMENT_TYPE_ERROR);
		}

		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) document;

		try
		{
			List<Field> extendedFields = payment.getExtendedFields();
			EniseyRequestHelper requestHepler = new EniseyRequestHelper(GateSingleton.getFactory());

			// �������� ��������� �� ����������
			BackRefReceiverInfoService receiverInfoService = GateSingleton.getFactory().service(BackRefReceiverInfoService.class);
			BusinessRecipientInfo businessRecipientInfo = receiverInfoService.getRecipientInfo(payment.getReceiverPointCode(), payment.getService().getCode());
			if (businessRecipientInfo == null)
				throw new GateException("�� ������� �������������� ���������� �� ���������� �����, ��� ���������� = " + payment.getReceiverPointCode() + ", ��� ������ = " + payment.getService().getCode());

			boolean debtAvailable = businessRecipientInfo.isDeptAvailable();

			//���� ������ ���� �����������, ������ ���������� ��������� ������ ����� �� ����������� �������
			CommonField stepField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, BillingPaymentHelper.REQUEST_BILLING_ATTRIBUTES_FIELD_NAME);
			if (stepField == null)
			{
				stepField = (CommonField) BillingPaymentHelper.createRequestBillingAttributesField();
				extendedFields.add(stepField);
			}

			String step = (String) stepField.getValue();

			//������ ������ ���. ����� ������� �� ��������
			if (Constants.STEP_1.equals(step))
			{
				step = Constants.STEP_2;

				Document billingAttrResponse = requestHepler.getRecipientExtendedFieldsDocument(payment.getReceiverPointCode(), payment.getService().getCode(), payment.getReceiverName());
				List<Field> billingFields = requestHepler.getExtendedFields(billingAttrResponse, false);

				//��������� ������ ����� ����
				for (Field billingField : billingFields)
				{
					//���� ������ ���� � ��������� ������� ����� �� ������� �������������, �� �� ������ ��� ������,
					//����� ������ �������������� �� ���� � ���� ����� ��������
					boolean needHideMainSum = debtAvailable && billingField.isMainSum();
					//���� ���� �� ������ � ����� �������
					Field extendedField = BillingPaymentHelper.getFieldById(extendedFields, billingField.getExternalId());
					//���� ���� �� ������� � ��� ������� �����, �� �������� ��, �.�. �� ����� ���������� ������ � ��������������
					if (extendedField == null && needHideMainSum)
					{
						((CommonField) billingField).setRequired(false);
						((CommonField) billingField).setVisible(false);
						extendedFields.add(billingField);
					}
					//���� ���� �� �������, �� ��������� ��� � ������ � ��������� ��� �� ������� "��������"
					else if (extendedField == null)
					{
						extendedFields.add(billingField);
						//���� ���� �������, ������ ������ ��� �������
						if (billingField.isKey())
							step = Constants.STEP_1;
					}
					//���� ���� �������, ��������� ��� �� ������� "��������" � �������������
					else if (extendedField.isKey() && StringHelper.isEmpty((String) extendedField.getValue()) && !needHideMainSum)
					{
						//������ ������ ����������� ��������� �������� ���
						((CommonField)extendedField).setError("������� �������� � ���� " + extendedField.getName());
						step = Constants.STEP_1;
					}

					//���� ������ ���������� �������� ���� �������� �����, ������ �� ����������������
					if (Constants.STEP_2.equals(step))
					{
						for (Field field : extendedFields)
						{
							if (field.isKey())
							{
								CommonField commonField = (CommonField) field;
								commonField.setEditable(false);
							}
						}
					}
				}
			}

			//������ ������ ���� �������������, �������������� ����� �������, ��������� � �������� ������� � ��������
			if (Constants.STEP_3.equals(step))
			{
				step = Constants.STEP_4;
			}

			//���� �������� ���� �������� ����� ���������, ��������� ������������� � ���� ������� ����� �������
			if (Constants.STEP_2.equals(step))
			{
				Document prepareBillingPaymentResponse = requestHepler.sendDebtAndValuesRequest(payment.getReceiverPointCode(), payment.getService().getCode(), extendedFields);
				setFieldValues(payment, prepareBillingPaymentResponse);

				String debtValue   = XmlHelper.getSimpleElementValue(prepareBillingPaymentResponse.getDocumentElement(), Constants.DEBT_SUM_TAG);
				String amountValue = XmlHelper.getSimpleElementValue(prepareBillingPaymentResponse.getDocumentElement(), Constants.TOTAL_SUM_TAG);

				//���� ������ ������������ ��������� �������������, ��������� ����
				//��� ������� ������� ���� ������������� �� ���������, �� �������� ������������� ��������� � ���� ����� �������
				if (debtAvailable && !payment.isTemplate())
				{
					CommonField debtField = (CommonField) EniseyRequestHelper.createDebtField();
					debtField.setDefaultValue(StringHelper.isEmpty(debtValue) ? "0" : debtValue);
					extendedFields.add(debtField);
				}

				//���� ������� ����� ����� ���� ��� ��������, ���� ���, �� ��������� ����
				CommonField amountField = (CommonField) BillingPaymentHelper.getMainSumField(extendedFields);
				if(amountField == null)
				{
					amountField = (CommonField) EniseyRequestHelper.createAmountField();
					amountField.setDefaultValue(!StringHelper.isEmpty(amountValue) ? amountValue : (debtAvailable && Float.valueOf(debtValue) >= 0 ? debtValue : "0"));
					extendedFields.add(amountField);
				}
				else
				{
					amountField.setRequired(true);
					amountField.setVisible(true);
					amountField.setValue(!StringHelper.isEmpty(amountValue) ? amountValue : (debtAvailable && Float.valueOf(debtValue) >= 0 ? debtValue : "0"));

					// ��������� ���� ���� � ����� ������, ���� ���� ����� ������������ ����� �������������
					extendedFields.remove(amountField);
					extendedFields.add(amountField);
				}
				//���� ������������� � ����� ������ �������� �������
				step = Constants.STEP_3;
			}

			//�������� ������� � ��������
			if (Constants.STEP_4.equals(step))
			{
				Document response = requestHepler.sendPreparePaymentRequest(payment);
				String commission = XmlHelper.getSimpleElementValue(response.getDocumentElement(), Constants.COMISSION_TAG);

				if (StringHelper.isEmpty(commission))
				{
					int countTransactions = 0;
					//���������� ������ �� ��� ���, ���� �� �������� ��������
					//����� �� ��������� ������������ ������������, ������ ������������ ���������� ���������� (20)
					while (StringHelper.isEmpty(commission))
					{
						if (countTransactions++ == Constants.MAX_COUNT_TRANSACTIONS)
							throw new GateLogicException("������� ��������� ��������� �������. ��������� ��������� ������");

						response = requestHepler.sendPreparePaymentRequest(response);
						commission = XmlHelper.getSimpleElementValue(response.getDocumentElement(), Constants.COMISSION_TAG);
						if (StringHelper.isEmpty(commission))
						{
							Document response2 = requestHepler.sendPreparePaymentRequest(response);
							if (!compareDocAttributes(response, response2))
								throw new GateLogicException("������� ��������� ��������� �������. ��������� ��������� ������");

							commission = XmlHelper.getSimpleElementValue(response2.getDocumentElement(), Constants.COMISSION_TAG);
							response = response2;
						}
					}
				}

				if (payment.isTemplate())
				{
					//� ������� ������� ���� ������������� �� ������ ������������
					Field debtField = BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_FIELD_NAME);
					extendedFields.remove(debtField);
				}
				else
				{
					//������������� �������� ��������, ��� �������� ������ � ������������ ������
					CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
					payment.setCommission(new Money(new BigDecimal(commission), currencyService.getNationalCurrency()));
				}

				//������������� �������� ����� ������������� ���������
				Field amountField = BillingPaymentHelper.getMainSumField(extendedFields);
				amountField.setValue(XmlHelper.getSimpleElementValue(response.getDocumentElement(), Constants.TOTAL_SUM_TAG));

				setFieldValues(payment, response);
				//������������� ����� ����� ������������ �������
				BillingPaymentHelper.prepareFields(payment);
				//��������� ��������� ���������
				payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));

				step = Constants.STEP_1;
			}

			stepField.setValue(step);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	//���� � ���� �������� ���� NameBS ��������� � ��������� ���� �� �������� xml, �� ���������
	//���� ��������� �� ������ ��
	private void setFieldValues(AccountPaymentSystemPayment payment, Document result) throws GateException
	{
		try
		{
			for (Field field: payment.getExtendedFields())
			{
				//��� ����� ����� �������� �� ���������
				if (EniseyRequestHelper.isOurField(field))
					continue;

				String eniseyValue = XmlHelper.getElementValueByPath(result.getDocumentElement(),
						"//" + Constants.ATTRIBUTE_TAG + "[" + Constants.NAMEBS_TAG + "='" + field.getExternalId() + "']/" + Constants.VALUE_TAG);

				if (StringHelper.isEmpty((String) field.getValue()))
				{
					((CommonField) field).setDefaultValue(eniseyValue);
				}
				else
				{
					field.setValue(eniseyValue);
				}
			}
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
		catch (TransformerException te)
		{
			throw new GateException(te);
		}
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountPaymentSystemPayment))
		{
			throw new GateException(Constants.PAYMENT_TYPE_ERROR);
		}
		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) document;
		EniseyRequestHelper requestHepler = new EniseyRequestHelper(GateSingleton.getFactory());
		Document result = requestHepler.sendExecutePaymentRequest(payment);
		String externalId = XmlHelper.getSimpleElementValue(result.getDocumentElement(), Constants.PAYMENT_ID_TAG);
		payment.setIdFromPaymentSystem(externalId);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	private boolean compareDocAttributes(Document doc1, Document doc2) throws GateException
	{
		final Element root1 = doc1.getDocumentElement();
		final Element root2 = doc2.getDocumentElement();
		try
		{
			NodeList nodes = XmlHelper.selectNodeList(root1, "//" + Constants.ATTRIBUTE_TAG);
			for (int i=0; i<nodes.getLength(); i++)
			{
				Element element = (Element) nodes.item(i);
				String nameBS = XmlHelper.getSimpleElementValue(element, Constants.NAMEBS_TAG);
				String value = XmlHelper.getSimpleElementValue(element, Constants.VALUE_TAG);
				Element element2 = XmlHelper.selectSingleNode(root2,
						"//" + Constants.ATTRIBUTE_TAG + "[" + Constants.NAMEBS_TAG + "='" + nameBS + "']");
				if (element2 == null)
					return false;
				if (!value.equals(XmlHelper.getElementValueByPath(element2, Constants.VALUE_TAG)))
					return false;
			}
		}
		catch (TransformerException te)
		{
			throw new GateException(te);
		}
		return true;
	}
}
