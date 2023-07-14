package com.rssl.phizic.business.ermb.migration.mbk;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.ERMBPhone;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.jmx.MobileBankConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;
import java.util.List;

/**
 * @author Rtischeva
 * @ created 11.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Аплоудер, выгружающий ЕРМБ-телефоны в МБК
 */
public class PhonesUploader
{
	protected static final Log log = LogFactory.getLog(PhonesUploader.class);
	private final ERMBPhoneService ermbPhoneService = new ERMBPhoneService();

	public PhonesUploadStatus uploadPhones()
	{
		MobileBankConfig mobileBankСonfig = ConfigFactory.getConfig(MobileBankConfig.class);
		int ermbPhonesMaxCount = mobileBankСonfig.getErmbPhonesMaxCount();

		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);

		try
		{
			List<ERMBPhone> ermbPhones = ermbPhoneService.getERMBPhones(ermbPhonesMaxCount);
			if (CollectionUtils.isEmpty(ermbPhones))
				return PhonesUploadStatus.ENOUGH;

			Calendar updatePhonesCallDate = Calendar.getInstance();
			mobileBankService.updateErmbPhonesInMb(ermbPhones);

			for (ERMBPhone ermbPhone : ermbPhones)
			{
				ermbPhone.setLastUpload(updatePhonesCallDate);
			}

			ermbPhoneService.updateERMBPhones(ermbPhones);
			return PhonesUploadStatus.MORE;
		}
		catch (BusinessException e)
		{
			log.error("Ошибка синхронизации ЕРМБ-телефонов с МБК", e);
			return PhonesUploadStatus.ENOUGH;
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
			return PhonesUploadStatus.ENOUGH;
		}
		catch (GateLogicException e)
		{
			log.error(e.getMessage(), e);
			return PhonesUploadStatus.ENOUGH;
		}
	}
}
