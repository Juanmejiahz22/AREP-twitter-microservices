import { describe, expect, it } from 'vitest';
import { validatePostContent } from '../lib/postValidation';

describe('validatePostContent', () => {
  it('rejects empty content', () => {
    expect(validatePostContent('   ')).toBe('Post content is required.');
  });

  it('rejects content longer than 140 characters', () => {
    expect(validatePostContent('a'.repeat(141))).toBe('Post content must be 140 characters or less.');
  });

  it('accepts valid content', () => {
    expect(validatePostContent('hello world')).toBe('');
  });
});
