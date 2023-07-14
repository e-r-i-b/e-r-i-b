package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.dictionaries.*;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 25.10.2005
 * Time: 18:54:16
 */
public class DictionaryDescriptor
{
	private String name;
	private String description;
    private ReplicaSource      source;
	private ReplicaDestination destination;
	private Comparator         comparator;
	private Date               lastUpdateDate;
	private ReplicaSource      temporarySource;
	private ReplicaDestination destinationDictionary;
	private String loadGroup;

	public Date  getLastUpdateDate ()
	{
		return lastUpdateDate;
	}

	public void setLastUpdateDate ( Date date )
	{
		this.lastUpdateDate = date;
	}
	public String getName ()
	{
		return name;
	}

	public void setName ( String name )
	{
		this.name = name;
	}

	/**
	 * @return описание справочника
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description описание справочника
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	public ReplicaSource getSource()
    {
        return source;
    }

    public void setSource(ReplicaSource source)
    {
        this.source = source;
    }

    public ReplicaDestination getDestination()
    {
        return destination;
    }

    public void setDestination(ReplicaDestination destination)
    {
        this.destination = destination;
    }

    public Comparator getComparator()
    {
        return comparator;
    }

    public void setComparator(Comparator comparator)
    {
        this.comparator = comparator;
    }

	public boolean isXmlSource()
	{
		return (source!=null) && (source instanceof XmlReplicaSource);
	}

	public boolean isCsvSource()
	{
		return (source!=null) && (source instanceof CsvReplicaSource);
	}

	public void setTemporarySource(ReplicaSource temporarySource)
	{
		this.temporarySource = temporarySource;
	}

	public ReplicaSource getTemporarySource()
	{
		return temporarySource;
	}

	public ReplicaDestination getDestinationDictionary()
	{
		return destinationDictionary;
	}

	public void setDestinationDictionary(ReplicaDestination destinationDictionary)
	{
		this.destinationDictionary = destinationDictionary;
	}

	public String getLoadGroup()
	{
		return loadGroup;
	}

	public void setLoadGroup(String loadGroup)
	{
		this.loadGroup = loadGroup;
	}
}
