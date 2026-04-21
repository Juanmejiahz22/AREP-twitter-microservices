export function LoginCard({ onLogin }) {
  return (
    <section className="card">
      <h2>Authentication</h2>
      <p>You need to sign in with Auth0 to create a post and open your profile.</p>
      <button onClick={onLogin}>Sign in with Auth0</button>
    </section>
  );
}
