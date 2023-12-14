import React, {useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom'
import './styles.css'

import api from '../../services/api'
import { urlApi } from '../../services/api'

import changeImg from '../../assets/change.png'
import deleteImg from '../../assets/delete.png'

export default function Products(){
    
    const [products, setProducts] = useState([]);
    const token = localStorage.getItem('token');
    const [companyInfo, setCompanyInfo] = useState({});

    useEffect(() => {

        const fetchProducts = async () => {
          try {
            const companyResponse = await api.get('api/company/info', {
                headers: {
                  Authorization: `Bearer ${token}`,
                },
              });
              setCompanyInfo(companyResponse.data);
            const response = await api.get('api/product/find',  {
                headers: {
                  Authorization: `Bearer ${token}`,
                },
            });
            setProducts(response.data.content); 
          } catch (error) {
            console.error('Erro ao buscar produtos:', error);
          }
        };
    
      
        fetchProducts();
      }, []); 
    
      const urlLogoCompany = `${urlApi}api/file/logo/${companyInfo.companyId}` 
      
      return (
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
        <section className='products'>
            <div className='listProducts'>
                <h2>Lista de Produtos</h2>
                <ul>
                    <li>
                        <div className="cardInfo">
                            <p>Nome do produto</p>
                            <p>Categoria</p>
                            <p>Quantidade</p>
                            <p>Preço de custo</p>
                            <p>Preço de venda</p>
                            <p>Alterar</p>
                            <p>Deletar</p>
                        </div>
                    </li>
                    {Array.isArray(products) && products.map(product => (
                        <li key={product.id}>
                            <div className="cardProduct">
                                <p>{product.name}</p>
                                <p>{product.categoryName}</p>
                                <p>{product.quantity}</p>
                                <p>{product.costValue}</p>
                                <p>{product.valueSell}</p>
                                <p><a href=''><img src={changeImg} alt="" /></a></p>
                                <p><a href=""><img src={deleteImg} alt="" /></a></p>
                            </div>
                        </li>
                    ))}
                </ul>
            </div>
        </section>
        </>
      );
}
    

