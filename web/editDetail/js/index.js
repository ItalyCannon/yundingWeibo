function load_home1() {
    window.location.href = "/home"
}

function load_home2() {
    window.location.href = "/collection";
}

function load_home3() {
    window.location.href = "/praise";
}

function load_home4() {
    window.location.href = "/commentReceive";
}

function show_list() {
    window.location.href = "/list";
}

function loadDetail() {
    window.location.href = "/detail"
}

function load_attention() {
    window.location.href = "/attention"
}

function load_fans() {
    window.location.href = "/fans"
}

function load_space() {
    window.location.href = "/space"
}

var userId;
var oldUserInfo;

function getInfo() {
    $.ajax({
        url: '/UserDetailsServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            oldUserInfo = data;
            userId = data.userId;
            $("#profilePicture").attr('src', data.profilePicture);
            $("#profile1").attr('src', data.profilePicture);
            $("#profilePictureEdit").attr('src', data.profilePicture);
            $("#nicknameLeft").html(data.nickname);
            $("#signatureLeft").html(data.signature);
            $("#attentionNum").html(data.subscribeNum);
            $("#fansNum").html(data.fansNum);
            $("#weiboNum").html(data.weiboNum);
            $("#loginId").html(data.loginId);

            $("#nicknameRight").attr("value", data.nickname);
            $("#realName").attr("value", data.realName);
            var gender = data.gender;
            if (gender !== undefined || gender != null) {
                switch (gender) {
                    case 0:
                        $("#gender option[value='0']").attr("selected", true);
                        break;
                    case 1:
                        $("#gender option[value='1']").attr("selected", true);
                        break;
                    case 2:
                        $("#gender option[value='2']").attr("selected", true);
                        break;
                    default:
                }
            }

            $("#location").html(data.location);
            var location = data.location;
            if (location !== undefined && location != null && location !== "" && location.trim() !== "-") {
                var strings = location.split("-");

                $("#province option[value=" + strings[0] + "]").attr("selected", true);
                //触发onchange事件
                $('#province').trigger('change');
                $("#city option[value=" + strings[1] + "]").attr("selected", true);

            }
            var sexualOrientation = data.sexualOrientation;
            switch (sexualOrientation) {
                case 0:
                    $("#sexualOrientation option[value='0']").attr("selected", true);
                    break;
                case 1:
                    $("#sexualOrientation option[value='1']").attr("selected", true);
                    break;
                case 2:
                    $("#sexualOrientation option[value='2']").attr("selected", true);
                    break;
                default:
            }

            var emotionalState = data.emotionalState;
            switch (emotionalState) {
                case 0:
                    $("#emotionalState option[value='0']").attr("selected", true);
                    break;
                case 1:
                    $("#emotionalState option[value='1']").attr("selected", true);
                    break;
                case 2:
                    $("#emotionalState option[value='2']").attr("selected", true);
                    break;
                default:
            }

            if (data.formatBirthday !== undefined || data.formatBirthday != null || data.formatBirthday != "") {
                var split = data.formatBirthday.split("-");
                for (var m = 1; m < split.length; ++m) {
                    if (split[m][0] == "0") {
                        split[m] = split[m].slice(1, 2);
                    }
                }
                $("#year option[value=" + split[0] + "]").attr("selected", true);
                $("#month option[value=" + split[1] + "]").attr("selected", true);
                $("#day option[value=" + split[2] + "]").attr("selected", true);
            }

            $("#signatureRight").attr("value", data.signature);
            $("#registrationTime").html(data.formatRegistrationTime);
            $("#email").attr("value", data.email);
            $("#qq").attr("value", data.qq);
            $("#undergraduateSchool").attr("value", data.undergraduateSchool);
            $("#graduateSchool").attr("value", data.graduateSchool);

            // var tags = data.tag;
            // for (var i = 0; i < tags.length; ++i) {
            //     $("#tag").append('<p class="items">' + tags[i] + '</p>');
            // }
        },
        async: false
    });
}

function query() {
    var user = {};
    user.userId = userId;
    user.nickname = $("#nicknameRight").val();
    user.realName = $("#realName").val();
    user.signature = $("#signatureRight").val();
    user.email = $("#email").val();
    user.qq = $("#qq").val();
    user.undergraduateSchool = $("#undergraduateSchool").val();
    user.graduateSchool = $("#graduateSchool").val();
    user.gender = $("#gender option:selected").val();
    user.sexualOrientation = $("#sexualOrientation option:selected").val();
    user.emotionalState = $("#emotionalState option:selected").val();

    var location = $("#province option:selected").val() + "-" + $("#city option:selected").val();
    if (location.indexOf("请") != -1) {
        user.location = "";
    } else {
        user.location = location;
    }

    var year = $("#year option:selected").val();
    var month = $("#month option:selected").val();
    if (month.length == 1) {
        month = "0" + month;
    }
    var day = $("#day option:selected").val();
    if (day.length == 1) {
        day = "0" + day;
    }
    user.birthday = year + "-" + month + "-" + day;
    user.subscribeNum = oldUserInfo.subscribeNum;
    user.fansNum = oldUserInfo.fansNum;
    user.weiboNum = oldUserInfo.weiboNum;
    console.log(user);
    $.ajax({
        url: '/EditUserInfoServlet',
        type: 'get',
        // dataType: 'json',
        data: {
            user: JSON.stringify(user)
        },
        success: function () {
            alert("保存成功");
            loadDetail();
            // getInfo();
        },
        async: true
    });
}

