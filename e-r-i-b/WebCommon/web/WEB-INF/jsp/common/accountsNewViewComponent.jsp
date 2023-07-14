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
<c:set var="imagePath" value="${globalUrl}/commonSkin/images/"/>
<c:if test="${typeView=='inES'}">
    <c:if test="${not phiz:impliesServiceRigid('ProductsSiriusView')}">
        <c:set var="showProduct" value="${false}"/>
    </c:if>
</c:if>

        <c:if test="${not empty form.cards && showProduct}">
            <c:set var="selectdIds" value="${selectedCardIds}"/>
            <div class="profile-user-products viewProductsWidth simpleTable inSocial">
                <fieldset>
                    <table width="100%">
                        <tr class="tblInfHeader">
                            <th class="titleTable checkboxColumn">отображать</th>
                            <th class="titleTable"><bean:message bundle="userprofileBundle" key="title.cards"/></th>
                            <th class="titleTable align-right">сумма</th>
                        </tr>
                        <logic:iterate id="listItem" name="form" property="cards">
                            <c:set var="state" value="${listItem.value.cardState}"/>
                            <c:set var="cardAccountstate" value="${listItem.value.cardAccountState}"/>
                            <c:set var="arrestedCard" value="${(cardAccountstate != null and cardAccountstate eq 'ARRESTED') and ((typeView eq 'inMobile') or (typeView eq 'inES'))}"/>
                            <c:if test="${not(state eq 'closed' or state eq 'blocked') and not arrestedCard}">
                                <tr>
                                    <%@ include file="accountsNewViewLabelComponent.jsp"%>
                                    <td class="align-left productText ${typeView}">
                                        <div class="products-text-style">
                                            <c:set var="description" value="${listItem.description}"/>
                                            <c:set var="imgCode" value="${phiz:getCardImageCode(description)}"/>
                                            <c:choose>
                                                <c:when test="${listItem.card.cardState != 'active'}">
                                                    <img class="visaImage float" src="${imagePath}/cards_type/icon_cards_${imgCode}32Blocked.jpg"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <img class="visaImage float" src="${imagePath}/cards_type/icon_cards_${imgCode}32.gif"/>
                                                </c:otherwise>
                                            </c:choose>
                                            <span class="viewCardName productProfileWidth"><bean:write name="listItem" property="name"/>&nbsp;</span>
                                            <span class="card-number viewCardName ">${phiz:getCutCardNumber(listItem.number)}</span>&nbsp;
                                        </div>
                                    </td>
                                    <td class="align-right ${typeView}">
                                        <span class="moneyCurrency"><nobr>${phiz:getHtmlFormatAmount(listItem.card.availableLimit)}</nobr></span>
                                    </td>
                                </tr>
                            </c:if>
                        </logic:iterate>
                        <tr class="showHiddenTr">
                            <td></td>
                            <td class="align-left">
                                <div class="products-text-style">
                                    <div id="CARD${productPredicat}" name="selectedClosedCards" class="notShowHidden" onclick="hideOrShowClosed(this, '${tableClosedCards}', this.id); profileProductName('${tableClosedCards}');" value="CARD${productPredicat}">
                                        <c:if test="${not empty confirmRequest}">
                                            <input id="hiddenCARD${productPredicat}" type="hidden" name="selectedClosedCards" value="${param.selectedClosedCards}"/>
                                        </c:if>
                                        <i class="showHiddenImage" name="hiddenImage"></i><span name="hiddenText" class="showHiddenText">Показать закрытые</span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>

                    <c:set var="showHideProducts" value="true"/>
                    <table id="${tableClosedCards}" width="100%">
                        <logic:iterate id="listItem" name="form" property="cards">
                            <c:set var="state" value="${listItem.value.cardState}"/>
                            <c:if test="${state eq 'closed' or state eq 'blocked'}">
                                <c:set var="showHideProducts" value="false"/>
                                <tr>
                                    <%@ include file="accountsNewViewNotShow.jsp"%>
                                    <td class="align-left productText ${typeView}">
                                        <div class="moneyCurrency closedProduct">
                                            <c:set var="description" value="${listItem.description}"/>
                                            <c:set var="imgCode" value="${phiz:getCardImageCode(description)}"/>
                                            <c:choose>
                                                <c:when test="${listItem.card.cardState != 'active'}">
                                                    <img class="visaImage float" src="${imagePath}/cards_type/icon_cards_${imgCode}32Blocked.jpg"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <img class="visaImage float" src="${imagePath}/cards_type/icon_cards_${imgCode}32.gif"/>
                                                </c:otherwise>
                                            </c:choose>
                                            <span class="viewCardName productProfileWidth"><bean:write name="listItem" property="name"/>&nbsp;</span>
                                            <span class="card-number viewCardName ">${phiz:getCutCardNumber(listItem.number)}</span>&nbsp;
                                        </div>
                                    </td>
                                    <td class="align-right ${typeView}">
                                        <span class="closedProduct"><nobr>${phiz:getHtmlFormatAmount(listItem.card.availableLimit)}</nobr></span>
                                    </td>
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
                            hideShowClosedProducts(document.getElementById("CARD${productPredicat}"), '${showHideProducts}');
                        </script>
                    </table>
                </fieldset>
            </div>
        </c:if>

       <c:if test="${not empty form.accounts}">
           <c:set var="selectdIds" value="${selectedAccountIds}"/>
            <div class="profile-user-products viewProductsWidth simpleTable">
                <fieldset>
                    <table width="100%">
                        <tr class="tblInfHeader">
                            <th class="titleTable checkboxColumn">отображать</th>
                            <th class="titleTable"><bean:message bundle="userprofileBundle" key="title.accounts"/></th>
                            <th class="titleTable align-right">сумма</th>
                        </tr>
                        <logic:iterate id="listItem" name="form" property="accounts">
                            <c:set var="arrestedAccount" value="${(listItem.value.accountState eq 'ARRESTED') and ((typeView eq 'inMobile') or (typeView eq 'inES'))}"/>
                            <c:if test="${not(listItem.value.accountState eq 'CLOSED') and not arrestedAccount}">
                                <tr>
                                    <%@ include file="accountsNewViewLabelComponent.jsp"%>
                                    <td class="align-left productText ${typeView}">
                                        <div class="products-text-style">
                                            <span class="productProfileWidth"><bean:write name="listItem" property="name"/>&nbsp;</span>
                                            <span class="card-number">${phiz:getFormattedAccountNumber(listItem.account.number)}</span>&nbsp;
                                        </div>
                                    </td>
                                    <td class="align-right ${typeView}">
                                        <span class="moneyCurrency"><nobr>${phiz:getHtmlFormatAmount(listItem.account.balance)}</nobr></span>
                                    </td>

                                </tr>
                            </c:if>
                        </logic:iterate>

                        <tr class="showHiddenTr">
                            <td></td>
                            <td class="align-left">
                                <div class="products-text-style">
                                    <div id="ACCOUNT${productPredicat}" name="selectedClosedAccounts" class="notShowHidden" onclick="hideOrShowClosed(this, 'tableClosedAccounts${typeView}', this.id); profileProductName('tableClosedAccounts${typeView}')" value="ACCOUNT${productPredicat}">
                                        <c:if test="${not empty confirmRequest}">
                                            <input id="hiddenACCOUNT${productPredicat}" type="hidden" name="selectedClosedAccounts" value="${param.selectedClosedAccounts}"/>
                                        </c:if>
                                        <i class="showHiddenImage" name="hiddenImage"></i><span name="hiddenText" class="showHiddenText">Показать закрытые</span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>

                    <c:set var="showHideProducts" value="true"/>
                    <table id="tableClosedAccounts${typeView}" width="100%">
                        <logic:iterate id="listItem" name="form" property="accounts">
                            <c:if test="${listItem.value.accountState eq 'CLOSED'}">
                                <c:set var="showHideProducts" value="false"/>
                                <tr>
                                    <%@ include file="accountsNewViewNotShow.jsp"%>
                                    <td class="align-left productText ${typeView}">
                                        <div class="closedProduct">
                                            <span class="productProfileWidth maxWidth200"><bean:write name="listItem" property="name"/>&nbsp;</span>
                                            <span class="card-number">${phiz:getFormattedAccountNumber(listItem.account.number)}</span>&nbsp;
                                        </div>
                                    </td>
                                    <td class="align-right ${typeView}">
                                        <div class="closedProduct">
                                            <span class="moneyCurrency"><nobr>${phiz:getHtmlFormatAmount(listItem.account.balance)}</nobr></span>
                                        </div>
                                    </td>

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
                            hideShowClosedProducts(document.getElementById("ACCOUNT${productPredicat}"), '${showHideProducts}');
                        </script>
                    </table>
                </fieldset>
            </div>
        </c:if>

        <c:if test="${not empty form.loans && showProduct}">
            <c:set var="selectdIds" value="${selectedLoanIds}"/>
            <div class="profile-user-products viewProductsWidth simpleTable">
                <fieldset>
                    <table width="100%">
                        <tr class="tblInfHeader">
                            <th class="titleTable checkboxColumn">отображать</th>
                            <th class="titleTable"><bean:message bundle="userprofileBundle" key="title.loans"/></th>
                            <th class="titleTable align-right">сумма</th>
                        </tr>
                        <logic:iterate id="listItem" name="form" property="loans">
                            <c:if test="${not(listItem.value.state eq 'closed')}">
                                <tr>
                                    <%@ include file="accountsNewViewLabelComponent.jsp"%>
                                    <td class="align-left productText ${typeView}">
                                        <div class="products-text-style">
                                            <span class="productProfileWidth"><bean:write name="listItem" property="name"/>&nbsp;</span>
                                            <span class="card-number">${listItem.number}</span>&nbsp;
                                        </div>
                                    </td>
                                    <td class="align-right ${typeView}">
                                        <span class="moneyCurrency"><nobr>${phiz:getHtmlFormatAmount(listItem.loan.loanAmount)}</nobr></span>
                                    </td>

                                </tr>
                            </c:if>
                        </logic:iterate>
                        <tr class="showHiddenTr">
                            <td></td>
                            <td class="align-left">
                                <div class="products-text-style">
                                    <div id="LOAN${productPredicat}" name="selectedClosedLoans" class="notShowHidden" onclick="hideOrShowClosed(this, 'tableClosedLoans${typeView}', this.id); profileProductName('tableClosedLoans${typeView}')" value="LOAN${productPredicat}">
                                        <c:if test="${not empty confirmRequest}">
                                            <input id="hiddenLOAN${productPredicat}" type="hidden" name="selectedClosedLoans" value="${param.selectedClosedLoans}"/>
                                        </c:if>
                                        <i class="showHiddenImage" name="hiddenImage"></i><span name="hiddenText" class="showHiddenText">Показать закрытые</span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>

                    <c:set var="showHideProducts" value="true"/>
                    <table id="tableClosedLoans${typeView}" width="100%">
                        <logic:iterate id="listItem" name="form" property="loans">
                            <c:if test="${listItem.value.state eq 'closed'}">
                                <c:set var="showHideProducts" value="false"/>
                                <tr>
                                    <%@ include file="accountsNewViewNotShow.jsp"%>
                                    <td class="align-left  productText ${typeView}">
                                        <div class="closedProduct">
                                            <div class="moneyCurrency">
                                                <span class="productProfileWidth"><bean:write name="listItem" property="name"/>&nbsp;</span>
                                                <span class="card-number">${listItem.number}</span>&nbsp;
                                            </div>
                                        </div>
                                    </td>

                                    <td class="align-right ${typeView}">
                                        <div class="closedProduct">
                                            <span class="moneyCurrency"><nobr>${phiz:getHtmlFormatAmount(listItem.loan.loanAmount)}</nobr></span>
                                        </div>
                                    </td>

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
                            hideShowClosedProducts(document.getElementById("LOAN${productPredicat}"), '${showHideProducts}');
                        </script>
                    </table>
                </fieldset>
            </div>
        </c:if>


        <c:if test="${typeView=='inSMS'}">
            <div class="profile-user-products viewProductsWidth simpleTable">
                <fieldset>
                    <table width="100%">
                        <tr class="tblInfHeader">
                            <th class="titleTable checkboxColumn" colspan="2"><bean:message bundle="userprofileBundle" key="label.productShowInSms.newProducts"/></th>
                        </tr>
                        <tr>
                            <td>
                                <html:checkbox property="newProductsShowInSms" name="form"  disabled= "${not empty confirmRequest}" onclick="${functionShowDesc}('newProductsShowInSms');"/>
                                <span class="newProductsShowInSms"><label for="newProductsShowInSms"></label></span>
                            </td>
                            <td class="align-left productText ${typeView}">
                                <div class="products-text-style">
                                    <span class="productProfileWidth"><bean:message bundle="userprofileBundle" key="message.productShowInSms.newProducts"/></span>
                                </div>
                            </td>
                        </tr>
                    </table>
                </fieldset>
            </div>
        </c:if>

        <c:if test="${typeView!='inSMS'}">
        <c:if test="${not empty form.imAccounts && showProduct}">
            <c:set var="selectdIds" value="${selectedIMAccountIds}"/>
            <div class="profile-user-products viewProductsWidth simpleTable">
                <fieldset>
                    <table width="100%">
                        <tr class="tblInfHeader">
                            <th class="titleTable checkboxColumn">отображать</th>
                            <th class="titleTable"><bean:message bundle="userprofileBundle" key="title.imaccounts"/></th>
                            <th class="titleTable align-right">сумма</th>
                        </tr>
                        <logic:iterate id="listItem" name="form" property="imAccounts">
                            <c:set var="arrestedIMAccount" value="${(listItem.value.state eq 'arrested') and ((typeView eq 'inMobile') or (typeView eq 'inES'))}"/>
                            <c:if test="${not(listItem.value.state eq 'closed') and not arrestedIMAccount}">
                                <c:set var="imAccount" value="${listItem.imAccount}"/>

                                <tr>
                                    <%@ include file="accountsNewViewLabelComponent.jsp"%>
                                    <td class="align-left productText ${typeView}">
                                        <div class="products-text-style">
                                            <span class="productProfileWidth"><bean:write name="listItem" property="name"/>&nbsp;</span>
                                            <span class="card-number">${listItem.number}</span>&nbsp;
                                        </div>
                                    </td>
                                    <td class="align-right ${typeView}">
                                        <c:if test="${not empty imAccount.balance}">
                                            <span class="moneyCurrency"><nobr>${phiz:getHtmlFormatAmount(listItem.imAccount.balance)}</nobr></span>
                                        </c:if>
                                    </td>

                                </tr>

                            </c:if>
                        </logic:iterate>
                        <tr class="showHiddenTr">
                            <td></td>
                            <td class="align-left">
                                <div class="products-text-style">
                                    <div id="IM_ACCOUNT${productPredicat}" name="selectedClosedIMAccounts" class="notShowHidden" onclick="hideOrShowClosed(this, 'tableClosedIMAccounts${typeView}', this.id); profileProductName('tableClosedIMAccounts${typeView}')" value="IM_ACCOUNT${productPredicat}">
                                        <c:if test="${not empty confirmRequest}">
                                            <input id="hiddenIM_ACCOUNT${productPredicat}" type="hidden" name="selectedClosedIMAccounts" value="${param.selectedClosedIMAccounts}"/>
                                        </c:if>
                                        <i class="showHiddenImage" name="hiddenImage"></i><span name="hiddenText" class="showHiddenText">Показать закрытые</span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>

                    <c:set var="showHideProducts" value="true"/>
                    <table id="tableClosedIMAccounts${typeView}" width="100%">
                        <logic:iterate id="listItem" name="form" property="imAccounts">
                            <c:if test="${listItem.value.state eq 'closed'}">
                                <c:set var="showHideProducts" value="false"/>
                                <c:set var="imAccount" value="${listItem.imAccount}"/>
                                <tr>
                                    <%@ include file="accountsNewViewNotShow.jsp"%>
                                    <td class="align-left productText ${typeView}">
                                        <div class="moneyCurrency closedProduct">
                                            <span class="productProfileWidth"><bean:write name="listItem" property="name"/>&nbsp;</span>
                                            <span class="card-number">${listItem.number}</span>&nbsp;
                                        </div>
                                    </td>
                                    <td class="align-right ${typeView}">
                                        <c:if test="${not empty imAccount.balance}">
                                            <div class="closedProduct">
                                                <span class="moneyCurrency"><nobr>${phiz:getHtmlFormatAmount(listItem.imAccount.balance)}</nobr></span>
                                            </div>
                                        </c:if>
                                    </td>

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
                            hideShowClosedProducts(document.getElementById("IM_ACCOUNT${productPredicat}"), '${showHideProducts}');
                        </script>
                    </table>
                </fieldset>
            </div>
        </c:if>
        </c:if>