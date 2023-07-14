package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.ServiceImpl;
import com.rssl.phizic.business.dictionaries.receivers.PaymentReceiversDictionary;
import com.rssl.phizic.business.dictionaries.receivers.generated.FieldDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.fields.FieldImpl;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Service;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Krenev
 * @ created 16.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class GoodsAndServicesPayment extends AbstractPaymentDocument implements com.rssl.phizic.gate.payments.systems.contact.ContactPayment, AbstractRUSPayment, AccountPaymentSystemPayment
{
	public static final String TRANSFER_PAYMENT_WAY = "transfer";//оплата рублевым переводом
	public static final String CONTACT_PAYMENT_WAY = "contact";//оплата переводом по сети Contact
	public static final String RAPIDA_PAYMENT_WAY = "rapida";//оплата переводом по сети Rapida
	public static final String DESTINATION_ATTRIBUTE_NAME = "destination";
	public static final String RECEIVER_POINT_CODE_ATTRIBUTE_NAME = "contact-code";
	public static final String APPOINTMENT_ATTRIBUTE_NAME = "appointment";
	public static final String RECEIVER_KEY_ATTRIBUTE_NAME = "receiverKey";
	public static final String RECEIVER_TYPE_ATTRIBUTE_NAME = "receiver-type";
	public static final String CODE_SERVICE_ATTRIBUTE_NAME = "codeService";
	public static final String RECEIVER_NAME = "receiver-name";
	public static final String BILLING_CLIENT_ID_ATTRIBUTE_NAME = "billing-client-id";

	public Class<? extends GateDocument> getType()
	{
		String receiverType = getReceiverType();
		if (CONTACT_PAYMENT_WAY.equals(receiverType))
		{
			return com.rssl.phizic.gate.payments.systems.contact.ContactPayment.class;
		}
		if (TRANSFER_PAYMENT_WAY.equals(receiverType))
		{
			return AbstractRUSPayment.class;
		}
		if (RAPIDA_PAYMENT_WAY.equals(receiverType))
		{
			return AccountPaymentSystemPayment.class;
		}
		throw new IllegalStateException("Неизвестный тип получателя " + receiverType);
	}

	/**
	 * @return код биллинга, в который отправляется платеж
	 */
	public String getBillingCode()
	{
		//todo реализовать в рамках запроса CHG020983
		return null;
	}

	/**
	 * Установить код биллинга
	 * @param billingCode код биллинга
	 */
	public void setBillingCode(String billingCode)
	{
		//todo реализовать в рамках запроса CHG020983
	}

	public String getReceiverPointCode()
	{
		return getNullSaveAttributeStringValue(RECEIVER_POINT_CODE_ATTRIBUTE_NAME);
	}

	public Long getReceiverInternalId()
	{
		return null;
	}

	public String getMultiBlockReceiverPointCode()
	{
		return null;
	}

	public void setReceiverPointCode(String receiverPointCode)
	{
		setNullSaveAttributeStringValue(RECEIVER_POINT_CODE_ATTRIBUTE_NAME, receiverPointCode);
	}

	public String getSalesCheck()
	{
		//todo реализовать в рамках запроса CHG020983
		return null;
	}

	public void setSalesCheck(String salesCheck)
	{
		//todo реализовать в рамках запроса CHG020983
	}

	public List<Field> getExtendedFields() throws DocumentException
	{
		ReceiverDescriptor receiverDescriptor = getReceiverDescriptor();
		if (receiverDescriptor == null)
			throw new DocumentException("Не задан описатель получателя receiverDescriptor=null");

		List<Field> extendedFields = new ArrayList<Field>();

		for (Object desc : receiverDescriptor.getFieldDescriptors())
		{
			FieldDescriptor fieldDescriptor = (FieldDescriptor) desc;
			FieldImpl field = new FieldImpl();
			field.setName(fieldDescriptor.getName());
			field.setExternalId(fieldDescriptor.getCode());
			field.setRequired(Boolean.parseBoolean(fieldDescriptor.getMandatory()));
			Object value = getNullSaveAttributeValue(fieldDescriptor.getName());
			field.setValue(value);
			extendedFields.add(field);
		}
		return extendedFields;
	}

	public String getDestination()
	{
		return getNullSaveAttributeStringValue(DESTINATION_ATTRIBUTE_NAME);
	}

	public void setTransitAccount(String transitAccount)
	{
		//todo для оплаты услуг не нужно
	}

	public String getReceiverSurName()
	{
		return getNullSaveAttributeStringValue(AbstractAccountsTransfer.RECEIVER_SUR_NAME_ATTRIBUTE_NAME);
	}

	public String getReceiverFirstName()
	{
		return getNullSaveAttributeStringValue(AbstractAccountsTransfer.RECEIVER_FIRST_NAME_ATTRIBUTE_NAME);
	}

	public String getReceiverPatrName()
	{
		return getNullSaveAttributeStringValue(AbstractAccountsTransfer.RECEIVER_PATR_NAME_ATTRIBUTE_NAME);
	}

	public String getBillingClientId()
	{
		return getNullSaveAttributeStringValue(BILLING_CLIENT_ID_ATTRIBUTE_NAME);
	}

	/**
	 * Установить идентификатор клиента в биллинге.
	 * Наличие данного поля в платеже позволит биллингу не запрашивать часть полей или заполнять их
	 * значениями уже извесными  биллингу. например, лицевой счет, фио, показания счетчиков и пр.
	 * @param billingClientId идентификатор клиента в биллинге
	 */
	public void setBillingClientId(String billingClientId)
	{
		setNullSaveAttributeStringValue(BILLING_CLIENT_ID_ATTRIBUTE_NAME, billingClientId);
	}

	public Service getService()
	{
		return new ServiceImpl(getNullSaveAttributeStringValue(CODE_SERVICE_ATTRIBUTE_NAME), null);
	}


	public void setService(Service service)
	{

	}

	public String getIdFromPaymentSystem()
	{
		return null;
	}

	public void setIdFromPaymentSystem(String id)
	{

	}

	public void setReceiverName(String receiverName)
	{
		setNullSaveAttributeStringValue(AbstractAccountsTransfer.RECEIVER_NAME_ATTRIBUTE_NAME, receiverName);
	}

	public String getReceiverAccount()
	{
		return getNullSaveAttributeStringValue(AbstractAccountsTransfer.RECEIVER_ACCOUNT_ATTRIBUTE_NAME);
	}

	public Currency getDestinationCurrency() throws GateException
	{
		return null;
	}

	public ResidentBank getReceiverBank()
	{
		//todo реализовать в рамках запроса CHG020983
		return null;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		setNullSaveAttributeStringValue(AbstractAccountsTransfer.RECEIVER_ACCOUNT_ATTRIBUTE_NAME, receiverAccount);
	}

	public String getReceiverTransitAccount()
	{
		//todo реализовать в рамках запроса CHG020983
		return null;
	}

	public void setReceiverTransitAccount(String receiverTransitAccount)
	{
		//todo реализовать в рамках запроса CHG020983
	}

	public void setExtendedFields(List<Field> extendedFields)
	{
	}

	public String getReceiverBIC()
	{
		return getNullSaveAttributeStringValue(RurPayment.RECEIVER_BIC_ATTRIBUTE_NAME);
	}

	public void setReceiverBIC(String receiverBIC)
	{
		setNullSaveAttributeStringValue(RurPayment.RECEIVER_BIC_ATTRIBUTE_NAME, receiverBIC);
	}

	public String getReceiverCorAccount()
	{
		return getNullSaveAttributeStringValue(RurPayment.RECEIVER_COR_ACCOUNT_ATTRIBUTE_NAME);
	}

	public void setReceiverCorAccount(String receiverCorAccount)
	{
		setNullSaveAttributeStringValue(RurPayment.RECEIVER_COR_ACCOUNT_ATTRIBUTE_NAME, receiverCorAccount);
	}

	public String getReceiverINN()
	{
		return getNullSaveAttributeStringValue(RurPayment.RECEIVER_INN_ATTRIBUTE_NAME);
	}

	public void setReceiverINN(String receiverINN)
	{
		setNullSaveAttributeStringValue(RurPayment.RECEIVER_INN_ATTRIBUTE_NAME, receiverINN);
	}

	public String getReceiverKPP()
	{
		return getNullSaveAttributeStringValue(RurPayment.RECEIVER_KPP_ATTRIBUTE_NAME);
	}

	public void setRegisterNumber(String registerNumber)
	{
		//todo реализовать в рамках запроса CHG020983
	}

	public void setRegisterString(String registerString)
	{
		//todo реализовать в рамках запроса CHG020983
	}

	public void setReceiverKPP(String receiverKPP)
	{
		setNullSaveAttributeStringValue(RurPayment.RECEIVER_KPP_ATTRIBUTE_NAME, receiverKPP);
	}

	public void setReceiverBank(ResidentBank receiverBank)
	{
		//todo реализовать в рамках запроса CHG020983
	}

	public ResidentBank getReceiverTransitBank()
	{
		//todo реализовать в рамках запроса CHG020983
		return null;
	}

	public String getReceiverPhone()
	{
		//todo реализовать в рамках запроса CHG020983
		return null;
	}

	public void setReceiverPhone(String receiverPhone)
	{
		//todo реализовать в рамках запроса CHG020983
	}

	public String getReceiverNameForBill()
	{
		//todo реализовать в рамках запроса CHG020983
		return null;
	}

	public void setReceiverNameForBill(String receiverNameForBill)
	{
		//todo реализовать в рамках запроса CHG020983
	}

	public boolean isNotVisibleBankDetails()
	{
		//todo реализовать в рамках запроса CHG020983
		return false;
	}

	public void setNotVisibleBankDetails(boolean notVisibleBankDetails)
	{
		//todo реализовать в рамках запроса CHG020983
	}

	public Code getReceiverOfficeCode()
	{
		//todo реализовать в рамках запроса CHG020983
		return null;
	}

	public void setReceiverOfficeCode(Code code)
	{
		//todo реализовать в рамках запроса CHG020983
	}

	public String getReceiverBankName()
	{
		return getNullSaveAttributeStringValue(RurPayment.RECEIVER_BANK_NAME_ATTRIBUTE_NAME);
	}

	public void setReceiverBankName(String receiverBankName)
	{
		setNullSaveAttributeStringValue(RurPayment.RECEIVER_BANK_NAME_ATTRIBUTE_NAME, receiverBankName);
	}

	public String getReceiverAlias()
	{
		return getNullSaveAttributeStringValue(RurPayment.RECEIVER_ALIAS_ATTRIBUTE_NAME);
	}

	public String getReceiverType()
	{
		return getNullSaveAttributeStringValue(RECEIVER_TYPE_ATTRIBUTE_NAME);
	}

	public void setReceiverType(String receiverType)
	{
		setNullSaveAttributeStringValue(RECEIVER_TYPE_ATTRIBUTE_NAME, receiverType);
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		// сохранить информацию о получателе
		ReceiverDescriptor receiverDescriptor = getReceiverDescriptor();
		setReceiverInfo(receiverDescriptor);
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.EXTERNAL_PAYMENT_OPERATION;
	}

	private ReceiverDescriptor getReceiverDescriptor() throws DocumentException
	{
		String paymentAppointment = getNullSaveAttributeStringValue(APPOINTMENT_ATTRIBUTE_NAME);
		if (paymentAppointment == null)
			throw new DocumentException("Не задан appointment");

		String receiverKey = getNullSaveAttributeStringValue(RECEIVER_KEY_ATTRIBUTE_NAME);
		if (receiverKey == null)
			throw new DocumentException("Не задан recieverKey");

		try
		{
			PaymentReceiversDictionary dictionary = new PaymentReceiversDictionary();
			return dictionary.getReceiverDescriptor(paymentAppointment, receiverKey);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private void setReceiverInfo(ReceiverDescriptor receiverDescriptor) throws DocumentException
	{
		if (receiverDescriptor == null)
			throw new DocumentException("Не задан описатель получателя receiverDescriptor=null");

		String receiverTypeValue = receiverDescriptor.getType();
		if (receiverTypeValue == null || receiverTypeValue.length() == 0)
		{
			throw new DocumentException("Не установлено значение [" + RECEIVER_TYPE_ATTRIBUTE_NAME + "]");
		}

		setReceiverType(receiverTypeValue);
		setReceiverName(receiverDescriptor.getDescription());

		if (CONTACT_PAYMENT_WAY.equals(receiverTypeValue))
		{
			setReceiverPointCode(receiverDescriptor.getContactCode());
		}
		else if (TRANSFER_PAYMENT_WAY.equals(receiverTypeValue))
		{
			setReceiverAccount(receiverDescriptor.getAccount());
			setReceiverCorAccount(receiverDescriptor.getCorAccount());
			setReceiverBIC(receiverDescriptor.getBic());
			setReceiverINN(receiverDescriptor.getInn());
			setReceiverKPP(receiverDescriptor.getKpp());
			setReceiverBankName(receiverDescriptor.getDescription());
		}
		else if (RAPIDA_PAYMENT_WAY.equals(receiverTypeValue))
		{
			setReceiverPointCode(receiverDescriptor.getContactCode());
			setReceiverAccount(receiverDescriptor.getAccount());
		}
		else
		{
			throw new DocumentException("Неизвестное значение [type]: " + receiverTypeValue);
		}
	}

	public String getName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_NAME);
	}

	public String getGround()
	{
		String ground = "";
		try
		{
			for (Field field: getExtendedFields())
				ground = ground.concat(field.getValue().toString());
			//todo нужен разделитель для полей! Может разделитель тоже сделать доп.полем, но не выводить его на форме?
		}
		catch (DocumentException e)
		{
			// todo
		}

		return ground;
	}

	public void setAuthorizeCode(String authorizeCode)
	{
		//todo реализовать в рамках запроса CHG020983
	}

	public String getAuthorizeCode()
	{
		//todo реализовать в рамках запроса CHG020983
		return null;
	}

	public Calendar getAuthorizeDate()
	{
		//todo реализовать в рамках запроса CHG020983
		return null;
	}

	public void setAuthorizeDate(Calendar authorizeDate)
	{
		//todo реализовать в рамках запроса CHG020983
	}
}
