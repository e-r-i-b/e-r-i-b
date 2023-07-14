package com.rssl.phizic.asfilial.listener;

import com.rssl.auth.csa.utils.CSAResponseUtils;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.phizic.asfilial.listener.generated.PhoneNumberType;
import com.rssl.phizic.asfilial.listener.generated.RequestPhoneHolderRqType;
import com.rssl.phizic.asfilial.listener.generated.RequestPhoneHolderRsType;
import com.rssl.phizic.asfilial.listener.generated.StatusType;
import com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial.ASFilialReturnCode;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mbv.MbvClientIdentity;
import com.rssl.phizic.gate.mobilebank.DepoMobileBankService;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.utils.PhoneNumber;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.w3c.dom.Document;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.transform.TransformerException;

import static com.rssl.phizic.utils.PhoneNumberFormat.MOBILE_INTERANTIONAL;

/**
 * Общие для блочного и прокси обработчиков запросов ФСБ методы
 * @author Puzikov
 * @ created 21.04.15
 * @ $Author$
 * @ $Revision$
 */

public abstract class AsFilialServiceBase implements com.rssl.phizic.asfilial.listener.generated.ASFilialInfoService
{
	private final ASFilialServiceBaseHelper asHelper = new ASFilialServiceBaseHelper();

	/**
	 * Данный интерфейс используется для отправки в ЕРИБ запроса о проверке номера мобильного телефона на наличие у другого держателя
	 * @param parameters RequestPhoneHolderRq
	 * @return RequestPhoneHolderRs
	 * @throws RemoteException
	 */
	public RequestPhoneHolderRsType requestPhoneHolder(RequestPhoneHolderRqType parameters) throws RemoteException
	{
		RequestPhoneHolderRsType response = new RequestPhoneHolderRsType();
		response.setRqTm(parameters.getRqTm());
		response.setRqUID(parameters.getRqUID());
		response.setOperUID(parameters.getOperUID());
		StatusType status = new StatusType();
		response.setStatus(status);

		if (!ConfigFactory.getConfig(ASFilialConfig.class).isUseV19spec())
		{
			asHelper.setStatus(status, "RequestPhoneHolderRq не поддерживается", ASFilialReturnCode.TECHNICAL_ERROR);
			return response;
		}

		try
		{
			PhoneNumberType[] engagedPhones = filterEngagedPhones(parameters.getPhones());
			if (ArrayUtils.isNotEmpty(engagedPhones))
			{
				response.setEngagedPhones(engagedPhones);
				asHelper.setStatus(status, ASFilialReturnCode.DUPLICATION_PHONE_ERR);
			}
			else
			{
				asHelper.setStatus(status, ASFilialReturnCode.OK);
			}
		}
		catch (NumberFormatException e)
		{
			asHelper.setStatus(status, e, ASFilialReturnCode.FORMAT_ERROR);
		}
		catch (Exception e)
		{
			asHelper.setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
		}

		return response;
	}

	private PhoneNumberType[] filterEngagedPhones(PhoneNumberType[] phones) throws Exception
	{
		Set<PhoneNumberType> engaged = new HashSet<PhoneNumberType>();
		for (PhoneNumberType phone : phones)
		{
			PhoneNumber phoneNumber = MOBILE_INTERANTIONAL.parse(phone.getPhoneNumberN());
			if (engagedNumber(phoneNumber))
				engaged.add(phone);
		}
		return engaged.toArray(new PhoneNumberType[engaged.size()]);
	}

	private boolean engagedNumber(PhoneNumber phoneNumber) throws Exception
	{
		return inErmb(phoneNumber) || inMbk(phoneNumber) || inMbv(phoneNumber);
	}

	private boolean inErmb(PhoneNumber phoneNumber) throws BackLogicException, BackException, TransformerException
	{
		try
		{
			Document csaInfo = CSABackRequestHelper.sendFindProfileNodeByPhoneRq(MOBILE_INTERANTIONAL.format(phoneNumber));
			return CSAResponseUtils.createNodeInfo(csaInfo.getDocumentElement()) != null;
		}
		catch (FailureIdentificationException ignored)
		{
			return false;
		}
		catch (AuthenticationFailedException ignored)
		{
			return false;
		}
		catch (UserNotFoundException ignored)
		{
			return false;
		}
		catch (ProfileNotFoundException ignored)
		{
			return false;
		}
	}

	private boolean inMbk(PhoneNumber phoneNumber) throws GateException, GateLogicException
	{
		//noinspection deprecation
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		Set<String> mbkCards = mobileBankService.getCardsByPhone(MOBILE_INTERANTIONAL.format(phoneNumber));
		return CollectionUtils.isNotEmpty(mbkCards);
	}

	private boolean inMbv(PhoneNumber phoneNumber) throws GateException
	{
		DepoMobileBankService mobileBankService = GateSingleton.getFactory().service(DepoMobileBankService.class);
		List<MbvClientIdentity> mbvInfo = mobileBankService.checkPhoneOwn(MOBILE_INTERANTIONAL.format(phoneNumber));
		return CollectionUtils.isNotEmpty(mbvInfo);
	}
}
