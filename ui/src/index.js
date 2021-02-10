import React from 'react';
import ReactDOM from 'react-dom';
import NavBar from './components/NavBar';
import PurchaseForm from './components/PurchaseForm';
import './index.css'
import { Container, CssBaseline } from '@material-ui/core';

ReactDOM.render(
    <div>
        <NavBar/>
        <CssBaseline/>
        <Container>
            <PurchaseForm />
        </Container>
    </div>,
    document.getElementById('root')
);