function change() {
    $.ajax({
        url: "/BackgroundServlet",
        type: "get",
        datatype: "json",
        data: {},
        success: function (data) {
            var text = eval(data);
            // var content = document.getElementById("content");
            // alert(content.style.backgroundImage);
            $(".main").css("background-image", "url(" + '"' + text.img + '"' + ")");
            // var content = $("#content").attr("class");
        }

    })
}


window.onload = function () {
    GetProvince();
    change();
    YYYYMMDDstart();
    getInfo();
};


var year = document.getElementById("year");
var month = document.getElementById("month");
var day = document.getElementById("day");

var changeDD = 1;//->一个全局变量
function YYYYMMDDstart() {
    MonHead = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    //先给年下拉框赋内容
    var y = new Date().getFullYear();
    for (var i = (y - 47); i < (y + 21); i++) //以今年为准，前30年，后30年
        year.options.add(new Option(i, i));
    //赋月份的下拉框
    for (var i = 1; i < 13; i++)
        month.options.add(new Option(i, i));
    year.value = y;
    month.value = new Date().getMonth() + 1;
    var n = MonHead[new Date().getMonth()];
    if (new Date().getMonth() == 1 && IsPinYear(YYYYvalue)) n++;
    writeDay(n); //赋日期下拉框
}

function YYYYDD(str) //年发生变化时日期发生变化(主要是判断闰平年)
{
    var MMvalue = month.options[month.selectedIndex].value;
    if (MMvalue == "") {
//            var e = day;
        optionsClear(e);
        return;
    }
    var n = MonHead[MMvalue - 1];
    if (MMvalue == 2 && IsPinYear(str)) n++;
    writeDay(n)
}

function MMDD(str) //月发生变化时日期联动
{
    var YYYYvalue = year.options[year.selectedIndex].value;
    if (YYYYvalue == "") {
        var e = day;
        optionsClear(e);
        return;
    }
    var n = MonHead[str - 1];
    if (str == 2 && IsPinYear(YYYYvalue)) n++;
    writeDay(n)
}

function writeDay(n) //据条件写日期的下拉框
{
    var e = day;
    optionsClear(e);
    for (var i = 1; i < (n + 1); i++) {
        e.options.add(new Option(i, i));
        if (i == changeDD) {
            e.options[i].selected = true;  //->保持选中状态
        }
    }
    e.remove(0);
}

function IsPinYear(year) //判断是否闰平年
{
    return (0 == year % 4 && (year % 100 != 0 || year % 400 == 0));
}

function optionsClear(e) {
    e.options.length = 1;
}

//->随时监听日的改变
function DDD(str) {
    changeDD = str;
}


var objprovince = document.getElementById("province");
var objcity = document.getElementById("city");

var parray = Array(
    "北京",
    "上海",
    "天津",
    "重庆",
    "河北",
    "山西",
    "内蒙古",
    "辽宁",
    "吉林",
    "黑龙江",
    "江苏",
    "浙江",
    "安徽",
    "福建",
    "江西",
    "山东",
    "河南",
    "湖北",
    "湖南",
    "广东",
    "广西",
    "海南",
    "四川",
    "贵州",
    "云南",
    "西藏",
    "陕西",
    "甘肃",
    "宁夏",
    "青海",
    "新疆",
    "香港",
    "澳门",
    "台湾"
);

