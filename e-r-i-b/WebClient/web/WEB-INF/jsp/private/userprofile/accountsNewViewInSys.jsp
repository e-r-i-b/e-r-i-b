<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<div id="inSystem-items">
   <div class="interface-items" >

        <tiles:insert definition="accountsNewView" flush="false">
           <tiles:put name="selectedCardIds" value="selectedCardIds"/>
           <tiles:put name="selectedAccountIds" value="selectedAccountIds"/>
           <tiles:put name="selectedLoanIds" value="selectedLoanIds"/>
           <tiles:put name="selectedIMAccountIds" value="selectedIMAccountIds"/>
           <tiles:put name="label_show" value="label.show_system"/>
           <tiles:put name="label_no_show" value="label.not_show_system"/>
           <tiles:put name="confirmRequest" value="${confirmRequest}"/>
           <tiles:put name="productPredicat" value=""/>
           <tiles:put name="functionShowDesc" value="showDescSys"/>
        </tiles:insert>

       <c:set var="personInf" value="${phiz:getPersonInfo()}"/>
       <c:choose>
           <c:when test="${!personInf.isRegisteredInDepo && personInf.creationType == 'UDBO'}">
               <div class="profile-user-products empty-profile-user-products">
                   <h4><bean:message bundle="userprofileBundle" key="title.depoAccounts"/></h4>
                   <div class="profile-empty-text-depo"> У Вас не подключена услуга &laquo;Депозитарий&raquo;. Чтобы получить доступ к депозитарию через систему &laquo;Сбербанк Онлайн&raquo;,
                       перейдите на страницу <html:link action="/private/depo/list.do">Счета депо</html:link>.
                   </div>

               </div>
           </c:when>
           <c:otherwise>
               <c:if test="${not empty form.depoAccounts}">
                   <div class="profile-user-products viewProductsWidth simpleTable">
                       <fieldset>
                           <table width="100%">
                               <tr class="tblInfHeader">
                                   <th class="titleTable checkboxColumn">отображать</th>
                                   <th class="titleTable"><bean:message bundle="userprofileBundle" key="title.depoAccounts"/></th>
                                   <th class="titleTable align-right">сумма</th>
                               </tr>
                               <logic:iterate id="listItem" name="form" property="depoAccounts">
                                   <c:if test="${not(listItem.value.state eq 'closed')}">
                                       <tr>
                                           <td class="align-center leftPaddingCell">
                                               <div class="products-text-style">
                                                   <c:set var="spanName" value="selectedDepoAccountIds${listItem.id}"/>
                                                   <c:if test="${not empty confirmRequest}">
                                                       <html:multibox name="form" property="selectedDepoAccountIds" styleId="${spanName}" onclick="showDescSys('${spanName}');" value="${listItem.id}" style="display:none;"/>
                                                   </c:if>
                                                   <c:set var="mess" value="label.show_system"/>
                                                   <c:if test="${listItem.showInSystem == false}">
                                                       <label for="${spanName}"><c:set var="mess" value="label.not_show_system"/></label>
                                                   </c:if>
                                                   <html:multibox name="form" property="selectedDepoAccountIds" styleId="${spanName}" onclick="showDescSys('${spanName}');" value="${listItem.id}" disabled="${not empty confirmRequest}"/>
                                                   <span class="${spanName}"><label for="${spanName}"></label></span>
                                               </div>
                                           </td>
                                           <td class="align-left productText inSystem">
                                               <div class="products-text-style">
                                                   <span class="productProfileWidth">
                                                   <c:choose>
                                                       <c:when test="${empty listItem.name}">
                                                           <bean:write name="listItem" property="accountNumber"/>
                                                       </c:when>
                                                       <c:otherwise>
                                                          <bean:write name="listItem" property="name"/>(<bean:write name="listItem" property="accountNumber"/>)
                                                       </c:otherwise>
                                                   </c:choose>&nbsp;
                                                   </span>
                                                   <span class="card-number">${listItem.depoAccount.agreementNumber}</span>&nbsp;
                                               </div>
                                           </td>
                                           <td>&nbsp;</td>
                                       </tr>

                                   </c:if>
                               </logic:iterate>
                               <tr class="showHiddenTr">
                                   <td></td>
                                   <td class="align-left">
                                       <div class="products-text-style">
                                           <div id="DEPO_ACCOUNT" name="selectedClosedDepoAccounts" value="DEPO_ACCOUNT" class="notShowHidden" onclick="hideOrShowClosed(this, 'tableClosedDepoInSystem', this.id); profileProductName('tableClosedDepoInSystem')">
                                               <c:if test="${not empty confirmRequest}">
                                                   <input id="hiddenDEPO_ACCOUNT" type="hidden" name="selectedClosedDepoAccounts" value="${param.selectedClosedDepoAccounts}"/>
                                               </c:if>
                                               <i class="showHiddenImage" name="hiddenImage"></i><span name="hiddenText" class="showHiddenText">Показать закрытые</span>
                                           </div>
                                       </div>
                                   </td>
                               </tr>
                           </table>

                           <c:set var="showHideProducts" value="true"/>
                           <c:set var="selectdIds" value="selectedDepoAccountIds"/>
                           <table id="tableClosedDepoInSystem" width="100%">
                               <logic:iterate id="listItem" name="form" property="depoAccounts">
                                   <c:if test="${listItem.value.state eq 'closed'}">
                                       <c:set var="showHideProducts" value="false"/>
                                       <tr>
                                           <%@ include file="/WEB-INF/jsp/common/accountsNewViewNotShow.jsp"%>
                                           <td class="align-left productText inSystem">
                                               <div class="closedProduct">
                                                   <div class="moneyCurrency">
                                                       <span class="productProfileWidth">
                                                           <c:choose>
                                                               <c:when test="${empty listItem.name}">
                                                                   <bean:write name="listItem" property="accountNumber"/>
                                                               </c:when>
                                                               <c:otherwise>
                                                                   <bean:write name="listItem" property="name"/>&nbsp;(<bean:write name="listItem" property="accountNumber"/>
                                                               </c:otherwise>
                                                           </c:choose>&nbsp;
                                                       </span>
                                                       <span class="card-number">${listItem.depoAccount.agreementNumber}</span>&nbsp;
                                                   </div>
                                               </div>
                                           </td>
                                       </tr>

                                       <script type="text/javascript">
                                           addClosed('DEPO_ACCOUNT', '${listItem.id}');
                                       </script>
                                   </c:if>
                               </logic:iterate>
                               <script type="text/javascript">
                                   <c:if test="${not empty param.selectedClosedDepoAccounts}">
                                       document.getElementsByName("selectedClosedDepoAccounts")[0].checked = true;
                                   </c:if>
                                   hideOrShowClosed(${empty param.selectedClosedDepoAccounts ? 'null' : 'document.getElementsByName("selectedClosedDepoAccounts")[0]'}, 'tableClosedDepoInSystem', 'DEPO_ACCOUNT');
                                   hideShowClosedProducts(document.getElementById("DEPO_ACCOUNT"), '${showHideProducts}');
                               </script>
                           </table>
                       </fieldset>
                   </div>
               </c:if>
           </c:otherwise>
       </c:choose>
       <c:if test="${not empty form.securityAccounts}">
           <div class="profile-user-products viewProductsWidth simpleTable">
               <fieldset>
                   <table width="100%">
                       <tr class="tblInfHeader">
                           <th class="titleTable checkboxColumn">отображать</th>
                           <th class="titleTable"><bean:message bundle="userprofileBundle" key="title.securityAccount"/></th>
                           <th class="titleTable align-right">сумма</th>
                       </tr>
                       <logic:iterate id="listItem" name="form" property="securityAccounts">
                           <c:set var="securityAccount" value="${listItem.securityAccount}"/>
                           <tr>
                               <td class="align-center leftPaddingCell">
                                  <div class="products-text-style">
                                      <c:set var="spanName" value="selectedSecurityAccountIds${listItem.id}"/>
                                      <c:if test="${not empty confirmRequest}">
                                          <html:multibox name="form" property="selectedSecurityAccountIds" styleId="${spanName}" onclick="showDescSys('${spanName}');" value="${listItem.id}" style="display:none;"/>
                                      </c:if>
                                      <c:set var="mess" value="label.show_system"/>
                                      <c:if test="${listItem.showInSystem == false}">
                                          <label for="${spanName}"><c:set var="mess" value="label.not_show_system"/></label>
                                      </c:if>
                                      <html:multibox name="form" property="selectedSecurityAccountIds" styleId="${spanName}" onclick="showDescSys('${spanName}');" value="${listItem.id}" disabled="${not empty confirmRequest}"/>
                                      <span class="${spanName}"><label for="${spanName}"></label></span>
                                  </div>
                               </td>
                               <td class="align-left productText inSystem">
                                   <div class="products-text-style">
                                       <span class="productProfileWidth"><bean:write name="listItem" property="name"/>&nbsp;</span>
                                       <span class="card-number">${securityAccount.serialNumber}</span>&nbsp;
                                   </div>
                               </td>
                               <td class="align-right ${typeView}">
                                   <c:set var="securityAmount" value="${phiz:getSecurityAmount(securityAccount)}"/>
                                   <span class="moneyCurrency"><nobr>${phiz:getHtmlFormatAmount(securityAmount)}</nobr></span>
                               </td>

                           </tr>
                       </logic:iterate>
                   </table>
               </fieldset>
           </div>
       </c:if>
       <%--Карточка ПФР--%>
       <c:choose>
           <c:when test="${form.SNILS == null}">
               <div class="profile-user-products empty-profile-user-products">
                   <h4><bean:message bundle="userprofileBundle" key="title.pfr"/></h4>
                   <div class="profile-empty-text-depo">
                       У Вас не указан страховой номер индивидуального лицевого счета. Для того чтобы добавить номер лицевого счета, перейдите на страницу «Настройки» - «Персональная информация».
                   </div>
               </div>
           </c:when>
           <c:otherwise>
               <div class="profile-user-products viewProductsWidth simpleTable">
                   <table width="100%">
                       <tr class="tblInfHeader">
                           <th class="titleTable checkboxColumn">отображать</th>
                           <th class="titleTable"><bean:message bundle="userprofileBundle" key="title.pfr"/></th>
                           <th class="titleTable align-right">сумма</th>
                       </tr>
                       <tr>
                           <td class="align-center">
                               <div class="products-text-style">
                                   <c:set var="spanName" value="pfrLinkSelected"/>
                                   <c:if test="${not empty confirmRequest}">
                                       <html:checkbox name="form" property="pfrLinkSelected" styleId="${spanName}" onclick="showDescSys('${spanName}');" style="display:none;"/>
                                   </c:if>
                                   <c:set var="mess" value="label.show_system"/>
                                   <c:if test="${listItem.showInSystem == false}">
                                       <label for="${spanName}"><c:set var="mess" value="label.not_show_system"/></label>
                                   </c:if>
                                   <html:checkbox name="form" property="pfrLinkSelected" styleId="${spanName}" onclick="showDescSys('${spanName}');" disabled="${not empty confirmRequest}"/>
                                   <span class="${spanName}"><label for="${spanName}"></label></span>
                               </div>
                           </td>
                           <td class="align-left inSystem">
                               <div class="products-text-style closedProduct">
                                   <span class="card-number">${form.SNILS}</span>
                               </div>
                           </td>
                           <td></td>
                       </tr>
                   </table>
               </div>
               <div class="clear"></div>
           </c:otherwise>
       </c:choose>
   </div>


   <div class="buttonsArea strategy">
       <c:choose>
           <c:when test="${not empty confirmRequest  and confirmName=='settingsViewProductsInSystem'}">
               <tiles:insert definition="clientButton" flush="false">
                   <tiles:put name="commandTextKey" value="button.cancelChanges"/>
                   <tiles:put name="commandHelpKey" value="button.cancelChanges"/>
                   <tiles:put name="bundle" value="userprofileBundle"/>
                   <tiles:put name="action" value="/private/userprofile/accountSecurity.do"/>
                   <tiles:put name="viewType" value="buttonGrey"/>
               </tiles:insert>
               <tiles:insert definition="confirmButtons" flush="false">
                   <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountSecurity/accountsViewConfirm"/>
                   <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                   <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                   <tiles:put name="preConfirmCommandKey" value="button.preConfirmSystem"/>
                   <tiles:put name="confirmCommandKey" value="button.confirmSystem"/>
               </tiles:insert>
           </c:when>
           <c:otherwise>
               <tiles:insert definition="clientButton" flush="false">
                   <tiles:put name="commandTextKey" value="button.cancelChanges"/>
                   <tiles:put name="commandHelpKey" value="button.cancelChanges"/>
                   <tiles:put name="bundle" value="userprofileBundle"/>
                   <tiles:put name="action" value="/private/userprofile/accountSecurity.do"/>
                   <tiles:put name="viewType" value="buttonGrey"/>
               </tiles:insert>
               <tiles:insert definition="commandButton" flush="false">
                   <tiles:put name="commandKey" value="button.saveShowInSystem"/>
                   <tiles:put name="commandHelpKey" value="button.save.help"/>
                   <tiles:put name="isDefault" value="true"/>
                   <tiles:put name="validationFunction" value="checkData()"/>
                   <tiles:put name="bundle" value="userprofileBundle"/>
                   <tiles:put name="enabled" value="false"/>
                   <tiles:put name="id" value="nextButtonForSystem"/>
               </tiles:insert>
           </c:otherwise>
       </c:choose>
   </div>
   <span class="rightButtonText" id="saveTextSys">
       Для сохранения изменений необходимо подтверждение <b>SMS-паролем.</b>
   </span>
</div>
