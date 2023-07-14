package com.rssl.phizicgate.rsV55.senders;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.CommissionTarget;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.SWIFTPayment;
import com.rssl.phizic.gate.payments.systems.SWIFTPaymentConditions;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.Country;
import com.rssl.phizic.gate.dictionaries.CountriesGateService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsV55.demand.ExpandedPaymentDemand;
import com.rssl.phizicgate.rsV55.demand.PaymentDemandBase;
import com.rssl.phizicgate.rsV55.demand.Remittee;
import com.rssl.phizicgate.rsV55.data.GateRSV55Executor;
import org.hibernate.Session;
import org.hibernate.Query;
import java.util.List;

/**
 * @author Krenev
 * @ created 17.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class SWIFTPaymentSender extends AbstractPaymentSender
{
	private static final long BANK_CODE = 6L;   // TODO: наверно нужна настройка
	private static final String PARAMETER_SUBOPERATION_TYPE_SPOT_NAME = "suboperation-type-spot";
	private static final String PARAMETER_SUBOPERATION_TYPE_TOM_NAME = "suboperation-type-tom";
	private static final String PARAMETER_SUBOPERATION_TYPE_TOD_NAME = "suboperation-type-tod";
	private static final String PARAMETER_SUBOPERATION_IN_COUNTRY = "suboperation-in-country";
	private static final String PARAMETER_SUBOPERATION_FROM_COUNTRY = "suboperation-from-country";
	private static final String PARAMETR_GROUND = "ground";

	/**
	 * создать за€вку
	 * @return за€вка
	 */
	protected PaymentDemandBase createDemand()
	{
		return new ExpandedPaymentDemand();
	}

	protected void fillDemand(PaymentDemandBase demand, GateDocument document) throws GateLogicException, GateException
	{
		if (!(document instanceof SWIFTPayment))
			throw new GateException("Ќеверный тип платежа, должен быть - SWIFTPayment.");
		SWIFTPayment swiftPayment = (SWIFTPayment) document;

		super.fillDemand(demand, swiftPayment);

		Client client = getOwner(document);
		ExpandedPaymentDemand expandedPaymentDemand = (ExpandedPaymentDemand) demand;
/*
    ак передавать срочность платежа (TOD/SPOT)? »ли это отдельные подоперации или писать это в основание
      и операционист уже будет сам как-то там проводить!?
 */
		if (swiftPayment.getCommissionOptions().getTarget().equals(CommissionTarget.MAIN))
			expandedPaymentDemand.setGround(getDocumentGround() + " ѕеревод " + swiftPayment.getConditions() +
			                                "  омисси€ со счета: " + swiftPayment.getCommissionOptions().getAccount());
		else
		    expandedPaymentDemand.setGround(getDocumentGround() + " ѕеревод " + swiftPayment.getConditions());

		expandedPaymentDemand.setReceiverAccount(swiftPayment.getReceiverAccount());

		String corAccount = findAccountBySWIFT(swiftPayment.getReceiverSWIFT());
		expandedPaymentDemand.setReceiverBic(corAccount); // bicdir.MFO_Depart
		expandedPaymentDemand.setReceiverCorAccount(swiftPayment.getReceiverSWIFT()); // bicdir.BIC_Key

		Remittee receiver = new Remittee();
		receiver.setPayerInn(client.getINN());
		receiver.setBank(swiftPayment.getReceiverBankName());
		receiver.setBankCode(BANK_CODE);
		receiver.setBic(corAccount);  // bicdir.MFO_Depart 
		receiver.setCorAccount(swiftPayment.getReceiverSWIFT()); //bicdir.BIC_Key
		receiver.setReceiverName(swiftPayment.getReceiverName());

		CountriesGateService countriesService = GateSingleton.getFactory().service(CountriesGateService.class);
		Country country = new Country();
		country.setIntCode(swiftPayment.getReceiverCountryCode());
		List<Country> countryList = countriesService.getAll(country, 0, 2);
		if (countryList.size() == 1)
		   country.setCode(countryList.get(0).getCode());

		receiver.setReceiverCountryCode(country.getCode());
		expandedPaymentDemand.setReceiver(receiver);
		receiver.setGround(swiftPayment.getGround());
		expandedPaymentDemand.setDestination(receiver);

		/* подопераци€ может зависеть от направлени€ перевода: за пределы банка или »з –оссии */	
		if ((getInCountrySuboperation() != null) && !getInCountrySuboperation().equals("")  &&
			(getFromCountrySuboperation() != null) && !getFromCountrySuboperation().equals("") && (country != null))
		{
			if (country.getCode().equals("RUS"))   // TODO: наверно нужна настройка
			   expandedPaymentDemand.setApplType(Long.valueOf(getInCountrySuboperation()));
			else
			   expandedPaymentDemand.setApplType(Long.valueOf(getFromCountrySuboperation()));
		}
		else
		{
			if (getCurrencyTransferSuboperationTOD().equals("") && getCurrencyTransferSuboperationTOM().equals("") &&
			    getCurrencyTransferSuboperationSPOT().equals(""))
			{
				expandedPaymentDemand.setApplType(0L);
			}
			else
			{
				if (swiftPayment.getConditions() == SWIFTPaymentConditions.tod)
					expandedPaymentDemand.setApplType(Long.valueOf(getCurrencyTransferSuboperationTOD()));
				if (swiftPayment.getConditions() == SWIFTPaymentConditions.tom)
					expandedPaymentDemand.setApplType(Long.valueOf(getCurrencyTransferSuboperationTOM()));
				if (swiftPayment.getConditions() == SWIFTPaymentConditions.spot)
					expandedPaymentDemand.setApplType(Long.valueOf(getCurrencyTransferSuboperationSPOT()));
			}
		}
	}

	private String getCurrencyTransferSuboperationSPOT()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_SPOT_NAME);
	}

	private String getCurrencyTransferSuboperationTOM()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_TOM_NAME);
	}

	private String getCurrencyTransferSuboperationTOD()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_TOD_NAME);
	}
	/**
	* ѕолучить описание основани€ платежа из настроек
	* Domain: Text
	* @return основание
	*/
	private String getDocumentGround()
	{
		return (String) getParameter(PARAMETR_GROUND);
 	}
    /**
	* @return подопераци€ при переводе внутри страны
	*/
	private String getInCountrySuboperation()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_IN_COUNTRY);
	}
    /**
	* @return подопераци€ при переводе из страны
	*/
	private String getFromCountrySuboperation()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_FROM_COUNTRY);
	}

     /**
	 * ¬озвращает кор.счет банка. Ёта специфика –етейла!
	 * @param SWIFT банка
	 * @return кор.счет банка
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public String findAccountBySWIFT(final String SWIFT) throws GateException
	{
        try
		{
			return GateRSV55Executor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("FindAccountBySWIFT");
					query.setParameter("swift", SWIFT);
					return (String) query.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}
}
