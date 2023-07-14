<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<html:form action="/private/security/list" onsubmit="return setEmptyAction(event)">

    <tiles:insert definition="securityAccountInfo">
        <c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>
        <c:set var="productUrl" value="${globalUrl}/commonSkin/images/products"/>

        <tiles:put name="enabledLink" value="false"/>
        <tiles:put name="menu" type="string"/>
        <tiles:put name="data" type="string">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:if test="${!form.backError}">
                <c:choose>
                    <c:when test="${not empty form.securityLinks  && not empty form.securityAccounts}">
                        <div class="messageBlock">
                            <tiles:insert definition="mainWorkspace" flush="false">
                                <tiles:put name="title" value="Сберегательные сертификаты"/>
                                <tiles:put name="data" type="string">
                                    <tiles:insert definition="roundBorderLight" flush="false">
                                        <tiles:put name="color" value="orange"/>
                                        <tiles:put name="data">
                                            <p class="orangeTitle">Обратите внимание!</p>
                                            <p>В списке указаны все выданные Вам не погашенные сертификаты. При передаче сертификата третьему лицу, он продолжает отображаться на вашей личной странице до его погашения новым владельцем. </p>
                                        </tiles:put>
                                    </tiles:insert>
                                    <c:set var="elementsCount" value="${fn:length(form.securityLinks)-1}"/>
                                    <logic:iterate id="listElement" name="form" property="securityLinks" indexId="i">
                                        <c:set var="securityLink" value="${listElement}" scope="request"/>
                                        <c:set var="securityAccount" value="${form.securityAccounts[listElement]}"/>
                                        <c:set var="securityInfoLink" value="true"/>
                                        <%@include file="securityAccountTemplate.jsp" %>
                                        <c:if test="${elementsCount != i}">
                                            <div class="productDivider"></div>
                                        </c:if>
                                    </logic:iterate>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:when>
                    <c:when test="${!form.useStoredResource}">
                        <tiles:insert definition="mainWorkspace" flush="false">
                            <tiles:put name="title" value="Сберегательные сертификаты"/>
                            <tiles:put name="data" type="string">
                                <div class="securityAccountTemplate">
                                <tiles:insert definition="formHeader" flush="false">
                                    <tiles:put name="image" value="${globalImagePath}/icon_pmnt_tax.jpg"/>
                                    <tiles:put name="description">
                                        <span class="emptyMessageHeader">
                                            Сберегательный сертификат Сбербанка России является ценной бумагой, удостоверяющей сумму вклада, внесенного в Банк,
                                            и права вкладчика (держателя сертификата) на получение по истечении установленного срока суммы вклада и обусловленных
                                            в сертификате процентов в любом филиале Сбербанка России.
                                        </span>
                                    </tiles:put>
                                </tiles:insert>
                                 <span class="emptyMessage">
                                     <p>Ставки по сберегательным сертификатам выше, чем по вкладам.</p>
                                    <p>Сберегательный сертификат оформляется более чем в 10 тысячах внутренних структурных подразделений банка. Сберегательный
                                     сертификат оформляется на предъявителя и может быть предъявлен к оплате в любой момент. Степень защиты сертификата не
                                     уступает денежным знакам.</p>
                                    <p>Рыночная ситуация постоянно меняется, и возможно, текущий момент - самый выгодный для приобретения Сберегательного
                                     сертификата на длительный срок с высокой ставкой.</p>
                                    <p>Пока у Вас нет ни одного сберегательного сертификата.</p>
                                    <p>Для получения более подробной информации пройдите по следующей ссылке: <a target="_blank" href="http://sberbank.ru/ru/person/investments/securities/certificates/"> сберегательные сертификаты.</a></p>
                                 </span>
                                </div>
                            </tiles:put>
                        </tiles:insert>
                    </c:when>
                </c:choose>
            </c:if>
        </tiles:put>
   </tiles:insert>
</html:form>
