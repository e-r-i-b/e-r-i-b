<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/configure/gate/services/run" onsubmit="return setEmptyAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="runGateServiceConfig">
        <tiles:put name="submenu" type="string" value="RunGateServiceRestriction"/>
        <tiles:put name="pageTitle">
            <bean:message bundle="gateServiceConfigBundle" key="label.run.title"/>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id"  value=""/>
                <tiles:put name="name">
                    <bean:message bundle="gateServiceConfigBundle" key="label.run.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="gateServiceConfigBundle" key="label.run.description"/>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="data">
                    <tr>
                        <td class="Width150 LabelAll" nowrap="true">
                            <bean:message bundle="gateServiceConfigBundle" key="label.run.field.url"/>
                        </td>
                        <td>
                            <input type="text" name="gateUrls" size="75"/>
                        </td>
                        <td nowrap="true">
                            <bean:message bundle="gateServiceConfigBundle" key="label.run.field.url.description"/>
                        </td>
                    </tr>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListMonitoringGateConfigOperation">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                        <tiles:put name="bundle"            value="gateServiceConfigBundle"/>
                        <tiles:put name="action"            value="/configure/gate/services/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="RunGateMonitoringOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandTextKey"     value="button.run.gate"/>
                        <tiles:put name="commandHelpKey"     value="button.run.gate.help"/>
                        <tiles:put name="bundle"             value="gateServiceConfigBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>