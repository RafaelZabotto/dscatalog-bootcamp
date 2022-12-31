import { ReactComponent as AuthImage } from "assets/images/Desenho.svg";
import Teste from "pages/Teste";
import { Navigate, Route, Routes } from 'react-router-dom';

const Auth = () => {

    return (
        <div className="auth-container">
            <div className="auth-banner-container">
                <h1>Divulgue seus produtos no DS Catalog</h1>
                <p>Faça parte do nosso catálogo de divulgação e aumente a venda de seus produtos. </p>
                <AuthImage />
            </div>
            <div className="auth-form-container">
            <Routes>
                <Route path='/admin/auth/login' element={<Teste/>}/>
                <Route path='/signup' />
                <Route path='/recovery' />
            </Routes>
            </div>
        </div>
    );

}

export default Auth;