import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom'
import './styles.css'

import api from '../../services/api'

import logo from '../../assets/login-background.jpg'
import logoEsphera from '../../assets/logo-esphera.png'



export default function Login(){


    const [email, setEmail] = useState(''); 
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    async function login(e){
        e.preventDefault();

        const data = {
            email,
            password,
        };

        try {
            const response = await api.post('api/authentication/login', data);
            localStorage.setItem('token', response.data.token);
            navigate('/home')
        } catch (error) {
            console.log(error)
            alert("Falha no login")
        }
    }

    return (
        <div className="login-container">
            <div className="image">
                <figure><img src={logo} alt="imageLogo" /></figure>
            </div>
            <div className="form">
                <form onSubmit={login}>
                    <figure><img src={logoEsphera} alt="" /></figure>
                    <h1>Login</h1>
                    <input 
                     placeholder='Email' 
                     value={email}
                     onChange={e => setEmail(e.target.value)}
                     />
                    <input 
                     type="password" placeholder='Senha' 
                     value={password}
                     onChange={e => setPassword(e.target.value)}
                     />
                    <button type='submit'>Entrar</button>
                </form>
            </div>
        </div>  
    )
}