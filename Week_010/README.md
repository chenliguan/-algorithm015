
一、递归思维要点
1、不要人肉进行递归（最大误区），初学者可以在纸上画出递归的状态树，慢慢熟练之后一定要抛弃这样的习惯。一定要记住：直接看函数本身开始写即可。否则，永远没办法掌握、熟练使用递归。
2、找到最近最简的方法，将其拆解成可重复解决的问题（找最近重复子问题）。原因是我们写的程序的指令，只包括 if else 、 for 和 while loop、递归调用。
3、数学归纳法的思维，最开始最简单的条件是成立的，比如n=1,n=2的时候是成立的，且第二点你能证明当n成立的时候，可以推导出n+1也成立的。

4、碰到一个题目，就会找到他的重复性：
  （1）最优重复性：动态规划；
  （2）最近重复性：根据重复性的构造和分解，便有分治和回溯。


二、递归模板
public void recur(int level, int param) {
  // 1.递归终结条件（最先写）
  if (level > MAX_LEVEL) {
    // process result
    return;
  }

  // 2.处理当前层逻辑
  process(level, param);

  // 3.下探到下一层，关键：level + 1
  recur(level: level + 1, newParam);

  // 4.清理恢复当前层
  revertStates();
}


三、分治模板
public void recur(int level, int param) {
  // 1.递归终结条件（最先写）
  if (level > MAX_LEVEL) {
    // process result
    return;
  }

  // 2.处理当前层逻辑
  process(level, param);

  // 3.调用函数下探到下一层，解决更细节的子问题
  int subresult1 = recur(level: level + 1, newParam);
  int subresult2 = recur(level: level + 1, newParam);
  int subresult3 = recur(level: level + 1, newParam);

  // 4.将子问题的解的合并，产生最终结果
  int result = processResult(subresult1, subresult2, subresult3, …);

  // 5.清理恢复当前层
  revertStates();
}


四、回溯
回溯算法是一种 有方向地 遍历搜索算法，以 深度优先遍历 的方式尝试所有的可能性。但不同的是在搜索过程中，达到结束条件后，恢复状态，回溯上一层，再次搜索。
因此回溯算法与 DFS 的区别就是有无状态重置

回溯算法关键在于:不合适就退回上一步，然后通过约束条件, 减少时间复杂度。

回溯算法 采用试错 的思想，它尝试分步的去解决一个问题。在分步解决问题的过程中，当它通过尝试发现现有的分步答案不能得到有效的正确的解答的时候，
它将取消上一步甚至是上几步的计算，再通过其它的可能的分步解答再次尝试寻找问题的答案。回溯法通常用最简单的递归方法来实现，在反复重复上述的步骤后可能出现两种情况：
（1）找到一个可能存在的正确的答案；②在尝试了所有可能的分步方法后宣告该问题没有答案。


五、动态规划
1、特点
动态规划和递归或者分治没有根本上的区别（关键看有无最优的子结构)
 1.共性:找到重复子问题
 2.差异性:最优子结构、中途可以淘太欠优解

递归问题一含有重疊的子问题，操作重复
 1.记忆化搜索(自顶而下)
 2.动态规划(自底而上)

常识：一般求解最值的问题都可以朝着动态规划的方向去想

2、关键点：
 1.根据最优子结构定义状态：dp[n] = bestOf(dp[n-1], dp[n-2], ...)
 2.递推状态转移方程（DP方程）
   一维：dp[i] = dp[n-1] + dp[n-2]
   二维: dp[i,j] = dp[i+1], i] + dp[i][j+1] (且判断dp[i, j]是否空地)
 3.考虑初始化（base case）
 4.考虑输出
 5.考虑优化空间

3、DP顺推模板
// 模板核心
for 状态1 in 状态1的所有取值：
    for 状态2 in 状态2的所有取值：
        for ...
            dp[状态1][状态2][...] = 择优(选择1，选择2...)

// 模板例子
public void fun(int level, int param) {
    // base case：定义 dp 数组并初始化第 0 行和第 0 列。// 二维情况
    dp = [][]

    // dp：根据状态转移方程 dp[i][j] = dp[i - 1][j] + dp[i][j - 1] 进行递推。
    for i = 0..M {
        for j = 0..N {
            dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
        }
    }
    return dp[M][N]
}

// 经典案例
fun uniquePaths(m: Int, n: Int): Int {
    if (m == 0 || n == 0) return 0

    // base case：定义 dp 数组并初始化第 0 行和第 0 列。// 二维情况
    val dp = Array(m) { IntArray(n) }
    for (i in 0 until m) dp[i][0] = 1
    for (j in 0 until n) dp[0][j] = 1

    // dp：根据状态转移方程 dp[i][j] = dp[i - 1][j] + dp[i][j - 1] 进行递推。
    for (i in 1 until m) {
        for (j in 1 until n) {
            dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
        }
    }
    return dp[m - 1][n - 1]
}

4、思考思路：想到动态规划，但又不知从何入手，可以试试这么思考：
 1.大问题是什么？
 2.规模小一点的子问题是什么？
 3.它们之间有什么联系？

