<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/autopayment/create" >
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="requisite" value="${form.requisite}"/>

    <tiles:insert definition="paymentCurrent">
        <tiles:put name="mainmenu" value="Payments"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <c:set var="provider" value="${phiz:getServiceProvider(form.recipient)}"/>
                <c:if test="${not empty provider.imageId}">
                    <tiles:put name="imageId" value="${provider.imageId}"/>
                </c:if>
                <tiles:put name="title">Автоплатежи: ${form.providerName}</tiles:put>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="description">
                    Если вы часто совершаете одну и ту же операцию, вы можете создать автоплатеж. Для этого <br>
                    выберите получателя, укажите лицевой счет и нажмите на кнопку &laquo;Продолжить&raquo;.
                </tiles:put>
                <tiles:put name="stripe">
                    <div class="threestepStripe">
                        <tiles:insert definition="stripe" flush="false">
                            <tiles:put name="name" value="ввод автоплатежа"/>
                            <tiles:put name="current" value="true"/>
                        </tiles:insert>
                        <tiles:insert definition="stripe" flush="false">
                            <tiles:put name="name" value="подтверждение"/>
                        </tiles:insert>
                        <tiles:insert definition="stripe" flush="false">
                            <tiles:put name="name" value="статус оплаты"/>
                        </tiles:insert>
                    </div>
                </tiles:put>
                <tiles:put name="data">
                    <html:hidden property="recipient"/>
                    <div onkeypress="onEnterKey(event);">
                        <div id="paymentForm">
                            <div class="form-row">
                               <div class="paymentLabel">
                                    <span class="paymentTextLabel">Получатель:</span>
                               </div>
                               <div class="paymentValue " id="nameProvider">
                                   <b>${form.providerName}</b>
                               </div>
                               <div class="clear"></div>
                            </div>

                            <div class="form-row">
                               <div class="paymentLabel">
                                    <span class="paymentTextLabel">Услуга:</span>
                               </div>
                               <div class="paymentValue" id="nameService">
                                    <b>${form.serviceName}</b>
                               </div>
                               <div class="clear"></div>
                            </div>

                            <div class="form-row">
                               <div class="paymentLabel">
                                   <span class="paymentTextLabel">${requisite.name}</span>
                                   <span class="asterisk">*</span>:
                               </div>
                               <div class="paymentValue">
                                   <c:set var="externalId" value="${requisite.externalId}"/>
                                   <c:set var="value" value="${empty form.fields[externalId] ? requisite.defaultValue : form.fields[externalId]}"/>

                                   <c:choose>
                                       <c:when test="${requisite.type == 'date'}">
                                           <input type="text" name="field(${requisite.externalId})" size="${requisite.maxLength}" value="${value}" class="dot-date-pick">
                                       </c:when>
                                       <c:when test="${requisite.type == 'list'}">
                                           <select name="field(${externalId})" id="${externalId}">
                                               <c:forEach items="${requisite.listValues}" var="valueOption">
                                                   <option  value="${valueOption}" <c:if test="${value == valueOption}">selected</c:if> >${valueOption}</option>
                                               </c:forEach>
                                           </select>
                                       </c:when>
                                       <c:otherwise>
                                           <input type="text" name="field(${externalId})" size="${requisite.maxLength>50?50:requisite.maxLength}" value="${value}">
                                       </c:otherwise>
                                   </c:choose>
                                   <div class="description" style="display: none;">
                                       ${phiz:escapeForJS(requisite.description, false)}
                                       <c:if test="${not empty requisite.hint}">
                                           <a href="#" onclick="payInput.openDetailClick(this); return false;">Подробнее...</a>
                                           <div class="detail" style="display: none">${phiz:escapeForJS(requisite.hint, false)}</div>
                                       </c:if>
                                   </div>
                                   <div class="errorDiv" style="display: none;"></div>
                               </div>
                               <div class="clear"></div>
                            </div>

                            <div class="form-row">
                               <div class="paymentLabel">
                                   <span class="paymentTextLabel">Оплата с</span>
                                   <span class="asterisk">*</span>:
                               </div>
                               <div class="paymentValue">
                                    <html:select property="fromResource" styleId="fromResource">
                                        <c:forEach items="${form.chargeOffResources}" var="resource">
                                            <html:option value="${resource.code}">
                                            <c:choose>
                                                <c:when test="${resource['class'].name == 'com.rssl.phizic.business.resources.external.CardLink'}">
                                                    ${phiz:getCutCardNumber(resource.number)} [${resource.name}] ${resource.rest.decimal} ${resource.currency.code}
                                                </c:when>
                                                <c:otherwise>
                                                    ${resource.number} [${resource.name}] ${resource.rest.decimal} ${resource.currency.code}
                                                </c:otherwise>
                                            </c:choose>
                                            </html:option>
                                        </c:forEach>
                                    </html:select>
                                   <div class="errorDiv" style="display: none;"></div>
                               </div>
                               <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                </tiles:put>
                <tiles:put name="buttons">
                    <div class="buttonsArea">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.cancel"/>
                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="viewType" value="boldLink"/>
                            <tiles:put name="action" value="/private/accounts"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.next"/>
                            <tiles:put name="commandTextKey" value="button.continue"/>
                            <tiles:put name="commandHelpKey" value="button.continue"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                        </tiles:insert>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>    