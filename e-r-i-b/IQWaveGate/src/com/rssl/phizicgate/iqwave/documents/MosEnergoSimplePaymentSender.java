package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rssl.phizicgate.iqwave.messaging.Constants.*;

/**
 * ������ ���������  � ���������� �� ������� ������  ����� ��������� � �������� ������������ ����� Period,
 * LastIndication, CurrentIndication � ��������������� ���� TariffNumber
 *
 * �������	            ���	            �����������	                    ���������
 * <Route>	            RouteCode       ��� ������� (��������)	        [1]
 * <DebitCard>	        CardInf         ���������� �� ����� ��������	[1]
 * <RecIdentifier>	    Requisite       ����� ���������� ���������	    [1]
 * <CurrCode>	        IsoCode         ������ ��������	                [1]
 * <Summa>	            Money           C���� ������� (�������������)	[1]
 * <Period>	            PayPeriod       ������ ���/���	                [1]
 * <LastIndication>	    NC-10           ���� ������ ���������           [1]
 *                                      ������������� �������� �001�	
 * <CurrentIndication>	Long            ������� ��������� ��������	    [1]
 * <TariffNumber>	    TariffNumb      ����� ������	                [0-1]
*/

public class MosEnergoSimplePaymentSender extends SimplePaymentSender
{
	private static final String TARIFF_VAR_OR_ZONA_EMPTY = "���";
	private static final Map<String, String> valuesCorrelation = new HashMap<String, String>();//������� ������������ ����, ��� ���� ������ ������������ � ��� ������ ���������� � ���������

	static
	{
		valuesCorrelation.put(TARIFF_ZONA_DAY,      "1");
		valuesCorrelation.put(TARIFF_ZONA_NIGHT,    "2");
		valuesCorrelation.put(TARIFF_ZONA_PEAK,     "13");
		valuesCorrelation.put(TARIFF_ZONA_HALFPEAK, "15");
		valuesCorrelation.put(TARIFF_VAR_ONETARIFF, "1");
		valuesCorrelation.put(TARIFF_VAR_TWOTARIFF, "2");
		valuesCorrelation.put(TARIFF_VAR_MANYTARIFF,"3");
	}

	/**
	 * ctor
	 * @param factory - �������� �������
	 */
	public MosEnergoSimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected Pair<String, String> getExecutionMessageName(GateDocument document)
	{
		return new Pair<String, String>(Constants.PAYMENT_MOSENERGO_REQUEST, Constants.PAYMENT_MOSENERGO_RESPONSE);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (document.getType() != CardPaymentSystemPayment.class)
			throw new GateException("�������� ��� ������� - ��������� CardPaymentSystemPayment");

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;

		if (StringHelper.isEmpty(payment.getChargeOffCard())
				|| payment.getChargeOffCardExpireDate() == null)
			throw new GateException("��������� ������������ ������ ��������� ��������");

		try
		{
			List<Field> extendedFields    = payment.getExtendedFields();
			List<Field> newExtendedFields = new ArrayList<Field>();

			//���� �������������� ���� ������� ���� �� �������, �� ��������� ���
			CommonField identifierField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
			if (identifierField == null)
			{
				newExtendedFields.add(RequestHelper.createIdentifierField());
			}

			//���� �������������� ���� ������ �� �������, �� ��������� ���
			if (BillingPaymentHelper.getFieldById(extendedFields, Constants.PERIOD_FIELD_NAME) == null)
			{
				newExtendedFields.add(RequestHelper.createPeriodField());
			}

			//���� �������������� ���� ���������� ��������� �������� �� �������, �� ��������� ���
			if (BillingPaymentHelper.getFieldById(extendedFields, Constants.LAST_INDICATION_FIELD_NAME) == null)
			{
				newExtendedFields.add(RequestHelper.createIndicationFieldField());
			}

			//���� �������������� ���� ������� ��������� �������� �� �������, �� ��������� ���
			if (BillingPaymentHelper.getFieldById(extendedFields, Constants.CURRENT_INDICATION_FIELD_NAME) == null)
			{
				newExtendedFields.add(RequestHelper.createCurrentIndicationField());
			}

			//���� �������������� ���� ������� ������ �� �������, �� ��������� ���
			CommonField tarifVersionField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.TARIFF_VAR_FIELD_NAME);
			if (tarifVersionField == null)
			{
				newExtendedFields.add(RequestHelper.createTarifVersionField());
			}
			else
			{
				tarifVersionField.setEditable(false);
			}

			//���������� ����� �������
			if (!newExtendedFields.isEmpty())
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}

