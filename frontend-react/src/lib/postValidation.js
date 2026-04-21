export function validatePostContent(content) {
  const trimmed = content.trim();

  if (!trimmed) {
    return 'Post content is required.';
  }

  if (trimmed.length > 140) {
    return 'Post content must be 140 characters or less.';
  }

  return '';
}
