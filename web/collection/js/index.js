function load_home1() {
    window.location.href = "/home/index.html"
}

function load_home2() {
    window.location.href = "/collection/index.html";
}

function load_home3() {
    window.location.href = "/praise/index.html";
}

function load_home4() {
    window.location.href = "/commentReceive/index.html";
}

function fun() {
    $.ajax({
        url: '/CollectionServlet?type=show',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            var html = '';
            for (var i = 0; i < data.length; i++) {
                html +=
                    '<div class="collect"><img src="'
                    + data[i].portrait
                    + '" alt="" class="portrait"><div class="name"><p class="nickname">'
                    + data[i].nickname
                    + '</p><p class="date">'
                    + data[i].formatCreateTime
                    + '</p></div><p class="text">'
                    + data[i].weiboContent
                    + '</p><img src="'
                    + data[i].img1
                    + '" alt="" class="middle_down_img middle_down_img1"><img src="'
                    + data[i].img2
                    + '" alt="" class="middle_down_img">'
                    + '<div class="bottom"><p><i class="iconfont">'
                    + "&#xe665;"
                    + '</i> '
                    + "已收藏"
                    + '</p>'
                    + '<p><i class="iconfont">'
                    + "&#xe64d;"
                    + '</i> '
                    + "转发"
                    + '</p>'
                    + '<p><i class="iconfont">'
                    + "&#xe643"
                    + '</i> '
                    + data[i].commentNum
                    + '</p>'
                    + '<p class="last" id="'
                    + 'part'
                    + i
                    + '"'
                    + 'onclick=fun'
                    + i
                    + '();'
                    + '><i class="iconfont">'
                    + "&#xe60c;"
                    + '</i> '
                    + data[i].praiseNum
                    + '</p></div></div>';
                noApplicationRecord.innerHTML = html
            }
            var newVar = data.length+1;
            $("#allcollection").html("全部收藏 "+ newVar);
        }
    })
}

window.onload = fun();