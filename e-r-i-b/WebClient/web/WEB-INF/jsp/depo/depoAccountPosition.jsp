<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>
<html:form action="/private/depo/info/position" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="depoAccountLink" value="${form.depoAccountLink}" scope="request"/>
    <c:set var="depoAccount" value="${depoAccountLink.depoAccount}" scope="request"/>
    <c:set var="depoAccountPosition" value="${form.depoAccountPosition}" scope="request"/>
    <c:set var="detailsPage" value="true"/>

<tiles:insert definition="depoAccountInfo">
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
            <tiles:put name="name"><span class="bold">${depoAccountLink.accountNumber}</span></tiles:put>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>

    <tiles:put name="data" type="string">
    <c:if test="${not empty depoAccountPosition}">
        <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="data">
                <script type="text/javascript">
                    var divisionCount = ${phiz:size(depoAccountPosition.depoAccountDivisions)};
                    var accountEmpty = false;


                    function hideOrShowMark()
                    {
                        if($("#mark").attr("checked") == false){
                            for (var j=1; j<=divisionCount; j++)
                            {
                                $("#MarkByNominal"+j).hide();
                                $("#MarkByCoupon"+j).hide();
                                $("#MarkByNominalAndCoupon"+j).hide();
                                $("#OpenDiv"+j).show();
                            }
                        }else{
                            for (var k=1; k<=divisionCount; k++)
                            {
                                $("#MarkByNominal"+k).show();
                                $("#MarkByCoupon"+k).show();
                                $("#MarkByNominalAndCoupon"+k).show();
                                $("#OpenDiv"+k).hide();
                            }
                        }
                    }

                    function hideOrShowClosed()
                    {
                        if($("#closed").attr("checked") == false){
                            for (var j=1; j<= divisionCount; j++)
                            {
                                $("#Closed"+j).hide();
                                $("#OpenDiv"+j).show();
                            }
                        }else{
                            for (var k=1; k<= divisionCount; k++)
                            {
                                $("#Closed"+k).show();
                                $("#OpenDiv"+k).hide();
                            }
                        }
                    }
                    $(document).ready(function(){

                        hideOrShowMark();
                        hideOrShowClosed();


                        $(".depoSecurityName").click(function(){
                            $(this).closest("tbody").next().show();
                        });

                        $(".depoDetailClose").click(function(){
                            $(this).closest("tbody").hide();
                        });

                        $("#mark").click(function(){
                            hideOrShowMark();
                        });

                        $("#closed").click(function(){
                           hideOrShowClosed();
                        });

                    });

                    function showDivision(accountDivision)
                    {
                        var val = $("#AccountDivision").val();
                        if (accountDivision != '' && accountDivision != null)
                        {
                            val = accountDivision;
                            $("#AccountDivision").val(val);
                        }
                        for (var j=1; j<= divisionCount; j++)
                        {
                            if ((val == j || val == "0") && !accountEmpty)
                            {
                                $("#positionNumber"+j).show();
                            }else
                            {
                                $("#positionNumber"+j).hide();
                            }
                        }
                        if(accountEmpty)
                        {
                            $("#positionNumber"+1).show();
                        }
                    }

                    function initialize()
                    {
                    <c:set var="lineNumber" value="0"/>
                    <c:set var="emptyDivisions" value ="0"/>
                    <c:forEach items="${depoAccountPosition.depoAccountDivisions}" var="depoAccountDivision">
                        <c:set var="lineNumber" value="${lineNumber+1}"/>
                        var  divOpen = '';
                        var  divClosed = '';
                        var  divMarkByNominal = '';
                        var  divMarkByCoupon = '';
                        var  divMarkByNominalAndCoupon = '';
                        var  divOpenHtml = '';
                        var  divClosedHtml = '';
                        var  divMarkByNominalHtml = '';
                        var  divMarkByCouponHtml = '';
                        var  divMarkByNominalAndCouponHtml = '';
                         <c:forEach items="${depoAccountDivision.depoAccountSecurities}" var="listElement">
                            var line = '<tr>' +
                                        '<td class="align-left leftPaddingCell">${listElement.name}</td> ' +
                                        '<td  class="align-left">${listElement.registrationNumber}</td>' +
                                        '<td  class="align-right">${phiz:getStringInNumberFormat(listElement.remainder)}</td>' +
                                    '</tr>';
                            var  lineMark;
                            <c:if test="${listElement.storageMethod != 'closed'}">
                                lineMark = '<tr>' +
                                                '<td class="align-left whiteCells leftPaddingCell"><span class="bold depoSecurityName">${listElement.name}</span></td> ' +
                                                '<td class="align-left whiteCells">${listElement.registrationNumber}</td>' +
                                                '<td class="align-right bold whiteCells">${phiz:getStringInNumberFormat(listElement.remainder)}</td>' +
                                            '</tr>';
                            </c:if>
                            <c:if test="${listElement.storageMethod == 'closed'}">
                                lineMark = '<tr>' +
                                                '<td class="align-left leftPaddingCell"><span class="bold depoSecurityName">${listElement.name}</span></td> ' +
                                                '<td class="align-left">${listElement.registrationNumber}  Номинал: ${listElement.nominal.decimal} ${listElement.nominal.currency.code}</td>' +
                                                '<td class="align-right bold">${phiz:getStringInNumberFormat(listElement.remainder)}</td>' +
                                            '</tr>';
                            </c:if>
                            <c:if test="${not empty listElement.securityMarkers}">
                                var size = ${phiz:size(listElement.securityMarkers)};
                                lineMark = lineMark + '<tr>' + '<td colspan="3" class="noBorder">' + '</td>' + '</tr>' +'<tbody style="display: none;">'+
                                                            '<tr>'+
                                                                '<td rowspan="'+size+'" class="align-left depoDetailClose"> &nbsp;- скрыть детализацию</td>'+
                                                                '<td class="align-left">${listElement.securityMarkers[0].marker}</td>'+
                                                                '<td class="align-right">${phiz:getStringInNumberFormat(listElement.securityMarkers[0].remainder)}</td>'+
                                                            '</tr>';
                                <c:forEach items="${listElement.securityMarkers}" var="marker" begin="1">
                                    lineMark =lineMark +'<tr>'+
                                                            '<td>${marker.marker}</td>'+
                                                            '<td class="align-right">${marker.remainder}</td>'+
                                                        '</tr>';
                                </c:forEach>
                                lineMark = lineMark + '</tbody>';
                            </c:if>
                            <c:choose>
                                <c:when test="${listElement.storageMethod == 'open'}">
                                    divOpenHtml = divOpenHtml + line;
                                </c:when>
                                <c:when test="${listElement.storageMethod == 'closed'}">
                                    divClosedHtml = divClosedHtml + lineMark;
                                </c:when>
                                <c:when test="${listElement.storageMethod == 'markByNominal'}">
                                    divMarkByNominalHtml = divMarkByNominalHtml + lineMark;
                                </c:when>
                                <c:when test="${listElement.storageMethod == 'markByCoupon'}">
                                    divMarkByCouponHtml = divMarkByCouponHtml + lineMark;
                                </c:when>
                                <c:when test="${listElement.storageMethod == 'markByNominalAndCoupon'}">
                                    divMarkByNominalAndCouponHtml = divMarkByNominalAndCouponHtml + lineMark;
                                </c:when>
                            </c:choose>
                        </c:forEach>

                        var tableTh = '<tr class="markTitle tblInfHeader">'+
                                '<th class="align-left titleTable">Наименование ЦБ</th>'+
                                '<th class="align-left titleTable">Регистрационный номер ЦБ</th>'+
                                '<th class="align-right titleTable" width="143">Кол-во</th>'+
                            '</tr>';
                        if (divOpenHtml != '')
                        {
                            divOpen = '<div  id="Open${lineNumber}"><div class=\"italic bold depoDivisionType\" ><span>Способ учета:</span> открытый учет</div>'+
                                      '<table class="depoPositionInfo">'+
                            tableTh + divOpenHtml + '</table></div>';
                        }
                        if (divClosedHtml != '')
                        {
                            divClosed = '<div  id="Closed${lineNumber}"><div class=\"italic bold depoDivisionType\"><span >Способ учета:</span> закрытый учет</div>'+
                                        '<table class="depoPositionInfo">'+
                            tableTh + divClosedHtml + '</table></div>';
                        }
                        if (divMarkByNominalHtml != '')
                        {
                            divMarkByNominal = '<div  id="MarkByNominal${lineNumber}"><div class=\"italic bold depoDivisionType\"><span>Способ учета:</span> маркированный по номиналу</div><div class=\"depoDivisionHint\">Для просмотра детальной информации по позиции щелкните по наименованию ценной бумаги.</div>'+
                                              '<table class="depoPositionMarkerInfo" >'+
                            tableTh + divMarkByNominalHtml + '</table></div>';
                        }
                        if (divMarkByCouponHtml != '')
                        {
                            divMarkByCoupon = '<div  id="MarkByCoupon${lineNumber}"><div class=\"italic bold depoDivisionType\"><span>Способ учета:</span> маркированный по купону</div><div class=\"depoDivisionHint\">Для просмотра детальной информации по позиции щелкните по наименованию ценной бумаги.</div>'+
                                              '<table class="depoPositionMarkerInfo" >'+
                            tableTh + divMarkByCouponHtml + '</table></div>';
                        }
                        if (divMarkByNominalAndCouponHtml != '')
                        {
                            divMarkByNominalAndCoupon = '<div  id="MarkByNominalAndCoupon${lineNumber}"><div class=\"italic bold depoDivisionType\"><span>Способ учета:</span> маркированный по номиналу и купону</div><div class=\"depoDivisionHint\">Для просмотра детальной информации по позиции щелкните по наименованию ценной бумаги.</div>'+
                                                        '<table class="depoPositionMarkerInfo" >'+
                            tableTh + divMarkByNominalAndCouponHtml + '</table></div>';
                        }

                        if(divOpen == '' && (divClosed != ''|| divMarkByNominal != '' || divMarkByCoupon != ''||  divMarkByNominalAndCoupon != ''))
                        {
                            divOpen = '<div class="emptyText greenBlock" id="OpenDiv${lineNumber}"><div class="workspace-box roundBorder greenBold">'+
                                         'У вас нет ценных бумаг выбранного способа хранения.'+
                                      '</div></div>';
                        }
                        if (${empty depoAccountDivision.depoAccountSecurities})
                        {
                            var divEmpty = ' <div class="emptyText greenBlock"><div class="workspace-box roundBorder greenBold">'+
                                                 'На выбранном разделе счета депо нет ценных бумаг.'+
                                            '</div></div>';
                            document.getElementById("positionNumber${lineNumber}").innerHTML = "<div class=\"bold uppercase depoDivisionName\">Раздел счета депо: <span class=\"text-green\"><c:out value="${depoAccountDivision.divisionType}"/>  <c:out value="${depoAccountDivision.divisionNumber}"/></span></div>"+ divEmpty;

                        } else
                            document.getElementById("positionNumber${lineNumber}").innerHTML = "<div class=\"bold uppercase depoDivisionName\">Раздел счета депо: <span  class=\"text-green\"><c:out value="${depoAccountDivision.divisionType}"/> <c:out value="${depoAccountDivision.divisionNumber}"/></span></div>"+divOpen + divClosed + divMarkByNominal + divMarkByCoupon + divMarkByNominalAndCoupon;
                         <c:if test="${empty depoAccountDivision.depoAccountSecurities}">
                                <c:set var="emptyDivisions" value ="${emptyDivisions + 1}"/>
                         </c:if>
                     </c:forEach>
                        if(${lineNumber} == ${emptyDivisions})
                        {
                            accountEmpty = true;
                            var divEmpty = ' <div class="emptyText"><div class="workspace-box roundBorder greenBold">'+
                                                     'На данном счете депо нет ценных бумаг.'+
                                            '</div></div>';
                            document.getElementById("positionNumber${1}").innerHTML = "<div class=\"bold uppercase depoDivisionName\"></span></div>"+ divEmpty;
                        }
                    }

                </script>

                <c:set var="showInMainCheckBox" value="true"/>


                <c:set var="nameOrNumber">
                    <c:choose>
                       <c:when test="${not empty depoAccountLink.name}">
                          <c:out value="«${depoAccountLink.name}»"/>
                       </c:when>
                       <c:otherwise>
                          ${depoAccountLink.accountNumber}
                       </c:otherwise>
                    </c:choose>
                </c:set>

                <div class="abstractContainer3">
                    <c:set var="baseInfo">
                        <bean:message key="favourite.link.depo" bundle="paymentsBundle"/>
                    </c:set>
                    <div class="favouriteLinksButton">
                        <tiles:insert definition="addToFavouriteButton" flush="false">
                            <tiles:put name="formName"><c:out value='${baseInfo} ${nameOrNumber}'/></tiles:put>
                            <tiles:put name="patternName"><c:out value='${baseInfo} ${depoAccountLink.patternForFavouriteLink}'/></tiles:put>
                            <tiles:put name="typeFormat">DEPO_LINK</tiles:put>
                            <tiles:put name="productId">${form.id}</tiles:put>
                        </tiles:insert>
                    </div>
                </div>

                <%@ include file="/WEB-INF/jsp/depo/depoAccountTemplate.jsp"%>
                <div class="tabContainer">
                    <tiles:insert definition="paymentTabs" flush="false">
                        <tiles:put name="count" value="2"/>
                        <c:set var="position" value="last"/>
                        <c:set var="active" value="false"/>
                        <c:if test="${depoAccount.state == 'closed'}">
                            <tiles:put name="count" value="1"/>
                            <c:set var="position" value="first-last"/>
                            <c:set var="active" value="true"/>
                        </c:if>
                        <tiles:put name="tabItems">
                            <c:if test="${depoAccount.state != 'closed'}">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="position" value="first"/>
                                    <tiles:put name="active" value="true"/>
                                    <tiles:put name="title" value="Информация по позиции"/>
                                    <tiles:put name="action" value="/private/depo/info/position.do?id=${depoAccountLink.id}"/>
                                </tiles:insert>
                            </c:if>
                            <tiles:insert definition="paymentTabItem" flush="false">
                                <tiles:put name="position" value="${position}"/>
                                <tiles:put name="active" value="${active}"/>
                                <tiles:put name="title" value="Список документов"/>
                                <tiles:put name="action" value="/private/depo/documents.do?id=${depoAccountLink.id}"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                <div class="depoInfoFilter">
                    <div class="productTitleDetailInfo">
                        <div id="productNameText" name="productNameText" class="word-wrap">
                                <span class="productTitleDetailInfoText">
                                    <c:out value="${form.fields.depoAccountName}"/>
                                    <a class="productTitleDetailInfoEditBullet" onclick="showEditProductName();"></a>
                                </span>
                        </div>
                        <div id="productNameEdit" name="productNameEdit" class="productTitleDetailInfoEditBlock">
                            <html:text property="field(depoAccountName)" size="30" maxlength="56" styleId="fieldDepoName" styleClass="productTitleDetailInfoEditTextBox"/>
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.saveDepoAccountName"/>
                                <tiles:put name="commandHelpKey" value="button.saveDepoAccountName.help"/>
                                <tiles:put name="bundle" value="depoBundle"/>
                            </tiles:insert>
                            <div class="errorDiv clear" style="display:none;"></div>
                        </div>
                    </div>
                    </br>

                    <script type="text/javascript">
                        function showEditProductName() {
                            $("#productNameText").hide();
                            $("#productNameEdit").show();
                            $("#fieldDepoName")[0].selectionStart = $("#fieldDepoName")[0].selectionEnd = $("#fieldDepoName").val().length;
                        }
                    </script>

                    <div class="depoCurrentDate">
                        <c:set var="curretntDate" value="${phiz:currentDate()}"/>
                        Информация на дату: <span class="bold">${phiz:formatDateWithStringMonth(curretntDate)}</span>
                    </div>
                    <fieldset class="depoPositionFilter">
                        <div><html:checkbox property="field(closed)" styleId="closed"/> показывать закрытое хранение</div>
                        <div><html:checkbox property="field(mark)" styleId="mark"/> показывать маркированный способ учета</div>
                    </fieldset>
                    <div class="depoDivisionSelect">
                        Раздел счета депо:
                        <c:set var="lineNumber" value="0"/>
                        <select name="accountDivision" id="AccountDivision">
                            <option value="0">Все</option>
                            <c:forEach items="${depoAccountPosition.depoAccountDivisions}" var="depoAccountDivision" >
                                <c:set var="lineNumber" value="${lineNumber+1}"/>
                                <option value="${lineNumber}">${depoAccountDivision.divisionType} ${depoAccountDivision.divisionNumber}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.view"/>
                    <tiles:put name="commandHelpKey" value="button.view"/>
                    <tiles:put name="bundle" value="depoBundle"/>
                    <tiles:put name="viewType" value="blueGrayLinkDotted"/>
                    <tiles:put name="onclick" value="showDivision();"/>
                </tiles:insert>
                <div class="clear"></div>

                <c:set var="lineNumber" value="0"/>
                <c:forEach items="${depoAccountPosition.depoAccountDivisions}" var="depoAccountDivision">
                    <c:set var="lineNumber" value="${lineNumber+1}"/>
                    <div id="positionNumber${lineNumber}"></div>
                </c:forEach>
                <div class="debtButtons buttonsArea">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.update"/>
                        <tiles:put name="commandHelpKey" value="button.update.list"/>
                        <tiles:put name="bundle"         value="depoBundle"/>
                    </tiles:insert>
                </div>
                <div class="clear"></div>
            </tiles:put>
        </tiles:insert>
        <c:if test="${not empty form.anotherDepoAccounts}">
            <div id="another-cards">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <c:set var="depoAccountCount" value="${phiz:getClientProductLinksCount('DEPO_ACCOUNT')}"/>
                    <tiles:put name="title">
                        Остальные счета депо
                        (<a href="${phiz:calculateActionURL(pageContext, '/private/depo/list')}" class="blueGrayLink">показать все ${depoAccountCount}</a>)
                    </tiles:put>
                    <tiles:put name="data">
                        <div class="another-items">
                            &nbsp;
                            <c:set var="depoInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/depo/info/position.do?id=')}"/>
                            <c:forEach items="${form.anotherDepoAccounts}" var="others">
                                <div class="another-container">
                                    <a href="${depoInfoUrl}${others.id}">
                                        <img src="${image}/icon_depositariy32.jpg" alt=""/>
                                    </a>
                                    <a class="another-number decoration-none" href="${depoInfoUrl}${others.id}">
                                        <c:choose>
                                            <c:when test="${empty others.name}">
                                                ${others.accountNumber}
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${others.name}(${others.accountNumber})"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                    <div class="another-name">
                                        <a class="another-name" href="${depoInfoUrl}${others.id}">${others.depoAccount.agreementNumber}</a>
                                        <c:set var="state" value="${others.depoAccount.state}"/>
                                        <c:set var="className">
                                            <c:if test="${state eq 'closed'}">
                                                red
                                            </c:if>
                                        </c:set>

                                        <span class="${className}">
                                            <span class="prodStatus status" style="font-weight:normal;">
                                                <c:if test="${not empty className}">
                                                    <nobr>(${state.description})</nobr>
                                                </c:if>
                                            </span>
                                        </span>
                                    </div>
                                </div>
                            </c:forEach>
                            &nbsp;
                            <div class="clear"></div>
                        </div>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:if>
        <script type="text/javascript">
            initialize();
            showDivision('${form.accountDivision}');
        </script>
    </c:if>
    </tiles:put>
</tiles:insert>

</html:form>
