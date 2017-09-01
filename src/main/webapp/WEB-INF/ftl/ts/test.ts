//定义一个类
class User{
    //属性
    name:string;

    //有参构造方法
    constructor(name:string) {
        this.name = name;
    }
    //无返回值
    say() : void{

    }

    //有返回值得
    hello(name : string) : string {
        return name;
    }

    //泛型
    word <T> (name : T) : T {
        return name;
    }
}

var user = new User("abc");
alert(user.hello("123456"));
