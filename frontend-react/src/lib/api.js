import { appConfig } from '../config';

function currentBaseUrl() {
  return appConfig.api.mode === 'api-gateway'
    ? appConfig.api.gatewayBaseUrl
    : appConfig.api.monolithBaseUrl;
}

function resolvePath(key) {
  const paths = {
    feed: appConfig.api.mode === 'api-gateway' ? '/feed' : '/api/feed',
    posts: appConfig.api.mode === 'api-gateway' ? '/posts' : '/api/posts',
    me: appConfig.api.mode === 'api-gateway' ? '/me' : '/api/me'
  };

  return `${currentBaseUrl()}${paths[key]}`;
}

async function send(url, options = {}) {
  const response = await fetch(url, {
    headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
    ...options
  });

  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || 'Request failed.');
  }

  return response.status === 204 ? null : response.json();
}

export const apiClient = {
  listFeed(token) {
    return send(resolvePath('feed'), {
      headers: { Authorization: `Bearer ${token}` }
    });
  },
  createPost(token, payload) {
    return send(resolvePath('posts'), {
      method: 'POST',
      body: JSON.stringify(payload),
      headers: { Authorization: `Bearer ${token}` }
    });
  },
  getProfile(token) {
    return send(resolvePath('me'), {
      headers: { Authorization: `Bearer ${token}` }
    });
  }
};

export function apiPathsForTests() {
  return {
    baseUrl: currentBaseUrl(),
    feed: resolvePath('feed'),
    posts: resolvePath('posts'),
    me: resolvePath('me')
  };
}
