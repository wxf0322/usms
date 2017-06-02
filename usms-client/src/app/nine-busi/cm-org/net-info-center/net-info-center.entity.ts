export class NetInfoCenter {
  id: string;//中心id
  centerName: string;//中心名称
  contactTel: string;//中心联系电话
  centerLvl: string;//中心层级
  popId: string;//中心负责人id
  name: string;//中心负责人姓名
  staffNum: string;//专职工作人员数量
  area: string;//办公用房面积（平方米）
  annualAmount: string;//年度运行经费（元）
  cameraQty: string;//联网公共安全视频监控摄像机数量
  is24HOnduty: string;//是否24小时有人值守*
  addrId: string;//所在地地址id
  addrScn: string;//地址所在区划
  address: string;//所在详址
  centerStatus: string;//中心在用状态
  upCenterId: string;//上级机构id
  upCenterName: string;//上级机构名称
  timeCreated: any;//创建时间
  creatorId: string;//创建人
  modifierId: string;//修改人
  timeModified: any;//修改时间
  gridId:string;//所属网格
  longitude:string;//经度
  latitude:string;//纬度
  remark:string;//备注

  gsmpLocGrids:any;//网格信息

}