package com.rssl.phizic.web.client.userprofile.avatars;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * Форма загрузки аватара.
 *
 * @author bogdanov
 * @ created 25.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UploadAvatarForm extends ActionForm
{
	private FormFile avatarFile;
	private int x;
	private int y;
	private int width;
	private int height;
	private String state; //load, loading, error, save, cancel, delete
	private String error;
	private String fileName;
	private boolean hasAvatar;

	private int maxFileSize;
	private String fileTypes;
	private String mimeTypes;
	private double imageWidth;
	private double imageHeight;

	/**
	 * @return загруженный пользователем файл аватара.
	 */
	public FormFile getAvatarFile()
	{
		return avatarFile;
	}

	/**
	 * @param avatarFile загруженный пользователем файл аватара.
	 */
	public void setAvatarFile(FormFile avatarFile)
	{
		this.avatarFile = avatarFile;
	}

	/**
	 * @return высота изображения после обрезки.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * @param height высота изображения после обрезки.
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * @return ширина изображения после обрезки.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @param width ширина изображения после обрезки.
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}

	/**
	 * @return x после обрезки.
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @param x x после обрезки.
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return y после обрезки.
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * @param y y после обрезки.
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * @return состояние загрузки.
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * @param state состояние загрузки.
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return сообщение об ошибке, отображаемое пользователю.
	 */
	public String getError()
	{
		return error;
	}

	/**
	 * @param error сообщение об ошибке, отображаемое пользователю.
	 */
	public void setError(String error)
	{
		this.error = error;
	}

	/**
	 * @return имя загружаемого файла.
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * @param fileName имя загружаемого файла.
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * @return тип файла.
	 */
	public String getFileTypes()
	{
		return fileTypes;
	}

	/**
	 * @param fileTypes тип файла.
	 */
	public void setFileTypes(String fileTypes)
	{
		this.fileTypes = fileTypes;
	}

	/**
	 * @return максимальный размер файла.
	 */
	public int getMaxFileSize()
	{
		return maxFileSize;
	}

	/**
	 * @param maxFileSize максимальный размер файла.
	 */
	public void setMaxFileSize(int maxFileSize)
	{
		this.maxFileSize = maxFileSize;
	}

	/**
	 * @return типы файлов, доступные для загрузки.
	 */
	public String getMimeTypes()
	{
		return mimeTypes;
	}

	/**
	 * @param mimeTypes типы файлов, доступные для загрузки.
	 */
	public void setMimeTypes(String mimeTypes)
	{
		this.mimeTypes = mimeTypes;
	}

	/**
	 * @return существует ли аватар.
	 */
	public boolean isHasAvatar()
	{
		return hasAvatar;
	}

	/**
	 * @param hasAvatar существует ли аватар.
	 */
	public void setHasAvatar(boolean hasAvatar)
	{
		this.hasAvatar = hasAvatar;
	}

	/**
	 * @param imageWidth ширина изображения.
	 */
	public void setImageWidth(double imageWidth)
	{
		this.imageWidth = imageWidth;
	}

	/**
	 * @return ширина изображения.
	 */
	public double getImageWidth()
	{
		return imageWidth;
	}

	/**
	 * @param imageHeight высота изображения.
	 */
	public void setImageHeight(double imageHeight)
	{
		this.imageHeight = imageHeight;
	}

	/**
	 * @return высота изображения.
	 */
	public double getImageHeight()
	{
		return imageHeight;
	}
}
