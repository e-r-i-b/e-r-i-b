<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>


<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
<c:set var="tariffAllowCardSmsNotification" value="${form.tariffAllowCardSmsNotification}"/>
<c:set var="tariffAllowAccountSmsNotification" value="${form.tariffAllowAccountSmsNotification}"/>
<c:set var="productsSmsNotificationConfirmStrategy" value="${form.productsSmsNotificationConfirmStrategy}"/>
<c:if test="${not empty form.productsSmsNotificationConfirmableObject}">
    <c:set var="productsSmsNotificationConfirmRequest" value="${phiz:currentConfirmRequest(form.productsSmsNotificationConfirmableObject)}"/>
</c:if>

<script type="text/javascript">

    function confirmRedirect()
    {
        if (isDataChanged() || ${not empty productsSmsNotificationConfirmRequest})
        {
            win.open('redirectRefused');
            return false;
        }
        redirect();
        return true;
    }

    function redirect()
    {
        window.location = "${phiz:calculateActionURL(pageContext, '/private/userprofile/accountsSystemView.do')}";
    }
    <c:set var="mobileBankMessage"><bean:message bundle="userprofileBundle" key="message.productNotification.mobilebankPage"/></c:set>
    doOnLoad(function() {

        initData();

        <c:set var="mbSettingsUrl" value="${phiz:calculateActionURL(pageContext,'/private/mobilebank/ermb/main.do')}"/>

        if (${form.showResourcesSmsNotificationBlock && !isNewProfile})
        {
            var message = "<bean:message bundle='userprofileBundle' key='message.productNotification.useInSMS'/> ".concat("<a onclick='confirmRedirect()' href = '#'}><bean:message bundle='userprofileBundle' key='message.productNotification.viewSettingsPage'/></a>");
            addMessage(message);
        }

        if (!${tariffAllowCardSmsNotification} && !${tariffAllowAccountSmsNotification})
        {
            var tariffMessageAccountCard = "<bean:message bundle='userprofileBundle' key='message.productNotification.changeTarifCardAndAccount'/> ".concat("<a href = '${mbSettingsUrl}'}>${mobileBankMessage}</a>");
            addMessage(tariffMessageAccountCard);
        }
        else if (!${tariffAllowCardSmsNotification})
        {
            var tariffMessageCard = "<bean:message bundle='userprofileBundle' key='message.productNotification.changeTarifCard'/> ".concat("<a href = '${mbSettingsUrl}'}>${mobileBankMessage}</a>");
            addMessage(tariffMessageCard);
        }
        else if (!${tariffAllowAccountSmsNotification})
        {
            var tariffMessageAccount = "<bean:message bundle='userprofileBundle' key='message.productNotification.changeTarifAccount'/> ".concat("<a href = '${mbSettingsUrl}'}>${mobileBankMessage}</a>");
            addMessage(tariffMessageAccount);
        }
    });

</script>


<c:if test="${not empty form.cards}">
    <div class="listContainer">
        <tiles:insert definition="simpleTableTemplate" flush="false">
            <tiles:put name="id" value="productsList"/>
            <tiles:put name="grid">
                <div class="productType">
                    <h2><bean:message bundle="userprofileBundle" key="title.cards"/></h2>
                </div>

                <sl:collection id="card" model="no-pagination" name="form" property="cards" bundle="userprofileBundle">

                    <c:if test="${not(card.value.cardState eq 'closed')}">
                        <sl:collectionItem title="">
                            <img src="${imagePathGlobal}/cards_type/icon_cards_m32.gif"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.productNotification.name">
                            <div class="product-name">
                                <c:out value="${card.name}"/>&nbsp
                                <span class="card-number">${phiz:getCutCardNumber(card.number)}</span>&nbsp;
                            </div>
                        </sl:collectionItem>
                        <sl:collectionItem title="">
                            <div class="product-amount">
                                <c:set var="spanClass" value="text-green"/>
                                <c:if test="${card.card.availableLimit.decimal < 0}">
                                    <c:set var="spanClass" value="text-red"/>
                                </c:if>
                                <span class="${spanClass}"><nobr>${phiz:formatAmount(card.card.availableLimit)}</nobr></span>
                            </div>
                        </sl:collectionItem>

                        <sl:collectionItem title="label.productNotification.notification" styleClass="centerListItem">
                            <c:if test="${not empty productsSmsNotificationConfirmRequest}">
                                <html:multibox name="form" property="selectedCardIds" value="${card.id}" style="display:none"/>
                            </c:if>

                            <html:multibox name="form" property="selectedCardIds" value="${card.id}" disabled="${not empty productsSmsNotificationConfirmRequest || not tariffAllowCardSmsNotification}"/>
                        </sl:collectionItem>
                    </c:if>

                </sl:collection>
            </tiles:put>
        </tiles:insert>
    </div>
</c:if>

