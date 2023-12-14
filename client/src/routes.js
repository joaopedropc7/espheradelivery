import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";

import Login from "./pages/login";
import Home from "./pages/home"
import Products from "./pages/products"

export default function AppRoutes(){
    return(
      <BrowserRouter>
        <Routes>
            <Route path="/login" element={<Login/>}/>
            <Route path="/home" element={<Home/>}/>
            <Route path="/produtos" element={<Products/>}/>
        </Routes>
      </BrowserRouter>  
    );
}