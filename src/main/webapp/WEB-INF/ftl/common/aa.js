var China = (function () {
    function China() {
    }
    China.prototype.say = function () {
        console.log("------------");
    };
    China.prototype.talk = function (t) {
        return t;
    };
    return China;
}());
var person = new China();
console.log(person.talk("fdsjfl"));