<c:if test="${not empty form.accounts}">
    <div class="listContainer">
        <tiles:insert definition="simpleTableTemplate" flush="false">
            <tiles:put name="id" value="productsList"/>
            <tiles:put name="grid">
                <div class="productType">
                    <h2><bean:message bundle="userprofileBundle" key="label.productNotification.account"/></h2>
                </div>

                <sl:collection id="account" model="no-pagination" name="form" property="accounts" bundle="userprofileBundle">

                    <c:if test="${not(account.value.accountState eq 'CLOSED')}">
                        <sl:collectionItem title="">
                            <img src="${imagePathGlobal}/deposits_type/account32.jpg"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.productNotification.name" >
                            <div class="product-name">
                                <c:out value="${account.name}"/>&nbsp
                                <span class="card-number">${phiz:getFormattedAccountNumber(account.number)}</span>&nbsp;
                            </div>
                        </sl:collectionItem>
                        <sl:collectionItem title="">
                            <div class="product-amount">
                                <c:set var="spanClass" value="text-green"/>
                                <c:if test="${account.account.balance.decimal < 0}">
                                    <c:set var="spanClass" value="text-red"/>
                                </c:if>
                                <span class="${spanClass}"><nobr>${phiz:formatAmount(account.account.balance)}</nobr></span>
                            </div>

                        </sl:collectionItem>

                        <sl:collectionItem title="label.productNotification.notification" styleClass="centerListItem">

                            <c:if test="${not empty productsSmsNotificationConfirmRequest}">
                                <html:multibox name="form" property="selectedAccountIds" value="${account.id}" style="display:none"/>
                            </c:if>

                            <html:multibox name="form" property="selectedAccountIds" value="${account.id}" disabled="${not empty productsSmsNotificationConfirmRequest || not tariffAllowAccountSmsNotification}"/>
                        </sl:collectionItem>
                    </c:if>

                </sl:collection>
            </tiles:put>
        </tiles:insert>
    </div>
</c:if>

<c:if test="${not empty form.loans}">
    <div class="listContainer">
        <tiles:insert definition="simpleTableTemplate" flush="false">
            <tiles:put name="id" value="productsList"/>
            <tiles:put name="grid">
                <div class="productType">
                    <h2><bean:message bundle="userprofileBundle" key="title.loans"/></h2>
                </div>

                <sl:collection id="loan" model="no-pagination" name="form" property="loans" bundle="userprofileBundle">

                    <c:if test="${not(loan.value.state eq 'closed')}">
                        <sl:collectionItem title="">
                            <img src="${imagePathGlobal}/products/icon_credit32.jpg"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.productNotification.name">
                            <div class="product-name">
                                <c:out value="${loan.name}"/>&nbsp
                                <span class="card-number">${loan.number}</span>&nbsp;
                            </div>
                        </sl:collectionItem>
                        <sl:collectionItem title="">
                            <div class="product-amount">
                                <c:set var="spanClass" value="text-green"/>
                                <span class="text-green"><nobr>${phiz:formatAmount(loan.loan.loanAmount)}</nobr></span>
                            </div>

                        </sl:collectionItem>

                        <sl:collectionItem title="label.productNotification.notification" styleClass="centerListItem">
                            <c:set var="isLoanNotificationActive" value="${phiz:isLoanSmsNotificationAvailable()}"/>

                            <c:if test="${not empty productsSmsNotificationConfirmRequest}">
                                <html:multibox name="form" property="selectedLoanIds" value="${loan.id}" style="display:none"/>
                            </c:if>

                            <html:multibox name="form" property="selectedLoanIds" value="${loan.id}" disabled= "${not isLoanNotificationActive or not empty productsSmsNotificationConfirmRequest}"/>
                        </sl:collectionItem>
                    </c:if>

                </sl:collection>
            </tiles:put>
        </tiles:insert>
    </div>
</c:if>

<div class="listContainer">
    <tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="id" value="qqq"/>
        <tiles:put name="text"><div class="productType"><h2><bean:message bundle="userprofileBundle" key="label.productNotification.newProducts"/></h2></div></tiles:put>
        <tiles:put name="data">
            <table class="tblInf newProductsNotification">
                <tr>
                    <td>
                        <html:checkbox property="newProductsNotification" name="form"  disabled= "${not empty productsSmsNotificationConfirmRequest}"/>
                    </td>
                    <td><bean:message bundle="userprofileBundle" key="message.productNotification.newProducts"/></td>
                </tr>
            </table>
        </tiles:put>
    </tiles:insert>
</div>

<div class="buttonsArea">
    <c:choose>
        <c:when test="${not empty productsSmsNotificationConfirmRequest}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.backToEdit"/>
                <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="action" value="${cancelURL}"/>
                <tiles:put name="viewType" value="buttonGrey"/>
            </tiles:insert>
            <tiles:insert definition="confirmButtons" flush="false">
                <tiles:put name="ajaxUrl" value="/private/async/userprofile/userNotification"/>
                <tiles:put name="confirmRequest" beanName="productsSmsNotificationConfirmRequest"/>
                <tiles:put name="confirmStrategy" beanName="productsSmsNotificationConfirmStrategy"/>
                <tiles:put name="preConfirmCommandKey" value="button.preConfirmProductsSmsNotification"/>
                <tiles:put name="anotherStrategy" value="false"/>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="action" value="/private/accounts.do"/>
                <tiles:put name="viewType" value="buttonGrey"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.saveProductsSmsNotificationSettings"/>
                <tiles:put name="commandHelpKey" value="button.saveProductsSmsNotificationSettings.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
            </tiles:insert>
        </c:otherwise>
    </c:choose>
</div>


