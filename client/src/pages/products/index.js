import React, {useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom'
import Modal from 'react-modal';

import './styles.css'

import api from '../../services/api'
import { urlApi } from '../../services/api'

import changeImg from '../../assets/change.png'
import deleteImg from '../../assets/delete.png'



export default function Products(){
    
    const [products, setProducts] = useState([]);
    const token = localStorage.getItem('token');
    const [companyInfo, setCompanyInfo] = useState({});
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [error, setError] = useState(null);
    const [productIdToDelete, setProductIdToDelete] = useState(null);

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

        const intervalId = setInterval(() => {
            window.location.reload();
          }, 30000);
      
          // Limpa o intervalo quando o componente é desmontado
          return () => clearInterval(intervalId);
      }, []); 
    
      
      const urlLogoCompany = `${urlApi}api/file/logo/${companyInfo.companyId}`
      
      const handleDeleteClick = async (productId) => {
        setProductIdToDelete(productId);
        setModalIsOpen(true);
      };    

      const handleConfirmDelete = async () => {    
        if(productIdToDelete){
          try {
            await api.delete(`api/product/${productIdToDelete}`,{
                headers: {
                  Authorization: `Bearer ${token}`,
                },
              });
            setModalIsOpen(false);
            window.location.reload();
          } catch (error) {
            setModalIsOpen(false);
            if (error.response && error.response.data && error.response.data.message) {
                setError(error.response.data.message);
              } else {
                setError('Erro ao excluir produto. Por favor, tente novamente mais tarde.');
              }
        }}};

        const handleCancelDelete = () => {
            setModalIsOpen(false);
          };

        const ErrorDialog = ({ message, onClose }) => {
        return (
            <div className="error-dialog">
                <p>{message}</p>
                <button onClick={onClose}>OK</button>
            </div>
            );
          };
      
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
                                <p><a href="" onClick={(e) => { e.preventDefault(); handleDeleteClick(product.id); }}><img src={deleteImg} alt="" /></a></p>
                            </div>
                        </li>
                    ))}
                </ul>
                <Modal className="modalConfirm"
                    isOpen={modalIsOpen}
                    onRequestClose={handleCancelDelete}
                    contentLabel="Confirmação de Exclusão">
                    <h2>Deseja realmente deletar este produto?</h2>
                    <button onClick={handleConfirmDelete}>Confirmar</button>
                    <button onClick={handleCancelDelete}>Cancelar</button>
                </Modal>
            </div>
        </section>
        <div>
      {error && (
        <div style={errorDialogStyle}>
          <p>{error}</p>
          <button style={closeButtonStyle} onClick={() => setError(null)}>
            Fechar
          </button>
        </div>
      )}
    </div>
        </>
      );
}
    

const errorDialogStyle = {
    position: 'fixed',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    backgroundColor: '#f44336', // Cor de fundo vermelha, você pode ajustar conforme necessário
    color: '#fff',
    padding: '30px',
    width: '360px',
    maxWidth: '80%',
    textAlign: 'center',
    fontSize: '18px',
    borderRadius: '4px',
    boxShadow: 'rgba(0, 0, 0, 0.35) 0px 5px 15px', // Adiciona uma sombra sutil
  };

  const closeButtonStyle = {
    backgroundColor: '#fff',
    color: '#f44336',
    padding: '8px 12px',
    border: 'none',
    cursor: 'pointer',
    borderRadius: '4px',
    marginTop: '15px',
  };