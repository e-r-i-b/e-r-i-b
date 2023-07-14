package com.rssl.phizic.ws.esberiblistener.esberib;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.security.Security;
import com.rssl.phizic.business.dictionaries.security.SecurityService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.NotRoundedMoney;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.*;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 13.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * Updater дл€ запроса SecDicInfoRq
 */
public class SecurityDicUpdater extends EsbEribUpdaterBase
{
	protected IFXRq_Type secDicInfoRq;

	public SecurityDicUpdater(IFXRq_Type secDicInfoRq)
	{
		this.secDicInfoRq = secDicInfoRq;
	}

	public IFXRs_Type doIFX()
	{
		IFXRs_Type ifxRs_type = new IFXRs_Type();
		ifxRs_type.setSecDicInfoRs(getSecDicInfoRs_type());

		return ifxRs_type;
	}

	/**
	 * ѕолучить заполненный ответ SecDicInfoRs_Type
	 * @return SecDicInfoRs_Type
	 */
	protected SecDicInfoRs_Type getSecDicInfoRs_type()
	{
		try
		{
			for (DictRec_Type dictRec: secDicInfoRq.getSecDicInfoRq().getDictRec())
			{
				Security security = createSecurity(dictRec);
				String erroeMessage = updateSecutiry(security);

				if (StringHelper.isNotEmpty(erroeMessage))
					return getErrorResponse(erroeMessage);
			}

			return getSuccessfullResponse();
		}
		catch (GateException e)
		{
			return getErrorResponse(e.getMessage());
		}
	}

	/**
	 * —оздать ценную бумагу
	 * @param dictRec запись о ценной бумаге в справочнике
	 * @return Security
	 * @throws GateException
	 */
	private Security createSecurity(DictRec_Type dictRec) throws GateException
	{
		Security security = new Security();
		security.setIssuer(dictRec.getIssuer());
		security.setName(dictRec.getSecurityName());
		security.setRegistrationNumber(dictRec.getSecurityNumber());
		security.setType(dictRec.getSecurityType());
		security.setNominal(getNullableNominal(dictRec));
		security.setInsideCode(dictRec.getInsideCode());
		security.setIsDelete(dictRec.isIsDelete());

		return security;
	}

	/**
	 * ќбновить запись о ценной бумаге в Ѕƒ
	 * ≈сли обновление произошло успешно, вернетс€ пуста€ строка
	 * ≈сли при обновлении произошла ошибка, возвратитс€ сообщение об ошибке
	 *
	 * @param security ценна€ бумага
	 * @return сообщение о результате выполнени€
	 */
	private String updateSecutiry(Security security)
	{
		try
		{
			SecurityService securityService = new SecurityService();
			securityService.update(security);

			return EMPTY_STRING;
		}
		catch (BusinessException e)
		{
			return e.getMessage();
		}
	}

	/**
	 * ѕолучить номинал ценной бумаги, если размер номинала не пришел, возвращаем null
	 * @param dictRec запись о ценной бумаге
	 * @return NotRoundedMoney
	 * @throws GateException
	 */
	private NotRoundedMoney getNullableNominal(DictRec_Type dictRec) throws GateException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency currency = currencyService.findByAlphabeticCode(dictRec.getSecurityNominalCur());
		BigDecimal securityNominal = dictRec.getSecurityNominal();

		if (currency == null || securityNominal == null)
			return null;

		return new NotRoundedMoney(securityNominal, currency);
	}

	/**
	 * @return ответ об удачном обновлении всех ценных бумаг
	 */
	protected SecDicInfoRs_Type getSuccessfullResponse()
	{
		return fillSecDicInfoRs_type(SUCCESSFULL_STATUS_CODE, null);
	}

	/**
	 * ответ о неудачном обновлении ценных бумаг
	 * @param erroeMessage сообщение об ошибке
	 * @return SecDicInfoRs_Type
	 */
	private SecDicInfoRs_Type getErrorResponse(String erroeMessage)
	{
		log.error(erroeMessage);

		return fillSecDicInfoRs_type(ERROR_STATUS_CODE, erroeMessage);
	}

	/**
	 * заполнить ответ данными
	 * @param statusCode код ошибки
	 * @param statusDesc описание ошибки
	 * @return SecDicInfoRs_Type
	 */
	private SecDicInfoRs_Type fillSecDicInfoRs_type(Long statusCode, String statusDesc)
	{
		Status_Type status = new Status_Type(statusCode, statusDesc, null, null);

		SecDicInfoRq_Type dicInfoRq = secDicInfoRq.getSecDicInfoRq();
		return new SecDicInfoRs_Type(dicInfoRq.getRqUID(), dicInfoRq.getRqTm(), dicInfoRq.getOperUID(), status);
	}
}
