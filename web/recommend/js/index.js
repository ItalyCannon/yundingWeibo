var img=document.querySelector(".slideshow_img");

function right_img(){
	var newleft;
	if(img.style.left == "-3740px"){
		newleft=-1496;
	}
	else{
		newleft=parseInt(img.style.left)-748;
	}
	img.style.left=newleft+"px";
}

var i=null;
function play(){
	i=setInterval(function(){right_img();},3000);
}
play();

var banner=document.querySelector(".slideshow");
banner.onmouseover=function(){
	clearInterval(i);
}
banner.onmouseleave=function(){
	play();
}