$(function(){  
	button.init();
    button.FileUpload();
})

	var button = {
        init : function(){
            /*
                button 波纹效果
            */
            // just add effect to elements
            Array.prototype.forEach.call(document.querySelectorAll('[data-ripple]'), function(element){
                // find all elements and attach effect
                new RippleEffect(element); // element is instance of javascript element node
            });
            //获取光标事件
            $('button').focus(function(event) {
                $(this).addClass('md-focused');
            }).blur(function(event) {
                $(this).removeClass('md-focused');
            });;
        },
		/*
		  File upload button
		*/
		FileUpload : function(){
			var $file = $('.file-input-wrapper');
			$file.click(function(event) {
				var input = $(this).children('input');

				input.click(function(event){
				 event.stopPropagation();//防止字节点冒泡
				})
				input.click();

			});
		}
		
	}	



  function RippleEffect(element){
        this.element = element;
       
        this.element.addEventListener('click', this.run.bind(this), false);
    }
    RippleEffect.prototype = {
        run: function(event){
            var ripplerContainer = this.element.querySelector('.ripple-container');
            var offsetInfo = this.element.getBoundingClientRect();
            if(ripplerContainer) {
                ripplerContainer.remove();
            }
        
            var rippleContainer = document.createElement('div');
            rippleContainer.style.position = 'absolute';
            rippleContainer.style.width = offsetInfo.width + 'px';
            rippleContainer.style.left = 0 + 'px';
            rippleContainer.style.top = 0 + 'px';
            rippleContainer.style.height = offsetInfo.height + 'px';
            rippleContainer.className = 'ripple-container';
            rippleContainer.style.overflow = 'hidden';
            this.element.appendChild(rippleContainer);

            var circleD = offsetInfo.width * 2;

            var ripple = document.createElement('div');
            ripple.style.position = 'absolute';
            ripple.style.width = circleD + 'px';
            ripple.style.height = circleD + 'px';
            ripple.style.borderRadius = '500px';
          
            ripple.style.left = ((event.pageX - offsetInfo.left) - circleD/2) + 'px';
            ripple.style.top = ((event.pageY - offsetInfo.top) - circleD/2 - $(window).scrollTop()) + 'px';

            ripple.className = 'ripple';
            rippleContainer.appendChild(ripple);
            ripple.addEventListener('animationend', function(){

                //判断是否是ie浏览器
                if (!!window.ActiveXObject || "ActiveXObject" in window) { 
                    rippleContainer.removeNode(true);
                }else{  
                    rippleContainer.remove();
                }  
                
            }.bind(this), false);
        }
    };


