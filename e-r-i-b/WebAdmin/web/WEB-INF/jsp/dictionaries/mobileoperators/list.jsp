<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/dictionaries/mobileoperators/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="dictionariesMain">
        <tiles:put name="submenu" type="string" value="MobileOperators"/>
        <tiles:put name="pageTitle" type="string" value="Справочник мобильных операторов"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="MobileOperatorListTable"/>
                <tiles:put name="grid">
                    <sl:collection id="mobileOperator" property="data" model="list">
                       <sl:collectionItem title="Код оператора" property="code"/>
                       <sl:collectionItem title="Наименование оператора" property="name"/>
                    </sl:collection>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>