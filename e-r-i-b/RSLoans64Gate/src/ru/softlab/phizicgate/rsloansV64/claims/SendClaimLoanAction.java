package ru.softlab.phizicgate.rsloansV64.claims;

import ru.softlab.phizicgate.rsloansV64.connection.LoansConnectionAction;
import ru.softlab.phizicgate.rsloansV64.jpub.Ikfltaclaimparm;
import ru.softlab.phizicgate.rsloansV64.jpub.Ikfltclaimparm;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Types;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Omeliyanchuk
 * @ created 14.01.2008
 * @ $Author$
 * @ $Revision$
 */

public class SendClaimLoanAction implements LoansConnectionAction<Void>
{
	private static final int NEW_CLAIM_ID_PARAM = 1;
	private static final int CLAIM_PARAMS_PARAM = 2;
	private static final int NEW_CLAIM_ID_OUT_PARAM = 3;
	private static final int DEBTOR_PARAM = 4;
	private static final int CONTRACT_NUMBER_PARAM = 5;

	private static final int NEW_CLAIM = 0;

	private static final String RESULT_ERROR_ID = "errCode";//код ошибки из loans
	private static final String RESULT_ERROR_DESCRIPTION = "errDesc";//описание ошибки из loans
	private static final String RESULT_LOAN_NUMBER = "number";//номер созданного договора из loans

	Ikfltaclaimparm claimParams;//параметры заявки
	String claimNumber;//номер заявки(показывается клиент)
	int claimId;//идентификатор заявки
	Integer debtorClaimId ;//идентификатор заявки заемщика
	Integer debtorContractNumber; // Номер контракта в договоре обеспечения

	public Integer getDebtorClaimId()
	{
		return debtorClaimId;
	}

	public void setDebtorClaimId(Integer debtorClaimId)
	{
		this.debtorClaimId = debtorClaimId;
	}

	public void setDebtorContractNumber(Integer debtorContractNumber)
	{
		this.debtorContractNumber = debtorContractNumber;
	}

	public Integer getDebtorContractNumber()
	{
		return debtorContractNumber;
	}

	public Ikfltaclaimparm getClaimParams()
	{
		return claimParams;
	}

	public void setClaimParams(Ikfltaclaimparm claimParams)
	{
		this.claimParams = claimParams;
	}

	public int getClaimId()
	{
		return claimId;
	}

	public void setClaimId(int claimId)
	{
		this.claimId = claimId;
	}

	public String getClaimNumber()
	{
		return claimNumber;
	}

	public void setClaimNumber(String claimNumber)
	{
		this.claimNumber = claimNumber;
	}

	public Void run(Connection connection) throws Exception
	{
		//FUNCTION ModifyClaim  (ClaimParm IN OUT TAClaimParm, ClaimID IN OUT NUMBER, DebitorClaimID IN NUMBER) RETURN NUMBER
		CallableStatement cst = connection.prepareCall("{ ? = call IKFL.ModifyClaim(?,?,?,?) }");
		cst.registerOutParameter(NEW_CLAIM_ID_PARAM, Types.NUMERIC);
		cst.registerOutParameter(CLAIM_PARAMS_PARAM, Types.ARRAY, "IKFLTACLAIMPARM");
		cst.registerOutParameter(NEW_CLAIM_ID_OUT_PARAM, Types.NUMERIC);

		cst.setObject(CLAIM_PARAMS_PARAM, claimParams );
		cst.setInt(NEW_CLAIM_ID_OUT_PARAM, NEW_CLAIM);
		if( getDebtorClaimId()==null )
		{
			cst.setNull(DEBTOR_PARAM, Types.NUMERIC);
			cst.setNull(CONTRACT_NUMBER_PARAM,Types.NUMERIC);
		}
		else
		{
			cst.setInt(DEBTOR_PARAM,getDebtorClaimId());
			cst.setInt(CONTRACT_NUMBER_PARAM,getDebtorContractNumber());
		}
		cst.execute();

		Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
		typeMap.put("IKFLTAClaimParm", Ikfltaclaimparm.class);
		typeMap.put("IKFLTCLAIMPARM", Ikfltclaimparm.class);

		Integer newClaimId = cst.getInt(NEW_CLAIM_ID_OUT_PARAM);
		Object[] resultParams = (Object[]) cst.getArray(CLAIM_PARAMS_PARAM).getArray(typeMap);
		//System.out.println(" \n\n\nClaim return "+newClaimId+"\n\n\n");

		setClaimId(newClaimId);
		proceedOutParams(resultParams);

		return null;
	}

	private void proceedOutParams(Object[] params) throws GateException
	{
		if(params==null)
			return;

		String errorCode = null;
		String errorDescription = null;
		int finded = NEW_CLAIM;
		boolean isClaimNumber = false;

		try
		{
			for (Object param : params)
			{
				Ikfltclaimparm claimParam = (Ikfltclaimparm)param;
				if(RESULT_ERROR_ID.equals(claimParam.getParmid().trim()) )
				{
					errorCode = claimParam.getParmvalue().trim();
					finded++;
				}
				if(RESULT_ERROR_DESCRIPTION.equals(claimParam.getParmid().trim()) )
				{
					errorDescription = claimParam.getParmvalue().trim();
					finded++;
				}
				if(RESULT_LOAN_NUMBER.equals(claimParam.getParmid().trim()))
				{
					setClaimNumber( claimParam.getParmvalue().trim());
					isClaimNumber = true;
					finded++;
				}
				if(finded >= 3)
					break;
			}
		}catch(SQLException ex)
		{
			throw new GateException("Ошибка при разборе результата обработки заявки:", ex);
		}

		if(errorCode != null)
		{
			StringBuilder builder = new StringBuilder();
			builder.append("Не удалось отправить заявку на кредит id. Код ошибки:");
			builder.append(errorCode);
			if(errorDescription != null && errorDescription.length() != NEW_CLAIM)
			{
				builder.append(". Описание ошибки:");
				builder.append(errorDescription);
			}
			throw new GateException(builder.toString());
		}

		if(!isClaimNumber)
			throw new GateException("Не установлен номер заявки:");
    }
}
