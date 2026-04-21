export function Header({ isAuthenticated, onLogin, onLogout, user }) {
  return (
    <header className="card header">
      <div>
        <h1>Twitter Secure</h1>
        <p>140-character posts with Auth0 and MongoDB.</p>
      </div>

      <div className="actions">
        {isAuthenticated ? (
          <>
            <span className="welcome">Signed in as {user?.name || 'user'}</span>
            <button onClick={onLogout}>Logout</button>
          </>
        ) : (
          <button onClick={onLogin}>Login</button>
        )}
      </div>
    </header>
  );
}
