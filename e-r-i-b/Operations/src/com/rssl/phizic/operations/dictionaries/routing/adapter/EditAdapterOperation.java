package com.rssl.phizic.operations.dictionaries.routing.adapter;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.config.Property;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizicgate.manager.routing.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author akrenev
 * @ created 08.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditAdapterOperation extends OperationBase implements EditEntityOperation
{
	private static final String constKeyWebService = "com.rssl.phisicgate.sbrf.ws.cod.url.";

	protected static final SimpleService simpleService = new SimpleService();
	private Adapter adapter;
	private Node node = null;
	private NodeAndAdapterRelation relation = null;
	private String addressWebService;

	private static final AdapterService adapterService = new AdapterService();
	private static final NodeService nodeService = new NodeService();
	private static final NodeAndAdapterRelationService nodeAndAdapterRelationService = new NodeAndAdapterRelationService();

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		try
		{
			adapter = adapterService.getAdapterById(id);
			if (adapter == null)
			{
				throw new BusinessLogicException("Адаптер с id=" + id + " не найден");
			}

			// в последствии понадобиться список всех узлов адаптера, а пока он один выбираем таким образом
			List<Node> nodes = adapterService.getNodes(adapter);

			if (CollectionUtils.isEmpty(nodes))
				return;
			
			node = nodes.get(nodes.size()-1);
			relation = nodeAndAdapterRelationService.getRelation(adapter, node);

			if (node != null && node.getType() == NodeType.SOFIA)
			{
				DetachedCriteria criteria = DetachedCriteria.forClass(Property.class);
				criteria.add(Expression.eq("key", constKeyWebService + adapter.getUUID()));
				criteria.add(Expression.eq("category", node.getPrefix()));
				Property propertyWithAddress = simpleService.findSingle(criteria);
				if (propertyWithAddress != null)
					addressWebService = propertyWithAddress.getValue();
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	public void initializeNew()
	{
		adapter = new Adapter();
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		if (adapter.getType() == AdapterType.NONE && node == null)
			throw new BusinessLogicException("Нужно указать узел через который будет осуществляться связь с адаптером.");

		try
		{
			adapterService.addOrUpdate(adapter);

			//адаптер интегрированный
			if(adapter.getType() != AdapterType.NONE)
				return;

			if (relation == null)
			{
				//перед созданием новой связи лучше еше раз проверить.
				//бывает такая связь уже есть. и если мы создадим вторую таку же, то потом возникнет ошибка
				relation = nodeAndAdapterRelationService.getRelation(adapter, node);
				if (relation == null)
				{
					relation = new NodeAndAdapterRelation();
					relation.setAdapter(adapter);
				}
			}

			relation.setNode(node);
			nodeAndAdapterRelationService.addOrUpdate(relation);

			if (node.getType() == NodeType.SOFIA)
			{
				DetachedCriteria criteria = DetachedCriteria.forClass(Property.class);
				criteria.add(Expression.eq("key", constKeyWebService + adapter.getUUID()));
				criteria.add(Expression.eq("category", node.getPrefix()));
				Property propertyWithAddress = simpleService.findSingle(criteria);
				if (propertyWithAddress == null)
				{
					propertyWithAddress = new Property();
					propertyWithAddress.setKey(constKeyWebService + adapter.getUUID());
					propertyWithAddress.setCategory(node.getPrefix());
				}
				propertyWithAddress.setValue(addressWebService);
				simpleService.addOrUpdate(propertyWithAddress);
			}

		}
		catch (DublicateAdapterException e)
		{
			throw new BusinessLogicException(e.getMessage());
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

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return adapter;
	}

	public Node getNode()
	{
		return node;
	}

	public void setNode(Long id) throws BusinessException
	{
		try
		{
			this.node = nodeService.getNodeById(id);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	public List<Node> getAllNodes() throws BusinessException
	{
		try
		{
			return adapterService.getNodes();
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	public String getAddressWebService()
	{
		return addressWebService;
	}

	public void setAddressWebService(String addressWebService)
	{
		this.addressWebService = addressWebService;
	}
}
