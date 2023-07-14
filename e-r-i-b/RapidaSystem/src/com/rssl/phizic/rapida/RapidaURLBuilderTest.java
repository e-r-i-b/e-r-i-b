package com.rssl.phizic.rapida;

import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizic.gate.payments.systems.recipients.Service;
import com.rssl.phizic.gate.payments.owner.ClientInfo;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;
import com.rssl.common.forms.doc.CreationType;
import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author Krenev
 * @ created 29.11.2007
 * @ $Author$
 * @ $Revision$
 */
public class RapidaURLBuilderTest extends RSSLTestCaseBase
{

	private Currency defautCurrency;
	private RapidaURLBuilder builder;
	private RapidaService rapidaOnlineService;

	protected void setUp() throws Exception
	{
		super.setUp();
		final CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		defautCurrency = currencyService.findByAlphabeticCode("USD");
		builder = new RapidaURLBuilder();
		rapidaOnlineService = RapidaFactory.service(RapidaService.class);
	}

	public void testBuildPaymentRequest() throws Exception
	{
		AccountPaymentSystemPayment payment = createTestPayment();
		String request = builder.buildPaymentRequest(payment);
		assertNotNull(request);
	}

	public void testBuildCheckPaymentRequest() throws Exception
	{
		AccountPaymentSystemPayment payment = createTestPayment();
		String request = builder.buildCheckPaymentRequest(payment);
		assertNotNull(request);
	}

	public void testSendCheckPaymentRequest() throws Exception
	{
		AccountPaymentSystemPayment payment = createTestPayment();
		String request = builder.buildCheckPaymentRequest(payment);
		assertNotNull(request);
		Document document = rapidaOnlineService.sendPayment(request);
		assertNotNull(document);
	}

	public void testSendPaymentRequest() throws Exception
	{
		AccountPaymentSystemPayment payment = createTestPayment();
		String request = builder.buildPaymentRequest(payment);
		assertNotNull(request);
		Document document = rapidaOnlineService.sendPayment(request);
		assertNotNull(document);
	}

