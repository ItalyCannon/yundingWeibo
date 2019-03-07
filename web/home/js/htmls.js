function load_home1() {
    var one = document.getElementById("1");
    one.style.display = 'block';
    var two = document.getElementById("2");
    two.style.display = 'none';
    var two = document.getElementById("3");
    two.style.display = 'none';
    var two = document.getElementById("4");
    two.style.display = 'none';
    var css = document.getElementById("css1");
    css.href = "../首页/css/style.css"
}

function load_home2() {
    var one = document.getElementById("1");
    one.style.display = 'none';
    var two = document.getElementById("2");
    two.style.display = 'block';
    var two = document.getElementById("3");
    two.style.display = 'none';
    var two = document.getElementById("4");
    two.style.display = 'none';
    var css = document.getElementById("css1");
    css.href = "../我的收藏/css/style.css"
}

function load_home3() {
    var one = document.getElementById("1");
    one.style.display = 'none';
    var two = document.getElementById("2");
    two.style.display = 'none';
    var two = document.getElementById("3");
    two.style.display = 'block';
    var two = document.getElementById("4");
    two.style.display = 'none';
    var css = document.getElementById("css1");
    css.href = "../我的点赞/css/style.css"
}

function load_home4() {
    var one = document.getElementById("1");
    one.style.display = 'none';
    var two = document.getElementById("2");
    two.style.display = 'none';
    var two = document.getElementById("3");
    two.style.display = 'none';
    var two = document.getElementById("4");
    two.style.display = 'block';
    var css = document.getElementById("css1");
    css.href = "../我的评论（发出）/css/style.css"
}

function showWeibo() {
    $.ajax({
        url: '/HomeWeiboServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            var html = '';
            for (var i = 0; i < data.beanList.length; i++) {
                alert(data.beanList[i]);
                alert(data.beanList[i].nickname);
                html +=
                    '<div class="part_2">' +
                    '<img src="' + data.beanList[i].profilePicture + ' alt="" class="portrait">' +
                    '<div class="name">' +
                    '<p class="nickname">' + data.benList.nickname + '</p>' +
                    '<p class="date">' + '今天 14:12' + '</p>' +
                    '</div>' +
                    '<p class="text">' + '素质过硬的图片' + '<span>' + '#这个是话题#' + '</span></p>' +
                    '<img src="./img/portrait_1.jpg" alt="img" class="middle_down_img middle_down_img1">' +
                    '<img src="./img/portrait_1.jpg" alt="img" class="middle_down_img middle_down_img2">' +
                    '<div class="bottom">' +
                    '<p onclick="collection(' + data[i].weiboId + ')"><i class="iconfont" id="' + data[i].weiboId + 'c' + '">&#xe665;</i> 收藏</p>'
                    + '<p onclick="repost()"><i class="iconfont" id="' + data[i].weiboId + 'r' + '">&#xe64d;</i> 转发</p>'
                    + '<p onclick="显示评论()"><i class="iconfont">&#xe643;</i> ' + '32' + '</p>' +
                    '<p class="last" onclick="点赞()"><i class="iconfont">&#xe60c;</i> ' + '12' + '</p>' +
                    '</div> </div>';
                noApplicationRecord.innerHTML = html
            }
        }
    })
}

$("#collection").click(
    function () {
        var url;
        if ($("#collection").html() === "收藏") {
            url = "/CollectionServlet?type=add";
            $("#collection").html("已收藏");
        } else {
            url = "/CollectionServlet?type=delete";
            $("#collection").html("收藏");
        }

        $.ajax({
            url: url,
            type: 'get',
            dataType: 'json',
            data: {},
            success: {}
        })
    }
);

// //获取列表中的原有内容
// var content = document.getElementById("img").innerHTML;
//
// //每被调用一次，就将网页原有内容添加一份，这个大家可以写自己要加载的内容或指令
// function addLi() {
//     document.getElementById("img").innerHTML += content;
// }
//
// /*
//  * 监听滚动条，本来不想用jQuery但是发现js里面监听滚动条的事件不好添加，这边就引用了Jquery的$(obj).scroll();这个方法了
//  */
// $(window).scroll(function () {
//     //下面这句主要是获取网页的总高度，主要是考虑兼容性所以把Ie支持的documentElement也写了，这个方法至少支持IE8
//     var htmlHeight = document.body.scrollHeight || document.documentElement.scrollHeight;
//     var number = parseInt(htmlHeight);
//     number -= 10;
//     //clientHeight是网页在浏览器中的可视高度，
//     var clientHeight = document.body.clientHeight || document.documentElement.clientHeight;
//     //scrollTop是浏览器滚动条的top位置，
//     var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
//     //通过判断滚动条的top位置与可视网页之和与整个网页的高度是否相等来决定是否加载内容；
//     if (scrollTop + clientHeight >= number) {
//         // addLi();
//     }
// });


window.onload = showWeibo();