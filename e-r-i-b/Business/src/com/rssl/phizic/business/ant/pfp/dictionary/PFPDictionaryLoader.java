package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ant.pfp.dictionary.actions.PFPDictionaryConfig;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import com.rssl.phizic.business.dictionaries.pfp.common.MarkDeletedRecord;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.OneWayReplicator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author akrenev
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������������ ���
 */
public class PFPDictionaryLoader
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();
	private File sourceFile;             //�������� ��������
	private boolean deleteUnknownProduct; //����� �� ������� ����������� ������
	private String dbInstanceName; //��� �������� ��

	/**
	 * ������ ������������� ������� ����������� ������
	 * @param deleteUnknownProduct ����� �� ������� ����������� ������
	 */
	public void setDeleteUnknownProduct(boolean deleteUnknownProduct)
	{
		this.deleteUnknownProduct = deleteUnknownProduct;
	}

	/**
	 * ������ ��� �������� ��
	 * @param dbInstanceName ��� �������� ��
	 */
	public void setDbInstanceName(String dbInstanceName)
	{
		this.dbInstanceName = dbInstanceName;
	}

	/**
	 * ������ �������� ��������
	 * @param sourceFile ��������
	 */
	public void setSourceFile(File sourceFile)
	{
		this.sourceFile = sourceFile;
	}

	/**
	 * ��������� ����������� ���
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void load() throws BusinessLogicException, BusinessException
	{
		try
		{
			List<PFPDictionaryRecord> removedRecords = new ArrayList<PFPDictionaryRecord>();
			log.info("������ xml");
			XmlPFPDictionary xmlPFPDictionary = new XmlPFPDictionary(sourceFile);
			xmlPFPDictionary.refresh();
			DbPFPDictionary dbPFPDictionary = new DbPFPDictionary(dbInstanceName);
			for (PFPDictionaryConfig config: PFPDictionaryConfig.values())
			{
				log.info("���������� ����������� \"" + config.getEntriesDescription() + "\"");
				PFPDictionaryReplicaSource replicaSource = config.getReplicaSource(xmlPFPDictionary);
				PFPDictionaryReplicaDestination replicaDestination = config.getReplicaDestination(dbPFPDictionary, dbInstanceName);
				replicaDestination.setDeleteUnknownProduct(deleteUnknownProduct);
				Comparator comparator = config.getComparator();
				OneWayReplicator replicator = new OneWayReplicator(replicaSource, replicaDestination, comparator);
				replicator.replicate();
				removedRecords.addAll(replicaDestination.getRemovedRecords());
			}

			if (deleteUnknownProduct)
			{
				log.info("�������� ����������� �������");
				//��� �������� ��������� ��������:
				//������� ������� ����������� �������, ����� ������� 
				for (int i = removedRecords.size() - 1 ; i >= 0 ; i--)
				{
					PFPDictionaryRecord record = removedRecords.get(i);
					if (record instanceof MarkDeletedRecord)
					{
						((MarkDeletedRecord)record).setDeleted(true);
						service.update(record, dbInstanceName);
					}
					else
					{
						service.remove(record, dbInstanceName);
					}
				}
			}
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
