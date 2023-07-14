package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.*;
import com.rssl.phizic.gate.dictionaries.ReplicaDestination;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.utils.StringHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 25.10.2005
 * Time: 18:38:49
 */
public class PropertiesDictionaryConfig extends DictionaryConfig
{
	private static final String NAME_KEY = "com.rssl.iccs.dictionaries.name.";
	private static final String DESCRIPTION_KEY = "com.rssl.iccs.dictionaries.description.";
	private static final String SOURCE_KEY = "com.rssl.iccs.dictionaries.source.";
	private static final String DESTINATION_KEY = "com.rssl.iccs.dictionaries.destination.";
	private static final String COMPARATOR_KEY = "com.rssl.iccs.dictionaries.comparator.";
	private static final String LOAD_GROUP_KEY = "com.rssl.iccs.dictionaries.load.group.";

	private static final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	private DateFormat getFormatter()
	{
		return (DateFormat)formatter.clone();
	}

	private Map<String, DictionaryDescriptor> dictionaryDescriptors;
	private Map<String, List<DictionaryDescriptor>> dictionaryGroupDescriptors;
    private List<DictionaryDescriptor> descriptors;

    private static final int MAX_DICTIONARIES = 100;

    public PropertiesDictionaryConfig(PropertyReader reader)
	{
		super(reader);
	}

	public Map<String, DictionaryDescriptor> getDictionaryDescriptors ()
	{
		return Collections.unmodifiableMap(dictionaryDescriptors);
	}

	public List<String> getGroupDescriptorNames(String name)
	{
		List<String> groupDescriptorNames = new ArrayList<String>();
		List<DictionaryDescriptor> groupDescriptors = dictionaryGroupDescriptors.get(name);
		if (groupDescriptors != null)
		{
			for (DictionaryDescriptor descriptor : groupDescriptors)
			{
				groupDescriptorNames.add(descriptor.getName());
			}
		}
		return groupDescriptorNames;
	}

    public DictionaryDescriptor getDescriptor(String name)
    {
        return dictionaryDescriptors.get(name);
    }

    public List<DictionaryDescriptor> getDescriptors()
    {
        return Collections.unmodifiableList(descriptors);
    }

    private void fillPropertiesMap () throws IllegalAccessException, InstantiationException, BusinessException
	{
		dictionaryDescriptors = new HashMap<String, DictionaryDescriptor>();
        descriptors = new ArrayList<DictionaryDescriptor>();
		dictionaryGroupDescriptors = new HashMap<String, List<DictionaryDescriptor>>();

        for (int i = 1; i <= MAX_DICTIONARIES; i++)
		{
			String nameKey = NAME_KEY+i;
			String descriptionKey = DESCRIPTION_KEY+i;
			String sourceKey = SOURCE_KEY+i;
			String destinationKey = DESTINATION_KEY+i;
			String temporarySourceKey       = SOURCE_KEY + "temporary." + i;
			String destinationDictionaryKey = DESTINATION_KEY + "dictionary." + i;
			String comparatorKey = COMPARATOR_KEY+i;
			String dateKey = NAME_KEY+i+".lastUpdate";
			String loadGroupKey = LOAD_GROUP_KEY + i;

			Date date = getDate(getProperty(dateKey));

			String name = getProperty(nameKey);
			String description = getProperty(descriptionKey);
			String sourceClassName = getProperty(sourceKey);

			if (StringHelper.isEmpty(sourceClassName))
			{
                continue;
			}

			Class<?> sourceClass      = getClass(sourceClassName);
			Class<?> destinationClass = getClass(getProperty(destinationKey));
			Class<?> comparatorClass  = getClass(getProperty(comparatorKey));

			DictionaryDescriptor dictionaryDescriptor = new DictionaryDescriptor();

			String temporarySourceProperty = getProperty(temporarySourceKey);
			if (StringHelper.isNotEmpty(temporarySourceProperty))
			{
				Class<?> temporarySourceClass = getClass(temporarySourceProperty);
				dictionaryDescriptor.setTemporarySource((ReplicaSource) temporarySourceClass.newInstance());
			}

			String destinationDictionaryProperty = getProperty(destinationDictionaryKey);
			if (StringHelper.isNotEmpty(destinationDictionaryProperty))
			{
				Class<?> dictionarySourceClass = getClass(destinationDictionaryProperty);
				dictionaryDescriptor.setDestinationDictionary((ReplicaDestination) dictionarySourceClass.newInstance());
			}

			dictionaryDescriptor.setName(name);
			dictionaryDescriptor.setDescription(description);
			dictionaryDescriptor.setSource((ReplicaSource)sourceClass.newInstance());
			dictionaryDescriptor.setDestination((ReplicaDestination)destinationClass.newInstance());
			dictionaryDescriptor.setComparator((Comparator)comparatorClass.newInstance());
			dictionaryDescriptor.setLastUpdateDate(date);

			String loadGroup = getProperty(loadGroupKey);
			if (StringHelper.isNotEmpty(loadGroup))
				dictionaryDescriptor.setLoadGroup(loadGroup);

			dictionaryDescriptors.put(name, dictionaryDescriptor);

			if (StringHelper.isNotEmpty(loadGroup))
			{
				List<DictionaryDescriptor> group = dictionaryGroupDescriptors.get(loadGroup);
				if (group == null)
				{
					group = new ArrayList<DictionaryDescriptor>();
					dictionaryGroupDescriptors.put(loadGroup, group);
				}
				group.add(dictionaryDescriptor);
			}
			else
				descriptors.add(dictionaryDescriptor);

		}
	}

	private Class<?> getClass ( String className )
	{
		try
		{
			return Thread.currentThread().getContextClassLoader().loadClass(className);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void doRefresh() throws ConfigurationException
	{
		try
		{
			fillPropertiesMap();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private Date getDate(String s) throws BusinessException
	{
		Date date = null;
		if (StringHelper.isEmpty(s))
			return date;
		try
		{
			date = getFormatter().parse(s);
		} catch (ParseException e){
			throw new BusinessException("Ошибка при получении даты "+e);
		}
		return date;
	}
}
