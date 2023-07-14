<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:set var="showProduct" value="${true}"/>
<c:if test="${typeView=='inES'}">
    <c:if test="${not phiz:impliesServiceRigid('ProductsSiriusView')}">
        <c:set var="showProduct" value="${false}"/>
    </c:if>
</c:if>

<c:if test="${not empty form.cards && showProduct}">
            <c:set var="selectdIds" value="${selectedCardIds}"/>
            <div class="profile-user-products viewProductsWidth">
                <fieldset>
                    <table width="100%">
                        <tr>
                            <th colspan="3"><bean:message bundle="userprofileBundle" key="title.cards"/></th>
                        </tr>
                        <logic:iterate id="listItem" name="form" property="cards">
                            <c:set var="state" value="${listItem.value.cardState}"/>
                            <c:set var="cardAccountstate" value="${listItem.value.cardAccountState}"/>
                            <c:set var="arrestedCard" value="${(cardAccountstate != null and cardAccountstate eq 'ARRESTED') and ((typeView eq 'inMobile') or (typeView eq 'inES'))}"/>
                            <c:if test="${not(state eq 'closed' or state eq 'blocked') and not arrestedCard}">
                                <tr>
                                    <td class="align-left ${typeView}">
                                        <div class="products-text-style">
                                            <span class="word-wrap"><bean:write name="listItem" property="name"/></span>
                                            <span class="card-number">${phiz:getCutCardNumber(listItem.number)}</span>&nbsp;
                                            <c:set var="spanClass" value="text-green"/>
                                            <c:if test="${listItem.card.availableLimit.decimal < 0}">
                                                <c:set var="spanClass" value="text-red"/>
                                            </c:if>
                                            <span class="${spanClass}"><nobr>${phiz:formatAmount(listItem.card.availableLimit)}</nobr></span>
                                        </div>
                                    </td>
                                    <%@ include file="accountsViewLabelComponent.jsp"%>
                                </tr>
                            </c:if>
                        </logic:iterate>
                        <tr class="closed-products-bg">
                            <td colspan="3" class="align-left">
                                <div class="products-text-style">

                                    <input id="CARD${productPredicat}" type="checkbox" onclick="hideOrShowClosed(this, '${tableClosedCards}', this.id)" name="selectedClosedCards" value="CARD${productPredicat}" ${not empty confirmRequest ? 'disabled' : ''}/>

                                    <c:if test="${not empty confirmRequest}">
                                        <input id="hiddenCARD${productPredicat}" type="hidden" name="selectedClosedCards" value="${param.selectedClosedCards}"/>
                                    </c:if>
                                    <b>
                                        <span><label for="CARD${productPredicat}">Показать закрытые</label></span>
                                    </b>
                                </div>
                            </td>
                        </tr>
                    </table>

                    <table id="${tableClosedCards}" width="100%">
                        <logic:iterate id="listItem" name="form" property="cards">
                            <c:set var="state" value="${listItem.value.cardState}"/>
                            <c:if test="${state eq 'closed' or state eq 'blocked'}">
                                <tr class="closed-products-bg">
                                    <td class="align-left ${typeView}">
                                        <div class="products-text-style">
                                            <span class="word-wrap"><bean:write name="listItem" property="name"/></span>
                                            <span class="card-number">${phiz:getCutCardNumber(listItem.number)}</span>&nbsp;
                                            <c:set var="spanClass" value="text-green"/>
                                            <c:if test="${listItem.card.availableLimit.decimal < 0}">
                                                <c:set var="spanClass" value="text-red"/>
                                            </c:if>
                                            <span class="${spanClass}"><nobr>${phiz:formatAmount(listItem.card.availableLimit)}</nobr></span>
                                        </div>
                                    </td>
                                   <%@ include file="accountsViewLabelComponent.jsp"%>
                                </tr>
                                <script type="text/javascript">
                                    addClosed('CARD${productPredicat}', '${listItem.id}');
                                </script>
                            </c:if>
                        </logic:iterate>
                        <script type="text/javascript">
                            <c:if test="${not empty param.selectedClosedCards}">
                                document.getElementsByName("selectedClosedCards")[0].checked = true;
                            </c:if>
                            hideOrShowClosed(${empty param.selectedClosedCards ? 'null' : 'document.getElementsByName("selectedClosedCards")[0]'}, '${tableClosedCards}', 'CARD${productPredicat}');
                        </script>
                    </table>
                </fieldset>
            </div>
