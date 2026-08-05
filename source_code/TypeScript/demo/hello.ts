function greet(name: string): string {
  return `Hello, ${name}!`;
}

const message: string = greet("World");

console.log(message);


interface User {
    name: string;
    age: number;
}

const user: User = {
    name: "Alice",
    age: 25
};

console.log(user);

