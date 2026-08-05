
interface User {
    name: string;
    age?: number;
}

const user1: User = {
    name: "Bob"
};

const user2: User = {
    name: "Alice",
    age: 30
};

console.log(user1, user2);

class Person {
    name: string;
    age: number;

    // Default constructor
    constructor() {
        this.name = "Unknown";
        this.age = 0;
    }
}

const person1 = new Person();

console.log(person1);
