import {TreeData} from '../api';
import {TreeNode} from 'primeng/primeng';

export class TreeUtil {

  /**
   * 递归构建树形节点
   * @param treeData
   * @param treeMap
   * @returns {TreeNode}
   */
  private static buildTreeNode(treeData: TreeData, treeMap: any): TreeNode {
    const treeNode: TreeNode = {
      label: treeData.label,
      expandedIcon: 'fa-folder-open',
      collapsedIcon: 'fa-folder',
      data: {},
      children: []
    };
    treeNode.data = treeData.data;
    treeNode.data['id'] = treeData.id;
    treeNode.data['parentId'] = treeData.parentId;
    treeNode.data['label'] = treeData.label;

    const id = treeData.id.toString();
    if (treeMap[id] != null) {
      for (let i = 0; i < treeMap[id].length; i++) {
        const childNode = treeMap[id][i];
        treeNode.children.push(this.buildTreeNode(childNode, treeMap));
      }
    }
    treeNode.children.sort(function (a, b) {
      return a.data.manualSn - b.data.manualSn;
    });
    return treeNode;
  }

  /**
   * 构造树形数据结构数组
   * @param treeDataArr
   * @returns {any}
   */
  static buildTrees(treeDataArr: TreeData[]): TreeNode[] {
    if (treeDataArr == null || treeDataArr.length === 0) {
      return [];
    }
    const treeMap: any = {};
    for (let i = 0; i < treeDataArr.length; i++) {
      const parentId = treeDataArr[i].parentId.toString();
      if (treeMap[parentId] == null) {
        treeMap[parentId] = [];
      }
      treeMap[parentId].push(treeDataArr[i]);
    }
    const resultTreeNodes: TreeNode[] = [];
    for (let i = 0; i < treeDataArr.length; i++) {
      if (treeDataArr[i].parentId === 0) {
        resultTreeNodes.push(this.buildTreeNode(treeDataArr[i], treeMap));
      }
    }
    return resultTreeNodes;
  }

  /**
   * 设置选中数据
   * @param treeNodes
   * @param selectedData
   */
  static setSelection(treeNodes: TreeNode[], selectedNodes: TreeNode[], selectedData: any[]) {
    if (treeNodes == null || treeNodes.length === 0) {
      return;
    }
    if (selectedData == null || selectedData.length === 0) {
      return;
    }
    for (let i = 0; i < treeNodes.length; i++) {
      for (let j = 0; j < selectedData.length; j++) {
        if (treeNodes[i].data['id'] === selectedData[j]['id']) {
          selectedNodes.push(treeNodes[i]);
          selectedData.splice(j, 1);
          break;
        }
      }
      this.setSelection(treeNodes[i].children, selectedNodes, selectedData);
    }
  }

  /**
   * 模糊匹配树形节点
   * @param treeNodes
   * @param word
   * @returns {TreeNode}
   */
  static findNodesByLabel(treeNodes: TreeNode[], word: string): TreeNode {
    if (treeNodes == null || treeNodes.length === 0) {
      return null;
    }
    for (let i = 0; i < treeNodes.length; i++) {
      if (treeNodes[i].label.indexOf(word) >= 0) {
        return treeNodes[i];
      }
      const selectedNode = this.findNodesByLabel(treeNodes[i].children, word);
      if (selectedNode != null) {
        treeNodes[i].expanded = true;
        return selectedNode;
      }
    }
    return null;
  }

}
