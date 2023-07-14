package ru.softlab.phizicgate.rsloansV64.loans;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.LoanInfo;
import com.rssl.phizic.gate.loans.LoanOpeningClaim;
import com.rssl.phizic.gate.loans.QuestionnaireAnswer;
import com.rssl.phizic.gate.payments.owner.ClientInfo;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import com.rssl.phizic.utils.GroupResultHelper;
import ru.softlab.phizicgate.rsloansV64.junit.RSLoans64GateTestCaseBase;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LoansServiceImplTest extends RSLoans64GateTestCaseBase
{
	LoansServiceImpl loansService = new LoansServiceImpl(gateFactory);
	private static final String CLIENT_ID = "31787";
	private static final String LOAN_ID = "980";
	private static final String CLAIM_ID = "1932";

	public void testGetLoans() throws Exception
	{
		/*List<Loan> list = loansService.getLoans(CLIENT_ID);
		assertNotNull(list);*/
	}

	public void testGetSchedule() throws Exception
	{
		/*List<Loan> list = loansService.getLoans(CLIENT_ID);
		assertNotNull(list);
		Loan loan = list.get(0);
		List<ScheduleItem> schedule = loansService.getSchedule(loan);
		assertNotNull(schedule);*/
	}

	public void testGetNextScheduleItem() throws Exception
	{
		/*List<Loan> list = loansService.getLoans(CLIENT_ID);
		assertNotNull(list);
		Loan loan = list.get(0);
		ScheduleItem item = loansService.getNextScheduleItem(loan);
		assertNotNull(item);*/
	}

	public void testGetLoan() throws Exception
	{		
		Loan loan = GroupResultHelper.getOneResult(loansService.getLoan(LOAN_ID));
		assertNotNull(loan);
	}

	public void testGetLoanInfo() throws Exception
	{
		Loan loan = GroupResultHelper.getOneResult(loansService.getLoan(LOAN_ID));		
		LoanInfo info = GroupResultHelper.getOneResult(loansService.getLoanInfo(loan));
		assertNotNull(info);
	}

	public void testGetLoanByClaim() throws Exception
	{
		Loan loan = loansService.getLoanByClaim(new LoanOpeningClaim(){
			public Money getLoanAmount()
			{
				return null;
			}

			public Money getSelfAmount()
			{
				return null;
			}

			public Money getObjectAmount()
			{
				return null;
			}

			public DateSpan getDuration()
			{
				return null;
			}

			public String getConditionsId()
			{
				return null;
			}

			public String getOfficeExternalId()
			{
				return null;
			}

			public Iterator<QuestionnaireAnswer> getQuestionnaireIterator()
			{
				return null;
			}

			public Iterator<LoanOpeningClaim> getGuarantorClaimsIterator()
			{
				return null;
			}

			public Money getApprovedAmount()
			{
				return null;
			}

			public void setApprovedAmount(Money amount)
			{

			}

			public DateSpan getApprovedDuration()
			{
				return null;
			}

			public void setApprovedDuration(DateSpan dateSpan)
			{

			}

			public String getClaimNumber()
			{
				return null;
			}

			public void setClaimNumber(String claimNumber)
			{

			}

			public String getExternalId()
			{
				return CLAIM_ID;
			}

			public void setExternalId(String externalId)
			{

			}

			public State getState()
			{
				return null;
			}

			public Calendar getExecutionDate()
			{
				return null;
			}

			public void setExecutionDate(Calendar executionDate)
			{

			}

			public void setState(State state)
			{

			}

			public Long getId()
			{
				return null;
			}

			public Calendar getClientCreationDate()
			{
				return null;
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

			public Office getOffice()
			{
				return null;
			}

			public void setOffice(Office office)
			{

			}

			public Class<? extends GateDocument> getType()
			{
				return null;
			}

			public FormType getFormType()
			{
				return null;
			}

			public Money getCommission()
			{
				return null;
			}

			public void setCommission(Money commission)
			{

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

			public void setConfirmedEmployeeInfo(EmployeeInfo info)
			{

			}

			public CreationType getClientCreationChannel()
			{
				return null;
			}

			public CreationType getClientOperationChannel()
			{
				return null;
			}

			public void setClientOperationChannel(CreationType channel)
			{

			}

			public CreationType getAdditionalOperationChannel()
			{
				return null;
			}

			public void setAdditionalOperationChannel(CreationType channel)
			{

			}

			public String getDocumentNumber()
			{
				return null;
			}

			public Calendar getAdmissionDate()
			{
				return null;
			}

			public boolean isTemplate()
			{
				return false;
			}

			public List<WriteDownOperation> getWriteDownOperations()
			{
				return null;
			}

			public void setWriteDownOperations(List<WriteDownOperation> list)
			{
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
		});
		assertNotNull(loan);
	}
}