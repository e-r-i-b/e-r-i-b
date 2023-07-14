<%--
  Created by IntelliJ IDEA.
  User: lepihina
  Date: 16.12.2011
  Time: 15:07:23
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/otpRestrictions" onsubmit="return setEmptyAction(event);">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="personEdit">
    <tiles:put name="submenu" type="string" value="OTPRestrictionsList"/>
    <tiles:put name="pageTitle" type="string">
        <bean:message key="cards.restrictions.title" bundle="personsBundle"/>
    </tiles:put>
    <tiles:put name="menu" type="string">
        <tiles:insert definition="clientButton" flush="false" operation="GetPersonsListOperation">
            <tiles:put name="commandTextKey" value="button.close"/>
            <tiles:put name="commandHelpKey" value="button.close"/>
            <tiles:put name="bundle" value="commonBundle"/>
            <tiles:put name="image"  value=""/>
            <tiles:put name="action" value="/persons/list.do"/>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
        <%--Карты--%>
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="tableCards"/>
            <tiles:put name="data">
                <tr class="tblInfHeader">
                    <th class="titleTable">Номер карты</th>
                    <th class="titleTable">Печать паролей</th>
                    <th class="titleTable">Статус распечатанных паролей</th>
                </tr>
                <c:set var="counter" value="0"/>
                <c:forEach var="listElement" items="${form.data}">
                    <c:set var="card" value="${listElement}"/>
                    <tr class="ListLine${counter}">
                       <td class="listItem">
                            ${phiz:getCutCardNumber(card.number)}
                       </td>
                       <td class="listItem">
                            <c:set var="mess" value="label.OTPGet"/>
                            <c:if test="${card.OTPGet == false}">
                                <c:set var="mess" value="label.notOTPGet"/>
                            </c:if>
                            <bean:message bundle="personsBundle" key="${mess}"/>
                       </td>
                       <td class="listItem">
                            <c:if test="${card.OTPGet == false}">
                                <c:set var="mess" value="label.OTPUse"/>
                                <c:if test="${card.OTPUse == false}">
                                    <c:set var="mess" value="label.notOTPUse"/>
                                </c:if>
                                <bean:message bundle="personsBundle" key="${mess}"/>
                            </c:if>
                            <c:if test="${card.OTPGet != false}">
                                -
                            </c:if>
                       </td>
                    </tr>
                    <c:set var="counter" value="${(counter + 1)%2}"/>
                </c:forEach>
		    </tiles:put>
            <tiles:put name="isEmpty" value="${empty form.data}"/>
            <tiles:put name="emptyMessage" value="Данный клиент не задавал ограничений на печать одноразовых паролей"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>