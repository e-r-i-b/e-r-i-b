<%--
  Created by IntelliJ IDEA.
  User: Mescheryakova
  Date: 15.12.2010
  Time: 15:59:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<html:form action="/external/fns/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="fnsMain">
        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Выбор задолженности"/>
                <tiles:put name="data">
                       <script type="text/javascript">
                           var id = null;

                           function setId(id)
                           {
                               this.id = id;
                           }

                           function getId()
                           {
                               return this.id;
                           }

                       </script>

                            <%--Окно с предупреждением --%>
                            <tiles:insert definition="window" flush="false">
                                <tiles:put name="id" value="showWarning"/>
                                
                                <tiles:put name="data">
                                    <br><br>
                                    <div class="showMessage">
                                    <bean:message bundle="fnsBundle" key="text.repay.notification" />
                                    </div>
                                    <br><br>
                                    <div style="margin-left:40%;">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.cancel"/>
                                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                            <tiles:put name="viewType" value="buttonGrey"/>
                                            <tiles:put name="bundle" value="fnsBundle"/>
                                            <tiles:put name="onclick">win.close('showWarning');</tiles:put>
                                        </tiles:insert>

                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.pay"/>
                                            <tiles:put name="commandHelpKey" value="button.pay.help"/>
                                            <tiles:put name="bundle" value="fnsBundle"/>
                                            <tiles:put name="onclick">goTo('/${phiz:loginContextName()}/external/payments/servicesPayments/edit.do?orderId=' + getId());</tiles:put>
                                        </tiles:insert>
                                    </div>
                                </tiles:put>

                            </tiles:insert>


                       <h1 class="grayStyleText italic">Выберите задолженность, которую необходимо погасить в первую очередь</h1>

                       <br/>
                        <tiles:insert definition="simpleTableTemplate" flush="false">
                            <tiles:put name="grid">
                                <sl:collection id="listElement" model="no-pagination" property="data">
                                    <c:set var="fnsInfo" value="${listElement[1]}"/>
                                    <c:set var="payment_id" value="${listElement[0]}"/>
                                    <c:set var="state" value="${form.linkTaxIndexState[fnsInfo.indexTaxationDocument]}"/>
                                    <c:set var="amount" value=""/>
                                    <c:choose>
                                        <c:when test="${state eq 'EXECUTED'}">
                                            <c:set var="styleClass" value="green_text"/>
                                            <c:set var="link" value=""/>
                                            <c:set var="amount" value="green_text"/>
                                        </c:when>
                                        <c:when test="${state eq 'DISPATCHED'}">
                                            <c:set var="styleClass" value="gray_link"/>
                                            <c:set var="orderId" value="${fnsInfo.order.id}"/>
                                            <c:set var="orderUuid"  value="${fnsInfo.order.uuid}"/>
                                            <c:set var="link" value="setId('${payment_id}');win.open('showWarning');"/>
                                            <c:set var="amount" value="gray_text"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="styleClass" value="black_link"/>
                                            <c:set var="orderId"  value="${fnsInfo.order.id}"/>
                                            <c:set var="orderUuid"  value="${fnsInfo.order.uuid}"/>
                                            <c:url var="link" value="/external/payments/servicesPayments/edit.do">
                                                <c:choose>
                                                    <c:when test="${not empty payment_id}">
                                                        <c:param name="id" value="${payment_id}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:param name="orderId" value="${orderUuid}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:url>
                                            <c:set var="link" value="goTo('${link}');"/>
                                        </c:otherwise>
                                    </c:choose>

                                    <sl:collectionItem title="Индекс налогового документа" styleClass="${styleClass}">
                                        ${phiz:getLink(link,fnsInfo.indexTaxationDocument )}
                                    </sl:collectionItem>
                                    <sl:collectionItem title="Налог" styleClass="${styleClass}">
                                        <c:set var="shortKBK" value=""/>
                                        <c:if test="${!empty fnsInfo.KBK}">
                                            <c:set var="shortKBK" value="${phiz:getKBK(fnsInfo.KBK)}"/>
                                        </c:if>
                                        <c:if test="${!empty shortKBK}">
                                            ${phiz:getLink(link,shortKBK.shortName )}
                                        </c:if>
                                        &nbsp;
                                    </sl:collectionItem>
                                    <sl:collectionItem title="Тип платежа" styleClass="${styleClass}">
                                        <c:choose>
                                            <c:when test="${fnsInfo.paymentType eq 'НС'}">${phiz:getLink(link,'Налог/сбор' )}</c:when>
                                            <c:when test="${fnsInfo.paymentType eq 'ПЛ'}">${phiz:getLink(link,'Платеж' )}</c:when>
                                            <c:when test="${fnsInfo.paymentType eq 'ГП'}">${phiz:getLink(link,'Пошлина' )}</c:when>
                                            <c:when test="${fnsInfo.paymentType eq 'ВЗ'}">${phiz:getLink(link,'Взнос' )}</c:when>
                                            <c:when test="${fnsInfo.paymentType eq 'АВ'}">${phiz:getLink(link,'Аванс/предоплата' )}</c:when>
                                            <c:when test="${fnsInfo.paymentType eq 'ПЕ'}">${phiz:getLink(link,'Пени' )}</c:when>
                                            <c:when test="${fnsInfo.paymentType eq 'ПЦ'}">${phiz:getLink(link,'Проценты' )}</c:when>
                                            <c:when test="${fnsInfo.paymentType eq 'СА'}">${phiz:getLink(link,'Налоговые санкции' )}</c:when>
                                            <c:when test="${fnsInfo.paymentType eq 'АШ'}">${phiz:getLink(link,'Административные штрафы' )}</c:when>
                                            <c:otherwise>${phiz:getLink(link, 'Иные штрафы')}</c:otherwise>
                                        </c:choose>
                                    </sl:collectionItem>
                                    <sl:collectionItem title="Сумма" styleClass="${amount}">
                                        <nobr>
                                            <fmt:formatNumber value="${fnsInfo.order.amount.decimal}" pattern="0.00" />
                                            ${phiz:getCurrencyName(fnsInfo.order.amount.currency)}
                                            &nbsp;
                                            <c:choose>
                                                <c:when test="${state eq 'EXECUTED'}">
                                                    <c:out value="(Оплачено)"/>
                                                </c:when>
                                                <c:when test="${state eq 'DISPATCHED'}">
                                                    <c:out value="(В работе)"/>
                                                </c:when>
                                            </c:choose>
                                        </nobr>
                                    </sl:collectionItem>

                                    <sl:collectionItem title="">
                                        <c:choose>
                                            <c:when test="${state ne 'EXECUTED' and state ne 'DISPATCHED'}">
                                                <a href="#"  onclick="${link}" class="payments-history">Оплатить</a>                                            
                                            </c:when>
                                            <c:otherwise>
                                                &nbsp;
                                            </c:otherwise>
                                        </c:choose>
                                    </sl:collectionItem>
                                    
                                </sl:collection>
                            </tiles:put>
                            <tiles:put name="isEmpty" value="${empty form.data}"/>
                            <tiles:put name="emptyMessage" value="Нет переданных неоплаченных задолженностей"/>
                        </tiles:insert>

                        <div class="buttonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel.link"/>
                                <tiles:put name="commandHelpKey" value="button.cancel.link.help"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="bundle" value="fnsBundle"/>
                                <tiles:put name="onclick">goTo('/${phiz:loginContextName()}/external/payments/system/end.do');</tiles:put>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>
                </tiles:put>

            </tiles:insert>
        </tiles:put>
    </tiles:insert>   
</html:form>