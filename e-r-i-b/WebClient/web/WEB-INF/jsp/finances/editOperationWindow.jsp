<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<%--
JSP с
    данными для отображения всплывающего окна редактирования операции,
    скриптами для работы с данным окном
--%>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="editOperationDiv"/>
    <tiles:put name="data">
        <h1><bean:message bundle="financesBundle" key="label.edit.operation.window.title"/></h1>

        <%-- Ошибки --%>
        <tiles:insert definition="errorBlock" flush="false">
            <tiles:put name="regionSelector" value="winOperationErrors"/>
            <tiles:put name="needWarning" value="false"/>
        </tiles:insert>
        <%-- /Ошибки --%>

        <input type="hidden" value="" name="operationId"/>
        <table class="operationTable">
            <tr>
                <td class="operationTitle">
                    <bean:message bundle="financesBundle" key="label.edit.operation.window.date"/>:
                </td>
                <td class="textData"><span id="operationDate"></span></td>
            </tr>
            <tr>
                <td class="operationTitle">
                    <bean:message bundle="financesBundle" key="label.edit.operation.window.name"/><span class='asterisk'>*</span>:
                </td>
                <td><input type="text" size="50" maxlength="1000" name="operationTitle" value=""/></td>
            </tr>
            <tr>
                <td class="operationTitle">
                    <bean:message bundle="financesBundle" key="label.edit.operation.window.category"/><span class='asterisk'>*</span>:
                </td>
                <td><select name="operationCategory"></select></td>
            </tr>
            <tr>
                <td class="operationTitle">
                    <bean:message bundle="financesBundle" key="label.edit.operation.window.cardNumber"/>:
                </td>
                <td><span id="cardInfoContainer" class="bold"></span></td>
            </tr>
            <tr>
                <td class="operationTitle">
                    <bean:message bundle="financesBundle" key="label.edit.operation.window.amount"/>:
                </td>
                <td class="textData">
                    <span id="operationSumSign"></span><span id="operationSum"></span>
                    <a href="#" id="cutLink"><bean:message bundle="financesBundle" key="label.edit.operation.window.divide"/></a>
                </td>
            </tr>
            <tr id="buttonShowOperationPayment">
                <td></td>
                <td>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.show.operation.payment"/>
                        <tiles:put name="commandHelpKey" value="button.show.operation.payment.help"/>
                        <tiles:put name="bundle" value="financesBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="onclick" value="editOperation.goToViewPayment();"/>
                        <tiles:put name="image" value="grayClock.gif"/>
                        <tiles:put name="imageHover" value="grayClock.gif"/>
                        <tiles:put name="imagePosition" value="left"/>
                    </tiles:insert>
                </td>
            </tr>
        </table>
        <div id="cutOperation" style="display: none">
            <div class="cutData"></div>
            <div class="buttonsArea">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.add.operations"/>
                    <tiles:put name="commandHelpKey" value="button.add.operations.help"/>
                    <tiles:put name="bundle" value="financesBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="onclick" value="editOperation.addOperationPart();"/>
                </tiles:insert>
            </div>
        </div>
        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="edit.operation.button.cancel"/>
                <tiles:put name="commandHelpKey" value="edit.operation.button.cancel.help"/>
                <tiles:put name="bundle" value="financesBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="editOperation.close();"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="edit.operation.button.save"/>
                <tiles:put name="commandHelpKey" value="edit.operation.button.save.help"/>
                <tiles:put name="bundle" value="financesBundle"/>
                <tiles:put name="onclick" value="editOperation.save();"/>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>

