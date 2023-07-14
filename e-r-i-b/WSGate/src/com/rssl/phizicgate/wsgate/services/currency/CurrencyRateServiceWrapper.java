package com.rssl.phizicgate.wsgate.services.currency;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.business.cache.BusinessCacheServiceImpl;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.proxy.CacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.GateBusinessCacheConfig;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.currency.generated.CurrencyRateServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.currency.generated.CurrencyRateService_Stub;
import com.sun.xml.rpc.client.ClientTransportException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * @author: Pakhomova
 * @created: 19.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateServiceWrapper  extends JAXRPCClientSideServiceBase<CurrencyRateService_Stub> implements CurrencyRateService
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final Cache ratesCache;

	static
	{
		ratesCache = CacheProvider.getCache(BusinessCacheServiceImpl.WRAPPER_RATE_CACHE);

		if ( ratesCache == null )
			throw new RuntimeException("Не найден кеш курсов валют");
	}

	public CurrencyRateServiceWrapper(GateFactory factory) throws GateException
	{
		super(factory);
		String url = factory.config(WSGateConfig.class).getURL() + "/CurrencyRateServiceImpl";
		CurrencyRateServiceImpl_Impl service = new CurrencyRateServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((CurrencyRateService_Stub) service.getCurrencyRateServicePort(), url);
	}
	public CurrencyRate getRate(Currency from, Currency to, CurrencyRateType type, Office office,
	                            String tarifPlanCodeType) throws GateException, GateLogicException
	{
		try
		{
			//курсы привязываются к ОСБ => ищем сначала ОСБ для офиса, потом ищем курс в кеше по ОСБ
			Office osb = departmentService.getOSBByOffice(office);

			CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(CurrencyRate.class);
			Serializable cacheKey = composer.getKey(new Object[]{from, to, type, osb, tarifPlanCodeType}, null);
			Element element  = ratesCache.get(cacheKey);
			if ( element != null )
				return (CurrencyRate) element.getObjectValue();

			com.rssl.phizicgate.wsgate.services.currency.generated.Currency generatedFrom = new com.rssl.phizicgate.wsgate.services.currency.generated.Currency();
			com.rssl.phizicgate.wsgate.services.currency.generated.Currency generatedTo = new com.rssl.phizicgate.wsgate.services.currency.generated.Currency();

			com.rssl.phizicgate.wsgate.services.currency.generated.Office generatedOffice = new com.rssl.phizicgate.wsgate.services.currency.generated.Office();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedFrom, from, CurrencyRateTypesCorrelation.toGeneratedTypes);
			BeanHelper.copyPropertiesWithDifferentTypes(generatedTo, to, CurrencyRateTypesCorrelation.toGeneratedTypes);

			BeanHelper.copyPropertiesWithDifferentTypes(generatedOffice, office, CurrencyRateTypesCorrelation.toGeneratedTypes);

			String encoded = encodeData(generatedOffice.getSynchKey());
			generatedOffice.setSynchKey(encoded);

			com.rssl.phizicgate.wsgate.services.currency.generated.CurrencyRate rate =
					getStub().getRate(generatedFrom, generatedTo, type.toString(), generatedOffice, tarifPlanCodeType);


//			//криво, но иначе надо добавлять сеттеры в currencyrate.
//			CurrencyRateType resType = new CurrencyRateType(rate.getType());
//
//			CurrencyImpl curfrom = new CurrencyImpl();
//			BeanHelper.copyPropertiesWithDifferentTypes(curfrom, rate.getFromCurrency(), CurrencyRateTypesCorrelation.toGateTypes);
//
//			CurrencyImpl curto = new CurrencyImpl();
//
//			BeanHelper.copyPropertiesWithDifferentTypes(curfrom, rate.getToCurrency(), CurrencyRateTypesCorrelation.toGateTypes);
//			CurrencyRate resultRate = new CurrencyRate(curto, rate.getFromValue(), curfrom, rate.getToValue(), resType );

			CurrencyRate resultRate = new CurrencyRate();
			BeanHelper.copyPropertiesWithDifferentTypes(resultRate, rate, CurrencyRateTypesCorrelation.toGateTypes);

			if (resultRate != null)
				ratesCache.put(new Element(cacheKey, resultRate));

			return resultRate;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public CurrencyRate convert(Money from, Currency to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.currency.generated.Money genMoney = new com.rssl.phizicgate.wsgate.services.currency.generated.Money();
			BeanHelper.copyPropertiesWithDifferentTypes(genMoney, from, CurrencyRateTypesCorrelation.toGeneratedTypes);

			com.rssl.phizicgate.wsgate.services.currency.generated.Currency generatedTo = new com.rssl.phizicgate.wsgate.services.currency.generated.Currency();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedTo, to, CurrencyRateTypesCorrelation.toGeneratedTypes);

			com.rssl.phizicgate.wsgate.services.currency.generated.Office generatedOffice = new com.rssl.phizicgate.wsgate.services.currency.generated.Office();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedOffice, office, CurrencyRateTypesCorrelation.toGeneratedTypes);

			String encoded = encodeData(generatedOffice.getSynchKey());
			generatedOffice.setSynchKey(encoded);
			String checkedTariffPlanCodeType = TariffPlanHelper.getTariffPlanCode(tarifPlanCodeType);
			com.rssl.phizicgate.wsgate.services.currency.generated.CurrencyRate rate = getStub().convert2(genMoney, generatedTo, generatedOffice, checkedTariffPlanCodeType);

			CurrencyRate resultRate = new CurrencyRate();
			BeanHelper.copyPropertiesWithDifferentTypes(resultRate, rate, CurrencyRateTypesCorrelation.toGateTypes);

			return resultRate;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public CurrencyRate convert(Currency from, Money to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.currency.generated.Money genMoney = new com.rssl.phizicgate.wsgate.services.currency.generated.Money();
			BeanHelper.copyPropertiesWithDifferentTypes(genMoney, to, CurrencyRateTypesCorrelation.toGeneratedTypes);

			com.rssl.phizicgate.wsgate.services.currency.generated.Currency generatedFrom = new com.rssl.phizicgate.wsgate.services.currency.generated.Currency();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedFrom, from, CurrencyRateTypesCorrelation.toGeneratedTypes);

			com.rssl.phizicgate.wsgate.services.currency.generated.Office generatedOffice = new com.rssl.phizicgate.wsgate.services.currency.generated.Office();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedOffice, office, CurrencyRateTypesCorrelation.toGeneratedTypes);
			String encoded = encodeData(generatedOffice.getSynchKey());
			generatedOffice.setSynchKey(encoded);
			String checkedTariffPlanCodeType = TariffPlanHelper.getTariffPlanCode(tarifPlanCodeType);
			com.rssl.phizicgate.wsgate.services.currency.generated.CurrencyRate rate = getStub().convert(generatedFrom, genMoney, generatedOffice, checkedTariffPlanCodeType);

			CurrencyRate resultRate = new CurrencyRate();
			BeanHelper.copyPropertiesWithDifferentTypes(resultRate, rate, CurrencyRateTypesCorrelation.toGateTypes);

			return resultRate;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}