var carray = Array(
    "东城,西城,崇文,宣武,朝阳,丰台,石景山,海淀,门头沟,房山,通州,顺义,昌平,大兴,平谷,怀柔,密云,延庆",
    "黄浦,卢湾,徐汇,长宁,静安,普陀,闸北,虹口,杨浦,闵行,宝山,嘉定,浦东,金山,松江,青浦,南汇,奉贤,崇明",
    "和平,东丽,河东,西青,河西,津南,南开,北辰,河北,武清,红挢,塘沽,汉沽,大港,宁河,静海,宝坻,蓟县,大邱庄",
    "万州,涪陵,渝中,大渡口,江北,沙坪坝,九龙坡,南岸,北碚,万盛,双挢,渝北,巴南,黔江,长寿,綦江,潼南,铜梁,大足,荣昌,壁山,梁平,城口,丰都,垫江,武隆,忠县,开县,云阳,奉节,巫山,巫溪,石柱,秀山,酉阳,彭水,江津,合川,永川,南川",
    "石家庄,邯郸,邢台,保定,张家口,承德,廊坊,唐山,秦皇岛,沧州,衡水",
    "太原,大同,阳泉,长治,晋城,朔州,吕梁,忻州,晋中,临汾,运城",
    "呼和浩特,包头,乌海,赤峰,呼伦贝尔盟,阿拉善盟,哲里木盟,兴安盟,乌兰察布盟,锡林郭勒盟,巴彦淖尔盟,伊克昭盟",
    "沈阳,大连,鞍山,抚顺,本溪,丹东,锦州,营口,阜新,辽阳,盘锦,铁岭,朝阳,葫芦岛",
    "长春,吉林,四平,辽源,通化,白山,松原,白城,延边",
    "哈尔滨,齐齐哈尔,牡丹江,佳木斯,大庆,绥化,鹤岗,鸡西,黑河,双鸭山,伊春,七台河,大兴安岭",
    "南京,镇江,苏州,南通,扬州,盐城,徐州,连云港,常州,无锡,宿迁,泰州,淮安",
    "杭州,宁波,温州,嘉兴,湖州,绍兴,金华,衢州,舟山,台州,丽水",
    "合肥,芜湖,蚌埠,马鞍山,淮北,铜陵,安庆,黄山,滁州,宿州,池州,淮南,巢湖,阜阳,六安,宣城,亳州",
    "福州,厦门,莆田,三明,泉州,漳州,南平,龙岩,宁德",
    "南昌市,景德镇,九江,鹰潭,萍乡,新馀,赣州,吉安,宜春,抚州,上饶",
    "济南,青岛,淄博,枣庄,东营,烟台,潍坊,济宁,泰安,威海,日照,莱芜,临沂,德州,聊城,滨州,菏泽,博兴",
    "郑州,开封,洛阳,平顶山,安阳,鹤壁,新乡,焦作,濮阳,许昌,漯河,三门峡,南阳,商丘,信阳,周口,驻马店,济源",
    "武汉,宜昌,荆州,襄樊,黄石,荆门,黄冈,十堰,恩施,潜江,天门,仙桃,随州,咸宁,孝感,鄂州",
    "长沙,常德,株洲,湘潭,衡阳,岳阳,邵阳,益阳,娄底,怀化,郴州,永州,湘西,张家界",
    "广州,深圳,珠海,汕头,东莞,中山,佛山,韶关,江门,湛江,茂名,肇庆,惠州,梅州,汕尾,河源,阳江,清远,潮州,揭阳,云浮",
    "南宁,柳州,桂林,梧州,北海,防城港,钦州,贵港,玉林,南宁地区,柳州地区,贺州,百色,河池",
    "海口,三亚",
    "成都,绵阳,德阳,自贡,攀枝花,广元,内江,乐山,南充,宜宾,广安,达川,雅安,眉山,甘孜,凉山,泸州",
    "贵阳,六盘水,遵义,安顺,铜仁,黔西南,毕节,黔东南,黔南",
    "昆明,大理,曲靖,玉溪,昭通,楚雄,红河,文山,思茅,西双版纳,保山,德宏,丽江,怒江,迪庆,临沧",
    "拉萨,日喀则,山南,林芝,昌都,阿里,那曲",
    "西安,宝鸡,咸阳,铜川,渭南,延安,榆林,汉中,安康,商洛",
    "兰州,嘉峪关,金昌,白银,天水,酒泉,张掖,武威,定西,陇南,平凉,庆阳,临夏,甘南",
    "银川,石嘴山,吴忠,固原",
    "西宁,海东,海南,海北,黄南,玉树,果洛,海西",
    "乌鲁木齐,石河子,克拉玛依,伊犁,巴音郭勒,昌吉,克孜勒苏柯尔克孜,博 尔塔拉,吐鲁番,哈密,喀什,和田,阿克苏",
    "香港",
    "澳门",
    "台北,高雄,台中,台南,屏东,南投,云林,新竹,彰化,苗栗,嘉义,花莲,桃园,宜兰,基隆,台东,金门,马祖,澎湖"
);
var i;
objprovince.onchange = function GetCity() {
    var x = this.selectedIndex - 1;
    var cityLength = objcity.length;
    for (var i = 0; i < cityLength; i++) {
        objcity.remove(0);
    }

    if (x >= 0) {
        var citylist = carray[x].split(',');

        for (i = 0; i < citylist.length; i++) {
            objcity.options[i] = new Option(citylist[i], citylist[i]);
        }
    } else {
        objcity.options[0] = new Option("请选择城市", "请选择城市");
    }
};

function GetProvince() {
    objprovince.options[0] = new Option("请选择省份", "请选择省份");
    for (i = 0; i < parray.length; i++) {
        objprovince.options[i + 1] = new Option(parray[i], parray[i]);
    }
}

$(document).ready(function () {
    $('#select').click(function () {
        $('#img_upload').click();
    });
});