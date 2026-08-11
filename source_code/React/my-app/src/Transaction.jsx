import React, { useState, useEffect } from 'react';

const Transaction = () => {

  const [balance, setBalance] = useState(100.0);
  const [messages, setMessage] = useState(["Transaction: "]);


  useEffect(() => {
    const handleKeyDown = (e) => {
      if (e.key === "Enter") {
        console.log("Enter pressed");
        setBalance((bal) => (bal + 100));
        setMessage((msgs) => [...msgs, "Added $$100"]);
      }
    };

    window.addEventListener("keydown", handleKeyDown);

    return () => {
      window.removeEventListener("keydown", handleKeyDown);
    };
  }, []);


  useEffect(() => {
    if (balance > 1000) {
      setMessage((msgs) => [...msgs, "Getting Rich"]);
    }
  }, [balance]);
  return (
    <div>
      {(balance > 1000) && <div>$$Dollar$$</div>}
      Balance: {balance}<br />
      <button onClick={() => {
        setBalance((bal) => (bal + 100));
        setMessage((msgs) => [...msgs, "Added $$100"]);
      }}>ADD $$</button>

      {messages.map(element => (
        <div>{element}</div>
      ))}
    </div>
  )
};

export default Transaction;