<script type="text/javascript">

    var editOperation =
    {
        WINDOW_ID : "editOperationDiv",
        ERROR_DIV: "winOperationErrors",
        operations : [], //список операций
        url: '',//урл по которому происходит сохранение данных
        canSave: true, //флаг показывающий возможность сохранения операций. Необходим для защиты от двойного сабмита
        listOperationData: null,//идентификатор текущего списка операций. Необходим для последующей перезагрузки данного списка
        callbackFunctions : [],
        inputId : 0,

        setListOperationData: function(elem, fromDate, toDate, typeDate, searchPage, resOnPage, showCreditCards, showOtherAccounts, openPageDate, income, onlyAvailableCategories)
        {
            this.listOperationData = {
                id: $(elem).closest(".operationsListBlock").attr("id"),
                fromDate: fromDate,
                toDate: toDate,
                typeDate: typeDate,
                searchPage: searchPage,
                resOnPage: resOnPage,
                showCreditCards: showCreditCards,
                showOtherAccounts: showOtherAccounts,
                openPageDate: openPageDate,
                income: income,
                onlyAvailableCategories:onlyAvailableCategories
            }
        },

        setListOperationWithCategoriesData: function(elem, fromDate, toDate, typeDate, searchPage, resOnPage,  openPageDate, categoryId, selectedCardIds, onlyAvailableCategories)
        {
            this.listOperationData = {
                id: $(elem).closest(".operationsListBlock").attr("id"),
                fromDate: fromDate,
                toDate: toDate,
                typeDate: typeDate,
                searchPage: searchPage,
                resOnPage: resOnPage,
                openPageDate: openPageDate,
                category: categoryId,
                selectedCardIds: selectedCardIds,
                onlyAvailableCategories: onlyAvailableCategories
            }
        },

        //функция вызываемая после сохранения операции
        reloadPage: function()
        {
            financesUtils.reloadPage(this.listOperationData.id, this.listOperationData);
            return false;
        },
        /**
         * Колбек, выполняемый после редактирования операции. Задается страницей, которая открывает данное окно.
         */
        callback: function(callBackParams)
        {
            for (var i = 0; i < this.callbackFunctions.length; i++)
            {
                this.callbackFunctions[i](callBackParams);
            }
        },
        /**
         * Добавить каллбек функцию в массив
         * @param func - функция
         */
        addCallbackFunction : function(func)
        {
            this.callbackFunctions.push(func);
        },
        /**
         * метод для получения полей операции
         */
        getCategoryOptions: function(operationCategory)
        {
            operationCategory.length = 0;
            for (var i = 0; i < this.operation.categories.length; i++)
            {
                var category = this.operation.categories[i];
                operationCategory.options.add(new Option(category.title, category.id));
                if (this.operation.categoryId == category.id)
                    operationCategory.selectedIndex = i;
            }
        },
        /**
         * метод для получения названия категории по идентификатору
         */
        getCategoryName: function(categoryId)
        {
            for (var i = 0; i < this.operation.categories.length; i++)
            {
                var category = this.operation.categories[i];
                if(categoryId == category.id)
                    return category.title;
            }
            return "";
        },
        getOperationFormFields: function ()
        {
           var operationDate = document.getElementById("operationDate");
           var operationTitle = document.getElementsByName("operationTitle")[0];
           var operationCategory = document.getElementsByName("operationCategory")[0];
           var operationSumSign = document.getElementById("operationSumSign");
           var operationSum = document.getElementById("operationSum");
           var operationId  = document.getElementsByName("operationId")[0];
           var cardInfo  = document.getElementById("cardInfoContainer");

           return {
               date: operationDate,
               title:operationTitle,
               category:operationCategory,
               sign: operationSumSign,
               sum:operationSum,
               id: operationId,
               cardInfo: cardInfo
           };
        },
        cutDiv: null,
        /**
         * метод получающий див контейнер для операций разбиения и другово связаного с этим стафа
         */
        getCutDiv: function()
        {
            if (this.cutDiv == null) this.cutDiv = document.getElementById("cutOperation");
            return this.cutDiv;
        },
        cutDataDiv: null,
        /**
         * получить див с непосредственно даннимы для разбиения
         */
        getCutDataDiv: function ()
        {
            if (this.cutDataDiv == null) this.cutDataDiv = findChildByClassName(this.getCutDiv(), "cutData");
            return this.cutDataDiv;
        },
        /**
         * метод для получения параметров операции
         */
        getOperationData: function(operationId)
        {
            var operationTr = $('#operation'+operationId);
            return {
                id:operationId,
                date: $(operationTr).find('.date').html(),
                title: $(operationTr).find('.description').html(),
                categoryName: $(operationTr).find('.categoryName').html(),
                sum:$(operationTr).find('.operationSumm').html()
            };
        },
        open : function(operationId)
        {
            var fields = this.getOperationFormFields();
            var cutLink = document.getElementById("cutLink");
            var winErrors = document.getElementById("winOperationErrors");
            var myself = this;
            var operation = null;
            for (var i=0; i < this.operations.length; i++)
                if (this.operations[i].id == operationId)
                {
                    operation = this.operations[i];
                    break;
                }
    
            this.operation = operation;
            if (operation == null)
                return;
            this.operation.isIncome = this.operation.sum >= 0;

            fields.id.value = operation.id;
            fields.date.innerHTML = operation.date;
            fields.title.value = operation.title;
            fields.sum.innerHTML = this.operation.sumText;
            fields.cardInfo.innerHTML = this.operation.cardInfo;

            // формируем список категорий
            this.getCategoryOptions(fields.category);

            this.getCutDataDiv().innerHTML = "";
            this.getCutDiv().style.display = "none";
            winErrors.style.display = "none";
            removeAllErrors(this.ERROR_DIV);
            cutLink.style.display = "";
            cutLink.onclick = function () {
                myself.openCut(cutLink);
                return false;
            };
            if (operation.businessDocumentId != "")
            {
                $('#buttonShowOperationPayment').show();
            }
            else
            {
                $('#buttonShowOperationPayment').hide();
            }
            win.open(this.WINDOW_ID);
        },
        close: function()
        {
            win.close(this.WINDOW_ID);
        },
        unlock: function()
        {
            this.canSave = true;
        },
        save : function()
        {
            if (!this.canSave)
                return;

            this.canSave = false;
            removeAllErrors(this.ERROR_DIV);
            var filds = this.getOperationFormFields();
            var myself = this;

            if (trim(filds.title.value) == '')
            {
                addError("Название операции не может быть пустым.", this.ERROR_DIV, true);
                return this.unlock();
            }

            var callBackParams = new Object();

            callBackParams.oldOperation = new Object();
            callBackParams.oldOperation.categoryId = this.operation.categoryId;
            callBackParams.oldOperation.categoryName = this.getCategoryName(callBackParams.oldOperation.categoryId);
            callBackParams.oldOperation.sum = - this.operation.sum;

            callBackParams.newOperation = new Object();
            callBackParams.newOperation.categoryId = filds.category.options[filds.category.selectedIndex].value;
            callBackParams.newOperation.categoryName = this.getCategoryName(callBackParams.newOperation.categoryId);

            callBackParams.newOperations = [];

            var params = {};

            params['operationId'] = this.operation.id;
            params['operationTitle']  = filds.title.value;
            params['operationCategoryId'] = filds.category.options[filds.category.selectedIndex].value;

            var allSum = findChildsByClassName(this.getCutDataDiv(), "newOperationSum");
            var sum = 0;
            var curSum = 0;
            for (var i = 0; i < allSum.length; i++ )
            {
                allSum[i].value = allSum[i].value.replace(',', '.');
                curSum = parseFloat(allSum[i].value);
                if (!isNaN(curSum))
                    if (curSum > 0)
                    {
                        if(!this.operation.isIncome)
                            curSum = - curSum;
                        sum = sum + curSum;
                        params['newOperationSum['+i+']'] = curSum;
                        callBackParams.newOperations[i] = new Object() ;
                        callBackParams.newOperations[i].sum =  curSum;
                    }
                    else
                    {
                        addError("Сумма операции должна быть больше нуля.", this.ERROR_DIV, true);
                        return this.unlock();
                    }
                else
                {
                    if (trim(allSum[i].value) == '')
                        addError("Вы не указали сумму операции.", this.ERROR_DIV, true);
                    else
                        addError("Вы неправильно указали сумму операции.", this.ERROR_DIV, true);
                    return this.unlock();
                }
            }

            callBackParams.newOperation.sum = this.operation.sum - sum;

            var splitCurSum= curSum.toString().split(".");
            if (splitCurSum[1]!=undefined && splitCurSum[1].length > 2)
            {
                addError("Вы неправильно указали сумму операции. Пожалуйста, введите не более двух знаков после запятой.", this.ERROR_DIV, true);
                return this.unlock();
            }

            if (Math.abs(this.operation.sum) - Math.abs(sum) < 0)
            {
                addError("Сумма операций, созданных при разбивке, не должна превышать сумму исходной операции.", this.ERROR_DIV, true);
                return this.unlock();
            }

            var allTitles  = findChildsByClassName(this.getCutDataDiv(), "newOperationTitle");
            for (var i = 0; i < allTitles.length; i++ )
            {
                if (trim(allTitles[i].value) == '')
                {
                    addError("Пожалуйста, укажите название операции.", this.ERROR_DIV, true);
                    return this.unlock();
                }

                params['newOperationTitle['+i+']'] = trim(allTitles[i].value);
                callBackParams.newOperations[i].title =  trim(allTitles[i].value);
            }

            var allCategories  = findChildsByClassName(this.getCutDataDiv(), "newOperationCategory");
            for (var i = 0; i < allCategories.length; i++ )
            {
                params['newOperationCategoryId['+i+']'] = allCategories[i].options[allCategories[i].selectedIndex].value;
                callBackParams.newOperations[i].categoryId =  allCategories[i].options[allCategories[i].selectedIndex].value;
                callBackParams.newOperations[i].categoryName =  this.getCategoryName(callBackParams.newOperations[i].categoryId);
            }
            params['operation'] = "button.save";

            ajaxQuery(convertAjaxParam (params), this.url, function(msg) {
                if (trim(msg) == '') {
                    win.close(myself.WINDOW_ID);
                    myself.unlock();
                    myself.callback(callBackParams);
                    return myself.reloadPage();
                }
                addError(msg, myself.ERROR_DIV, true);
                return myself.unlock();
            } );
        },
        /**
         * метод открывающий первое разбиение операции
         * @param cutLink ссылка разбить
         */
        openCut: function (cutLink)
        {
            cutLink.style.display = "none";
            this.getCutDiv().style.display = "";
            this.addOperationPart();
        },
        addOperationPart : function()
        {
            var table = document.createElement("table");
            table.className = "operationTable";

            // tr
            var tr = document.createElement("tr");

            var td = document.createElement("td");
            td.colSpan = 2;
            td.innerHTML = "<b class='finance-title'>Операция</b>";
            tr.appendChild(td);
            table.appendChild(tr);
            // /tr

            // tr
            var tr = document.createElement("tr");

            var td = document.createElement("td");
            td.className = "operationTitle";
            td.innerHTML = "Название<span class='asterisk'>*</span>:";
            tr.appendChild(td);

            td = document.createElement("td");
            var input = document.createElement("input");
            input.className = "newOperationTitle";
            input.type = "text";
            td.appendChild(input);

            tr.appendChild(td);
            table.appendChild(tr);
            // /tr
            // tr
            tr = document.createElement("tr");

            td = document.createElement("td");
            td.className = "operationTitle";
            td.innerHTML = "Категория<span class='asterisk'>*</span>:";
            tr.appendChild(td);

            td = document.createElement("td");
            var select = document.createElement("select");
            select.className = "newOperationCategory";
            this.getCategoryOptions(select);
            td.appendChild(select);

            tr.appendChild(td);
            table.appendChild(tr);
            // /tr
            // tr
            tr = document.createElement("tr");

            td = document.createElement("td");
            td.className = "operationTitle";
            td.innerHTML = "Сумма<span class='asterisk'>*</span>:";
            tr.appendChild(td);

            td = document.createElement("td");
            input = document.createElement("input");
            input.className = "newOperationSum";
            input.id = 'newOperation' + this.inputId;
            this.inputId++;
            input.type = "text";
            td.appendChild(input);
            if (!this.operation.isIncome)
                td.innerHTML = "-" + td.innerHTML;
            td.innerHTML += " <b>"+this.operation.currency+"</b>" + " <a href='#' class='operationDelete'>удалить</a>";

            tr.appendChild(td);
            table.appendChild(tr);
            // /tr

            this.getCutDataDiv().appendChild(table);
            if (isIE())
            {
                $('#cutOperation').find('span:has(span.selectWrapper)').each(function(index, element)
                {
                    // Удаляем обертки предыдущих кастомных селектов, чтобы инициализировать заново. Иначе ослик
                    // не раскрывает их.
                    var span = $(element);
                    var parentTd = span.parent();
                    span.remove();
                    parentTd.find('select').attr('inited', '');
                });
                this.getCutDataDiv().innerHTML = this.getCutDataDiv().innerHTML;
            }
            // инициализируем селекты
            window.selectCore.init(this.getCutDataDiv());
            // навешиваем события на изменение суммы
            this.setSumKeyEvents();
            this.setDeleteEvents();
        },
        /**
         * метод для добавления событий на поле с суммной операцией
         */
        setSumKeyEvents: function ()
        {
            var myself = this;
            // И скажем снова спасибо IE document.getElementsByName с динамически созданными элементами ему не подвластно
            var allSum = findChildsByClassName(this.getCutDataDiv(), "newOperationSum");
            for (var i = 0; i < allSum.length; i++ )
                allSum[i].onkeyup = function () {
                    myself.sumChange(allSum, this);
                    };
        },
        /**
         * метод для удаления новой операции
         */
        setDeleteEvents: function()
        {
            var myself = this;
            var allLinks = findChildsByClassName(this.getCutDataDiv(), "operationDelete");
            for (var i = 0; i < allLinks.length; i++ )
                allLinks[i].onclick = function () {
                    var table = findParentByClassName(this, "operationTable");
                    table.parentNode.removeChild(table);
                    myself.setSumKeyEvents();
                    myself.sumChange();
                    return false;
                    };
        },
        /**
         * Событие отрабатывающее при каждом нажитии кнопки в поле с суммой
         * @param allSum
         * @param input
         */
        sumChange: function (allSum, input)
        {
            var sum = 0;
            var curSum = 0;
            if (allSum == null)
                var allSum = findChildsByClassName(this.getCutDataDiv(), "newOperationSum");

            for (var i = 0; i < allSum.length; i++ )
            {
                allSum[i].value = allSum[i].value.replace(',', '.');
                curSum = parseFloat(allSum[i].value);
                if (!isNaN(curSum))
                {
                    curSum = Math.round(curSum*100)/100;   //округляем до 2-х знаков после запятой
                    if (curSum >= 0)
                        sum = sum + curSum;
                    else
                        allSum[i].value = 0;
                }
            }

            if (Math.abs(this.operation.sum) - sum >= 0)
            {
                var sumSignumChar = '';
                if(!this.operation.isIncome)
                    sumSignumChar = '-';
                document.getElementById("operationSum").innerHTML = sumSignumChar + FloatToString(Math.abs(this.operation.sum) - sum , 2,' ') + " "+ this.operation.currency;
            }
            else
                if (input != null)
                {
                    curSum = 0;
                    for (var i = 0; i < allSum.length; i++)
                        if (!isNaN(input.value))
                            if (allSum[i].id != input.id)
                            {
                                var curVal =  parseFloat(allSum[i].value);
                                if (!isNaN(curVal))
                                    curSum += curVal;
                            }
                    if (!isNaN(curSum))
                        input.value = Math.abs(this.operation.sum) - curSum;
                    document.getElementById("operationSum").innerHTML = 0 + " "+ this.operation.currency;
                }
        },

        /**
        * Перейти на форму просмотра платежа
         */
        goToViewPayment: function()
        {
            financesUtils.goToViewPayment(this.operation.businessDocumentId);
        }
    };

</script>