import { describe, expect, it, vi } from 'vitest';

vi.stubGlobal('window', { location: { origin: 'http://localhost:5173' } });

describe('api paths', () => {
  it('can be imported', async () => {
    const module = await import('../lib/api');
    expect(module.apiClient).toBeDefined();
  });
});
