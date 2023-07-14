package com.rssl.phizicgate.mdm.operations;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizicgate.mdm.business.products.ClientProductsSynchronizeInfo;
import com.rssl.phizicgate.mdm.business.products.ProductService;
import com.rssl.phizicgate.mdm.business.profiles.Document;
import com.rssl.phizicgate.mdm.business.profiles.Profile;
import com.rssl.phizicgate.mdm.business.profiles.ProfileService;
import com.rssl.phizicgate.mdm.common.ClientInfo;
import com.rssl.phizicgate.mdm.common.ClientProductsInfo;
import com.rssl.phizicgate.mdm.integration.csa.CSAClientService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;

/**
 * @author akrenev
 * @ created 06.08.2015
 * @ $Author$
 * @ $Revision$
 *
 * Базовая операция обработки оповещения ЕРИБа МДМом
 */

public abstract class UpdateClientInfoOperationBase<DS, RT> implements Operation<DS, RT>
{
	protected static final Log log = LogFactory.getLog(LogModule.Core.toString());

	private static final ProfileService profileService = new ProfileService();
	private static final ProductService productService = new ProductService();
	private static final CSAClientService csaService = new CSAClientService();

	private DS source;

	public void initialize(DS source)
	{
		this.source = source;
	}

	protected DS getSource()
	{
		return source;
	}

	protected Profile updateProfile(Long csaProfileId, ClientInfo clientInfo) throws GateException, GateLogicException
	{
		String mdmId = clientInfo.getMdmId();

		Profile profile = profileService.getProfile(mdmId);
		if (profile == null)
		{
			profile = new Profile();
			profile.setMdmId(mdmId);
			profile.setProfileIds(new HashSet<Long>());
			profile.setDocuments(new HashSet<Document>());
		}

		profile.getProfileIds().add(csaProfileId);
		MDMInfoHelper.synchronizeProfile(profile, clientInfo);
		profileService.save(profile);

		return profile;
	}

	protected void updateProducts(ClientProductsInfo productsInfo, Long profileId) throws GateException
	{
		ClientProductsSynchronizeInfo clientProductsSynchronizeInfo = productService.getClientProductsInfo(profileId);
		MDMInfoHelper.synchronizeProducts(clientProductsSynchronizeInfo, productsInfo, profileId);
		productService.saveClientProductsInfo(clientProductsSynchronizeInfo);
	}

	protected void notifyCSA(Long csaProfileId, String mdmId)
	{
		try
		{
			getCsaService().updateProfile(csaProfileId, mdmId);
		}
		catch (Exception e)
		{
			log.error("Ошибка обновления профиля в цса.", e);
		}
	}

	protected static ProfileService getProfileService()
	{
		return profileService;
	}

	private static CSAClientService getCsaService()
	{
		return csaService;
	}
}
