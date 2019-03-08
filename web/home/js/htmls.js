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
    css.href = "../home/css/style.css"
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

var allCollection;

var PageCode = 1;

function showWeibo() {
    var url = '/HomeWeiboServlet?pc=' + PageCode;
    $.ajax({
        url: '/HomeWeiboServlet?pc=',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            flushAllCollections();

            var data = eval(text);
            var html = '';
            for (var i = 0; i < data.beanList.length; i++) {
                html +=
                    '<div class="part_1"' + ' id="' + data.beanList[i].weiboId + '">' +
                    '<img src="' + data.beanList[i].profilePicture + ' alt="" class="portrait">' +
                    '<div class="name">' +
                    '<p class="nickname">' + data.beanList[i].nickname + '</p>' +
                    '<p class="date">' + data.beanList[i].formatCreateTime + '</p>' +
                    '</div>' +
                    '<p class="text">' + data.beanList[i].weiboContent + '<span>' + '' + '</span></p>' +
                    '<img src="./img/portrait_1.jpg" alt="img" class="middle_down_img middle_down_img1">' +
                    '<img src="./img/portrait_1.jpg" alt="img" class="middle_down_img middle_down_img2">' +
                    '<div class="bottom">' +
                    '<p onclick="collection(' + data.beanList[i].weiboId + ')" id="' + data.beanList[i].weiboId + 'c'
                    + '"><i class="iconfont">&#xe665;</i> '
                    + collectionCondition(data.beanList[i].weiboId) + '</p>'
                    + '<p onclick="repost(' + data.beanList[i].weiboId + ')"><i class="iconfont" id="' + data.beanList[i].weiboId + 'r' + '">&#xe64d;</i> 转发</p>'
                    + '<p onclick="showComment(' + data.beanList[i].weiboId + ')"><i class="iconfont">&#xe643;</i> ' + data.beanList[i].commentNum + '</p>' +
                    '<p class="last" onclick="praise(' + data.beanList[i].weiboId + ')"><i class="iconfont">&#xe60c;</i> ' + data.beanList[i].praiseNum + '</p>' +
                    '</div>' + '</div>';
                noApplicationRecord.innerHTML = html
            }
        }
    });
    PageCode++;
}

function flushAllCollections() {
    $.ajax({
        url: '/CollectionServlet?type=show',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            allCollection = eval(text);
        },
        async: false
    });
}

function repost(weiboId) {
    $.ajax({
        url: '/RepostServlet?type=add',
        type: 'get',
        dataType: 'json',
        data: {
            weibo: weiboId
        },
        success: {},
        async: false
    });
    alert("转发成功!");
}

function collectionCondition(weiboId) {
    for (var q = 0; q < allCollection.length; ++q) {
        if (allCollection[q].weiboId === weiboId) {
            return "已收藏";
        }
    }
    return "收藏";
}

function showComment(weiboId) {
    var weiboDiv = document.getElementById(weiboId);

}

function praise(weiboId) {
    $.ajax({
        url: '/PraiseServlet?type=weibo',
        type: 'get',
        dataType: 'json',
        data: {
            weibo: weiboId
        },
        success: {},
        async: false
    });

}

function collection(weiboId) {
    var url;
    var collention = document.getElementById(weiboId + "c");
    if (collectionCondition(weiboId) === "收藏") {
        url = "/CollectionServlet?type=add";
        collention.innerHTML = '<i class="iconfont">&#xe665;</i>' + "已收藏";
        allCollection.push({"weiboId": weiboId});
    } else {
        url = "/CollectionServlet?type=delete";
        collention.innerHTML = '<i class="iconfont">&#xe665;</i>' + "收藏";
        for (var b = 0; b < allCollection.length; ++b) {
            if (allCollection[b].weiboId === weiboId) {
                allCollection.splice(b, 1);
                break;
            }
        }
    }
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',
        data: {
            weibo: weiboId
        },
        success: function (text) {
            alert(text);
        },
        async: false
    })
}

function showTopic() {

}

//获取列表中的原有内容
var content = document.getElementById("noApplicationRecord").innerHTML;

//每被调用一次，就将网页原有内容添加一份，这个大家可以写自己要加载的内容或指令
function addLi() {
    document.getElementById("noApplicationRecord").innerHTML += content;
}

/*
 * 监听滚动条，本来不想用jQuery但是发现js里面监听滚动条的事件不好添加，这边就引用了Jquery的$(obj).scroll();这个方法了
 */
$(window).scroll(function () {
    //下面这句主要是获取网页的总高度，主要是考虑兼容性所以把Ie支持的documentElement也写了，这个方法至少支持IE8
    var htmlHeight = document.body.scrollHeight || document.documentElement.scrollHeight;
    var number = parseInt(htmlHeight);
    number -= 10;
    //clientHeight是网页在浏览器中的可视高度，
    var clientHeight = document.body.clientHeight || document.documentElement.clientHeight;
    //scrollTop是浏览器滚动条的top位置，
    var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
    //通过判断滚动条的top位置与可视网页之和与整个网页的高度是否相等来决定是否加载内容；
    if (scrollTop + clientHeight >= number) {
        showWeibo();
    }
});


window.onload = function () {
    // showWeibo();
};
