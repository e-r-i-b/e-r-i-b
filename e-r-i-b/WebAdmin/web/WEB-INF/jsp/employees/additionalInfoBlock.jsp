<%--
  Created by IntelliJ IDEA.
  User: Zhuravleva
  Date: 13.04.2009
  Time: 11:43:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:if test="${(not empty form)&&(not empty form.employee)}">

<c:set var="employee" scope="request" value="${form.employee}"/>
<c:set var="blocked"  scope="request" value="${!empty employee.login.blocks}"/>

<tiles:insert definition="infoBlock" flush="false">
	<tiles:put name="image" value="anonym.jpg"/>
	<tiles:put name="data">
        <div class="size18 personName"><c:out value="${employee.surName} ${employee.firstName} ${employee.patrName}"/></div>
        <div class="infoBlockStatus">
            <c:choose>
                <c:when test="${blocked}">
                    <c:set var="hasLongInactivityBlock" value="${false}"/>
                    <c:forEach var="blockReason" items="${employee.login.blocks}">
                        <c:if test="${blockReason.reasonType eq 'longInactivity'}">
                            <c:set var="hasLongInactivityBlock" value="${true}"/>
                        </c:if>
                    </c:forEach>

                    <nobr>
                        <span class="menuInsertText backTransparent">Заблокирован</span>
                        <c:if test="${hasLongInactivityBlock}">
                            <br><span class="menuInsertText backTransparent"><bean:message key="label.long.inactivity.block" bundle="personsBundle"/></span>
                        </c:if>
                    </nobr>
                </c:when>
                <c:otherwise>
                    <span class="menuInsertText backTransparent">Активный</span>
                </c:otherwise>
            </c:choose>
        </div>
	</tiles:put>
</tiles:insert>
</c:if>