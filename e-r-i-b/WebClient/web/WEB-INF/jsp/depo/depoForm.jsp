<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<c:set var="form" value="${frm.form}"/>
<c:set var="accountLink" value="${frm.account}"/>
 <c:set var="depoAccount" value="${accountLink.depoAccount}" scope="request"/>
<tiles:insert definition="news">
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Счета депо"/>
            <tiles:put name="action" value="/private/depo/list.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name"><span class="bold">${depoAccount.accountNumber}</span></tiles:put>
            <tiles:put name="action" value="/private/depo/info/position.do?id=${accountLink.id}"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name">Анкета депонента</tiles:put>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
        <div class="depo-form">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Анкета депонента" />
                <tiles:put name="data">
                    <script type="text/javascript">
                        function update()
                        {
                          var url = "${phiz:calculateActionURL(pageContext,'/private/payments/payment.do')}";
                          window.location = url + "?form=DepositorFormClaim&depoId="+${accountLink.id};
                        }
                    </script>

                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${imagePath}/iconPmntList_DepositorFormClaim.png"/>
                        <tiles:put name="description">
                            На этой странице Вы можете просмотреть анкету депонента для выбранного счета депо.
                            Для обновления анкеты депонента нажмите на кнопку «Обновить» и оформите поручение на
                            получение анкетных данных. Эта операция в системе является платной и выполняется в
                            соответствии с тарифами банка. С тарифами  Вы можете ознакомиться на сайте Банка.
                        </tiles:put>
                    </tiles:insert>

                    <div class="clear"></div>
                    <div class="depositor-info">
                        <table>
                            <tr>
                                <td class="align-right field">По состоянию на:</td>
                                <td><span class="bold"> <bean:write name="form" property="lastUpdateDate.time" format="dd.MM.yyyy"/></span></td>
                            </tr>
                            <tr>
                                <td class="align-right field">ФИО:</td>
                                <td><span class="bold">${phiz:getFormattedPersonName(form.firstName, form.surName, form.partName)}</span></td>
                            </tr>
                            <tr>
                                <td class="align-right field">Номер счета депо:</td>
                                <td><span class="bold">${depoAccount.accountNumber}</span></td>
                            </tr>
                        </table>
                    </div>
                    <div class="tabContainer">
                        <tiles:insert definition="paymentTabs" flush="false">
                            <tiles:put name="count" value="3"/>
                            <c:choose>
                                <c:when test="${activeTabNumber == '0'}">
                                    <c:set var="firstActive" value="true"/>
                                    <c:set var="secondActive" value="false"/>
                                    <c:set var="thirdActive" value="false"/>
                                </c:when>
                                <c:when test="${activeTabNumber == '1'}">
                                    <c:set var="firstActive" value="false"/>
                                    <c:set var="secondActive" value="true"/>
                                    <c:set var="thirdActive" value="false"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="firstActive" value="false"/>
                                    <c:set var="secondActive" value="false"/>
                                    <c:set var="thirdActive" value="true"/>
                                </c:otherwise>
                            </c:choose>
                            <tiles:put name="tabItems">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="position" value="first"/>
                                    <tiles:put name="active" value="${firstActive}"/>
                                    <tiles:put name="title" value="Реквизиты физического лица"/>
                                    <tiles:put name="action" value="/private/depo/info/form.do?linkId=${accountLink.id}"/>
                                </tiles:insert>
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="active" value="${secondActive}"/>
                                    <tiles:put name="title" value="Адресные реквизиты"/>
                                    <tiles:put name="action" value="/private/depo/info/address.do?linkId=${accountLink.id}"/>
                                </tiles:insert>
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="position" value="last"/>
                                    <tiles:put name="active" value="${thirdActive}"/>
                                    <tiles:put name="title" value="Банковские реквизиты"/>
                                    <tiles:put name="action" value="/private/depo/info/bank.do?linkId=${accountLink.id}"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                        <div class="clear"></div>
                        ${content}
                        <div class="backToService">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.goto.depoAccounts.list"/>
                                <tiles:put name="commandHelpKey" value="button.goto.depoAccounts.list"/>
                                <tiles:put name="bundle" value="depoBundle"/>
                                <tiles:put name="viewType" value="blueGrayLink"/>
                                <tiles:put name="action" value="/private/depo/list"/>
                            </tiles:insert>
                        </div>
                        <div class="buttonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.update"/>
                                <tiles:put name="commandHelpKey" value="button.update"/>
                                <tiles:put name="bundle"         value="depoBundle"/>
                                <tiles:put name="onclick">update(event)</tiles:put>
                            </tiles:insert>
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>
