interface Person{
    say (s : number) :number;
}

class China implements Person {
    talk<T>(t: T): T {
        return undefined;
    }
    say(s: number) :number{
        console.log("say2方法！");
        return 1;
    }

    tell() {
        console.log("tell");
    }
}

class Japan implements Person{
    say(s: number) :number {
        console.log("say3方法！");
        return 2;
    }
}


