import { UsmsClientPage } from './app.po';

describe('usms-client App', () => {
  let page: UsmsClientPage;

  beforeEach(() => {
    page = new UsmsClientPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
