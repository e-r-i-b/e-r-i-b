package com.rssl.phizic.web.settings;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.phizic.config.PropertiesPageMode;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.*;

/**
 * ������� ����� �������������� ��������
 * @author gladishev
 * @ created 30.01.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditPropertiesFormBase extends EditFormBase
{
	private List<Long> nodes; //������ ��������������� ������ ��� ���������� ��������
	protected String[] selectedProperties; //������ ��������� �������� ��� ����������
	private Long[] selectedNodes; //������ ��������� ������ ��� ���������� ��������
	private Set<String> blackListFieldForReplication;  //������ �������� ����������� � ����������
	private String replicationGUID;  //������������� �������� ������������� ��������
	private Calendar replicationDate; //���� ������ �������� ����������
	private String date;
	private List<Object[]> syncInfo;     //���������� � ������������� ��������
	private PropertiesPageMode pageMode = PropertiesPageMode.EDIT;  //����� ����������� ��������
	private String replicatedNodes;

	/**
	 * �������� �� ������� ����������. ��������� ��� ��������������� ������ ������� ����.
	 */
	public boolean isReplication()
	{
		return pageMode == PropertiesPageMode.REPLICATE;
	}

	/**
	 * ��������� ������� ����������. ��������� ��� ��������������� ������ ������� ����.
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
	 * @return ������� �����
	 */
	public abstract Form getForm();

	/**
	 * @return ������ ���� ����� �����
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
	 * @return ������ ��������� ����� ��� ����������
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
