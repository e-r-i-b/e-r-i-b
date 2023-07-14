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

		<tiles:put name="data" type="string">
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="HandBooksPh"/>
				<tiles:put name="name" value="Загрузка справочников"/>
				<tiles:put name="description" value="Используйте данную форму загрузки спрвочников."/>
				<tiles:put name="data">
				<c:forEach items="${SynchronizeDictionariesForm.descriptors}" var="descriptor">
				    <c:set var="name" value="${descriptor.name}"/>
					<tr>
						<td>

							<html:multibox property="selected" style="border:none">
								<c:out value="${name}"/>
							</html:multibox>
						</td>
						<td>
							<c:out value="${name}"/> <i><c:out value="(Последнее обновление: "/><bean:write name="descriptor" property="lastUpdateDate" format="dd.MM.yyyy HH:mm:ss"/><c:out value=")"/></i>
                        </td>
					</tr>
				</c:forEach>
				<%--<logic:iterate id="descriptor" name="SynchronizeDictionariesForm" property="descriptors">--%>
					<!--<tr>-->
						<!--<td>-->
							<%--<%--<html:multibox property="selected" style="border:0" value="${descriptor.name}"/>--%>
							<%--<%--<bean:write name="descriptor" property="name"/>--%>
							<%--<html:multibox property="selected" style="border:none">--%>
								<%--<bean:write name="descriptor" property="name"/>--%>
							<%--</html:multibox>--%>
						<!--</td>-->
					<!--</tr>-->
				<%--</logic:iterate>--%>
			</tiles:put>
			<tiles:put name="buttons">
		        <tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey" value="button.loadDictionaries"/>
					<tiles:put name="commandHelpKey" value="button.loadDictionaries.help"/>
					<tiles:put name="bundle" value="employeesBundle"/>
				</tiles:insert>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
		</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
