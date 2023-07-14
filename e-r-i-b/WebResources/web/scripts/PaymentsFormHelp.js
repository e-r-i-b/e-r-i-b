function PayInput()
{
    // переменная для запоминания текущего активного input`а
    this.curentPayInput;
    // переменная для запоминания последней выбранной строки
    this.lastClickedRow;
    // переменная для запоминания массива валидаторов по именам полей
    this.validators = {};
    // Имя класса дива в обычном состоянии
    this.CLASS_NAME = "form-row";
 // Имя класса дива в обычном состоянии
    this.CLASS_NAME_NOT_MARK = "notMark";
    // Имя класса дива в активном состоянии
    this.ACTIVE_CLASS_NAME = "active-help";
    // Имя класса дива при возникновении ошибки
    this.ERROR_CLASS_NAME = "form-row error";
    // Имя класса дива текста ошибки
    this.ERROR_DIV_CLASS_NAME = "errorDiv";
    //Имя класса дива с предупреждающей ошибкой
    this.PREVENT_ERROR_DIV_CLASS_NAME = "preventErrorDiv";
    // Имя класса дива описания
    this.DESCRIPTION_CLASS_NAME = "description";
    // Имя класса дива детального описания
    this.DETAIL_CLASS_NAME = "detail";
    // Имя класса дива лейбла поля
    this.LABEL_CLASS_NAME = "paymentTextLabel";

    this.additErrorBlock = "";

    // Функция отката объекта до исходного соостояния
    this.rollBack = function (obj)
    {
        if (obj.parentNode == null) return;

        //Откатываем надпись на ссылке подробнее
        var detailLink = obj.detalLink;
        if (detailLink != undefined)
            if (detailLink.origInner != undefined)
                $(detailLink).html(detailLink.origInner);

        //Смотрим текущий инпут, если задана функция валидации - валидируем.
        if (obj.currentInput != undefined)
        {
            var name = obj.currentInput.name;
            if (this.validators[name] != undefined)
            {

                var msgs = this.validators[name](obj.currentInput);
                if (msgs != undefined && msgs.length > 0)
                    this.openClosePreventErrorDiv(obj, msgs, false);
            }
        }

        this.openCloseDescriptionDiv(obj);
    };

    // Открывает краткое описание. obj - див родителя поиск идет по класс
    this.openCloseDescriptionDiv = function (obj, open)
    {

        var objDiv = findChildByClassName(obj, this.DESCRIPTION_CLASS_NAME);
        if (objDiv != undefined)
        {
            if (open != null && trim($(objDiv).html()) == '') open = null;
            hideOrShow(objDiv, open == null);
        }

        if ( open == null  )
        {
            var objDiv = findChildByClassName(obj, this.DETAIL_CLASS_NAME);
            if (objDiv != undefined)
                hideOrShow(objDiv, true);
        }
    };

    // Открывает див с предупреждающей ошибкой. obj - див родителя поиск идет по класс
    this.openClosePreventErrorDiv = function (obj, msgs, hide)
    {
        var objDiv = findChildsByClassName(obj, this.PREVENT_ERROR_DIV_CLASS_NAME);
        var textDiv = findChildByClassName(obj, this.PREVENT_ERROR_DIV_CLASS_NAME + "Text");
        if (objDiv == undefined)
            return;
        if (msgs != undefined && msgs != null)
        {
            $(textDiv).html("");
            for (var i = 0; i < msgs.length; i++)
            {
                if (i != 0)
                    $(textDiv).append("<br/>");
                $(textDiv).append(msgs[i]);
            }
        }
        for (var i=0; i< objDiv.length; i++)
        //Если не задано открыть или закрыть, то открываем
            hideOrShow(objDiv[i], (hide == undefined) ? true : hide);
    };

    // Функция закрывающая детальные подробности поля формы платежа.
    //obj - родительский div
    this.openDetailClick = function (obj)
    {
        this.curentPayInput.detalLink = obj;
        var detali = findChildByClassName(obj.parentNode, this.DETAIL_CLASS_NAME);

        if (detali == undefined)
            return false;

        if (detali.style.display == "none")
        {
            obj.origInner = $(obj).html();
            $(obj).html("Скрыть");
            detali.style.display = "block";
        }
        else
        {
            if (obj.origInner != undefined)
               $(obj).html(obj.origInner);
            else
                $(obj).html("Подробнее...");

            detali.style.display = "none";
        }

        return false;
    };

    //Функция осуществляющая всю логику визуализации div
    //obj - родительский div. Div - обертка input
    this.onClick = function (obj)
    {
        var notFirst = this.curentPayInput != undefined && this.curentPayInput.className != obj.className;

        if (this.curentPayInput != undefined && !notFirst)
        {
            return false;
        }

        if (notFirst)
        {
            try {
                this.rollBack(this.curentPayInput);
            }
            catch(err) { }
        }

        if (obj.error)
        {
            this.curentPayInput = undefined;
            return false;
        }

         if(this.lastClickedRow != null)
         {
             $(this.lastClickedRow).removeClass(this.ACTIVE_CLASS_NAME);
             this.lastClickedRow = obj;
             $(obj).addClass(this.ACTIVE_CLASS_NAME);
         }
         else
         {
             this.lastClickedRow = obj;
             $(obj).addClass(this.ACTIVE_CLASS_NAME);
         }

        this.openCloseDescriptionDiv(obj, true);
        this.openClosePreventErrorDiv(obj, null, true);
        this.curentPayInput = obj;

        return true;
    };

    // Fild error
    this.fieldError = function (fieldName, error, blockId)
    {

        var parentDiv = this.getParenDivByName(fieldName, blockId);
        if (parentDiv == null) return false;

        this.openCloseDescriptionDiv(parentDiv);
        this.resetCurrentPayInput();

        // флаг  обозначающий, что произошла ошибка.
        parentDiv.error = true;
        parentDiv.className = this.ERROR_CLASS_NAME;

        if (error == null) return true;

        var errorDiv = findChildByClassName(parentDiv, this.ERROR_DIV_CLASS_NAME);

        if (errorDiv != undefined)
        {
            errorDiv.style.display = 'block';
            if (this.additErrorBlock != "")
            {
                findChildByClassName(parentDiv, this.additErrorBlock).style.display = 'block';
                // Показываем или скрываем всплывашку при наличии/потере фокуса
                $('.error-row-new .errorDivPmnt').each(function()
                {
                    var errInput = $(this).closest('.error-row-new').find('.paymentValueNew');
                    errInput.focusin(function(){
                        $('.error-row-new .errorDivPmnt').show();
                    });
                    errInput.focusout(function(){
                        $('.error-row-new .errorDivPmnt').hide();
                    });
                });

                // Обрабатываем клик по DOM
                $("body").click(function(e) {
                    var target = $(e.target);
                    var errPayDiv = $('.error-row-new .paymentValueNew');
                     if(!$.contains(errPayDiv[0], e.target))
                     {
                     $('.errorDivPmnt').hide();
                     }
                });
            }

            $(errorDiv).html(error);

            // отображем многоточие в всплывашке с ошибкой
            function errTextHeight()
            {
                $('.error-row-new').each(function()
                {
                    var errContainer = $(this).find('.errMsg');
                    var errorBlock = $(this).find('.errorDiv');

                    if(errorBlock.height() <= 25){
                        errorBlock.css('height', errorBlock.height());
                        errContainer.addClass('showFullErrText');
                    }
                });
            }
            errTextHeight();
        }
        return true;
    };

    /* очистка ошибка для строки в платеже(с классом form-row) */
    this.formRowClearError = function(formRowElement)
    {
        if (formRowElement == null)
            return false;

        // флаг  обозначающий, что произошла ошибка.
        formRowElement.error = false;
        formRowElement.className = this.CLASS_NAME;
        this.resetCurrentPayInput();

        var errorDiv = findChildByClassName(formRowElement, this.ERROR_DIV_CLASS_NAME);

        if (errorDiv != undefined)
        {
            errorDiv.style.display = 'none';
            $(errorDiv).html("");
        }
        if (this.additErrorBlock != "")
        {
            findChildByClassName(formRowElement, this.additErrorBlock).style.display = 'none';
        }
        return true;
    };

    //убираем выделение с поля с ошибкой
    this.fieldClearError = function (fieldName)
    {
        var parentDiv = this.getParenDivByName(fieldName);
        this.formRowClearError(parentDiv);
    };

    //onfocus
    this.onFocus = function (obj)
    {
        // поиск родительского дива - обертки
        var parentDiv = findParentByClassName(obj, this.CLASS_NAME);
        if (parentDiv == document)
            parentDiv = findParentByClassName(obj, this.ACTIVE_CLASS_NAME);
        if (parentDiv == document)
            parentDiv = findParentByClassName(obj, this.ERROR_CLASS_NAME);

        if (parentDiv == document)
            return false;
        parentDiv.currentInput = obj;
    };

     //Функция возращающая родительский див инпута
    this.getParenDivByName = function (fieldName, blockId)
    {
        // поиск поля по имени
        var blockSelector = blockId != null ? ("#" + blockId + " ") : "";
        var field = $(blockSelector + "*[name='" + fieldName + "']").toArray();
        if (field.length == 0)
        {
            var newFieldName = 'field('+fieldName+')';
            field = $(blockSelector + "*[name='" + newFieldName + "']").toArray();
            if (field.length == 0)
            {
                fieldName = 'filter('+fieldName+')';
                field = $(blockSelector + "*[name='" + fieldName + "']").toArray();
                if (field.length == 0)
                    return null;
            }
        }

        var parentDivFunc = function(field, payInp){
            // поиск родительского дива - обертки
            var parentDiv = findParentByClassName(field, payInp.CLASS_NAME);
            if (parentDiv == document)
                parentDiv = findParentByClassName(field, payInp.ACTIVE_CLASS_NAME);
            if (parentDiv == document)
                parentDiv = findParentByClassName(field, payInp.ERROR_CLASS_NAME);

            return parentDiv == document ? null : parentDiv;
        };

        // проверяем что поля имеют одного родителя
        var parentDiv = parentDivFunc(field[0], this);
        for(var i = 1; i < field.length; i++)
        {
            if(parentDiv != parentDivFunc(field[i], this))
                return null;
        }

        return parentDiv;
    };

    //Функция возращающая содержание дива с именем поля
    this.getFieldLabel = function (fieldName)
    {
        var parentDiv = this.getParenDivByName(fieldName);
        if (parentDiv == null)
            return '';

        // поиск дива с Лейблом
        var labelDiv = findChildByClassName(parentDiv, this.LABEL_CLASS_NAME);
        if (labelDiv == undefined)
            return '';

        return $(labelDiv).html();
    };

    // функции для перебора массива контролов и постановки им действия onFocus
    this.setOnFocus = function(arr)
    {
     var self = this;
     for (var i=0; i< arr.length; i++ )
     {
        arr[i].payOriginFocus = arr[i].onfocus;
        arr[i].onfocus = function(e) { self.onFocus(this); return (this.payOriginFocus!=null)? this.payOriginFocus(e): true; };
     }
    };

   function setElementsEvents(elements, self)
   {
       var element = null;
       for (var i=0; i< elements.length; i++ )
       {
           element = elements[i];
           if (!findClassName(element, self.CLASS_NAME_NOT_MARK) &&
               (findClassName(element, self.CLASS_NAME) ||
                findClassName(element, self.ACTIVE_CLASS_NAME) ||
                findClassName(element, self.ERROR_CLASS_NAME)
               ) &&
               element.payInput == undefined
              )
           {
               element.onclick = function () { self.onClick(this) };
               //input
               self.setOnFocus( element.getElementsByTagName("input") );
               //select
               self.setOnFocus( element.getElementsByTagName("select") );
               //textarea
               self.setOnFocus( element.getElementsByTagName("textarea") );
               element.payInput = self;
           }
       }
   }
    /* Функция для поиска по дому полей типа form-row и установки им событий */
    this.setEvents = function(self, parent)
    {
       if (parent == null) parent = document;
       var divs = parent.getElementsByTagName("div");
       var trs  = parent.getElementsByTagName("tr");
       setElementsEvents(divs, self);
       setElementsEvents(trs, self);

    };

    this.addValidators = function(fieldName, funct)
    {
        this.validators[fieldName] = funct;
    };

    /**
     * Сброс текущего активного input'а
     */
    this.resetCurrentPayInput = function()
    {
        if (this.curentPayInput != undefined)
        {
            this.openCloseDescriptionDiv(this.curentPayInput);
            this.curentPayInput.className = this.CLASS_NAME;
            this.curentPayInput = undefined;
        }
    };
}





var payInput = new PayInput;
doOnLoad(payInput.setEvents, payInput);