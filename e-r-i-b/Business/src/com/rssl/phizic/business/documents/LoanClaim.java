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
	 * Сумма запрашиваемого кредита
	 *
	 * @return сумма
	 */
	public Money getLoanAmount()
	{
		String amount = getNullSaveAttributeStringValue(CLIENT_REQUEST_AMOUNT_ATTRIBUTE_NAME);
		String currency = getNullSaveAttributeStringValue(LOAN_CURRENCY_ATTRIBUTE_NAME);
		return createMoney(amount, currency);
	}

	/**
	 * Собственные средства заемщика.
	 * Может быть null.
	 *
	 * @return сумма
	 */
	public Money getSelfAmount()
	{
		return null;//TODO
	}

	/**
	 * Стоимость приобретаемой собственности (товара).
	 * Может быть null.
	 *
	 * @return сумма
	 */
	public Money getObjectAmount()
	{
		return null; //TODO
	}

	/**
	 * Срок запрашиваемого кредита
	 *
	 * @return срок
	 */
	public DateSpan getDuration()
	{
		String duration = getNullSaveAttributeStringValue(CLIENT_REQUEST_TERM_ATTRIBUTE_NAME);
		return new DateSpan(0, Integer.parseInt(duration), 0);
	}

	/**
	 * Внешний ID определяющий условия кредита
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
	 * Внешний ID подразделения банка в котором предоставляется кредит
	 * Domain: ExternalID
	 *
	 * @return внешний ID
	 */
	public String getOfficeExternalId()
	{
		return getNullSaveAttributeStringValue(OFFICE_EXTERNAL_ID_ATTRINUTE_NAME);
	}

	/**
	 * Итератор ответов на вопросы анкеты
	 *
	 * @return итератор
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
	 * Ответ на вопрос анкеты
	 * форматы
	 * -строка - as is
	 * -дата - yyyy.mm.dd
	 * -число - nnnnn.nnnn (число знаков до и после запятой произвольное)
	 * -даньги - nnnnnnnnnnnnnn.nn (два знака после запятой)
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
	 * Итератор по анкетам поручителей.
	 * Если поручителей нет, то итератор на пустой список.
	 *
	 * @return итератор
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
	 * Сумма утвержденного кредита
	 *
	 * @return сумма
	 */
	public Money getApprovedAmount()
	{
		String amount = getNullSaveAttributeStringValue(BANK_ACCEPT_AMOUNT_ATTRIBUTE_NAME);
		String currency = getNullSaveAttributeStringValue(LOAN_CURRENCY_ATTRIBUTE_NAME);
		return createMoney(amount, currency);
	}

	/**
	 * Установить утвержденную сумму. Заполняется гейтом
	 *
	 * @param amount сумма
	 */
	public void setApprovedAmount(Money amount)
	{
		//что за ересь приходится писать.
		setNullSaveAttributeStringValue(BANK_ACCEPT_AMOUNT_ATTRIBUTE_NAME, amount.getDecimal().toPlainString());
	}

	/**
	 * Срок утвержденного кредита
	 *
	 * @return срок
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
	 * Установленный комментарий
	 *
	 * @return комментарий
	 */
	public String getComment()
	{
		return getNullSaveAttributeStringValue(BANK_COMMENT_ATTRIBUTE_NAME);
	}

	/**
	 * Установить комментарий
	 */
	public void setComment(String comment)
	{
		setNullSaveAttributeStringValue(BANK_COMMENT_ATTRIBUTE_NAME, comment);
	}

	/**
	 * Установить утрержденный срок. Заполняется гейтом
	 *
	 * @param dateSpan Срок
	 */
	public void setApprovedDuration(DateSpan dateSpan)//TODO
	{
		//что за ересь приходится писать.
		setNullSaveAttributeStringValue(BANK_ACCEPT_TERM_ATTRIBUTE_NAME, dateSpan.toString());
	}

	/**
	 * Номер заявки для отображения пользователю
	 * @return
	 */
	public String getClaimNumber()
	{
		return getNullSaveAttributeStringValue(RS_LOANS_NUMBER_ATTRIBUTE_NAME);
	}

	/**
	 *  Установить номер заявки для отображения пользователю
	 * @param claimNumber номер заявки в Loans
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
		 * ID документа во внешней системе.
		 * ID должен быть уникален среди всех плетежей.
		 * Domain: ExternalID
		 *
		 * @return id
		 */
		public String getExternalId()
		{
			return LoanClaim.this.getExternalId();
		}

		/**
		 * Установить привязку к документу во внешней системе
		 *
		 * @param externalId (Domain: ExternalID)
		 */
		public void setExternalId(String externalId)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * Текущий статус документа
		 *
		 * @return статус
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
		 * Установить новый статус
		 *
		 * @param state статус
		 */
		public void setState(State state)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * Внутренний ID документа - уникален в для каждого конкретного типа документов
		 * Domain: InternalID
		 */
		public Long getId()
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * Дата-время создания документа
		 * Domain: DateTime
		 *
		 * @return дата создания
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
		 * Внутренний ID клиента-владельца (создателя) документа
		 * Domain: ExternalID
		 *
		 * @return идентификатор плательщика
		 */
		public Long getInternalOwnerId() throws GateException
		{
			return LoanClaim.this.getInternalOwnerId();
		}

		/**
		 * Внешний ID клиента-владельца (создателя) документа
		 * Domain: ExternalID
		 *
		 * @return идентификатор плательщика
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
		 * Внешний ID офиса, в который был отправлен документ
		 * Domain: ExternalID
		 *
		 * @return идентификатор офиса
		 */
		public Office getOffice()
		{
			return LoanClaim.this.getOffice();
		}

		/**
		 * Установить внешний ID офиса, в который был отправлен документ
		 * на тот случай, если гейт отправит документ в другой офис.
		 * Domain: ExternalID
		 */
		public void setOffice(Office office)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * Фактичский тип документа
		 *
		 * @return фактичский тип документа
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
		 * Комиссия взымаемая за отправку(обработку etc). Если комиссия отсутствует == null.
		 */
		public Money getCommission()
		{
			return LoanClaim.this.getCommission();
		}

		/**
		 * Установить комиссию. Комиссия, установленная перед отправкой может быть
		 * проигнорирована by underlying system.
		 * В первую очередь это свойство предназначено для информирования пользователя о размере комиссии.
		 *
		 * @param commission размер комиссии. Если комиссия не взимается == null
		 */
		public void setCommission(Money commission)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * Параметры взымания комиссии.
		 * Конкретные платежи документы определяют ограничения на возможные варианты.
		 *
		 * По умолчанию поддерживается только взымание комиссии с указанного счета (other)
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
		 * Получить номер документа в ИКФЛ, который отображется пользователю.
		 *
		 * @return номер документа
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
		 * Является ли документ шаблоном
		 * @return true является
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
		 * Сумма запрашиваемого кредита
		 *
		 * @return сумма
		 */
		public Money getLoanAmount()
		{
			return LoanClaim.this.getLoanAmount();
		}

		/**
		 * Собственные средства заемщика.
		 * Может быть null.
		 *
		 * @return сумма
		 */
		public Money getSelfAmount()
		{
			return LoanClaim.this.getSelfAmount();
		}

		/**
		 * Стоимость приобретаемой собственности (товара).
		 * Может быть null.
		 *
		 * @return сумма
		 */
		public Money getObjectAmount()
		{
			return LoanClaim.this.getObjectAmount();
		}

		/**
		 * Срок запрашиваемого кредита
		 *
		 * @return срок
		 */
		public DateSpan getDuration()
		{
			return LoanClaim.this.getDuration();
		}

		/**
		 * Внешний ID определяющий условия кредита
		 * Domain: ExternalID
		 *
		 * @return id
		 */
		public String getConditionsId()
		{
			return LoanClaim.this.getConditionsId();
		}

		/**
		 * Внешний ID подразделения банка в котором предоставляется кредит
		 * Domain: ExternalID
		 *
		 * @return внешний ID
		 */
		public String getOfficeExternalId()
		{
			return LoanClaim.this.getOffice().getSynchKey().toString();
		}

		/**
		 * Итератор ответов на вопросы анкеты
		 *
		 * @return итератор
		 */
		public Iterator<QuestionnaireAnswer> getQuestionnaireIterator()
		{
			return LoanClaim.this.getQuestionnaireIterator(prefix);
		}

		/**
		 * Итератор по анкетам поручителей.
		 * Если поручителей нет, то итератор на пустой список.
		 *
		 * @return итератор
		 */
		public Iterator<LoanOpeningClaim> getGuarantorClaimsIterator()
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * Сумма утвержденного кредита
		 *
		 * @return сумма
		 */
		public Money getApprovedAmount()
		{
			return LoanClaim.this.getApprovedAmount();
		}

		/**
		 * Установить утвержденную сумму. Заполняется гейтом
		 *
		 * @param amount сумма
		 */
		public void setApprovedAmount(Money amount)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * Срок утвержденного кредита
		 *
		 * @return срок
		 */
		public DateSpan getApprovedDuration()
		{
			return LoanClaim.this.getApprovedDuration();
		}

		/**
		 * Установить утрержденный срок. Заполняется гейтом
		 *
		 * @param dateSpan Срок
		 */
		public void setApprovedDuration(DateSpan dateSpan)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * Номер заявки для отображения пользователю
		 * @return
		 */
		public String getClaimNumber()
		{
			return LoanClaim.this.getClaimNumber();
		}

		/**
		 *  Установить номер заявки для отображения пользователю
		 * @param claimNumber номер заявки в Loans
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
