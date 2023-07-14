package com.rssl.phizic.operations.dictionaries.kbk;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.dictionaries.kbk.KBK;
import com.rssl.phizic.business.dictionaries.kbk.KBKReplicaSource;
import com.rssl.phizic.business.dictionaries.kbk.KBKReplicaDestination;
import com.rssl.phizic.business.dictionaries.kbk.KBKComparator;
import com.rssl.phizic.business.dictionaries.currencies.CurrencyServiceImpl;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.dictionaries.OneWayReplicator;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author akrenev
 * @ created 08.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ReplicateKBKOperation  extends OperationBase
{
	private BufferedReader reader;
	private long recordCount = 0;
	private long wrongCount = 0;
	private Object[] errors;

	public void initialize(InputStream inputStream) throws IOException
	{
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		reader = new BufferedReader(inputStreamReader);
	}

	/**
	 * Репликация справочника КБК из текстового файла.
	 */
	public void replicate() throws Exception
	{
		KBKReplicaSource source = new KBKReplicaSource(reader);
		KBKReplicaDestination destination = new KBKReplicaDestination();
		KBKComparator comparator = new KBKComparator();
		OneWayReplicator replicator = new OneWayReplicator(source, destination, comparator);
		replicator.replicate();
		List errors = source.getErrors();
		this.errors = errors.toArray();
		recordCount = source.getRecordCount();
		wrongCount = errors.size();
	}

	public long getRecordCount()
	{
		return recordCount;
	}

	public long getWrongCount()
	{
		return wrongCount;
	}

	public Object[] getErrors()
	{
		return errors;
	}
}
