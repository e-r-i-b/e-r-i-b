package com.rssl.phizic.operations.ermb.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbClientPhone;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ermb.ResetIMSIHelper;
import com.rssl.phizic.utils.PhoneEncodeUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * �������� ������ IMSI ��� ��������� ����
 * @author Rtischeva
 * @ created 02.08.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbResetIMSIOperation extends OperationBase
{
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ����� IMSI ��� ������ ��������
	 * @param phonesToDecode ���� ���������.
	 * @param profileId id �������.
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public boolean resetIMSI(List<String> phonesToDecode,Long profileId) throws BusinessException
	{
		List<String> decodePhoneNumbers;
		ErmbProfileImpl profile = null;

		if (profileId != null)
		{
			profile = profileService.findByPersonId(profileId);
			Set<String> initialPhoneNumbers =  new HashSet<String>();
			for (ErmbClientPhone phone:profile.getPhones())
			{
				initialPhoneNumbers.add(phone.getNumber());
			}
			decodePhoneNumbers = new ArrayList<String>(PhoneEncodeUtils.decodePhoneNumbers(new HashSet<String>(phonesToDecode), initialPhoneNumbers, true));
		}
		else
		{
			//���� ������� �� ������� �� ��� ����� �������, � �������� �� ������������.
			decodePhoneNumbers = phonesToDecode;
		}
		try
		{
			ResetIMSIHelper.resetIMSI(decodePhoneNumbers, profile);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return false;
		}

		return true;
	}
}
