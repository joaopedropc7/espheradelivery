import React, {useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom'
import './styles.css'

import api from '../../services/api'
import { urlApi } from '../../services/api'

export default function Home(){

    const [companyInfo, setCompanyInfo] = useState({});
    const [orders, setOrders] = useState([]);
    const token = localStorage.getItem('token');

    useEffect(() => {
        const fetchData = async () => {
          try {
            const companyResponse = await api.get('api/company/info', {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            });
            setCompanyInfo(companyResponse.data);
    
            const ordersResponse = await api.get('api/sell/findall', {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            });
            setOrders(ordersResponse.data.content);
          } catch (error) {
            console.error('Erro ao buscar dados:', error);
          }
        };

        fetchData();

        const intervalId = setInterval(fetchData, 30000);

        return () => clearInterval(intervalId); 


      }, []);
      

    const urlLogoCompany = `${urlApi}api/file/logo/${companyInfo.companyId}` 



    return(
        <>
        <header>
            <nav>
                <ul>
                    <div className="init">
                        <li><a href="/home"><figure><img src={urlLogoCompany} alt="" /></figure></a></li>
                        <li><a href="/home">{companyInfo.companyName}</a></li>
                    </div>
                    <div className="content">
                        <li><a href="#">Pedidos</a></li>
                        <li><a href="/produtos">Produtos</a></li>
                        <li><a href="#">Comandas</a></li>
                        <li><a href="#">Mesas</a></li>
                        <li><a href="#">Entregas</a></li>
                        <li><a href="#">Configurações</a></li>
                        <li><a href="#">Logout</a></li>
                    </div>
                </ul>
            </nav>
        </header>
        <section className='orders'>
            <div className='orderslist'>
                <ul>
                {Array.isArray(orders) && orders.map(order => (
                    <li key={order.id}>
                        <div className="ordercard">
                            <div className="content">
                                <p>Cliente: {order.clientName}</p>
                                <p>Valor total: {order.sellValue}</p>
                                <p>Valor com desconto:{order.sellValueWithDescount}</p>
                                <p>Desconto: {order.discount}%</p>
                                <p>{order.typeDelivery}</p>
                                <p>Status: {order.statusOrder}</p>
                                <p>Hora do pedido: {formatISODate(order.dateStartOrder)}</p>
                            </div>
                            <div className={`statusOrder ${getStatusColorClass(order.statusOrder)}`}>
                                {renderButtonBasedOnStatus(order.statusOrder)}
                            </div>
                        </div>
                    </li>
            ))}
                </ul>
            </div>
        </section>
    
        </>
    );

    function renderButtonBasedOnStatus(status) {
        if (status === 'Recebido') {
            return <p>RECEBIDO</p>;
        } else if (status === 'EmPreparo') {
            return <p>Em Preparo</p>;
        } else if (status === 'ProntoRetirada') {
            return <p>Aguardando Retirada</p>;
        } else if (status === 'Rota') {
            return <p>Em Rota</p>;
        } else if (status === 'Finalizado') {
            return <p>Finalizado</p>;
        } else if (status === 'Cancelado') {
            return <p>Cancelado</p>;
        } else {
            return null; 
        }
    }

    function getStatusColorClass(status) {
    switch (status) {
        case 'Recebido':
            return 'recebido-color';
        case 'EmPreparo':
            return 'preparo-color';
        case 'ProntoRetirada':
            return 'retirada-color';
        case 'Rota':
            return 'rota-color'; 
        case 'Finalizado':
            return 'finalizado-color';
        case 'Cancelado':
            return 'cancelado-color';
        default:
            return '';
        }
      }

    function formatISODate(isoDate) {
        const date = new Date(isoDate);
    
        
    const options = {
        hour: 'numeric',
        minute: 'numeric'
    };

    
        return new Intl.DateTimeFormat('pt-BR', options).format(date);
    }
}