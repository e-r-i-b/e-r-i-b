<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<html:form action="/private/favourite/list/favouriteLinks" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="favouriteLinks" value="${form.favouriteLinks}"/>
    <c:set var="favouriteLinksCount" value="${phiz:size(favouriteLinks)}"/>
    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/async/favourite/save/favouriteLinks')}"/>
    <c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=/private/favourite/list/null')}"/>
    <c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

    <tiles:insert definition="favouriteList">
        <tiles:put name="data" type="string">
            <div id="favourites">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="data">
                        <div class="clear">&nbsp;</div>
                        <div class="titleItems float"><h1>Избранное</h1></div>

                        <div class="clear"></div>
                        <div class="paddBottom10 titleItems">
                            На этой странице Вы можете управлять списком избранных операций.
                        </div>
                        <div class="clear"></div>

                        <div class="favouriteList">
                            <input type="hidden" id="selectedIds" name="selectedIds"/>
                            <c:set var="urlForBusinessmanRegistration" value="${phiz:getBusinessmanRegistrationUrl()}"/>
                            <tiles:insert definition="simpleTableTemplate" flush="false">
                               <tiles:put name="grid">
                                    <c:if test="${not empty favouriteLinks}">
                                        <sl:collection  id="favourite" name="favouriteLinks" model="sort-list" styleClass="rowOver ${favouriteLinksCount==1 ? 'no-sort' : 'sort'}" indexId="ind">
                                            <c:set var="favouriteId"     value="${favourite.id}"/>
                                            <c:set var="favouriteName">
                                                <c:out value="${favourite.name}"/>
                                            </c:set>
                                            <c:set var="favouriteLink"   value="${favourite.link}"/>
                                            <c:set var="favouriteTypeLink"   value="${favourite.typeLink}"/>

                                            <html:hidden property="sortFavouriteLinks" value="${favouriteId}"/>
                                            <sl:collectionItem styleClass="align-left repeatLink sortableCells" styleTitleClass="sortableCells" title="">

                                                <img src="${globalImagePath}/slip.gif" class="slipImage">

                                                <c:choose>
                                                    <c:when test="${favouriteLink == urlForBusinessmanRegistration}">
                                                        <c:set var="favouriteLink"   value="openBusinessmanRegistrationWindow(event);"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="favouriteLink"   value="window.location = '${favouriteLink}'"/>
                                                    </c:otherwise>
                                                </c:choose>
                                                <span class="templateId sortableHiddenText" onclick="${favouriteLink}">
                                                    ${favouriteName}
                                                </span>
                                            </sl:collectionItem>
                                            <sl:collectionItem styleClass="align-right repeatLink3 sortableCells" title="">
                                                <c:if test="${favouriteTypeLink == 'AUTO'}">
                                                    <span class="autoFavouriteLinkText">
                                                        добавлена автоматически
                                                    </span>
                                                </c:if>
                                            </sl:collectionItem>
                                            <sl:collectionItem  styleClass="align-right listOfOperationWidth editColumn sortableCells" styleTitleClass="" name="" title="">
                                                <c:if test="${favouriteTypeLink == 'USER'}">
                                                    <tiles:insert definition="listOfOperation" flush="false">
                                                        <tiles:putList name="items">
                                                            <tiles:add><a onclick="openFavouriteLink('${favouriteId}', '${favouriteName}');">Переименовать</a></tiles:add>
                                                            <tiles:add>
                                                                 <tiles:insert definition="confirmationButton" flush="false">
                                                                    <tiles:put name="winId">confirmationRemoveLink${favouriteId}</tiles:put>
                                                                    <tiles:put name="title">Избранное</tiles:put>
                                                                    <tiles:put name="currentBundle">commonBundle</tiles:put>
                                                                    <tiles:put name="confirmCommandKey">button.remove</tiles:put>
                                                                    <tiles:put name="getConfirmMessageFunction">removeFavouriteLink('${favouriteId}')</tiles:put>
                                                                 </tiles:insert>
                                                            </tiles:add>
                                                        </tiles:putList>
                                                    </tiles:insert>
                                                </c:if>
                                            </sl:collectionItem>
                                        </sl:collection>
                                    </c:if>
                                </tiles:put>
                                <tiles:put name="isEmpty" value="${empty favouriteLinks}"/>
                                <tiles:put name="emptyMessage">
                                    <span class="normal">На данный момент у Вас нет ни одной избранной ссылки. Для того чтобы с любой страницы быстро переходить к нужной операции,
                                        на интересующей Вас странице нажмите на ссылку «Добавить в избранное» и она появится в Личном меню в разделе «Избранное».
                                    </span>
                                </tiles:put>
                            </tiles:insert>
                        </div>

                        <c:if test="${favouriteLinksCount>1}">
                            <div class="sortAutoPaymentAndTemplateText marginLeftFavourite">
                                Перемещайте элементы избранного в нужном Вам порядке
                            </div>
                        </c:if>
                    </tiles:put>
                 </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="favoriteLink"/>
        <tiles:put name="data">
            <h1>Избранное</h1>
            <br/>
            Для переименования операции Вам необходимо вести новое название. Затем нажмите кнопку &laquo;Применить&raquo;.
            <div>
                <div>
                    <div class="paymentInputDiv">
                        <fieldset>
                            <table>
                                    <tr>
                                        <td class="paymentTextLabel">
                                            Новое название: &nbsp;
                                        </td>
                                        <td class="align-left">
                                            <input type="text" id="nameFavouriteLink" name="nameFavouriteLink"  size="60" maxlength="300"/>
                                            <input type="hidden" id="nameFavouriteLinkHidden" name="nameFavouriteLinkHidden"/>
                                        </td>
                                    </tr>
                            </table>
                        </fieldset>
                    </div>
                </div>
                <div class="clear"></div>
            </div>

        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="win.close('favoriteLink')"/>
                <tiles:put name="viewType" value="buttonGrey"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="id" value="submitFavorite"/>
                <tiles:put name="commandKey" value="button.favourite.edit"/>
                <tiles:put name="commandTextKey" value="button.favourite.edit"/>
                <tiles:put name="commandHelpKey" value="button.favourite.edit.help"/>
                <tiles:put name="bundle" value="userprofileBundle"/>
            </tiles:insert>
        </div>
        </tiles:put>
    </tiles:insert>
