<%--
  Created by IntelliJ IDEA.
  User: Kosyakov
  Date: 20.09.2006
  Time: 13:08:54
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% pageContext.getRequest().setAttribute("mode", "Services");%>
<% pageContext.getRequest().setAttribute("userMode", "SynchronizeDictionaries");%>
<html:form action="/dictionaries/synchronize" enctype="multipart/form-data">
	<tiles:insert definition="dictionariesMain">
		<tiles:put name="submenu" type="string" value="Synchronize"/>
		<tiles:put name="pageTitle" type="string">
			Загрузка справочников
		</tiles:put>
		<tiles:put name="menu" type="string">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.loadDictionaries"/>
				<tiles:put name="commandHelpKey" value="button.loadDictionaries.help"/>
				<tiles:put name="bundle"  value="employeesBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
			</tiles:insert>
		</tiles:put>
		<tiles:put name="data" type="string">
			<table cellpadding="0" cellspacing="0" class="MaxSize">
			<tr>
			<td height="100%">
			<div class="MaxSize">
			<table cellspacing="2" cellpadding="0" onkeypress="onEnterKey(event);">
				<tr>
					<td>
						Выберите XML-файл справочников для загрузки:
					</td>
				</tr>
				<tr>
					<td>
						<html:file property="content" size="100"/>
					</td>
				</tr>
				<logic:iterate id="descriptor" name="SynchronizeDictionariesForm" property="descriptors">
					<c:if test="${descriptor.xmlSource}">
						<tr>
							<td>
								<html:multibox property="selected" style="border:0" value="${descriptor.name}"/>
								<bean:write name="descriptor" property="name"/>
							</td>
						</tr>
					</c:if>
				</logic:iterate>
			</table>
			</div>
			</td>
			</tr>
			</table>
		</tiles:put>
	</tiles:insert>
</html:form>
