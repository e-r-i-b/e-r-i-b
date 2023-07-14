package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.AlfOperation;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ����� �� ������ ������� �������� ���
 *
 * @author Balovtsev
 * @since 12.05.2014
 */
@XmlType(propOrder = {"status", "operations", "numberOfOperations"})
@XmlRootElement(name = "message")
public class AlfHistoryResponse extends Response
{
	private Integer            numberOfOperations;
	private List<AlfOperation> operations;

	/**
	 */
	public AlfHistoryResponse()
	{
		super();
	}

	/**
	 * @param status ������ ������
	 * @param operations ������ ��������. ����� ���� null
	 */
	public AlfHistoryResponse(Status status, List<AlfOperation> operations)
	{
		super(status);
		this.operations = operations;
	}

	/**
	 * @return ������ ��������
	 */
	@XmlElementWrapper(name = "operations", required = false)
	@XmlElement(name = "operation", required = true)
	public List<AlfOperation> getOperations()
	{
		return operations;
	}

	/**
	 * �������������� �������
	 * @return ����� ���������� ��������
	 */
	@XmlElement(name = "numberOfOperations", required = false)
	public Integer getNumberOfOperations()
	{
		return numberOfOperations;
	}

	public void setOperations(List<AlfOperation> operations)
	{
		this.operations = operations;
	}

	public void setNumberOfOperations(Integer numberOfOperations)
	{
		this.numberOfOperations = numberOfOperations;
	}
}
