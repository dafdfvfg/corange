package com.example.liang.corange.element;

public class State {
    public static int netState = -1;//-1:未启动，未初始化  1没有网络  0 有网络
    public static int screenState = 0;//-1:黑屏，被锁,屏幕关闭  1屏幕灰 开屏  0 解锁了
//	public static int IMState=0;//-1:被挤下线，0正常

    /**
     * 全选相关：全选的按钮逻辑。默认为0
     * 0:点击全选按钮，全选的key为true；选中所有的item 选中全选，全选的key为true;
     * 1:点击全选按钮，全选的key为true；选中所有的item 全选不选中,全选的key为false
     * 2:点击全选按钮，全选的key为true；选中所有的item 全选选中,全选的key为false
     */
    public static int IS_CHECK_ALL_IS_SELECT_ALL = 0;
    /**
     * 全选相关：给后台传递参数的逻辑
     * 后台接受的参数主要是两个
     * 1 isSelectAll ;
     * 2 list<T> ;
     *
     * 选中全选后，在点击一个已选中的item ☟
     * 0:planA:isSelectAll为false；取消所有选中的人，只选中当前点击的item
     * 1:planB:isSelectAll为false；取消选择点击的人的item，其他已经选中且已经加载出来的item也保持选中状态
     * 2:planC:isSelectAll为true； 将没有选中的人放入list<T>,后台将会把list里的item反选
     */
    public static int CHECK_ALL_LOGIC_SWITCH_STATE = 0;
}
