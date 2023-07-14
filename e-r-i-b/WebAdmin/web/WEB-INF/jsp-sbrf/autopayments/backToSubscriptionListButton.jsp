<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%-- Кнопка для возврата к списку авоподписок(копилок/автоплатежей/автопереводов). --%>

<tiles:importAttribute/>
<tiles:insert definition="clientButton" service="${serviceName}" flush="false">
    <tiles:put name="commandTextKey"    value="button.backToList.${subMenu}"/>
    <tiles:put name="commandHelpKey"    value="button.backToList.${subMenu}.help"/>
    <tiles:put name="bundle"            value="autopaymentsBundle"/>
    <tiles:put name="action"            value="${linkButtonBackToList}"/>
    <tiles:put name="viewType" value="blueBorder"/>
</tiles:insert>