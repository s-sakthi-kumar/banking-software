"use strict";
class Animal {
    name;
    age;
    species;
    constructor(name, age, species) {
        this.name = name;
        this.age = age;
        this.species = species;
    }
    getInfo() {
        return `${this.name} is a ${this.species}.`;
    }
    // Adding the getAge method to access the private age property
    getAge() {
        return this.age;
    }
}
class Dog extends Animal {
    constructor(name, age) {
        super(name, age, 'Dog');
    }
    getDetails() {
        // Accessing age through the getAge method
        return `${this.name} is a ${this.species} and is ${this.getAge()} years old.`;
    }
}
const myDog = new Dog('Buddy', 3);
console.log(myDog.name); // Accessible
console.log(myDog.getInfo()); // Accessible
console.log(myDog.getDetails()); // Accessible
