/**
 * @author zhixin wen <wenzhixin2010@gmail.com>
 * @version: v1.0.1
 */

(function ($) {
    'use strict';

    $.extend($.fn.bootstrapTable.defaults, {
        fixedColumns: false,
        fixedNumber: 1,
        fixedHoverCss: []
    });

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _initHeader = BootstrapTable.prototype.initHeader,
        _initBody = BootstrapTable.prototype.initBody,
        _resetView = BootstrapTable.prototype.resetView,
    	_getCaret = BootstrapTable.prototype.getCaret,
    	_backSelect	= BootstrapTable.prototype.updateSelected;
    //zt 给复选框加样式
    BootstrapTable.prototype.updateSelected = function(){
    	_backSelect.apply(this, Array.prototype.slice.apply(arguments));
    	var checkAll = this.$selectItem.filter(':enabled').length &&
	        this.$selectItem.filter(':enabled').length ===
	        this.$selectItem.filter(':enabled').filter(':checked').length;
    	if(checkAll){
    		$('thead .bs-checkbox').removeClass('no-checkAll');
    		$('thead .bs-checkbox').addClass('checkAll');
    	}else{
    		$('thead .bs-checkbox').removeClass('checkAll');
    		$('thead .bs-checkbox').addClass('no-checkAll');
    	}
    };
    BootstrapTable.prototype.initFixedColumns = function () {
    	this.$fixedEl = this.$el.clone(true);
    	this.$fixedEl.addClass('fixed');
    	this.$el.after(this.$fixedEl);
    	
    	this.timeoutHeaderColumns_ = 0;
    	this.timeoutBodyColumns_ = 0;
    };

    BootstrapTable.prototype.initHeader = function () {
        _initHeader.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.fixedColumns) {
            return;
        }

        this.initFixedColumns();
        
        this.$fixedHeader = this.$fixedEl.find('>thead');
        if (!this.$fixedHeader.length) {
            this.$fixedHeader = $('<thead></thead>').appendTo(this.$fixedEl);
        }

        var that = this, $trs = this.$header.find('tr').clone(true);
        $trs.each(function () {
            $(this).find('th:gt(' + (that.options.fixedNumber-1) + ')').remove();
        });        
        
        this.$fixedHeader.html('').append($trs); 
    };

    BootstrapTable.prototype.initBody = function () {
        _initBody.apply(this, Array.prototype.slice.apply(arguments));
        
        if (!this.options.fixedColumns) {
            return;
        }

        var that = this,
            rowspan = 0;
        
        this.$fixedBody = this.$fixedEl.find('>tbody');
        if (!this.$fixedBody.length) {
            this.$fixedBody = $('<tbody></tbody>').appendTo(this.$fixedEl);
        }
        this.$fixedBody.html('');
        
        this.$body.find('>tr[data-index]').each(function () {
            var $tr = $(this).clone(true),
                $tds = $tr.find('td');
                //zt 为什么把tr 设置为空
            //$tr.html('');
            var end = that.options.fixedNumber;
            if (rowspan > 0) {
                --end;
                --rowspan;
            }
            for (var i = 0; i < end; i++) {
                $tr.append($tds.eq(i).clone(true));
            }
            that.$fixedBody.append($tr);
            
            if ($tds.eq(0).attr('rowspan')){
            	rowspan = $tds.eq(0).attr('rowspan') - 1;
            }
            
            var $OriTr = $(this);

            $OriTr.hover(function(){
            	$tr.addClass('hover');
            },
            function(){
            	$tr.removeClass('hover');
            });
            
            $tr.hover(function(){
            	$OriTr.addClass('hover');
            },
            function(){
            	$OriTr.removeClass('hover');
            });
            // $OriTr.click(function(event) {
            //     var target = $(event.target);
            //     $tr.parents('tbody').find('tr').removeClass('bg_color');
            //     $tr.addClass('bg_color');
            // });
            
        });
        
    };
    
    BootstrapTable.prototype.getCaret = function() {
    	_getCaret.apply(this, Array.prototype.slice.apply(arguments));
    	
        var that = this;
        if(this.$fixedHeader != undefined){
	        $.each(this.$fixedHeader.find('th'), function(i, th) {
	            $(th).find('.sortable').removeClass('desc asc').addClass($(th).data('field') === that.options.sortName ? that.options.sortOrder : 'both');
	        });
        }
    };

    BootstrapTable.prototype.resetView = function () {
        _resetView.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.fixedColumns) {
            return;
        }

        clearTimeout(this.timeoutHeaderColumns_);
        this.timeoutHeaderColumns_ = setTimeout($.proxy(this.fitHeaderColumns, this), this.$el.is(':hidden') ? 100 : 0);

        clearTimeout(this.timeoutBodyColumns_);
        this.timeoutBodyColumns_ = setTimeout($.proxy(this.fitBodyColumns, this), this.$el.is(':hidden') ? 100 : 0);
    };

    BootstrapTable.prototype.fitHeaderColumns = function () {
        var that = this,
            visibleFields = this.getVisibleFields(),
            headerWidth = 0;

        this.$body.find('tr:first-child:not(.no-records-found) > *').each(function (i) {
            var $this = $(this),
                index = i;

            if (i >= that.options.fixedNumber) {
                return false;
            }

            if (that.options.detailView && !that.options.cardView) {
                index = i - 1;
            }

            that.$fixedHeader.find('th[data-field="' + visibleFields[index] + '"]')
                .find('.fht-cell').width($this.innerWidth());
            headerWidth += $this.outerWidth();
        });
        this.$fixedEl.width(headerWidth).show();
    };

    BootstrapTable.prototype.fitBodyColumns = function () {
        var that = this,
            top = -(parseInt(this.$el.css('margin-top')) -1),
            // the fixed height should reduce the scorll-x height
            height = this.$tableBody.height() - 14;

        if (!this.$body.find('> tr[data-index]').length) {
            this.$fixedEl.hide();
            return;
        }

        if (!this.options.height) {
            top = this.$fixedHeader.height();
            height = height - top;
        }

        this.$body.find('> tr').each(function (i) {
            that.$fixedBody.find('tr:eq(' + i + ')').height($(this).height());
        });

        // events
        this.$tableBody.on('scroll', function () {
            that.$fixedBody.find('table').css('top', -$(this).scrollTop());
        });
        this.$body.find('> tr[data-index]').off('hover').hover(function () {
            var index = $(this).data('index');
            that.$fixedBody.find('tr[data-index="' + index + '"]').addClass('hover');
        }, function () {
            var index = $(this).data('index');
            that.$fixedBody.find('tr[data-index="' + index + '"]').removeClass('hover');
        });
        this.$fixedBody.find('tr[data-index]').off('hover').hover(function () {
            var index = $(this).data('index');
            that.$body.find('tr[data-index="' + index + '"]').addClass('hover');
        }, function () {
            var index = $(this).data('index');
            that.$body.find('> tr[data-index="' + index + '"]').removeClass('hover');
        }); 
    };  

})(jQuery);
