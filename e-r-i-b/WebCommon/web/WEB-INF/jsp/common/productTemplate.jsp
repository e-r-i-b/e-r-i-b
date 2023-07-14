<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<tiles:importAttribute/>

<%--
  _____________________________________________________________________________
 |           |  title/imgStatus                          |     centerData      |
 |           |___________________________________________|_____________________|
 |   img     | leftData                                  |       rightData     |
 |           |_________________________________________________________________|
 |product    |                                                                 |
 |Img        |                                                                 |
 |Additional |                                                                 |
 |Data       |                 additionalData                                  |
 |           | show in main                                                    |
 |___________|_________________________________________________________________|
 |                                                                             |
 | bottomData                                                                  |
 |_____________________________________________________________________________|

  Шаблон для отображения карточки продукта
   id                   - id продукта 
   img                  - относительный путь до картинки (при необходимости)
   productImgAdditionalData - данные отображаемые под картинкой продукта
   alt                  - alt текст картинки (при необходимости)
   title                - название (заголовок) продукта
   comment              - комментарий к заголовку
   rightComment         - комментарий справа от заголовка
   src                  - ссылка на детальную информацию о продукте (при необходимости)
   needClick            - необходимо ли делать кликабельной всю область продукта (по умолчанию true)
   showInMainCheckBox   - отображать ли чекбокс показа страницы на главной (по умолчанию false)
   inMain               - отображается ли продукт на главной Необходимо для индикации галкой (по умолчанию false)
   leftData             - левая часть данных
   centerData           - центральная часть данных
   rightData            - правая часть данных (при отсутствии колонка скрывается, по умолчанию скрывается)
   additionalData       - дополнительные данные, которые необходимо отображать под основными
   bottomData           - нижняя часть данных (при отсутствии колонка скрывается)
   imgStatus            - статус отображаемый под картинкой
   productType          - тип продукта (карта/счет/кредит и т.д.)
   status               - статус продукта:
                            active    - продукт активен (зеленый цвет шаблона)
                            inActive  - продукт не активен (серый цвет шаблона)
                            error     - продукт содержит ошибки (красный цвет шаблона)
   productViewBacklight - необходимо ли подсвечивать продукт
   titleClass           - определение стилей заголовков продуктов
   rightBlockId         - id правого блока(для программы лояльности)
   Чтобы обозначить цветом часть текста данных в зависимости от статусса необходимо кусок текста
   заключить в тег `span` или `a` и присвоить ему класс prodStatus
   operationsBlockPosition - позиция блока операций (topPosition - для страниц со списком продуктов, rightPosition - для страниц детальной информации по продуктам)
   additionalProductInfo   - дополнительная информация по продукту
   productNumbers           - Номер продукта
   productNumbersClass      - Cтиль номера продукта
   additionalProductClass   - Определение стилей для дополнительных данных по продукту
   additionalCommentClass   - Определение дополнительных стилей для комментария к продукту
--%>

<c:if test="${img != ''}">
    <c:set var="img"><img src="${img}" alt="${alt}" onerror="onImgError(this)" border="0"/></c:set>
</c:if>

<c:if test="${not needClick}">
    <c:set var="disableRef">onclick="return false;"</c:set>
</c:if>
<c:if test="${not empty src and needClick}">
    <c:set var="onClick">onclick="showFullInfo('${src}');"</c:set>
</c:if>
<c:set var="titleBlock" value="${title}"/>
<c:choose>
    <c:when test="${src != ''}">
        <c:set var="titleBlock"><a  href="${src}" ${disableRef}><span class="${titleClass}" ${onClick}>${title}</span></a></c:set>
        <c:set var="img"><a href="${src}" ${disableRef}>${img}</a></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="titleBlock"><span class="${titleClass}">${title}</span></c:set>
    </c:otherwise>
</c:choose>

<script type="text/javascript">
    function showFullInfo(url)
    {
        window.location = url;
    }
</script>
<c:set var="operationsBlock">
    <div class="productRight">
        <c:choose>
            <c:when test="${notificationButton == 'true'}">
                <div class="buttonSelect notificationButton" id="${id}">
                        <div class="buttonSelectLeft"></div>
                        <div class="buttonSelectCenter">
                            напоминать
                        </div>
                        <div class="buttonSelectRight"></div>
                        <div class="clear"></div>
                </div>
            </c:when>
            <c:otherwise>
                <span class="prodStatus">${rightData}</span>
            </c:otherwise>
        </c:choose>
    </div>
    <c:set var="rightClass" value="productRightMargin" />
