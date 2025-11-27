import { TestBed } from '@angular/core/testing';

import { RenderapiService } from './renderapi';

describe('Renderapi', () => {
  let service: RenderapiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RenderapiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
