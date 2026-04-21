export const appConfig = {
  auth0: {
    domain: import.meta.env.VITE_AUTH0_DOMAIN,
    clientId: import.meta.env.VITE_AUTH0_CLIENT_ID,
    audience: import.meta.env.VITE_AUTH0_AUDIENCE,
    appName: import.meta.env.VITE_AUTH0_APP_NAME,
    redirectUri: import.meta.env.VITE_AUTH0_REDIRECT_URI || window.location.origin
  },
  api: {
    mode: import.meta.env.VITE_API_MODE || 'monolith',
    monolithBaseUrl: import.meta.env.VITE_API_BASE_URL,
    gatewayBaseUrl: import.meta.env.VITE_API_GATEWAY_BASE_URL
  },
  frontend: {
    prodUrl: import.meta.env.VITE_FRONTEND_PROD_URL,
    useCloudFront: import.meta.env.VITE_USE_CLOUDFRONT === 'true'
  }
};