</c:set>

<div class="forProductBorder ${additionalProductClass}">
    <div class="productCover ${status}Product">
        <c:set var="allAboutProductClass" value="all-about-product"/>
        <c:if test="${arrow eq 'true'}">
            <c:set var="allAboutProductClass" value="all-about-product-with-arrow"/>
        </c:if>
        <%-- img --%>
        <div class="pruductImg <c:if test="${arrow eq 'true'}">additionalProductImg</c:if>">
            ${img}
            <c:if test="${not empty productImgAdditionalData}">${productImgAdditionalData}</c:if>
        </div>

        <div class="${allAboutProductClass}">
           <%-- title --%>
               <div class="productTitle">
                   <div class="productTitleText ${showInMainCheckBox?'mainCheckBox':''} ${showInThisWidgetCheckBox?'thisWidgetCheckBox':''}">
                       <table width="100%">
                           <tr>
                               <td class="alignTop">
                                   <div class="productName">
                                       <div class="titleName">
                                           <div class="description">
                                               <c:if test="${rightComment == 'false'}">${comment}</c:if>
                                           </div>
                                           <div class="clear"></div>
                                           <div class="relative float">
                                               <span class="relative titleBlock" title="${fn:trim(title)}">
                                                   ${titleBlock}
                                                   <div class="lightness" ${onClick}></div>
                                               </span>&nbsp;
                                               <c:if test="${rightComment == 'true'}">
                                                    <div class="description descriptionRight ${additionalCommentClass}">${comment}</div>
                                               </c:if>
                                           </div>
                                       </div>
                                       <c:if test="${!empty additionalProductInfo}">
                                           <table>
                                               <tr>
                                                   <td>
                                                       ${additionalProductInfo}
                                                   </td>
                                               </tr>
                                           </table>
                                       </c:if>
                                   </div>
                               </td>
                               <td class="productButtonsAndOperations">
                                    <table class="floatRight" id="${rightBlockId}">
                                        <tr>
                                            <td>
                                                <div class="productAmount">
                                                    <div class="productCenter">${centerData}</div>
                                                </div>
                                            </td>
                                            <td class="alignMiddle">
                                                <c:set var="rightClass" value="" />
                                                <c:if test="${rightData != '' && operationsBlockPosition == 'topPosition'}">
                                                    ${operationsBlock}
                                                </c:if>
                                            </td>
                                        </tr>
                                    </table>
                               </td>
                           </tr>
                       </table>
                       <div class="${productNumbersClass}">
                            ${productNumbers}
                       </div>
                   </div>
               </div>

            <%-- rightData --%>
            <c:set var="rightClass" value="" />
            <c:if test="${rightData != '' && operationsBlockPosition == 'rightPosition'}">
                ${operationsBlock}
            </c:if>
            <%-- leftData, centerData, bottomData --%>
            <div class="productCenterContainer ${rightClass}">
                <div class="productCDL">
                    <div class="productLeft">${leftData}</div>
                </div>
                <div class="clear"></div>
                <c:if test="${showInMainCheckBox}">
                    <c:set var="checked" value=""/>
                    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/async/product/display/change')}"/>
                    <c:if test="${inMain}">
                        <c:set var="checked" value="checked"/>
                    </c:if>
                    <div class="productShowInMain">
                        <input type="checkbox"  name="showInMain" id="showinMain" onclick="changeShowInMain('${id}', '${productType}', '${url}'); cancelBubbling(event);" ${checked}/>
                        <label for="showinMain"><bean:message bundle="commonBundle" key="label.showInMain"/></label>
                    </div>
                </c:if>
                <c:if test="${showInThisWidgetCheckBox}">
                    <c:set var="checked" value=""/>
                    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/async/product/display/change')}"/>
                    <div class="productShowInThisWidget">
                        <input type="checkbox" name="showInThisWidget" onclick="cancelBubbling(event);" value="${id}"/>
                        <bean:message bundle="commonBundle" key="label.showInWidget"/>
                    </div>
                </c:if>
                <div class="clear"></div>
            </div>
            <div class="clear"></div>
           <c:if test="${not empty additionalData}">
                <div class="additionalProductData">${additionalData}</div>
            </c:if>
            <div class="productBottom" >
               ${bottomData}
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>

