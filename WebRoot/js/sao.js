/**
 * Created by 刘华 on 2016/4/5.
 */
var itime=0;
var bool=true;
var x1,y1;
var x= 0,y=0;
var timer;
var width;
var height;
$(function agin(){
	width=$(".sao").width();
	height=$(".sao").height();
	var sao = $(".sao");
    $(this).bind("contextmenu",function(e){
    	x1= e.pageX;y1= e.pageY;
    	 x=e.pageX+80;y=e.pageY+200;
    	window.clearTimeout(timer);
        console.log(e.pageY);
    	 $(".sao").show(1000);
         $(".saoMenu").slideDown(1000);
         sport();    //调用动画
         $(this).undelegate();   //这里消除所有事件
         $(document).click(function (e) {
             var w = $(".sao").width();
             var h = $(".sao").height();
             if(($(".sao").is(":visible"))&&(w>=width)&&(h>=height)) {    //判断sao的宽高，是否显示
                 var x2 = $(".sao").offset().left;
                 var y2 = $(".sao").offset().top;
                 if(!(e.pageX>x2 && e.pageX<x2+w && e.pageY>y2 && e.pageY<y2+h+30)){ //判断是否在sao外点击
                     $(".saoMenu").hide(500);
                     $(".sao").hide(1000);
                     agin(); //调用当前函数
                 }
             }
         })
    	 return false;
    });
    $(this).mousedown(function(e){
        x1= e.pageX;y1= e.pageY;
       if(e.button==0)      //判断是否为左击
       $(this).mousemove(function(e){
            $(this).mouseup(function(e){
                x=e.pageX;y=e.pageY;
                if((y1+width<y)&&(!$(".sao").is(":visible"))) {   //判断y坐标和sao是否显示
                    window.clearTimeout(timer);
                    $(".sao").show(1000);
                    $(".saoMenu").slideDown(1000);
                    sport();    //调用动画
                    $(this).undelegate();   //这里消除所有事件
                    $(document).click(function (e) {
                        var w = $(".sao").width();
                        var h = $(".sao").height();
                        if(($(".sao").is(":visible"))&&(w>=width)&&(h>=height)) {    //判断sao的宽高，是否显示
                            var x2 = $(".sao").offset().left;
                            var y2 = $(".sao").offset().top;
                            if(!(e.pageX>x2 && e.pageX<x2+w && e.pageY>y2 && e.pageY<y2+h+30)){ //判断是否在sao外点击
                                $(".saoMenu").hide(500);
                                $(".sao").hide(1000);
                                agin(); //调用当前函数
                            }
                        }
                    })
                }
            })
        })
    });

    });

function sport() {
   var sao = document.getElementById("sao");
    if (itime>=0&&bool&&$(".sao").is(":visible")) {
        sao.style.left=(x-100)+"px";sao.style.top=(y-300+itime)+"px";
        itime = itime+1;
        if(itime>=30){
            bool=false;
        }
    }
    if(itime<=30&&!bool&&$(".sao").is(":visible")){
        sao.style.top=(y-300+itime)+"px"; sao.style.left=(x-100)+"px";
        itime = itime-1;
        if(itime<=0){
            bool=true;
        }
    }
   timer =  window.setTimeout(sport,50);
}
