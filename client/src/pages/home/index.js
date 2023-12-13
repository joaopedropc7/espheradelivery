import React, {useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom'
import './styles.css'

import api from '../../services/api'
import { urlApi } from '../../services/api'

export default function Home(){

    const [companyInfo, setCompanyInfo] = useState({});
    const [companyLogo, setCompanyLogo] = useState(null);
    const token = localStorage.getItem('token');

    useEffect(() => {
      api.get('api/company/info', {
        headers: {
            Authorization: `Bearer ${token}`
        }
      }).then(response => {
        setCompanyInfo(response.data)
      })
    });

    const urlLogoCompany = `${urlApi}api/file/logo/${companyInfo.companyId}` 



    return(
        
        <header>
            <nav>
                <ul>
                    <div className="init">
                        <li><figure><img src={urlLogoCompany} alt="" /></figure></li>
                        <li>{companyInfo.companyName}</li>
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