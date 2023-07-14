package com.rssl.phizic.business.dictionaries.jbt;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeAdapter;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;

import java.io.IOException;
import java.util.*;

/**
 * @author hudyakov
 * @ created 21.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class BillingJBTFetcher extends JBTFetcherBase
{
	private static final String QUERY_PREFIX = "com.rssl.phizic.operations.dictionaries.jbt.UnloadJBTOperation.billing_";

	private static final BillingService billingService = new BillingService();

	private String[] providerIds;       //идентификаторы поставщиков услуг
	private UnloadingState state;
	private String sourceName;
	private String billingId;


	public void initialize(String[] providerIds, UnloadingState state, String billingId) throws BusinessException
	{
		this.providerIds = providerIds;
		this.state = state;
		this.billingId = billingId;
		this.sourceName = getSourceName();
	}

	public String getSourceName() throws BusinessException
	{
		return billingService.getById(Long.valueOf(billingId)).getCode();
	}

	public List<JurPayment> getSource(final Date dateFrom, final Date dateTo) throws BusinessException
	{
		try
		{
			final String nameQuery = QUERY_PREFIX + (state == UnloadingState.ALL ? UnloadingState.EXCEPT : state).toString().toLowerCase();
			final EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();

			return HibernateExecutor.getInstance().execute(new HibernateAction<List<JurPayment>>()
				{
					public List<JurPayment> run(Session session) throws Exception
					{
						ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), nameQuery);
						query.setParameterList("serviceProviders", providerIds);
						query.setParameter("unloadPeriodDateFrom", dateFrom);
						query.setParameter("unloadPeriodDateTo",   dateTo);
						query.setParameter("billingId",            billingId);
						query.setParameter("employeeLoginId",      employeeData.getEmployee().getLogin().getId());
						query.setParameter("allTbAccess", employeeData.isAllTbAccess());
						return query.executeList();
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Map<String, StringBuilder> downloadArhive(List<? extends AbstractPaymentDocument> payments, String[] date) throws BusinessException, IOException, DocumentException
	{
		Map<String, StringBuilder> entries = new HashMap<String, StringBuilder>();
		ServiceProviderBase provider = null;
		StringBuilder currentData = null;
		for (AbstractPaymentDocument payment: payments)
		{
			JurPayment jurPayment = (JurPayment) payment;

			String receiverPointCode = jurPayment.getReceiverPointCode();
			String compareReceiverPointCode = receiverPointCode.indexOf("@") > - 1 ? receiverPointCode.substring(receiverPointCode.indexOf("@")+1):receiverPointCode;
			String providerSynchKey = (provider != null) ? (String)provider.getSynchKey() : "";
			String compareSynchKey = providerSynchKey.indexOf("@") > - 1 ? providerSynchKey.substring(providerSynchKey.indexOf("@")+1):providerSynchKey;
			// если обрабатываем первую запись, или сменился поставщик, то создаем новый файл в архиве
			if ((provider == null) || (!compareSynchKey.equals(compareReceiverPointCode)))
			{
				provider = serviceProviderService.findBySynchKey(receiverPointCode);
				SBRFOfficeAdapter office = getSBRFOfficeAdapterForProvider(provider);
				StringBuilder entryName = new StringBuilder();
				entryName.append(sourceName).append("_").append(provider.getCode()).append("_").append(office.getCode().getFields().get("branch")).
						append("_").append(date[0]).append("_").append(date[1]).append("_").append(date[2]).append(".ikfl");
				currentData = new StringBuilder(getFieldsDetail(provider, jurPayment));
				entries.put(entryName.toString(), currentData);

			}
			else
			{
				currentData.append(getFieldsDetail(provider, jurPayment));
			}	
		}
		return entries;
	}

	private String getFieldsDetail(ServiceProviderBase provider, JurPayment payment) throws BusinessException
	{
		StringBuilder builder = new StringBuilder();
		SBRFOfficeAdapter office = getSBRFOfficeAdapterForProvider(provider);
		String paymentId = payment.getId().toString();
		builder.append(paymentId.length()>4 ? paymentId.substring(paymentId.length()-4) : StringHelper.appendLeadingZeros(paymentId, 4)).append(ATTR_DELIMITER);
		builder.append(StringHelper.appendLeadingZeros(String.format(DATESTAMP, payment.getExecutionDate().getTime()),10)).append(ATTR_DELIMITER);//  String.format(DATESTAMP, payment.getExecutionDate().getTime())).append(ATTR_DELIMITER);
		builder.append(StringHelper.appendLeadingZeros(office.getCode().getBranch(), 5)).append(ATTR_DELIMITER);
		builder.append("00888").append(ATTR_DELIMITER);
		builder.append("99999").append(ATTR_DELIMITER);
		builder.append("000").append(ATTR_DELIMITER);
		builder.append(provider.getNSICode() == null ? "0" : provider.getNSICode()).append(ATTR_DELIMITER);
		builder.append("119").append(ATTR_DELIMITER);
		builder.append(ZERO).append(ATTR_DELIMITER);
		builder.append(StringHelper.appendLeadingZeros(String.format(DATESTAMP, Calendar.getInstance().getTime()),10)).append(ATTR_DELIMITER);   //String.format(DATESTAMP, Calendar.getInstance().getTime())).append(ATTR_DELIMITER);
		builder.append(StringHelper.appendLeadingZeros(String.format(DATESTAMP, Calendar.getInstance().getTime()),10)).append(ATTR_DELIMITER);  // String.format(DATESTAMP, Calendar.getInstance().getTime())).append(ATTR_DELIMITER);
		builder.append(payment.getExactAmount().getDecimal().toString()).append(ATTR_DELIMITER);
		builder.append("0.00").append(ATTR_DELIMITER);
		String corrAccount = payment.getReceiverBank().getAccount();
		builder.append(!StringHelper.isEmpty(corrAccount)?corrAccount:"0").append(ATTR_DELIMITER);
		builder.append(payment.getReceiverAccount()).append(ATTR_DELIMITER);
		builder.append(payment.getReceiverINN()).append(ATTR_DELIMITER);
		builder.append(ZERO).append(ATTR_DELIMITER);
		builder.append(" ").append(ATTR_DELIMITER);
		builder.append("   ").append(ATTR_DELIMITER);
		builder.append(payment.getReceiverBIC()).append(ATTR_DELIMITER);
		builder.append(ZERO).append(ATTR_DELIMITER);
		builder.append(ZERO).append(ATTR_DELIMITER);
		builder.append(ZERO).append(ATTR_DELIMITER);
		String transitAccount = provider.getTransitAccount();
		String account = transitAccount.substring(transitAccount.length()-7);
		builder.append(account).append(ATTR_DELIMITER);
		builder.append(ZERO).append(ATTR_DELIMITER);
		builder.append(ZERO).append(ATTR_DELIMITER);
		Money commission = payment.getCommission();
		String commissionAsString = commission !=null? String.valueOf(commission.getAsCents()): "0";
		builder.append(commissionAsString).append(ATTR_DELIMITER);
		getExtendedFieldsDetail(payment, builder);
		builder.append(ATTR_DELIMITER).append("\r\n");

		return builder.toString();
	}

	public void getExtendedFieldsDetail(JurPayment payment, StringBuilder builder) throws BusinessException
	{
		StringBuilder result = new StringBuilder();
		result.append(ATTR_VALUES_DELIMITER).append("0").append(ATTR_VALUES_DELIMITER);
		try
		{
			List<Field> fields = payment.getExtendedFields();
			int i=1;

			for (Field field: fields)
			{
				Object value = field.getValue();
				if(i++ < fields.size())
				{
					result.append(StringHelper.getEmptyIfNull(value)).append(ATTR_VALUES_DELIMITER);
				}
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
		builder.append(StringHelper.appendLeadingZeros(String.valueOf(result.length() + 4), 4)).append(result);
	}
}
