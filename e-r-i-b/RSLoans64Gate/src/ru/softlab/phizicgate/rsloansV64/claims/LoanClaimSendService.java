package ru.softlab.phizicgate.rsloansV64.claims;

import com.rssl.phizic.gate.loans.LoanOpeningClaim;
import com.rssl.phizic.gate.exceptions.GateException;
import ru.softlab.phizicgate.rsloansV64.jpub.Ikfltaclaimparm;
import ru.softlab.phizicgate.rsloansV64.connection.GateRSLoans64Executor;

import java.util.*;

/**
 * @author Omeliyanchuk
 * @ created 11.01.2008
 * @ $Author$
 * @ $Revision$
 */

public class
		LoanClaimSendService
{
	/**
	 * Внутренний класс, для хранения информации по заявки.
	 */
	private class ClaimPInfoStorge
	{
		private LoanOpeningClaim claim;
		private Ikfltaclaimparm params;

		public LoanOpeningClaim getClaim()
		{
			return claim;
		}

		public Ikfltaclaimparm getParams()
		{
			return params;
		}

		ClaimPInfoStorge(LoanOpeningClaim claim, Ikfltaclaimparm params)
		{
			this.claim = claim;
			this.params = params;
		}
	}
	public void send(LoanOpeningClaim claim) throws GateException
	{
		ClaimParamsBuilder claimParamsBuilder = new ClaimParamsBuilder();

		final Ikfltaclaimparm mainClaimParam = claimParamsBuilder.build(claim, null);
		if(mainClaimParam == null)
			throw new GateException("Не найдены параметры заявки на кредит");

		List<ClaimPInfoStorge> guarantorClaims = new ArrayList<ClaimPInfoStorge>();
		Iterator<LoanOpeningClaim> guarantorIterator = claim.getGuarantorClaimsIterator();
		//сначала парсим все параметры всех заявок, потом отправляем.
		while(guarantorIterator != null && guarantorIterator.hasNext())
		{
			LoanOpeningClaim guarantorClaim = guarantorIterator.next();
			Ikfltaclaimparm params = claimParamsBuilder.build( guarantorClaim, claim );
			if(params !=null)
			{
				guarantorClaims.add( new ClaimPInfoStorge(guarantorClaim, params) );
			}
		}

//   FUNCTION ModifyClaim  (ClaimParm IN OUT TAClaimParm, ClaimID IN OUT NUMBER, DebitorClaimID IN NUMBER, DebitorContractNumber IN NUMBER) RETURN NUMBER;

		sendClaim(mainClaimParam, claim, null,null);

		Integer contractNumber=0;
		for (ClaimPInfoStorge infoStorge : guarantorClaims)
		{
			sendClaim(infoStorge.getParams(), infoStorge.getClaim(), claim ,++contractNumber);
		}
	}

	private void sendClaim(Ikfltaclaimparm mainClaimParam, LoanOpeningClaim claim, LoanOpeningClaim debtorClaim,Integer ContractNumber) throws GateException
	{
		SendClaimLoanAction claimAction = new SendClaimLoanAction();
		claimAction.setClaimParams(mainClaimParam);
		if(debtorClaim!=null && debtorClaim.getExternalId()!=null)
		{
			claimAction.setDebtorClaimId( Integer.parseInt(debtorClaim.getExternalId()) );
			claimAction.setDebtorContractNumber(ContractNumber);
		}

		try
		{
			GateRSLoans64Executor.getInstance(false).execute(claimAction);
		}
		catch(Exception ex)
		{
			throw new GateException("Ошибка при отправке заявки", ex);
		}
		//todo может оставить тут только отправку, а обработку результата выше
		if(debtorClaim==null)
		{
		  claim.setExternalId(String.valueOf( claimAction.getClaimId() ) );
		  claim.setClaimNumber( claimAction.getClaimNumber() );
		}
	}
}
