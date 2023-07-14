package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.depo.DepoAccountPosition;
import com.rssl.phizic.gate.depo.DepoAccountDivision;
import com.rssl.phizic.gate.depo.DepoAccountSecurity;
import com.rssl.phizic.gate.depo.DepoAccountSecurityStorageMethod;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.math.BigInteger;
import java.util.Map;
import java.util.List;

/**
 * @author komarov
 * @ created 20.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class SecuritiesCountValidator extends MultiFieldsValidatorBase
{
	private static final String DIVISION_NUMBER     = "divNum";
	private static final String DIVISION_TYPE       = "divType";
	private static final String REGISTRATION_NUMBER = "regCode";
	private static final String DEPO_ACCOUNT        = "depoAcc";
	private static final String SECURITY_COUNT      = "secCount";
	
	public boolean validate(Map values) throws TemporalDocumentException
	{
		PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		DepoAccountLink depoAccLink;

		String divisionNumber     = (String)retrieveFieldValue(DIVISION_NUMBER, values);
		String divisionType       = (String)retrieveFieldValue(DIVISION_TYPE, values);
		String registrationNumber = (String)retrieveFieldValue(REGISTRATION_NUMBER, values);
		String depoAccount        = (String)retrieveFieldValue(DEPO_ACCOUNT, values);
		BigInteger securityCount  = (BigInteger)retrieveFieldValue(SECURITY_COUNT, values);

		try
		{
		    depoAccLink = data.findDepo(depoAccount);
			DepoAccountPosition position = depoAccLink.getDepoAccountPositionInfo();
			List<DepoAccountDivision> accountDivisions = position.getDepoAccountDivisions();

			for(DepoAccountDivision accountDivision : accountDivisions)
			{
				if(accountDivision.getDivisionNumber().equals(divisionNumber) &&
				   accountDivision.getDivisionType().equals(divisionType))
				{
					List<DepoAccountSecurity> list = accountDivision.getDepoAccountSecurities();
					for(DepoAccountSecurity security : list)
					{
						if( registrationNumber.equals(security.getRegistrationNumber()) &&
							security.getStorageMethod() == DepoAccountSecurityStorageMethod.open &&
							security.getRemainder().compareTo(securityCount.longValue()) >= 0 )
						{
							return true;
						}
					}
				}
			}
		}
		catch(BusinessException be)
		{
			 throw new TemporalDocumentException("Невозможно получить информацию по счёту: "+ depoAccount);
		}
		catch (GateLogicException e)
		{
			 throw new TemporalDocumentException("Невозможно получить информацию по счёту: "+ depoAccount);
		}
		return false;
	}
}