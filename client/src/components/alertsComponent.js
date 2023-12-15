import React, { useState } from 'react';

function AlertComponent() {
  const [error, setError] = useState('Mensagem de erro aqui');

  const errorDialogStyle = {
    position: 'fixed',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    backgroundColor: '#f44336', // Cor de fundo vermelha, você pode ajustar conforme necessário
    color: '#fff',
    padding: '20px',
    width: '300px',
    maxWidth: '80%',
    textAlign: 'center',
    borderRadius: '4px',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)', // Adiciona uma sombra sutil
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

  return (
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
  );
}

export default AlertComponent;
