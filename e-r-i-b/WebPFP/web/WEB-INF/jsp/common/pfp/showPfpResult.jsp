<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

<html:form action="/showPfpResult" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="profile" value="${form.riskProfile}"/>
    <c:set var="portfolioList" value="${form.portfolioList}"/>

    <tiles:insert definition="webModulePagePfp">
        <tiles:put name="title">
            <bean:message bundle="pfpBundle" key="page.title"/>
        </tiles:put>
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message bundle="pfpBundle" key="index.breadcrumbsLink"/></tiles:put>
                <c:choose>
                    <c:when test="${phiz:impliesService('UseWebAPIService')}">
                        <tiles:put name="url" value="${phiz:getWebAPIUrl('graphics.finance')}"/>
                    </c:when>
                    <c:otherwise>
                        <tiles:put name="action" value="/private/graphics/finance"/>
                    </c:otherwise>
                </c:choose>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name">Финансовое планирование</tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="description">
            Ваш личный план готов! Для оформления выбранных продуктов обратитесь к персональному менеджеру.
            <c:set var="viewMode" value="false"/>
            <c:if test="${form.fields['isViewMode']}">
                <c:set var="viewMode" value="true"/>
            </c:if>
        </tiles:put>
        <tiles:put name="data">
            <c:if test="${form.fields.relocateToDownload != null && form.fields.relocateToDownload == 'true'}">
                <script type="text/javascript">
                    $(window).load(function(){
                        <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/download')}?fileType=PfpResult&contentType=pdf&clientFileName=${form.fields.clientFileName}"/>
                        clientBeforeUnload.showTrigger=false;
                        if(window.navigator.userAgent.indexOf("iPhone") != -1 || window.navigator.userAgent.indexOf("iPad") != -1)
                            window.open('${downloadFileURL}');
                        else
                            goTo('${downloadFileURL}');
                        clientBeforeUnload.showTrigger=false;
                    });
                </script>
            </c:if>
            <div class="pfpBlocks">
                <div id="paymentStripe">
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.targets" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.riskProfile" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.portfolio" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.financePlan" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.plan" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="current" value="true"/>
                    </tiles:insert>
                    <div class="clear"></div>
                </div>

                <c:if test="${!profile.isDefault}">
                    <div class="pfpRiskResults">
                        <div class="pfpRiskResTitle">
                            <span class="riskProf">Ваш риск-профиль – </span><span class="greenResult"><c:out value="${profile.name}"/></span>
                        </div>
                        <div>
                            <c:out value="${profile.description}"/>
                        </div>
                    </div>
                </c:if>

                <div class="pfpDiagram">
                    <div class="riskProf">Распределение средств в портфелях</div>
                    <div id="pfpDiagramsBlock">
                        <c:set var="count" value="0"/>
                        <c:set var="diagrams">
                            <c:forEach items="${portfolioList}" var="portfolio">
                                <c:if test="${ not portfolio.isEmpty}">
                                    <c:set var="count" value="${count+1}"/>
                                    <div class='pfpPieChart'>
                                        <div class='pfpDiagramTitle'>${portfolio.type.description}</div>
                                        <div id="pfpDiagram${portfolio.type}" ></div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:set>
                        <c:choose>
                            <c:when test="${count == 1}">
                                <div class="oneDiagram">
                                    ${diagrams}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="twoDiagram">
                                    ${diagrams}
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="clear"></div>

                    <div class="portfolioProductTable">
                        <table class="pfpTableContainer" cellpadding="0" cellspacing="0">
                            <tbody>
                                <tr class="tblInfHeader">
                                    <th>Средства</th>
                                </tr>
                                <c:if test="${profile.productsWeights[form.insuranceType] != null}">
                                    <tr id="tr_insurance">
                                        <td class="productTitle">
                                            <img src="${globalImagePath}/pfp/color_insurance.gif">
                                            <span>Страхование</span>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${profile.productsWeights[form.accountType] != null}">
                                    <tr id="tr_account">
                                        <td class="productTitle">
                                            <img src="${globalImagePath}/pfp/color_account.gif">
                                            <span>Вклады</span>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${profile.productsWeights[form.fundType] != null}">
                                    <tr id="tr_fund">
                                        <td class="productTitle">
                                            <img src="${globalImagePath}/pfp/color_fund.gif">
                                            <span>ПИФы</span>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${profile.productsWeights[form.imaType] != null}">
                                    <tr id="tr_IMA">
                                        <td class="productTitle">
                                            <img src="${globalImagePath}/pfp/color_IMA.gif">
                                            <span>ОМС</span>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${profile.productsWeights[form.trustManagingType] != null}">
                                    <tr id="tr_trustManaging">
                                        <td class="productTitle">
                                            <img src="${globalImagePath}/pfp/color_trustManaging.gif">
                                            <span>Доверительное управление</span>
                                        </td>
                                    </tr>
                                </c:if>
                                <tr class="productTotalAmount">
                                    <td class="totalAmountTitle">Всего средств в портфеле</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="clear"></div>
                </div>

                <div class="pfpResultDescr">
                    Благодарим Вас за прохождение персонального финансового планирования.

                    <br/><br/>
                    На основе Ваших данных сформирован отчет о проведенном планировании. Вы можете скачать его в удобном формате.
                    Для этого нажмите на иконку отчета и сохраните файл на Вашем компьютере.

                    <br/><br/>
                    <div class="centering">
                        <div class="pfpUpload">
                            <html:link onclick="new CommandButton('button.unload').click('', true); return false;" href="#">
                                <div class="floatLeft"><img src="${globalImagePath}/pfp/PDF_icon.png" border="0"></div>
                                <div><c:out value="${form.pdfName}"/></div>
                            </html:link>
                            <div class="clear"></div>
                        </div>
                    </div>

                    <br/>
                    Обращаем Ваше внимание, что стоимость инвестиционных продуктов может как увеличиваться, так и уменьшаться.
                    Доход, полученный в прошлом, не гарантирует доходность в будущем.

                    <br/><br/>
                    Мы очень ценим наше сотрудничество и сделаем всё возможное, чтобы оно было комфортным и результативным.

                    <br/><br/>
                    <div class="align-right">С уважением, ОАО &laquo;Сбербанк России&raquo;</div>

                </div>

                <script type="text/javascript">
                    <%-- Цвета для круговой диаграммы: вклады, ОМС, ПИФы, страхование, доверительное управление --%>
                    var account_COLOR = "#5cc837";
                    var IMA_COLOR = "#b600ff";
                    var fund_COLOR = "#ff9e01";
                    var insurance_COLOR = "#0ebff0";
                    var trustManaging_COLOR = "#ed1c24";
                    var chartData = [];
                    var paramsPie = {
                        titleField: "title",
                        valueField: "percent",
                        colorField: "color",
                        outlineColor: "#FFFFFF",
                        outlineAlpha: 0.8,
                        outlineThickness: 0,
                        depth3D: 10,
                        angle: 30,
                        startRadius: '0%',
                        labelText: '[[percents]]%',
                        percentFormatter: {precision:0, decimalSeparator:'.', thousandsSeparator:' '},

                        balloonForDiagram:{
                            adjustBorderColor: true,
                            color: "#FFFFFF",
                            cornerRadius: 0,
                            fillColor: "#000000",
                            fillAlpha: 0.7,
                            borderThickness: 0,
                            pointerWidth: 10,
                            verticalPadding: 10
                        }
                    };

                    <c:forEach items="${portfolioList}" var="portfolio">
                        <c:if test="${not portfolio.isEmpty}">
                            chartData['${portfolio.type}'] = [
                                <c:set var="i" value="0"/>

                                <c:forEach items="${portfolio.productWeightPercentMap}" var="portfolioProduct">

                                       <c:if test="${i > 0}">
                                            ,
                                       </c:if>
                                       {
                                           title: "${portfolioProduct.key.description}",
                                           percent: ${portfolioProduct.value},
                                           color: ${portfolioProduct.key}_COLOR
                                        }
                                        <c:set var="i" value="${i+1}"/>

                                </c:forEach>
                            ];
                        </c:if>
                    </c:forEach>
                    var diagrams = [];
                    $(document).ready(function(){
                        <c:forEach items="${portfolioList}" var="portfolio">
                            <c:if test="${not portfolio.isEmpty}">
                                diagrams['${portfolio.type}'] = new diagram(paramsPie, 'pfpDiagram${portfolio.type}', 'circle', chartData['${portfolio.type}']);
                            </c:if>
                        </c:forEach>
                    });

                    function drawTable()
                    {
                        <c:forEach items="${portfolioList}" var="portfolio">
                            <c:if test="${!portfolio.isEmpty}">
                                var th = "<th class='alignRight'>${portfolio.type.description}</th>";
                                $(th).appendTo(".portfolioProductTable .tblInfHeader");
                                <c:forEach items="${portfolio.productWeightMap}" var="portfolioProduct">
                                    var td = "<td class='alignRight'>${phiz:formatAmount(portfolioProduct.value)}</td>";
                                    $(td).appendTo(".portfolioProductTable #tr_${portfolioProduct.key}");
                                </c:forEach>
                                var td = "<td class='alignRight'>${phiz:formatAmount(portfolio.productSum)}</td>";
                                $(td).appendTo(".portfolioProductTable .productTotalAmount");
                            </c:if>
                        </c:forEach>
                    }

                    $(document).ready(function(){
                       drawTable();
                    });

                </script>

            </div>

            <div class="pfpButtonsBlock">
                <c:set var="isAvailableStartPFP" value="${form.fields.isAvailableStartPFP == 'true'}"/>
                <c:choose>
                    <c:when test="${form.hasNotCoplitePFP && !viewMode}">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="button.continuePlanning"/>
                            <tiles:put name="commandTextKey" value="index.button.continuePlanning.text"/>
                            <tiles:put name="commandHelpKey" value="index.button.continuePlanning.help"/>
                            <tiles:put name="isDefault"        value="true"/>
                            <tiles:put name="bundle"         value="pfpBundle"/>
                        </tiles:insert>
                        <c:if test="${isAvailableStartPFP}">
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey"     value="button.startReplanning"/>
                                <tiles:put name="commandTextKey" value="index.button.startNewPlanning.text"/>
                                <tiles:put name="commandHelpKey" value="index.button.startNewPlanning.help"/>
                                <tiles:put name="isDefault"        value="true"/>
                                <tiles:put name="bundle"         value="pfpBundle"/>
                            </tiles:insert>
                        </c:if>
                    </c:when>
                    <c:when test="${isAvailableStartPFP}">
                        <tiles:insert definition="confirmationButton" flush="false">
                            <tiles:put name="winId"             value="confirmReplan"/>
                            <tiles:put name="title"><bean:message bundle="pfpBundle" key="button.confirmation.replan.title"/></tiles:put>
                            <tiles:put name="message"><bean:message bundle="pfpBundle" key="button.confirmation.replan.message"/></tiles:put>
                            <tiles:put name="buttonViewType"    value="buttonGreen"/>
                            <tiles:put name="confirmCommandKey" value="button.replan"/>
                            <tiles:put name="confirmKey"        value="button.confirmation.replan"/>
                            <tiles:put name="currentBundle"     value="pfpBundle"/>
                        </tiles:insert>
                    </c:when>
                </c:choose>
                <div class="clear"></div>
            </div>
            <c:if test="${isAvailableStartPFP}">
                <tiles:insert definition="confirmationButton" flush="false">
                    <tiles:put name="winId"                 value="confirmBack"/>
                    <tiles:put name="title"><bean:message bundle="pfpBundle" key="button.back2.title"/></tiles:put>
                    <tiles:put name="message"><bean:message bundle="pfpBundle" key="button.back2.message"/></tiles:put>
                    <tiles:put name="buttonViewType"        value="blueGrayLink"/>
                    <tiles:put name="confirmCommandKey"     value="button.back2"/>
                    <tiles:put name="confirmCommandTextKey" value="button.back2.apply"/>
                    <tiles:put name="confirmKey"            value="button.back2"/>
                    <tiles:put name="currentBundle"         value="pfpBundle"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>