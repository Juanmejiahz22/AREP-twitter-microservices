import { useState } from 'react';
import { validatePostContent } from '../lib/postValidation';

export function CreatePostForm({ onCreate, disabled }) {
  const [content, setContent] = useState('');
  const [error, setError] = useState('');

  async function handleSubmit(event) {
    event.preventDefault();
    const validation = validatePostContent(content);

    if (validation) {
      setError(validation);
      return;
    }

    setError('');
    await onCreate({ content: content.trim() });
    setContent('');
  }

  return (
    <section className="card">
      <h2>Create post</h2>

      <form onSubmit={handleSubmit}>
        <textarea
          aria-label="Post content"
          maxLength={140}
          rows={4}
          value={content}
          onChange={(event) => setContent(event.target.value)}
          placeholder="Write something short and public."
        />

        <div className="form-footer">
          <span>{content.length}/140</span>
          <button type="submit" disabled={disabled}>Publish</button>
        </div>

        {error && <p className="form-error">{error}</p>}
      </form>
    </section>
  );
}
