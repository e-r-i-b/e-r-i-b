<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/log/detail/messageDetail">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="entry" value="${form.messageLog}"/>

    <tiles:insert definition="dictionary">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="label.detail.message" bundle="logBundle"/>
        </tiles:put>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close"/>
                <tiles:put name="commandHelpKey" value="button.close"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="onclick" value="window.close();"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="head">
                    <td class="listItem" align="center" valign="top"><bean:message key="label.record.num" bundle="logBundle"/></td>
                    <td  class="listItem"><pre><bean:write name="entry" property="id"/></pre></td>
                </tiles:put>
                <tiles:put name="data">
                    <tr class="listLine1">
                        <td class="listItem" align="center" valign="top"><bean:message key="label.datetime" bundle="logBundle"/></td>
                        <td  class="listItem"><pre><bean:write name="entry" property="date.time" format="dd.MM.yyyy HH:mm:ss"/></pre></td>
                    </tr>
                    <tr class="listLine0">
                        <td class="listItem" align="center" valign="top"><bean:message key="label.request" bundle="logBundle"/></td>
                        <td  class="listItem"><pre><bean:write name="form" property="messageRequest"/></pre></td>
                    </tr>
                    <tr class="listLine1">
                    <td class="listItem" align="center" valign="top"><bean:message key="label.responce" bundle="logBundle"/></td>
                    <td class="listItem">
                        <c:choose>
                            <c:when test="${empty form.messageResponse}">
                                <bean:message key="label.detail.message.response.empty" bundle="logBundle"/>
                            </c:when>
                            <c:otherwise>
                                <pre><bean:write name="form" property="messageResponse"/></pre>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>