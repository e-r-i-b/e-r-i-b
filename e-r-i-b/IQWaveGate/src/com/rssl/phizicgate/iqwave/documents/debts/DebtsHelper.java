package com.rssl.phizicgate.iqwave.documents.debts;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.systems.recipients.DebtImpl;
import com.rssl.phizgate.common.payments.systems.recipients.DebtRowImpl;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.DebtRow;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.documents.RequestHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author krenev
 * @ created 15.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class DebtsHelper
{
	/**
	 * ���������� ����� � ������������� ��� ���� ��� FNS
	 *
	 * ����� � �������������:
	 * �������	            ���	        �����������	                        ���������
	 * <SummCanEdit>	    Bool        ������ ����� �������� ����� �������	    [1]
	 * <DebtsRoute>	        RouteCode   ��� ������� (��������)	                [1]
	 * <DebtsCurrCode>	    IsoCode     ������ �������������	                [1]
	 * <DebtsSumma>	        DebSumType  ����� �������������	                    [1]
	 *
	 * @param response �������� � ������� � �������������
	 * @return ������ ��������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static Debt parseMGTSFNSDebt(Document response) throws GateException, GateLogicException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Element root = response.getDocumentElement();
		DebtImpl debt = new DebtImpl();
		String debtCode = RequestHelper.getRouteCode(root, "DebtsRoute").toString();
		debt.setCode(debtCode);
		debt.setDescription("�������������");
		debt.setFixed(!Boolean.parseBoolean(XmlHelper.getSimpleElementValue(root, "SummCanEdit")));

		DebtRowImpl debtRow = new DebtRowImpl();
		debtRow.setCode(debtCode);
		Currency currency = currencyService.findByAlphabeticCode(XmlHelper.getSimpleElementValue(root, "DebtsCurrCode"));
		Money noMoney = new Money(BigDecimal.ZERO, currency);

		BigDecimal debtValue = getMoney(root, "DebtsSumma");
		debtRow.setDebt(new Money(debtValue, currency));
		debtRow.setCommission(noMoney);
		debtRow.setFine(noMoney);
		debt.addRow(debtRow);

		return debt;
	}

	/**
	 * ���������� ����� � ������������� ��� ���� ��� FNS
	 *
	 * ����� � �������������:
	 * �������	            ���	        �����������	                        ���������
	 * <SummCanEdit>	    Bool        ������ ����� �������� ����� �������	    [1]
	 * <DebtsRoute>	        RouteCode   ��� ������� (��������)	                [1]
	 * <DebtsCurrCode>	    IsoCode     ������ �������������	                [1]
	 * <DebtsSumma>	        DebSumType  ����� �������������	                    [1]
	 *
	 * @param response �������� � ������� � �������������
	 * @return ������ ��������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	//TODO ����� �������� �������� (����� ���������������), � ����������� ������
	//TODO ���� �� ���� � ������� ��� ������ ���� ���-� ����� �����
	static List<Debt> parseMGTSFNSDebtList(Document response) throws GateException, GateLogicException
	{
		List<Debt> result = new ArrayList<Debt>();
		result.add(parseMGTSFNSDebt(response));
		return result;
	}

	private static BigDecimal getMoney(Element root, String tagName) throws GateException
	{
		BigDecimal debtValue = null;
		try
		{
			debtValue = NumericUtil.parseBigDecimal(XmlHelper.getSimpleElementValue(root, tagName));
		}
		catch (ParseException e)
		{
			throw new GateException("������������ ������ ����� �������������", e);
		}
		return debtValue;
	}

	/**
	 * ������������ �������� ���� ������a �������������

	 * ������ �������������:
	 * �������	         ���	        �����������	                  ���������
	 * <Route>	        RouteCode      ��� ������� (��������)          [1]
	 * <DebitCard>	    CardInf        ���������� �� ����� ��������	   [1]
	 * <RecIdentifier>	Requisite      ����� ���������� ���������	   [1]
	 *
	 * @param recipient ����������
	 * @param keyFields �������� ����
	 * @throws GateException
	 */
	//TODO ����� �������� �������� (����� ���������������), � ����������� ������
	//TODO ���� �� ���� � ������� ��� ������ ���� ���-� ����� �����
	static void fillDebtRequest(GateMessage message, Recipient recipient, List<Field> keyFields) throws GateException
	{
		//��� ������� (��������)
		RequestHelper.appendRouteCode(message, "Route", Long.valueOf((String) recipient.getSynchKey()));
		//���������� �� ����� ��������
		appendCardInf(message, keyFields);
		//����� ���������� ���������
		Field identifier = BillingPaymentHelper.getFieldById(keyFields, Constants.REC_IDENTIFIER_FIELD_NAME);
		message.addParameter(Constants.REC_IDENTIFIER_FIELD_NAME, identifier.getValue());
	}

	//TODO ����� �������� �������� (����� ���������������), � ����������� ������
	//TODO ���� �� ���� � ������� ��� ������ ���� ���-� ����� �����
	private static void appendCardInf(GateMessage message, List<Field> keyFields) throws GateException
	{
		Field cardNumberField = BillingPaymentHelper.getFieldById(keyFields, "$$CLIENT_CARD_NUMBER");
		Field cardExpireDateField = BillingPaymentHelper.getFieldById(keyFields, "$$CLIENT_CARD_EXPIRE_DATE");
		Object value = cardExpireDateField.getValue();
		RequestHelper.appendCardInf(message, (String) cardNumberField.getValue(), DateHelper.toCalendar((Date) value));
	}

	/**
	 * ������������ �������� ���� ������a �������������

	 * ������ �������������:
	 * �������	         ���	        �����������	                  ���������
	 * <Route>	        RouteCode      ��� ������� (��������)          [1]
	 * <DebitCard>	    CardInf        ���������� �� ����� ��������	   [1]
	 * <RecIdentifier>	Requisite      ����� ���������� ���������	   [1]
	 *
	 * @param message ���������
	 * @param payment ������
	 * @throws GateException
	 */
	public static void fillDebtRequest(GateMessage message, CardPaymentSystemPayment payment) throws GateException
	{
		try
		{
			//��� ������� (��������)
			RequestHelper.appendRouteCode(message, "Route", Long.valueOf(payment.getReceiverPointCode()));
			//���������� �� ����� ��������
			RequestHelper.appendCardInf(message, payment.getChargeOffCard(), payment.getChargeOffCardExpireDate());
			//����� ���������� ���������
			Field identifier = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.REC_IDENTIFIER_FIELD_NAME);
			message.addParameter(Constants.REC_IDENTIFIER_FIELD_NAME, identifier.getValue());
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ���������� ����� � ������������� ��� ���
	 *
	 *  ����� � �������������:
	 * �������	                    ���	            �����������	                                                             ���������
	 * <SummCanEdit>	            Bool        ������ ����� �������� ����� �������.                                        [1]
	 *                                  (������ ����� �false�, ������ ����� ������).
	 * <PayDebtsList>	 ������ ��������������. ������ ������ ��������, ��� � ������� ��� ������� �������������.	        [0..n]
	 *      <DebtsRoute>	        RouteCode   ��� ������� (��������)	                                                    [1]
	 *  	<DebtsNumber>	        Long        ����� �������������	                                                        [1]
	 *      <DebtsPeriod>	        PayPeriod   ������ �������������	                                                    [1]
	 *  	<DebtsCurrCode>	        IsoCode     ������ �������������	                                                    [1]
	 *      <DebtsSumma>	        DebSumType  ����� �������������, ������ ���� �������������, ��������� �� �����������.	[1]
	 *  	<DebtsComission>	    Money       ����� ��������, ������ ���� �������������	                                [1]
	 *  	<DebtsName>	            C-50        �������� �������������	                                                    [1]
	 *  	<DebtsCases>    �������� ������	                                                                                [0�2]
	 *  		<CaseNumber>	    Long        ����� ���� ������������ E-pay ��� ���� ����� �������� ������.	            [1]
	 *  		<CaseDesc>	        C-50        �������� �������� ������	                                                [1]
	 *          <CaseSumma>	        DebSumType  ����� ������������� �������� ������, ������ ���� �������������,
	 *                                          ��������� �� �����������.	                                                [1]
	 *          <CaseComission>    Money       ����� �������� �������� ������, ������ ���� �������������	                        [1]
	 *
	 * ����������: ���� <DebtsCases> �� �������� �������� ���������,
	 * ����� ������������� � �������� ������� �� ����� <DebtsSumma> � <DebtsComission>.
	 * ���� <DebtsCases> �������� 1 ��� 2 �������� ������,
	 * ����� ������������� � �������� ������� �� ����� <CaseSumma> � <CaseComission>.
	 *
	 * @param response �������� � ������� � �������������
	 * @return ������ ��������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static List<Debt> parseGKHDebts(Document response) throws GateException, GateLogicException
	{
		Element root = response.getDocumentElement();
		final CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		final boolean summCanEdit = !Boolean.parseBoolean(XmlHelper.getSimpleElementValue(root, "SummCanEdit"));

		final List<Debt> result = new ArrayList<Debt>();
		try
		{
			XmlHelper.foreach(root, "PayDebtsList", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					Currency currency = currencyService.findByAlphabeticCode(XmlHelper.getSimpleElementValue(element, "DebtsCurrCode"));
					DebtImpl debt = new DebtImpl();
					String debtCode = RequestHelper.getRouteCode(element, "DebtsRoute").toString();
					debt.setCode(debtCode);
					debt.setDescription(XmlHelper.getSimpleElementValue(element, "DebtsName"));
					debt.setFixed(summCanEdit);
					debt.setPeriod(RequestHelper.getDebtsPeriod(element, "DebtsPeriod"));
					String debtsNumber = XmlHelper.getSimpleElementValue(element, "DebtsNumber");
					debt.setCode(debtCode + "^" + debtsNumber);
					List<DebtRow> rows = parseGKHDebtRows(element, currency, debtsNumber);

					if (rows.isEmpty())
					{
						DebtRowImpl debtRow = new DebtRowImpl();
						debtRow.setCode(debtsNumber);
						BigDecimal debtValue = getMoney(element, "DebtsSumma");
						debtRow.setDebt(new Money(debtValue, currency));
						BigDecimal commission = getMoney(element, "DebtsComission");
						debtRow.setCommission(new Money(commission, currency));
						rows.add(debtRow);
					}
					debt.setRows(rows);
					result.add(debt);
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		return result;
	}

	/**
	 *  <DebtsCases>    �������� ������	                                                                                [0�2]
	 *  	<CaseNumber>	    Long        ����� ���� ������������ E-pay ��� ���� ����� �������� ������.	            [1]
	 *  	<CaseDesc>	        C-50        �������� �������� ������	                                                [1]
	 *      <CaseSumma>	        DebSumType  ����� ������������� �������� ������, ������ ���� �������������,
	 *                                      ��������� �� �����������.	                                                [1]
	 *      <CaseComission>     Money       ����� �������� �������� ������, ������ ���� �������������                   [1]
	 */
	private static List<DebtRow> parseGKHDebtRows(Element element, final Currency currency, final String debtsNumber) throws GateException
	{
		try
		{
			final List<DebtRow> rows = new ArrayList<DebtRow>();
			XmlHelper.foreach(element, "DebtsCases", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					DebtRowImpl debtRow = new DebtRowImpl();
					debtRow.setCode(debtsNumber + "-" + XmlHelper.getSimpleElementValue(element, "CaseNumber"));
					debtRow.setDescription(XmlHelper.getSimpleElementValue(element, "CaseDesc"));
					BigDecimal debtValue = getMoney(element, "CaseSumma");
					debtRow.setDebt(new Money(debtValue, currency));
					BigDecimal commission = getMoney(element, "CaseComission");
					debtRow.setCommission(new Money(commission, currency));
					rows.add(debtRow);
				}
			});
			return rows;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ���������� ����� �� Rostelecom
	 *
	 * ����� �������������:
	 * �������	             ���	        �����������	                        ���������
	 *	<SummCanEdit>        Bool        ������ ����� �������� ����� �������. (������ ����� �false�, ������ ����� ������).	     [1]
	 *	<PayDebtsList>		             ������ ��������������. ������ ������ ��������, ��� � ������� ��� ������� �������������. [0..n]
	 *	    <DebtsRoute>	 RouteCode   ��� ������� (��������)	                [1]
	 *	    <DebtsNumber>	 Long        ����� �������������	                [1]
	 *      <DebtsAccNumber> NC-20       ����� ����� �������������              [1]
	 *	    <DebtsPeriod>	 PayPeriod   ������ �������������	                [1]
	 *	    <DebtsCurrCode>	 IsoCode     ������ �������������	                [1]
	 *	    <DebtsSumma>	 DebSumType  ����� �������������, ������ ���� �������������, ��������� �� �����������.	            [1]
	 *	    <DebtsName>	     C-50        �������� �������������	                [1]
	 *
	 * @param response  - �����
	 * @return - ������ ������������
	 */
	public static List<Debt> parseRostelecomDebts(Document response) throws GateException
	{
		Element root = response.getDocumentElement();
		final CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		final boolean summCanEdit = !Boolean.parseBoolean(XmlHelper.getSimpleElementValue(root, "SummCanEdit"));

		final List<Debt> result = new ArrayList<Debt>();
		try
		{
			XmlHelper.foreach(root, "PayDebtsList", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					DebtImpl debt = new DebtImpl();
					DebtRowImpl debtRow = new DebtRowImpl();
					List<DebtRow> debtRows = new ArrayList<DebtRow>();

					debt.setCode(RequestHelper.getRouteCode(element, "DebtsRoute").toString());

					// ���������� ��� ������������� ������������� ��������� ����� ����������� �������� DebtsAccNumber
					// � DebtsNumber ��� �������� ������� �������� �������� DebtsAccNumber �� ���� ��������������.
					String debtsNumber = XmlHelper.getSimpleElementValue(element, "DebtsNumber");
					String debtsAccNumber = XmlHelper.getSimpleElementValue(element, "DebtsAccNumber");
					StringBuilder code = new StringBuilder();
					code.append(debtsNumber).append(Constants.DELIMITER).append(debtsAccNumber);
					debtRow.setCode(code.toString());

					debt.setPeriod(RequestHelper.getDebtsPeriod(element, "DebtsPeriod"));
					debt.setDescription(XmlHelper.getSimpleElementValue(element, "DebtsName"));
					debt.setFixed(summCanEdit);
					Currency currency = currencyService.findByAlphabeticCode(XmlHelper.getSimpleElementValue(element, "DebtsCurrCode"));
					debtRow.setDebt(new Money(getMoney(element, "DebtsSumma"), currency));
					debtRows.add(debtRow);
					debt.setRows(debtRows);
					result.add(debt);
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		return result;
	}
}
