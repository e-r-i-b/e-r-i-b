package ru.softlab.phizicgate.rsloansV64.claims;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.DocumentUpdater;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.LoanClaimStateUpdateInfoImpl;
import com.rssl.phizic.gate.loans.LoanOpeningClaim;
import ru.softlab.phizicgate.rsloansV64.common.DateSpanHelper;
import ru.softlab.phizicgate.rsloansV64.connection.GateRSLoans64Executor;
import ru.softlab.phizicgate.rsloansV64.connection.LoansConnectionAction;
import ru.softlab.phizicgate.rsloansV64.jpub.Ikfltaclaimstate;
import ru.softlab.phizicgate.rsloansV64.jpub.Ikfltclaimstate;
import ru.softlab.phizicgate.rsloansV64.money.CurrencyImpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Maleyev
 * @ created 08.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanOpeningClaimUpdater implements DocumentUpdater, LoansConnectionAction<Void>
{
	private LoanOpeningClaim claim;

	public StateUpdateInfo execute(GateDocument document) throws GateException
	{
		if(! (document instanceof LoanOpeningClaim))
		        throw new GateException("Неверный тип заявки. Должен быть LoanOpeningClaim. Проверьте конфигурационный файл gate-documents-config.xml");

		claim = (LoanOpeningClaim)document;
		try
		{
			GateRSLoans64Executor.getInstance(true).execute(this);
			return new LoanClaimStateUpdateInfoImpl(claim);
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}

	public void setParameters(Map<String, ?> params)
	{
	}

	public Void run(Connection connection) throws Exception
	{
		CallableStatement cst = null ;
		try
		{
			cst = connection.prepareCall("{ call IKFL.GetClaimState(?) }");
			cst.registerOutParameter(1, Types.ARRAY, Ikfltaclaimstate._SQL_NAME);
			Ikfltclaimstate[] st = new Ikfltclaimstate[1];
			st[0] = new Ikfltclaimstate(claim.getExternalId(),"","","","","");
			cst.setObject(1, new Ikfltaclaimstate(st));
			cst.execute();

			Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
			typeMap.put(Ikfltclaimstate._SQL_NAME,Ikfltclaimstate.class);
			Object[] resultParams = (Object[]) cst.getArray(1).getArray(typeMap);
			for (Object param : resultParams)
			{
				Ikfltclaimstate state = (Ikfltclaimstate)param;
				// todo currency null !!!
				CurrencyImpl currency = new CurrencyImpl();
				currency.setCode(state.getCreditsumcurrency());
				claim.setApprovedAmount(new Money(new BigDecimal(state.getCreditsum()),currency));
				claim.setApprovedDuration(DateSpanHelper.getDateSpan(state.getCredittypeduration(),state.getCreditduration()));
			}
		}
		finally
		{
			if(cst != null)
			         cst.close();
 		}
		return null;
	}
}
