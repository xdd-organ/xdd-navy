interface Person<T> {
    say();
    talk<T>(t:T) :T;
}

class China<T> implements Person<T> {
    say() {
        console.log("------------");
    }

    talk<T>(t:T) :T{
        return t;
    }
}

var person = new China<string>();
console.log(person.talk("fdsjfl"));


