<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/pfr/pfrTemplate" enctype="multipart/form-data">
	<tiles:insert definition="logMain">
        <c:set var="bundle" value="pfrBundle"/>
		<tiles:put name="submenu" type="string" value="PFRTemplate"/>

		<tiles:put name="data" type="string">
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="PFRF"/>
				<tiles:put name="name" value="Шаблон выписки ПФР"/>
				<tiles:put name="description" value="Используйте данную форму загрузки (выгрузки) XSLT (XSD) файлов."/>
				<tiles:put name="data">
                    <table>
                        <tr>
                            <td colspan="3">
                                <b><bean:message bundle="${bundle}" key="select.xslt.file.load"/></b>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <html:file property="xsltLoadable" size="40" />
                            </td>
                            <td>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey" value="button.xsltLoad"/>
                                    <tiles:put name="commandHelpKey" value="button.xsltLoad.help"/>
                                    <tiles:put name="bundle" value="pfrBundle"/>
                                </tiles:insert>
                            </td>
                            <td>
                                <html:link action="/pfr/pfrTemplate.do?type=xslt">
                                    <bean:message bundle="${bundle}" key="select.xslt.file.unload"/>
                                </html:link>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <b><bean:message bundle="${bundle}" key="select.xsd.file.load"/></b>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <html:file property="xsdLoadable" size="40"/>
                            </td>
                            <td>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey" value="button.xsdLoad"/>
                                    <tiles:put name="commandHelpKey" value="button.xsltLoad.help"/>
                                    <tiles:put name="bundle" value="pfrBundle"/>
                                </tiles:insert>
                            </td>
                            <td>
                                <html:link action="/pfr/pfrTemplate.do?type=xsd">
                                    <bean:message bundle="${bundle}" key="select.xsd.file.unload"/>
                                </html:link>
                            </td>
                        </tr>
                    </table>
			    </tiles:put>
			    <tiles:put name="alignTable" value="center"/>
		    </tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
