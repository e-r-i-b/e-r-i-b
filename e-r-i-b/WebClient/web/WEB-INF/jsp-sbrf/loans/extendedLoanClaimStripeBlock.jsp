<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<tiles:importAttribute/>

<c:set var="className" value="sevenstepStripe"/>
<c:if test="${hasQuestionnaire}">
    <c:set var="className" value="eightstepStripe"/>
</c:if>
<div id="paymentStripe" class="${className}">
    <tiles:insert definition="stripe" flush="false">
        <tiles:put name="name">
            Выбор кредита
        </tiles:put>
        <c:choose>
            <c:when test="${extendedLoanClaimState == 'INITIAL'}">
                <tiles:put name="current" value="true"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="future" value="false"/>
            </c:otherwise>
        </c:choose>
    </tiles:insert>
    <tiles:insert definition="stripe" flush="false">
        <tiles:put name="name">
            Личные данные
        </tiles:put>
        <c:choose>
            <c:when test="${extendedLoanClaimState == 'INITIAL2'}">
                <tiles:put name="current" value="true"/>
            </c:when>
            <c:when test="${extendedLoanClaimState == 'INITIAL3'
                || extendedLoanClaimState == 'INITIAL4'
                || extendedLoanClaimState == 'INITIAL5'
                || extendedLoanClaimState == 'INITIAL6'
                || extendedLoanClaimState == 'INITIAL7'
                || extendedLoanClaimState == 'INITIAL8'}">
                <tiles:put name="future" value="false"/>
            </c:when>
        </c:choose>
    </tiles:insert>
    <tiles:insert definition="stripe" flush="false">
        <tiles:put name="name">
            Семья
        </tiles:put>
        <c:choose>
            <c:when test="${extendedLoanClaimState == 'INITIAL3'}">
                <tiles:put name="current" value="true"/>
            </c:when>
            <c:when test="${extendedLoanClaimState == 'INITIAL4'
                || extendedLoanClaimState == 'INITIAL5'
                || extendedLoanClaimState == 'INITIAL6'
                || extendedLoanClaimState == 'INITIAL7'
                || extendedLoanClaimState == 'INITIAL8'}">
                <tiles:put name="future" value="false"/>
            </c:when>
        </c:choose>
    </tiles:insert>
    <tiles:insert definition="stripe" flush="false">
        <tiles:put name="name">
            Прописка
        </tiles:put>
        <c:choose>
            <c:when test="${extendedLoanClaimState == 'INITIAL4'}">
                <tiles:put name="current" value="true"/>
            </c:when>
            <c:when test="${extendedLoanClaimState == 'INITIAL5'
                || extendedLoanClaimState == 'INITIAL6'
                || extendedLoanClaimState == 'INITIAL7'
                || extendedLoanClaimState == 'INITIAL8'}">
                <tiles:put name="future" value="false"/>
            </c:when>
        </c:choose>
    </tiles:insert>
    <tiles:insert definition="stripe" flush="false">
        <tiles:put name="name">
            Работа и доход
        </tiles:put>
        <c:choose>
            <c:when test="${extendedLoanClaimState == 'INITIAL5'}">
                <tiles:put name="current" value="true"/>
            </c:when>
            <c:when test="${extendedLoanClaimState == 'INITIAL6'
                || extendedLoanClaimState == 'INITIAL7'
                || extendedLoanClaimState == 'INITIAL8'}">
                <tiles:put name="future" value="false"/>
            </c:when>
        </c:choose>
    </tiles:insert>
    <tiles:insert definition="stripe" flush="false">
        <tiles:put name="name">
            Собственность
        </tiles:put>
        <c:choose>
            <c:when test="${extendedLoanClaimState == 'INITIAL6'}">
                <tiles:put name="current" value="true"/>
            </c:when>
            <c:when test="${extendedLoanClaimState == 'INITIAL7'
                || extendedLoanClaimState == 'INITIAL8'}">
                <tiles:put name="future" value="false"/>
            </c:when>
        </c:choose>
    </tiles:insert>
    <tiles:insert definition="stripe" flush="false">
        <tiles:put name="name">
            Способ получения кредита
        </tiles:put>
        <c:choose>
            <c:when test="${extendedLoanClaimState == 'INITIAL7'}">
                <tiles:put name="current" value="true"/>
            </c:when>
            <c:when test="${extendedLoanClaimState == 'INITIAL8'}">
                <tiles:put name="future" value="false"/>
            </c:when>
        </c:choose>
    </tiles:insert>
    <c:if test="${hasQuestionnaire}">
        <tiles:insert definition="stripe" flush="false">
            <tiles:put name="name">
                Анкета
            </tiles:put>
            <c:choose>
                <c:when test="${extendedLoanClaimState == 'INITIAL8'}">
                    <tiles:put name="current" value="true"/>
                </c:when>
            </c:choose>
        </tiles:insert>
    </c:if>
    <div class="clear"></div>
</div>