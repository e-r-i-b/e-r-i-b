<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<%--
parameter - параметр для которого настраивается архивация
label - заголовок, показывающий, какой параметр настраиваем
--%>

<tr>
    <td colspan="2">
        <span class="bold">${label}:</span>
    </td>
</tr>
<tr>
    <td class="rightAlign alignTop">
        <bean:message bundle="configureBundle" key="settings.mail.archiving.periodicity.label"/>
    </td>
    <td>
        <tiles:insert definition="propertyInput" flush="false">
            <tiles:put name="fieldName" value="${parameter}.period"/>
            <tiles:put name="textSize" value="3"/>
            <tiles:put name="textMaxLength" value="3"/>
        </tiles:insert>
        <tiles:insert definition="propertyInput" flush="false">
            <tiles:put name="fieldName" value="${parameter}.period.type"/>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems">DAY@<bean:message bundle="mailBundle" key="archive.period.type.day"/>|WEEK@<bean:message bundle="mailBundle" key="archive.period.type.week"/>|MONTH@<bean:message bundle="mailBundle" key="archive.period.type.month"/></tiles:put>
        </tiles:insert>
        &nbsp;в&nbsp;
        <tiles:insert definition="propertyInput" flush="false">
            <tiles:put name="fieldName" value="${parameter}.archTime"/>
            <tiles:put name="textSize" value="5"/>
            <tiles:put name="textMaxLength" value="5"/>
        </tiles:insert>
    </td>
</tr>

<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="${parameter}.lastMonth"/>
    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.mail.archiving.leave.for.period.label"/></tiles:put>
    <tiles:put name="textSize" value="3"/>
    <tiles:put name="textMaxLength" value="3"/>
    <tiles:put name="inputDesc"><bean:message bundle="configureBundle" key="settings.mail.archiving.leave.for.period.month.label"/></tiles:put>
    <tiles:put name="showHint">none</tiles:put>
    <tiles:put name="tdStyle" value="rightAlign"/>
</tiles:insert>