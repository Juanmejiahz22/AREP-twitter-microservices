import { useEffect, useState } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import { Header } from './components/Header';
import { FeedPanel } from './components/FeedPanel';
import { CreatePostForm } from './components/CreatePostForm';
import { ProfilePanel } from './components/ProfilePanel';
import { LoginCard } from './components/LoginCard';
import { ErrorBanner } from './components/ErrorBanner';
import { apiClient } from './lib/api';

export default function App() {
  const { loginWithRedirect, logout, isAuthenticated, isLoading, getAccessTokenSilently, user } = useAuth0();
  const [feed, setFeed] = useState([]);
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState('');
  const [loadingFeed, setLoadingFeed] = useState(true);

  useEffect(() => {
    loadFeed();
  }, []);

  useEffect(() => {
    if (isAuthenticated) {
      loadFeed();
      loadProfile();
    } else {
      setProfile(null);
      setFeed([]); 
      setLoadingFeed(false);
    }
  }, [isAuthenticated]);
  async function loadFeed() {
    setLoadingFeed(true);

    try {
      const data = await apiClient.listFeed();
      setFeed(data);
      setError('');
    } catch (err) {
      setError(err.message);
    } finally {
      setLoadingFeed(false);
    }
  }

  async function loadProfile() {
    try {
      const token = await getAccessTokenSilently();
      const data = await apiClient.getProfile(token);
      setProfile(data);
      setError('');
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleCreatePost(payload) {
    try {
      const token = await getAccessTokenSilently();
      await apiClient.createPost(token, payload);
      await loadFeed();
      setError('');
    } catch (err) {
      setError(err.message);
    }
  }

  function handleLogin() {
    loginWithRedirect();
  }

  function handleLogout() {
    logout({ logoutParams: { returnTo: window.location.origin } });
  }

  if (isLoading) {
    return <main className="page"><p>Loading authentication...</p></main>;
  }

  return (
    <main className="page">
      <Header
        isAuthenticated={isAuthenticated}
        onLogin={handleLogin}
        onLogout={handleLogout}
        user={user}
      />

      <ErrorBanner message={error} />

      <div className="layout">
        <FeedPanel posts={feed} loading={loadingFeed} />

        <div className="sidebar">
          {isAuthenticated ? (
            <>
              <CreatePostForm onCreate={handleCreatePost} />
              <ProfilePanel profile={profile} />
            </>
          ) : (
            <LoginCard onLogin={handleLogin} />
          )}
        </div>
      </div>
    </main>
  );
}
