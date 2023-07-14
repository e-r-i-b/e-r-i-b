package com.rssl.phizic.business.template;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 22.01.2007 Time: 13:12:46 To change this template use File
 * | Settings | File Templates.
 */
public interface DocTemplateBase
{
    /**
	 * @return id ���������
	 */
	Long getId();

	/**
	 * @param id ���������
	 */
	void setId(Long id);

	/**
	 * @return ������������ ���������
	 */
	String getName();

	/**
	 * @param name ������������ ���������
	 */
	void setName(String name);

	/**
	 * @return ���� ���������
	 */
	byte[] getData();

	/**
	 * @param data ���� ���������
	 */
	void setData(byte[] data);

	/**
	 * @return �����������
	 */
	Long getDepartmentId();

	/**
	 * @param departmentId �����������
	 */
	void setDepartmentId(Long departmentId);

	/**
	 * @return ���� �������� ���������
	 */
	Calendar getUpdate();

	/**
	 * @param update ���� �������� ���������
	 */
	void setUpdate(Calendar update);

	/**
	 * @return �������� ���������
	 */
	String getDescription();

	/**
	 * @param description �������� ���������
	 */
	void setDescription(String description);

	/**
	 * @return ��� ������������ �����
	 */
	String getFileName();
	/**
	 * @param fileName ��� ������������ �����
	 */
	void setFileName(String fileName);

	/**
	 * @return ��� ������������ �����
	 */
	String getFileType();
	/**
	 * @param fileType ��� ������������ �����
	 */
	void setFileType(String fileType);


}
