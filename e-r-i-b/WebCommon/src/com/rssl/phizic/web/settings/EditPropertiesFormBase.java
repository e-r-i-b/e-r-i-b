package com.rssl.phizic.web.settings;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.phizic.config.PropertiesPageMode;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.*;

/**
 * Базовая форма редактирования настроек
 * @author gladishev
 * @ created 30.01.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditPropertiesFormBase extends EditFormBase
{
	private List<Long> nodes; //список идентификаторов блоков для репликации настроек
	protected String[] selectedProperties; //список выбранных настроек для репликации
	private Long[] selectedNodes; //список выбранных блоков для репликации настроек
	private Set<String> blackListFieldForReplication;  //массив настроек запрещенных к репликации
	private String replicationGUID;  //идентификатор операции синхронизации настроек
	private Calendar replicationDate; //дата начала операции репликации
	private String date;
	private List<Object[]> syncInfo;     //информация о синхронизации настроек
	private PropertiesPageMode pageMode = PropertiesPageMode.EDIT;  //режим отображения страницы
	private String replicatedNodes;

	/**
	 * проверка на признак репликпции. Оставлено для консистентности работы старого кода.
	 */
	public boolean isReplication()
	{
		return pageMode == PropertiesPageMode.REPLICATE;
	}

	/**
	 * выставить признак репликации. Оставлено для консистентности работы старого кода.
	 */
	public void setReplication(boolean replication)
	{
		if (replication)
			this.pageMode = PropertiesPageMode.REPLICATE;
	}

	public List<Long> getNodes()
	{
		return nodes;
	}

	public void setNodes(List<Long> nodes)
	{
		this.nodes = nodes;
	}

	public String[] getSelectedProperties()
	{
		return selectedProperties;
	}

	public void setSelectedProperties(String[] selectedProperties)
	{
		this.selectedProperties = selectedProperties;
	}

	public Long[] getSelectedNodes()
	{
		return selectedNodes;
	}

	public void setSelectedNodes(Long[] selectedNodes)
	{
		this.selectedNodes = selectedNodes;
	}

	public void setBlackListFieldForReplication(Set<String> blackListFieldForReplication)
	{
		this.blackListFieldForReplication = blackListFieldForReplication;
	}

	public Set<String> getBlackListFieldForReplication()
	{
		return blackListFieldForReplication;
	}

	/**
	 * @return текущая форма
	 */
	public abstract Form getForm();

	/**
	 * @return список имен полей формы
	 */
	public Set<String> getFieldKeys()
	{
		List<Field> fields = getForm().getFields();
		Set<String> result = new HashSet<String>(fields.size());
		for (Field field : fields)
			result.add(field.getName());

		return  result;
	}

	/**
	 * @return список выбранных полей для репликации
	 */
	public String[] getPropertiesForReplicate()
	{
		return selectedProperties;
	}

	public String getReplicationGUID()
	{
		return replicationGUID;
	}

	public void setReplicationGUID(String replicationGUID)
	{
		this.replicationGUID = replicationGUID;
	}

	public Calendar getReplicationDate()
	{
		return replicationDate;
	}

	public void setReplicationDate(Calendar replicationDate)
	{
		this.replicationDate = replicationDate;
	}

	public PropertiesPageMode getPageMode()
	{
		return pageMode;
	}

	public void setPageMode(PropertiesPageMode pageMode)
	{
		this.pageMode = pageMode;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public List<Object[]> getSyncInfo()
	{
		return syncInfo;
	}

	public void setSyncInfo(List<Object[]> syncInfo)
	{
		this.syncInfo = syncInfo;
	}

	public String getReplicatedNodes()
	{
		return replicatedNodes;
	}

	public void setReplicatedNodes(String replicatedNodes)
	{
		this.replicatedNodes = replicatedNodes;
	}
}
