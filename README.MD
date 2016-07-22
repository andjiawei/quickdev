## 快速开发框架

### 第一次提交 封装BaseActivity和Toolbar
    1 应用强杀后的启动状态判断
    2 setcontentView和toolbar的封装。（仅返回键 居中文本 menu）
    
### 第二次提交 SampleListActivity
    1 基本的activity 跳转逻辑封装完成
    2 加入 SwipeRefreshLayout RecyclerView
    
### 第三次提交 增加BaseListActivity

### 第四次提交 RecyclerView的封装优化
    1 BaseViewHolder
    2 增加分割线
    
### 第五次提交 RecyclerView和SwipeReflashLayout合并
        1 组合为一个控件，外部只需set相应属性集合。
        
### 第六次提交 加载更多的简单实现
    仅增加逻辑 没有footView布局
    在onRefresh方法中 根据状态判断 刷新或加载更多，加载更多根据RecyvlerView的OnScrollChanger的监听判断，到底部时加载
    
    