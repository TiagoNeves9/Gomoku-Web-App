import { useState } from 'react';


const useAuthentication = () => {
    const [user, setUser] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const login = async (username, password) => {
        setLoading(true);
        try {
            // Fazer a chamada à API
            const response = await fetch('/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });

            if (!response.ok) throw new Error('Error in login');

            // Obter os dados do usuário da resposta
            const userInfo = await response.json();

            // Armazenar as informações do usuário no estado ou em cookies, como preferir
            setUser(userInfo);

            // Retornar os dados do usuário, se necessário
            return userInfo;
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    const logout = () => {
        // Limpar as informações do usuário do estado ou dos cookies
        setUser(null);
    };

    return { user, error, loading, login, logout };
};

export default useAuthentication;