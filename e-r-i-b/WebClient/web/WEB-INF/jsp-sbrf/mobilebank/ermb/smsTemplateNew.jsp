<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<tiles:importAttribute/>

<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>
<c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm16')}"/>

<div id="popularPayments" class="newProfileContentWidth">
    <tiles:insert definition="mainWorkspace" flush="false">
        <tiles:put name="title" value="Мобильный банк"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="formHeader" flush="false">
               <tiles:put name="image" value="${image}/ico_mobileBank.jpg"/>
               <tiles:put name="description">
                   <h3>На этой странице можно изменить настройки подключения к Мобильному банку, просмотреть SMS-запросы и шаблоны, а также историю отправленных в банк запросов. Для этого перейдите на соответствующую вкладку. </h3>
               </tiles:put>
           </tiles:insert>
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="selectedTab" value="templates"/>
            <%@ include file="/WEB-INF/jsp-sbrf/mobilebank/ermb/mobileBankHeader.jsp" %>
            <div id="mobilebank">
                <div>
                    <h2 class="inline">Шаблоны платежей</h2>
                    <html:link href="" onclick="openFAQ('${faqLink}'); return false;" styleClass="text-green">
                        почему здесь не все мои шаблоны?
                    </html:link>
                </div>
            </div>
            <c:if test="${not empty form.templates}">
                <div id="inSystem-items">
                    <div class="interface-items">
                        <div class="user-products simpleTable">
                            <fieldset>
                                <table width="100%">
                                    <tr class="tblInfHeader">
                                        <th class="align-left titleTable">
                                            <bean:message bundle="ermbBundle" key="title.create.date"/>
                                        </th>
                                        <th class="align-left titleTable">
                                            <bean:message bundle="ermbBundle" key="title.name"/>
                                        </th>
                                        <th class="align-left titleTable">
                                            <bean:message bundle="ermbBundle" key="title.sum"/>
                                        </th>
                                        <th class="align-left titleTable">
                                            <bean:message bundle="ermbBundle" key="title.sms.text"/>
                                            <img src="${imagePath}/sms.png" alt="" border="0"/>
                                        </th>
                                    </tr>

                                    <logic:iterate id="temp" name="form" property="templates" indexId="i">
                                        <c:set var="templateUrl" value="${phiz:calculateActionURL(pageContext,'/private/templates/default-action.do?')}"/>
                                        <c:set var="stateCode" value="${temp.state.code}" />

                                        <c:if test="${i<5}">
                                            <c:if test="${not(state eq 'closed' or state eq 'blocked')}">
                                                <tr>
                                                    <td class="align-left inSystem">
                                                        <div class="products-text-style">
                                                            ${phiz:сalendarToString(temp.clientCreationDate)}
                                                        </div>
                                                    </td>
                                                    <td class="align-left greenRepeatLink" style="width: 49%;">
                                                        <div class="products-text-style">
                                                            <html:link href="${templateUrl}&id=${temp.id}&objectFormName=${temp.formType.name}&stateCode=${stateCode}" style="color: #449D2F;">
                                                                <span class="word-wrap">${temp.templateInfo.name}</span>
                                                            </html:link>
                                                        </div>
                                                    </td>
                                                    <td class="align-left" style="width: 13%;">
                                                        <div class="products-text-style">
                                                            <c:if test="${temp.inputSumType == 'DESTINATION'}">
                                                                ${phiz:formatAmount(document.destinationAmount)}
                                                            </c:if>
                                                             <c:if test="${temp.inputSumType == 'CHARGEOFF'}">
                                                                 ${phiz:formatAmount(temp.chargeOffAmount)}
                                                            </c:if>
                                                        </div>
                                                    </td>
                                                    <td class="align-left" style="width: 27%;">
                                                        <div class="products-text-style">
                                                            <b>SMS:</b> <span class="word-wrap">${temp.templateInfo.name}</span> <c:out value="<сумма>"/>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:if>
                                    </logic:iterate>
                                </table>
                            </fieldset>
                        </div>
                    </div>
                    </div>
                </c:if>


                <span onclick="showOptions();">
                     <div class="hide-show-switch hide-switch" id="hide-show-switch">
                         <a class="blueGrayLinkDotted">Информационные SMS</a>
                     </div>
                    <div class="clear"></div>
                </span>

                <script type="text/javascript">
                    function showOptions()
                    {
                        if (document.getElementById("showInformationalSms").className == 'invisible'){
                            document.getElementById("showInformationalSms").className = '';
                            document.getElementById("hide-show-switch").className = 'hide-show-switch hide-switch';}
                        else {
                            document.getElementById("hide-show-switch").className = 'hide-show-switch';
                            document.getElementById("showInformationalSms").className = 'invisible';

                        }
                    }
                </script>
                <div class="hide-switch">
                    <div id="showInformationalSms">
                        <%@ include file="informationalSms.jsp"%>
                    </div>
                </div>
        </tiles:put>
    </tiles:insert>
</div>
