package com.rssl.phizic.operations.config;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.config.Property;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.config.propertysyncinfo.PropertiesSyncInfoService;
import com.rssl.phizic.config.propertysyncinfo.PropertySyncInfoStatus;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizic.utils.xml.xstream.XStreamSerializer;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import javax.jms.JMSException;

import static com.rssl.phizic.config.DbPropertyService.REPLICATE_PROPERTIES_MESSAGE_KEY;
import static com.rssl.phizic.config.DbPropertyService.SYNCHRONIZATION_GUID_KEY;

/**
 * �������� �������������� �������� �������
 * @author gladishev
 * @ created 29.01.14
 * @ $Author$
 * @ $Revision$
 */
public class EditPropertiesOperation<R extends Restriction> extends OperationBase<R> implements EditEntityOperation<Map<String, String>, R>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final JmsService jmsService = new JmsService();
	private static final String PROPERTY_CATEGORY = "PropertyCategory";
	private static final String REPLICATION_MESSAGE_SEND = "��������� [%s] ��� ���������� ����������, queueName=%s, factoryName=%s";

	private static final PropertiesSyncInfoService syncInfoService = new PropertiesSyncInfoService();

	private Map<String, String> properties; // ������ ������������� ��������.
	private PropertyCategory propertyCategory; //��������� ��������
	private Map<String, Object> replicatedProperties = new HashMap<String, Object>();
	private String guid;                       //������������� �������� ����������
	private Calendar replicationDate;          //���� ���������� �������� ���������� �����������
	private List<Object[]> syncInfo = new ArrayList<Object[]>();  //���������� � ��������� �������� ����������


	/**
	 * ���������������� ������ �������� ������ �����
	 * @param propertyCategory - ��������� ��������
	 */
	public void initialize(PropertyCategory propertyCategory) throws BusinessException
	{
		this.propertyCategory = propertyCategory;
		properties = new HashMap<String, String>();
	}

	/**
	 * ���������������� ������ ��������
	 * @param propertyCategory - ��������� ��������
	 * @param propertyKeys - ������ ������ ��������
	 */
	public void initialize(PropertyCategory propertyCategory, Set<String> propertyKeys) throws BusinessException
	{
		if (propertyKeys.isEmpty())
			initialize(propertyCategory);
		else
		{
			this.propertyCategory = propertyCategory;

			List<Property> props = findProperties(propertyCategory, propertyKeys);
			this.properties = new HashMap<String, String>(props.size());
			for (Property prop : props)
				this.properties.put(prop.getKey(), prop.getValue());
		}

	}

	public void initializeListProperties(String propertyKey)
	{
		List<Property> props = findPropertiesByKey(propertyKey);
		String[] result = new String[props.size()];
		for (int i = 0; i<props.size(); i++)
		{
			result[i] = props.get(i).getValue();
		}
		replicatedProperties.put(propertyKey, result);
	}

	public void initializeReplicate(PropertyCategory category, Set<String> keys) throws BusinessException, BusinessLogicException
	{
		generateGUID();
		initialize(category, keys);
		replicationAdditionalInit();
	}

	/**
	 * �������������� �������� ��� ����������� ���������� � ��������� ������������� ��������
	 * @param guid - ������������� �������� ��������������
	 * @param date - ���� ���������� �������������
	 */
	public void initialiizeViewSyncInfo(String guid, Calendar date, List<String> nodes) throws Exception
	{
		List<Object[]> buf = syncInfoService.getInfoByGUID(guid, date);
		syncInfo = new ArrayList<Object[]>();
		//������� ����� � ��������� ���������� ���������� �� ��
		for (Object[] item: buf)
		{
			nodes.remove(item[0].toString());
			item[1] = PropertySyncInfoStatus.valueOf((String)item[1]);
			syncInfo.add(item);
		}
		//����� ��� ���� ������, ��� ������� �� ������� ������ ����������� ������ "��������������"
		for (String node :nodes)
		{
			Object[] item = new Object[2];
			item[0] = node;
			item[1] = PropertySyncInfoStatus.PROCESSING;
			syncInfo.add(item);
		}
	}

	/**
	 * ��������� �������������� �������� ������������� ��������
	 */
	private void generateGUID()
	{
		this.guid = new RandomGUID().getStringValue();
	}

	/**
	 * �������������� ������������� ������ ����������� ��� ������������ ��������� ������� �������������
	 */
	private void replicationAdditionalInit()
	{
		this.properties.put(SYNCHRONIZATION_GUID_KEY, this.guid);
		this.replicationDate = Calendar.getInstance();
		this.replicationDate.add(Calendar.SECOND, -1);
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		DbPropertyService.updateProperties(properties, propertyCategory, getDbInstance(propertyCategory));
	}

	public Map<String, String> getEntity()
	{
		return properties;
	}

	/**
	 * ���������� ��������� �������� � MQ ������
	 * @param nodeIds - �������������� ������
	 */
	public List<Long> replicate(List<Long> nodeIds) throws BusinessException, BusinessLogicException
	{
		if (CollectionUtils.isEmpty(nodeIds))
			throw new BusinessLogicException("�� ������� ����� ��� ���������� ��������");

		replicatedProperties.put(PROPERTY_CATEGORY, propertyCategory.getValue());

		List<Long> errorNodes = new ArrayList<Long>();

		for (Long nodeId : nodeIds)
		{
			NodeInfo node = ConfigFactory.getConfig(NodeInfoConfig.class).getNode(nodeId);

			if (node == null)
			{
				log.error("������ ����������: �� ������ ���� � ��������������� " + nodeId);
				continue;
			}

			MQInfo dictionaryMQ = node.getDictionaryMQ();
			try
			{
				String serializedProperties = XStreamSerializer.serialize(getReplicatedProperties());
				String queueName = dictionaryMQ.getQueueName();
				String factoryName = dictionaryMQ.getFactoryName();
				jmsService.sendObjectToQueue(serializedProperties, queueName, factoryName, REPLICATE_PROPERTIES_MESSAGE_KEY, null);
				log.trace(String.format(REPLICATION_MESSAGE_SEND, serializedProperties, queueName, factoryName));
			}
			catch (JMSException e)
			{
				log.error(e.getMessage(), e);
				errorNodes.add(nodeId);
			}
		}

		return errorNodes;
	}

	protected List<Property> findProperties(PropertyCategory category, Set<String> propertyKeys)
	{
		return DbPropertyService.findProperties(propertyKeys, category.getValue(), getDbInstance(category));
	}

	protected List<Property> findPropertiesByKey(String key)
	{
		return DbPropertyService.findPropertiesLike(key, propertyCategory.getValue(), getDbInstance(propertyCategory));
	}

	protected PropertyCategory getPropertyCategory()
	{
		return propertyCategory;
	}

	protected String getDbInstance(PropertyCategory category)
	{
		return DbPropertyService.getDbInstance(category.getValue());
	}

	protected void addProperties(Map<String, String> properties)
	{
		this.properties.putAll(properties);
	}

	protected void addProperty(String key, String value)
	{
		this.properties.put(key, value);
	}

	protected Map<String, Object> getReplicatedProperties() throws BusinessException, BusinessLogicException
	{
		replicatedProperties.putAll(getEntity());
		return replicatedProperties;
	}

	/**
	 * @return �������������� ������ �������
	 */
	public List<Long> getNodes()
	{
		Collection<NodeInfo> nodes = ConfigFactory.getConfig(NodeInfoConfig.class).getNodes();
		if (CollectionUtils.isEmpty(nodes))
			return null;

		List<Long> result = new ArrayList<Long>(nodes.size());
		for (NodeInfo node : nodes)
			result.add(node.getId());

		Collections.sort(result);
		return result;
	}

	/**
	 * @return ������ �������� ������� �� ������ ����������� � ����������
	 */
	public Set<String> getBlackListFieldForReplication() throws BusinessLogicException, BusinessException
	{
		return Collections.emptySet();
	}

	public String getGuid()
	{
		return guid;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}

	public Calendar getReplicationDate()
	{
		return replicationDate;
	}

	public void setReplicationDate(Calendar replicationDate)
	{
		this.replicationDate = replicationDate;
	}

	public List<Object[]> getSyncInfo()
	{
		return syncInfo;
	}

	public void setSyncInfo(List<Object[]> syncInfo)
	{
		this.syncInfo = syncInfo;
	}

	public Map<String, String> getProperties()
	{
		return properties;
	}

	public void setProperties(Map<String, String> properties)
	{
		this.properties = properties;
	}
}
