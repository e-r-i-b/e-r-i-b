package com.rssl.phizic.business.documents;

import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.gate.depo.SecurityOperationType;
import com.rssl.phizic.gate.documents.GateDocument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lukina
 * @ created 28.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class SecurityRegistrationClaim  extends AbstractDepoAccountClaim implements com.rssl.phizic.gate.claims.SecurityRegistrationClaim
{
	public static final String CORR_DEPOSITARY_ATTRIBUTE_NAME = "corr-depositary"; //Депозитарий/Регистратор
	public static final String INSIDE_CODE_ATTRIBUTE_NAME = "inside-code";    //идентификатор ценной бумаги
	public static final String SECURITY_NAME_ATTRIBUTE_NAME = "security-name";  //наименование ценной бумаги
	public static final String SECURITY_NUMBER_ATTRIBUTE_NAME = "security-number";   //Регистрационный номер ценной бумаги
	public static final String ISSUER_ATTRIBUTE_NAME = "issuer";   //эмитент ценной бумаги

	public static final String DEPOSITARY_OPERATION_NAME = "depositary-operation"; // планируемые операции: депозитарные операции (1)
	public static final String DEPOSIT_OPERATION_NAME = "deposit-operation";  //планируемые операции: залоговые операции  (2)
	public static final String ACCOUNT_OPERATION_NAME = "account-operation";  //планируемые операции: только учет и хранение (4)
	public static final String CLIENT_OPERATION_NAME = "client-operation";     //планируемые операции: операция клиента (8)
	public static final String TRADE_OPERATION_NAME = "trade-operation";      //планируемые операции: торговые операции (16)
	public static final String CLIENT_OPERATION_DECS_NAME = "client-operation-desc"; //  описание операции клиента

	private static final String CLIENT_OPERATIONS_DELIMITER = "\\|"; // разделитель клиентских операций в client-operation-desc


	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.SecurityRegistrationClaim.class;
	}

	public String getCorrDepositary()
	{
		return getNullSaveAttributeStringValue(CORR_DEPOSITARY_ATTRIBUTE_NAME);
	}

	public String getInsideCode()
	{
		return getNullSaveAttributeStringValue(INSIDE_CODE_ATTRIBUTE_NAME);
	}

	public String getSecurityName()
	{
		return getNullSaveAttributeStringValue(SECURITY_NAME_ATTRIBUTE_NAME);
	}

	public String getSecurityNumber()
	{
		return getNullSaveAttributeStringValue(SECURITY_NUMBER_ATTRIBUTE_NAME);
	}

	public String getIssuer()
	{
		return getNullSaveAttributeStringValue(ISSUER_ATTRIBUTE_NAME);
	}

	public List<SecurityOperationType> getOperations()
	{
		List<SecurityOperationType> listOperations = new ArrayList<SecurityOperationType>();
		if ((Boolean)getNullSaveAttributeValue(DEPOSITARY_OPERATION_NAME))
		{
			listOperations.add(SecurityOperationType.DEPOSITARY_OPERATION);
		}
		if ((Boolean)getNullSaveAttributeValue(DEPOSIT_OPERATION_NAME))
		{
			listOperations.add(SecurityOperationType.DEPOSIT_OPERATION);
		}
		if ((Boolean)getNullSaveAttributeValue(ACCOUNT_OPERATION_NAME))
		{
			listOperations.add(SecurityOperationType.ACCOUNT_OPERATION);
		}
		if ((Boolean)getNullSaveAttributeValue(TRADE_OPERATION_NAME))
		{
			listOperations.add(SecurityOperationType.TRADE_OPERATION);
		}
		if ((Boolean)getNullSaveAttributeValue(CLIENT_OPERATION_NAME))
		{
			listOperations.add(SecurityOperationType.CLIENT_OPERATION);
		}
	    return listOperations;
	}

	public String getClientOperationDescription()
	{
		return getNullSaveAttributeStringValue(CLIENT_OPERATION_DECS_NAME);
	}

	public List<String> getClientOperationDescriptionsList()
	{
		String decsription = getClientOperationDescription();
		// substring убирает первый и последний делители
		return Arrays.asList(decsription.substring(1, decsription.length()-1).split(CLIENT_OPERATIONS_DELIMITER));
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}
}
