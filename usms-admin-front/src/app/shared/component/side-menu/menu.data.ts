export const PAGES_MENU = [
  {
    path: 'pages',
    children: [
      {
        path: 'main',
        data: {
          menu: {
            title: '主页',
            icon: 'fa fa-home',
            selected: false,
            expanded: false,
            order: 0
          }
        }
      },
      {
        path: 'feature1',
        data: {
          menu: {
            title: '一级功能1',
            icon: 'fa fa-map',
            selected: false,
            expanded: false,
            order: 100,
          }
        }
      },
      {
        path: 'feature2',
        data: {
          menu: {
            title: '一级功能2',
            icon: 'fa fa-cogs',
            selected: false,
            expanded: false,
            order: 250,
          }
        },
        children: [
          {
            path: 'feature21',
            data: {
              menu: {
                title: '二级功能21',
              }
            }
          },{
            path: 'feature22',
            data: {
              menu: {
                title: '二级功能22',
              }
            }
          },{
            path: 'feature23',
            data: {
              menu: {
                title: '二级功能23',
              }
            }
          },
        ]
      }
    ]
  }
];
