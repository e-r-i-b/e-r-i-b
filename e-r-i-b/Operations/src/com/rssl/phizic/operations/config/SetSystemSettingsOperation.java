package com.rssl.phizic.operations.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleJobService;
import com.rssl.phizic.business.dictionaries.providers.aggr.CatalogAggregationJob;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.payments.job.JobRefreshConfig;
import com.rssl.phizic.business.payments.job.RepeatSendRequestDocumentJob;
import com.rssl.phizic.config.*;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.gate.PropertiesGateConfig;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.manager.events.JAXRPCStubsUpdateEvent;

import java.util.*;

import static com.rssl.phizic.security.config.Constants.*;

/**
 * @author egorova
 * @ created 29.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class SetSystemSettingsOperation extends EditPropertiesOperation
{
	private static final String JOBTRIGGER_GROUP = "DEFAULT";
	private static final String REPEAT_SEND_REQUEST_DOCUMENT_TRIGGER = "RepeatSendRequestDocumentTrigger";
	private static final String CATALOG_AGGREGATION_TRIGGER = "CatalogAggregationTrigger";
	private static final String PREFIX = "urn:sbrfsystems:";

	private final SimpleJobService jobService = new SimpleJobService();

	//справочник табличных настроек и хелперов для их конвертаци
	private static final Map<String, ListPropertiesHelper> listProperties = new HashMap<String, ListPropertiesHelper>();

	static
	{
		Set<String> mobileCodeKeys= new LinkedHashSet<String>();
		mobileCodeKeys.add(MOBILE_PROVIDER_CODE_KEY);
		mobileCodeKeys.add(MOBILE_SERVICE_CODE_KEY);
		listProperties.put(MOBILE_PROVIDER_KEY, new MultiFieldsListPropertiesHelper(MOBILE_PROVIDER_ID_KEY, MOBILE_PROVIDER_KEY, mobileCodeKeys, "", ";"));
		listProperties.put(OLD_DOC_ADAPTER_KEY, new ListPropertiesHelper(OLD_DOC_ADAPTER_ID_KEY, OLD_DOC_ADAPTER_CODE_KEY, PREFIX, ";"));
	}

	@Override
	public void initialize(PropertyCategory category, Set keys) throws BusinessException
	{
		super.initialize(category, keys);

		List<Property> mppList = findPropertiesByKey(MOBILE_PROVIDER_KEY);
		addProperties(listProperties.get(MOBILE_PROVIDER_KEY).convertToMap(mppList));

		List<Property> odapList = findPropertiesByKey(OLD_DOC_ADAPTER_KEY);
		addProperties(listProperties.get(OLD_DOC_ADAPTER_KEY).convertToMap(odapList));
	}

	public void initializeReplicate(PropertyCategory category, Set keys) throws BusinessException, BusinessLogicException
	{
		boolean mobileProviders = keys.remove(MOBILE_PROVIDER_KEY);
		boolean oldDocAdapters = keys.remove(OLD_DOC_ADAPTER_KEY);

		super.initialize(category, keys);

		if (mobileProviders)
			initializeListProperties(MOBILE_PROVIDER_KEY);

		if (oldDocAdapters)
			initializeListProperties(OLD_DOC_ADAPTER_KEY);
	}

	@Transactional
	public void save() throws BusinessException, BusinessLogicException
	{
		boolean needUpdateStubs = false;
		if (ConfigFactory.getConfig(PropertiesGateConfig.class).getTimeout() != Integer.parseInt((String) getEntity().get(TimeoutHttpTransport.GATE_WRAPPER_CONNECTION_TIMEOUT)))
			needUpdateStubs = true;

		try
		{
			List<String> mobileValues = listProperties.get(MOBILE_PROVIDER_KEY).convertToList(getEntity());
			DbPropertyService.updateListProperty(MOBILE_PROVIDER_KEY, mobileValues, getPropertyCategory(), null);

			List<String> oldDocValues = listProperties.get(OLD_DOC_ADAPTER_KEY).convertToList(getEntity());
			DbPropertyService.updateListProperty(OLD_DOC_ADAPTER_KEY, oldDocValues, getPropertyCategory(), null);

			super.save();

			if (needUpdateStubs)
				EventSender.getInstance().sendEvent(new JAXRPCStubsUpdateEvent());

			updateTrigger(RepeatSendRequestDocumentJob.class.getSimpleName(),REPEAT_SEND_REQUEST_DOCUMENT_TRIGGER,JobRefreshConfig.REPEAT_SEND_REQUEST_DOCUMENT_TIMEOUT);
			updateTrigger(CatalogAggregationJob.class.getSimpleName(), CATALOG_AGGREGATION_TRIGGER, JobRefreshConfig.CATALOG_AGGREGATION_INTERVAL);
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	private void updateTrigger(String jobName, String triggerName, String fieldName) throws BusinessException
	{
		try
		{
			String timeout = (String) getEntity().get(fieldName);
			Calendar startTime = DateHelper.getTime(DateHelper.clearTime(Calendar.getInstance()));
			Calendar nextTime = DateHelper.getTime(DateHelper.parseCalendar(timeout, DateHelper.TIME_FORMAT));
			Long diff = DateHelper.diff(nextTime, startTime);
			jobService.rescheduleTriggerInterval(jobName, JOBTRIGGER_GROUP, triggerName,JOBTRIGGER_GROUP, diff);
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}
}
