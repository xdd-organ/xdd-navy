var China = (function () {
    function China() {
    }
    China.prototype.talk = function (t) {
        return undefined;
    };
    China.prototype.say = function (s) {
        console.log("say2方法！");
        return 1;
    };
    China.prototype.tell = function () {
        console.log("tell");
    };
    return China;
}());
var Japan = (function () {
    function Japan() {
    }
    Japan.prototype.say = function (s) {
        console.log("say3方法！");
        return 2;
    };
    return Japan;
}());
