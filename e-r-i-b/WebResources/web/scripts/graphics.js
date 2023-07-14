
(function(window)
{
    var INFO_DIV_ID = "graphicsInfoDiv";
    var BG_INFO_DIV_CLASS = "bgInfoDiv";
    var TEXT_INFO_DIV_CLASS = "textInfoDiv";
    var MOUSE_MOVE_EVENT_KEY = "graphicsMouseMove";

    var infoDiv = null;

    var graphics = new Object();

    var monthArray = ["янв.", "фев.", "мар.", "апр.", "мая", "июн.", "июл.", "авг.", "сен.", "окт.", "нояб.", "дек."];

    /**
     * приватная функция для создания
     */
    function createInfoDiv()
    {
        if (infoDiv != null) return;
        var mainDiv = document.createElement("div");
        mainDiv.id = INFO_DIV_ID;

        var bgDiv = document.createElement("div");
        bgDiv.className = BG_INFO_DIV_CLASS;

        var textDiv = document.createElement("div");
        textDiv.className = TEXT_INFO_DIV_CLASS;

        mainDiv.appendChild(bgDiv);
        mainDiv.appendChild(textDiv);
        document.body.appendChild(mainDiv);

        infoDiv = document.getElementById(INFO_DIV_ID);
    }

    /**
     * Координаты курсора
     */
    function mousePageXY(e)
    {
        var x = 0, y = 0;

        if (!e) e = window.event;

        if (e.pageX || e.pageY)
        {
            x = e.pageX;
            y = e.pageY;
        }
        else if (e.clientX || e.clientY)
        {
            var scrolLeft = 0;
            var scrolTop = 0;
            scrolLeft = (document.documentElement.scrollLeft || document.body.scrollLeft);
            scrolTop = (document.documentElement.scrollTop || document.body.scrollTop);
            x = e.clientX + scrolLeft - document.documentElement.clientLeft;
            y = e.clientY + scrolTop - document.documentElement.clientTop;
        }

        return {"x":x, "y":y};
    }

    function getLabelSteps (totalDays)
    {
        if (totalDays <= 14) return 1;
        else if(totalDays <= 21) return 2;
        else if (totalDays <= 31) return 4;
        else if (totalDays <= 61) return 6;
        else if (totalDays <= 92) return 8;
        else if (totalDays <= 123) return 10;
        else if (totalDays <= 154) return 12;
        else return 14;

    }

    graphics.ONE_DAY = 24*60*60*1000;

    /**
     * метод для отображения подсказки
     * @param text - текстовка подсказки
     * @param ev - событие, для получения координат мыши
     */
    graphics.showInfoDiv = function (text, ev)
    {
       if (infoDiv == null) createInfoDiv();
       var textDiv = window.findChildByClassName(infoDiv, TEXT_INFO_DIV_CLASS);
       var bgDiv = window.findChildByClassName(infoDiv, BG_INFO_DIV_CLASS);

       infoDiv.style.display = "block";

       textDiv.innerHTML = text;
       bgDiv.style.width = textDiv.offsetWidth+"px";
       bgDiv.style.height = textDiv.offsetHeight+"px";

       var mouse = mousePageXY(ev);
       infoDiv.style.top = mouse.y+"px";
       infoDiv.style.left = mouse.x+"px";

       window.addSwitchableEvent(window.document.body, 'mousemove', function (e) {
            var mousePos = mousePageXY(e);
            infoDiv.style.top = mousePos.y+"px";
            infoDiv.style.left = mousePos.x+"px";
        }, MOUSE_MOVE_EVENT_KEY);

    };

    /**
     * скрыть подсказку
     */
    graphics.closeInfoDiv = function ()
    {
        if (infoDiv != null) infoDiv.style.display = "none";
        window.switchEvent(MOUSE_MOVE_EVENT_KEY, true)
    };

    /**
     * Приватный абстрактный метод для генерации данныхоси X
     * @param controler       { total: 10, // кол-во элементов (дней)
     *   pushData: function(index, value) {}, // метод для добавления элемента
     *   next: function () {}, // слудующий элемент
     *   hasNext: function () {}, // метод возращающий true пока есть элементы
     *   getCurIndex: function() {} // метод возращающий индекс текущего шага
     *   getCurLable: function() {} // метод возращающий лейбел текущего шага
     *  }
     */
    function getXTicks(controler)
    {
      var EMPTY_LABEL = " ";
      var GRID_LIMIT = 50; // предел дней после которых сетка перестает рисоваться для каждого дня
      var steps = getLabelSteps(controler.total);
      var halfSteps = Math.round(steps/2);
      var label = "";
      var i = 0;
      while (controler.hasNext())
      {
          label = i==0?controler.getCurLable():EMPTY_LABEL;

          if (controler.total > GRID_LIMIT && (label != EMPTY_LABEL || i == halfSteps) || controler.total <= GRID_LIMIT )
          {
            controler.pushData(controler.getCurIndex(), label);
          }
          controler.next();
          i++;
          if (i>=steps) i=0;
      }
      // дорисовываем последнее значение если шагов больше 2-х и нарисовалось больше дней чем половина шага сетки
      if ( steps>=2 && (i>=halfSteps || i==0) || steps == 1)
        controler.pushData(controler.getCurIndex(), controler.getCurLable());
      else
        controler.pushData(controler.getCurIndex(), EMPTY_LABEL);
    }

    /**
     * Метод для получеия масива подписи сетки и подсчета общего количества дней отображаемых на графрафике
     * @param from объект типа Date
     * @param to объект типа Date
     * @return двухмерный массив {totalDays: 20, data:[[date, "31 янв. 2009"], [..],....]}
     */
    graphics.getTicks = function (from, to) {

        var myself = this;
        var total = Math.ceil((to.getTime() - from.getTime())/myself.ONE_DAY) + 1;
        var controler = {
            total: total,
            result: [],
            cur: from,
            next: function () { this.cur.setTime(this.cur.getTime() + myself.ONE_DAY) },
            hasNext: function ()
            {
                return this.cur < to;
            },
            getCurIndex: function() {
                return this.cur.getTime();
            },
            getCurLable: function() {
                return this.cur.getDate() + " " + monthArray[this.cur.getMonth()] +" " + this.cur.getFullYear();
            },
            pushData: function (index, label){
                this.result.push([index, label]);
            }
        };

      getXTicks(controler);
      return { totalDays: controler.total, data: controler.result};
    };

    graphics.getQuarterXTicks = function(data)
    {
        var result = [];
        for(var i = 0; i < data.length; i++)
        {
            result.push([data[i][0].getTime(),parseQuarter(data[i][0])]);
        }
        return result;
    };

    /**
     * Метод для получеия масива подписи сетки
     * @param groups массив из объектов типа Date [{from: Date, to: Date}, {from: Date, to: Date} ...]
     */
    graphics.getDataGroupXTicks = function (groups)
    {
        var myself = this;
        var controler = {
            total: groups.length,
            result: [],
            cur: 0,
            next: function () { this.cur++ },
            hasNext: function ()
            {
                return this.cur+1 < groups.length;
            },
            getCurIndex: function() {
                return this.cur;
            },
            getCurLable: function() {
                var from = groups[this.cur].from;
                var to = groups[this.cur].to;
                if (to.getTime() - from.getTime() < myself.ONE_DAY)
                    return to.getDate() + " " + monthArray[to.getMonth()] +" " + to.getFullYear();
                else
                {
                    var avgDate;
                    if (this.cur == 0)
                        avgDate = new Date(from.getTime());
                    else if (!this.hasNext())
                        avgDate = new Date(to.getTime());
                    else
                        avgDate = new Date(from.getTime() + (to.getTime() - from.getTime())/2);
                    
                    return avgDate.getDate() + " " + monthArray[avgDate.getMonth()] +" " + avgDate.getFullYear();
                }
            },
            pushData: function (index, label){
                this.result.push([index, label]);
            }
        };


      getXTicks(controler);

      return { total: controler.total, data: controler.result};
    };

    /**
     * Метод для получения значений для оси y
     * @param data - двумерный массив с датой [ [xValue, yValue],... ]
     * @param bottomBind - параметр отвачающий за отсутствия нижнего отступа
     * @param useIndex - использовать ли индекс. если useIndex = true, то возвращаем значения с учетом индекса
     * @return { ticks: [[title, value]], index: int}
     */
    graphics.getYTicks = function (data, bottomBind, startWith, useIndex) {
        var TOTAL_STEPS = 15;

        if (data.length < 1) return [];
        var min;
        var max;
        var index=1;
        var indent = 2; // кол-во шагов на отступ
        min = data[0][1];
        max = data[0][1];

        if (bottomBind == null)
            bottomBind = false;

        if(useIndex == null)
            useIndex = true;

        // если отступ снизу не нужен то убераем один шаг оставленный на отступ
        if (bottomBind)
            indent = indent-1;

        for (var i=1; i < data.length; i++)
        {
            if (data[i][1] > max ) max = data[i][1];
            else if( data[i][1] < min ) min = data[i][1];
        }

        if (startWith != null)
            min = startWith;

        // множитель, показатель тысяч
        if (max > 1000000 || min > 100000) index = 1000;
        if (max > 1000000000 || min > 100000000) index = 1000000;
        if (max > 1000000000000 || min > 100000000000) index = 1000000000;
        if (max > 1000000000000000 || min > 100000000000000) index = 1000000000000;
        // определяем истенный размер шага между минимумом и максимумом
        var originDelta = ((max-min)/index)/( TOTAL_STEPS-indent -1 ); // indent шаг на отступ сверху и снизу. -1 так как старт известен
        // originDelta Всегда больше 0
        // определяем число кратное шагу сетки поумолчанию кратно 10
        var greedStep = 10; // кратное сетки
        if (originDelta < 10 && originDelta > 5)  greedStep = 5;
        else if (originDelta < 5) greedStep = 1;
        // определяем кратное дельто
        var delta = Math.ceil(originDelta/greedStep)*greedStep;
		// дельта не может быть меньше 1
        if (delta < 1) delta = 1;

        // не забываем про тысячные
        delta = delta*index;

        // вычисляем начальный шаг (значение ниже минимума)
        var start;
        if (min < 0)
           start = Math.ceil(min/delta)*delta;
        else
           start = Math.floor(min/delta)*delta;

        // выравниваем график по центру сдвигая старт
        var stop = start + (TOTAL_STEPS)*delta; // конечная точка графика
        //высчитываем количество свободного места
        var emptyTopCount = Math.floor( (stop - max - ( min - start))/(2*delta));
        // количество свободных шкал не может быть отрицательным числом
        if ( emptyTopCount<0) emptyTopCount = (-1)*emptyTopCount;
        if (!bottomBind)
            start = start - emptyTopCount*delta;

        var result = [];
        var cur;
        for (var i=0; i< TOTAL_STEPS; i++)
        {
            cur = start + i*delta;
            if(useIndex)
                result.push([cur, ( Math.round(cur/index) ).toString()]);
            else
               result.push([cur, ( Math.round(cur) ).toString()]);
        }

        return { ticks: result, index: index }
    };
    /**
     * метод для получения сокращенного месяца
      * @param month
     */
   graphics.getMonth = function(month)
   {
     return monthArray[month];
   };

    /**
     * метод который возращает повернутый текст, если не поддерживается canvas native font
     * @param text текст
     * @param angle только 90 -90 и -60
     */
    graphics.getTurnedText = function (text, angle)
    {
        if (!isIE())
        {
            var width = 20;
            var height = 250;
            //90
            var x = 125;
            var y = -5;
            var textAnchor = "middle";
            if (angle  == -90)
            {
                x = -125;
                y = 15;
            }
            else if (angle == -60)
            {
                 width = 100;
                 height = 90;
                 x = 25;
                 y = 50;
                 textAnchor = "end";
            }

            return "<object height=\""+height+"\" width=\""+width+"\" type=\"image/svg+xml\" data=\"data:image/svg+xml; charset=utf-8,"+
                "<svg xmlns='http://www.w3.org/2000/svg'><text x='"+x+"' y='"+y+"' font-family='Tahoma' font-size='14'"+
                " fill='#808080' transform='rotate("+angle+")' text-rendering='optimizeSpeed' style='text-anchor: "+textAnchor+";'>"+text+"</text></svg>\"></object>";
        }
        else
           return '<div class="vertical'+angle+'"> <span>' + text + '</span></div>'
    };

    /**
     * поддерживается ли текст в canvas
     */
   graphics.isCanvasTextSupported = function ()
    {
    return !!(document.createElement('canvas').getContext && typeof document.createElement('canvas').getContext('2d').fillText == 'function');
    };
   /**
     * метод для приобразования даты в стрингу
      * @param date
     */
   graphics.dateToString = function(date)
   {

      var dateString = "";
      var month = date.getMonth()+1;
      if (month < 10) dateString+="0";
      dateString+= month+"/";

      if (date.getDate() < 10) dateString+="0";
      dateString+= date.getDate()+"/";
      dateString+= date.getFullYear();
       return dateString;
   };

    /**
     * перечень цветов для круговой диаграммы
     */
   graphics.pieColor =          ["#72bf44", "#f58220", "#0066b3", "#cf3734", "#fff450", "#009597", "#74489d", "#d4711a",
                                 "#89c765", "#00599d", "#aa55a1", "#ed1c24", "#d89016", "#00a65d", "#1c3687", "#cf3734",
                                 "#e3d200", "#00aaad", "#ce181e", "#f58220", "#62a73b", "#0066b3", "#8e187c", "#f04e4c",
                                 "#faa61a", "#00b274", "#21409a", "#f3715a", "#fff200", "#00b6be", "#5c2d91", "#f79548",
                                 "#009252", "#1b75bc", "#a3238e", "#51247f", "#fdba4d" ];

    graphics.pieHighlightColor =["#99d178", "#f8a55e", "#4791c8", "#dc6f6d", "#fff781", "#47b3b4", "#9b7bb8", "#e0995a",
                                 "#aad790", "#4787b8", "#c284bb", "#f25b61", "#e3af57", "#e3af57", "#5b6ea8", "#dc6f6d",
                                 "#ebdf47", "#47c2c4", "#dc585d", "#f8a55e", "#8ec072", "#4791c8", "#ad58a0", "#f47f7e",
                                 "#fbbf5a", "#47c79b", "#5f75b6", "#f69988", "#fff647", "#47cad0", "#8967b0", "#f9b37b",
                                 "#47b082", "#5a9bcf", "#bd60ad", "#8161a3", "#fecd7f" ];     

   window.graphics = graphics;
})(window);


