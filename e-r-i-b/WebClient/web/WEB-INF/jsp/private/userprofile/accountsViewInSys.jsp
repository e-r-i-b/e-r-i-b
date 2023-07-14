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

        <script type="text/javascript">
            function showDescSys(elementId)
            {
                if ($("#"+elementId).attr("checked") == true)
                {
                   $("."+elementId + ' label').html(availableinSYS);
                }
                else
                {
                    $("."+elementId + ' label').html(unavailableinSYS);
                }
            }
        </script>

         <tiles:insert definition="accountsView" flush="false">
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
                    <div class="profile-empty-text-depo"> <bean:message bundle="commonBundle" key="text.depositary.not.include"/>&nbsp;<html:link action="/private/depo/list.do">Счета депо</html:link>.
                    </div>

                </div>
            </c:when>
            <c:otherwise>
                <c:if test="${not empty form.depoAccounts}">
                    <div class="profile-user-products viewProductsWidth">
                        <fieldset>
                            <table width="100%">
                                <tr>
                                    <th colspan="3"><bean:message bundle="userprofileBundle" key="title.depoAccounts"/></th>
                                </tr>
                                <logic:iterate id="listItem" name="form" property="depoAccounts">
                                    <c:if test="${not(listItem.value.state eq 'closed')}">
                                        <tr>
                                            <td class="align-left inSystem">
                                                <div class="products-text-style">
                                                    <c:choose>
                                                        <c:when test="${empty listItem.name}">
                                                            <bean:write name="listItem" property="accountNumber"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                           <bean:write name="listItem" property="name"/>(<bean:write name="listItem" property="accountNumber"/>)
                                                        </c:otherwise>
                                                    </c:choose>&nbsp;
                                                    <span class="card-number">${listItem.depoAccount.agreementNumber}</span>&nbsp;
                                                </div>
                                            </td>
                                            <td class="align-left">
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
                                                    <span class="${spanName}"><label for="${spanName}"><bean:message bundle="userprofileBundle" key="${mess}"/></label></span>
                                                </div>
                                            </td>
                                        </tr>

                                    </c:if>
                                </logic:iterate>
                                <tr class="closed-products-bg">
                                    <td colspan="3" class="align-left">
                                        <div class="products-text-style">
                                            <input id="DEPO_ACCOUNT" type="checkbox" name="selectedClosedDepoAccounts" value="DEPO_ACCOUNT" onclick="hideOrShowClosed(this, 'tableClosedDepoInSystem', this.id)" ${not empty confirmRequest ? 'disabled' : ''}/>

                                            <c:if test="${not empty confirmRequest}">
                                                <input id="hiddenDEPO_ACCOUNT" type="hidden" name="selectedClosedDepoAccounts" value="${param.selectedClosedDepoAccounts}"/>
                                            </c:if>
                                            <b>
                                                <span><label for="DEPO_ACCOUNT">Показать закрытые</label></span>
                                            </b>
                                        </div>
                                    </td>
                                </tr>
                            </table>

                            <table id="tableClosedDepoInSystem" width="100%">
                                <logic:iterate id="item" name="form" property="depoAccounts">
                                    <c:if test="${item.value.state eq 'closed'}">
                                        <tr class="closed-products-bg">
                                            <td class="align-left inSystem">
                                                <div class="products-text-style">
                                                    <c:choose>
                                                        <c:when test="${empty item.name}">
                                                            <bean:write name="item" property="accountNumber"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="word-wrap"><bean:write name="item" property="name"/></span>(<bean:write name="item" property="accountNumber"/>
                                                        </c:otherwise>
                                                    </c:choose>&nbsp;
                                                    <span class="card-number">${item.depoAccount.agreementNumber}</span>&nbsp;
                                                </div>
                                            </td>
                                            <td class="align-left">
                                                <div class="products-text-style">
                                                    <c:set var="spanName" value="selectedDepoAccountIds${item.id}"/>
                                                    <c:if test="${not empty confirmRequest}">
                                                        <html:multibox name="form" property="selectedDepoAccountIds" styleId="${spanName}" onclick="showDescSys('${spanName}');" value="${item.id}" style="display:none;"/>
                                                    </c:if>
                                                    <c:set var="mess" value="label.show_system"/>
                                                    <c:if test="${item.showInSystem == false}">
                                                        <label for="${spanName}"><c:set var="mess" value="label.not_show_system"/></label>
                                                    </c:if>
                                                    <html:multibox name="form" property="selectedDepoAccountIds" styleId="${spanName}" onclick="showDescSys('${spanName}');" value="${item.id}" disabled="${not empty confirmRequest}"/>
                                                    <span class="${spanName}"><label for="${spanName}"><bean:message bundle="userprofileBundle" key="${mess}"/></label></span>
                                                </div>
                                            </td>
                                        </tr>

                                        <script type="text/javascript">
                                            addClosed('DEPO_ACCOUNT', '${item.id}');
                                        </script>
                                    </c:if>
                                </logic:iterate>
                                <script type="text/javascript">
                                    <c:if test="${not empty param.selectedClosedDepoAccounts}">
                                        document.getElementsByName("selectedClosedDepoAccounts")[0].checked = true;
                                    </c:if>
                                    hideOrShowClosed(${empty param.selectedClosedDepoAccounts ? 'null' : 'document.getElementsByName("selectedClosedDepoAccounts")[0]'}, 'tableClosedDepoInSystem', 'DEPO_ACCOUNT');
                                </script>
                            </table>
                        </fieldset>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty form.securityAccounts}">
            <div class="profile-user-products viewProductsWidth">
                <fieldset>
                    <table width="100%">
                        <tr>
                            <th colspan="3"><bean:message bundle="userprofileBundle" key="title.securityAccount"/></th>
                        </tr>
                        <logic:iterate id="listItem" name="form" property="securityAccounts">
                            <c:set var="securityAccount" value="${listItem.securityAccount}"/>
                            <tr>
                                <td class="align-left inSystem">
                                    <div class="products-text-style">
                                                <bean:write name="listItem" property="name"/>
                                        <span class="card-number">${securityAccount.serialNumber}</span>&nbsp;
                                        <c:set var="spanClass" value="text-green"/>
                                        <c:set var="securityAmount" value="${phiz:getSecurityAmount(securityAccount)}"/>
                                        <span class="text-green"><nobr>${phiz:formatAmount(securityAmount)}</nobr></span>
                                    </div>
                                </td>
                                <td class="align-left">
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
                                        <span class="${spanName}"><label for="${spanName}"><bean:message bundle="userprofileBundle" key="${mess}"/></label></span>
                                    </div>
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
                <div class="profile-user-products viewProductsWidth">
                    <table width="100%">
                        <tr>
                            <th colspan="3"><bean:message bundle="userprofileBundle" key="title.pfr"/></th>
                        </tr>
                        <tr>
                            <td class="align-left inSystem">
                                <div class="products-text-style">
                                    <span class="card-number">${form.SNILS}</span>
                                </div>
                            </td>
                            <td class="align-left">
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
                                    <span class="${spanName}"><label for="${spanName}"><bean:message bundle="userprofileBundle" key="${mess}"/></label></span>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="clear"></div>
            </c:otherwise>
        </c:choose>
    </div>


    <div class="backToService bold">
        <html:link action="/private/userprofile/accountSecurity.do" onclick="return redirectResolved();" styleClass="blueGrayLink">
            <bean:message bundle="userprofileBundle" key="back.to.security"/>
        </html:link>
    </div>
    <div class="buttonsArea">
        <c:choose>
            <c:when test="${not empty confirmRequest}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.backToEdit"/>
                    <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                </tiles:insert>
                <tiles:insert definition="confirmButtons" flush="false">
                    <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountsSystemView"/>
                    <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                    <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                    <tiles:put name="preConfirmCommandKey" value="button.preConfirmSystem"/>
                    <tiles:put name="confirmCommandKey" value="button.confirmSystem"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="action" value="/private/userprofile/accountSecurity.do"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                </tiles:insert>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.saveShowInSystem"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="validationFunction" value="checkData()"/>
                    <tiles:put name="bundle" value="userprofileBundle"/>
                </tiles:insert>
            </c:otherwise>
        </c:choose>
        <div class="clear"></div>
    </div>
</div>
