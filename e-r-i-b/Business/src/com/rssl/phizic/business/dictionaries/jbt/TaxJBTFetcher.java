package com.rssl.phizic.business.dictionaries.jbt;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeAdapter;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.gate.payments.RUSTaxPayment;
import com.rssl.phizic.person.Person;
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
public class TaxJBTFetcher extends JBTFetcherBase
{
	private static final String QUERY_PREFIX = "com.rssl.phizic.operations.dictionaries.jbt.UnloadJBTOperation.tax_";
	private static final String SOURCE_NAME  = "nalog";
	private static final String NAME_DELIMITER = "&";

	private String[] providerIds;
	private UnloadingState state;
	private String sourceName;

	public void initialize(String[] providerIds, UnloadingState state, String billingId) throws BusinessException
	{
		this.providerIds = providerIds;
		this.state = state;
		this.sourceName = getSourceName();
	}

	public String getSourceName() throws BusinessException
	{
		return SOURCE_NAME;
	}

	public List<? extends AbstractPaymentDocument> getSource(final Date dateFrom, final Date dateTo) throws BusinessException
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
			if(RUSTaxPayment.class != payment.getType())
			{
				continue;
			}

			RurPayment taxPayment = (RurPayment) payment;
			String receiverINN = taxPayment.getReceiverINN();
			String providerINN = (provider != null) ? provider.getINN() : "";

			// если обрабатываем первую запись, или сменился поставщик, то создаем новый файл в архиве
			if ((provider == null) || (!receiverINN.equals(providerINN)))
			{
				provider = serviceProviderService.findTaxProviderByINN(receiverINN);
				if (taxPayment.isFullPayment())
				{
					continue;   //см. ТЗ		
				}
				SBRFOfficeAdapter office = getSBRFOfficeAdapterForProvider(provider);
				StringBuilder entryName = new StringBuilder();
				entryName.append(sourceName).append("_").append(provider.getCode()).append("_").append(office.getCode().getFields().get("branch")).
						append("_").append(date[0]).append("_").append(date[1]).append("_").append(date[2]).append(".ikfl");
				currentData = new StringBuilder(getFieldsDetail(provider, taxPayment));
				entries.put(entryName.toString(), currentData);
			}
			else
			{
				currentData.append(getFieldsDetail(provider, taxPayment));
			}
		}
		return entries;
	}

	private String getFieldsDetail(ServiceProviderBase provider, RurPayment payment) throws BusinessException
	{
		StringBuilder builder = new StringBuilder();
		SBRFOfficeAdapter office = getSBRFOfficeAdapterForProvider(provider);
		String paymentId = payment.getId().toString();
		builder.append(paymentId.length()>4 ? paymentId.substring(paymentId.length()-4) : StringHelper.appendLeadingZeros(paymentId, 4)).append(ATTR_DELIMITER);
		builder.append(String.format(DATESTAMP, payment.getClientCreationDate().getTime())).append(ATTR_DELIMITER);
		builder.append(StringHelper.appendLeadingZeros(office.getCode().getBranch(), 5)).append(ATTR_DELIMITER);
		builder.append("00888").append(ATTR_DELIMITER);
		builder.append("99999").append(ATTR_DELIMITER);
		builder.append("000").append(ATTR_DELIMITER);
		builder.append(provider.getNSICode() == null ? "0" : provider.getNSICode()).append(ATTR_DELIMITER);
		builder.append("119").append(ATTR_DELIMITER);
		builder.append(ZERO).append(ATTR_DELIMITER);
		builder.append(String.format(DATESTAMP, Calendar.getInstance().getTime())).append(ATTR_DELIMITER);
		builder.append(String.format(DATESTAMP, Calendar.getInstance().getTime())).append(ATTR_DELIMITER);
		builder.append(payment.getChargeOffAmount().getDecimal().toString()).append(ATTR_DELIMITER);
		builder.append("0.00").append(ATTR_DELIMITER);
		String receiverCorAccount = payment.getReceiverCorAccount();
		builder.append(!StringHelper.isEmpty(receiverCorAccount)?receiverCorAccount:"0").append(ATTR_DELIMITER);
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
		builder.append(getExtendedFieldsDetail(payment)).append(ATTR_DELIMITER).append("\r\n");

		return builder.toString();
	}

	private String getExtendedFieldsDetail(RurPayment payment) throws BusinessException
	{
		StringBuilder builder = new StringBuilder();

		BusinessDocumentOwner documentOwner = payment.getOwner();
		Person person = documentOwner.getPerson();
		builder.append(ATTR_VALUES_DELIMITER).append(ZERO).append(ATTR_VALUES_DELIMITER)
			   .append(payment.getTaxDocumentNumber()).append(ATTR_VALUES_DELIMITER)
			   .append(payment.getReceiverINN()).append(ATTR_VALUES_DELIMITER)
			   .append(payment.getReceiverKPP()).append(ATTR_VALUES_DELIMITER)
			   .append(payment.getTaxKBK()).append(ATTR_VALUES_DELIMITER)
			   .append(person.getInn()).append(ATTR_VALUES_DELIMITER)
			   .append(payment.getTaxPaymentStatus()).append(ATTR_VALUES_DELIMITER);

		//Если поле ИНН заполнено, то поля адреса заполнять необязательно   (см. Т.З.)
		if (StringHelper.isEmpty(person.getInn()))
		{
			//todo разобраться с доп.полями код региона (№8), населенный пункт (№12)
			builder.append(ATTR_VALUES_DELIMITER)
				   .append(payment.getPayerPostalCode()).append(ATTR_VALUES_DELIMITER)
				   .append(payment.getPayerDistrict()).append(ATTR_VALUES_DELIMITER)
				   .append(payment.getPayerCity()).append(ATTR_VALUES_DELIMITER)
				   .append(payment.getPayerCity()).append(ATTR_VALUES_DELIMITER)
				   .append(payment.getPayerStreet()).append(ATTR_VALUES_DELIMITER)
				   .append(payment.getPayerHouse()).append(ATTR_VALUES_DELIMITER)
				   .append(payment.getPayerBuilding()).append(ATTR_VALUES_DELIMITER)
				   .append(payment.getPayerFlat()).append(ATTR_VALUES_DELIMITER);
		}
		else
		{
			builder.append(ATTR_VALUES_DELIMITER).append(ATTR_VALUES_DELIMITER).append(ATTR_VALUES_DELIMITER)
				   .append(ATTR_VALUES_DELIMITER).append(ATTR_VALUES_DELIMITER).append(ATTR_VALUES_DELIMITER)
				   .append(ATTR_VALUES_DELIMITER).append(ATTR_VALUES_DELIMITER).append(ATTR_VALUES_DELIMITER);
		}

		//поля 17,18 пока не используются   (см. Т.З.)
		builder.append(ATTR_VALUES_DELIMITER).append(ATTR_VALUES_DELIMITER);

		StringBuilder name = new StringBuilder();
		name.append(NAME_DELIMITER).append(payment.getReceiverName()).append(NAME_DELIMITER).append(StringHelper.appendLeadingZeros(String.valueOf(name.length() + 5), 4));
		builder.append(name).append(ATTR_VALUES_DELIMITER);

		return StringHelper.appendLeadingZeros(String.valueOf(builder.length() + 4), 4) + builder;
	}
}