/**
 * Oбъект для работы с выпиской по категории
 */
var categoryAbstract = {
    ERROR_DIV: "winErrors",
    /**
     * список транзакций
     */
    transactions : [],
    /*this.transactions[0] = {
               id: 230,
               date: "14/08/2011",
               title: "Снятие денег",
               categoryId: 25,
               sum: 235.50,
               currency: "${phiz:getCurrencySign('RUB')}",
               categories: [{
                               id: 27,
                               title: "Тестовая категория0"},
                               {
                               id: 26,
                               title: "Тестовая категория1"},
                               {
                               id: 25,
                               title: "Тестовая категория2"
                           }]
           };*/
    /**
     * текущаяя операция
     */
    operation: null,
    /**
     * урл для сохранения данных
     */
    url: '',
    /**
     * метод для наполнения селекта с категориями для конкретной операции
     * @param operationCategory select
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
     * метод для получения полей операции
     */
    getOperationFormFields: function ()
    {
       var operationDate = document.getElementById("operationDate");
       var operationTitle = document.getElementsByName("operationTitle")[0];
       var operationCategory = document.getElementsByName("operationCategory")[0];
       var operationSumSign = document.getElementById("operationSumSign");
       var operationSum = document.getElementById("operationSum");
       var operationId  = document.getElementsByName("operationId")[0];

       return {
           date: operationDate,
           title:operationTitle,
           category:operationCategory,
           sign: operationSumSign,
           sum:operationSum,
           id: operationId
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
     * Метод отвечающий за открытие окна с редактирвание категории
     * @param transactionId
     */
    openCategoryEdit: function (transactionId)
    {
        var fields = this.getOperationFormFields();
        var cutLink = document.getElementById("cutLink");
        var winErrors = document.getElementById("winErrors");
        var myself = this;
        var operation = null;
        for (var i=0; i < this.transactions.length; i++)
            if (this.transactions[i].id == transactionId)
            {
                operation = this.transactions[i];
                break;
            }

        this.operation = operation;
        if (operation == null) return;
        this.operation.isIncome = this.operation.sum >= 0;

        if (!this.operation.isIncome)
            this.operation.sum = -1 * Math.abs(this.operation.sum);

        fields.id.value = operation.id;
        fields.date.innerHTML = operation.date;
        fields.title.value = operation.title;
        fields.sign.innerHTML = this.operation.isIncome?"":"-";
        fields.sum.innerHTML = $().number_format(Math.abs(this.operation.sum), moneyFormat) + " "+ this.operation.currency;

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
        win.open('categoryDiv');
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
    /**
     * Метод для добавления операции
     */
    addOperationPart: function ()
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
        input.type = "text";
        td.appendChild(input);
        if (!this.operation.isIncome)
            td.innerHTML = "-" + td.innerHTML;
        td.innerHTML += " <b>"+this.operation.currency+"</b>" + " <a href='#' class='operationDelete'>удалить</a>";

        tr.appendChild(td);
        table.appendChild(tr);
        // /tr

        this.getCutDataDiv().appendChild(table);
        // пока ишака не пнеш груз не поедит :/
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
                if (curSum >= 0) sum = sum + curSum;
                else allSum[i].value = 0;
            }
        }

        if (Math.abs(this.operation.sum) - sum >= 0)
            document.getElementById("operationSum").innerHTML = $().number_format(Math.abs(this.operation.sum) - sum, moneyFormat) + " "+ this.operation.currency;
        else
            if (input != null)
            {
                curSum = 0;
                for (var i = 0; i < allSum.length; i++)
                    if (!isNaN(input.value))
                        if (allSum[i].value != input.value)
                            curSum += parseFloat(allSum[i].value);
                if (!isNaN(curSum))
                    input.value = Math.abs(this.operation.sum) - curSum;//curSum - curentOverdraft;
                document.getElementById("operationSum").innerHTML = 0 + " "+ this.operation.currency;
            }
    },
    canSave: true,
    /**
     * Метод валидирующий данные и сохраняющий изменения на сервере
     */
    save: function ()
    {
        if (!this.canSave)
            return ;

        this.canSave = false;
        removeAllErrors(this.ERROR_DIV);
        var filds = this.getOperationFormFields();
        var myself = this;
        
        var unlock = function () { myself.canSave = true; };
        
        if (trim(filds.title.value) == '')
        {
            addError("Название операции не может быть пустым.", this.ERROR_DIV, true);
            return  unlock();
        }

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
                if (curSum > 0) sum = sum + curSum;
                else
                {
                    addError("Сумма операции должна быть больше нуля.", this.ERROR_DIV, true);
                    return  unlock();
                }
            else
            {
                if (trim(allSum[i].value) == '')
                    addError("Вы не указали сумму операции.", this.ERROR_DIV, true);
                else
                    addError("Вы неправильно указали сумму операции.", this.ERROR_DIV, true);
                return  unlock();
            }

            params['newOperationSum['+i+']'] = curSum;
        }

        var splitCurSum= curSum.toString().split(".");
        if (splitCurSum[1]!=undefined && splitCurSum[1].length > 2)
        {
            addError("Вы неправильно указали сумму операции. Пожалуйста, введите не более двух знаков после запятой.", this.ERROR_DIV, true);
            return  unlock();
        }

        if (Math.abs(this.operation.sum) - sum < 0)
        {
            addError("Сумма операций, созданных при разбивке, не должна превышать сумму исходной операции.", this.ERROR_DIV, true);
            return  unlock();
        }

        var allTitles  = findChildsByClassName(this.getCutDataDiv(), "newOperationTitle");
        for (var i = 0; i < allTitles.length; i++ )
        {
            if (trim(allTitles[i].value) == '')
            {
                addError("Пожалуйста, укажите название операции.", this.ERROR_DIV, true);
                return  unlock();
            }

            params['newOperationTitle['+i+']'] = trim(allTitles[i].value);
        }

        var allCategories  = findChildsByClassName(this.getCutDataDiv(), "newOperationCategory");
        for (var i = 0; i < allCategories.length; i++ )
        {
            params['newOperationCategoryId['+i+']'] = allCategories[i].options[allCategories[i].selectedIndex].value;
        }
        params['operation'] = "button.save";

        ajaxQuery(convertAjaxParam (params), this.url, function(msg) {
            if (trim(msg) == '') {
                findCommandButton("button.filter").click();   
                return;
            }
            addError(msg, myself.ERROR_DIV, true);
            myself.canSave = true;
        } );

        return ;
    }
};