			//��������� ���� ���� �����
			CommonField tarifZoneField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.TARIFF_ZONE_FIELD_NAME);
			if (tarifZoneField == null)
			{
				newExtendedFields.add(RequestHelper.createTarifZone((String) tarifVersionField.getValue()));
			}

			//���� �������������� ���� ����� �� �������, �� ��������� ���
			if (BillingPaymentHelper.getMainSumField(extendedFields) == null)
			{
				newExtendedFields.add(BillingPaymentHelper.createAmountField());
			}

			//���������� ����� �������
			if (!newExtendedFields.isEmpty())
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}

			//�����. ������������� ������������� ���� �� ������� �������
			payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	protected void fillExecutionMessage(GateMessage message, CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		super.fillExecutionMessage(message, payment);

		try
		{
			//������ �������������
			Field period = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.PERIOD_FIELD_NAME);
			RequestHelper.appendPeriod(message, "Period", (String) period.getValue());
			//���������� ��������� ��������
			message.addParameter(Constants.LAST_INDICATION_FIELD_NAME, getAttribute(payment, Constants.LAST_INDICATION_FIELD_NAME));
			//������� ��������� ��������
			message.addParameter(Constants.CURRENT_INDICATION_FIELD_NAME, getAttribute(payment, Constants.CURRENT_INDICATION_FIELD_NAME));
			//����� ������
			Field tariffVar = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.TARIFF_VAR_FIELD_NAME);
			if (tariffVar != null && !tariffVar.getValue().equals(TARIFF_VAR_OR_ZONA_EMPTY))
			{
				Field tariffZone = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.TARIFF_ZONE_FIELD_NAME);
				appendTariffNumber(message, tariffVar.getValue(), tariffZone.getValue());
			}
		}
		catch(DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� ����� ������
	 *  TariffNumb ����� ������ ��� ������ ���������
	 *      TariffVar	NC-2 ������� ������	[1]
	 *      TariffZone	NC-2 ���� �����	    [1]
	 *
	 * @param message ���������.
	 * @param tariffVar �������� ���� ������� ������
	 * @param tariffZone �������� ���� ���� �����
	 */
	private void appendTariffNumber(GateMessage message, Object tariffVar, Object tariffZone) throws GateException
	{
		Document requestDoc = message.getDocument();
		Element element = requestDoc.getDocumentElement();
		Element tariffNumber = XmlHelper.appendSimpleElement(element, Constants.TARIFF_NUMBER_FIELD_NAME);
		XmlHelper.appendSimpleElement(tariffNumber, Constants.TARIFF_VAR_FIELD_NAME, valuesCorrelation.get((String) tariffVar));
		XmlHelper.appendSimpleElement(tariffNumber, Constants.TARIFF_ZONE_FIELD_NAME, valuesCorrelation.get((String) tariffZone));
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
			Field tariffVar = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.TARIFF_VAR_FIELD_NAME);
			//���� ��������������, ���� ��� ��� � ���������� ��� ��� �� ���������, �� �� ����������
			if (tariffVar != null && !tariffVar.getValue().equals(TARIFF_VAR_OR_ZONA_EMPTY))
			{
				String tarifVarValue = (String) tariffVar.getValue();
				//���������, ��� ��������� �������� ���� "������� ������" ������������� �������� �� ���������
				if (!tarifVarValue.equals(TARIFF_VAR_ONETARIFF) && !tarifVarValue.equals(TARIFF_VAR_TWOTARIFF) &&
							!tarifVarValue.equals(TARIFF_VAR_MANYTARIFF) && !tarifVarValue.equals(TARIFF_VAR_OR_ZONA_EMPTY))
						throw new GateException("�������� ���� '������� ������' �� ������������� ����������. ������ ����: '" + TARIFF_VAR_ONETARIFF + "','" +
								TARIFF_VAR_TWOTARIFF + "','" + TARIFF_VAR_MANYTARIFF + "' � '" + TARIFF_VAR_OR_ZONA_EMPTY + "'");

				Field tariffZona = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.TARIFF_ZONE_FIELD_NAME);
				//���� ������� ������ ������, �� ���� "���� �����" ������ ���� ���������
				if (tariffZona == null)
					throw new GateException("�� ������ ������� " + Constants.TARIFF_VAR_FIELD_NAME);
				else if ((tariffZona.getValue()).equals(TARIFF_VAR_OR_ZONA_EMPTY))
					throw new GateLogicException("������� �������� � ���� '���� �����'");
				else
				{
					String tariffZonaValue = (String) tariffZona.getValue();
					//���������, ��� ��������� �������� ���� "���� �����" ������������� �������� �� ���������
					if (!tariffZonaValue.equals(TARIFF_ZONA_DAY) && !tariffZonaValue.equals(TARIFF_ZONA_NIGHT) &&
							!tariffZonaValue.equals(TARIFF_ZONA_PEAK) && !tariffZonaValue.equals(TARIFF_ZONA_HALFPEAK) && !tariffZonaValue.equals(TARIFF_VAR_OR_ZONA_EMPTY))
						throw new GateException("�������� ���� '���� �����' �� ������������� ����������. ������ ����: '" + TARIFF_ZONA_DAY + "','" +
								TARIFF_ZONA_NIGHT + "','" + TARIFF_ZONA_PEAK + "','" + TARIFF_ZONA_HALFPEAK + "' � '" + TARIFF_VAR_OR_ZONA_EMPTY + "'");

					//���� ������������ � �� ������ ���� �� ������������ ����� (� ��������� �� ������ ���� �
					//�������������� ����������  � ��������������� ���� ������� �1(����)�) � ����������� ������� ������� � ��������� ���������
					if (tarifVarValue.equals(TARIFF_VAR_ONETARIFF) && !tariffZonaValue.equals(TARIFF_ZONA_DAY))
						throw new GateLogicException("�� ����������� ������� ���� �����. ��� ������� ������ ����� ������� ������ �������� �" + TARIFF_ZONA_DAY + "�");

					//���� ������������ � �� ����� ������� ������������ ������� �� ����� ���� ����� �� ������ (����/����), ����� ������ ������
					//���� �� ������������ ����� (� ��������� �� ������ ���� � �������������� ����������  � ��������������� ���� ������� �1(����)/2(����)�)
					//� ����������� ������� ������� � ��������� ���������
					if (tarifVarValue.equals(TARIFF_VAR_TWOTARIFF) && !(tariffZonaValue.equals(TARIFF_ZONA_DAY) || tariffZonaValue.equals(TARIFF_ZONA_NIGHT)))
						throw new GateLogicException("�� ����������� ������� ���� �����. ��� ������� ������ ����� ������� �������� �" + TARIFF_ZONA_DAY + "� ��� �" + TARIFF_ZONA_NIGHT + "�");
				}
			}
		}
		catch(DocumentException e)
		{
			throw new GateException(e);
		}
	}

	//��� ������������ �����
	private Object getAttribute(CardPaymentSystemPayment payment, String attributeName) throws GateException
	{
		Field pair;
		try
		{
			pair = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), attributeName);
		}
		catch(DocumentException e)
		{
			throw new GateException(e);
		}
		if (pair == null)
		{
			throw new GateException("�� ������ ������� " + attributeName);
		}
		return pair.getValue();
	}
}
