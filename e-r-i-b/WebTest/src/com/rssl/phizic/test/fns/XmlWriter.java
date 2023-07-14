package com.rssl.phizic.test.fns;

import java.util.Stack;

/**
 * Класс для создания строки xml
 */
class XmlWriter {

	private StringBuilder result;
	private Stack stack;
	private StringBuilder attrs;
	private boolean empty;
	private boolean textOnly;
	private boolean closed;
	private int margin;
	private boolean margins;

	/**
	 * Create an XmlWriter
	 */
	public XmlWriter(int margin) {
		this.result = new StringBuilder();
		this.closed = true;
		this.stack = new Stack();
		this.margin = margin;
		this.margins = this.margin >= 0;
	}

	public XmlWriter() {
		this(-1);
	}

	/**
	 * Создать новый тег
	 * @param name - имя тега
	 * @return this
	 */
	public XmlWriter writeEntity(String name) {
		closeOpeningTag(true);
		this.closed = false;
		writeMargin();
		this.result.append("<");
		this.result.append(name);
		this.stack.add(name);
		this.empty = true;
		this.textOnly = false;
		return this;
	}

	/**
	 * Создать атрибут
	 * @param attr - название атрибута
	 * @param value - значение атрибута
	 * @return - this
	 */
	public XmlWriter writeAttribute(String attr, String value) {
		if (this.attrs == null) {
			this.attrs = new StringBuilder();
		}
		this.attrs.append(" ");
		this.attrs.append(attr);
		this.attrs.append("=\"");
		this.attrs.append(escapeXml(value));
		this.attrs.append("\"");
		return this;
	}

	/**
	 * Завершение атрибута
	 * @return - this
	 * @throws Exception
	 */
	public XmlWriter endEntity() throws Exception {
		if (this.stack.empty()) {
			throw new Exception("Called endEntity too many times.");
		}
		String name = (String) this.stack.pop();
		if (name != null) {
			if (this.empty) {
				writeAttributes();
				this.result.append("/>");
				writeNewLine();
				this.closed = true;
			} else {
				if (!this.textOnly)	writeMargin();
				this.result.append("</");
				this.result.append(name);
				this.result.append(">");
				writeNewLine();
				this.closed = true;
			}
			this.empty = false;
		}
		return this;
	}

	/**
	 * Дописать xml
	 * @param xml - строка xml
	 * @return - this
	 */
	public XmlWriter writeXml(String xml) {
		closeOpeningTag(true);
		this.textOnly = false;
		this.empty = false;
		this.result.append(xml);
		return this;
	}

	private void closeOpeningTag(boolean newLine) {
		if (!this.closed) {
			writeAttributes();
			this.closed = true;
			this.result.append(">");
			if (newLine) writeNewLine();
		}
	}

	private void writeAttributes() {
		if (this.attrs != null) {
			this.result.append(this.attrs.toString());
			this.attrs.setLength(0);
		}
	}

	private void writeNewLine() {
		if (!this.margins) return;
		this.result.append((char)10);
	}

	private void writeMargin() {
		if (!this.margins) return;
		for (int i = 0; i < this.stack.size() + this.margin; i++) this.result.append((char)9);
	}

	public String toString() {
		return result.toString();
	}

	public XmlWriter writeText(String text) {
		closeOpeningTag(false);
		if (this.empty) this.textOnly = true;
		this.empty = false;
		this.result.append(escapeXml(text));
		return this;
	}

	private static String escapeXml(String text) {
		return text
				.replaceAll("\\&", "&amp;")
				.replaceAll("\\\"", "&quot;")
				.replaceAll("\\<", "&lt;")
				.replaceAll("\\>", "&gt;")
				.replaceAll("\\'", "&apos;");
	}

}
