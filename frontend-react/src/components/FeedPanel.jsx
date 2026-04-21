export function FeedPanel({ posts, loading }) {
  return (
    <section className="card">
      <h2>Public feed</h2>

      {loading && <p>Loading feed...</p>}

      {!loading && posts.length === 0 && <p>No posts yet.</p>}

      <ul className="feed-list">
        {posts.map((post) => (
          <li key={post.id} className="feed-item">
            <div className="feed-meta">
              <strong>{post.authorName}</strong>
              <span>{post.createdAt}</span>
            </div>
            <p>{post.content}</p>
          </li>
        ))}
      </ul>
    </section>
  );
}
