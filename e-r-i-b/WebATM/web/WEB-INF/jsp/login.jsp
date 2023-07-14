<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<tiles:insert definition="atm" flush="false">
    <tiles:put name="data"> 
        <c:set var="person" value="${phiz:getPersonInfo()}"/>
        <loginCompleted>${not empty person}</loginCompleted>
        <tiles:insert definition="personType" flush="false">
            <tiles:put name="name" value="person" />
            <tiles:put name="person" beanName="person"/>
        </tiles:insert>
        <c:if test="${not empty person}">
            <c:set var="cardLink" value="${phiz:getLastLogonCard(person)}"/>
            <c:if test="${not empty cardLink}">
                <c:set var="card" value="${cardLink.card}"/>
                <card>
                    <id>${cardLink.id}</id>
                    <name><c:out value="${phiz:getCardUserName(cardLink)}"/></name>
                    <c:if test="${not empty cardLink.description}">
                        <description>${cardLink.description}</description>
                    </c:if>
                    <c:if test="${not empty cardLink.number}">
                        <number>${phiz:getCutCardNumber(cardLink.number)}</number>
                    </c:if>
                    <isMain>${cardLink.main}</isMain>
                    <type>${card.cardType}</type>
                    <c:if test="${not empty card.availableLimit}">
                        <tiles:insert definition="atmMoneyType" flush="false">
                            <tiles:put name="name" value="availableLimit"/>
                            <tiles:put name="money" beanName="card" beanProperty="availableLimit"/>
                        </tiles:insert>
                    </c:if>
                    <state>${card.cardState}</state>
                </card>
            </c:if>
        </c:if>

        <c:set var="region" value="${phiz:findRegionByAtmRegionCode()}"/>
        <c:choose>
            <c:when test="${not empty region}">
                <atmRegion>
                   <id><c:out value="${region.id}"/></id>
                   <name><c:out value="${region.name}"/></name>
                   <c:if test="${region.id != 0}">
                       <%--TODO (CHG082279: [ATMAPI] Вернуть ОКАТО при входе клиента) вернуть на межблочный ИД после доработок СИРИУСа--%>
                       <guid><c:out value="${region.synchKey}"/></guid>
                       <%--<c:if test="${not empty region.multiBlockRecordId and region.id != 0}">--%>
                           <%--<guid><c:out value="${region.multiBlockRecordId}"/></guid>--%>
                       <%--</c:if>--%>
                   </c:if>
                </atmRegion>
            </c:when>
        </c:choose>
        <checkedUDBO>${phiz:isCheckedUDBO()}</checkedUDBO>
    </tiles:put>

    <tiles:put name="messagesBundle" value="securityBundle"/>
</tiles:insert>