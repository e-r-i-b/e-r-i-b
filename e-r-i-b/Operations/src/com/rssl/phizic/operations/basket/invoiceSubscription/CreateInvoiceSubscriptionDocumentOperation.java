package com.rssl.phizic.operations.basket.invoiceSubscription;

import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntity;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.operations.restrictions.AccountingEntityRestriction;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateESBAutoPayOperation;

import java.util.*;

/**
 * �������� �������� �������� ��� ������� ����
 * @author niculichev
 * @ created 04.05.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateInvoiceSubscriptionDocumentOperation extends CreateESBAutoPayOperation
{
	private static final SimpleService simpleService = new SimpleService();
	private AccountingEntity accountingEntity;

	public void initialize(DocumentSource source, Long receiverId) throws BusinessException, BusinessLogicException
	{
		CreateInvoiceSubscriptionPayment payment = (CreateInvoiceSubscriptionPayment) source.getDocument();

		Long accountingEntityId = payment.getAccountingEntityId();
		if(accountingEntityId != null && accountingEntityId != 0L)
		{
			AccountingEntity temp = simpleService.findById(AccountingEntity.class, accountingEntityId);
			if(temp == null)
				throw new BusinessException("�� ������ ������ ����� � id = " + accountingEntityId);

			AccountingEntityRestriction restriction = (AccountingEntityRestriction) getRestriction();
			if(!restriction.accept(temp))
				throw new BusinessException("������ ����� � id = " + accountingEntityId + " �� ����������� �������.");

			this.accountingEntity = temp;
		}

		super.initialize(source, receiverId, ObjectEvent.CLIENT_EVENT_TYPE);
	}

	/**
	 * @return ��� ���������(���@seriesAndNumber) � ���������� �� ����� � ������ ���������
	 */
	public Map<String, String> getSeriesAndNumberPersonDocuments() throws BusinessException
	{
		return MaskPaymentFieldUtils.buildDocumentSeriesAndNumberValues(
				PersonHelper.getDocumentForProfile(getPerson().getPersonDocuments()));
	}

	/**
	 * @return ������ �����, � ������� �������� ��������� ��������
	 */
	public AccountingEntity getAccountingEntity()
	{
		return accountingEntity;
	}

	public List<FieldDescription> getProviderAllServicesFields() throws BusinessException
	{
		List<FieldDescription> allFieldDescriptions = super.getProviderAllServicesFields();
		List<FieldDescription> result = new ArrayList<FieldDescription>();
		for(FieldDescription field: allFieldDescriptions)
		{
			if(field.isKey())
				result.add(field);
		}
		return result;
	}
}
