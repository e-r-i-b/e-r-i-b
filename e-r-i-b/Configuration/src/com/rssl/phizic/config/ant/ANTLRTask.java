package com.rssl.phizic.config.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.antlr.ANTLR3;
import org.apache.tools.ant.taskdefs.DefBase;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * @author Erkin
 * @ created 06.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Генерирует классы парсера и лексера по грамматике
 */
public class ANTLRTask extends DefBase
{
	private File target;
	
	private File outputDirectory;

	///////////////////////////////////////////////////////////////////////////

	public void setTarget(File target)
	{
		this.target = target;
	}

	public void setOutputDirectory(File outputDirectory)
	{
		this.outputDirectory = outputDirectory;
	}

	@Override
	public void execute() throws BuildException
	{
		// 1. В файле грамматики заменяем не ASCII-символы кодами
		// и сохраняем результат в промежуточный файл
		File normTarget = normalizeGrammarFile(target);

		// 2. Результат п.1 отдаём оригинальному ANTLR-таску
		ANTLR3 antlr = new ANTLR3();
		antlr.init();
		antlr.setTarget(normTarget);
		antlr.setOutputdirectory(outputDirectory);
		antlr.createClasspath().add(getClasspath());
		antlr.execute();
	}

	private File normalizeGrammarFile(File input) throws BuildException
	{
		File output = createNormalizedGramarFile(input);

		InputStream is = null;
		Reader reader = null;
		Writer writer = null;
		try
		{
			is = new FileInputStream(input);
			reader = new InputStreamReader(is, "UTF8");
			writer = new FileWriter(output);

			CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();

			while (true)
			{
				int c = reader.read();
				if (c < 0)
					break;

				if (asciiEncoder.canEncode((char) c))
					writer.write(c);
				else writer.write(String.format("\\u%04x", c));
			}
		}
		catch (IOException e)
		{
			//noinspection ResultOfMethodCallIgnored
			output.delete();
			throw new BuildException(e);
		}
		finally
		{
			if (reader != null)
				try { reader.close(); } catch (IOException ignored) {}
			if (writer != null)
				try { writer.close(); } catch (IOException ignored) {}
			if (is != null)
				try { is.close(); } catch (IOException ignored) {}
		}

		return output;
	}

	private File createNormalizedGramarFile(File input) throws BuildException
	{
		try
		{
			// 1. Узнаём директорию временных файлов текущего пользователя
			File temproot = getTempRoot();

			// 2. Получаем директорию временных файлов для PhizIC
			File tempdir = new File(temproot, "phiz");
			if (tempdir.exists()) {
				if (!tempdir.isDirectory()) {
					FileUtils.forceDelete(tempdir);
					FileUtils.forceMkdir(tempdir);
				}
			}
			else {
				FileUtils.forceMkdir(tempdir);
			}

			// 3. Результат: <директорию_временных_файлов>/<input>
			return new File(tempdir, input.getName());
		}
		catch (IOException e)
		{
			throw new BuildException(e);
		}
	}

	private static File getTempRoot() throws IOException {return File.createTempFile("phizic", "temp").getParentFile();}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
