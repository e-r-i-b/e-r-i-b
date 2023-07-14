package com.rssl.phizicgate.esberibgate.statistics.exception;

import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 22.11.13
 * @ $Author$
 * @ $Revision$
 *
 * таблица коррел€ции
 */

public class TypesCorrelation
{
	private static final Map<Class, Class> types = new HashMap<Class,Class>();

	static
	{
		addType(IFXRq_Type.class);
		addType(AcctInfoRq_Type.class);
		addType(DepAcctRec_Type.class);
		addType(DepAcctId_Type.class);
		addType(AcctInqRq_Type.class);
		addType(BankAcctId_Type.class);
		addType(DepAcctStmtInqRq_Type.class);
		addType(DepAcctStmtInqRq_TypeDepAcctRec.class);
		addType(ImaAcctInRq_Type.class);
		addType(ImsRec_Type.class);
		addType(ImsAcctId_Type.class);
		addType(BankAcctStmtInqRq_Type.class);
		addType(BankAcctRec_Type.class);
		addType(BankAcctFullStmtInqRq_Type.class);
		addType(CardAcctDInqRq_Type.class);
		addType(CardInfo_Type.class);
		addType(CardAcctId_Type.class);
		addType(BankAcctStmtImgInqRq_Type.class);
		addType(LoanInqRq_Type.class);
		addType(LoanInfoRq_Type.class);
		addType(LoanAcctId_Type.class);
		addType(LoanPaymentRq_Type.class);
		addType(DepoAccInfoRqType.class);
		addType(DepoAcctId_Type.class);
		addType(DepoDeptDetInfoRqType.class);
		addType(DepoDeptCardPayRqType.class);
		addType(PayInfo_Type.class);
		addType(PayInfo_TypeAcctId.class);
		addType(DepoAccTranRqType.class);
		addType(DepoAccSecRegRqType.class);
		addType(XferAddRq_Type.class);
		addType(XferInfo_Type.class);
		addType(SvcAddRq_Type.class);
		addType(ServiceStmtRq_Type.class);
		addType(SvcAcctId_Type.class);
		addType(SvcAcctAudRq_Type.class);
		addType(SvcAcctAudRq_TypeSvcAcct.class);
		addType(SvcAcctDelRq_Type.class);
		addType(SvcAcctDelRq_TypeSvcAcct.class);
		addType(AccStopDocRq_Type.class);
		addType(DocInfo_Type.class);
		addType(CardBlockRq_Type.class);
		addType(DepToNewDepAddRq_Type.class);
		addType(CardToNewDepAddRq_Type.class);
		addType(SetAccountStateRq_Type.class);
		addType(DepToNewIMAAddRq_Type.class);
		addType(XferIMAInfo_Type.class);
		addType(CardToNewIMAAddRq_Type.class);
		addType(CardToIMAAddRq_Type.class);
		addType(IMAToCardAddRq_Type.class);
		addType(IMAAcctId_Type.class);
		addType(CardReissuePlaceRq_Type.class);
		addType(SecuritiesInfoInqRq_Type.class);
		addType(ChangeAccountInfoRq_Type.class);
		addType(BillingPayPrepRq_Type.class);
		addType(BillingPayExecRq_Type.class);
	}

	private static void addType(Class typeClass)
	{
		types.put(typeClass, SystemIdResolver.class);
	}
	
	/**
	 * @return таблица коррел€ции
	 */
	public static Map<Class, Class> getTypes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return types;
	}
}
