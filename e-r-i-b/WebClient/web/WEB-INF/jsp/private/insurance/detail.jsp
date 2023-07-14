<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="detailsPage" value="true" scope="request"/>

<tiles:insert definition="mainWorkspace" flush="false">
    <tiles:put name="data">
        <script type="text/javascript">
            function printDetail(event)
            {
                var url = "${phiz:calculateActionURL(pageContext,'/private/insurance/print')}?id=${form.id}";
                openWindow(event, url, "PrintInformation", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
            }
        </script>
        <c:set var="name" value="${insuranceLink.name}"/>

        <c:set var="pattern">
            «${insuranceLink.patternForFavouriteLink}»
        </c:set>

        <div class="abstractContainer3">
            <div class="favouriteLinksButton">
                <tiles:insert definition="addToFavouriteButton" flush="false">
                    <tiles:put name="formName"><c:out value='${name}'/></tiles:put>
                    <tiles:put name="patternName"><c:out value='${pattern}'/></tiles:put>
                    <tiles:put name="typeFormat">${favoriteLinkType}</tiles:put>
                    <tiles:put name="productId">${form.id}</tiles:put>
                </tiles:insert>
            </div>
        </div>
        <%@include file="insuranceTemplate.jsp" %>

        <div class="tabContainer">
            <tiles:insert definition="paymentTabs" flush="false">
                <tiles:put name="count" value="1"/>
                <tiles:put name="tabItems">
                    <tiles:insert definition="paymentTabItem" flush="false">
                        <tiles:put name="position" value="first-last"/>
                        <tiles:put name="active" value="true"/>
                        <tiles:put name="title" value="Детальная информация"/>
                     </tiles:insert>
                </tiles:put>
            </tiles:insert>

            <div class="productTitleDetailInfo">
                <div id="productNameText" name="productNameText" class="word-wrap">
                    <span class="productTitleDetailInfoText">
                        <c:out value="${form.fields.insuranceName}"/>
                        <a class="productTitleDetailInfoEditBullet" onclick="showEditProductName();"></a>
                    </span>
                </div>
                <div id="productNameEdit" name="productNameEdit" class="productTitleDetailInfoEditBlock">
                    <html:text property="field(insuranceName)" size="30" maxlength="56" styleId="fieldInsName" styleClass="productTitleDetailInfoEditTextBox"/>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.saveInsuranceName"/>
                        <tiles:put name="commandHelpKey" value="button.saveInsuranceName"/>
                        <tiles:put name="bundle" value="insuranceBundle"/>
                    </tiles:insert>
                    <div class="errorDiv clear" style="display:none;"></div>
                </div>
            </div>
            <script type="text/javascript">
                function showEditProductName() {
                    $("#productNameText").hide();
                    $("#productNameEdit").show();
                    $("#fieldInsName")[0].selectionStart = $("#fieldInsName")[0].selectionEnd = $("#fieldInsName").val().length;
                }
            </script>

            <div class="abstractContainer2">
                <div class="printButtonRight">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.print"/>
                        <tiles:put name="commandHelpKey" value="button.print"/>
                        <tiles:put name="bundle" value="insuranceBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="image"    value="print-check.gif"/>
                        <tiles:put name="imageHover"     value="print-check-hover.gif"/>
                        <tiles:put name="imagePosition" value="left"/>
                        <tiles:put name="onclick">printDetail(event)</tiles:put>
                    </tiles:insert>
                </div>
            </div>
            <div class="clear">&nbsp;</div>
            <fieldset>
                <table class="additional-product-info firstColumnFix">
                    <c:if test="${not empty insuranceApp.program}">
                        <tr>
                            <td class="align-right field fixColumn">Страховая программа:</td>
                            <td><span class="bold word-wrap"><c:out value="${insuranceApp.program}"/></span></td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty insuranceApp.company}">
                        <tr>
                            <td class="align-right field">Страховая компания:</td>
                            <td><span class="bold word-wrap"><c:out value="${insuranceApp.company}"/></span></td>
                        </tr>
                    </c:if>

                    <c:set var="amount" value="${phiz:formatAmount(insuranceApp.amount)}"/>
                    <c:if test="${not empty amount}">
                        <tr>
                            <td class="align-right field">Страховая сумма:</td>
                            <td>
                                <span class="bold word-wrap">
                                    ${amount}
                                </span>
                            </td>
                        </tr>
                    </c:if>

                    <c:set var="startDate" value="${phiz:сalendarToString(insuranceApp.startDate)}"/>
                    <c:if test="${not empty startDate}">
                        <tr>
                            <td class="align-right field">Дата начала действия страховки:</td>
                            <td><span class="bold word-wrap">${startDate}</span></td>
                        </tr>
                    </c:if>

                    <c:set var="endDate" value="${phiz:сalendarToString(insuranceApp.endDate)}"/>
                    <c:if test="${not empty endDate}">
                        <tr>
                            <td class="align-right field">Срок окончания страховки:</td>
                            <td><span class="bold word-wrap">${endDate}</span></td>
                        </tr>
                    </c:if>

                    <c:set var="issueDate" value="${phiz:formatDateWithStringMonth(insuranceApp.policyDetails.issureDt)}"/>
                    <c:if test="${not empty insuranceApp.policyDetails.series or not empty insuranceApp.policyDetails.num or not empty issueDate}">
                        <tr>
                            <td class="align-right field">Реквизиты договора:</td>
                            <td><span class="bold word-wrap">№${insuranceApp.policyDetails.series} ${insuranceApp.policyDetails.num} от ${issueDate}.</span></td>
                        </tr>
                    </c:if>

                    <c:if test="${not empty insuranceApp.risk}">
                        <tr>
                            <td class="align-right field">Страховые риски:</td>
                            <td><span class="bold word-wrap"><c:out value="${insuranceApp.risk}"/></span></td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty insuranceApp.additionalInfo}">
                        <tr>
                            <td class="align-right field">Дополнительная информация:</td>
                            <td><span class="bold word-wrap"><c:out value="${insuranceApp.additionalInfo}"/></span></td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty insuranceApp.status}">
                        <tr>
                            <td class="align-right field">Статус:</td>
                            <td><span class="bold word-wrap"><c:out value="${insuranceApp.status}"/></span></td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty insuranceApp.SNILS}">
                        <tr>
                            <td class="align-right field">№ СНИЛС:</td>
                            <td><span class="bold word-wrap"><c:out value="${insuranceApp.SNILS}"/></span></td>
                        </tr>
                    </c:if>
                 </table>
            </fieldset>
        </div>
    </tiles:put>
</tiles:insert>