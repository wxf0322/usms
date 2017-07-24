import { UsmsAdminFrontPage } from './app.po';

describe('usms-admin-front App', () => {
  let page: UsmsAdminFrontPage;

  beforeEach(() => {
    page = new UsmsAdminFrontPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
