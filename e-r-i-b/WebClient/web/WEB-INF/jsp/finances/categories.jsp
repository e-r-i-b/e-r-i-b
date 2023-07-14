<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>

<%--

outcomeCategories - список расходных категорий
incomeCategories  - список доходных категорий

--%>
<c:set var="outcomeCategoriesSize" value="${fn:length(outcomeCategories)}"/>
<c:set var="incomeCategoriesSize" value="${0}"/>
<c:set var="editCategoryUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/finances/categories')}"/>

<div id="categoryList" class="hidableCategoriesOperationsData">

    <div id="outcomeCategories" class="operationCategoriesBlock">
        <c:set var="outcomeCategoriesColumnSize" value="${(outcomeCategoriesSize + 5)/2}"/>
        <c:if test="${outcomeCategoriesColumnSize < 5}">
            <c:set var="outcomeCategoriesColumnSize" value="${outcomeCategoriesSize}"/>
        </c:if>

        <div class="categoriesListLeftColumn">
            <c:set var="categoryListId" value="outcomeCategoryList"/>
            <c:set var ="categoriesList" value="${outcomeCategories}"/>
            <c:set var ="startIndex" value="0"/>
            <c:set var ="endIndex" value="${outcomeCategoriesColumnSize - 1}"/>
            <%@ include file="/WEB-INF/jsp/finances/categoriesListColumn.jsp" %>            
        </div>

        <div class="categoriesListRightColumn">
            <c:set var="categoryListId" value="outcomeCategoryList"/>
            <c:set var ="startIndex" value="${outcomeCategoriesColumnSize}"/>
            <c:set var ="endIndex" value="${outcomeCategoriesSize - 1}"/>
            <%@ include file="/WEB-INF/jsp/finances/categoriesListColumn.jsp" %>
        </div>
    </div>



    <%-- Всплывающее окно добавления категории --%>
    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="categoryDiv"/>
        <tiles:put name="data">
            <jsp:include page="/WEB-INF/jsp/finances/window/addCategory.jsp"/>
        </tiles:put>
    </tiles:insert>

    <%-- Всплывающее окно подтверждения удаления категории --%>
    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="confirmRemoveCategory"/>
        <tiles:put name="data">
            <jsp:include page="/WEB-INF/jsp/finances/window/confirmRemoveCategory.jsp"/>
        </tiles:put>
    </tiles:insert>

    <%-- Всплывающее окно подтверждения редактирования операций удаляемой категории --%>
    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="removeOperationsFromCategory"/>
        <tiles:put name="data">
            <jsp:include page="/WEB-INF/jsp/finances/window/removeOperationsFromCategory.jsp"/>
        </tiles:put>
    </tiles:insert>

    <script type="text/javascript">
        var operationCategories = {
            ERROR_DIV: "winErrors",
            ERROR_REMOVE_DIV: "winRemoveCategoryErrors",
            ajaxSended: false,
            currentRemovedCategoryKey: "",
            currentEditCategoryKey: -1,
            afterDeleteCallbackFunctions : [],
            afterAddCallbackFunctions : [],
            drawData :function()
            {                
                $('.operationCategoriesBlock').hide();
                this.selectCategoryList($('#lastShowCategory').val()+'Categories');
            },
            selectCategoryList: function(categoryListId)
            {
                var isVisible = $('#'+categoryListId).is(':visible');
                if(!isVisible)
                {
                    $('.operationCategoriesBlock').hide();
                    $('#'+categoryListId).show();
                    $('.greenSelector').addClass("transparent");
                    $('#'+categoryListId+'Selector').removeClass("transparent");
                }
            },
            openWindow: function (currentCategoryListId)
            {
                this.currentEditCategoryKey = -1;
                removeAllErrors(this.ERROR_DIV);
                this.ajaxSended = false;
                $('#winHeaderNew').show();
                $('#winHeaderEdit').hide();
                win.open('categoryDiv');

                $('input[name=field(name)]').val("");
            },
            openEditWindow: function (categoryId)
            {
                removeAllErrors(this.ERROR_DIV);
                this.ajaxSended = false;
                $('#winHeaderEdit').show();
                $('#winHeaderNew').hide();
                win.open('categoryDiv');
                this.currentEditCategoryKey = categoryId;
                $('input[name=field(name)]').val($('#categoryName'+categoryId).val());
            },
            getAddFilds: function ()
            {
                var name = $('input[name=field(name)]').val();
                return {name: name};
            },
            saveNewCategory: function ()
            {
                if(this.ajaxSended)
                    return;
                removeAllErrors(this.ERROR_DIV);
                var filds = this.getAddFilds();
                if ( trim (filds.name) == '')
                {
                    addError("Пожалуйста, укажите название категории операции.", this.ERROR_DIV, true);
                    return;
                }
                var params = {};
                if (this.currentEditCategoryKey > 0)
                    params['id'] = this.currentEditCategoryKey;
                params['field(name)'] = filds.name;
                params['operation'] = "button.add";
                var myself = this;
                this.ajaxSended = true;
                ajaxQuery(convertAjaxParam (params), '${editCategoryUrl}',
                    function(data) {
                        data = trim(data);
                        //если вернулась пустая строка, то вероятнее всего произошл тайм аут сессии, перезагружаем страницу
                        if (data == '')
                        {
                            window.location.reload();
                        }
                        var message = $(data).find('#messages');
                        if(message.length != 0)
                        {
                            removeAllErrors(myself.ERROR_DIV);
                            addError(message.html(), myself.ERROR_DIV, true);
                        }
                        var categoryResult = $(data).find('tr');
                        if(categoryResult.length != 0)
                        {
                            var categoryList;
                            myself.addCategoryOnPage("outcome", myself.currentEditCategoryKey, categoryResult);
                            var categoryId = myself.currentEditCategoryKey > 0 ? myself.currentEditCategoryKey : categoryResult.attr('id').replace('category', "");
                            var info = {categorySumm:0,categoryName:filds.name, categoryId:categoryId};
                            myself.afterAddCallback(info);
                            myself.currentEditCategoryKey = -1;
                            for(var i = 0; i < editOperation.operations.length;i++)
                            {
                                editOperation.operations[i].categories.push({title:filds.name, id:categoryId});
                            }
                            win.close('categoryDiv');
                        }
                        myself.ajaxSended = false;
                    }
                );
            },
            addCategoryOnPage: function(incomeType, id, categoryResult)
            {
                var categoryList = $('#' + incomeType + 'CategoryList');
                if (id > 0)
                {
                    $('#category'+id).replaceWith(categoryResult);
                }
                else
                    categoryList.append(categoryResult);
                $(categoryResult).find('span').breakWords();
            },
            openConfirmRemoveCategoryWindow: function(id)
            {
                this.currentRemovedCategoryKey = id;

                var categoryNameSpan = $('#removedCategoryName');
                categoryNameSpan.text($('#categoryName'+id).val());
                categoryNameSpan.breakWords();
                win.open('confirmRemoveCategory');
            },
            deleteCategory: function()
            {
                if (this.ajaxSended)
                    return;
                removeAllErrors(this.ERROR_REMOVE_DIV);
                var params = {};
                params['id'] = this.currentRemovedCategoryKey;
                params['operation'] = "button.delete";
                var myself = this;
                this.ajaxSended = true;
                ajaxQuery(convertAjaxParam (params), '${editCategoryUrl}', function(data) {
                    data = trim(data);
                    //если вернулась пустая строка, то вероятнее всего произошл тайм аут сессии, перезагружаем страницу
                    if (data == '')
                    {
                        window.location.reload();
                    }
                    var message = $(data).find('#messages');
                    if(message.length != 0)
                    {
                        myself.ajaxSended = false;
                        removeAllErrors(myself.ERROR_REMOVE_DIV);
                        addError(message.html(), myself.ERROR_REMOVE_DIV, true);
                    }
                    else
                    {
                        var removedCategoryId = $(data).find('#removedCategoryId').html();
                        var operationState = $(data).find('#removedOperationState').text();
                        if(operationState == 'ExistOperationsInCategory')
                        {
                            operationCategories.currentRemovedCategoryKey = removedCategoryId;
                            win.close('confirmRemoveCategory');
                            win.open('removeOperationsFromCategory');
                        }
                        else
                        {
                            $('#category'+removedCategoryId).remove();
                            for(var i = 0; i < editOperation.operations.length;i++)
                            {
                                for(var j = 0; j < editOperation.operations[i].categories.length;j++)
                                    if (editOperation.operations[i].categories[j].id == removedCategoryId)
                                    {
                                        editOperation.operations[i].categories.splice(j,1);
                                        break;
                                    }
                            }
                            myself.afterDeleteCallback({'categoryId':removedCategoryId});
                            win.close('confirmRemoveCategory');
                        }
                    }
                    myself.ajaxSended = false;
                });
            },
            showAbstractCategory: function()
            {
                var url = "${phiz:calculateActionURL(pageContext,'/private/finances/categoryOperationsAbstract.do')}";
                window.location = url + "?categoryId=" + this.currentRemovedCategoryKey;
            },
            /**
            * Колбек, выполняемый после добавления категории.
            * @param callBackParams параметры
            */
           afterAddCallback: function(callBackParams)
           {
               for (var i = 0; i < this.afterAddCallbackFunctions.length; i++)
               {
                   this.afterAddCallbackFunctions[i](callBackParams);
               }
           },
           /**
            * Колбек, выполняемый после удаления категории.
            * @param callBackParams параметры
            */
           afterDeleteCallback: function(callBackParams)
           {
                for (var i = 0; i < this.afterDeleteCallbackFunctions.length; i++)
                {
                    this.afterDeleteCallbackFunctions[i](callBackParams);
                }
           },
           /**
            * Добавить каллбек функцию в массив
            * @param func - функция
            */
           addAfterCreateCallbackFunction : function(func)
           {
               this.afterAddCallbackFunctions.push(func);
           },
          /**
           * Добавить каллбек функцию в массив
           * @param func - функция
           */
           addAfterDeleteCallbackFunction : function(func)
           {
                this.afterDeleteCallbackFunctions.push(func);
           }
        };
    </script>
</div>