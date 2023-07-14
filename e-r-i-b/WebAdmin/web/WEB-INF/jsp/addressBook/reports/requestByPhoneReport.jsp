<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags"                           prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"               prefix="fmt" %>

<html:form action="/addressBook/reports/requestByPhone">
    <c:set var="form"       value="${phiz:currentForm(pageContext)}"/>
    <c:set var="fromStart"  value="${form.fromStart}"/>
    <tiles:insert definition="addressBookReports">
         <tiles:put name="submenu" value="RequestByPhone"/>
         <tiles:put name="filter" type="string">
             <tiles:insert definition="filterDateField" flush="false">
                 <tiles:put name="label" value="label.form.requestByPhone.filter.fromDate"/>
                 <tiles:put name="bundle" value="addressBookReportsBundle"/>
                 <tiles:put name="mandatory" value="true"/>
                 <tiles:put name="name" value="fromDate"/>
             </tiles:insert>

             <tiles:insert definition="filterDateField" flush="false">
                 <tiles:put name="label" value="label.form.requestByPhone.filter.toDate"/>
                 <tiles:put name="bundle" value="addressBookReportsBundle"/>
                 <tiles:put name="mandatory" value="true"/>
                 <tiles:put name="name" value="toDate"/>
             </tiles:insert>
             <tiles:insert definition="filterTextField" flush="false">
                 <tiles:put name="label" value="label.form.requestByPhone.list.login"/>
                 <tiles:put name="bundle" value="addressBookReportsBundle"/>
                 <tiles:put name="mandatory" value="false"/>
                 <tiles:put name="name" value="loginId"/>
             </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <c:choose>
                <c:when test="${fromStart}">
                    <script type="text/javascript">
                        doOnLoad(switchFilter, this);
                    </script>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="tableTemplate" flush="false">
                        <tiles:put name="text"><bean:message bundle="addressBookReportsBundle" key="label.form.requestByPhone.list.title"/></tiles:put>
                        <tiles:put name="grid">
                            <sl:collection id="listElement" model="list" property="data" bundle="addressBookReportsBundle" styleClass="standartTable">
                                <sl:collectionItem title="label.form.requestByPhone.list.login">
                                    <phiz:link action="/persons/edit" serviceId="PersonManagement">
                                        <phiz:param name="person" value="${listElement[0]}"/>
                                        <c:out value="${listElement[0]}"/>
                                    </phiz:link>
                                </sl:collectionItem>
                                <sl:collectionItem title="label.form.requestByPhone.list.name">
                                    <phiz:link action="/persons/edit" serviceId="PersonManagement">
                                        <phiz:param name="person" value="${listElement[0]}"/>
                                        <c:out value="${listElement[1]}"/>
                                    </phiz:link>
                                </sl:collectionItem>
                                <sl:collectionItem title="label.form.requestByPhone.list.document" >
                                    <c:if test="${listElement[2] != null}"><bean:message key="document.type.${listElement[2]}" bundle="personsBundle" /></c:if>
                                    <c:out value="${listElement[3]}"/>
                                </sl:collectionItem>
                                <sl:collectionItem title="label.form.requestByPhone.list.birthday">
                                    <fmt:formatDate value="${listElement[4].time}" pattern="dd.MM.yyyy"/>
                                </sl:collectionItem>
                                    </sl:collection>
                            <tiles:put name="isEmpty" value="${empty form.data}"/>
                            <tiles:put name="emptyMessage">
                                <bean:message bundle="addressBookReportsBundle" key="label.form.requestByPhone.list.empty"/>
                            </tiles:put>
                        </tiles:put>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</html:form>
