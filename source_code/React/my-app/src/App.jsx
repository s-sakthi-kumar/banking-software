import './App.css'
import React, { useState } from 'react'
import Dashboard from './myCompnent'

function App() {
  const [boom,setBoom] = useState(false)

  return (
    <>
      <section id="center">
        <h3> Hi Welcome, press Enter</h3>
        <sub>boom boom</sub>
        <Dashboard/>
        {boom?"Hello":"world"}
        <button onClick={()=>{setBoom((bm)=>!bm)}}>Click me !</button>
      </section>
    </>
  )
}

export default App
