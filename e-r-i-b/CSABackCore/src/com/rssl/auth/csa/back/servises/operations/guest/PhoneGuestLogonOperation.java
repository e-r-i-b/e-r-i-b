package com.rssl.auth.csa.back.servises.operations.guest;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.integration.mobilebank.MobileBankService;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.GuestProfile;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.SyncUtil;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;

/**
 * Операция гостевого входа по номеру телефона
 * @author niculichev
 * @ created 21.01.15
 * @ $Author$
 * @ $Revision$
 */
public class PhoneGuestLogonOperation extends GuestLogonOperation
{
	public void initialize(final String phone) throws Exception
	{
		fillClientProfileInfo(phone);
		super.initialize(phone);
	}

	public GuestLogonType getLogonType()
	{
		return GuestLogonType.PHONE;
	}

	public GuestProfile executeBase() throws Exception
	{
		return execute(new HibernateAction<GuestProfile>()
		{
			public GuestProfile run(Session session) throws Exception
			{
				return new GuestProfile(getPhone(), GuestProfile.generateCode());
			}
		});
	}

	private void fillClientProfileInfo(String phone) throws Exception
	{
		Pair<Long, Long> profileIdAndCode = GuestProfile.getIdAndCodeByPhone(phone);
		if(profileIdAndCode.getSecond() != null)
		{
			setGuestProfileId(profileIdAndCode.getFirst());
		}
	}
}