5、大问题是一个字符串是否是回文串，那规模小一点的子问题呢？
 一个字符串是回文串，它的首尾字符相同，且剩余子串也是一个回文串。所以，剩余子串是否为回文串，就是规模小一点的子问题，它的结果影响大问题的结果。
 我们怎么去描述子问题呢？写出
 （1）base case：只有一个字母的时候肯定是回文子串，for (i in 0 until n) dp[i][i] = true
 （2）db方程：
    // 如果s[i]==s[j]，说明只要dp[i+1][j-1]是回文子串，那么dp[i][j]也是回文子串；如果s[i]!=s[j]，说明dp[i][j]必定不是回文子串。
    if(s.charAt(i) == s.charAt(j)){
        dp[i][j] = dp[i+1][j-1]
    } else {
        dp[i][j] = false;
    }


六、二分查找代码模板
public int binarySearch(int[] array, int target) {
    int left = 0, right = array.length - 1, mid;
    while (left <= right) {
        mid = (right - left) / 2 + left;

        if (target == array[mid]) {
            return mid;
        } else if (target < array[mid]) {
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }

    return -1;
}

七、BFS模板-迭代
fun levelOrder(root: TreeNode?): List<List<Int>>? {
    val res: MutableList<List<Int>> = mutableListOf()  // 存放最终结果的集合
    if (root == null) return res

    val queue: Queue<TreeNode> = LinkedList()   // 创建一个队列，将根节点放入其中
    queue.offer(root)
    while (!queue.isEmpty()) {
        val level: MutableList<Int> = mutableListOf()
        val size: Int = queue.size   // 每次遍历的数量为队列的长度

        for (i in 0 until size) {   // 将这一层的元素全部取出，放入到结果集合
            val node = queue.poll()
            level.add(node.`val`)

            val left = node.left
            val right = node.right
            if (left != null) queue.offer(left)  // 如果节点的左右孩子不为空，放入队列
            if (right != null) queue.offer(right)
        }

        res.add(level)
    }

    return res
}


八、DFS模板
1、递归
fun levelOrder1(root: TreeNode?): List<List<Int>>? {
    val res: MutableList<MutableList<Int>> = mutableListOf()  // 存放最终结果的集合
    if (root == null) return res

    dfsLevel(1, root, res)
    return res
}

fun dfsLevel(index: Int, root: TreeNode?, res: MutableList<MutableList<Int>>) {
    if (root == null) return

    if (res.size < index) {      // 假设res是[ [1],[2,3] ]， index是3，就再插入一个空list放到res中
        res.add(mutableListOf())
    }

    res[index - 1].add(root.`val`)   // 将当前节点的值加入到res中，index代表当前层，假设index是3，节点值是6。res是[ [1],[2,5],[3,4] ]，加入后res就变为 [ [1],[2,5],[3,4,6] ]

    if (root.left != null) {    // 递归的处理左子树，右子树，同时将层数index+1
        dfsLevel(index + 1, root.left, res)
    }
    if (root.right != null) {
        dfsLevel(index + 1, root.right, res)
    }
}

2、迭代
fun preorder(root: Node?): List<Int> {
    val res: MutableList<Int> = mutableListOf()
    val stack: Deque<Node?> = LinkedList()

    if (root == null) return res else stack.push(root)

    while (!stack.isEmpty()) {
        val p = stack.pop()    // 将根节点弹出
        res.add(p!!.`val`)     // 加入到结果集合中

        for (i in p.children.size - 1 downTo 0) {   // 将该节点的子节点从右往左压入栈
            stack.push(p.children[i])
        }
    }
    return res
}

fun postorder(root: Node?): List<Int> {
    val res: MutableList<Int> = mutableListOf()
    val stack: Deque<Node?> = LinkedList()

    if (root == null) return res else stack.push(root)

    while (!stack.isEmpty()) {
        val p = stack.pop()     // 将根节点弹出
        res.add(p!!.`val`)      // 加入到结果集合中

        for (i in 0 until p.children.size) {  // 将该节点的子节点从左往右压入栈
            stack.push(p.children[i])
        }
    }
    return res.reversed()  // 最后将list反转
}


九、并查集
class UnionFind(n: Int) {

    private var count = 0
    private var parent: IntArray

    /**
     * 用 parent 数组记录每个节点的父节点，相当于指向父节点的指针，所以 parent 数组内实际存储着一个森林（若干棵多叉树）
     * 构造函数，n 为图的节点总数
     * @param x
     * @return
     */
    init {
        // 一开始互不连通
        count = n
        parent = IntArray(n)
        // 父节点指针初始指向自己
        for (i in 0 until n) parent[i] = i
    }

    /**
     * 返回某个节点 x 的根节点
     * @param x
     * @return
     */
    fun find(x: Int): Int {
        // 根节点的 parent[x] == x
        var x = x
        while (parent[x] != x) {
            // 进行路径压缩
            parent[x] = parent[parent[x]]
            x = parent[x]
        }
        return x
    }

    /**
     * 合并
     * @param p
     * @param q
     */
    fun union(p: Int, q: Int) {
        val rootP = find(p)
        val rootQ = find(q)
        if (rootP == rootQ) return

        // 将两棵树合并为一棵
        parent[rootP] = rootQ
        count--
    }
}