	private AccountPaymentSystemPayment createTestPayment()
	{
		return new AccountPaymentSystemPayment()
		{
			private String externalId;
			private State state;
			private Money commission;
			private Office office;
			public String receiverAccount;
			private Calendar executionDate;
			private String salesCheck;
			private String operationCode;
			private List<WriteDownOperation> writeDownOperations = new ArrayList<WriteDownOperation>();

			/**
	 * @return код биллинга, в который отправляется платеж
			 */
			public String getBillingCode()
			{
				return null;
			}

			/**
	        * Установить код биллинга
			 * @param billingCode код биллинга
			 */
			public void setBillingCode(String billingCode)
			{
			}

			public String getReceiverPointCode()
			{
				return "403";
			}

			public Long getReceiverInternalId()
			{
				return null;
			}

			public List<Field> getExtendedFields()
			{
				List<Field> fields = new ArrayList<Field>();
				fields.add(createField("кватрира", "220", "12", true));
				fields.add(createField("ФИО", "221", "ФАНТУЗОВ", false));
				fields.add(createField("Номер счета", "224", "0123456789", true));
				fields.add(createField("Дата выставления счета", "226", "20062007", true));
				fields.add(createField("Код телфонного узла", "509", "85", true));
				return fields;
			}

			private Field createField(String name, String code, Object value, boolean mandatory)
			{
				CommonField field = new CommonField();
				field.setName(name);
				field.setExternalId(code);
				field.setRequired(mandatory);
				field.setValue(value);
				return field;
			}

			public String getGround()
			{
				return "ground";
			}

			public String getChargeOffAccount()
			{
				return "12345678901234567890";
			}

			public String getChargeOffCard()
			{
				return "1234567891234567";
			}

			public Calendar getChargeOffCardExpireDate()
			{
				return null;
			}

			public Money getChargeOffAmount()
			{
				return new Money(new BigDecimal(102), defautCurrency);
			}

			public void setChargeOffAmount(Money amount)
			{

			}

			public Money getDestinationAmount()
			{
				return null;
			}

			public void setDestinationAmount(Money amount)
			{

			}

			public InputSumType getInputSumType()
			{
				return InputSumType.CHARGEOFF;
			}

			public CurrencyRate getDebetSaleRate()
			{
				return null;
			}

			public CurrencyRate getDebetBuyRate()
			{
				return null;
			}

			public CurrencyRate getCreditSaleRate()
			{
				return null;
			}

			public CurrencyRate getCreditBuyRate()
			{
				return null;
			}

			public BigDecimal getConvertionRate()
			{
				return null;
			}

			public void setAuthorizeCode(String authorizeCode)
			{
			}

			public String getAuthorizeCode()
			{
				return null;
			}

			public Calendar getAuthorizeDate()
			{
				return null;
			}

			public void setAuthorizeDate(Calendar authorizeDate)
			{
			}

			public String getExternalId()
			{
				return externalId;
			}

			public void setExternalId(String externalId)
			{
				this.externalId = externalId;
			}

			public State getState()
			{
				return state;
			}

			public void setState(State state)
			{
				this.state = state;
			}

			public Long getId()
			{
				return 1205L;
			}

			public Calendar getClientCreationDate()
			{
				return DateHelper.getCurrentDate();
			}

			public Calendar getClientOperationDate()
			{
				return null;
			}

			public void setClientOperationDate(Calendar clientOperationDate)
			{

			}

			public Calendar getAdditionalOperationDate()
			{
				return null;
			}

			public Long getInternalOwnerId()
			{
				return null;
			}

			public String getExternalOwnerId()
			{
				return null;
			}

			public void setExternalOwnerId(String externalOwnerId)
			{
			}

			public Class<? extends GateDocument> getType()
			{
				return AccountPaymentSystemPayment.class;
			}

			public FormType getFormType()
			{
				return null;
			}

			public Money getCommission()
			{
				return commission;
			}

			public void setCommission(Money commission)
			{
				this.commission = commission;
			}

			public CommissionOptions getCommissionOptions()
			{
				return null;
			}

			public ClientInfo getClientInfo()
			{
				return null;
			}

			public EmployeeInfo getCreatedEmployeeInfo() throws GateException
			{
				return null;
			}

			public EmployeeInfo getConfirmedEmployeeInfo() throws GateException
			{
				return null;
			}

			public CreationType getClientCreationChannel()
			{
				return null;
			}

			public CreationType getClientOperationChannel()
			{
				return null;
			}

			public CreationType getAdditionalOperationChannel()
			{
				return null;
			}

			public Office getOffice()
			{
				return office;
			}

			public void setOffice(Office office)
			{
				this.office = office;
			}

			public String getDocumentNumber()
			{
				return "404";
			}

			public Calendar getAdmissionDate()
			{
				return null;
			}

			public boolean isTemplate()
			{
				return false;
			}

			public String getReceiverINN()
			{
				return null;
			}

			public String getReceiverAccount()
			{
				return receiverAccount;
			}

			public ResidentBank getReceiverBank()
			{
				return null;
			}

			public void setReceiverAccount(String receiverAccount)
			{
				this.receiverAccount = receiverAccount;
			}

			public String getReceiverTransitAccount()
			{
				return null;
			}

			public void setReceiverTransitAccount(String receiverTransitAccount)
			{

			}

			public void setReceiverINN(String receiverINN)
			{

			}

			public void setReceiverKPP(String receiverKPP)
			{

			}

			public void setReceiverBank(ResidentBank receiverBank)
			{

			}

			public void setReceiverBank(String receiverBank)
			{

			}

			public ResidentBank getReceiverTransitBank()
			{
				return null;
			}

			public String getReceiverPhone()
			{
				return null;
			}

			public void setReceiverPhone(String receiverPhone)
			{

			}

			public String getReceiverNameForBill()
			{
				return null;
			}

			public void setReceiverNameForBill(String receiverNameForBill)
			{

			}

			public boolean isNotVisibleBankDetails()
			{
				return false;
			}

			public void setNotVisibleBankDetails(boolean notVisibleBankDetails)
			{

			}

			public Code getReceiverOfficeCode()
			{
				return null;
			}

			public void setReceiverOfficeCode(Code code)
			{

			}

			public void setExtendedFields(List<Field> extendedFields)
			{
			}

			public void setRecipientInfo(RecipientInfo info)
			{
			}

			public void setGround(String ground)
			{
			}

			public String getReceiverName()
			{
				return null;
			}

			public void setReceiverName(String receiverName)
			{
			}

			public String getReceiverKPP()
			{
				return null;
			}

			public void setRegisterNumber(String registerNumber)
			{

			}

			public void setRegisterString(String registerString)
			{

			}

			public String getIdFromPaymentSystem()
			{
				return null;
			}

			public void setIdFromPaymentSystem(String id)
			{
			}

			public void setReceiverPointCode(String receiverPointCode)
			{

			}

			public RecipientInfo getRecipientInfo()
			{
				return null;
			}

			public Calendar getExecutionDate()
			{
				return executionDate;
			}

			public void setExecutionDate(Calendar executionDate)
			{
				this.executionDate = executionDate;
			}

			public String getBillingClientId()
			{
				return null;
			}

			public Service getService()
			{
				return null;
			}

			public void setService(Service service)
			{
				
			}

			public String getSalesCheck()
			{
				return salesCheck;
			}

			public void setSalesCheck(String salesCheck)
			{
				this.salesCheck = salesCheck;
			}

			public String getOperationCode()
			{
				return operationCode;
			}

			public void setOperationCode(String operationCode)
			{
				this.operationCode = operationCode;
			}

			public String getMbOperCode()
			{
				return null;
			}

			public void setMbOperCode(String mbOperCode)
			{
			}

			public Long getSendNodeNumber()
			{
				return null;
			}

			public void setSendNodeNumber(Long nodeNumber)
			{
			}

			public TemplateInfo getTemplateInfo()
			{
				return null;
			}

			public void setTemplateInfo(TemplateInfo templateInfo)
			{

			}

			public Map<String, String> getFormData() throws GateException
			{
				return null;
			}

			public void setFormData(Map<String, String> formData)
			{

			}

			public List<WriteDownOperation> getWriteDownOperations()
			{
				return writeDownOperations;
			}

			public void setWriteDownOperations(List<WriteDownOperation> writeDownOperations)
			{
				this.writeDownOperations = writeDownOperations;
			}
		};
	}
}
