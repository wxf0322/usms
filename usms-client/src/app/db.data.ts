
export const usmsMenus = [
  {
    title: '统一用户管理',
    icon: 'fa fa-cogs',
    expanded: true,
    children: [
      {
        title: '应用管理',
        routerLink: '/usms/application',
      }, {
        title:'组织管理',
        routerLink:'/usms/institution'
      },{
        title: '用户管理',
        routerLink: '/usms/user',
      },{
        title: '角色管理',
        routerLink: '/usms/role',
      }, {
        title: '权限管理',
        routerLink: '/usms/privilege',
      }, {
        title: '操作管理',
        routerLink: '/usms/operation',
      },{
        title: '网格管理',
        routerLink: '/usms/grid',
      }
    ]
  }
];