</html:form>

<script type="text/javascript">
    try
    {
        $(document).ready(function(){

            $("#nameFavouriteLink").keyup(function(e){
                checkInputText();
            })
            .bind('paste', function(e){
                checkInputText();
            })
            .blur(function(e){
                checkInputText();
            });

            $( ".sort" ).sortable({
                items: "li:not(.listInfHeader)",
                helper : 'clone',
                axis: "y",
                cancel : ".text-highlight",
                containment: "parent",
                tolerance: "pointer",
                placeholder: "ui-state-highlight",
                start: function( event, ui ) {
                    ui.helper.find(".listOfOperation").mouseout();
                    ui.helper.css('cursor','move');
                },
                update: function( event, ui ) {
                    var parameters = "";
                    $(this).find("li:not(.listInfHeader) input[type='hidden']").each(function(index){
                        parameters += ((index != 0) ? "&sortFavouriteLinks=" : "sortFavouriteLinks=") + $(this).val();
                    });
                    ajaxQuery(parameters, '${url}', function(data){});
                }});
        });
    } catch (e) { }

    function openFavouriteLink(favouriteLinkId, favouriteLinkName)
    {
        setElement("selectedIds", favouriteLinkId);
        setElement("nameFavouriteLink", favouriteLinkName);
        setElement("nameFavouriteLinkHidden", favouriteLinkName);
        if(!$("#submitFavorite .buttonGreen").hasClass("disabled"))
            $("#submitFavorite .buttonGreen").addClass("disabled");
        $("#submitFavorite .buttonGreen")[0].onclick = null;
        win.open('favoriteLink');
    }

    function removeFavouriteLink(favouriteLinkId)
    {
        setElement("selectedIds", favouriteLinkId);
        return "Вы действительно хотите удалить избранную операцию?";
    }

    function openBusinessmanRegistrationWindow(event)
    {
       <c:if test="${not empty urlForBusinessmanRegistration}">
           var winparams = "resizable=1,menubar=0,toolbar=0,scrollbars=1";
           var pwin = openWindow(event, "${urlForBusinessmanRegistration}", "businessman", winparams);
           pwin.focus();
       </c:if>
    }
    
    function checkInputText()
    {
        if ($("#nameFavouriteLink").val() != '' && $("#nameFavouriteLink").val() != $("#nameFavouriteLinkHidden").val())
        {
            var submitFavorite = $('#submitFavorite')[0];
            $(submitFavorite).find('.buttonGreen').removeClass("disabled");
            submitFavorite.onclick = function(){findCommandButton('button.favourite.edit').click('', false);};
        }
        else
        {
            var submitFavorite = $('#submitFavorite')[0];
            $(submitFavorite).find('.buttonGreen').addClass("disabled");
            submitFavorite.onclick = null;
        }
    }

</script>