package com.rssl.phizic.business.security.pin.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.auth.pin.PINEnvelope;
import com.rssl.phizic.auth.pin.PINService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;

import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 15.09.2006
 * @ $Author$
 * @ $Revision$
 */
public class PINDepartmentLinkValidator  extends MultiFieldsValidatorBase
{
	public static final String DEPARTEMNT_ID_FIELD = "departmentId";
	public static final String PIN_ENVELOPE_FIELD = "pinEnvelopeNumber";

	private static final PINService pinService = new PINService();
	private static final SimpleService simpleService = new SimpleService();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String departmentId = (String) retrieveFieldValue(DEPARTEMNT_ID_FIELD, values);
		String pin = (String) retrieveFieldValue(PIN_ENVELOPE_FIELD, values);

		if(departmentId==null || pin==null)return true;

		Department department = null;
		try
		{
			department = simpleService.findById(Department.class, Long.parseLong( departmentId ) );
		}
		catch(BusinessException e)
		{
			return false;
		}
		PINEnvelope envelope = pinService.findEnvelope(pin);

		if(envelope==null)return false;
		if(department==null)return false;

		if(department.getId().equals(envelope.getDepartmentId()))return true;
		else return false;
	}
}
