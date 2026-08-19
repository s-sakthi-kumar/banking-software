import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "./pages/Home";
import Accounts from "./pages/Accounts";
import About from "./pages/About";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/accounts" element={<Accounts />} />
                <Route path="/about" element={<About />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
