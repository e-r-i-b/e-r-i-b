package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.SecurityTypeHelper;
import com.rssl.auth.csa.back.servises.connectors.PasswordBasedConnector;
import com.rssl.auth.csa.back.servises.connectors.SocialAPIConnector;
import com.rssl.auth.csa.back.servises.restrictions.operations.SocialConnecotrsCountRestriction;
import com.rssl.auth.csa.back.servises.restrictions.security.MAPIPasswordSecurityRestriction;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

/**
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */
public class SocialRegistrationOperation extends MobileRegistrationOperation
{

	public SocialRegistrationOperation() {}

	public SocialRegistrationOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	public Connector execute(final String passwordValue, final String deviceState, final String deviceId, final String appName) throws Exception
	{
		MAPIPasswordSecurityRestriction.getInstance().check(passwordValue);
		setAppName(appName);
		Connector connector = execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				Profile profile = lockProfile();
                PasswordBasedConnector result;

                //для социальных платформ коннекторы уникальны в рамках платформы, т.е. могут быть одинаковые deviceId для разных платформ
                preSocialExecute(profile);
                SocialConnecotrsCountRestriction.getInstance().check(SocialRegistrationOperation.this);
                result = new SocialAPIConnector(getOuid(), getUserId(), getCbCode(), getCardNumber(), getDeviceInfo(), deviceState, profile, deviceId, getRegistrationLoginType());

				result.save();

				if (profile.getSecurityType() == null)
				{
					//Если клиент зарегистрировался до переноса уровня безопасности в ЦСА и ни разу не входил, то у профиля уровень безопасности не выставлен
					profile.setSecurityType(SecurityTypeHelper.calcSecurityType(profile, getCardNumber(), result.getType()));
					profile.save();
				}

				setPasswordChanged(result.changePassword(passwordValue));
				return result;
			}
		});
		setExecuted(true);

		sendNotification(getCardNumber());
		return connector;
	}

}
