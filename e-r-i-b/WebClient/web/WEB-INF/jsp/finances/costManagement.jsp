<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="curType"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(typeDate)"/></c:set>
<div id="costList" class="hidableCategoriesOperationsData">
<c:choose>
    <c:when test="${curType != 'month' || (not empty frm.selectedId && frm.selectedId != 'allCardsAndCash' && fn:length(frm.node.allIdentifiers) > 1)}">
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="greenBold"/>
            <tiles:put name="data">
                <span class="text-dark-gray normal relative">
                    Пожалуйста, укажите месяц, за который Вы хотите просмотреть расходы, и выберите из списка продуктов значение «Все карты и наличные».
                </span>
            </tiles:put>
        </tiles:insert>
    </c:when>
    <c:otherwise>
        <script type="text/javascript">

             var costManagment = {
                totalBudget : 0,
                totalAmount : 0,
                budgetList : [],
                init : function()
                {
                    <c:forEach items="${operations}" var="operation">
                        <c:set var="budgetSum" value="0"/>
                        <c:if test="${operation.budgetSum != null}">
                            <c:set var="budgetSum" value="${operation.budgetSum}"/>
                        </c:if>
                        <c:set var="categoryName">
                            <c:out value="${operation.name}"/>
                        </c:set>
                        var budget = {
                            categoryId : ${operation.id},
                            categorySumm: Math.abs(${operation.categorySum}),
                            categoryBudget: ${budgetSum},
                            categoryName : "${phiz:escape(categoryName)}",
                            show : ${operation.budgetSum != null && (!operation.avgBudget || operation.budgetSum != 0)}
                        };
                        this.budgetList.push(budget);
                    </c:forEach>
                    editOperation.addCallbackFunction(this.updateBudgetList);
                    if(typeof operationCategories != "undefined")
                    {
                        operationCategories.addAfterCreateCallbackFunction(this.addOrUpdateCategory);
                        operationCategories.addAfterDeleteCallbackFunction(this.deleteThermometerOrButton);
                    }
                },
                calcTotalAmount : function()
                {
                    this.totalBudget = 0;
                    this.totalAmount = 0;
                    for(var i=0; i<this.budgetList.length; i++)
                    {
                        var budget = this.budgetList[i];
                        /*Общий бюджет считаем только для отображаемых категорий*/
                        if(budget.show)
                        {
                            this.totalBudget = this.totalBudget + budget.categoryBudget;
                            this.totalAmount = this.totalAmount + budget.categorySumm;
                        }
                    }
                },
                drawBudgetList: function()
                {
                    for(var i=0; i<this.budgetList.length; i++)
                    {
                        var budget = this.budgetList[i];
                        if(!budget.show)
                            continue;
                        this.drawNewBudget(budget);
                    }
                },
                redrawBudgetList: function()
                {
                    for(var i=0; i<this.budgetList.length; i++)
                    {
                        var budget = this.budgetList[i];
                        if(!budget.show)
                            continue;
                        this.redrawThermometerForBudget(budget);
                        var thermometer = $('#thermometer'+budget.categoryId).parents('.budgetScale');
                        var rightData = thermometer.find(".thermometerRightData span");
                        rightData.text(FloatToString(budget.categoryBudget, $('#ShowCents').is(':checked') ? 2 : 0, ' ', 21, RoundingMode.DOWN));
                    }
                    this.drawTotalThermometer()
                },
                /*Функция по добавлению нового бюджета*/
                drawNewBudget : function(budget, addNew)
                {
                    var thermometer = createTermometer(budget.categoryId, budget.categorySumm, budget.categoryBudget, budget.categoryName);
                    $('#budgetScalesBlock').append(thermometer);
                    this.redrawThermometerForBudget(budget);
                    $(thermometer).find('.thermometerLeftData span').breakWords();
                    $(thermometer).find('.budgetValue').addClass('moneyField');
                    initMoneyFields($('#thermometer'+budget.categoryId).closest('.categoryThermometer'));
                    if(addNew)
                    {
                        var rightData = $(thermometer).find('.thermometerRightData');
                        rightData.find('.editBudgetData').hide();
                        rightData.find('.editableBudgetData').show();
                        $(thermometer).find('.budgetValue').val('');
                        $(thermometer).find('.budgetValue').focus();
                    }
                },
                /*Функция по обновлению категории "Всего" актуальными данными*/
                drawTotalThermometer : function()
                {
                    var totalThermometer = $('#budgetScaleTotal');
                    var rightData = totalThermometer.find(".thermometerRightData span");
                    rightData.text(FloatToString(this.totalBudget, $('#ShowCents').is(':checked') ? 2 : 0, ' ', 21, RoundingMode.DOWN));
                    var maxAmount = Math.max(this.totalAmount,this.totalBudget);
                    var thermometerColor = 'green';
                    if(this.totalAmount > this.totalBudget)
                        thermometerColor = 'red';
                    var totalThermometerJS = thermometerManager.getThermometer('total');
                    if(totalThermometerJS)
                    {
                        totalThermometerJS.setValue(this.totalAmount);
                        totalThermometerJS.setMaxValue(maxAmount);
                        totalThermometerJS.setColor(thermometerColor);
                    }
                    else
                    {
                        totalThermometerJS = new Thermometer('total', this.totalAmount, maxAmount, true,thermometerColor);
                        thermometerManager.addThermometer(totalThermometerJS);
                    }

                    totalThermometerJS.calculateWidths();
                    totalThermometerJS.scale();
                    totalThermometerJS.redraw();
                },
                /*Функция по перерисовке градусника для бюджета*/
                redrawThermometerForBudget : function(budget)
                {
                    var thermometerJS = thermometerManager.getThermometer(budget.categoryId);
                    if(!thermometerJS)
                    {
                        thermometerJS = new Thermometer(budget.categoryId, 0, 100);
                        thermometerManager.addThermometer(thermometerJS);
                    }
                    thermometerJS.calculateWidths();
                    thermometerJS.scale();
                    var max = (budget.categoryBudget > budget.categorySumm ? budget.categoryBudget : budget.categorySumm);
                    if(budget.categorySumm > budget.categoryBudget)
                        thermometerJS.setColor('red');
                    else
                        thermometerJS.setColor('green');

                    thermometerJS.setValue(budget.categorySumm);
                    thermometerJS.setMaxValue(max);
                    thermometerJS.redraw(budget.categoryId, budget.categorySumm, max);
                },
                getBudgetByCategoryId : function(categoryId)
                {
                    for(var i=0; i<this.budgetList.length; i++)
                    {
                        if(this.budgetList[i].categoryId == categoryId)
                            return this.budgetList[i];
                    }
                    return null;
                },
                /*Функция для удаления бюджета со страницы*/
                deleteBudget : function(budget)
                {
                    this.totalBudget = this.totalBudget - budget.categoryBudget;
                    this.totalAmount = this.totalAmount - budget.categorySumm;
                    budget.show = false;
                    this.drawTotalThermometer();
                },
                addCategoryAmount : function(categoryId, amount)
                {
                    var budget = this.getBudgetByCategoryId(categoryId);
                    budget.categorySumm = budget.categorySumm + amount;
                    this.redrawThermometerForBudget(budget);
                },
                /**
                 * Создает кнопку в области "Управление категориями по информации, в которой должны содержаться следующие значения:
                 * идентификатор категории, название категории, сумма операций по данной категории
                 **/
                addCateroryButton : function(info)
                {
                    var newCategoryButton = $($('#defaultAddButton').html());
                    newCategoryButton.find(".categoryId").val(info.categoryId);
                    newCategoryButton.find(".categorySumm").val(info.categorySumm);

                    var categoryName = newCategoryButton.find(".buttonText span");
                    categoryName.html(info.categoryName);
                    if (info.categorySumm > 0)
                        categoryName.addClass("bold");
                    categoryName.breakWords();

                    $('#newCategoryButtons').append(newCategoryButton);
                    costManagment.showHideAddCategoryBlock();
                },
                addOrUpdateCategory : function(info)
                {
                    var newCategoryButton = $('.addNewCategoryButton:visible .categoryId[value='+info.categoryId+']').parent('.addNewCategoryButton');
                    var isNewButton = newCategoryButton.length <= 0;
                    var budgetCategory = costManagment.getBudgetByCategoryId(info.categoryId);
                    if(budgetCategory == null || !budgetCategory.show)
                        costManagment.addOrUpdateButton(info, isNewButton, newCategoryButton);
                    if(budgetCategory != null)
                        costManagment.updateBudgetName(info, budgetCategory);
                    costManagment.showHideAddCategoryBlock();
                },
                addOrUpdateButton: function(info, isNew, newCategoryButton)
                {
                    if(isNew)
                    {
                        newCategoryButton = $($('#defaultAddButton').html());
                        var budget = {
                            categoryId : info.categoryId,
                            categorySumm: Math.abs(info.categorySumm),
                            categoryBudget: 0,
                            categoryName : info.categoryName,
                            show : false
                        };
                        this.budgetList.push(budget);
                    }

                    newCategoryButton.find(".categoryId").val(info.categoryId);
                    newCategoryButton.find(".categorySumm").val(info.categorySumm);

                    var categoryName = newCategoryButton.find(".buttonText span");
                    categoryName.text(info.categoryName);
                    if (info.categorySumm > 0)
                        categoryName.addClass("bold");
                    categoryName.breakWords();

                    if(isNew)
                    {
                        $('#newCategoryButtons').append(newCategoryButton);
                    }
                },
                updateBudgetName: function(info, budgetCategory)
                {
                    budgetCategory.categoryName = info.categoryName;
                    var t = $('#thermometer'+info.categoryId).parent('.categoryThermometer');
                    t.find('.thermometerLeftData span').text(info.categoryName);
                },
                deleteThermometerOrButton: function(data)
                {
                    if(data.categoryId)
                    {
                        $('.addNewCategoryButton:visible .categoryId[value='+data.categoryId+']').parent('.addNewCategoryButton').remove();
                        $('#thermometer'+data.categoryId).parents('.budgetScale').remove();
                    }
                },
                showHideAddCategoryBlock : function()
                {
                    if ($('.addNewCategoryButton').length > 1)
                        $('.addNewBudgetCategory').show();
                    else
                        $('.addNewBudgetCategory').hide();
                },
                 /**
                  * Обновить список бюджетов категорий после редактирования списка операций
                  * @param data - параметры новых операций после редактирования
                  */
                updateBudgetList : function(data)
                {
                    for(var i = 0; i < data.newOperations.length; i ++)
                    {
                        var operation = data.newOperations[i];
                        costManagment.addCategoryAmount(operation.categoryId,-operation.sum);
                    }
                    costManagment.addCategoryAmount(data.oldOperation.categoryId,-data.oldOperation.sum);
                    costManagment.addCategoryAmount(data.newOperation.categoryId,-data.newOperation.sum);
                    costManagment.calcTotalAmount();
                    costManagment.drawTotalThermometer();
                }
            };

            $('#ShowCents').bind('click',
                 function()
                 {
                     if(!redirectResolved())
                         return;
                     costManagment.redrawBudgetList();
                  }
            );

            function saveBudget(elem, event)
            {
                var parent = $(elem).closest('.forProductBorder');
                $(parent).removeClass('budgetError');
                $(parent).find('.budgetErrorText').text('').hide();

                var rightData = $(elem).parents('.thermometerRightData:first');
                var categoryId = rightData.find('#categoryId').val();
                var summ = rightData.find('input:last').val();

                if (trim(summ) == '')
                {
                    $(parent).addClass('budgetError');
                    $(parent).find('.budgetErrorText').text('Пожалуйста, введите сумму планируемых расходов по категории.').show();
                    cancelBubbling(event);
                    return;
                }

                var params = 'operation=button.save';
                params += '&id=' + categoryId;
                params += '&field(budgetSumm)=' + summ;
                params += '&field(openPageDate)=' + '${openPageDate}';
                params += '&field(selectedCardIds)=' + '${selectedCardIds}';
                ajaxQuery (params, "${phiz:calculateActionURL(pageContext, '/private/async/budget/save')}",
                        function(data){
                            if(data != undefined && data.budgetSumm != undefined){
                                var budget = costManagment.getBudgetByCategoryId(categoryId);
                                budget.categoryBudget = data.budgetSumm;
                                budget.show = true;
                                costManagment.calcTotalAmount();
                                costManagment.drawTotalThermometer();
                                costManagment.redrawThermometerForBudget(budget);
                                rightData.find("span:first").text(FloatToString(budget.categoryBudget, $('#ShowCents').is(':checked') ? 2 : 0, ' ', 21, RoundingMode.DOWN));
                            }
                            rightData.find('.editableBudgetData').hide();
                            rightData.find('.editBudgetData').show();

                        },
                        "json", true);
                cancelBubbling(event);
            }

            var currentRemoveBudgetId = -1;
            function confirmRemoveBudget(elem, event)
            {
                var rightData = $(elem).parents('.thermometerRightData:first');
                var name = $(elem).closest('.categoryThermometer').find('.thermometerLeftData span').text();
                currentRemoveBudgetId = rightData.find('#categoryId').val();

                $('#removedBudgetCategoryName').text(name);
                win.open('confirmRemoveBudgetCategory');
                cancelBubbling(event);
            }

            function deleteBudget()
            {
                var params = 'operation=button.delete';
                params += '&id=' + currentRemoveBudgetId;
                params += '&field(openPageDate)=' + '${openPageDate}';
                params += '&field(selectedCardIds)=' + '${selectedCardIds}';

                ajaxQuery (params, "${phiz:calculateActionURL(pageContext, '/private/async/budget/save')}",
                        function(data){
                            var budget = costManagment.getBudgetByCategoryId(currentRemoveBudgetId);
                            costManagment.deleteBudget(budget);
                            $('#thermometer'+currentRemoveBudgetId).parents('.budgetScale:first').remove();
                            costManagment.addCateroryButton(budget);
                            win.close('confirmRemoveBudgetCategory');
                        },
                        null, true);
            }

            function rollBackBudget(elem, event)
            {
                var parent = $(elem).closest('.forProductBorder');
                $(parent).removeClass('budgetError');
                $(parent).find('.budgetErrorText').text('').hide();

                var rightData = $(elem).parents('.thermometerRightData:first');
                rightData.find('.editableBudgetData').hide();
                rightData.find('.editBudgetData').show();
                var summ = rightData.find('span:first').text();
                rightData.find('input:last').val(summ);
                cancelBubbling(event);
            }

            function editBudget(elem, event)
            {
                var rightData = $(elem).parents('.thermometerRightData:first');
                rightData.find('.editBudgetData').hide();
                rightData.find('.editableBudgetData').show();
                cancelBubbling(event);
            }

            $(document).ready(function(){
                var budgetScales = $('.budgetScale');

                budgetScales.live("mouseenter",
                        function(){
                            $(this).find('.deleteBudget').show();
                            $(this).find('.editBudget').show();
                });

                budgetScales.live("mouseleave",
                        function(){
                            $(this).find('.deleteBudget').hide();
                            $(this).find('.editBudget').hide();
                });

                $('.addNewCategoryButton').live('click', function(){
                    if(!redirectResolved())
                        return;
                    var categoryId = $(this).find('.categoryId').val();
                    var summ = $(this).find('.categorySumm').val();
                    var date = $('[name=filter(toDate)]').val();
                    var button= $(this);
                    var params = 'operation=button.save&id=' + categoryId;
                    params += '&field(budgetSumm)='+summ;
                    params += '&field(date)='+ date;
                    params += '&field(openPageDate)=' + '${openPageDate}';
                    params += '&field(selectedCardIds)=' + '${selectedCardIds}';
                    ajaxQuery (params, "${phiz:calculateActionURL(pageContext, '/private/async/budget/save')}",
                            function(data){
                                if(data == undefined)
                                    return;

                                var budget = costManagment.getBudgetByCategoryId(categoryId);
                                budget.show = true;
                                budget.categoryBudget = data.budgetSumm;
                                costManagment.drawNewBudget(budget, true);
                                costManagment.calcTotalAmount();
                                costManagment.drawTotalThermometer();
                                button.remove();
                                costManagment.showHideAddCategoryBlock();
                            },
                            "json", true);

                });

                var budgetOperationsAjaxLoading = false;
                budgetScales.live('click', function(){
                    if(!redirectResolved())
                        return;
                    var elem = $(this);
                    var categoryId = $(this).find('#categoryId').val();
                    var operationsBlockId = 'costOperations';
                    var onlyAvailableCategories = false;
                    if(categoryId == undefined || categoryId == null)
                    {
                        operationsBlockId += 'Total';
                        onlyAvailableCategories = true;
                    }
                    else
                    {
                        operationsBlockId += categoryId;
                    }
                    if($('#'+operationsBlockId).is(':visible'))
                    {
                        $('#'+operationsBlockId).hide();
                        return;
                    }
                    if (budgetOperationsAjaxLoading)
                        return;
                    budgetOperationsAjaxLoading = true;
                    financesUtils.getOperationsToCategories(operationsBlockId,
                                    {
                                        category: categoryId,
                                        onlyAvailableCategories: onlyAvailableCategories,
                                        clickElem : elem,
                                        openPageDate: '${openPageDate}',
                                        selectedCardIds: '${selectedCardIds}'
                                    },
                                    function()
                                    {
                                        budgetOperationsAjaxLoading = false;
                                    });

                });

                $('.operationsList, .emptyText').live('click', function(e){
                    preventDefault(e);
                    return false;
                });

                costManagment.init();
                costManagment.showHideAddCategoryBlock();
                costManagment.calcTotalAmount();
                costManagment.drawTotalThermometer();
                costManagment.drawBudgetList();
            });

            function createTermometer(id, summ, budget, leftValue)
            {
                var thermometer = $($('#defaultThermometer').html());
                thermometer.find("#thermometer0").attr('id', 'thermometer'+id);
                thermometer.find(".dribbleHint .dribbleCenter span").text(FloatToString(summ, 2, ' '));
                thermometer.find(".thermometerLeftData span").html(leftValue);

                var rightData = thermometer.find(".thermometerRightData");
                rightData.find('#categoryId').val(id);
                rightData.find('#categorySum').val(budget);
                rightData.find('.budgetValue').val(budget);
                rightData.find('.editBudgetData span').text(FloatToString(budget, $('#ShowCents').is(':checked') ? 2 : 0, ' ', 21, RoundingMode.DOWN));

                thermometer.find('#costOperations').attr('id','costOperations'+id);
                thermometer.find('#listcostOperations').attr('id','listcostOperations'+id);

                return thermometer;
            }

            function closeBudgetOperations(elem, event)
            {
                var id = $(elem).closest('.operationsListBlock').attr('id');
                closeOperations(id);
                cancelBubbling(event);
            }

            function redirectResolved()
            {
                var editable = $(".editableBudgetData:visible");
                if (editable.length == 0)
                    return true;

                $("#editableCategory").html(editable.parents(".categoryThermometer").find('.thermometerLeftData span').text());
                win.open('redirectRefused');
                return false;
            }

            function saveCurrent(event)
            {
                win.close('redirectRefused');
                $(".editableBudgetData:visible").each(function(event){
                   saveBudget(this,event);
               });
            }
        </script>


        <div class="budgetScale" id="budgetScaleTotal">
            <tiles:insert definition="thermometerTemplate" flush="false">
                <tiles:put name="id" value="total"/>
                <tiles:put name="value" value="10"/>
                <tiles:put name="leftData" value="Всего"/>
                <tiles:put name="maxValue" value="1000"/>
                <tiles:put name="rightData">
                    <div class="editBudgetData">
                        <span>0</span> руб.
                    </div>
                </tiles:put>
                <tiles:put name="clazz" value="categoryThermometer"/>
            </tiles:insert>
            <div id="costOperationsTotal" class="operationsListBlock" style="display:none">
                <div id="listcostOperationsTotal" class="operationsList">
                </div>
                <div class="align-center marginBottom5">
                    <a href="#" onclick="if(!redirectResolved()) return false;closeBudgetOperations(this, event); return false;">свернуть</a>
                </div>
            </div>
        </div>
        <div class="productDivider"></div>

        <div id="budgetScalesBlock"></div>
        <div id="defaultThermometer" style="display:none;">
            <div class="budgetScale">
                <div class="forProductBorder showLight">
                    <div class="productCover activeProduct">
                        <div class="budgetErrorText" style="display: none;"></div>
                        <c:set var="rightData">
                            <input type="hidden" id="categoryId" value="0">
                            <input type="hidden" id="categorySum" value="0">
                            <div class="editBudgetData text-green">
                                <div class="float underline">
                                    <span>0</span> руб.
                                </div>
                                <div class="deleteBudget" style="display:none" onclick="if(!redirectResolved()) return false;confirmRemoveBudget(this, event);"></div>
                                <a class="editBudget" style="display:none" onclick="if(!redirectResolved()) return false;editBudget(this, event);"></a>
                            </div>
                            <div class="editableBudgetData" style="display:none">
                                <div class="rollBackBudgetButton" onclick="rollBackBudget(this, event);"></div>
                                <input type="text" size="10" class="budgetValue" value="0" onclick="cancelBubbling(event);" maxlength="11">
                                <div class="saveBudgetButton" onclick="saveBudget(this, event);"></div>
                            </div>
                        </c:set>
                        <tiles:insert definition="thermometerTemplate" flush="false">
                            <tiles:put name="id" value="0"/>
                            <tiles:put name="value" value="-1"/><%-- Значение по умолчанию для правильной отрисовки капельки(обязательно отрицательное) --%>
                            <tiles:put name="leftData"><span class="text-green underline"></span></tiles:put>
                            <tiles:put name="maxValue" value="100"/>
                            <tiles:put name="rightData" value="${rightData}"/>
                            <tiles:put name="clazz" value="categoryThermometer"/>
                        </tiles:insert>
                        <div id="costOperations" class="operationsListBlock" style="display:none">
                            <div id="listcostOperations" class="operationsList">
                            </div>
                            <div class="align-center marginBottom5">
                                <a href="#" onclick="if(!redirectResolved()) return false;closeBudgetOperations(this, event); return false;">свернуть</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="productDivider"></div>
            </div>
        </div>

        <div class="addNewBudgetCategory">
             <h1 class="decoration-none noBg grayText">
                Добавить категорию
             </h1>
             <div class="hint"></div>
        </div>

        <div id="newCategoryButtons">
            <c:forEach items="${operations}" var="operation">
                <c:if test="${operation.budgetSum == null || (operation.budgetSum==0 && operation.avgBudget)}">
                    <div class="addNewCategoryButton">
                        <c:choose>
                            <c:when test="${operation.categorySum == 0}">
                                <input class="categoryId" type="hidden" value="${operation.id}">
                                <input class="categorySumm" type="hidden" value="0">

                                <tiles:insert definition="addButton" flush="false">
                                    <tiles:put name="text" value="${operation.name}"/>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <input class="categoryId" type="hidden" value="${operation.id}">
                                <input class="categorySumm" type="hidden" value="${-operation.categorySum}">

                                <tiles:insert definition="addButton" flush="false">
                                    <tiles:put name="text" value="${operation.name}"/>
                                    <tiles:put name="textClass" value="bold"/>
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <div id="defaultAddButton" style="display: none;">
            <div class="addNewCategoryButton">
                <input class="categoryId" type="hidden" value="">
                <input class="categorySumm" type="hidden" value="0">

                <tiles:insert definition="addButton" flush="false"/>
            </div>
        </div>

    </c:otherwise>
</c:choose>
</div>

<%-- Всплывающее окно подтверждения удаления категории --%>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="confirmRemoveBudgetCategory"/>
    <tiles:put name="data">
        <jsp:include page="/WEB-INF/jsp/finances/window/confirmRemoveBudgetCategory.jsp"/>
    </tiles:put>
</tiles:insert>



<%-- Всплывающее окно подтверждения сохранения изменений --%>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="redirectRefused"/>
    <tiles:put name="data">
        <div class="confirmWindowTitle">
            <h2>
                Сохранение изменений
            </h2>
        </div>
        <div class="confirmWindowMessage">
            Сохранить изменение бюджета категории &laquo;<span id="editableCategory">наименование категории</span>&raquo;
        </div>
        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save"/>
                <tiles:put name="bundle" value="financesBundle"/>
                <tiles:put name="onclick" value="saveCurrent(event)"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close"/>
                <tiles:put name="commandHelpKey" value="button.close"/>
                <tiles:put name="bundle" value="financesBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="win.close('redirectRefused');"/>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>


