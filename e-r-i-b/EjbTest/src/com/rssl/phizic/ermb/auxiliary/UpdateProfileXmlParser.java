package com.rssl.phizic.ermb.auxiliary;

import com.rssl.phizic.common.type.ErmbProfileIdentity;
import com.rssl.phizic.common.type.ErmbUpdateProfileInfo;
import com.rssl.phizic.synchronization.SOSMessageHelper;
import com.rssl.phizic.synchronization.types.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 @author: EgorovaA
 @ created: 17.01.2013
 @ $Author$
 @ $Revision$
 */
class UpdateProfileXmlParser
{
	public List<ErmbUpdateProfileInfo> parse(UpdateProfilesRq rq) throws JAXBException, ParseException
    {
		List<ErmbUpdateProfileInfo> messageList = new ArrayList<ErmbUpdateProfileInfo>();

        UpdatedProfilesType updatedProfilesType =  rq.getUpdatedProfiles();
        if (updatedProfilesType != null)
        {
            List<UpdateProfileType> profileTypeList = updatedProfilesType.getProfiles();
            for (UpdateProfileType profileType :profileTypeList)
            {
                ErmbUpdateProfileInfo ermbUpdateProfileInfo = new ErmbUpdateProfileInfo();
	            ermbUpdateProfileInfo.setProfileVersion(profileType.getVersion());
                IdentityType clientIdentity = profileType.getClientIdentity();
	            ErmbProfileIdentity personIdentity = SOSMessageHelper.getClientIdentity(clientIdentity);
                ermbUpdateProfileInfo.setClientIdentity(personIdentity);
                List<ClientOldIdentityType> clientOldIdentityList = profileType.getClientOldIdentities();
                List< ErmbProfileIdentity > oldPersonIdentityList = new ArrayList<ErmbProfileIdentity>();
                for (ClientOldIdentityType oldIdentityType:clientOldIdentityList)
                {
                    IdentityType oldIdentity = oldIdentityType.getIdentity();
	                ErmbProfileIdentity oldPersonIdentity = SOSMessageHelper.getClientIdentity(oldIdentity);
                    oldPersonIdentityList.add(oldPersonIdentity);
                }
                ermbUpdateProfileInfo.setClientOldIdentity(oldPersonIdentityList);
                messageList.add(ermbUpdateProfileInfo);
            }
        }
		return messageList;
	}
}
