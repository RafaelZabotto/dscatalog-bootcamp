import Teste from 'pages/Teste';
import { Navigate, Route, Routes } from 'react-router-dom';
import Navbar from './Navbar';
import './styles.css';

const Admin = () => {
  return (
    <div className='admin-container'>
      <Navbar />
      <div className='admin-content'>
        <Routes>
          <Route path='products' element={<Teste/>} />
          <Route path='categories'  />
          <Route path='users'/>
        </Routes>
      </div>
    </div>
  );
};

export default Admin;
