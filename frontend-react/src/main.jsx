import React from 'react';
import ReactDOM from 'react-dom/client';
import { Auth0Provider } from '@auth0/auth0-react';
import App from './App';
import { appConfig } from './config';
import './styles.css';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <Auth0Provider
      domain={appConfig.auth0.domain}
      clientId={appConfig.auth0.clientId}
      authorizationParams={{
        redirect_uri: appConfig.auth0.redirectUri,
        audience: appConfig.auth0.audience
      }}
      cacheLocation="localstorage"
      useRefreshTokens
    >
      <App />
    </Auth0Provider>
  </React.StrictMode>
);
