import React from 'react';


function Account() {
   const user = "from prop drilling";
   return <Parent user={user} />;
}


function Parent({ user }) {
   return <Child user={user} />;
}


function Child({ user }) {
   return <GrandChild user={user} />;
}


function GrandChild({ user }) {
   return <p>Hello {user}</p>;
}

export default Account;

