import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom'
import './styles.css'

import api from '../../services/api'

export default function Home(){
    return(
        
        <header>
            <nav>
                <ul>
                    <div className="init">
                        <li></li>
                    </div>
                    <div className="content">
                        <li><a href="#">Pedidos</a></li>
                        <li><a href="#">Produtos</a></li>
                        <li><a href="#">Comandas</a></li>
                        <li><a href="#">Mesas</a></li>
                        <li><a href="#">Entregas</a></li>
                        <li><a href="#">Configurações</a></li>
                        <li><a href="#">Logout</a></li>
                    </div>
                </ul>
            </nav>
        </header>
    );
}