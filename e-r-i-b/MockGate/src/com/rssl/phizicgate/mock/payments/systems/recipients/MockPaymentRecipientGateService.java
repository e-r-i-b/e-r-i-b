package com.rssl.phizicgate.mock.payments.systems.recipients;

import com.rssl.phizgate.common.payments.systems.recipients.CalendarFieldImpl;
import com.rssl.phizgate.common.payments.systems.recipients.FieldImpl;
import com.rssl.phizgate.common.payments.systems.recipients.ListFieldImpl;
import com.rssl.phizgate.common.payments.systems.recipients.ServiceImpl;
import com.rssl.phizgate.common.routable.RecipientImpl;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizicgate.mock.clients.ClientImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 17.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class MockPaymentRecipientGateService extends AbstractService implements com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService
{
	public MockPaymentRecipientGateService(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Найти получателей в биллинге по счету, БИКу и ИНН
	 * @param account счет получателя
	 * @param bic бик банка получателя
	 * @param inn инн получателя
	 * @param billing биллинг, в котором нуджно найти получателя
	 * @return список получателей удовлетворяющих результату. если получтели не найдены - пустой список
	 */
	public List<Recipient> getRecipientList(String account, String bic, String inn, Billing billing) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public List<Recipient> getPersonalRecipientList(String billingClientId, Billing billing) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public List<Field> getPersonalRecipientFieldsValues(Recipient recipient, String billingClientId) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public RecipientInfo getRecipientInfo(Recipient recipient, final List<Field> fields, String debtCode) throws GateException, GateLogicException
	{
		return new RecipientInfo(){

			public String getINN()
			{
				return "7875489765";
			}

			public String getKPP()
			{
				return "213123123";
			}

			public String getAccount()
			{
				return "40817810855555555555";
			}

			public ResidentBank getBank()
			{
				ResidentBank bank = new ResidentBank();
				bank.setBIC("049853792");
				bank.setShortName("Короткое имя Mock-банка");
				bank.setName("Mock-bank");
				bank.setAccount("30101810800000000792");
				return bank;
			}

			public String getPayerAccount()
			{
				if (fields == null || fields.isEmpty())
				{
					return null;
				}
				return (String) fields.get(0).getValue();
			}

			public String getTransitAccount()
			{
				return "getTransitAccount";
			}
		};
	}

	public Recipient getRecipient(String recipientId) throws GateException, GateLogicException
	{
		RecipientImpl recipient = new RecipientImpl();
		recipient.setService(new ServiceImpl("serviceCode","serviceName"));
		recipient.setName("recipientName");
		recipient.setDescription("Блок с информацией по получателю средств содержит комментарий для клиента по получателю, который поможет клиенту заполнить форму оплаты товаров и услуг.");
		recipient.setSynchKey(recipientId);
		recipient.setMain(null);
		return recipient;
	}

	public List<Field> getRecipientFields(Recipient recipient,List<Field> keyFields) throws GateException, GateLogicException
	{
		List<Field> list = new ArrayList<Field>();

		FieldImpl simpleField= new FieldImpl();
		simpleField.setName("простое текстовое поле");
		simpleField.setExternalId("E1");
		simpleField.setType(FieldDataType.string);
		simpleField.setKey(true);
		simpleField.setHint("Подсказка по заполнению");
		simpleField.setDescription("Описание поля");
		list.add(simpleField);

		FieldImpl readonlyField= new FieldImpl();
		readonlyField.setName("нередaктируемое поле");
		readonlyField.setExternalId("E2");
		readonlyField.setEditable(false);
		readonlyField.setDefaultValue("значение поля");
		readonlyField.setDescription("Описание нередактируемого поля");
		readonlyField.setType(FieldDataType.string);
		list.add(readonlyField);

		FieldImpl integerField= new FieldImpl();
		integerField.setName("простое числовое поле");
		integerField.setExternalId("E3");
		integerField.setType(FieldDataType.number);
		integerField.setHint("Подсказка по заполнению числового поля");
		list.add(integerField);

		FieldImpl integerSizedField= new FieldImpl();
		integerSizedField.setName("числовое поле с размером");
		integerSizedField.setExternalId("E31");
		integerSizedField.setMinLength(10L);
		integerSizedField.setMaxLength(13L);
		integerSizedField.setType(FieldDataType.number);
		list.add(integerSizedField);

		FieldImpl integerMinSizedField= new FieldImpl();
		integerMinSizedField.setName("числовое поле с минимальным размером");
		integerMinSizedField.setExternalId("E32");
		integerMinSizedField.setMinLength(10L);
		integerMinSizedField.setType(FieldDataType.number);
		list.add(integerMinSizedField);

		FieldImpl integerMaxSizedField= new FieldImpl();
		integerMaxSizedField.setName("числовое поле с максимальным размером");
		integerMaxSizedField.setExternalId("E33");
		integerMaxSizedField.setMaxLength(10L);
		integerMaxSizedField.setType(FieldDataType.number);
		list.add(integerMaxSizedField);

		FieldImpl hiddenField= new FieldImpl();
		hiddenField.setName("скрытое поле");
		hiddenField.setExternalId("E4");
		hiddenField.setType(FieldDataType.number);
		hiddenField.setVisible(false);
		list.add(hiddenField);

		FieldImpl amountField= new FieldImpl();
		amountField.setName("поле суммы");
		amountField.setExternalId("E5");
		amountField.setType(FieldDataType.money);
        list.add(amountField);

		
		FieldImpl mainAmountField= new FieldImpl();
		mainAmountField.setName("поле основной суммы");
		mainAmountField.setExternalId("E6");
		mainAmountField.setType(FieldDataType.money);
		mainAmountField.setMainSum(true);
		list.add(mainAmountField);

		FieldImpl dateField= new FieldImpl();
		dateField.setName("поле дата");
		dateField.setExternalId("E61");
		dateField.setType(FieldDataType.date);
		list.add(dateField);

		ListFieldImpl listField= new ListFieldImpl();
		listField.setName("поле список");
		listField.setExternalId("E7");
		listField.setType(FieldDataType.list);
		List<ListValue> values = new ArrayList<ListValue>();
		values.add(new ListValue("v__1", "1"));
		values.add(new ListValue("v__2", "2"));
		values.add(new ListValue("v__3", "3"));
		listField.setValues(values);
		list.add(listField);
//*
		CalendarFieldImpl brokenCalendarField= new CalendarFieldImpl();
		brokenCalendarField.setName("brokenCalendarField");
		brokenCalendarField.setExternalId("E8");
		brokenCalendarField.setType(FieldDataType.calendar);
		brokenCalendarField.setPeriod(CalendarFieldPeriod.broken);
		list.add(brokenCalendarField);
/*/
		CalendarFieldImpl unbrokenCalendarField = new CalendarFieldImpl();
		unbrokenCalendarField.setName("unbrokenCalendarField");
		unbrokenCalendarField.setExternalId("E8");
		unbrokenCalendarField.setType(FieldDataType.calendar);
		unbrokenCalendarField.setPeriod(CalendarFieldPeriod.unbroken);
		list.add(unbrokenCalendarField);
//*/
		FieldImpl debtField= new FieldImpl();
		debtField.setName("Задолженность");
		debtField.setExternalId("debt");
		debtField.setType(FieldDataType.money);
		debtField.setEditable(false);

		list.add(debtField);
		FieldImpl keyField = new FieldImpl();
		keyField.setName("ключевое поле");
		keyField.setExternalId("keyField");
		keyField.setType(FieldDataType.string);
		keyField.setEditable(true);
		keyField.setKey(true);
		keyField.setRequired(true);

		list.add(keyField);
		
		return list;
	}

	public List<Recipient> getRecipientList(Billing billing) throws GateException, GateLogicException
	{
		return null;
	}

	public List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException
	{
		List<Field> list = new ArrayList<Field>();

		FieldImpl keyField = new FieldImpl();
		keyField.setName("ключевое поле");
		keyField.setExternalId("keyField");
		keyField.setType(FieldDataType.string);
		keyField.setEditable(true);
		keyField.setKey(true);
		keyField.setRequired(true);

		list.add(keyField);
		return list;
	}

	public Client getCardOwner(String cardId, Billing billing) throws GateException, GateLogicException
	{
		ClientImpl client = new ClientImpl();
		client.setFirstName("Александр");
		client.setSurName("Дюма");
		client.setFullName("Александр Дюма");
		client.setShortName("Алекс Дюма");
		client.setDisplayId("граф Александр Дюма");
		return client;
	}
}