</c:if>

       <c:if test="${not empty form.accounts}">
           <c:set var="selectdIds" value="${selectedAccountIds}"/>
            <div class="profile-user-products viewProductsWidth">
                <fieldset>
                    <table width="100%">
                        <tr>
                            <th colspan="2"><bean:message bundle="userprofileBundle" key="title.accounts"/></th>
                        </tr>
                        <logic:iterate id="listItem" name="form" property="accounts">
                            <c:set var="arrestedAccount" value="${(listItem.value.accountState eq 'ARRESTED') and ((typeView eq 'inMobile') or (typeView eq 'inES'))}"/>
                            <c:if test="${not(listItem.value.accountState eq 'CLOSED') and not arrestedAccount}">
                                <tr>
                                    <td class="align-left ${typeView}">
                                        <div class="products-text-style">
                                            <bean:write name="listItem" property="name"/>&nbsp;
                                            <span class="card-number">${phiz:getFormattedAccountNumber(listItem.account.number)}</span>&nbsp;
                                            <c:set var="spanClass" value="text-green"/>
                                            <c:if test="${listItem.account.balance.decimal < 0}">
                                                <c:set var="spanClass" value="text-red"/>
                                            </c:if>
                                            <span class="${spanClass}"><nobr>${phiz:formatAmount(listItem.account.balance)}</nobr></span>
                                        </div>
                                    </td>

                                   <%@ include file="accountsViewLabelComponent.jsp"%>
                                </tr>
                            </c:if>
                        </logic:iterate>
                        <tr class="closed-products-bg">
                            <td colspan="3" class="align-left">
                                <div class="products-text-style">
                                    <input id="ACCOUNT${productPredicat}" type="checkbox" onclick="hideOrShowClosed(this, 'tableClosedAccounts${typeView}', this.id)" name="selectedClosedAccounts" value="ACCOUNT${productPredicat}" ${not empty confirmRequest ? 'disabled' : ''}/>

                                    <c:if test="${not empty confirmRequest}">
                                        <input id="hiddenACCOUNT${productPredicat}" type="hidden" name="selectedClosedAccounts" value="${param.selectedClosedAccounts}"/>
                                    </c:if>
                                    <b>
                                        <span><label for="ACCOUNT${productPredicat}">Показать закрытые</label></span>
                                    </b>
                                </div>
                            </td>
                        </tr>
                    </table>

                    <table id="tableClosedAccounts${typeView}" width="100%">
                        <logic:iterate id="listItem" name="form" property="accounts">
                            <c:if test="${listItem.value.accountState eq 'CLOSED'}">
                                <tr class="closed-products-bg">
                                    <td class="align-left ${typeView}">
                                        <div class="products-text-style">
                                            <span class="word-wrap"><bean:write name="listItem" property="name"/></span>
                                            <span class="card-number">${phiz:getFormattedAccountNumber(listItem.account.number)}</span>&nbsp;
                                            <c:set var="spanClass" value="text-green"/>
                                            <c:if test="${listItem.account.balance.decimal < 0}">
                                                <c:set var="spanClass" value="text-red"/>
                                            </c:if>
                                            <span class="${spanClass}"><nobr>${phiz:formatAmount(listItem.account.balance)}</nobr></span>
                                        </div>
                                    </td>
                                    <%@ include file="accountsViewLabelComponent.jsp"%>
                                </tr>
                                <script type="text/javascript">
                                    addClosed('ACCOUNT${productPredicat}', '${listItem.id}');
                                </script>

                            </c:if>
                        </logic:iterate>
                        <script type="text/javascript">
                            <c:if test="${not empty param.selectedClosedAccounts}">
                                document.getElementsByName("selectedClosedAccounts")[0].checked = true;
                            </c:if>
                            hideOrShowClosed(${empty param.selectedClosedAccounts ? 'null' : 'document.getElementsByName("selectedClosedAccounts")[0]'}, 'tableClosedAccounts${typeView}', 'ACCOUNT${productPredicat}');
                        </script>
                    </table>
                </fieldset>
            </div>
        </c:if>

        <c:if test="${not empty form.loans && showProduct}">
            <c:set var="selectdIds" value="${selectedLoanIds}"/>
            <div class="profile-user-products viewProductsWidth">
                <fieldset>
                    <table width="100%">
                        <tr>
                            <th colspan="3"><bean:message bundle="userprofileBundle" key="title.loans"/></th>
                        </tr>
                        <logic:iterate id="listItem" name="form" property="loans">
                            <c:if test="${not(listItem.value.state eq 'closed')}">
                                <tr>
                                    <td class="align-left ${typeView}">
                                        <div class="products-text-style">
                                            <span class="word-wrap"><bean:write name="listItem" property="name"/></span>
                                            <span class="card-number">${listItem.number}</span>&nbsp;
                                            <span class="text-green"><nobr>${phiz:formatAmount(listItem.loan.loanAmount)}</nobr></span>
                                        </div>
                                    </td>
                                    <%@ include file="accountsViewLabelComponent.jsp"%>
                                </tr>
                            </c:if>
                        </logic:iterate>
                        <tr class="closed-products-bg">
                            <td colspan="3" class="align-left">
                                <div class="products-text-style">
                                    <input id="LOAN${typeView}" type="checkbox" name="selectedClosedLoans" onclick="hideOrShowClosed(this, 'tableClosedLoans${typeView}', this.id)" value="LOAN" ${not empty confirmRequest ? 'disabled' : ''}/>

                                    <c:if test="${not empty confirmRequest}">
                                        <input id="hiddenLOAN" type="hidden" name="selectedClosedLoans" value="${param.selectedClosedLoans}"/>
                                    </c:if>
                                    <b>
                                        <span><label for="LOAN${typeView}">Показать закрытые</label></span>
                                    </b>
                                </div>
                            </td>
                        </tr>
                    </table>

                    <table id="tableClosedLoans${typeView}" width="100%">
                        <logic:iterate id="listItem" name="form" property="loans">
                            <c:if test="${listItem.value.state eq 'closed'}">
                                <tr class="closed-products-bg">
                                    <td class="align-left ${typeView}">
                                        <div class="products-text-style">
                                            <span class="word-wrap"><bean:write name="listItem" property="name"/></span>
                                            <span class="card-number">${listItem.number}</span>&nbsp;
                                            <span class="text-green"><nobr>${phiz:formatAmount(listItem.loan.loanAmount)}</nobr></span>
                                        </div>
                                    </td>
                                    <%@ include file="accountsViewLabelComponent.jsp"%>
                                </tr>

                                <script type="text/javascript">
                                    addClosed('LOAN', '${listItem.id}');
                                </script>
                            </c:if>
                        </logic:iterate>
                        <script type="text/javascript">
                            <c:if test="${not empty param.selectedClosedLoans}">
                                document.getElementsByName("selectedClosedLoans")[0].checked = true;
                            </c:if>
                            hideOrShowClosed(${empty param.selectedClosedLoans ? 'null' : 'document.getElementsByName("selectedClosedLoans")[0]'}, 'tableClosedLoans${typeView}', 'LOAN');
                        </script>
                    </table>
                </fieldset>
            </div>
        </c:if>

        <c:if test="${typeView=='inSMS'}">
            <div class="profile-user-products viewProductsWidth">
                <fieldset>
                    <table width="100%">
                        <tr>
                            <th colspan="2"><bean:message bundle="userprofileBundle" key="label.productShowInSms.newProducts"/></th>
                        </tr>
                        <tr>
                            <td>
                                <html:checkbox property="newProductsShowInSms" name="form"  disabled= "${not empty confirmRequest}"/>
                            </td>
                            <td><bean:message bundle="userprofileBundle" key="message.productShowInSms.newProducts"/></td>
                        </tr>
                    </table>
                </fieldset>
            </div>
        </c:if>

        <c:if test="${typeView!='inSMS'}">
        <c:if test="${not empty form.imAccounts && showProduct}">
            <c:set var="selectdIds" value="${selectedIMAccountIds}"/>
            <div class="profile-user-products viewProductsWidth">
                <fieldset>
                    <table width="100%">
                        <tr>
                            <th colspan="3"><bean:message bundle="userprofileBundle" key="title.imaccounts"/></th>
                        </tr>
                        <logic:iterate id="listItem" name="form" property="imAccounts">
                            <c:set var="arrestedIMAccount" value="${(listItem.value.state eq 'arrested') and ((typeView eq 'inMobile') or (typeView eq 'inES'))}"/>
                            <c:if test="${not(listItem.value.state eq 'closed') and not arrestedIMAccount}">
                                <c:set var="imAccount" value="${listItem.imAccount}"/>

                                <tr>
                                    <td class="align-left ${typeView}">
                                        <div class="products-text-style">
                                            <bean:write name="listItem" property="name"/>&nbsp;
                                            <span class="card-number">${listItem.number}</span>&nbsp;
                                            <c:if test="${not empty imAccount.balance}">
                                                <c:set var="spanClass" value="text-green"/>
                                                <c:if test="${imAccount.balance.decimal < 0}">
                                                    <c:set var="spanClass" value="text-red"/>
                                                </c:if>
                                                <span class="${spanClass}"><nobr>${phiz:formatAmount(imAccount.balance)}&nbsp;${phiz:normalizeCurrencyCode(imAccount.balance.currency.code)}</nobr></span>
                                            </c:if>
                                        </div>
                                    </td>
                                    <%@ include file="accountsViewLabelComponent.jsp"%>
                                </tr>

                            </c:if>
                        </logic:iterate>
                        <tr class="closed-products-bg">
                            <td colspan="3" class="align-left">
                                <div class="products-text-style">
                                    <input id="IM_ACCOUNT${typeView}" type="checkbox" name="selectedClosedIMAccounts" value="IM_ACCOUNT" onclick="hideOrShowClosed(this, 'tableClosedIMAccounts${typeView}', this.id)" ${not empty confirmRequest ? 'disabled' : ''}/>

                                    <c:if test="${not empty confirmRequest}">
                                        <input id="hiddenIM_ACCOUNT${typeView}" type="hidden" name="selectedClosedIMAccounts" value="${param.selectedClosedIMAccounts}"/>
                                    </c:if>
                                    <b>
                                        <span><label for="IM_ACCOUNT${typeView}">Показать закрытые</label></span>
                                    </b>
                                </div>
                            </td>
                        </tr>
                    </table>

                    <table id="tableClosedIMAccounts${typeView}" width="100%">
                        <logic:iterate id="listItem" name="form" property="imAccounts">
                            <c:if test="${listItem.value.state eq 'closed'}">
                                <c:set var="imAccount" value="${listItem.imAccount}"/>
                                <tr class="closed-products-bg">
                                    <td class="align-left ${typeView}">
                                        <div class="products-text-style">
                                            <span class="word-wrap"><bean:write name="listItem" property="name"/></span>
                                            <span class="card-number">${listItem.number}</span>&nbsp;
                                            <c:if test="${not empty imAccount.balance}">
                                                <c:set var="spanClass" value="text-green"/>
                                                <c:if test="${imAccount.balance.decimal < 0}">
                                                    <c:set var="spanClass" value="text-red"/>
                                                </c:if>
                                                <span class="${spanClass}"><nobr>${phiz:formatAmount(imAccount.balance)}&nbsp;${phiz:normalizeCurrencyCode(imAccount.balance.currency.code)}</nobr></span>
                                            </c:if>
                                        </div>
                                    </td>
                                    <%@ include file="accountsViewLabelComponent.jsp"%>
                                </tr>
                                <script type="text/javascript">
                                    addClosed('IM_ACCOUNT', '${listItem.id}');
                                </script>
                            </c:if>
                        </logic:iterate>

                        <script type="text/javascript">
                            <c:if test="${not empty param.selectedClosedIMAccounts}">
                                document.getElementsByName("selectedClosedIMAccounts")[0].checked = true;
                            </c:if>
                            hideOrShowClosed(${empty param.selectedClosedIMAccounts ? 'null' : 'document.getElementsByName("selectedClosedIMAccounts")[0]'}, 'tableClosedIMAccounts${typeView}', 'IM_ACCOUNT');
                        </script>
                    </table>
                </fieldset>
            </div>
        </c:if>
        </c:if>