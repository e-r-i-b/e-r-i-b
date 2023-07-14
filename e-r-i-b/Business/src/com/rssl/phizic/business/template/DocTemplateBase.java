package com.rssl.phizic.business.template;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 22.01.2007 Time: 13:12:46 To change this template use File
 * | Settings | File Templates.
 */
public interface DocTemplateBase
{
    /**
	 * @return id документа
	 */
	Long getId();

	/**
	 * @param id документа
	 */
	void setId(Long id);

	/**
	 * @return наименование документа
	 */
	String getName();

	/**
	 * @param name наименование документа
	 */
	void setName(String name);

	/**
	 * @return файл документа
	 */
	byte[] getData();

	/**
	 * @param data файл документа
	 */
	void setData(byte[] data);

	/**
	 * @return департамент
	 */
	Long getDepartmentId();

	/**
	 * @param departmentId департамент
	 */
	void setDepartmentId(Long departmentId);

	/**
	 * @return дата создания документа
	 */
	Calendar getUpdate();

	/**
	 * @param update дата создания документа
	 */
	void setUpdate(Calendar update);

	/**
	 * @return описание документа
	 */
	String getDescription();

	/**
	 * @param description описание документа
	 */
	void setDescription(String description);

	/**
	 * @return имя загруженного файла
	 */
	String getFileName();
	/**
	 * @param fileName имя загруженного файла
	 */
	void setFileName(String fileName);

	/**
	 * @return тип загруженного файла
	 */
	String getFileType();
	/**
	 * @param fileType тип загруженного файла
	 */
	void setFileType(String fileType);


}
