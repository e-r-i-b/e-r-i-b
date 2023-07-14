package com.rssl.phizic.business.documents;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.forms.LoanClaimMetadataLoader;
import com.rssl.phizic.business.loans.claims.LoanClaimDefinitionProvider;
import com.rssl.phizic.business.loans.claims.generated.FieldDescriptor;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.business.loans.products.LoanProductService;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.loans.LoanOpeningClaim;
import com.rssl.phizic.gate.loans.QuestionnaireAnswer;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * @author Krenev
 * @ created 11.01.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaim extends GateExecutableDocument implements LoanOpeningClaim
{
	private static final LoanProductService loanProductsService = new LoanProductService();
	public static final String RS_LOANS_NUMBER_ATTRIBUTE_NAME = "rs-loans-number";
	public static final String BANK_COMMENT_ATTRIBUTE_NAME = "bank-comment";
	public static final String LOAN_KIND_ATTRIBUTE_NAME = "kind";
	public static final String OFFICE_EXTERNAL_ID_ATTRINUTE_NAME = "office";
	public static final String LOAN_PRODUCT_ID_ATRIBUTE_NAME = "product";
	public static final String CLIENT_REQUEST_AMOUNT_ATTRIBUTE_NAME = "client-request-amount";
	public static final String LOAN_CURRENCY_ATTRIBUTE_NAME = "loan-currency";
	public static final String BANK_ACCEPT_AMOUNT_ATTRIBUTE_NAME = "bank-accept-amount";
	public static final String BANK_ACCEPT_TERM_ATTRIBUTE_NAME = "bank-accept-term";
	private static final String CLIENT_REQUEST_TERM_ATTRIBUTE_NAME = "client-request-term";

	public Class<? extends GateDocument> getType()
	{
		return LoanOpeningClaim.class;
	}

	/**
	 * ����� �������������� �������
	 *
	 * @return �����
	 */
	public Money getLoanAmount()
	{
		String amount = getNullSaveAttributeStringValue(CLIENT_REQUEST_AMOUNT_ATTRIBUTE_NAME);
		String currency = getNullSaveAttributeStringValue(LOAN_CURRENCY_ATTRIBUTE_NAME);
		return createMoney(amount, currency);
	}

	/**
	 * ����������� �������� ��������.
	 * ����� ���� null.
	 *
	 * @return �����
	 */
	public Money getSelfAmount()
	{
		return null;//TODO
	}

	/**
	 * ��������� ������������� ������������� (������).
	 * ����� ���� null.
	 *
	 * @return �����
	 */
	public Money getObjectAmount()
	{
		return null; //TODO
	}

	/**
	 * ���� �������������� �������
	 *
	 * @return ����
	 */
	public DateSpan getDuration()
	{
		String duration = getNullSaveAttributeStringValue(CLIENT_REQUEST_TERM_ATTRIBUTE_NAME);
		return new DateSpan(0, Integer.parseInt(duration), 0);
	}

	/**
	 * ������� ID ������������ ������� �������
	 * Domain: ExternalID
	 *
	 * @return id
	 */
	public String getConditionsId()
	{
		try
		{
			return getLoanProduct().getConditionId(getOfficeExternalId(), getLoanAmount().getCurrency());
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * ������� ID ������������� ����� � ������� ��������������� ������
	 * Domain: ExternalID
	 *
	 * @return ������� ID
	 */
	public String getOfficeExternalId()
	{
		return getNullSaveAttributeStringValue(OFFICE_EXTERNAL_ID_ATTRINUTE_NAME);
	}

	/**
	 * �������� ������� �� ������� ������
	 *
	 * @return ��������
	 */
	public Iterator<QuestionnaireAnswer> getQuestionnaireIterator()
	{
		return getQuestionnaireIterator("");
	}

	private Iterator<QuestionnaireAnswer> getQuestionnaireIterator(String prefix)
	{
		LoanClaimDefinitionProvider definitionProvider = new LoanClaimDefinitionProvider();
		try
		{
			List<FieldDescriptor> fields = definitionProvider.getLoanDefinitionFields(getLoanKind());
			List<QuestionnaireAnswer> answers = new ArrayList<QuestionnaireAnswer>();
			for (FieldDescriptor descriptor : fields)
			{
				if (descriptor.getKey() == null || descriptor.getKey().length() == 0)
					continue;

				if (prefix.length() > 0 && !descriptor.isGuarantor())
				{
					continue;
				}
				String value = getFieldValue(descriptor, prefix);
				QuestionnaireAnswer answer = new QuestionnaireAnswerImpl(descriptor.getKey(), value);
				answers.add(answer);
			}
			return answers.listIterator();
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * ����� �� ������ ������
	 * �������
	 * -������ - as is
	 * -���� - yyyy.mm.dd
	 * -����� - nnnnn.nnnn (����� ������ �� � ����� ������� ������������)
	 * -������ - nnnnnnnnnnnnnn.nn (��� ����� ����� �������)
	 */
	private String getFieldValue(FieldDescriptor descriptor, String prefix)//TODO
	{
		String value = getNullSaveAttributeStringValue(prefix + descriptor.getName());
		if (value == null)
		{
			return null;
		}
		String type = descriptor.getType();
		if ("date".equals(type))
		{
			return value.replace("-", ".");
		}
		if ("money".equals(type))
		{
			return String.format("%1$.2f", new BigDecimal(value)).replace(',', '.');
		}
		return value;
	}

	private String getLoanKind()
	{
		return getNullSaveAttributeStringValue(LOAN_KIND_ATTRIBUTE_NAME);
	}

	/**
	 * �������� �� ������� �����������.
	 * ���� ����������� ���, �� �������� �� ������ ������.
	 *
	 * @return ��������
	 */
	public Iterator<LoanOpeningClaim> getGuarantorClaimsIterator()
	{
		try
		{
			List<LoanOpeningClaim> guarantorClaims = new ArrayList<LoanOpeningClaim>();
			for (int i = 0; i < getLoanProduct().getGuarantorsCount(); i++)
			{
				String prefix = String.format(LoanClaimMetadataLoader.GUARANTORS_PREFIX, i + 1);
				guarantorClaims.add(new LoanOpeningClaimImpl(prefix));
			}
			return guarantorClaims.listIterator();
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * ����� ������������� �������
	 *
	 * @return �����
	 */
	public Money getApprovedAmount()
	{
		String amount = getNullSaveAttributeStringValue(BANK_ACCEPT_AMOUNT_ATTRIBUTE_NAME);
		String currency = getNullSaveAttributeStringValue(LOAN_CURRENCY_ATTRIBUTE_NAME);
		return createMoney(amount, currency);
	}

	/**
	 * ���������� ������������ �����. ����������� ������
	 *
	 * @param amount �����
	 */
	public void setApprovedAmount(Money amount)
	{
		//��� �� ����� ���������� ������.
		setNullSaveAttributeStringValue(BANK_ACCEPT_AMOUNT_ATTRIBUTE_NAME, amount.getDecimal().toPlainString());
	}

	/**
	 * ���� ������������� �������
	 *
	 * @return ����
	 */
	public DateSpan getApprovedDuration()
	{
		ExtendedAttribute attribute = getAttribute(BANK_ACCEPT_TERM_ATTRIBUTE_NAME);
		if (attribute == null)
		{
			return null;
		}

		return new DateSpan(attribute.getStringValue());
	}

	/**
	 * ������������� �����������
	 *
	 * @return �����������
	 */
	public String getComment()
	{
		return getNullSaveAttributeStringValue(BANK_COMMENT_ATTRIBUTE_NAME);
	}

	/**
	 * ���������� �����������
	 */
	public void setComment(String comment)
	{
		setNullSaveAttributeStringValue(BANK_COMMENT_ATTRIBUTE_NAME, comment);
	}

	/**
	 * ���������� ������������ ����. ����������� ������
	 *
	 * @param dateSpan ����
	 */
	public void setApprovedDuration(DateSpan dateSpan)//TODO
	{
		//��� �� ����� ���������� ������.
		setNullSaveAttributeStringValue(BANK_ACCEPT_TERM_ATTRIBUTE_NAME, dateSpan.toString());
	}

	/**
	 * ����� ������ ��� ����������� ������������
	 * @return
	 */
	public String getClaimNumber()
	{
		return getNullSaveAttributeStringValue(RS_LOANS_NUMBER_ATTRIBUTE_NAME);
	}

	/**
	 *  ���������� ����� ������ ��� ����������� ������������
	 * @param claimNumber ����� ������ � Loans
	 */
	public void setClaimNumber(String claimNumber)
	{
		setNullSaveAttributeStringValue(RS_LOANS_NUMBER_ATTRIBUTE_NAME, claimNumber);
	}

	private LoanProduct getLoanProduct() throws BusinessException
	{
		String productId = getNullSaveAttributeStringValue(LOAN_PRODUCT_ID_ATRIBUTE_NAME);
		return loanProductsService.findById(Long.valueOf(productId));
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.INTERNAL_PAYMENT_OPERATION;
	}

	private class LoanOpeningClaimImpl implements LoanOpeningClaim
	{
		private String prefix;

		private LoanOpeningClaimImpl(String prefix)
		{

			this.prefix = prefix;
		}

		/**
		 * ID ��������� �� ������� �������.
		 * ID ������ ���� �������� ����� ���� ��������.
		 * Domain: ExternalID
		 *
		 * @return id
		 */
		public String getExternalId()
		{
			return LoanClaim.this.getExternalId();
		}

		/**
		 * ���������� �������� � ��������� �� ������� �������
		 *
		 * @param externalId (Domain: ExternalID)
		 */
		public void setExternalId(String externalId)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * ������� ������ ���������
		 *
		 * @return ������
		 */
		public State getState()
		{
			return LoanClaim.this.getState();
		}

		public Calendar getExecutionDate()
		{
			return LoanClaim.this.getExecutionDate();
		}

		public void setExecutionDate(Calendar executionDate)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * ���������� ����� ������
		 *
		 * @param state ������
		 */
		public void setState(State state)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * ���������� ID ��������� - �������� � ��� ������� ����������� ���� ����������
		 * Domain: InternalID
		 */
		public Long getId()
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * ����-����� �������� ���������
		 * Domain: DateTime
		 *
		 * @return ���� ��������
		 */
		public Calendar getClientCreationDate()
		{
			return LoanClaim.this.getClientCreationDate();
		}

		public Calendar getClientOperationDate()
		{
			return LoanClaim.this.getClientOperationDate();
		}

		public void setClientOperationDate(Calendar clientOperationDate)
		{
			throw new UnsupportedOperationException();
		}

		public Calendar getAdditionalOperationDate()
		{
			return LoanClaim.this.getAdditionalOperationDate();
		}

		/**
		 * ���������� ID �������-��������� (���������) ���������
		 * Domain: ExternalID
		 *
		 * @return ������������� �����������
		 */
		public Long getInternalOwnerId() throws GateException
		{
			return LoanClaim.this.getInternalOwnerId();
		}

		/**
		 * ������� ID �������-��������� (���������) ���������
		 * Domain: ExternalID
		 *
		 * @return ������������� �����������
		 */
		public String getExternalOwnerId()
		{
			return LoanClaim.this.getExternalOwnerId();
		}

		public void setExternalOwnerId(String externalOwnerId)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * ������� ID �����, � ������� ��� ��������� ��������
		 * Domain: ExternalID
		 *
		 * @return ������������� �����
		 */
		public Office getOffice()
		{
			return LoanClaim.this.getOffice();
		}

		/**
		 * ���������� ������� ID �����, � ������� ��� ��������� ��������
		 * �� ��� ������, ���� ���� �������� �������� � ������ ����.
		 * Domain: ExternalID
		 */
		public void setOffice(Office office)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * ���������� ��� ���������
		 *
		 * @return ���������� ��� ���������
		 */
		public Class<? extends GateDocument> getType()
		{
			return LoanOpeningClaim.class;
		}

		public FormType getFormType()
		{
			return LoanClaim.this.getFormType();
		}

		/**
		 * �������� ��������� �� ��������(��������� etc). ���� �������� ����������� == null.
		 */
		public Money getCommission()
		{
			return LoanClaim.this.getCommission();
		}

		/**
		 * ���������� ��������. ��������, ������������� ����� ��������� ����� ����
		 * ��������������� by underlying system.
		 * � ������ ������� ��� �������� ������������� ��� �������������� ������������ � ������� ��������.
		 *
		 * @param commission ������ ��������. ���� �������� �� ��������� == null
		 */
		public void setCommission(Money commission)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * ��������� �������� ��������.
		 * ���������� ������� ��������� ���������� ����������� �� ��������� ��������.
		 *
		 * �� ��������� �������������� ������ �������� �������� � ���������� ����� (other)
		 */
		public CommissionOptions getCommissionOptions()
		{
			return LoanClaim.this.getCommissionOptions();
		}

		public EmployeeInfo getCreatedEmployeeInfo() throws GateException
		{
			return LoanClaim.this.getCreatedEmployeeInfo();
		}

		public EmployeeInfo getConfirmedEmployeeInfo() throws GateException
		{
			return LoanClaim.this.getConfirmedEmployeeInfo();
		}

		public CreationType getClientCreationChannel()
		{
			return LoanClaim.this.getClientCreationChannel();
		}

		public CreationType getClientOperationChannel()
		{
			return LoanClaim.this.getClientOperationChannel();
		}

		public CreationType getAdditionalOperationChannel()
		{
			return LoanClaim.this.getAdditionalOperationChannel();
		}

		/**
		 * �������� ����� ��������� � ����, ������� ����������� ������������.
		 *
		 * @return ����� ���������
		 */
		public String getDocumentNumber()
		{
			return LoanClaim.this.getDocumentNumber();
		}

		public Calendar getAdmissionDate()
		{
			return LoanClaim.this.getAdmissionDate();
		}

		/**
		 * �������� �� �������� ��������
		 * @return true ��������
		 */
		public boolean isTemplate()
		{
			return LoanClaim.this.isTemplate(); 
		}

		public List<WriteDownOperation> getWriteDownOperations()
		{
			return LoanClaim.this.getWriteDownOperations();
		}

		public void setWriteDownOperations(List<WriteDownOperation> list)
		{
			throw new UnsupportedOperationException();
		}

		public String getNextState()
		{
			throw new UnsupportedOperationException();
		}

		public void setNextState(String nextState)
		{
			throw new UnsupportedOperationException();
		}

		public ExternalSystemIntegrationMode getIntegrationMode()
		{
			return LoanClaim.this.getIntegrationMode();
		}

		/**
		 * ����� �������������� �������
		 *
		 * @return �����
		 */
		public Money getLoanAmount()
		{
			return LoanClaim.this.getLoanAmount();
		}

		/**
		 * ����������� �������� ��������.
		 * ����� ���� null.
		 *
		 * @return �����
		 */
		public Money getSelfAmount()
		{
			return LoanClaim.this.getSelfAmount();
		}

		/**
		 * ��������� ������������� ������������� (������).
		 * ����� ���� null.
		 *
		 * @return �����
		 */
		public Money getObjectAmount()
		{
			return LoanClaim.this.getObjectAmount();
		}

		/**
		 * ���� �������������� �������
		 *
		 * @return ����
		 */
		public DateSpan getDuration()
		{
			return LoanClaim.this.getDuration();
		}

		/**
		 * ������� ID ������������ ������� �������
		 * Domain: ExternalID
		 *
		 * @return id
		 */
		public String getConditionsId()
		{
			return LoanClaim.this.getConditionsId();
		}

		/**
		 * ������� ID ������������� ����� � ������� ��������������� ������
		 * Domain: ExternalID
		 *
		 * @return ������� ID
		 */
		public String getOfficeExternalId()
		{
			return LoanClaim.this.getOffice().getSynchKey().toString();
		}

		/**
		 * �������� ������� �� ������� ������
		 *
		 * @return ��������
		 */
		public Iterator<QuestionnaireAnswer> getQuestionnaireIterator()
		{
			return LoanClaim.this.getQuestionnaireIterator(prefix);
		}

		/**
		 * �������� �� ������� �����������.
		 * ���� ����������� ���, �� �������� �� ������ ������.
		 *
		 * @return ��������
		 */
		public Iterator<LoanOpeningClaim> getGuarantorClaimsIterator()
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * ����� ������������� �������
		 *
		 * @return �����
		 */
		public Money getApprovedAmount()
		{
			return LoanClaim.this.getApprovedAmount();
		}

		/**
		 * ���������� ������������ �����. ����������� ������
		 *
		 * @param amount �����
		 */
		public void setApprovedAmount(Money amount)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * ���� ������������� �������
		 *
		 * @return ����
		 */
		public DateSpan getApprovedDuration()
		{
			return LoanClaim.this.getApprovedDuration();
		}

		/**
		 * ���������� ������������ ����. ����������� ������
		 *
		 * @param dateSpan ����
		 */
		public void setApprovedDuration(DateSpan dateSpan)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * ����� ������ ��� ����������� ������������
		 * @return
		 */
		public String getClaimNumber()
		{
			return LoanClaim.this.getClaimNumber();
		}

		/**
		 *  ���������� ����� ������ ��� ����������� ������������
		 * @param claimNumber ����� ������ � Loans
		 */
		public void setClaimNumber(String claimNumber)
		{
			throw new UnsupportedOperationException();
		}

		public String getMbOperCode()
		{
			throw new UnsupportedOperationException();
		}

		public void setMbOperCode(String mbOperCode)
		{
			throw new UnsupportedOperationException();
		}

		public Long getSendNodeNumber()
		{
			return null;
		}

		public void setSendNodeNumber(Long nodeNumber)
		{
			throw new UnsupportedOperationException();
		}
	}
}
