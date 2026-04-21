export function ProfilePanel({ profile }) {
  if (!profile) {
    return null;
  }

  return (
    <section className="card">
      <h2>My profile</h2>
      <dl className="profile-grid">
        <dt>Sub</dt>
        <dd>{profile.sub}</dd>
        <dt>Name</dt>
        <dd>{profile.name}</dd>
        <dt>Email</dt>
        <dd>{profile.email || 'Not available in token'}</dd>
      </dl>
    </section>
  );
}
